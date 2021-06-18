package com.example.final_project_amit_and_gal
/*
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform


class Face_Recognition : AppCompatActivity() {
    /*private val PERMISSION_REQUEST_CODE = 200
    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null


     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_recognition)

        initPython()
        Log.i("Python", getPythonHelloWorld())
    }

    private fun getPythonHelloWorld(): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("face_proj/face_app")
        return pythonFile.callAttr("face_reco").toString()
    }

    private fun initPython() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }
}

/*
        // Create an instance of Camera
        mCamera = getCameraInstance()

        mPreview = mCamera?.let {
            // Create our Preview view
            CameraPreview(this, it)
        }

        // Set the Preview view as the content of our activity.
        mPreview?.also {
            val preview: FrameLayout = findViewById(R.id.camera_preview)
            preview.addView(it)
        }

        Log.i("FACE RECO", "START")
    }



    /** Check if this device has a camera */
    private fun checkCameraHardware(context: Context): Boolean {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true
        } else {
            // no camera on this device
            return false
        }
    }

    /** A safe way to get an instance of the Camera object. */
    fun getCameraInstance(): Camera? {
        return try {
            openFrontFacingCamera() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }

    private fun openFrontFacingCamera(): Camera? {
        var cameraCount = 0
        var cam: Camera? = null
        val cameraInfo = Camera.CameraInfo()
        cameraCount = Camera.getNumberOfCameras()
        Log.i("TAG", cameraCount.toString())
        for (camIdx in 0 until cameraCount) {
            Camera.getCameraInfo(camIdx, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    if (!checkPermission()) {
                        requestPermission()
                    }
                    cam = Camera.open(camIdx)
                } catch (e: RuntimeException) {
                    Log.e("TAG", "Camera failed to open: " + e.localizedMessage)
                }
            }
        }
        return cam
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            false
        } else true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),PERMISSION_REQUEST_CODE
        )
    }

}

 */