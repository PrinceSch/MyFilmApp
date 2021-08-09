package ru.geeekbrains.myfilmapp.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnakeBar(text: String, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, text, length).show()
}

fun View.showSnakeBar(stringResource: Int, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, context.getString(stringResource), length).show()
}

fun View.show(): View{
    if (visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View{
    if (visibility != View.GONE){
        visibility = View.GONE
    }
    return this
}