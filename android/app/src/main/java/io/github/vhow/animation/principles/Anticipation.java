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

public class Anticipation extends BaseFragment {
    private View mTarget;
    private View mWall;

    public static BaseFragment newInstance() {
        return new Anticipation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.anticipation, container, false);
        mTarget = view.findViewById(R.id.target);
        mWall = view.findViewById(R.id.wall);
        addTarget(mTarget);
        return view;
    }

    private void start() {
        mTarget.setPivotX(mTarget.getWidth() >> 1);
        mTarget.setPivotY(mTarget.getHeight());
        final int changeX = mWall.getWidth() >> 1;
        final ObjectAnimator translationX = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, 0, -changeX);
        final ObjectAnimator rotation = ObjectAnimator.ofFloat(mTarget, View.ROTATION, 0, -20, -10, -30);
        final AnimatorSet dismissSet = new AnimatorSet();
        final ObjectAnimator translationXX = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, -changeX, -changeX - this.mTarget.getWidth());
        final ObjectAnimator translationY = ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_Y, 0, this.mTarget.getHeight());
        final ObjectAnimator alpha = ObjectAnimator.ofFloat(mTarget, View.ALPHA, 0);
        final ObjectAnimator rotation2 = ObjectAnimator.ofFloat(mTarget, View.ROTATION, -30, -90);
        dismissSet.playTogether(translationXX, translationY, alpha, rotation2);
        final AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(translationX, rotation, dismissSet);
        set.setInterpolator(EaseUtil.easeInOut);
        addAnimator(set);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                super.onAnimationEnd(animation);
                mTarget.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, 800);
            }
        });
        set.start();
    }

    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.anticipation;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
