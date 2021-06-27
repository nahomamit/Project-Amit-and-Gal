package com.example.final_project_amit_and_gal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Face_Recognition extends SharedFunctions implements CameraBridgeViewBase.CvCameraViewListener2 {

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
        TextView tv = findViewById(R.id.task_n);
        tv.setText(name_id);

        javaCameraView=(JavaCameraView)findViewById(R.id.my_camera_view);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraIndex(2);
        final VideoView videoView = findViewById(R.id.video_view);
        String videoPath = nameToUTL(name_id);

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
       Log.i("name",name);
        switch (name) {
            case "שפתיים1" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips1;
            case "שפתיים2" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips2;
            case "שפתיים3" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips3;
            case "שפתיים4" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips5;
            case "שפתיים5" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips6;
            case "שפתיים6" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips7;
            case "שפתיים7" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips8;
            case "שפתיים8" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips9;
            case "שפתיים9" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips10;
            case "שפתיים10" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips13;
            case "שפתיים11" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips14;
            case "שפתיים12" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.lips16;
            case "לשון1" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue1;
            case "לשון2" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue2;
            case "לשון3" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue3;
            case "לשון4" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue4;
            case "לשון5" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue6;
            case "לשון6" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue7;
            case "לשון7" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue8;
            case "לשון8" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue9;
            case "לשון9" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue10;
            case "לשון10" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue11;
            case "לשון11" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue12;
            case "לשון12" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue16;
            case "לשון13" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.tongue24;

            case "לסת1" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.jaws1;
            case "עיניים1" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.eyes1;
            case "עיניים2" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.eyes2;
            case "עיניים3" :  return  "android.resource://com.example.final_project_amit_and_gal/" + R.raw.eyes3;

       }
       Log.i("fail","failed");
       return "";
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
            try {
                baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
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