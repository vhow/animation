package io.github.vhow.animation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.github.vhow.animation.principles.Anticipation;
import io.github.vhow.animation.principles.Arc;
import io.github.vhow.animation.principles.EaseInEaseOut;
import io.github.vhow.animation.principles.Exaggeration;
import io.github.vhow.animation.principles.Follow;
import io.github.vhow.animation.principles.Rotation;
import io.github.vhow.animation.principles.SquashStretch;
import io.github.vhow.animation.principles.Staging;
import io.github.vhow.animation.principles.StraightAhead;
import io.github.vhow.animation.principles.Timing;
import io.github.vhow.animation.util.BaseFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_LAST_PRINCIPLE = "last_principle";
    private final Handler mHandler = new UiHandler(this);
    private SharedPreferences mPref;
    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private Toolbar mToolbar;

    private static List<Integer> sPrincipleList = new ArrayList<>();

    static {
        sPrincipleList.add(R.id.exaggeration);
        sPrincipleList.add(R.id.anticipation);
        sPrincipleList.add(R.id.squash_and_stretch);
        sPrincipleList.add(R.id.staging);
        sPrincipleList.add(R.id.straight_and_pose);
        sPrincipleList.add(R.id.follow_and_overlap);
        sPrincipleList.add(R.id.ease_in_ease_out);
        sPrincipleList.add(R.id.arc);
        sPrincipleList.add(R.id.rotate);
        sPrincipleList.add(R.id.timing);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustSystemUi();
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mViewPager = findViewById(R.id.viewPager);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int lastId = mPref.getInt(KEY_LAST_PRINCIPLE, 0);
        onFocus(lastId);
        mViewPager.setCurrentItem(lastId);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected() called with: position = [" + position + "]");
                onFocus(position);
            }
        });
    }

    private void adjustSystemUi() {
        final Window window = getWindow();
        window.setNavigationBarColor(getResources().getColor(R.color.navigation_bar_bg));
        final View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    private void onFocus(int position) {
        mToolbar.setTitle(mAdapter.getTitle(position));
        save(position);
        mHandler.sendEmptyMessageDelayed(UiHandler.MSG_START_ANIMATION, 600);
    }

    private void save(int position) {
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(KEY_LAST_PRINCIPLE, position);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void startAnimation() {
        final Fragment frag = mAdapter.getItem(mViewPager.getCurrentItem());
        if (!(frag instanceof BaseFragment)) {
            return;
        }
        final BaseFragment baseFragment = (BaseFragment) frag;
        baseFragment.onAnimationStart();
    }

    private void trigger(int menuId) {
        final Fragment frag = mAdapter.getItem(mViewPager.getCurrentItem());
        if (!(frag instanceof BaseFragment)) {
            return;
        }
        final BaseFragment baseFragment = (BaseFragment) frag;
        switch (menuId) {
            case R.id.play:
                baseFragment.onAnimationStart();
                break;
            case R.id.reset:
                baseFragment.onAnimationReset();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case R.id.play:
                trigger(R.id.play);
                break;
            case R.id.reset:
                trigger(R.id.reset);
                break;
            case R.id.squash_and_stretch:
            case R.id.anticipation:
            case R.id.staging:
            case R.id.straight_and_pose:
            case R.id.follow_and_overlap:
            case R.id.ease_in_ease_out:
            case R.id.arc:
            case R.id.rotate:
            case R.id.timing:
            case R.id.exaggeration:
            case R.id.secondary_action:
            case R.id.solid_drawing:
            case R.id.appeal:
                mViewPager.setCurrentItem(sPrincipleList.indexOf(itemId));
                break;
        }
        return true;
    }

    private static class UiHandler extends Handler {
        private static final int MSG_START_ANIMATION = 0;
        private final WeakReference<MainActivity> mRef;

        UiHandler(MainActivity activity) {
            mRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final MainActivity activity = mRef.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case MSG_START_ANIMATION:
                    activity.startAnimation();
                    break;
            }
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<BaseFragment> mList = new ArrayList<>();

        private MyPagerAdapter(FragmentManager fm) {
            super(fm);
            for (int i = 0, size = sPrincipleList.size(); i < size; i++) {
                final int id = sPrincipleList.get(i);
                switch (id) {
                    case R.id.anticipation:
                        mList.add(Anticipation.newInstance());
                        break;
                    case R.id.exaggeration:
                        mList.add(Exaggeration.newInstance());
                        break;
                    case R.id.squash_and_stretch:
                        mList.add(SquashStretch.newInstance());
                        break;
                    case R.id.staging:
                        mList.add(Staging.newInstance());
                        break;
                    case R.id.straight_and_pose:
                        mList.add(StraightAhead.newInstance());
                        break;
                    case R.id.follow_and_overlap:
                        mList.add(Follow.newInstance());
                        break;
                    case R.id.ease_in_ease_out:
                        mList.add(EaseInEaseOut.newInstance());
                        break;
                    case R.id.arc:
                        mList.add(Arc.newInstance());
                        break;
                    case R.id.rotate:
                        mList.add(Rotation.newInstance());
                        break;
                    case R.id.timing:
                        mList.add(Timing.newInstance());
                        break;
                }
            }
        }

        private int getTitle(int position) {
            return mList.get(position).getTitle();
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
