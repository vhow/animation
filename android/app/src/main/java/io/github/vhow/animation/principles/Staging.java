package io.github.vhow.animation.principles;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;
import io.github.vhow.animation.util.EaseUtil;

public class Staging extends BaseFragment {
    private View mTarget;
    private View mTargetA;
    private View mTargetB;

    public static BaseFragment newInstance() {
        return new Staging();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.staging, container, false);
        mTarget = view.findViewById(R.id.target);
        mTargetA = view.findViewById(R.id.target_a);
        mTargetB = view.findViewById(R.id.target_b);
        addTarget(mTarget, mTargetA, mTargetB);
        return view;
    }

    private Interpolator getInterpolator(double p1X, double p1Y, double p2X, double p2Y) {
        return PathInterpolatorCompat.create((float) p1X, (float) p1Y, (float) p2X, (float) p2Y);
    }

    private void start() {
        mTarget.setPivotX(0);
        mTarget.setPivotY(mTarget.getHeight());
        final AnimatorSet fadeSet = new AnimatorSet();
        fadeSet.playTogether(
                ObjectAnimator.ofFloat(mTargetA, View.ALPHA, .2f),
                ObjectAnimator.ofFloat(mTargetB, View.ALPHA, .2f)
        );
        final AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.setInterpolator(EaseUtil.easeInOut);
        set.playSequentially(
                fadeSet,
                ObjectAnimator.ofFloat(this.mTarget, View.ROTATION, -30)
        );
        addAnimator(set);
        set.start();
    }

    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.staging;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
