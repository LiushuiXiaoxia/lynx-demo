package cn.mycommons.lynx_android

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cn.mycommons.lynx_android.lynx.DemoLynxImageFetcher
import cn.mycommons.lynx_android.lynx.DemoTemplateProvider
import com.lynx.tasm.LynxView
import com.lynx.tasm.LynxViewBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val lynxView = buildLynxView()
        findViewById<FrameLayout>(R.id.main).addView(lynxView)

        val uri = "main.lynx.bundle"
        lynxView.renderTemplateUrl(uri, "")
    }

    private fun buildLynxView(): LynxView {
        val viewBuilder: LynxViewBuilder = LynxViewBuilder()
        viewBuilder.setImageFetcher(DemoLynxImageFetcher(this))
        viewBuilder.setTemplateProvider(DemoTemplateProvider(this))
        return viewBuilder.build(this)
    }
}