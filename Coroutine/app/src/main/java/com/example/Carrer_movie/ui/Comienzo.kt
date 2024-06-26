package com.example.RaceTracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.RaceTracker.ui.theme.RaceTrackerTheme
import com.example.racetracker.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppComienzo() {
    val jugador1 = remember {
        Participante(nombre = "Jugador N°1")
    }
    val jugador2 = remember {
        Participante(nombre = "Jugador N°2")
    }
    var carrera by remember { mutableStateOf(false) }

    if (carrera) {
        LaunchedEffect(jugador1, jugador2) {
            // Simulación de carrera usando coroutines
            coroutineScope {
                launch { jugador1.runRace() }
                launch { jugador2.runRace() }
            }
            carrera = false
        }
    }
    PantallaCarrera(
        playerOne = jugador1,
        playerTwo = jugador2,
        estadoCarrera = carrera,
        estadoCarreraCambiable = { carrera = it },
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
    )
}

@Composable
private fun PantallaCarrera(
    playerOne: Participante,
    playerTwo: Participante,
    estadoCarrera: Boolean,
    estadoCarreraCambiable: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "CARRERA")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.carrera),
                contentDescription = null,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
            StatusIndicator(
                participantName = playerOne.nombre,
                currentProgress = playerOne.progresoActual,
                maxProgress = playerOne.progresoMaximo.toString(),
                progressFactor = playerOne.factorProgreso,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
            StatusIndicator(
                participantName = playerTwo.nombre,
                currentProgress = playerTwo.progresoActual,
                maxProgress = playerTwo.progresoMaximo.toString(),
                progressFactor = playerTwo.factorProgreso,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
            RaceControls(
                isRunning = estadoCarrera,
                onRunStateChange = estadoCarreraCambiable,
                onReset = {
                    playerOne.reiniciar()
                    playerTwo.reiniciar()
                    estadoCarreraCambiable(false)
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun StatusIndicator(
    participantName: String,
    currentProgress: Int,
    maxProgress: String,
    progressFactor: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = participantName,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            LinearProgressIndicator(
                progress = progressFactor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.progress_indicator_height))
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.progress_indicator_corner_radius)))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$currentProgress%",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = maxProgress,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun RaceControls(
    onRunStateChange: (Boolean) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    isRunning: Boolean = true,
) {
    Column(
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Button(
            onClick = { onRunStateChange(!isRunning) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (isRunning) stringResource(R.string.pausa) else stringResource(R.string.inicio))
        }
        OutlinedButton(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.reiniciar))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RaceTrackerAppPreview() {
    RaceTrackerTheme {
        AppCarrera()
    }
}
