package com.example.RaceTracker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

class Elegido(
    val nombre: String,
    val ProgresoMax: Int = 100,
    private val pasosPorAvance: Int = 5,
    private val delayMillis: Long = 200L,
    private val ProgresoInic: Int = 0
) {
    init {
        require(ProgresoMax > 0) { "ProgresoMaximo=$ProgresoMax; " }
        require(pasosPorAvance > 0) { "PasosPorAvance=$pasosPorAvance; " }
    }

    var ProgresoActual by mutableStateOf(ProgresoInic)
        private set

    fun avanzar() {
        if (ProgresoActual < ProgresoMax) {
            ProgresoActual += pasosPorAvance
        }
    }

    fun reset() {
        ProgresoActual = 0
    }
}

val Participante.progressFactor: Float
    get() = ProgresoActual / ProgresoMax.toFloat()
