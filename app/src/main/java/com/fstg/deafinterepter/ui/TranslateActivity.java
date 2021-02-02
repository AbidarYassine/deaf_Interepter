package com.fstg.deafinterepter.ui;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.ml.Model;
import com.fstg.deafinterepter.utils.CategoryMl;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TranslateActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG = "MainActivity";


    private Mat mRgba;
    private CameraBridgeViewBase mOpenCvCameraView;
    private static final int BATCH_SIZE = 1;
    private static final int PIXEL_SIZE = 3;

//    private static final int IMAGE_MEAN = 128;
//    private static final float IMAGE_STD = 128.0f;

    protected Interpreter tflite;
    private MappedByteBuffer tfliteModel;
    private TensorImage inputImageBuffer;
    private int imageSizeX;
    private int imageSizeY;
    private TensorBuffer outputProbabilityBuffer;
    private TensorProcessor probabilityProcessor;
    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;
    private static final float PROBABILITY_MEAN = 0.0f;
    private static final float PROBABILITY_STD = 255.0f;
    private Bitmap bitmap;
    private List<String> labels;
    TextView classitext;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_translate);
        setupView();
//        try {
//            tflite = new Interpreter(loadmodelfile(this));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_id);
//        mOpenCvCameraView.setCameraIndex(1);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
//        mOpenCvCameraView.setLayoutDirection(View.IMPORTANT_FOR_AUTOFILL_AUTO);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    private void setupView() {
        classitext = findViewById(R.id.classitext);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(getApplicationContext(), "There's a problem, yo!", Toast.LENGTH_SHORT).show();
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_2, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(mLoaderCallback.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }


    @Override
    public void onCameraViewStopped() {

        mRgba.release();
    }


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
//        int imageTensorIndex = 0;
//        int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape(); // {1, height, width, 3}
//        imageSizeY = imageShape[1];
//        imageSizeX = imageShape[2];
//        DataType imageDataType = tflite.getInputTensor(imageTensorIndex).dataType();
//
//        int probabilityTensorIndex = 0;
//        int[] probabilityShape =
//                tflite.getOutputTensor(probabilityTensorIndex).shape(); // {1, NUM_CLASSES}
//        DataType probabilityDataType = tflite.getOutputTensor(probabilityTensorIndex).dataType();
//
//        inputImageBuffer = new TensorImage(imageDataType);
//        outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType);
//        probabilityProcessor = new TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build();
//        Bitmap bitmap = createBitmapfromMat(mRgba);
//        inputImageBuffer = loadImage(bitmap);
//
//        tflite.run(inputImageBuffer.getBuffer(), outputProbabilityBuffer.getBuffer().rewind());
//        showresult();
        try {
            Bitmap bitmap = createBitmapfromMat(mRgba);
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorImage image = TensorImage.fromBitmap(bitmap);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(image);
            List<Category> probability = outputs.getProbabilityAsCategoryList();
            CategoryMl categories = new CategoryMl();
            float max = probability.get(0).getScore();
            for (Category category : probability) {
                if (category.getScore() > max) {
                    categories.setLabel(category.getLabel());
                    categories.setScore(category.getScore());
                } else {
                    categories.setLabel(probability.get(0).getLabel());
                    categories.setScore(probability.get(0).getScore());
                }
            }
            runOnUiThread(() -> {
                classitext.setText(categories.getLabel());
            });
            Log.i("TAGe", probability.toString());

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            Log.i("TAGe", e.getLocalizedMessage());
        }

        return mRgba;
    }

    public Bitmap createBitmapfromMat(Mat snap) {
        Bitmap bp = Bitmap.createBitmap(snap.cols(), snap.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(snap, bp);
        return bp;
    }

    private TensorImage loadImage(final Bitmap bitmap) {
        // Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap);

        // Creates processor for the TensorImage.
        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        // TODO(b/143564309): Fuse ops inside ImageProcessor.
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                        .add(getPreprocessNormalizeOp())
                        .build();
        return imageProcessor.process(inputImageBuffer);
    }

    private MappedByteBuffer loadmodelfile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd("saved_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength);
    }

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }

    private TensorOperator getPostprocessNormalizeOp() {
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    private void showresult() {

        try {

            labels = FileUtil.loadLabels(this, "labels.txt");
            Log.i("TAG Label", labels.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Float> labeledProbability =
                new TensorLabel(labels,
                        probabilityProcessor.process(outputProbabilityBuffer))
                        .getMapWithFloatValue();
        Log.i("TAG Result", labeledProbability.toString());
        float maxValueInMap = (Collections.max(labeledProbability.values()));

        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
//            runOnUiThread(() -> {
//                classitext.setText(entry.getKey());
//            });
            if (entry.getValue() == maxValueInMap) {
                runOnUiThread(() -> {
                    classitext.setText(entry.getKey());
                });
            }
        }
    }

}