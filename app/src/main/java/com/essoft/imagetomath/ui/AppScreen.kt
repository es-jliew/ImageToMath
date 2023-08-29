package com.essoft.imagetomath.ui

sealed class AppScreens(val name : String){
    object Home : AppScreens("home")
}