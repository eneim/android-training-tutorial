package im.ene.zintenttutorial.services;

import im.ene.zintenttutorial.MainActivity;
import im.ene.zintenttutorial.broadcastreceiver.CustomBroadcastReceiver;
import im.ene.zintenttutorial.broadcastreceiver.CustomBroadcastReceiver.OnBroadcastReceiveListener;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CustomService extends Service implements
		OnBroadcastReceiveListener {

	private CustomBroadcastReceiver mReceiver;

	private IntentFilter mIntentFilter;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		mReceiver = new CustomBroadcastReceiver();
		mReceiver.setOnBroadcastReceiveListener(this);

		mIntentFilter = new IntentFilter();

		mIntentFilter.addAction(MainActivity.PRESS_TOPLEFT_BUTTON);
		mIntentFilter.addAction(MainActivity.PRESS_TOPRIGHT_BUTTON);
		mIntentFilter.addAction(MainActivity.PRESS_MIDLEFT_BUTTON);
		mIntentFilter.addAction(MainActivity.PRESS_MIDRIGHT_BUTTON);

		registerReceiver(mReceiver, mIntentFilter);

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(getBaseContext(), "started", Toast.LENGTH_SHORT).show();
		Log.d("service", "started");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onBroadcastReceiveUpdateUI(Intent intent) {
		String action = intent.getAction();

		// compatible with KitKat and above, to avoid errors, you should use
		// if/else instead;

		switch (action) {
		case MainActivity.PRESS_TOPLEFT_BUTTON:
			Toast.makeText(getBaseContext(), "top-left button is clicked",
					Toast.LENGTH_SHORT).show();
			break;

		case MainActivity.PRESS_MIDRIGHT_BUTTON:
			Toast.makeText(getBaseContext(), "mid-right button is clicked",
					Toast.LENGTH_SHORT).show();
			break;
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/* stop/pause receiving broadcast from activity */
		try {
			unregisterReceiver(mReceiver);
		} catch (Exception er) {
			er.printStackTrace();
		}
	}
}
