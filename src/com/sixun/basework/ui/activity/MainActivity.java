package com.sixun.basework.ui.activity;

import java.io.File;

import com.sixun.basework.R;
import com.sixun.basework.ui.BaseActivity;
import com.sixun.basework.utils.L;
import com.sixun.basework.utils.camera.CameraUtils;
import com.sixun.basework.utils.camera.CameraUtils.OnBitmapListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener {

	private TextView tv;
	private ImageView img;
	private String s = "13213213212131313132123";
	private CameraUtils cameraUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initView() {
		fv.find(R.id.button1).setOnClickListener(this);
		fv.find(R.id.button2).setOnClickListener(this);
		fv.find(R.id.button3).setOnClickListener(this);
		fv.find(R.id.button4).setOnClickListener(this);
		tv = fv.textView(R.id.textView1);
//		img = fv.imageView(R.id.imageView1);
		img = (ImageView) findViewById(R.id.imageView1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			cameraUtils = new CameraUtils(mActivity, new OnBitmapListener() {
				
				@Override
				public void bitmapResult(File file) {
					Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
					img.setImageBitmap(bitmap);
				}
			});
//			cameraUtils.setAspect(2, 1);
			cameraUtils.setOutput(720, 360);
			cameraUtils.showPop();
			break;
		case R.id.button2:
			
			break;
		case R.id.button3:
			
			break;
		case R.id.button4:
			
			break;
		default:
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(cameraUtils == null || !cameraUtils.onActivityResult(requestCode, resultCode, data)){
			//1.回调CameraUtils的onActivityResult方法
				super.onActivityResult(requestCode, resultCode, data);
		}
	}

	class Person {
		int age;
  
		@Override
		public String toString() {
			return "Person [age=" + age + ", name=" + name + ", score=" + score
					+ "]";
		}

		String name;
		float score;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}
	}

}
