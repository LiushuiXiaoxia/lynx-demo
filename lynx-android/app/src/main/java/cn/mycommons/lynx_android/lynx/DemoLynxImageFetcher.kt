package cn.mycommons.lynx_android.lynx

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.net.toUri
import cn.mycommons.lynx_android.util.uriInputStream
import com.lynx.tasm.image.ImageContent
import com.lynx.tasm.image.model.AnimationListener
import com.lynx.tasm.image.model.ImageInfo
import com.lynx.tasm.image.model.ImageLoadListener
import com.lynx.tasm.image.model.ImageRequestInfo
import com.lynx.tasm.image.model.LynxImageFetcher
import java.io.File
import java.io.InputStream
import kotlin.concurrent.thread

enum class ImageSourceType {
    ASSETS, // 资源文件
    FILE, // 本地文件
    HTTP, // 网络文件
    UNKNOWN // 未知类型
}

class DemoLynxImageFetcher(val context: Context, val rootUri: String) : LynxImageFetcher {

    companion object {
        private const val TAG = "DemoLynxImageFetcher"
    }

    private var app: Context = context.applicationContext

    override fun loadImage(
        imageRequestInfo: ImageRequestInfo,
        loadListener: ImageLoadListener,
        animationListener: AnimationListener?,
        context: Context?,
    ) {
        Log.i(TAG, "loadImage: url = ${imageRequestInfo.url}")

        val imageUri = if (imageRequestInfo.url.startsWith("/static/image/")) {
            val idx = rootUri.lastIndexOf("/")
            val imageUri = rootUri.substring(0, idx) + imageRequestInfo.url
            Log.i(TAG, "loadImage: imageUri = $imageUri")
            imageUri
        } else {
            val imageUri = imageRequestInfo.url
            Log.i(TAG, "loadImage: imageUri = $imageUri")
            imageUri
        }
        thread {
            val image = imageUri.uriInputStream(app)
            val bitmap = BitmapFactory.decodeStream(image)
            loadListener.onSuccess(
                ImageContent(bitmap),
                imageRequestInfo,
                ImageInfo(bitmap.width, bitmap.height, false)
            )
        }
    }

    fun loadImage(
        imageUri: String,
        imageRequestInfo: ImageRequestInfo,
        loadListener: ImageLoadListener,
        animationListener: AnimationListener?,
        context: Context?,
    ) {
        val image = when {
            imageUri.startsWith("http://") || imageUri.startsWith("https://") -> {
                val image = uriToImageStream(imageUri, ImageSourceType.HTTP)
                image
            }

            rootUri.startsWith("file://") -> {
                val image = uriToImageStream(imageUri, ImageSourceType.FILE)
                image
            }

            rootUri.startsWith("assets://") -> {
                uriToImageStream(imageUri, ImageSourceType.ASSETS)
            }

            else -> {
                // 如果是资源文件，直接显示
                throw RuntimeException("not support")
            }
        }

        val bitmap = BitmapFactory.decodeStream(image)
        loadListener.onSuccess(
            ImageContent(bitmap),
            imageRequestInfo,
            ImageInfo(bitmap.width, bitmap.height, false)
        )
    }


    private fun uriToImageStream(uri: String, type: ImageSourceType): InputStream {
        when (type) {
            ImageSourceType.ASSETS -> {
                val image = app.assets.open(uri.removePrefix("assets://"))
                return image
            }

            ImageSourceType.FILE -> {
                return context.contentResolver.openInputStream(uri.toUri())
                    ?: throw Exception("Open input stream failed")
            }

            ImageSourceType.HTTP -> TODO()
            ImageSourceType.UNKNOWN -> TODO()
        }
    }

    override fun releaseImage(imageRequestInfo: ImageRequestInfo) {
    }
}