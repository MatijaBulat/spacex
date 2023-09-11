package hr.algebra.spacex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SpaceXApplication @Inject constructor() : Application() {
}