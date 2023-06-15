package com.example.pliin.apppliin.ui.manifest.mymanifest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ManifestScreen() {
    Box() {
        Column() {
            Header(
                Modifier
                    .weight(0.2f)
                    .background(Color(0xFF4425a7))
            )
            Body(
                Modifier
                    .weight(2.2f)
                    .padding(horizontal = 8.dp)
            )
            Footer(
                Modifier
                    .weight(0.6f)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }
    }

}

@Composable
fun Header(modifier: Modifier) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text(text = "Manifiesto") },
        backgroundColor = Color(0xFF4425a7),
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    )
}

@Composable
fun Body(padding: Modifier) {
}

@Composable
fun Footer(padding: Modifier) {
}