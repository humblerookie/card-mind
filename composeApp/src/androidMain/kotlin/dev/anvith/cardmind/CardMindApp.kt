package dev.anvith.cardmind

import android.app.Application
import dev.anvith.cardmind.di.initKoin
import dev.anvith.cardmind.screens.DetailViewModel
import dev.anvith.cardmind.screens.ListViewModel
import org.koin.dsl.module

class CardMindApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory { ListViewModel(get()) }
                    factory { DetailViewModel(get()) }
                }
            )
        )
    }
}
