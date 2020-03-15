package io.github.vhow.animation.principles;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;

public class Exaggeration extends BaseFragment {
    private static final String TAG = "Exaggeration";

    private View mTarget;

    public static BaseFragment newInstance() {
        return new Exaggeration();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.exaggeration, container, false);
        mTarget = view.findViewById(R.id.target);
        addTarget(mTarget);
        return view;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

    private void start() {
        mTarget.setPivotY(mTarget.getWidth());
        mTarget.setPivotY(mTarget.getHeight());
        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTarget,
                buildRotationHolder(),
                buildScaleHolder(View.SCALE_X), buildScaleHolder(View.SCALE_Y));
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        addAnimator(animator);
        animator.start();
    }

    private PropertyValuesHolder buildRotationHolder() {
        final Keyframe kf0 = Keyframe.ofFloat(0f, 0);
        final Keyframe kf1 = Keyframe.ofFloat(0.1f, 0);
        kf1.setInterpolator(new PathInterpolator(.87f, -1f, .66f, 1));
        final Keyframe kf2 = Keyframe.ofFloat(0.4f, -45);
        kf2.setInterpolator(new PathInterpolator(.16f, .54f, 0, 1));
        final Keyframe kf3 = Keyframe.ofFloat(0.7f, 360);
        return PropertyValuesHolder.ofKeyframe(View.ROTATION, kf0, kf1, kf2, kf3);
    }

    private PropertyValuesHolder buildScaleHolder(Property<View, Float> property) {
        final Keyframe kf0 = Keyframe.ofFloat(0f, 1);
        final Keyframe kf1 = Keyframe.ofFloat(0.4f, 2);
        kf1.setInterpolator(new PathInterpolator(.87f, -1f, .66f, 1));
        final Keyframe kf2 = Keyframe.ofFloat(0.7f, 1);
        kf2.setInterpolator(new PathInterpolator(.16f, .54f, 0, 1));
        final Keyframe kf3 = Keyframe.ofFloat(1f, 1);
        return PropertyValuesHolder.ofKeyframe(property, kf0, kf1, kf2, kf3);
    }

    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.exaggeration;
    }
}
