package io.github.vhow.animation.util;

import android.animation.Animator;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    private List<Animator> mAnimatorList = new ArrayList<>();
    private List<View> mTargetList = new ArrayList<>();

    protected final void addTarget(View... views) {
        Collections.addAll(mTargetList, views);
        for (View view : views) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAnimationStart();
                }
            });
        }
    }

    protected final void addAnimator(Animator... animators) {
        Collections.addAll(mAnimatorList, animators);
    }

    public abstract void onAnimationStart();

    public abstract void onAnimationReset();

    public abstract int getTitle();

    @Override
    public void onPause() {
        super.onPause();
        reset();
    }

    protected void reset() {
        for (Animator animator : mAnimatorList) {
            animator.removeAllListeners();
            animator.cancel();
        }
        for (View view : mTargetList) {
            view.animate()
                    .translationX(0).translationY(0)
                    .rotation(0).rotationX(0).rotationY(0)
                    .scaleX(1).scaleY(1)
                    .alpha(1)
                    .setDuration(300)
                    .start();
        }
    }
}
