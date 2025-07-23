package cn.mycommons.lynx_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cn.mycommons.lynx_android.lynx.DemoLynxImageFetcher
import cn.mycommons.lynx_android.lynx.DemoTemplateProvider
import cn.mycommons.lynx_android.lynx.downloadAndUnzip
import com.lynx.tasm.LynxView
import com.lynx.tasm.LynxViewBuilder
import kotlin.concurrent.thread

class LynxActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "LynxActivity"

        const val EXTRA_URI = "EXTRA_URI"

        fun gotoLynxActivity(context: Context, uri: String) {
            val intent = Intent(context, LynxActivity::class.java)
            intent.putExtra(EXTRA_URI, uri)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lynx)

        val uri = intent.getStringExtra(EXTRA_URI)
        if (uri.isNullOrBlank()) {
            Toast.makeText(this, "uri is null", Toast.LENGTH_SHORT).show()
            return
        }
        processUri(uri)
    }

    private fun processUri(uri: String) {
        when {
            uri.startsWith("http://") || uri.startsWith("https://") -> {
                if (uri.endsWith(".zip")) {
                    // 如果是 zip 文件，下载并解压
                    thread {
                        val zipFile = downloadAndUnzip(this, uri)
                        val uri2 = "file://${zipFile.absolutePath}/main.lynx.bundle"

                        runOnUiThread {
                            showLynxView(uri2)
                        }
                    }
                } else {
                    // 如果是网络文件，直接显示
                    showLynxView(uri)
                }
            }

            uri.startsWith("file://") -> {
                showLynxView(uri)
            }

            uri.startsWith("assets://") -> {
                // 如果是 assets 文件，去掉前缀
                showLynxView(uri)
            }

            else -> {
                showLynxView(uri)
            }
        }
    }

    private fun showLynxView(uri: String) {
        Log.i(TAG, "showLynxView: uri = $uri")
        val viewBuilder: LynxViewBuilder = LynxViewBuilder()
        viewBuilder.setTemplateProvider(DemoTemplateProvider(this))
        viewBuilder.setImageFetcher(DemoLynxImageFetcher(this, uri))
        val lynxView = viewBuilder.build(this)

        findViewById<FrameLayout>(R.id.main).addView(lynxView)
        // 如果是本地文件，直接使用文件路径
        lynxView.renderTemplateUrl(uri, "")
    }
}