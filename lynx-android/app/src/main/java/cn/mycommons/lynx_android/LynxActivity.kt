package cn.mycommons.lynx_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cn.mycommons.lynx_android.lynx.DemoLynxImageFetcher
import cn.mycommons.lynx_android.lynx.DemoTemplateProvider
import com.lynx.tasm.LynxView
import com.lynx.tasm.LynxViewBuilder

class LynxActivity : AppCompatActivity() {

    companion object {

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
        showLynxView(uri)
    }

    private fun showLynxView(uri: String) {
        val lynxView = buildLynxView()
        findViewById<FrameLayout>(R.id.main).addView(lynxView)
        lynxView.renderTemplateUrl(uri, "")
    }

    private fun buildLynxView(): LynxView {
        val viewBuilder: LynxViewBuilder = LynxViewBuilder()
        viewBuilder.setImageFetcher(DemoLynxImageFetcher(this))
        viewBuilder.setTemplateProvider(DemoTemplateProvider(this))
        return viewBuilder.build(this)
    }
}