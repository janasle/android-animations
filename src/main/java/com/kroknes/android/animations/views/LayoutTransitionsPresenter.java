package com.kroknes.android.animations.views;

import java.lang.reflect.Field;

import com.kroknes.android.R;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LayoutTransitionsPresenter {

    private static final int BACKGROUND_COLOR = Color.rgb(244, 244, 244);
    private static final int BACKGROUND_COLOR_2 = Color.rgb(200, 200, 200);
    private static final int TEXT_COLOR = Color.BLACK;
    private static final int TEXT_COLOR_2 = Color.rgb(90, 90, 90);
    private final Context context;
    private final ViewGroup pane;
    private int counter;

    public LayoutTransitionsPresenter(Context context, View layout) {
        this.context = context;
        pane = (ViewGroup) layout.findViewById(R.id.pane);
        layout.findViewById(R.id.enable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableLayoutTransitions();
                v.setVisibility(View.INVISIBLE);
            }
        });

        layout.findViewById(R.id.add_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        layout.findViewById(R.id.remove_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView();
            }
        });

        layout.findViewById(R.id.adjust_delay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Remove Delay".equals(getText(v))) {
                    setDelay(0);
                    setText(v, "Add Delay");
                } else {
                    setDelay(300);
                    setText(v, "Remove Delay");
                }
            }
        });

        layout.findViewById(R.id.adjust_in_animations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustAnimations(v,
                                 LayoutTransition.APPEARING,
                                 "Disable in-animations",
                                 "Enable in-animations");
            }
        });

        layout.findViewById(R.id.adjust_out_animations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustAnimations(v,
                                 LayoutTransition.DISAPPEARING,
                                 "Disable out-animations",
                                 "Enable out-animations");
            }
        });

        layout.findViewById(R.id.adjust_in_change_animations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustAnimations(v,
                                 LayoutTransition.CHANGE_APPEARING,
                                 "Disable in-change-animations",
                                 "Enable in-change-animations");

            }
        });

        layout.findViewById(R.id.adjust_out_change_animations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustAnimations(v,
                                 LayoutTransition.CHANGE_DISAPPEARING,
                                 "Disable out-change-animations",
                                 "Enable out-change-animations");
            }
        });
    }

    private void adjustAnimations(View view, int type, CharSequence disableText, CharSequence enableText) {
        if (disableText.equals(getText(view))) {
            disableAnimation(type);
            setText(view, enableText);
        } else {
            enableAnimation(type, getDefaultAnimator(type));
            setText(view, disableText);
        }
    }

    private void enableLayoutTransitions() {
        pane.setLayoutTransition(new LayoutTransition());
    }

    private void addView() {
        pane.addView(newTextView(), pane.getChildCount() == 0 ? 0 : 1);
    }

    private TextView newTextView() {
        TextView child = new TextView(context);
        child.setText("View #" + (++counter));
        child.setBackgroundColor(counter % 2 == 0 ? BACKGROUND_COLOR : BACKGROUND_COLOR_2);
        child.setTextColor(counter % 2 == 0 ? TEXT_COLOR : TEXT_COLOR_2);
        child.setPadding(10, 10, 10, 10);
        return child;
    }

    private void removeView() {
        if (pane.getChildCount() > 1) {
            pane.removeViewAt(1);
        } else {
            pane.removeAllViews();
        }
    }

    private void setDelay(int delay) {
        pane.getLayoutTransition().setStartDelay(LayoutTransition.APPEARING, delay);
        pane.getLayoutTransition().setStartDelay(LayoutTransition.CHANGE_APPEARING, delay);
        pane.getLayoutTransition().setStartDelay(LayoutTransition.DISAPPEARING, delay);
        pane.getLayoutTransition().setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, delay);
    }

    private void disableAnimation(int type) {
        pane.getLayoutTransition().setAnimator(type, null);
    }

    private void enableAnimation(int type, Animator animator) {
        pane.getLayoutTransition().setAnimator(type, animator);
    }

    private Animator getDefaultAnimator(int type) {
        try {
            switch (type) {
            case LayoutTransition.APPEARING:
                return (Animator) getField("defaultFadeIn").get(pane.getLayoutTransition());
            case LayoutTransition.DISAPPEARING:
                return (Animator) getField("defaultFadeOut").get(pane.getLayoutTransition());
            case LayoutTransition.CHANGE_APPEARING:
                return (Animator) getField("defaultChangeIn").get(pane.getLayoutTransition());
            case LayoutTransition.CHANGE_DISAPPEARING:
                return (Animator) getField("defaultChangeOut").get(pane.getLayoutTransition());
            default:
                throw new RuntimeException("Unknown layout transition type = " + type);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field getField(String name) {
        Field field;
        try {
            field = LayoutTransition.class.getDeclaredField(name);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return field;
    }

    private static void setText(View v, CharSequence text) {
        ((Button) v).setText(text);
    }

    private static String getText(View v) {
        return ((Button) v).getText().toString();
    }

}
