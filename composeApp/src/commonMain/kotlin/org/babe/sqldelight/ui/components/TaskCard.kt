package org.babe.sqldelight.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.babe.sqldelight.data.model.Task
import org.babe.sqldelight.ui.theme.AppColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TaskCard(
    task: Task,
    onToggle: (Task) -> Unit,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    
    // Animations pour l'état de la tâche
    val backgroundColor by animateColorAsState(
        targetValue = when {
            task.done -> AppColors.Success50
            isPressed -> AppColors.Primary50
            else -> AppColors.Surface
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "background_color"
    )
    
    val borderColor by animateColorAsState(
        targetValue = when {
            task.done -> AppColors.Success300
            isPressed -> AppColors.Primary300
            else -> AppColors.Neutral200
        },
        animationSpec = tween(300),
        label = "border_color"
    )
    
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "card_scale"
    )
    
    val elevation by animateDpAsState(
        targetValue = when {
            task.done -> 2.dp
            isPressed -> 1.dp
            else -> 6.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "card_elevation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onToggle(task) }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.5.dp,
            color = borderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        )
    ) {
        Box {
            // Effet de gradient subtil
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = if (task.done) {
                                listOf(
                                    AppColors.Success400,
                                    AppColors.Success500,
                                    AppColors.Success600
                                )
                            } else {
                                listOf(
                                    AppColors.Primary400,
                                    AppColors.Primary500,
                                    AppColors.Secondary500
                                )
                            }
                        )
                    )
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Checkbox animée personnalisée
                AnimatedCheckIcon(
                    isChecked = task.done,
                    size = 28,
                    modifier = Modifier.clickable { onToggle(task) }
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Contenu de la tâche
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    AnimatedContent(
                        targetState = task.title,
                        transitionSpec = {
                            slideInVertically { it } + fadeIn() togetherWith
                            slideOutVertically { -it } + fadeOut()
                        },
                        label = "task_title"
                    ) { title ->
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (task.done) AppColors.Neutral600 else AppColors.Neutral900,
                            textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                    
                    if (task.done) {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInVertically() + fadeIn(),
                            exit = slideOutVertically() + fadeOut()
                        ) {
                            Text(
                                text = "✨ Terminée",
                                style = MaterialTheme.typography.bodySmall,
                                color = AppColors.Success600,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Bouton de suppression avec animation
                AnimatedVisibility(
                    visible = !showDeleteConfirm,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    AnimatedDeleteIcon(
                        isVisible = true,
                        onClick = { showDeleteConfirm = true }
                    )
                }
                
                // Confirmation de suppression
                AnimatedVisibility(
                    visible = showDeleteConfirm,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Bouton Annuler
                        IconButton(
                            onClick = { showDeleteConfirm = false },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AppColors.Neutral200),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "↶",
                                    color = AppColors.Neutral600,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                        
                        // Bouton Confirmer
                        IconButton(
                            onClick = { onDelete(task.id) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AppColors.Error500),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✓",
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}