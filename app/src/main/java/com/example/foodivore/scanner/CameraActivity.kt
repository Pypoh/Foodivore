package com.example.foodivore.scanner

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.foodivore.R
import com.example.foodivore.utils.customview.OverlayView

abstract class CameraActivity : Activity(), ImageReader.OnImageAvailableListener {
    companion object {
        private const val PERMISSIONS_REQUEST = 1
        private const val LOGGING_TAG = "objdetector"
    }

    private var handler: Handler? = null
    private var handlerThread: HandlerThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.activity_camera)

        if (hasPermission()) {
            setFragment()
        } else {
            requestPermission()
        }

    }

    override fun onResume() {
        super.onResume()

        handlerThread = HandlerThread("inference")
        handlerThread!!.start()
        handler = Handler(handlerThread!!.looper)
    }

    override fun onPause() {
        if (!isFinishing) {
            finish()
        }

        handlerThread!!.quitSafely()
        try {
            handlerThread!!.join()
            handlerThread = null
            handler = null
        } catch (ex: InterruptedException) {
            Log.e(LOGGING_TAG, "Exception: " + ex.message)
        }

        super.onPause()
    }

    @Synchronized
    protected open fun runInBackground(runnable: Runnable?) {
        if (handler != null) {
            handler!!.post(runnable!!)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    setFragment()
                } else {
                    requestPermission()
                }
            }
        }

    }

    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        } else {
            true
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ) {
                Toast.makeText(
                    this@CameraActivity,
                    "Camera AND storage permission are required for this app", Toast.LENGTH_LONG
                ).show()
            }
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), PERMISSIONS_REQUEST
            )
        }
    }

    protected open fun setFragment() {
        val cameraConnectionFragment = CameraConnectionFragment()

        val connCallback = object : CameraConnectionFragment.ConnectionCallback {

            override fun onPreviewSizeChosen(size: Size, rotation: Int) {
                previewHeight = size.height
                previewWidth = size.width
                this@CameraActivity.onPreviewSizeChosen(size, rotation)
            }
        }

        cameraConnectionFragment.addConnectionListener()
        cameraConnectionFragment.addImageAvailableListener(this)
        fragmentManager
            .beginTransaction()
            .replace(R.id.container, cameraConnectionFragment)
            .commit()
    }

    fun requestRender() {
        val overlay: OverlayView = findViewById<View>(R.id.overlay) as OverlayView
        overlay.postInvalidate()
    }

    fun addCallback(callback: OverlayView.DrawCallback?) {
        val overlay: OverlayView = findViewById<View>(R.id.overlay) as OverlayView
        overlay.addCallback(callback)
    }

    protected abstract fun onPreviewSizeChosen(size: Size?, rotation: Int)
}