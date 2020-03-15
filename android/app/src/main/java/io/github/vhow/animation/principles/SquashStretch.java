package io.github.vhow.animation.principles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;

import io.github.vhow.animation.R;
import io.github.vhow.animation.util.BaseFragment;
import io.github.vhow.animation.util.EaseUtil;

public class SquashStretch extends BaseFragment {
    private static final String TAG = "SquashStretch";
    private View mTarget;
    private View mWall;

    public static BaseFragment newInstance() {
        return new SquashStretch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.squash_stretch, container, false);
        mTarget = view.findViewById(R.id.target);
        mWall = view.findViewById(R.id.wall);
        addTarget(mTarget);
        return view;
    }

    private void start() {
        final AnimatorSet set = new AnimatorSet();
        set.playSequentially(buildForwardAnimator(), buildDropAnimator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTarget.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, 2000);
            }
        });
        addAnimator(set);
        set.start();
    }

    private Animator buildForwardAnimator() {
        mTarget.setPivotX(0);
        mTarget.setPivotY(0);
        final float distance = mWall.getX() - mTarget.getX();
        final int width = mTarget.getWidth();

        final ObjectAnimator forwardTranslateX =
                ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_X, distance - width / 2);
        final ObjectAnimator forwardScaleX =
                ObjectAnimator.ofFloat(mTarget, View.SCALE_X, 1, 0.5f);
        final ObjectAnimator forwardScaleY =
                ObjectAnimator.ofFloat(mTarget, View.SCALE_Y, 1, 1.5f);

        final AnimatorSet forward = new AnimatorSet();
        forward.play(forwardTranslateX).with(forwardScaleX).with(forwardScaleY);
        forward.setDuration(600);
        forward.setInterpolator(new PathInterpolator(1, -1, .95f, .89f));
        return forward;
    }

    private Animator buildDropAnimator() {
        final ObjectAnimator drop =
                ObjectAnimator.ofFloat(mTarget, View.TRANSLATION_Y,
                        0, mWall.getHeight() / 2 + mTarget.getHeight() / 2);
        final ObjectAnimator alpha = ObjectAnimator.ofFloat(mTarget, View.ALPHA, 1, 0);
        final AnimatorSet down = new AnimatorSet();
        down.setStartDelay(300);
        down.playTogether(drop, alpha);
        down.setInterpolator(EaseUtil.easeIn);
        down.setDuration(600);
        return down;
    }

    @Override
    public void onAnimationReset() {
        reset();
    }

    @Override
    public int getTitle() {
        return R.string.squash_and_stretch;
    }

    @Override
    public void onAnimationStart() {
        start();
    }

}
