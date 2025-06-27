package org.babe.sqldelight.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.babe.sqldelight.data.model.Task
import org.babe.sqldelight.ui.components.TaskCard
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // Header avec statistiques
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = AppColors.Surface,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Titre principal
                Text(
                    text = "Mes Tâches",
                    style = MaterialTheme.typography.displaySmall,
                    color = AppColors.Neutral900,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Statistiques
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$completedTasks/$totalTasks terminées",
                            style = MaterialTheme.typography.bodyLarge,
                            color = AppColors.Neutral700,
                            fontWeight = FontWeight.Medium
                        )
                        
                        if (totalTasks > 0) {
                            Text(
                                text = "${(progressPercentage * 100).toInt()}% de progression",
                                style = MaterialTheme.typography.bodySmall,
                                color = AppColors.Neutral600
                            )
                        }
                    }
                    
                    // Indicateur de progression circulaire
                    if (totalTasks > 0) {
                        Box(
                            modifier = Modifier.size(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = { progressPercentage },
                                modifier = Modifier.fillMaxSize(),
                                color = AppColors.Success500,
                                strokeWidth = 4.dp,
                                trackColor = AppColors.Neutral200
                            )
                            Text(
                                text = "${(progressPercentage * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = AppColors.Neutral700,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
        
        // Contenu principal
        if (tasks.isEmpty()) {
            // État vide amélioré
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icône d'état vide
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(AppColors.Primary100),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📝",
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Aucune tâche pour le moment",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AppColors.Neutral800,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Commencez par créer votre première tâche",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.Neutral600
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Button(
                        onClick = onAdd,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary600,
                            contentColor = AppColors.Surface
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Text(
                            text = "✨ Créer ma première tâche",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        } else {
            // Liste des tâches
            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = tasks,
                    key = { it.id }
                ) { task ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            animationSpec = tween(300),
                            initialOffsetY = { it / 2 }
                        ) + fadeIn(animationSpec = tween(300)),
                        exit = slideOutVertically(
                            animationSpec = tween(300),
                            targetOffsetY = { -it / 2 }
                        ) + fadeOut(animationSpec = tween(300))
                    ) {
                        TaskCard(
                            task = task,
                            onToggle = onToggle,
                            onDelete = onDelete
                        )
                    }
                }
            }
        }
        
        // Bouton d'ajout flottant
        if (tasks.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = onAdd,
                    containerColor = AppColors.Primary600,
                    contentColor = AppColors.Surface,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.size(56.dp)
                ) {
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}