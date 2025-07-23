package cn.mycommons.lynx_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cn.mycommons.lynx_android.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.edtUri.setText("assets://main.lynx.bundle")
//        binding.edtUri.setText("https://macross-jks.bilibili.co/archive/fawkes/pack/infra_ci_lynx/19940111/bundle.zip")
//        binding.edtUri.setText("https://macross-jks.bilibili.co/archive/fawkes/pack/infra_ci_lynx/19940111/main.lynx.bundle")

        binding.btnScan.setOnClickListener {
            // 启动扫码页面（用 ZXing）
            val intent = IntentIntegrator(this)
                .apply {
                    setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    setPrompt("请对准二维码")
                    setBeepEnabled(true)
                    setBarcodeImageEnabled(true)
                    // captureActivity = CustomCaptureActivity::class.java // 可选
                }
                .createScanIntent()

            scanLauncher.launch(intent)
        }
        binding.btnGo.setOnClickListener {
            val uri = binding.edtUri.text.toString()
            if (uri.isBlank()) {
                Toast.makeText(this, "uri is null", Toast.LENGTH_SHORT).show()
            } else {
                gotoLynxActivity(uri)
            }
        }
    }

    fun gotoLynxActivity(uri: String) {
        LynxActivity.gotoLynxActivity(this, uri)
    }

    // 注册回调 launcher
    private val scanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
        if (intentResult != null) {
            if (intentResult.contents != null) {
                Toast.makeText(this, "扫码结果: ${intentResult.contents}", Toast.LENGTH_SHORT).show()
                gotoLynxActivity(intentResult.contents)
            } else {
                Toast.makeText(this, "扫码取消", Toast.LENGTH_SHORT).show()
            }
        }
    }
}