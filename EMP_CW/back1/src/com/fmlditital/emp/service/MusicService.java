package com.fmlditital.emp.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.model.MusicModel;
import com.fmlditital.emp.music.MusicPlayState;
import com.fmlditital.emp.music.MusicPlayer;

public class MusicService extends Service {
	private EMPApp app = EMPApp.getSingleton();

	// private List<MusicModel> data = app.getDataMusic();
	int index = 0;
	int seekTo = 0;

	public final static String Broadcast = "com.fmlditital.emp.service";
//	Timer timer = new Timer();
//	final Handler handler = new Handler() {
//
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 1: {
//				send();
//			}
//				break;
//			}
//			super.handleMessage(msg);
//		}
//
//	};
//	TimerTask task = new TimerTask() {
//
//		@Override
//		public void run() {
//			// Log.v("",
//			// ">>>>>>>>>>>>>>>>>DownloadPosterActivity TimerTask run()");
//			Message message = new Message();
//			message.what = 1;
//			handler.sendMessage(message);
//
//		}
//	};

	private      MusicPlayer player= null;
//
//	public static MusicPlayer getPlayer() {
//		return player;
//	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
//		 player = new MusicPlayer(this);
//		 player = new MusicPlayer();
//		timer.schedule(task, 0, 500);
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		String playFlag = intent.getExtras().getString("playFlag");
		if (playFlag.equals(MusicPlayState.MUSIC_PLAY)) {
			index = intent.getExtras().getInt("index");
			playMusic();
		}

		else if (playFlag.equals(MusicPlayState.MUSIC_SEEK_TO)) {
			seekTo = intent.getExtras().getInt("progress");
			seek_to();
		} else if (playFlag.equals(MusicPlayState.MUSIC_NEXT)) {
			nextMusic();
		}else if (playFlag.equals(MusicPlayState.MUSIC_PAUSE)) {
			pauseMusic();
		}

		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
//		timer.cancel();
		player.stop();
		super.onDestroy();
	}

	private void playMusic() {
		player.play(index);
	}
	
	private void pauseMusic() {
		player.pause();
	}

	private void seek_to() {
		player.seekTo(seekTo);
	}
	
	private void nextMusic() {
		player.playNext();
	}

//	private void send() {
//		Intent intent = new Intent();
//		Bundle bundle = new Bundle();
//		bundle.putInt("CurPosition", player.getCurPosition());
//		bundle.putInt("Duration", player.getDuration());
//		intent.putExtras(bundle);
//		intent.setAction(Broadcast);
//		sendBroadcast(intent);
//	}
}
