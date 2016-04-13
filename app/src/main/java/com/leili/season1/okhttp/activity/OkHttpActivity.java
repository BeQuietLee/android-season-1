package com.leili.season1.okhttp.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leili.season1.R;
import com.leili.season1.util.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp
 * Created by lei.li on 4/13/16.
 */
public class OkHttpActivity extends Activity {
	private static final String TAG = OkHttpActivity.class.getSimpleName();

	// baidu城市请求api，参数：cityname，需要在拼接后进行encode
	private static final String CITY_REQ = "http://apistore.baidu.com/microservice/cityinfo?cityname=%s";
	// baidu天气请求api，参数：cityid
	private static final String WEATHER_REQ = "http://apistore.baidu.com/microservice/weather?cityid=%s";
	private static final String ERROR = "请求失败！";
	private static final String PLZ_INPUT = "请输入城市名称（汉字）";

	private OkHttpClient client;
	private EditText etInput;
	private TextView tvCityInfo, tvWeatherInfo;
	private Button btnSearch;

	private String cityCode; // 城市id

	private ResponseHandler cityRespHandler, weatherRespHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.okhttp_activity);
		initData();
		initViews();
	}

	private void initData() {
		client = new OkHttpClient();
		cityRespHandler = new ResponseHandler() {
			@Override
			public void handle(String response) {
				try {
					JSONObject cityInfoJson = new JSONObject(response).optJSONObject("retData");
					StringBuilder sb = new StringBuilder();
					sb.append(cityInfoJson.optString("provinceName"));
					sb.append("省 ");
					sb.append(cityInfoJson.optString("cityName"));
					sb.append("市");
					cityCode = cityInfoJson.optString("cityCode");
					reqWeatherInfo(cityCode);
					tvCityInfo.setText(sb.toString());
				} catch (JSONException e) {
					Log.e(TAG, "wrong json!" + response, e);
				}
			}
		};
		weatherRespHandler = new ResponseHandler() {
			@Override
			public void handle(String response) {
				try {
					JSONObject weatherInfoJson = new JSONObject(response).optJSONObject("retData");
					StringBuilder sb = new StringBuilder();
					sb.append("日期");
					sb.append(" ");
					sb.append(weatherInfoJson.optString("date"));
					sb.append(" ");
					sb.append(weatherInfoJson.optString("time"));
					sb.append("\n");
					sb.append(weatherInfoJson.optString("weather"));
					sb.append(" ");
					sb.append(weatherInfoJson.optString("l_tmp"));
					sb.append("℃~");
					sb.append(weatherInfoJson.optString("h_tmp"));
					sb.append("℃");
					tvWeatherInfo.setText(sb.toString());
				} catch (JSONException e) {
					Log.e(TAG, "wrong json!" + response, e);
				}
			}
		};
	}

	private void initViews() {
		etInput = (EditText) findViewById(R.id.input);
		tvCityInfo = (TextView) findViewById(R.id.city_info);
		tvWeatherInfo = (TextView) findViewById(R.id.weather_info);
		btnSearch = (Button) findViewById(R.id.search);

		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String usrInp = etInput.getText().toString();
				if (TextUtils.isEmpty(usrInp)) {
					ViewUtils.showShortToast(OkHttpActivity.this, PLZ_INPUT);
				} else {
					reqCityInfo(usrInp);
				}
			}
		});
	}

	private void reqCityInfo(String cityName) {
		String url = String.format(CITY_REQ, cityName);
		runAsyncTask(cityRespHandler, url);
	}

	private void reqWeatherInfo(String cityCode) {
		String url = String.format(WEATHER_REQ, cityCode);
		runAsyncTask(weatherRespHandler, url);
	}

	private void runAsyncTask(final ResponseHandler handler, final String url) {
		new NetworkAsyncTask().execute(handler, url);
	}

	class NetworkAsyncTask extends AsyncTask {

		private ResponseHandler handler;

		@Override
		protected Object doInBackground(Object[] params) {
			try {
				handler = (ResponseHandler) params[0];
				String url = (String) params[1];
				Request request = new Request.Builder().url(url).build();
				Response response = client.newCall(request).execute();
				return response.body().string(); // return JSON string
			} catch (IOException e) {
				return ERROR;
			}
		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			handler.handle((String) o);
		}
	}

	/**
	 * 处理json返回
	 */
	interface ResponseHandler {
		/**
		 * @param response must be json string
		 */
		void handle(String response);
	}
}
