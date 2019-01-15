package com.nikhil.otw.webview.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.nikhil.otw.webview.R;


/**
 * Created by Kamlesh on 023 23/11/15.
 */
public class AnimUtils {
	public static void forwardAnimation(Context context, View inView, View outView) {
		inView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.a_slide_in_right));
		outView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.a_slide_out_left));
	}

	public static void backwardAnimation(Context context, View inView, View outView) {
		inView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.a_slide_in_left));
		outView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.a_slide_out_right));
	}

	public static void forwardAnimation(Activity currentActivity) {
		currentActivity.overridePendingTransition(R.anim.a_slide_in_right, R.anim.a_slide_out_left);
	}

	public static void backwardAnimation(Activity currentActivity) {
		currentActivity.overridePendingTransition(R.anim.a_slide_in_left, R.anim.a_slide_out_right);
	}
}
