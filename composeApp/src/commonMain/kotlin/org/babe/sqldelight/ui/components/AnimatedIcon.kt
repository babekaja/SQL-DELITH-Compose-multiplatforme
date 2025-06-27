package org.babe.sqldelight.ui.components

import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.babe.sqldelight.ui.theme.AppColors

@Composable
fun AnimatedCheckIcon(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    size: Int = 24
) {
    val scale by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "check_scale"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked) AppColors.Success500 else Color.Transparent,
        animationSpec = tween(300),
        label = "check_background"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (isChecked) AppColors.Success500 else AppColors.Neutral300,
        animationSpec = tween(300),
        label = "check_border"
    )

    Box(
        modifier = modifier
            .size(size.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(backgroundColor)
            .background(
                if (!isChecked) Color.Transparent else Color.Transparent,
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!isChecked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent, CircleShape)
                    .padding(2.dp)
                    .background(borderColor, CircleShape)
                    .padding(2.dp)
                    .background(AppColors.Surface, CircleShape)
            )
        } else {
            AnimatedVisibility(
                visible = isChecked,
                enter = scaleIn(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                ) + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = (size * 0.6).sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AnimatedDeleteIcon(
    isVisible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "delete_scale"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else 180f,
        animationSpec = tween(300),
        label = "delete_rotation"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .rotate(rotation)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(AppColors.Error100),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "×",
                color = AppColors.Error600,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AnimatedAddIcon(
    modifier: Modifier = Modifier,
    isPressed: Boolean = false
) {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "add_scale"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) 45f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "add_rotation"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .rotate(rotation),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PulsingIcon(
    icon: String,
    modifier: Modifier = Modifier,
    color: Color = AppColors.Primary500
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Text(
        text = icon,
        modifier = modifier.scale(scale),
        color = color.copy(alpha = alpha),
        fontSize = 48.sp
    )
}