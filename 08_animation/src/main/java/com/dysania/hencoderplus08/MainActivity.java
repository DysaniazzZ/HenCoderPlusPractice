package com.dysania.hencoderplus08;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraView cameraView = findViewById(R.id.camera_view);
//        ImageView imageView = findViewById(R.id.image_view);
//        PointView pointView = findViewById(R.id.point_view);

        // 同一个 View 多个属性依次改变
        ObjectAnimator bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView, "bottomFlip", 45);
        ObjectAnimator flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation", 270);
        ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(cameraView, "topFlip", -45);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator);
        animatorSet.setStartDelay(1000);
        animatorSet.setDuration(1500);
        animatorSet.start();

        // 同一个 View 多个属性同时改变
//        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 45);
//        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270);
//        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -45);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(cameraView, bottomFlipHolder, flipRotationHolder, topFlipHolder);
//        objectAnimator.setStartDelay(1000);
//        objectAnimator.setDuration(1500);
//        objectAnimator.start();

        // 同一个 View 同一个属性阶段改变
//        float length = UIUtil.dp2px(250);
//        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
//        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length);
//        Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length);
//        Keyframe keyframe4 = Keyframe.ofFloat(1, 1 * length);
//
//        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, valuesHolder);
//        objectAnimator.setStartDelay(1000);
//        objectAnimator.setDuration(1500);
//        objectAnimator.start();

        // 插值器
//        imageView.animate()
//                .translationX(UIUtil.dp2px(400))
//                .setStartDelay(1000)
//                .setDuration(500)
//                .setInterpolator(new AccelerateInterpolator())
//                .start();

        // 自定义属性
//        Point targetPoint = new Point((int) UIUtil.dp2px(300), (int) UIUtil.dp2px(300));
//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(pointView, "point", new PointEvaluator(), targetPoint);
//        objectAnimator.setStartDelay(1000);
//        objectAnimator.setDuration(1500);
//        objectAnimator.start();
    }

//    class PointEvaluator implements TypeEvaluator<Point> {
//
//        @Override
//        public Point evaluate(float fraction, Point startValue, Point endValue) {
//            float x = startValue.x + (endValue.x - startValue.x) * fraction;
//            float y = startValue.x + (endValue.x - startValue.x) * fraction;
//            return new Point((int) x, (int) y);
//        }
//    }
}
