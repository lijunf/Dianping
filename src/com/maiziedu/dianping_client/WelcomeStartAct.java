package com.maiziedu.dianping_client;

import java.util.Timer;
import java.util.TimerTask;

import com.maiziedu.dianping_client.utils.SharedUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
//延时跳转可以使用handler
public class WelcomeStartAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
//		new Handler(new Handler.Callback() {
//			//处理接收到的消息的方法
//			@Override
//			public boolean handleMessage(Message msg) {
//				//实现页面跳转
//				startActivity(new Intent(getApplicationContext(), MainActivity.class));
//				return false;
//			}
//		}).sendEmptyMessageDelayed(0, 3000);//表示延时三秒进行任务的执行
		//使用java中的定时器进行处理
		Timer timer = new Timer();
		timer.schedule(new Task(), 3000);//定时器延时执行任务的方法
	}
	class Task extends TimerTask{

		@Override
		public void run() {
			//实现页面的跳转
			//不是第一次启动
			if (SharedUtils.getWelcomeBoolean(getBaseContext())) {
				startActivity(new Intent(getBaseContext(), MainActivity.class));
			}else{
				startActivity(new Intent(WelcomeStartAct.this, WelcomeGuideAct.class));
				//保存访问记录
				SharedUtils.putWelcomeBoolean(getBaseContext(), true);
			}
			finish();
		}
		
	}
}
