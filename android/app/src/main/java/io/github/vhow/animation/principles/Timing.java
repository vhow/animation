package io.github.vhow.animation.principles;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;

public class Timing extends BaseFragment {
    private static final String TAG = "Timing";
    private View mTargetLeft;
    private View mTargetRight;

    public static BaseFragment newInstance() {
        return new Timing();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.timing, container, false);
        mTargetLeft = view.findViewById(R.id.target_a);
        mTargetRight = view.findViewById(R.id.target_b);
        addTarget(mTargetLeft, mTargetRight);
        return view;
    }

    private void setPivot(View view) {
        view.setPivotX(view.getWidth());
        view.setPivotY(view.getHeight());
    }

    private void start() {
        startRotating(mTargetLeft, new PathInterpolator(0.93f, 0, 0.67f, 1));
        startRotating(mTargetRight, new PathInterpolator(1, -0.97f, 0.23f, 1));
    }

    private void startRotating(View view, Interpolator interpolator) {
        setPivot(view);
        final Keyframe kf0 = Keyframe.ofFloat(0, 0);
        final Keyframe kf1 = Keyframe.ofFloat(0.4f, 90);
        final Keyframe kf2 = Keyframe.ofFloat(0.6f, 90);
        final Keyframe kf3 = Keyframe.ofFloat(1, 90);
        final PropertyValuesHolder holder =
                PropertyValuesHolder.ofKeyframe(View.ROTATION, kf0, kf1, kf2, kf3);
        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder);
        animator.setInterpolator(interpolator);
        animator.setDuration(2000);
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
        return R.string.timing;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
