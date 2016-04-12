package com.test.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CountService extends Service {
	private static final String TAG = "CountService";
	private boolean flag = false;
	private myThread thread;
    int count;
	@Override
	public void onCreate() {
		thread = new myThread();
		thread.start();
		Log.v(TAG, "onCreate");
		super.onCreate();
	}

	@Override
	@Deprecated
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
		// TODO Auto-generated method stub
		return null;
	}

	class myThread extends Thread {
		@Override
		public void run() {
			while (!flag) {
				count++;
				System.out.println("µ±Ç°Öµ£º" + count);
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
