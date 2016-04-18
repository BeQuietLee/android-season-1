package com.leili.season1.customfont.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leili.season1.R;
import com.leili.season1.util.ViewUtils;

/**
 * Created by lei.li on 4/18/16.
 */
public class CustomFontActivity extends Activity {

	private View vPreEdit;
	private EditText etInput;
	private Button btnGenerate;
	private TextView tvContent;

	private Typeface face;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_font_activity);
		initData();
		initViews();
	}

	private void initData() {
		face = Typeface.DEFAULT.createFromAsset(getAssets(), "fonts/Elephant_Handwritten_Pen_Chinese.ttf");
	}

	private void initViews() {
		vPreEdit = findViewById(R.id.pre_edit);
		etInput = (EditText) vPreEdit.findViewById(R.id.input);
		btnGenerate = (Button) vPreEdit.findViewById(R.id.generate);
		btnGenerate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userInput = etInput.getText().toString();
				if (TextUtils.isEmpty(userInput)) {
					ViewUtils.showShortToast(CustomFontActivity.this, "输入不能为空！");
				} else {
					ViewUtils.hideKeyboard(v);
					vPreEdit.setVisibility(View.GONE);
					tvContent.setText(userInput);
					tvContent.setVisibility(View.VISIBLE);
				}
			}
		});
		tvContent = (TextView) findViewById(R.id.content);
		tvContent.setTypeface(face);
	}
}
