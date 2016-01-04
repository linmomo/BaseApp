package com.sixun.basework.ui.activity;

import com.sixun.basework.R;
import com.sixun.basework.ui.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 测试  
 * 2015-12-28
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	private TextView tv;
	private ImageView img;
	String s = "13213213212131313132123";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	/**
	 * 初始化view
	 */
	public void initViews() {
		fv.find(R.id.button1).setOnClickListener(this);
		fv.find(R.id.button2).setOnClickListener(this);
		fv.find(R.id.button3).setOnClickListener(this);
		fv.find(R.id.button4).setOnClickListener(this);
		tv = fv.textView(R.id.textView1);
		img = fv.imageView(R.id.imageView1);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			
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
