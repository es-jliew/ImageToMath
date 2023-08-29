package com.essoft.imagetomath.module

import com.essoft.imagetomath.ui.presentation.homeScreen.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }
}