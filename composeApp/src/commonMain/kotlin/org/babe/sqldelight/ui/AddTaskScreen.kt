package org.babe.sqldelight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.babe.sqldelight.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // Header personnalisé
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = AppColors.Surface,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bouton retour
                TextButton(
                    onClick = onCancel,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AppColors.Neutral600
                    )
                ) {
                    Text(
                        text = "← Annuler",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                Text(
                    text = "Nouvelle tâche",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppColors.Neutral900,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Bouton sauvegarder
                Button(
                    onClick = { if (title.isNotBlank()) onSave(title) },
                    enabled = title.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary600,
                        contentColor = AppColors.Surface,
                        disabledContainerColor = AppColors.Neutral200,
                        disabledContentColor = AppColors.Neutral400
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Créer",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        
        // Contenu principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Titre de section
            Text(
                text = "Que souhaitez-vous accomplir ?",
                style = MaterialTheme.typography.headlineMedium,
                color = AppColors.Neutral800,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Décrivez votre tâche en quelques mots",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Neutral600
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Champ de saisie amélioré
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
                        "Ex: Faire les courses, Appeler le médecin...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.Neutral400
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
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
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Indicateur de caractères
            Text(
                text = "${title.length} caractères",
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.Neutral500,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}