package com.chan.jetpackcompose.auth

import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*


/**
 * Created by Chandrabhan Haribhau Aher on 08-05-2023.
 * chandrabhan99@gmail.com
 */
class WindowCenterOffset(private val x: Int = 0, private val y: Int = 0) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect, windowSize: IntSize, layoutDirection: LayoutDirection, popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(
            (windowSize.width - popupContentSize.width) / 2 + x,
            (windowSize.height - popupContentSize.height) / 2 + y)
    }
}