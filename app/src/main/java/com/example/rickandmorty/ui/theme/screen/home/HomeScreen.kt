package com.example.rickandmorty.ui.theme.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickandmorty.R
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues? = null,
) {

    val pullToRefreshState = rememberPullToRefreshState()
    val lazyVerticalGridState = rememberLazyGridState()

    val charactersList by viewModel.charactersListFlow.collectAsState()
    val filteredCharactersList by viewModel.filteredCharactersListFlow.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(lazyVerticalGridState) {
        snapshotFlow { lazyVerticalGridState.layoutInfo }.collect { layoutInfo ->
            if (!viewModel.isLoadingMore) {
                val totalItemsNumber = layoutInfo.totalItemsCount
                if (totalItemsNumber == 0) return@collect
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
                val threshold = 4
                if (lastVisibleItemIndex == totalItemsNumber) {
                    viewModel.refresh()
                }
                if (lastVisibleItemIndex >= totalItemsNumber - 1 - threshold) {
                    viewModel.loadMoreCharacters()
                }
            }
        }
    }

    if (viewModel.isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.isBottomSheetOpen = false },
            sheetState = bottomSheetState
        ) {
            Column {
                Row {
                    val genderOptions: List<GenderFilter> = GenderFilter.entries
                    genderOptions.forEach { gender ->
                        val isSelected = gender == viewModel.selectedGender
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                viewModel.selectedGender = if (isSelected) null else gender
                            },
                            label = { Text(gender.name) },
                            modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        isRefreshing = viewModel.isRefreshing,
        onRefresh = {
            viewModel.refresh()
        },
        state = pullToRefreshState,
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 16.dp),
            state = lazyVerticalGridState,
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues ?: PaddingValues(0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Home Screen",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = {
                            viewModel.isBottomSheetOpen = true
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.outline_filter_alt_24),
                            contentDescription = "filter_icon"
                        )
                    }
                }
            }
            items(filteredCharactersList/*charactersList*/) {character ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Column {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(character.image)
                                .crossfade(true)
                                // .placeholder(R.drawable.placeholder)
                                // .error(R.drawable.error_image)
                                .build(),
                            contentDescription = "Изображение персонажа: ${character.name}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = character.name,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = character.species,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = character.status,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = character.gender,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)) {
                    AnimatedVisibility(
                        visible = viewModel.isLoadingMore,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        CircularProgressIndicator(modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.Center), strokeWidth = 3.dp)
                    }
                }
            }
        }
    }
}