package com.essoft.imagetomath.module

import com.essoft.imagetomath.repo.HomeRepo
import com.essoft.imagetomath.repo.IHomeRepo
import org.koin.dsl.bind
import org.koin.dsl.module

val repoModule = module {
    single { HomeRepo(get(), get()) } bind (IHomeRepo::class)
}