package cn.mycommons.lynx_android

import android.app.Application
import com.lynx.tasm.LynxEnv

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLynx()
    }

    private fun initLynx() {
        LynxApp.initLynxService()
        LynxEnv.inst().init(
            this,
            null,
            null,
            null
        )
        LynxApp.initLynxEnv()
    }
}