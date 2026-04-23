package com.glacei.tresrayas

object Routes {
    const val HOME = "home"
    const val CREDITS = "credits"
    const val SETUP = "setup"
    const val GAME = "game/{mode}/{p1}/{p2}"

    fun createGameRoute(mode: String, p1: String, p2: String) = "game/$mode/$p1/$p2"
}