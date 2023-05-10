package com.chan.jetpackcompose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chan.jetpackcompose.auth.MainScreen
import com.chan.jetpackcompose.auth.WindowCenterOffset
import com.chan.jetpackcompose.model.NavScreen
import com.chan.jetpackcompose.model.NavigationDrawerItem
import com.chan.jetpackcompose.ui.theme.JetpackComposeTheme
import com.chan.jetpackcompose.ui.theme.Purple200
import kotlinx.coroutines.delay
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
    val optionMenu = arrayOf("Favorites", "Option", "Setting", "Share")
    val disableItem = 1
    
    var popupControl by remember{
        mutableStateOf(false)
    }

    val menuItems = listOf(NavScreen.MyHome, NavScreen.Setting, NavScreen.About)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Jetpack Compose") },
                elevation = AppBarDefaults.TopAppBarElevation,
                backgroundColor = Color.Blue,
                contentColor = contentColorFor(backgroundColor = Color.Black),

                navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Navigation Menu"
                        )
                    }
                },
                actions = {
//                       Search Icon
                    IconButton(onClick = {
                        Toast.makeText(context, "Search hear", Toast.LENGTH_SHORT).show() }) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                    }
//                  Logout Icon
                    IconButton(onClick = { popupControl = true }) {
                        Icon(painter = painterResource(id = R.drawable.logout), contentDescription = "Lock")
                    }
//                 Option menu
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Options Menu"
                        )
                    }
//                  DropDown Option menu
                    DropdownMenu(
                        expanded = expanded, onDismissRequest = { expanded = false },
                        offset = DpOffset(x = 10.dp, y = 10.dp)) {

                        optionMenu.forEachIndexed { index, value ->
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                                },
                                enabled = (index != disableItem))
                            {
                                Text(text = value)
                            }
                        }
                    }
//                    End Dropdown option menu
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch { Toast.makeText(context, "Snackbar", Toast.LENGTH_SHORT).show() }
            }, shape = RoundedCornerShape(16.dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Fan Icon")
            }
        },
        drawerElevation = 5.dp,
        drawerBackgroundColor = Color.Cyan,
        drawerContentColor = Color.Black,
        drawerContent = {
            DrawerContest { itemLabel ->
                navController.navigate(itemLabel) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch {
                    delay(timeMillis = 250)
                    scaffoldState.drawerState.close()
                }
            }
        },

        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.LightGray,
                cutoutShape = null,
                elevation = AppBarDefaults.BottomAppBarElevation,
                contentPadding = AppBarDefaults.ContentPadding,
                contentColor = contentColorFor(backgroundColor = Color.Black)) {
                BottomNavigation(backgroundColor = Color.LightGray) {
                    menuItems.forEach { item ->
                        BottomNavigationItem(
                            selected = currentDestination?.hierarchy?.any{(it.route == item.route)} == true,//item.route == selectedItem,
                            label = {
                                Text(
                                    stringResource(id = item.resourceId),
                                    fontFamily = FontFamily.SansSerif
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = item.resourceId.toString()
                                )
                            },
                            onClick = {
                                //selectedItem = item.route

                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            enabled = true,
                            alwaysShowLabel = true
                        )
                    }
                }
            }
        }) {it.calculateTopPadding()
        NavHost(
            navController = navController,
            startDestination = NavScreen.MyHome.route) {

            composable(NavScreen.MyHome.route) {
                HomeScreen(navController)
            }
            composable(NavScreen.Profile.route) {
                ProfileScreen(
                    onHome = {navController.popBackStack(NavScreen.MyHome.route, false)}
                )
            }
            composable(NavScreen.Setting.route) {
                SettingScreen(
                    onHome = { navController.popBackStack() },
                    onProfile = { navController.navigate(NavScreen.Profile.route) })
            }
            composable(NavScreen.Friends.route) {
                FriendScreen(
                    onHome = {navController.popBackStack(NavScreen.MyHome.route, false)}
                )
            }
            composable(NavScreen.About.route) {
                AboutScreen(
                    onHome = {navController.popBackStack(NavScreen.MyHome.route, false)}
                )
            }
        }
    }
    
    if(popupControl){
        Popup(
            popupPositionProvider = WindowCenterOffset(),
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                excludeFromSystemGesture = true, clippingEnabled = false
            ),
            onDismissRequest = { popupControl = false }) {

            Surface(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(8.dp),
                color = Purple200,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp)
                    .background(Color.Cyan)) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    
                    Text(text = "You want to logout?", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .absolutePadding(20.dp, 16.dp, 20.dp, 0.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly){
                        Button(onClick = {popupControl = false }) {
                            Text(text = "Close")
                        }
                        Button(onClick = {
                            popupControl = false 
                            context.startActivity(Intent(context, MainScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        }) {
                            Text(text = "Logout")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(onHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.profile), fontSize = 20.sp)
        Button(onClick =  onHome) {
            Text(text = "Go back to home", fontSize = 20.sp)
        }
    }
}

@Composable
fun SettingScreen(onHome: () -> Unit, onProfile: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.setting), fontSize = 20.sp)
        Button(onClick = onHome) {
            Text(text = "Go back to home", fontSize = 20.sp)
        }
        Button(onClick = onProfile) {
            Text(text = "Go to  Profile", fontSize = 20.sp)
        }
    }
}

@Composable
fun FriendScreen(onHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.friend), fontSize = 20.sp)
        Button(onClick = onHome) {
            Text(text = "Go back to home", fontSize = 20.sp)
        }
    }
}

@Composable
fun AboutScreen(onHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.about), fontSize = 20.sp)
        Button(onClick = onHome) {
            Text(text = "Go back to home", fontSize = 20.sp)


        }
    }
}
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.title_activity_home), fontSize = 20.sp)

        Button(onClick = { navController.navigate(NavScreen.Setting.route) }) {
            Text(text = "Go to Setting", fontSize = 20.sp)
        }
        Button(onClick = { navController.navigate(NavScreen.About.route) }) {
            Text(text = "Go to About", fontSize = 20.sp)
        }
    }
}

@Composable
fun DrawerContest(
    gradientColors: List<Color> = listOf(Color(0xFFF70A74), Color(0xFFF59118)),
    itemClick: (String) -> Unit
) {

    val menuItemList = navigationDrawerItems()
    val activity = LocalContext.current.findActivity()

    val username = activity?.intent!!.getStringExtra("username")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp)) {

        item {
//            User Photo
            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Image"
            )

//            Users Name
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = username!!,
                style = MaterialTheme.typography.username
            )

//            Users Email
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = " Abc@gmail.com",
                style = MaterialTheme.typography.email
            )
        }
//        Drawable Menu item
        items(menuItemList) { item ->
            NavigationMenuList(item = item) { itemClick(item.route) }
        }
    }

}

@Composable
fun NavigationMenuList(
    item: NavigationDrawerItem,
    unreadBubbleColor: Color = Color(0xFF0FFF93),
    itemClick: () -> Unit
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { itemClick() }
        .padding(horizontal = 24.dp, vertical = 10.dp)) {
//        menu item icon
        Box {
            Icon(
                modifier = Modifier
                    .padding(all = if (item.showUnreadBubble && item.label == "Message") 5.dp else 2.dp)
                    .size(size = if (item.showUnreadBubble && item.label == "Message") 24.dp else 28.dp),
                painter = item.image,
                contentDescription = item.label, tint = Color.White
            )

            if (item.showUnreadBubble) {
                Box(
                    modifier = Modifier
                        .size(size = 8.dp)
                        .align(alignment = Alignment.TopEnd)
                        .background(color = unreadBubbleColor, shape = CircleShape)
                )
            }
        }
//        Menu label
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = item.label,
            style = MaterialTheme.typography.drawerItemText
        )
    }
}

@Composable
fun navigationDrawerItems(): List<NavigationDrawerItem> {
    val itemList = arrayListOf<NavigationDrawerItem>()

    itemList.add(
        NavigationDrawerItem(
            image = painterResource(id = R.drawable.home),
            label = stringResource(id = R.string.title_activity_home),
            route = "home",
        )
    )
    itemList.add(
        NavigationDrawerItem(
            image = painterResource(id = R.drawable.profle),
            label = stringResource(id = R.string.profile),
            route = "profile",
        )
    )

    itemList.add(
        NavigationDrawerItem(
            image = painterResource(id = R.drawable.friends),
            label = stringResource(id = R.string.friend),
            route = "friends",
        )
    )
    return itemList
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TopBarContent()
}