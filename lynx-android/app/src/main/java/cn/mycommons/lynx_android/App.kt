package cn.mycommons.lynx_android

import android.app.Application
import cn.mycommons.lynx_android.lynx.DemoTemplateProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.memory.PoolConfig
import com.facebook.imagepipeline.memory.PoolFactory
import com.lynx.service.http.LynxHttpService
import com.lynx.service.image.LynxImageService
import com.lynx.service.log.LynxLogService
import com.lynx.tasm.LynxEnv
import com.lynx.tasm.service.LynxServiceCenter

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLynx()
    }

    private fun initLynx() {
        val factory = PoolFactory(PoolConfig.newBuilder().build())
        val builder = ImagePipelineConfig.newBuilder(applicationContext).setPoolFactory(factory)
        Fresco.initialize(applicationContext, builder.build())

        LynxServiceCenter.inst().registerService(LynxImageService.getInstance())
        LynxServiceCenter.inst().registerService(LynxLogService)
        LynxServiceCenter.inst().registerService(LynxHttpService)

        LynxApp.initLynxService()

        LynxEnv.inst().init(
            this,
            null,
            DemoTemplateProvider(this),
            null
        )
        LynxApp.initLynxEnv()
    }
}