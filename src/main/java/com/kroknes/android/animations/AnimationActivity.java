package com.kroknes.android.animations;

import com.kroknes.android.R;
import com.kroknes.android.animations.views.LayoutTransitionsPresenter;
import com.kroknes.android.animations.views.ObjectAnimatorPresenter;
import com.kroknes.android.animations.views.PathAnimationPresenter;
import com.kroknes.android.animations.views.ValueAnimatorPresenter;
import com.kroknes.android.animations.views.ViewPropertyAnimatorPresenter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class AnimationActivity extends Activity {

    private static final String[] titles = {"LayoutTransitions",
                                            "ValueAnimator",
                                            "ObjectAnimator",
                                            "ViewPropertyAnimator",
                                            "PathAnimations"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animations_main);
        wire();
    }

    private void wire() {
        ViewGroup layoutTransitionsView = (ViewGroup) View.inflate(this, R.layout.layout_transitions_layout, null);
        ViewGroup objectAnimatorView = (ViewGroup) View.inflate(this, R.layout.object_animator_layout, null);
        ViewGroup valueAnimatorView = (ViewGroup) View.inflate(this, R.layout.value_animator_layout, null);
        ViewGroup viewPropertyAnimatorView = (ViewGroup) View.inflate(this, R.layout.view_property_animator_layout, null);
        ViewGroup pathAnimationView = (ViewGroup) View.inflate(this, R.layout.path_animation_layout, null);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(layoutTransitionsView,
                                                  valueAnimatorView,
                                                  objectAnimatorView,
                                                  viewPropertyAnimatorView,
                                                  pathAnimationView));
        configurePagerStrip((PagerTabStrip) findViewById(R.id.pager_indicator));

        new LayoutTransitionsPresenter(this, layoutTransitionsView);
        new ObjectAnimatorPresenter(objectAnimatorView);
        new ValueAnimatorPresenter(valueAnimatorView);
        new ViewPropertyAnimatorPresenter(viewPropertyAnimatorView);
        new PathAnimationPresenter(pathAnimationView);
    }

    private static void configurePagerStrip(PagerTabStrip strip) {
        strip.setDrawFullUnderline(true);
        strip.setTabIndicatorColor(Color.rgb(90, 90, 90));
        strip.setBackgroundColor(Color.rgb(244, 244, 244));
        strip.setTextColor(Color.rgb(83, 83, 83));
    }

    private static class ViewPagerAdapter extends PagerAdapter {

        private final View[] views;

        private ViewPagerAdapter(View... views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views[position];
            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}
