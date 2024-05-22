package com.scarry.ammusicplayer.ui.Components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun AsyncImageWithPlaceholder(
    model: Any?,
    contentDescription: String?,
    onImageLoadingFinished: (Throwable?) -> Unit,
    onImageLoading: () -> Unit,
    modifier: Modifier = Modifier,
    isLoadingPlaceholderVisible: Boolean,
    placeholderHighlight: PlaceholderHighlight = PlaceholderHighlight.shimmer(),
    errorPainter: Painter? = null,//that is displayed when the image request is unsuccessful.
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    ){
    AsyncImage(
        modifier = Modifier
            .placeholder(
                visible = isLoadingPlaceholderVisible,
                highlight = placeholderHighlight,
            ),
        model = model,
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        onError = { onImageLoadingFinished(it.result.throwable) },
        onSuccess = { onImageLoadingFinished(null) },
        onLoading = { onImageLoading() },
        error = errorPainter,
    )
}