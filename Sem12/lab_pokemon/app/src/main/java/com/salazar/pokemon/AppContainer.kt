package com.salazar.pokemon

import android.content.Context
import com.salazar.pokemon.data.auth.AuthRepository
import com.salazar.pokemon.data.auth.FirebaseAuthRepository
import com.salazar.pokemon.data.local.AppDatabase
import com.salazar.pokemon.data.remote.RetrofitClient
import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.data.repository.ItemRepositoryImpl
import com.salazar.pokemon.notification.Notificador
import com.salazar.pokemon.notification.NotificadorAndroid

class AppContainer(context: Context) {
    private val database = AppDatabase.crear(context)

    val itemRepository: ItemRepository =
        ItemRepositoryImpl(RetrofitClient.api, database.itemDao())

    val authRepository: AuthRepository = FirebaseAuthRepository()

    val notificador: Notificador = NotificadorAndroid(context)
}
