package cn.mycommons.lynx_android.lynx

import android.content.Context
import android.util.Log
import com.lynx.tasm.provider.AbsTemplateProvider
import kotlin.concurrent.thread

class DemoTemplateProvider(context: Context) : AbsTemplateProvider() {


    companion object {
        private const val TAG = "DemoTemplateProvider"
    }

    private var app: Context = context.applicationContext

    override fun loadTemplate(uri: String, callback: Callback) {
        Log.i(TAG, "loadTemplate: uri = $uri")
        thread {
            runCatching {
                val data = app.assets.open(uri).use { it.readBytes() }
                callback.onSuccess(data)
            }.onSuccess {
                Log.i(TAG, "loadTemplate: uri = $uri, success")
            }.onFailure {
                Log.e(TAG, "loadTemplate: uri = $uri, failed", it)
                callback.onFailed(it.message)
            }
        }
    }
}