package com.kroknes.android.animations.views;

import com.kroknes.android.R;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ObjectAnimatorPresenter {

    private static final float SCALE_X = .05f;
    private static final float TRANSLATION_X = 100f;
    private static final int COLOR_START = Color.rgb(224, 255, 255);
    private static final int COLOR_END = Color.BLACK;
    private final FrameLayout imagePane;
    private final ImageView image;

    public ObjectAnimatorPresenter(View layout) {
        imagePane = (FrameLayout) layout.findViewById(R.id.oa_image_pane);
        image = (ImageView) layout.findViewById(R.id.image);
        layout.findViewById(R.id.scale_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateScaleX(SCALE_X);
            }
        });
        layout.findViewById(R.id.translation_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateTranslationX(TRANSLATION_X);
            }
        });
        layout.findViewById(R.id.background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateImagePaneBackgroundColor();
            }
        });
        layout.findViewById(R.id.rotation_x_y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRotationYAndX();
            }
        });
        layout.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void animateScaleX(float scaleX) {
        float currentScaleX = image.getScaleX();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(image,
                                                               View.SCALE_X,
                                                               currentScaleX,
                                                               currentScaleX + scaleX);
        scaleXAnimator.setDuration(1000);
        scaleXAnimator.start();
    }

    private void animateTranslationX(float translationX) {
        float currentTranslationX = image.getTranslationX();
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(image,
                                                                     View.TRANSLATION_X,
                                                                     currentTranslationX,
                                                                     currentTranslationX + translationX);
        translationXAnimator.setDuration(1000);
        translationXAnimator.start();
    }

    private void animateImagePaneBackgroundColor() {
        ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(imagePane,
                                                                         "backgroundColor",
                                                                         new ArgbEvaluator(),
                                                                         COLOR_START,
                                                                         COLOR_END);
        backgroundColorAnimator.setDuration(2000);
        backgroundColorAnimator.start();
    }

    private void animateRotationYAndX() {
        float currentRotationX = image.getRotationX();
        PropertyValuesHolder rotationX = PropertyValuesHolder.ofFloat(View.ROTATION_X,
                                                                      currentRotationX,
                                                                      currentRotationX + 20f);
        float currentRotationY = image.getRotationY();
        PropertyValuesHolder rotationY = PropertyValuesHolder.ofFloat(View.ROTATION_Y,
                                                                      currentRotationY,
                                                                      currentRotationY + 20f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(image, rotationX, rotationY);
        animator.setDuration(2000);
        animator.start();

    }

    private void reset() {
        imagePane.setBackgroundColor(COLOR_START);
        imagePane.invalidate();
        image.setRotationX(0f);
        image.setRotationY(0f);
        image.setTranslationX(0f);
        image.setScaleX(1f);
        image.invalidate();
    }


}
