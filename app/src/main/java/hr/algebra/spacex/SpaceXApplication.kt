package hr.algebra.spacex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import hr.algebra.spacex.common.util.StoragePathFinder
import javax.inject.Inject

@HiltAndroidApp
class SpaceXApplication @Inject constructor() : Application() {
    override fun onCreate() {
        super.onCreate()
        StoragePathFinder.initilize(this)
    }
}