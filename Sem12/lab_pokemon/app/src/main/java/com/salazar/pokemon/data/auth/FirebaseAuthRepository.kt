package com.salazar.pokemon.data.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.salazar.pokemon.domain.model.Rol
import com.salazar.pokemon.domain.model.Sesion
import kotlinx.coroutines.tasks.await
import java.io.IOException

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val usuarioActual: UsuarioActual = UsuarioActual()
) : AuthRepository {

    override suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion> = try {
        val uid = auth.signInWithEmailAndPassword(correo, clave).await().user!!.uid
        Result.success(leerSesion(uid, correo))
    } catch (e: FirebaseNetworkException) {
        Result.failure(IOException(e))
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Result.failure(CredencialesInvalidasException())
    } catch (e: FirebaseAuthInvalidUserException) {
        Result.failure(CredencialesInvalidasException())
    } catch (e: FirebaseTooManyRequestsException) {
        Result.failure(DemasiadosIntentosException())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun sesionActual(): Sesion? {
        val usuario = auth.currentUser ?: return null
        return leerSesion(usuario.uid, usuario.email ?: "")
    }

    override fun cerrarSesion() {
        auth.signOut()
        usuarioActual.establecer(null)
    }

    // el rol vive en Firestore: usuarios/{uid}.rol
    private suspend fun leerSesion(uid: String, correo: String): Sesion {
        usuarioActual.establecer(uid)
        val rol = try {
            val doc = db.collection("usuarios").document(uid).get().await()
            if (doc.getString("rol") == "admin") Rol.ADMIN else Rol.USUARIO
        } catch (e: Exception) {
            Rol.USUARIO
        }
        return Sesion(uid, correo, rol)
    }
}
