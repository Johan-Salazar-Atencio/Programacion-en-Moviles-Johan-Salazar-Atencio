package com.tecsup.campusgps.ui

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.tecsup.campusgps.R
import com.tecsup.campusgps.model.LugarConDistancia
import com.tecsup.campusgps.model.UbicacionUi
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

private const val ATRIBUCION = "© MapTiler © OpenStreetMap contributors"
private const val ROJO = 0xFFE53935.toInt()
private const val AZUL = 0xFF1E88E5.toInt()
private const val VERDE = 0xFF43A047.toInt()

private class TeselasMapTiler(private val clave: String) : OnlineTileSourceBase(
    "MapTiler", 0, 20, 256, ".png", arrayOf("https://api.maptiler.com/maps/streets-v2/256/"), ATRIBUCION
) {
    override fun getTileURLString(indice: Long): String =
        "${baseUrl}${MapTileIndex.getZoom(indice)}/${MapTileIndex.getX(indice)}/" +
            "${MapTileIndex.getY(indice)}$mImageFilenameEnding?key=$clave"
}

private fun claveMapTiler(contexto: Context): String =
    contexto.packageManager
        .getApplicationInfo(contexto.packageName, PackageManager.GET_META_DATA)
        .metaData?.getString("MAPTILER_KEY").orEmpty()

private fun crearMapa(contexto: Context): MapView {
    Configuration.getInstance().apply {
        userAgentValue = contexto.packageName
        osmdroidBasePath = contexto.cacheDir
        osmdroidTileCache = contexto.cacheDir.resolve("tiles")
    }
    return MapView(contexto).apply {
        setTileSource(TeselasMapTiler(claveMapTiler(contexto)))
        setMultiTouchControls(true)
        zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
        controller.setZoom(12.0)
        controller.setCenter(GeoPoint(-12.0464, -77.0428))
    }
}

private fun pin(contexto: Context, color: Int): Drawable {
    val base = ContextCompat.getDrawable(contexto, R.drawable.ic_pin)!!.mutate()
    DrawableCompat.setTint(base, color)
    return base
}

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercano: Int?,
    radio: Int?,
    modifier: Modifier = Modifier
) {
    val contexto = LocalContext.current
    val mapa = remember { crearMapa(contexto) }
    val ciclo = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(ciclo) {
        val observador = LifecycleEventObserver { _, evento ->
            when (evento) {
                Lifecycle.Event.ON_RESUME -> mapa.onResume()
                Lifecycle.Event.ON_PAUSE -> mapa.onPause()
                else -> Unit
            }
        }
        ciclo.addObserver(observador)
        onDispose {
            ciclo.removeObserver(observador)
            mapa.onDetach()
        }
    }

    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            mapa.controller.animateTo(
                GeoPoint(estado.posicion.latitud, estado.posicion.longitud), 16.0, 1000L
            )
        }
    }

    LaunchedEffect(seleccionado) {
        lugares.firstOrNull { it.lugar.id == seleccionado }?.let {
            mapa.controller.animateTo(GeoPoint(it.lugar.latitud, it.lugar.longitud), 17.0, 1000L)
        }
    }

    Box(modifier = modifier.clipToBounds()) {
        AndroidView(
            factory = { mapa },
            modifier = Modifier.fillMaxSize(),
            update = { vista ->
                vista.overlays.clear()
                if (estado is UbicacionUi.Lista && radio != null) {
                    vista.overlays.add(Polygon(vista).apply {
                        points = Polygon.pointsAsCircle(
                            GeoPoint(estado.posicion.latitud, estado.posicion.longitud),
                            radio.toDouble()
                        )
                        fillPaint.color = 0x338B1E3F
                        outlinePaint.color = 0xFF8B1E3F.toInt()
                        outlinePaint.strokeWidth = 4f
                    })
                }
                lugares.forEach { item ->
                    val marcador = Marker(vista).apply {
                        position = GeoPoint(item.lugar.latitud, item.lugar.longitud)
                        title = item.lugar.nombre
                        snippet = item.lugar.categoria
                        icon = pin(contexto, if (item.lugar.id == masCercano) VERDE else AZUL)
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    }
                    vista.overlays.add(marcador)
                    if (item.lugar.id == seleccionado) marcador.showInfoWindow()
                }
                if (estado is UbicacionUi.Lista) {
                    vista.overlays.add(Marker(vista).apply {
                        position = GeoPoint(estado.posicion.latitud, estado.posicion.longitud)
                        title = "Estás aquí"
                        icon = pin(contexto, ROJO)
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    })
                }
                vista.invalidate()
            }
        )
        Text(
            text = ATRIBUCION,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        )
    }
}
