package cn.mycommons.lynx_android

import android.content.Context
import com.lynx.tasm.provider.AbsTemplateProvider
import java.io.IOException

class DemoTemplateProvider(context: Context) : AbsTemplateProvider() {

    private var mContext: Context = context.applicationContext

    override fun loadTemplate(uri: String, callback: Callback) {
        Thread {
            try {
                val data = mContext.assets.open(uri).use { it.readBytes() }
                callback.onSuccess(data)

            } catch (e: IOException) {
                callback.onFailed(e.message)
            }
        }.start()
    }
}