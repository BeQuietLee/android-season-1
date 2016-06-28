package com.leili.season1.jni.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leili.season1.R;
import com.leili.season1.util.ViewUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lei.li on 6/15/16.
 */
public class JNIActivity extends Activity {
	private static final String TAG = JNIActivity.class.getSimpleName();

	// armeabi-v7a
	private static final String helloFileUri = "http://10.4.232.53:9528/jni_hello/armeabi-v7a/libJni.so";
	private static final String goodbyeFileUri = "http://10.4.232.53:9528/jni_goodbye/armeabi-v7a/libJni.so";
	private static final String appFolder = "/data/data/com.leili.season1/";

	private String soFilePath;
	private OkHttpClient client;

//	static {
//		System.loadLibrary("Jni");
//		System.load(appFolder + "lib/libJni.so");
//		Log.e(TAG, "System.loadLibrary");
//	}

	// Java_com_leili_season1_jni_activity_JNIActivity_say
	public native String say();

	private TextView tvSay;
	private Button btnLoad;

	private boolean flag = true;
	private int count = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.loadLibrary("Jni");
		// /data/data/com.leili.season1/lib/libJni.so
//		System.load(getFilesDir().getParent() + "/lib/libJni.so");
//		System.load("/data/app/com.leili.season1-2/lib/x86/libJni.so");
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.jni_activity);
		initData();
		initViews();
	}

	private void initData() {
		soFilePath = getFilesDir() + "/libJni2.so";
		client = new OkHttpClient();
	}

	private void initViews() {
		tvSay = (TextView) findViewById(R.id.say);
		btnLoad = (Button) findViewById(R.id.load);
		btnLoad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				downloadRemoteFile(flag ? helloFileUri : goodbyeFileUri);
				Log.e(TAG, "downloading remote file: " + (flag ? "hello" : "goodbye"));
				flag = !flag;
				Log.e(TAG, "count = " + count);
				if (count++ % 3 == 0) {
					Log.e(TAG, "System.loadLibrary(\"Jni\")");
					System.loadLibrary("Jni");
				}
			}
		});
	}

	private void downloadRemoteFile(String uri) {
		Request request = new Request.Builder().url(uri).build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) {
				try {
					InputStream is = response.body().byteStream();
					File file = new File(soFilePath);
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					int len;
					byte[] buf = new byte[2048];
					while ((len = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, len);
					}

					fileOutputStream.flush();
					fileOutputStream.close();
					is.close();
					System.load(soFilePath);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ViewUtils.showShortToast(JNIActivity.this, "Done~!");
							tvSay.setText(say());
						}
					});
				} catch (Exception e) {
					Log.e(TAG, "fail to write", e);
				}
			}
		});
	}

}
