package com.hiven.talo.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Stock : Screen("stock")
    data object Sale : Screen("sale")
}