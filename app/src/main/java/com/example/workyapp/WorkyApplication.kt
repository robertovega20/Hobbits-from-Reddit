package com.example.workyapp

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Interceptor

@HiltAndroidApp
class WorkyApplication(): Application() {


    fun initializeFlipper(): Interceptor {
        SoLoader.init(this, false)

        val networkFlipperPlugin = NetworkFlipperPlugin()
        val client = AndroidFlipperClient.getInstance(this)
        client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
        client.addPlugin(networkFlipperPlugin)
        client.start()

        return FlipperOkhttpInterceptor(networkFlipperPlugin)
    }
}