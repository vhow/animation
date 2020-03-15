package io.github.vhow.animation.principles;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;
import io.github.vhow.animation.util.SkewView;

public class Follow extends BaseFragment {
    private static final String TAG = "Follow";

    private SkewView mTarget;

    public static BaseFragment newInstance() {
        return new Follow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.follow, container, false);
        mTarget = view.findViewById(R.id.target);
        addTarget(mTarget);
        return view;
    }

    private void start() {
        final int duration = 4000; // ms
        final int dur = 40; // ms

        final ObjectAnimator skew1 = ObjectAnimator.ofFloat(mTarget, SkewView.SKEW_X, 0);
        skew1.setDuration(dur * 35);
        final ObjectAnimator skew2 = ObjectAnimator.ofFloat(mTarget, SkewView.SKEW_X, 20);
        skew2.setDuration(dur * 15);
        final ObjectAnimator skew3 = ObjectAnimator.ofFloat(mTarget, SkewView.SKEW_X, 20);
        skew3.setDuration(dur * 10);
        final ObjectAnimator skew4 = ObjectAnimator.ofFloat(mTarget, SkewView.SKEW_X, 0);
        skew2.setDuration(dur * 30);
        final ObjectAnimator skew5 = ObjectAnimator.ofFloat(mTarget, SkewView.SKEW_X, 0);
        skew2.setDuration(dur * 10);
        final AnimatorSet skewSet = new AnimatorSet();
        skewSet.playSequentially(skew1, skew2, skew3, skew4, skew5);
        skewSet.setInterpolator(
                new PathInterpolator(.64f, -.36f, .1f, 1));

        final int distance = mTarget.getWidth() * 4;

        final ObjectAnimator tr1 = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, -distance / 6);
        tr1.setDuration(dur * 15);
        final ObjectAnimator tr2 = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, -distance / 6);
        tr2.setDuration(dur * 10);
        final ObjectAnimator tr3 = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, distance);
        tr3.setDuration(dur * 60);
        final ObjectAnimator tr4 = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, distance);
        tr4.setDuration(dur * 15);
        final AnimatorSet trSet = new AnimatorSet();
        trSet.playSequentially(tr1, tr2, tr3, tr4);
        trSet.setInterpolator(new PathInterpolator(.64f, -.36f, .1f, 1));

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(skewSet, trSet);
        addAnimator(set);
        set.start();
    }

    @Override
    public void onAnimationReset() {
        mTarget.setSkewX(0);
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.follow_and_overlap;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
