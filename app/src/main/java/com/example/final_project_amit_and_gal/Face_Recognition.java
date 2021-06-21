package com.example.final_project_amit_and_gal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.SurfaceView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Face_Recognition extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static String TAG = "MainActivity";
    CascadeClassifier face_detector;
    JavaCameraView javaCameraView;
    Mat mRGBA,mGREY;
    File cascFile;
    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(Face_Recognition.this) {
        @Override
        public void onManagerConnected(int status) throws IOException {
            switch (status) {
                case BaseLoaderCallback.SUCCESS: {
                    InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
                    File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                    cascFile= new File(cascadeDir,"haarcascade_frontalface_alt2.xml");
                    FileOutputStream fos = new FileOutputStream(cascFile);
                    byte[] buffer = new byte[4096];
                    int byteRead;
                    while ((byteRead = is.read(buffer)) !=-1 ){
                        fos.write(buffer,0,byteRead);
                    }
                    is.close();
                    fos.close();

                    face_detector = new CascadeClassifier(cascFile.getAbsolutePath());
                    if(face_detector.empty())
                    {
                        face_detector = null;
                    }
                    else
                        cascadeDir.delete();
                    javaCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                    break;
                }
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);
        javaCameraView=(JavaCameraView)findViewById(R.id.my_camera_view);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraIndex(2);
        //javaCameraView.setMaxFrameSize(500,500);

        /*
        if(!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this, baseCallback);

        } else {
            Log.i(TAG, "not good");
        }

         */

        /*javaCameraView=(JavaCameraView)findViewById(R.id.my_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(Face_Recognition.this);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new ObjectDetection().run(new String[]{"src/main/assets/haarcascade_frontalface_default.xml", "src/main/assets/haarcascade_eye.xml"});
         */
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBA= new Mat();
        mGREY = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRGBA.release();
        mGREY.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRGBA= inputFrame.rgba();
        mGREY = inputFrame.gray();
        //Core.flip(mRGBA.t(), mRGBA, -1);
        //Core.flip(mGREY.t(), mGREY, -1);
        Log.i("MYMAT", String.valueOf(mRGBA.dims()));

        MatOfRect faceDetections = new MatOfRect();
        face_detector.detectMultiScale(mGREY,faceDetections);
        for(Rect rect: faceDetections.toArray())
        {
            Imgproc.rectangle(mRGBA,new Point(rect.x,rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255,0,0));
        }

        return mRGBA;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(javaCameraView != null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(javaCameraView != null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(OpenCVLoader.initDebug()) {
            Log.i(TAG,"good");
            try {
                baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "not good");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this, baseLoaderCallback);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_face_recognition);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_first_time_enter);
        }
    }


}