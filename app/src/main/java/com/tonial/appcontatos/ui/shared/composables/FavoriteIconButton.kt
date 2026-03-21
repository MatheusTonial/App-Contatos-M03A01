package com.tonial.appcontatos.ui.shared.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tonial.appcontatos.ui.theme.AppContatosTheme

@Composable
fun FavoriteIconButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavoritePressesd: () -> Unit
){
    IconButton(
        modifier = modifier,
        onClick = {
            onFavoritePressesd()
        }
    ) {
        Icon(
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Filled.FavoriteBorder
            },
            contentDescription = "Favoritar",
            tint = if (isFavorite) {
                Color.Red
            } else {
                LocalContentColor.current
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun FavoriteIconButtonPreview(){
    AppContatosTheme {
        FavoriteIconButton(
            isFavorite = false,
            onFavoritePressesd = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun FavoriteIconButtonPreviewIsFavorited(){
    AppContatosTheme {
        FavoriteIconButton(
            isFavorite = true,
            onFavoritePressesd = {}
        )
    }
}