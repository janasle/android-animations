package com.kroknes.android.animations.views;

import com.kroknes.android.R;

import android.view.View;
import android.widget.ImageView;

public class ViewPropertyAnimatorPresenter {

    private final ImageView image;

    public ViewPropertyAnimatorPresenter(View layout) {
        image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.nibbler);
        layout.findViewById(R.id.alpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateAlpha();
            }
        });
        layout.findViewById(R.id.scale_y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateScaleY();
            }
        });
        layout.findViewById(R.id.translation_y_and_rotation_y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateTranslationYAndRotationY();
            }
        });
        layout.findViewById(R.id.location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateLocation();
            }
        });
        layout.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void animateAlpha() {
        image.animate().alpha(0f).setDuration(2000);
    }

    private void animateScaleY() {
        image.animate().scaleYBy(.15f).setDuration(1000);
    }

    private void animateTranslationYAndRotationY() {
        image.animate().translationYBy(50f).rotationYBy(30f).setDuration(2000);
    }

    private void animateLocation() {
        image.animate().translationXBy(50f).translationYBy(50f).setDuration(2000);
    }

    private void reset() {
        image.animate().alpha(1f).scaleY(1f).translationX(0f).translationY(0f).rotationY(0f);
    }
}
