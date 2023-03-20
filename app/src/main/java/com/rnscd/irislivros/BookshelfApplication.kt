package com.rnscd.irislivros

import android.app.Application
import com.rnscd.irislivros.di.AppContainer
import com.rnscd.irislivros.di.DefaultAppContainer

class BookshelfApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}