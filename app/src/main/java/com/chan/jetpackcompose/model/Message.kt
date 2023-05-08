package com.chan.jetpackcompose.model


/**
 * Created by Chandrabhan Haribhau Aher on 15-03-2023.
 * chandrabhan99@gmail.com
 */
data class Message(
    var title: String,
    var body: String
)

object Messages {
    val conversationSample = listOf(
        Message("Android", "Jetpack Compose"),
        Message(
            "Android",
            "Jetpack Compose text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text" +
                    "text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text" +
                    "text.. text text.. text text.. text text.. text text.. text text.. text text.. text text.. text"
        ),
        Message("Android", "I think Kotlin is my favorite language. It's so much fun!"),
        Message("Android", "Searching for alternative XMl Layout"),
        Message("Android", "Test for list"),
        Message(
            "Android",
            "Hey, take a look at Jetpack Compose, it's great!\n" +
                    "It's the Android's modern toolkit for building native UI." +
                    "It simplifies and accelerates UI development on Android." +
                    "Less code, powerful tools, and intuitive Kotlin APIs"
        ),
        Message(
            "Android",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Android",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Android",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Android",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Android",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Android",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}
