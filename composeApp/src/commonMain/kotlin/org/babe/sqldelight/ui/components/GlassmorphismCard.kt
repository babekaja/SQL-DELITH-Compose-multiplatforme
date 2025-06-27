package org.babe.sqldelight.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.babe.sqldelight.ui.theme.AppColors

@Composable
fun GlassmorphismCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppColors.Glass.copy(alpha = 0.25f),
                        AppColors.Glass.copy(alpha = 0.1f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        content()
    }
}

@Composable
fun NeumorphismCard(
    modifier: Modifier = Modifier,
    isPressed: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val elevation = if (isPressed) 2.dp else 8.dp
    val shadowColor = if (isPressed) AppColors.Neutral400 else AppColors.Neutral300
    
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isPressed) {
                            listOf(
                                AppColors.Neutral100,
                                AppColors.Surface
                            )
                        } else {
                            listOf(
                                AppColors.Surface,
                                AppColors.Neutral50
                            )
                        }
                    )
                )
        ) {
            content()
        }
    }
}