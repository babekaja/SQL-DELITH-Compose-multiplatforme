package org.babe.sqldelight.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.babe.sqldelight.ui.components.GlassmorphismCard
import org.babe.sqldelight.ui.components.PulsatingDots
import org.babe.sqldelight.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    // Animation d'entrée
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
        delay(300)
        focusRequester.requestFocus()
    }
    
    // Gestion de la sauvegarde avec animation
    val handleSave = {
        if (title.isNotBlank()) {
            isLoading = true
            keyboardController?.hide()
        }
    }
    
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(800) // Simulation d'une sauvegarde
            showSuccess = true
            delay(500)
            onSave(title)
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            initialOffsetY = { it }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it }
        ) + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AppColors.Background,
                            AppColors.Primary50.copy(alpha = 0.3f)
                        )
                    )
                )
        ) {
            // Header avec glassmorphism
            GlassmorphismCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bouton retour avec animation
                    TextButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = AppColors.Neutral600
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "←",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Retour",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Titre centré
                    AnimatedContent(
                        targetState = when {
                            showSuccess -> "✅ Créée !"
                            isLoading -> "Création..."
                            else -> "Nouvelle tâche"
                        },
                        transitionSpec = {
                            slideInVertically { -it } + fadeIn() togetherWith
                            slideOutVertically { it } + fadeOut()
                        },
                        label = "header_title"
                    ) { headerTitle ->
                        Text(
                            text = headerTitle,
                            style = MaterialTheme.typography.headlineSmall,
                            color = when {
                                showSuccess -> AppColors.Success600
                                isLoading -> AppColors.Primary600
                                else -> AppColors.Neutral900
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Bouton sauvegarder avec animation
                    AnimatedContent(
                        targetState = isLoading,
                        transitionSpec = {
                            scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut()
                        },
                        label = "save_button"
                    ) { loading ->
                        if (loading) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(AppColors.Primary100),
                                contentAlignment = Alignment.Center
                            ) {
                                PulsatingDots(
                                    dotCount = 3,
                                    color = AppColors.Primary600
                                )
                            }
                        } else {
                            Button(
                                onClick = handleSave,
                                enabled = title.isNotBlank() && !isLoading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppColors.Primary600,
                                    contentColor = Color.White,
                                    disabledContainerColor = AppColors.Neutral200,
                                    disabledContentColor = AppColors.Neutral400
                                ),
                                shape = RoundedCornerShape(12.dp),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 2.dp
                                )
                            ) {
                                Text(
                                    text = "Créer",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
            
            // Contenu principal avec animation
            AnimatedVisibility(
                visible = !isLoading,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    // Section titre avec animation
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { it / 2 }
                        ) + fadeIn(
                            animationSpec = tween(delayMillis = 200)
                        )
                    ) {
                        Column {
                            Text(
                                text = "Que souhaitez-vous accomplir ?",
                                style = MaterialTheme.typography.headlineMedium,
                                color = AppColors.Neutral800,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Décrivez votre tâche en quelques mots pour mieux vous organiser",
                                style = MaterialTheme.typography.bodyLarge,
                                color = AppColors.Neutral600
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Champ de saisie amélioré avec animation
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { it / 2 }
                        ) + fadeIn(
                            animationSpec = tween(delayMillis = 400)
                        )
                    ) {
                        Column {
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                label = { 
                                    Text(
                                        "Titre de la tâche",
                                        style = MaterialTheme.typography.bodyMedium
                                    ) 
                                },
                                placeholder = { 
                                    Text(
                                        "Ex: Faire les courses, Appeler le médecin, Finir le rapport...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = AppColors.Neutral400
                                    ) 
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AppColors.Primary500,
                                    unfocusedBorderColor = AppColors.Neutral300,
                                    focusedLabelColor = AppColors.Primary600,
                                    unfocusedLabelColor = AppColors.Neutral600,
                                    cursorColor = AppColors.Primary600,
                                    focusedContainerColor = AppColors.Surface,
                                    unfocusedContainerColor = AppColors.Surface
                                ),
                                textStyle = MaterialTheme.typography.bodyLarge,
                                singleLine = false,
                                maxLines = 4,
                                shape = RoundedCornerShape(16.dp),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { handleSave() }
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Indicateurs et conseils
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "💡 Soyez précis pour mieux vous organiser",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AppColors.Primary600
                                )
                                
                                AnimatedContent(
                                    targetState = "${title.length} caractères",
                                    transitionSpec = {
                                        slideInVertically { it } + fadeIn() togetherWith
                                        slideOutVertically { -it } + fadeOut()
                                    },
                                    label = "character_count"
                                ) { count ->
                                    Text(
                                        text = count,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppColors.Neutral500
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Suggestions avec animation
                    AnimatedVisibility(
                        visible = title.isEmpty(),
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        Column {
                            Text(
                                text = "💭 Quelques idées :",
                                style = MaterialTheme.typography.titleMedium,
                                color = AppColors.Neutral700,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            val suggestions = listOf(
                                "📚 Lire 30 minutes",
                                "🏃‍♂️ Faire du sport",
                                "📞 Appeler un ami",
                                "🛒 Faire les courses",
                                "💼 Préparer la réunion"
                            )
                            
                            suggestions.forEachIndexed { index, suggestion ->
                                AnimatedVisibility(
                                    visible = true,
                                    enter = slideInHorizontally(
                                        animationSpec = tween(delayMillis = index * 100)
                                    ) + fadeIn(
                                        animationSpec = tween(delayMillis = index * 100)
                                    )
                                ) {
                                    TextButton(
                                        onClick = { title = suggestion.substringAfter(" ") },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = AppColors.Neutral600
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Text(
                                                text = suggestion,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Écran de chargement avec animation
            AnimatedVisibility(
                visible = isLoading,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Animation de création
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            AppColors.Primary100,
                                            AppColors.Primary50
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (showSuccess) "✅" else "✨",
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        AnimatedContent(
                            targetState = if (showSuccess) "Tâche créée avec succès !" else "Création de votre tâche...",
                            transitionSpec = {
                                slideInVertically { it } + fadeIn() togetherWith
                                slideOutVertically { -it } + fadeOut()
                            },
                            label = "loading_text"
                        ) { text ->
                            Text(
                                text = text,
                                style = MaterialTheme.typography.headlineSmall,
                                color = if (showSuccess) AppColors.Success600 else AppColors.Primary600,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        
                        if (!showSuccess) {
                            Spacer(modifier = Modifier.height(16.dp))
                            PulsatingDots(
                                dotCount = 3,
                                color = AppColors.Primary500
                            )
                        }
                    }
                }
            }
        }
    }
}