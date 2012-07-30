package com.kroknes.android.animations.views;

import com.kroknes.android.R;

import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

/**
 * http://android-developers.blogspot.com/2011/02/animation-in-honeycomb.html
 * http://developer.android.com/guide/topics/graphics/prop-animation.html#value-animator
 * <p/>
 * ValueAnimator is the main workhorse of the entire system. It runs the internal timing loop that causes all of a process's
 * animations to calculate and set values and has all of the core functionality that allows it to do this, including the timing
 * details of each animation, information about whether an animation repeats, listeners that receive update events, and the
 * capability of evaluating different types of values (see TypeEvaluator for more on this). There are two pieces to animating
 * properties: calculating the animated values and setting those values on the object and property in question. ValueAnimator
 * takes care of the first part; calculating the values.
 * <p/>
 * Usage: when the object doesn't expose a setter function for the property you want to animate
 */
public class ValueAnimatorPresenter {

    private static final int DEFAULT_PADDING = 50;
    private static final int DEFAULT_MARGIN = 50;
    private final FrameLayout imagePane;

    public ValueAnimatorPresenter(View layout) {
        imagePane = (FrameLayout) layout.findViewById(R.id.va_image_pane);
        ((ImageView)layout.findViewById(R.id.image)).setImageResource(R.drawable.nibbler);
        layout.findViewById(R.id.padding_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatePaddingLeft(DEFAULT_PADDING);
            }
        });
        layout.findViewById(R.id.padding_multiple_left_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatePaddingLeftAndRightSimultaneously();
            }
        });
        layout.findViewById(R.id.margins_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateMarginBottom(50);
            }
        });
        layout.findViewById(R.id.margins_multiple_top_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateMarginsTopAndBottomSimultaneously();
            }
        });
        layout.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void animatePaddingLeft(int dp) {
        int currentPaddingLeft = imagePane.getPaddingLeft();
        ValueAnimator paddingLeftAnimator = ValueAnimator.ofInt(currentPaddingLeft, currentPaddingLeft + dp);
        paddingLeftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imagePane.setPadding((Integer) animation.getAnimatedValue(),
                                     imagePane.getPaddingTop(),
                                     imagePane.getPaddingRight(),
                                     imagePane.getPaddingBottom());
                imagePane.invalidate();
            }
        });
        paddingLeftAnimator.setDuration(1000);
        paddingLeftAnimator.start();
    }

    private void animatePaddingLeftAndRightSimultaneously() {
        int currentPaddingLeft = imagePane.getPaddingLeft();
        int currentPaddingRight = imagePane.getPaddingRight();
        PropertyValuesHolder paddingLeft = PropertyValuesHolder.ofInt("paddingLeft",
                                                                      currentPaddingLeft,
                                                                      currentPaddingLeft + DEFAULT_PADDING);
        PropertyValuesHolder paddingRight = PropertyValuesHolder.ofInt("paddingRight",
                                                                       currentPaddingRight,
                                                                       currentPaddingRight + DEFAULT_PADDING);
        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(paddingLeft, paddingRight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imagePane.setPadding((Integer) animation.getAnimatedValue("paddingLeft"),
                                     imagePane.getPaddingTop(),
                                     (Integer) animation.getAnimatedValue("paddingRight"),
                                     imagePane.getPaddingBottom());
                imagePane.invalidate();
            }
        });
        animator.setDuration(1000);
        animator.start();

    }

    private void animateMarginBottom(int dp) {
        int currentMarginBottom = ((GridLayout.LayoutParams) imagePane.getLayoutParams()).bottomMargin;
        ValueAnimator paddingLeftAnimator = ValueAnimator.ofInt(currentMarginBottom, currentMarginBottom + dp);
        paddingLeftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) imagePane.getLayoutParams();
                params.bottomMargin = (Integer) animation.getAnimatedValue();
                imagePane.requestLayout();
            }
        });
        paddingLeftAnimator.setDuration(1000);
        paddingLeftAnimator.start();
    }

    private void animateMarginsTopAndBottomSimultaneously() {
        int currentMarginBottom = ((GridLayout.LayoutParams) imagePane.getLayoutParams()).bottomMargin;
        int currentMarginTop = ((GridLayout.LayoutParams) imagePane.getLayoutParams()).topMargin;
        ValueAnimator marginBottom = ValueAnimator.ofInt(currentMarginBottom,
                                                         currentMarginBottom + DEFAULT_MARGIN);
        marginBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) imagePane.getLayoutParams();
                params.bottomMargin = (Integer) animation.getAnimatedValue();
                imagePane.requestLayout();
            }
        });
        ValueAnimator marginTop = ValueAnimator.ofInt(currentMarginTop,
                                                      currentMarginTop + DEFAULT_MARGIN);
        marginTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) imagePane.getLayoutParams();
                params.topMargin = (Integer) animation.getAnimatedValue();
                imagePane.requestLayout();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(marginBottom, marginTop);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    private void reset() {
        imagePane.setPadding(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING);
        imagePane.invalidate();
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) imagePane.getLayoutParams();
        params.bottomMargin = DEFAULT_MARGIN;
        params.topMargin = DEFAULT_MARGIN;
        imagePane.requestLayout();
    }

}
