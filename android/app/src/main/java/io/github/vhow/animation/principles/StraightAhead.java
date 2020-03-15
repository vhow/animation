package io.github.vhow.animation.principles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;
import io.github.vhow.animation.util.EaseUtil;

public class StraightAhead extends BaseFragment {
    private View mTargetA;
    private View mTargetB;

    public static BaseFragment newInstance() {
        return new StraightAhead();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.straight_ahead, container, false);
        mTargetA = view.findViewById(R.id.target_a);
        mTargetB = view.findViewById(R.id.target_b);
        addTarget(mTargetA, mTargetB);
        return view;
    }

    private void start() {
        animate(this.mTargetA, 0);
        animate(this.mTargetB, 500);
    }

    private void animate(final View view, int delay) {
        final AnimatorSet set = new AnimatorSet();
        set.setInterpolator(EaseUtil.ease);
        set.play(ObjectAnimator.ofFloat(view, View.SCALE_X, 2))
                .with(ObjectAnimator.ofFloat(view, View.SCALE_Y, 2))
                .after(ObjectAnimator.ofFloat(view, View.ROTATION, -45))
                .before(ObjectAnimator.ofFloat(view, View.SCALE_X, 1))
                .before(ObjectAnimator.ofFloat(view, View.SCALE_Y, 1));
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().rotation(0).start();
            }
        });
        set.setStartDelay(delay);
        set.setDuration(1000);
        addAnimator(set);
        set.start();
    }

    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.straight_and_pose;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
