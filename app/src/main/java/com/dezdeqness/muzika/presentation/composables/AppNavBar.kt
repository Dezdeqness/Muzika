package com.dezdeqness.muzika.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.muzika.presentation.AquaBottomTabModel

@Composable
fun AppNavBar(
    modifier: Modifier = Modifier,
    height: Dp,
    selectedTab: AquaBottomTabModel,
    onTabSelected: (AquaBottomTabModel) -> Unit,
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        containerColor = AppTheme.colors.background,
        tonalElevation = 4.dp,
    ) {
        AquaBottomTabModel.entries.forEach { item ->
            val isSelected = item == selectedTab

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onTabSelected(item)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
