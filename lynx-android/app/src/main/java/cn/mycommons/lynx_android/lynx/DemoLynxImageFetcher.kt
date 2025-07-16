package cn.mycommons.lynx_android.lynx

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.lynx.tasm.image.ImageContent
import com.lynx.tasm.image.model.AnimationListener
import com.lynx.tasm.image.model.ImageInfo
import com.lynx.tasm.image.model.ImageLoadListener
import com.lynx.tasm.image.model.ImageRequestInfo
import com.lynx.tasm.image.model.LynxImageFetcher

class DemoLynxImageFetcher(context: Context) : LynxImageFetcher {

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
        val image = app.assets.open(imageRequestInfo.url.substring(1))
        val bitmap = BitmapFactory.decodeStream(image)
        loadListener.onSuccess(
            ImageContent(bitmap),
            imageRequestInfo,
            ImageInfo(bitmap.width, bitmap.height, false)
        )
    }

    override fun releaseImage(imageRequestInfo: ImageRequestInfo) {
    }
}