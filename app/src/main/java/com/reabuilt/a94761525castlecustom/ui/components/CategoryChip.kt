package com.reabuilt.a94761525castlecustom.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Chip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reabuilt.a94761525castlecustom.ui.theme.CastleTheme

@Composable
fun CategoryChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Chip(
        modifier = modifier
            .padding(end = 8.dp),
        onClick = onClick,
        selected = isSelected,
        onSelectedChange = null,
        shape = RoundedCornerShape(20.dp),
        colors = if (isSelected) {
            androidx.compose.material3.ChipDefaults.selectedChipColors(
                containerColor = CastleTheme.colorScheme.primary,
                contentColor = CastleTheme.colorScheme.onPrimary
            )
        } else {
            androidx.compose.material3.ChipDefaults.unselectedChipColors(
                containerColor = CastleTheme.colorScheme.surfaceVariant,
                contentColor = CastleTheme.colorScheme.onSurfaceVariant
            )
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Chip(
        modifier = modifier.padding(end = 8.dp),
        onClick = onClick,
        selected = isSelected,
        onSelectedChange = null,
        shape = RoundedCornerShape(16.dp),
        colors = if (isSelected) {
            androidx.compose.material3.ChipDefaults.selectedChipColors(
                containerColor = CastleTheme.colorScheme.tertiaryContainer,
                contentColor = CastleTheme.colorScheme.onTertiaryContainer
            )
        } else {
            androidx.compose.material3.ChipDefaults.unselectedChipColors(
                containerColor = CastleTheme.colorScheme.surfaceVariant,
                contentColor = CastleTheme.colorScheme.onSurfaceVariant
            )
        }
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )
    }
}
