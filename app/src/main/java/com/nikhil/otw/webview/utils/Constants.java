package com.nikhil.otw.webview.utils;
// Created by nikhil on 19/10/16, 4:47 PM


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Constants {
	private static final String TAG = "Constants";



	public static void getPackageHash(Context context) {

		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(),
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				//Log.d(TAG, "hash key"+ Base64.encodeToString(md.digest(), Base64.NO_WRAP));
			}
		} catch (PackageManager.NameNotFoundException e) {
			//Log.d("Name not found", e.getMessage(), e);

		} catch (NoSuchAlgorithmException e) {
			//Log.d("Error", e.getMessage(), e);
		}
	}

	public static String getAppVersion(Context context) {
		String versionNumber = "";
		try {
			versionNumber = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionNumber;
	}





}
