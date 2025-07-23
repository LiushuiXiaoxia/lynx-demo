package cn.mycommons.lynx_android.lynx

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import cn.mycommons.lynx_android.util.uriInputStream
import com.lynx.tasm.provider.AbsTemplateProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class DemoTemplateProvider(val context: Context) : AbsTemplateProvider() {


    companion object {
        private const val TAG = "DemoTemplateProvider"
    }

    private var app: Context = context.applicationContext

    override fun loadTemplate(uri: String, callback: Callback) {
        Log.i(TAG, "loadTemplate: uri = $uri")
        thread {
            runCatching {
                val data = processUri(uri)
                callback.onSuccess(data)
            }.onSuccess {
                Log.i(TAG, "loadTemplate: uri = $uri, success")
            }.onFailure {
                Log.e(TAG, "loadTemplate: uri = $uri, failed", it)
                callback.onFailed(it.message)
            }
        }
    }

    private fun processUri(uri: String): ByteArray {
        Log.i(TAG, "processUri: uri = $uri")
        // 这里可以添加更多处理逻辑

        when {
            uri.startsWith("http://") || uri.startsWith("https://") -> {
                // 如果是网络文件，直接显示
                Log.i(TAG, "processUri: loading from network")

                val req = Request.Builder()
                    .url(uri)
                    .build()
                val client = OkHttpClient.Builder().build()
                val resp = client.newCall(req).execute()
                return resp.body?.bytes() ?: throw Exception("Response body is null")
            }

            else -> {
                Log.i(TAG, "processUri: loading from uri")
                return uri.uriInputStream(app)?.readBytes()
                    ?: throw Exception("Can not open input stream from uri: $uri")
            }
        }
    }
}