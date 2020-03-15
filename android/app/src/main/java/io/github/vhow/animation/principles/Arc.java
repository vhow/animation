package io.github.vhow.animation.principles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;

import java.util.ArrayList;
import java.util.List;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;

public class Arc extends BaseFragment {
    private static final String TAG = "Arc";
    private View mTarget;

    public static BaseFragment newInstance() {
        return new Arc();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.arc, container, false);
        mTarget = view.findViewById(R.id.target);
        addTarget(mTarget);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    private Animator buildSingleDropAnimator(double value, TimeInterpolator interpolator, double duration) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_Y, (float) value);
        animator.setDuration((long) duration);
        animator.setInterpolator(interpolator);
        return animator;
    }

    private void start() {
        final ViewParent parent = mTarget.getParent();
        if (parent instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) parent;
            final float h = (group.getHeight() - mTarget.getHeight()) / 16.0f;
            final int w = group.getWidth();// - mTarget.getWidth();
            final int duration = 4000;
            final float t = duration / 100.0f; // ms;
            final PathInterpolator it1 = new PathInterpolator(.51f, .01f, .79f, .02f);
            final PathInterpolator it2 = new PathInterpolator(.19f, 1f, .7f, 1f);
            final List<Animator> list = new ArrayList<>();
            list.add(buildSingleDropAnimator(h * -8, it1, 0));
            list.add(buildSingleDropAnimator(h * 8, it2, t * 15));
            list.add(buildSingleDropAnimator(h * -4, it1, t * 10));
            list.add(buildSingleDropAnimator(h * 8, it2, t * 7.5));
            list.add(buildSingleDropAnimator(0, it1, t * 7.5));
            list.add(buildSingleDropAnimator(h * 8, it2, t * 5));
            list.add(buildSingleDropAnimator(h * 3, it1, t * 5));
            list.add(buildSingleDropAnimator(h * 8, it2, t * 6));
            list.add(buildSingleDropAnimator(h * 6, it1, t * 4));
            list.add(buildSingleDropAnimator(h * 8, it2, t * 4));
            list.add(buildSingleDropAnimator(h * 7.5, it1, t * 2));
            list.add(buildSingleDropAnimator(h * 8, it2, t * 4));
            final AnimatorSet dropSet = new AnimatorSet();
            dropSet.playSequentially(list);
            dropSet.setInterpolator(new LinearInterpolator());
            final ObjectAnimator translateX = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, w);
            final ObjectAnimator alpha = ObjectAnimator.ofFloat(mTarget, View.ALPHA, 0);
            alpha.setStartDelay(duration * 7 / 10);
            final AnimatorSet hSet = new AnimatorSet();
            hSet.setInterpolator(
                    new PathInterpolator(.37f, .55f, .49f, .67f));
            hSet.setDuration(duration);
            hSet.playTogether(translateX, alpha);
            final AnimatorSet set = new AnimatorSet();
            set.playTogether(dropSet, hSet);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    reset();
                }
            });
            addAnimator(set);
            set.start();
        }
    }

    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.arc;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
