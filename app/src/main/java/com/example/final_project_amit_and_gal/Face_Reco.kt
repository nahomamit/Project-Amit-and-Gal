package com.example.final_project_amit_and_gal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.SurfaceView
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.android.JavaCameraView
import org.opencv.core.Mat



class Face_Reco : AppCompatActivity(),CameraBridgeViewBase.CvCameraViewListener2 {
    lateinit var cam : JavaCameraView
    lateinit var mRGBA : Mat
    lateinit var mRGBAT : Mat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face__reco)

        if (OpenCVLoader.initDebug()) {
            Log.i("TAGTAG", "yes")
        } else {
            Log.e("TAGTAG", "NO")
        }
        cam = findViewById(R.id.my_camera_view)
        cam.visibility = SurfaceView.VISIBLE
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
            TODO("Not yet implemented")
    }

    override fun onCameraViewStopped() {
        TODO("Not yet implemented")
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        TODO("Not yet implemented")
    }
}