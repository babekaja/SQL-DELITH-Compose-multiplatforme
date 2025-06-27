package org.babe.sqldelight.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.babe.sqldelight.data.model.Task
import org.babe.sqldelight.ui.components.*
import org.babe.sqldelight.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onToggle: (Task) -> Unit,
    onDelete: (Long) -> Unit,
    onAdd: () -> Unit
) {
    val completedTasks = tasks.count { it.done }
    val totalTasks = tasks.size
    val progressPercentage = if (totalTasks > 0) (completedTasks.toFloat() / totalTasks) else 0f
    
    var isAddPressed by remember { mutableStateOf(false) }
    var showCelebration by remember { mutableStateOf(false) }
    
    // Animation de célébration quand toutes les tâches sont terminées
    LaunchedEffect(progressPercentage) {
        if (progressPercentage == 1f && totalTasks > 0) {
            showCelebration = true
            delay(3000)
            showCelebration = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppColors.Background,
                        AppColors.BackgroundSecondary.copy(alpha = 0.3f)
                    )
                )
            )
    ) {
        // Header moderne avec glassmorphism
        GlassmorphismCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Titre avec animation
                AnimatedContent(
                    targetState = if (showCelebration) "🎉 Félicitations !" else "Mes Tâches",
                    transitionSpec = {
                        slideInVertically { -it } + fadeIn() togetherWith
                        slideOutVertically { it } + fadeOut()
                    },
                    label = "header_title"
                ) { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.displaySmall,
                        color = if (showCelebration) AppColors.Success600 else AppColors.Neutral900,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Statistiques avec animations
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        AnimatedContent(
                            targetState = "$completedTasks/$totalTasks terminées",
                            transitionSpec = {
                                slideInVertically { it } + fadeIn() togetherWith
                                slideOutVertically { -it } + fadeOut()
                            },
                            label = "stats_text"
                        ) { stats ->
                            Text(
                                text = stats,
                                style = MaterialTheme.typography.titleLarge,
                                color = AppColors.Neutral800,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        
                        if (totalTasks > 0) {
                            AnimatedContent(
                                targetState = "${(progressPercentage * 100).toInt()}% de progression",
                                transitionSpec = {
                                    slideInVertically { it } + fadeIn() togetherWith
                                    slideOutVertically { -it } + fadeOut()
                                },
                                label = "progress_text"
                            ) { progress ->
                                Text(
                                    text = progress,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AppColors.Neutral600
                                )
                            }
                        }
                    }
                    
                    // Indicateur de progression circulaire animé
                    if (totalTasks > 0) {
                        Box(
                            modifier = Modifier.size(64.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val animatedProgress by animateFloatAsState(
                                targetValue = progressPercentage,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                label = "progress_animation"
                            )
                            
                            CircularProgressIndicator(
                                progress = { animatedProgress },
                                modifier = Modifier.fillMaxSize(),
                                color = if (progressPercentage == 1f) AppColors.Success500 else AppColors.Primary500,
                                strokeWidth = 6.dp,
                                trackColor = AppColors.Neutral200
                            )
                            
                            AnimatedContent(
                                targetState = "${(animatedProgress * 100).toInt()}%",
                                transitionSpec = {
                                    scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut()
                                },
                                label = "progress_percentage"
                            ) { percentage ->
                                Text(
                                    text = percentage,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = if (progressPercentage == 1f) AppColors.Success600 else AppColors.Primary600,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                
                // Barre de progression linéaire
                if (totalTasks > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val animatedLinearProgress by animateFloatAsState(
                        targetValue = progressPercentage,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "linear_progress"
                    )
                    
                    LinearProgressIndicator(
                        progress = { animatedLinearProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (progressPercentage == 1f) AppColors.Success500 else AppColors.Primary500,
                        trackColor = AppColors.Neutral200
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Contenu principal
        if (tasks.isEmpty()) {
            // État vide avec animation
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icône animée d'état vide
                    PulsingIcon(
                        icon = "📝",
                        modifier = Modifier.size(80.dp),
                        color = AppColors.Primary400
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Aucune tâche pour le moment",
                        style = MaterialTheme.typography.headlineMedium,
                        color = AppColors.Neutral800,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Commencez par créer votre première tâche\net organisez votre journée",
                        style = MaterialTheme.typography.bodyLarge,
                        color = AppColors.Neutral600,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Bouton d'action principal avec animation
                    Button(
                        onClick = onAdd,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary600,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .height(56.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "✨",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Créer ma première tâche",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        } else {
            // Liste des tâches avec animations
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(
                    items = tasks,
                    key = { _, task -> task.id }
                ) { index, task ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            ),
                            initialOffsetY = { it / 2 }
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 300,
                                delayMillis = index * 50
                            )
                        ),
                        exit = slideOutHorizontally(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            ),
                            targetOffsetX = { -it }
                        ) + fadeOut(animationSpec = tween(200))
                    ) {
                        TaskCard(
                            task = task,
                            onToggle = onToggle,
                            onDelete = onDelete
                        )
                    }
                }
                
                // Espacement pour le FAB
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        
        // Bouton d'ajout flottant moderne
        if (tasks.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = {
                        isAddPressed = true
                        onAdd()
                    },
                    containerColor = AppColors.Primary600,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.size(64.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 12.dp,
                        pressedElevation = 6.dp
                    )
                ) {
                    AnimatedAddIcon(
                        isPressed = isAddPressed,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        
        // Reset de l'état pressed
        LaunchedEffect(isAddPressed) {
            if (isAddPressed) {
                delay(150)
                isAddPressed = false
            }
        }
    }
}