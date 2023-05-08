package com.chan.jetpackcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.chan.jetpackcompose.ui.theme.JetpackComposeTheme
import kotlinx.coroutines.launch

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopBarContent()
        }
    }
}

@Composable
fun TopBarContent() {
    JetpackComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            ShowNavigationContent()
        }
    }
}

@Composable
fun ShowNavigationContent() {
    val context = LocalContext.current.applicationContext

    val navController = rememberNavController()
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    var selectedItem by remember {
        mutableStateOf("Profile")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val disableItem = 1

    val bottomMenu = arrayOf("Profile", "Friends", "Setting")
    val optionMenu = arrayOf("Favorites", "Option", "Setting", "Share")

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = "Jetpack Compose") },
                backgroundColor = Color.Blue,
                contentColor = Color.Black,
                elevation = AppBarDefaults.TopAppBarElevation,

                navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Filled.Menu, "Navigation Menu")
                    }
                },
                actions = {
//                       Search Icon
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Search hear",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(Icons.Outlined.Search, "Search")
                    }
//                       Lock Icon
                    IconButton(onClick = {
                        Toast.makeText(context, "Lock !!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Outlined.Lock, "Lock")
                    }
//                       Option menu

                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Outlined.MoreVert, "Options")
                    }

//                       DropDown Option menu

                    DropdownMenu(
                        expanded = expanded, onDismissRequest = { expanded = false },
                        offset = DpOffset(x = 10.dp, y = 10.dp)) {

                        optionMenu.forEachIndexed { index, value ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                Toast.makeText(context, value, Toast.LENGTH_SHORT).show() },
                                enabled = (index != disableItem)) {
                                Text(text = value)
                            }
                        }
                    }
//                    End Dropdown option menu
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {},
        drawerContent = {},


        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.LightGray,
                elevation = AppBarDefaults.BottomAppBarElevation,
                contentPadding = AppBarDefaults.ContentPadding,
                contentColor = contentColorFor(backgroundColor = Color.Black)
            ) {
                BottomNavigation(backgroundColor = Color.LightGray) {
                    bottomMenu.forEach { item ->
                        BottomNavigationItem(
                            selected = item == selectedItem,
                            label = { Text(text = item) },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = item
                                )
                            },
                            onClick = {
                                selectedItem = item
                                Toast.makeText(context, "Selected item $item", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            enabled = true,
                            alwaysShowLabel = true
                        )
                    }
                }
            }
        }) {
        it.calculateTopPadding()
        it.calculateBottomPadding()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TopBarContent()
}