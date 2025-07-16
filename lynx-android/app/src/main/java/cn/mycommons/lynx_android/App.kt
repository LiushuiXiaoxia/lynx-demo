package cn.mycommons.lynx_android

import android.app.Application
import com.lynx.tasm.LynxEnv

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLynxService()
        initLynxEnv()
    }

    private fun initLynxService() {

    }

    private fun initLynxEnv() {
        LynxEnv.inst().init(
            this,
            null,
            null,
            null
        )
    }
}