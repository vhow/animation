package io.github.vhow.animation.principles;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;

public class Rotation extends BaseFragment {
    private static final String TAG = "Rotation";
    private View mTargetInter;
    private View mTargetOuter;

    public static BaseFragment newInstance() {
        return new Rotation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.rotation, container, false);
        mTargetInter = view.findViewById(R.id.target_inter);
        mTargetOuter = view.findViewById(R.id.target_outer);
        addTarget(mTargetInter, mTargetOuter);
        return view;
    }

    private void start() {
        final AnimatorSet set = new AnimatorSet();
        set.playTogether(buildRotatingAnimator(mTargetInter, 360, 6000),
                buildRotatingAnimator(mTargetOuter, -360, 3000));
        addAnimator(set);
        set.start();
    }

    private Animator buildRotatingAnimator(View target, float to, long duration) {
        final ObjectAnimator animator = ObjectAnimator
                .ofFloat(target, View.ROTATION, to).setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }


    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.rotate;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
