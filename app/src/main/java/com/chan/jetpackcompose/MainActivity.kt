package com.chan.jetpackcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.chan.jetpackcompose.model.Message
import com.chan.jetpackcompose.model.Messages
import com.chan.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopAppBar()
        }
    }
}

@Composable
fun TopAppBar() {

    val menuList = arrayOf("Favorites", "Options", "Setting", "Share")
    val disableItem = 1

    val context = LocalContext.current.applicationContext

    var expanded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Jetpack Compose") },
                backgroundColor = MaterialTheme.colors.onPrimary,
                elevation = AppBarDefaults.TopAppBarElevation,
                contentColor = contentColorFor(backgroundColor = Color.Black),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Navigation Icon"
                        )
                    }
                },
                actions = {
//                    Show Search toolbar Icon
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                    }

                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Lock")
                    }
                    
//                    Option Menu
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "Option Menu")
                    }

//                    Option Drop Down Menu

                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 10.dp, y = 10.dp)) {

                    }

                }
            )
        }) {
        it.calculateTopPadding()
        ShowContent()
    }
}

@Composable
fun ShowContent() {
    JetpackComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Conversation(Messages.conversationSample)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

//     Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }

//        surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column(modifier = Modifier.clickable {
            isExpanded = !isExpanded
        }) { // click event is clickable
            Text(
                text = msg.title,
                style = MaterialTheme.typography.title, // text style
            )
            Spacer(modifier = Modifier.height(4.dp)) // between spacing
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = surfaceColor,
                elevation = 1.dp
            ) { // background shape
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp), // set text padding
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1, // show isExpanded is true show all line else show single line
                    style = MaterialTheme.typography.subTitle,
                )
            }

        }
    }
}

// Show List

@Composable
fun Conversation(messages: List<Message>) {
    val context = LocalContext.current.applicationContext
    LazyColumn {
        items(messages) { message ->
            Card(
                elevation = 2.dp, modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                    .clickable { onClick(message, context) },
                border = BorderStroke(1.dp, Color.Gray),
                backgroundColor = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(10.dp)
            ) {
                MessageCard(message)
            }
        }
    }
}

fun onClick(message: Message, context: Context) {
    Toast.makeText(context, "Info = ${message.title}\n${message.body}", Toast.LENGTH_LONG).show()
}

@Preview
@Composable
fun PreviewConversation() {
    TopAppBar()
}


// enable dark theme
//@Preview(name = "Light Mode")
/*@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

@Composable
fun PreviewMessageCard() {
    JetpackComposeTheme {
        Surface() {
            MessageCard(
                msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}*/
