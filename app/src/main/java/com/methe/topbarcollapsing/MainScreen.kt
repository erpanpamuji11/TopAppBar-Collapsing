package com.methe.topbarcollapsing

import android.graphics.drawable.Icon
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(text = "Megawe")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                    }
                },
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
//                ),
            actions = {
                Row {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "menu")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "menu")
                    }
                }
            },

            )
        },
        content = {
            CustomList(paddingValues = it)
        }
    )

}

@Composable
fun CustomList(paddingValues: PaddingValues) {
    val numbers = remember { mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) }

    LazyColumn(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())){
        items(items = numbers, key = {it.hashCode()}) {
            Text(
                text = "$it",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(lazyListState: LazyListState) {
    TopAppBar(
        title = {
                Text(text = "Darkside")
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT),

    )
}

@Composable
fun MainContent(lazyListState: LazyListState) {
    val number = remember { List(size = 25) { it } }
    val padding by animateDpAsState(
        targetValue = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(durationMillis = 300)
    )

    LazyColumn(
        modifier = Modifier.padding(top = padding),
        state = lazyListState
    ) {
        items(
            items = number,
            key = {it}
        ) {
            NumberHolder(number = it)
        }
    }
}

@Composable
fun NumberHolder(number: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = number.toString(), style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        ))
    }
}

val TOP_BAR_HEIGHT = 56.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
