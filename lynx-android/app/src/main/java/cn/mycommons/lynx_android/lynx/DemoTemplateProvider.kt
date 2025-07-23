package cn.mycommons.lynx_android.lynx

import android.content.Context
import android.util.Log
import com.lynx.tasm.provider.AbsTemplateProvider
import okhttp3.OkHttpClient
import okhttp3.Request
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
                if (uri.startsWith("http://") || uri.startsWith("https://")) {
                    val req = Request.Builder()
                        .url(uri)
                        .build()
                    val client = OkHttpClient.Builder().build()
                    client.newCall(req).execute().use { response ->
                        if (!response.isSuccessful) {
                            throw Exception("Failed to load template: ${response.message}")
                        }
                        val data = response.body?.bytes() ?: throw Exception("Response body is null")
                        callback.onSuccess(data)
                    }
                } else {
                    val data = app.assets.open(uri).use { it.readBytes() }
                    callback.onSuccess(data)
                }
            }.onSuccess {
                Log.i(TAG, "loadTemplate: uri = $uri, success")
            }.onFailure {
                Log.e(TAG, "loadTemplate: uri = $uri, failed", it)
                callback.onFailed(it.message)
            }
        }
    }
}