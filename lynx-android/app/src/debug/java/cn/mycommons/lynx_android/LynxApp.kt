package cn.mycommons.lynx_android

import com.lynx.service.devtool.LynxDevToolService
import com.lynx.tasm.LynxEnv
import com.lynx.tasm.service.LynxServiceCenter

object LynxApp {

    fun initLynxService() {
         LynxServiceCenter.inst().registerService(LynxDevToolService.INSTANCE)
    }

    fun initLynxEnv() {
        // 打开 Lynx Debug 开关
        LynxEnv.inst().enableLynxDebug(true)
        // 打开 Lynx DevTool 开关
        LynxEnv.inst().enableDevtool(true)
        // 打开 Lynx LogBox 开关
        LynxEnv.inst().enableLogBox(true)
    }
}