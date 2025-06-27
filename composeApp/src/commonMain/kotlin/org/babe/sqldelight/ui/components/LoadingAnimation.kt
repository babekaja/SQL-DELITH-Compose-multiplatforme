package org.babe.sqldelight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.babe.sqldelight.ui.theme.AppColors

@Composable
fun PulsatingDots(
    modifier: Modifier = Modifier,
    dotCount: Int = 3,
    color: Color = AppColors.Primary500
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val infiniteTransition = rememberInfiniteTransition(label = "dot_$index")
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = index * 200),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dot_scale_$index"
            )
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
fun SpinningLoader(
    modifier: Modifier = Modifier,
    size: Int = 32,
    strokeWidth: Int = 3
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spinner_rotation"
    )

    CircularProgressIndicator(
        modifier = modifier
            .size(size.dp)
            .rotate(rotation),
        strokeWidth = strokeWidth.dp,
        color = AppColors.Primary500,
        trackColor = AppColors.Primary100
    )
}

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true
) {
    if (isLoading) {
        val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
        val shimmerTranslateAnim by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer_translate"
        )

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            AppColors.Shimmer.copy(alpha = 0.3f),
                            AppColors.Shimmer.copy(alpha = 0.7f),
                            AppColors.Shimmer.copy(alpha = 0.3f)
                        ),
                        startX = shimmerTranslateAnim - 300f,
                        endX = shimmerTranslateAnim
                    )
                )
        )
    }
}

@Composable
fun BouncingBalls(
    modifier: Modifier = Modifier,
    ballCount: Int = 3
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(ballCount) { index ->
            val infiniteTransition = rememberInfiniteTransition(label = "ball_$index")
            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(500, delayMillis = index * 150),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "ball_offset_$index"
            )
            
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .offset(y = offsetY.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                AppColors.Primary400,
                                AppColors.Primary600
                            )
                        )
                    )
            )
        }
    }
}