package org.babe.sqldelight.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.babe.sqldelight.data.model.Task
import org.babe.sqldelight.ui.theme.AppColors

@Composable
fun TaskCard(
    task: Task,
    onToggle: (Task) -> Unit,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (task.done) AppColors.Success50 else AppColors.Surface,
        animationSpec = tween(300),
        label = "background_color"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (task.done) AppColors.Success200 else AppColors.Neutral200,
        animationSpec = tween(300),
        label = "border_color"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onToggle(task) },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (task.done) 2.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox personnalisée
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (task.done) AppColors.Success500 else Color.Transparent
                    )
                    .clickable { onToggle(task) },
                contentAlignment = Alignment.Center
            ) {
                if (task.done) {
                    Text(
                        text = "✓",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Transparent,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(2.dp)
                            .background(
                                Color.Transparent,
                                RoundedCornerShape(4.dp)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    AppColors.Neutral200,
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Titre de la tâche
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (task.done) AppColors.Neutral600 else AppColors.Neutral900,
                textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Bouton de suppression
            IconButton(
                onClick = { onDelete(task.id) },
                modifier = Modifier.size(32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppColors.Error100)
                        .clickable { onDelete(task.id) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "×",
                        color = AppColors.Error600,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}