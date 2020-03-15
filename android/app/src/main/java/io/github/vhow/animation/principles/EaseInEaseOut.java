package io.github.vhow.animation.principles;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;

public class EaseInEaseOut extends BaseFragment {
    private static final String TAG = "EaseInEaseOut";
    private View mTarget;

    public static BaseFragment newInstance() {
        return new EaseInEaseOut();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.ease_in_ease_out, container, false);
        mTarget = view.findViewById(R.id.target);
        addTarget(mTarget);
        return view;
    }

    private void start() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        final int x = displayMetrics.widthPixels * 4 / 10;
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(mTarget, View.TRANSLATION_X, -x, x)
                .setDuration(2000);
        animator.setInterpolator(new PathInterpolator(.5f, 0, .5f, 1));
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        addAnimator(animator);
        animator.start();
    }

    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.ease_in_ease_out;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
