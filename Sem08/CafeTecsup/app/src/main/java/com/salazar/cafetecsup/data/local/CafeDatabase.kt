package com.salazar.cafetecsup.data.local

import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.data.local.entity.Usuario
import com.salazar.cafetecsup.data.local.entity.Pedido
import com.salazar.cafetecsup.data.local.entity.DetallePedido
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salazar.cafetecsup.data.local.dao.PedidoDao
import com.salazar.cafetecsup.data.local.dao.ProductoDao
import com.salazar.cafetecsup.data.local.dao.UsuarioDao

@Database(
    entities = [
        Producto::class,
        Usuario::class,
        Pedido::class,
        DetallePedido::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CafeDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile
        private var INSTANCE: CafeDatabase? = null

        fun obtener(context: Context): CafeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CafeDatabase::class.java,
                    "cafecampus.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}