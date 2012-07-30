package com.kroknes.android.animations.views;

import com.kroknes.android.R;
import com.kroknes.android.path.AnimatorPath;
import com.kroknes.android.path.PathEvaluator;
import com.kroknes.android.path.PathPoint;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;

public class PathAnimationPresenter {

    private static final int COLOR = Color.rgb(224, 255, 255);
    private final Ball ball;

    public PathAnimationPresenter(ViewGroup layout) {
        FrameLayout pane = (FrameLayout) layout.findViewById(R.id.pane);
        pane.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    animate(event.getX(), event.getY());
                }
                return true;
            }
        });
        ball = new Ball(pane.getContext());
        pane.addView(ball);
    }

    private void animate(float x, float y) {
        AnimatorPath path = new AnimatorPath();
        path.curveTo(100, 300, 500, 900, x - 70f, y - 70f);
        ObjectAnimator anim = ObjectAnimator.ofObject(this, "ballPosition",
                                                      new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new AnticipateOvershootInterpolator(1f));
        anim.setDuration(5000);
        anim.start();
    }

    public void setBallPosition(PathPoint point) {
        ball.setTranslationX(point.mX);
        ball.setTranslationY(point.mY);
    }

    public PathPoint getBallPosition() {
        return PathPoint.moveTo(ball.getX(), ball.getY());
    }

    private class Ball extends View {

        private Ball(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            ShapeDrawable shapeDrawable = shape();
            canvas.save();
            canvas.translate(50f, 50f);
            shapeDrawable.draw(canvas);
            canvas.restore();
        }

        private ShapeDrawable shape() {
            OvalShape ovalShape = new OvalShape();
            ovalShape.resize(50f, 50f);
            ShapeDrawable shape = new ShapeDrawable(ovalShape);
            shape.getPaint().setColor(COLOR);
            int darkColor = 0xff000000 | Color.RED / 4 << 16 | Color.GREEN / 4 << 8 | Color.BLUE / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                                                         50f, COLOR, darkColor, Shader.TileMode.CLAMP);
            shape.getPaint().setShader(gradient);
            return shape;
        }
    }

}
