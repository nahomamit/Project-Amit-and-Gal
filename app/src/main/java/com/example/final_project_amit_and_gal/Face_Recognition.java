package com.example.final_project_amit_and_gal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import java.net.URI;
import java.util.ArrayList;

public class Face_Recognition extends SharedFunctions implements CameraBridgeViewBase.CvCameraViewListener2 {

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

        Intent intent = getIntent();
        final ArrayList<String> name_ids = intent.getStringArrayListExtra("name");

        String name_id = name_ids.get(0);
       // String name = intent.getStringExtra("name");
        TextView tv = findViewById(R.id.task_n);
        tv.setText(name_id);

        javaCameraView=(JavaCameraView)findViewById(R.id.my_camera_view);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraIndex(2);
        //javaCameraView.setMaxFrameSize(500,500);
        final VideoView videoView = findViewById(R.id.video_view);
        String videoPath = nameToUTL("");

        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
                videoView.start();

                final Button next =(Button) findViewById(R.id.next_exc);
                if(name_ids.size() == 1){
                    next.setText("סיים תרגול");
                } else {
                    next.setText("עבור לתרגול הבא");
                }
                next.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(getApplicationContext(), FaceCheckTransfer.class);
                        intent2.putStringArrayListExtra("name", name_ids);
                        startActivity(intent2);
                    }

        });
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // TODO: Your application init goes here.
                    next.setEnabled(true);

                }
            }, 15000);


    }

   public String nameToUTL(String name) {
        return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.video;
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
       // Log.i("MYMAT", String.valueOf(mRGBA.dims()));

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