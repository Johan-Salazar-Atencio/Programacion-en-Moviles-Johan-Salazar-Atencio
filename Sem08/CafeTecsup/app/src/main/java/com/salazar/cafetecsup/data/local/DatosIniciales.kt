package com.salazar.cafetecsup.data.local

import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.data.local.entity.Usuario


object DatosIniciales {

    suspend fun sembrar(db: CafeDatabase) {
        if (db.usuarioDao().contar() == 0) {
            //Usuarios
            db.usuarioDao().insertar(
                Usuario(
                    nombre = "Johan Salazar",
                    correo = "johan@campus.test",
                    clave = "1234",
                    rol = Rol.CLIENTE.name
                )
            )
            db.usuarioDao().insertar(
                Usuario(
                    nombre = "Administrador",
                    correo = "admin@cafe.test",
                    clave = "admin",
                    rol = Rol.ADMIN.name
                )
            )

            //6 Productos iniciales
            db.productoDao().insertar(
                Producto(
                    nombre = "Café americano",
                    descripcion = "Café pasado recién hecho",
                    categoria = "Bebidas",
                    precio = 4.00,
                    disponible = true,
                    icono = "cafe"
                )
            )
            db.productoDao().insertar(
                Producto(
                    nombre = "Sándwich de pollo",
                    descripcion = "Con mayonesa y lechuga fresca",
                    categoria = "Piqueos",
                    precio = 9.00,
                    disponible = true,
                    icono = "pan"
                )
            )
            db.productoDao().insertar(
                Producto(
                    nombre = "Menú del día",
                    descripcion = "Plato principal + refresco",
                    categoria = "Platos",
                    precio = 12.00,
                    disponible = true,
                    icono = "plato"
                )
            )
            db.productoDao().insertar(
                Producto(
                    nombre = "Jugo de naranja",
                    descripcion = "100% natural exprimidor",
                    categoria = "Bebidas",
                    precio = 5.00,
                    disponible = true,
                    icono = "jugo"
                )
            )
            db.productoDao().insertar(
                Producto(
                    nombre = "Empanada",
                    descripcion = "Rellena de carne molida",
                    categoria = "Piqueos",
                    precio = 4.50,
                    disponible = true,
                    icono = "pan"
                )
            )
            db.productoDao().insertar(
                Producto(
                    nombre = "Chicha morada",
                    descripcion = "Vaso helado de 500ml",
                    categoria = "Bebidas",
                    precio = 3.50,
                    disponible = true,
                    icono = "botella"
                )
            )
        }
    }
}