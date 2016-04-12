package com.test.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindCountService extends Service {
	private static final String TAG = "bindCountService";
	private boolean flag = false;
	private myThread thread;
	private BindCountService bindCountService;
	int count;

	@Override
	public void onCreate() {

		Log.v(TAG, "onCreate");
		super.onCreate();
	}

	public void getCount() {
		thread = new myThread();
		thread.start();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "onStart");
		
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "onStartCommand");
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		this.flag = true;
		Log.v(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public boolean stopService(Intent name) {
		Log.v(TAG, "stopService");
		return super.stopService(name);
	}

	@Override
	public IBinder onBind(Intent intent) {
		count=intent.getIntExtra("count", 0);
		System.out.println("IBinder:"+count);
		return mybinder;
	}

	public class MyBinder extends Binder {
		public BindCountService getService() {
			return BindCountService.this;
		}
	}

	private MyBinder mybinder = new MyBinder();

	class myThread extends Thread {
		@Override
		public void run() {
			while (!flag) {
				count++;
				Intent intent = new Intent("com.update.count");
				intent.putExtra("count", count);
				sendBroadcast(intent);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			super.run();
		}

	}

}
