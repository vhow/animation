package io.github.vhow.animation.util;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Property;
import android.widget.ImageView;

public class SkewView extends ImageView {
    public static final Property<SkewView, Float> SKEW_X = new FloatProperty<SkewView>("skewX") {
        @Override
        public void setValue(SkewView object, float value) {
            object.setSkewX(value);
        }

        @Override
        public Float get(SkewView object) {
            return object.getSkewX();
        }
    };
    private static final String TAG = "SkewView";
    private float mSkewX;

    public SkewView(Context context) {
        super(context);
    }

    public SkewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SkewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SkewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public float getSkewX() {
        return mSkewX;
    }

    public void setSkewX(float skewX) {
        mSkewX = skewX;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mSkewX != 0) {
            canvas.skew((float) (mSkewX * Math.PI / 180.0f), 0);
        }
        super.onDraw(canvas);
    }
}
