package com.scarry.ammusicplayer.ui.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun FilterChip(
    text : String,
    isSelected : Boolean,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
){
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.7f)
            else MaterialTheme.colors.surface,
        ),
        border = if (isSelected) BorderStroke(1.dp, MaterialTheme.colors.primary)
        else ButtonDefaults.outlinedBorder,
        content = { Text(text = text, color = Color.Black)}
    )
}