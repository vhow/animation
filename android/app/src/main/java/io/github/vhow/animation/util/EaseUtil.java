package io.github.vhow.animation.util;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public class EaseUtil {
    public static Interpolator ease = new PathInterpolator(.25f, 1, .25f, 1);
    public static Interpolator easeIn = new PathInterpolator(.42f, 0, 1, 1);
    public static Interpolator easeOut = new PathInterpolator(0, 0, .58f, 1);
    public static Interpolator easeInOut = new PathInterpolator(.42f, 0, .58f, 1);
    public static Interpolator easeInQuad = new PathInterpolator(.55f, .085f, .68f, .53f);
    public static Interpolator easeInCubic = new PathInterpolator(.55f, .55f, .67f, .19f);
    public static Interpolator easeInQuart = new PathInterpolator(.895f, .03f, .685f, .22f);
    public static Interpolator easeInQuint = new PathInterpolator(.755f, .05f, .855f, .06f);
    public static Interpolator easeInSine = new PathInterpolator(.47f, 0, .745f, .715f);
    public static Interpolator easeInExpo = new PathInterpolator(0.950f, 0.050f, 0.795f, 0.035f);
    public static Interpolator easeInCirc = new PathInterpolator(0.600f, 0.040f, 0.980f, 0.335f);
    public static Interpolator easeInBack = new PathInterpolator(0.600f, -0.280f, 0.735f, 0.045f);
    public static Interpolator easeOutQuad = new PathInterpolator(0.600f, -0.280f, 0.735f, 0.045f);
    public static Interpolator easeOutCubic = new PathInterpolator(0.215f, 0.610f, 0.355f, 1.000f);
    public static Interpolator easeOutQuart = new PathInterpolator(0.165f, 0.840f, 0.440f, 1.000f);
    public static Interpolator easeOutQuint = new PathInterpolator(0.230f, 1.000f, 0.320f, 1.000f);
    public static Interpolator easeOutSine = new PathInterpolator(0.390f, 0.575f, 0.565f, 1.000f);
}
