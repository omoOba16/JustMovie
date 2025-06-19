package com.example.justmovie.util.compose

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun getShimmerAnimateBrush(
    animDuration : Int = 1000
): Brush {

    val gradient = listOf(
        Color.Gray.copy(alpha = 0.1f),
        Color.Gray.copy(alpha = 0.3f),
        Color.Gray.copy(alpha = 0.1f)
    )

    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animDuration,
                easing = FastOutLinearInEasing
            )
        ), label = ""
    )
    return Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
}