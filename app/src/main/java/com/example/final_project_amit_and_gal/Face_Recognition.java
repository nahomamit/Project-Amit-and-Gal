package com.example.final_project_amit_and_gal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.core.content.ContextCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
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
import java.util.HashMap;
import java.util.Map;

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

        Map<String, String[]> nameUrlTaskMap = getMap();
        Intent intent = getIntent();
        final ArrayList<String> name_ids = intent.getStringArrayListExtra("name");

        String name_id = name_ids.get(0);
        TextView tv = findViewById(R.id.task_n);
        tv.setText(nameUrlTaskMap.get(name_id)[1]);

        javaCameraView=(JavaCameraView)findViewById(R.id.my_camera_view);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraIndex(1);
        final VideoView videoView = findViewById(R.id.video_view);
        //String videoPath = nameToUTL(name_id);
        String videoPath = nameUrlTaskMap.get(name_id)[0];
        Log.i("URL", videoPath);
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
        Core.flip(mRGBA, mRGBA, 1);

        /*mGREY = inputFrame.gray();

        MatOfRect faceDetections = new MatOfRect();
        face_detector.detectMultiScale(mGREY,faceDetections);
        for(Rect rect: faceDetections.toArray())
        {
            Imgproc.rectangle(mRGBA,new Point(rect.x,rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255,0,0));
        }*/

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


    private Map<String, String[]> getMap() {
        Map<String, String[]> myMap = new HashMap<String, String[]>();
        myMap.put(getString(R.string.lips_1), new String[]{"android.resource://" + getPackageName() + "/" + "/" + R.raw.lips1, getString(R.string.lips_1_task)});
        myMap.put(getString(R.string.lips_2), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips2, getString(R.string.lips_2_task)});
        myMap.put(getString(R.string.lips_3), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips3, getString(R.string.lips_3_task)});
        myMap.put(getString(R.string.lips_5), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips5, getString(R.string.lips_5_task)});
        myMap.put(getString(R.string.lips_6), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips6, getString(R.string.lips_6_task)});
        myMap.put(getString(R.string.lips_7), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips7, getString(R.string.lips_7_task)});
        myMap.put(getString(R.string.lips_8), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips8, getString(R.string.lips_8_task)});
        myMap.put(getString(R.string.lips_9), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips9, getString(R.string.lips_9_task)});
        myMap.put(getString(R.string.lips_10), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips10, getString(R.string.lips_10_task)});
        myMap.put(getString(R.string.lips_13), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips13, getString(R.string.lips_13_task)});
        myMap.put(getString(R.string.lips_14), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips14, getString(R.string.lips_14_task)});
        myMap.put(getString(R.string.lips_16), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.lips16, getString(R.string.lips_16_task)});

        myMap.put(getString(R.string.jaw_1), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.jaws1, getString(R.string.jaw_1_task)});

        myMap.put(getString(R.string.eyes_1), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.eyes1, getString(R.string.eyes_1_task)});
        myMap.put(getString(R.string.eyes_2), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.eyes2, getString(R.string.eyes_2_task)});
        myMap.put(getString(R.string.eyes_3), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.eyes3, getString(R.string.eyes_3_task)});

        myMap.put(getString(R.string.tongue_1), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue1, getString(R.string.tongue_1_task)});
        myMap.put(getString(R.string.tongue_2), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue2, getString(R.string.tongue_2_task)});
        myMap.put(getString(R.string.tongue_3), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue3, getString(R.string.tongue_3_task)});
        myMap.put(getString(R.string.tongue_4), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue4, getString(R.string.tongue_4_task)});
        myMap.put(getString(R.string.tongue_6), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue6, getString(R.string.tongue_6_task)});
        myMap.put(getString(R.string.tongue_7), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue7, getString(R.string.tongue_7_task)});
        myMap.put(getString(R.string.tongue_8), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue8, getString(R.string.tongue_8_task)});
        myMap.put(getString(R.string.tongue_9), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue9, getString(R.string.tongue_9_task)});
        myMap.put(getString(R.string.tongue_10), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue10, getString(R.string.tongue_10_task)});
        myMap.put(getString(R.string.tongue_11), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue11, getString(R.string.tongue_11_task)});
        myMap.put(getString(R.string.tongue_12), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue12, getString(R.string.tongue_12_task)});
        myMap.put(getString(R.string.tongue_16), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue16, getString(R.string.tongue_16_task)});
        myMap.put(getString(R.string.tongue_24), new String[]{"android.resource://" + getPackageName() + "/" + R.raw.tongue24, getString(R.string.tongue_24_task)});

        return myMap;
    }
}