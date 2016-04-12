package com.test.android;

import com.test.android.service.BindCountService;
import com.test.android.service.BindCountService.MyBinder;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private Button bt_start;
	private Button bt_stop;
	private Button bt_bind;
	private Button bt_unbind;
	 TextView tv_count;
    private MyReceiver receiver;
    private static int count=0;
    public ServiceConnection conn=new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
            Log.v("FirstActivity", "unbindservice");		
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			   MyBinder binder = (MyBinder)service;
	            BindCountService bindService = binder.getService();
	            bindService.getCount();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		initView();
		TabHost th = (TabHost) findViewById(R.id.tab_host);
		th.setup();
		th.addTab(th
				.newTabSpec("first")
				.setIndicator("tab1",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.first));
		th.addTab(th.newTabSpec("second").setIndicator("tab2", null)
				.setContent(R.id.second));
		th.addTab(th.newTabSpec("third").setIndicator("tab3")
				.setContent(R.id.third));
		th.addTab(th.newTabSpec("forth").setIndicator("tab4")
				.setContent(R.id.forth));
		
		IntentFilter filter=new IntentFilter();
	     filter.addAction("com.update.count");
	     receiver=new MyReceiver();
		registerReceiver(receiver, filter);
	}

	public void initView() {
		bt_start = (Button) findViewById(R.id.bt_start);
		bt_stop = (Button) findViewById(R.id.bt_stop);
		bt_bind = (Button) findViewById(R.id.bt_bind);
		bt_unbind = (Button) findViewById(R.id.bt_unbind);
		tv_count=(TextView) findViewById(R.id.tv_count);
		bt_start.setOnClickListener(this);
		bt_stop.setOnClickListener(this);
		bt_bind.setOnClickListener(this);
		bt_unbind.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent mIntent=new Intent(MainActivity.this, com.test.android.service.CountService.class);
		Intent nIntent=new Intent(MainActivity.this, com.test.android.service.BindCountService.class);
		nIntent.putExtra("count", count);
		switch (v.getId()) {
		case R.id.bt_start:
              startService(mIntent);
			break;
		case R.id.bt_stop:
              stopService(mIntent);
			break;
		case R.id.bt_bind:
			bindService(nIntent, conn, Context.BIND_AUTO_CREATE);
			break;
		case R.id.bt_unbind:
             unbindService(conn);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		unregisterReceiver(receiver);
		
		super.onStop();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	

	public class MyReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			 count=intent.getIntExtra("count", 0);
			System.out.println("π„≤•Ω” ’£∫"+count);
		  	tv_count.setText(count+"");
		}
		
		
	}
}
