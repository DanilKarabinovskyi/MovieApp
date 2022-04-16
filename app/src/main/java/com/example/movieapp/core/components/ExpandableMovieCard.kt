package com.example.movieapp.core.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.core.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.core.presentation.COLLAPSE_ANIMATION_DURATION
import com.example.movieapp.core.presentation.EXPAND_ANIMATION_DURATION
import com.example.movieapp.core.presentation.FADE_IN_ANIMATION_DURATION
import com.example.movieapp.core.presentation.FADE_OUT_ANIMATION_DURATION
import com.example.movieapp.data.entity.Movie
import com.example.movieapp.ui.theme.cardCollapsedBackgroundColor
import com.example.movieapp.ui.theme.cardExpandedBackgroundColor

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableMovieCard(
    cardModel: Movie,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    showFavoriteIcon:Boolean = true,
    onLikeClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "bgColorTransition") {
        cardCollapsedBackgroundColor
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "elevationTransition") {
        if (expanded) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "cornersTransition") {
        16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }

    Card(
        backgroundColor = cardBgColor,
        contentColor = Color(
            ContextCompat.getColor(
                LocalContext.current,
                com.example.movieapp.R.color.purple_200
            )
        ),
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Column (
            modifier = Modifier.clickable {
                onItemClick(cardModel.id)
            }
        ){
            Row (modifier = Modifier.height(125.dp)){
                Box(modifier = Modifier.height(125.dp).weight(2f)) {
                    Image(
                        painter = rememberImagePainter(
                            data = cardModel.images[0],
                            imageLoader = ImageLoader.invoke(LocalContext.current)
                        ),
                        contentDescription = "Image",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(104.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.onSurface,
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.Crop
                    )
                }

                Column (modifier = Modifier.weight(3f).padding(top = 8.dp)) {
                    CardTitle(title = cardModel.title)
                    CardTitle(title = "Director: ${cardModel.director}")
                    CardTitle(title = "Released: ${cardModel.year}")
                    CardArrow(
                        degrees = arrowRotationDegree,
                        onClick = onCardArrowClick
                    )
                }
                if(showFavoriteIcon) {
                    Box(modifier = Modifier.height(125.dp).weight(1f)) {
                        Icon(
                            imageVector = if (cardModel.liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(end = 26.dp)
                                .size(24.dp).clickable {
                                    onLikeClick(cardModel.id)
                                },
                        )
                    }
                }
            }
            ExpandableContent(visible = expanded,cardModel.plot)
        }

    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = com.example.movieapp.R.drawable.ic_expand_less_24),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees).size(24.dp),
            )
        }
    )
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 2.dp,
                top = 4.dp,
                end = 4.dp
            ),
        textAlign = TextAlign.Start,
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    description:String
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION_DURATION))
    }
    AnimatedVisibility(
        visible = visible,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row() {
                Text(
                    text = description,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}