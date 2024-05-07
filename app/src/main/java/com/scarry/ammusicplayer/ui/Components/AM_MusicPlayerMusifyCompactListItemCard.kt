package com.scarry.ammusicplayer.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.scarry.ammusicplayer.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AM_MusicPlayerCompactListItemCard(
    thumbnailImageURLString: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    trailingButton: ImageVector,
    onTrailingButtonClick: () -> Unit,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            AsyncImage(
                model = thumbnailImageURLString,
                contentDescription = null,
                modifier = Modifier.size(75.dp),
                contentScale = ContentScale.Crop
            )
            Column (
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            IconButton(onClick = onTrailingButtonClick) {
                Icon(
                    imageVector =trailingButton ,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun MusifyCompactListItemPreview(){
    AM_MusicPlayerCompactListItemCard(
       thumbnailImageURLString = "hi",
       title = "rock",
       subtitle = "hi",
       onClick = { /*TODO*/ },
       trailingButton = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
   ) {

   }
}