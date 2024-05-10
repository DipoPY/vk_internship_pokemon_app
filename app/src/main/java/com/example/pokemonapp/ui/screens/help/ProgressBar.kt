package com.example.pokemonapp.ui.screens.help

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.common.CustomFontFamily
import com.example.pokemonapp.ui.theme.AttakColor
import com.example.pokemonapp.ui.theme.DefColor
import com.example.pokemonapp.ui.theme.HpColor

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ProgressBar(currentPoints: Int, maxPoints: Int = 100, target: String) {
    val fillRatio = currentPoints.toFloat() / maxPoints.toFloat()

    BoxWithConstraints(
        modifier = Modifier
            .padding(16.dp)
            .height(30.dp)
            .fillMaxWidth()
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        val result: String = "$currentPoints / $maxPoints"

        Box(
            modifier = Modifier
                .fillMaxWidth(fillRatio)
                .height(30.dp)
                .background(
                    color = when (target) {
                        "hp" -> HpColor
                        "attak" -> AttakColor
                        else -> DefColor
                    }, shape = RoundedCornerShape(12.dp)
                )
        )
        Text(
            text = result,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.Center),
            fontFamily = CustomFontFamily,
            color = Color.Black,

            )
    }
}
