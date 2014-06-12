package com.fmlditital.emp.manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.music.MusicPlayer;

public class MusicManage {

	private static MusicManage musicManage = null;

	private static MusicPlayer player = null;

	private int Duration = 0;
	private int currentPoint = 0, bufferingUpdatePpercent = 0;

	private int index = 0, seekTo = 0;

	private static boolean isUpdateSeekBar = false;
	private int mTimerTime = 500;

	private static List<BaseModel> musicData = null;

	public static boolean musicStatic = false;

	public static MusicManage getMusicManage(List<BaseModel> data) {
		// if (musicData == null)
		// musicData = new ArrayList<BaseModel>();
		musicData = new ArrayList(data);
		Collections.copy(musicData, data);

		if (musicManage == null)
			musicManage = new MusicManage();
		if (player == null)
			player = new MusicPlayer(musicData);

		musicStatic = false;

		return musicManage;
	}

	private MusicManage() {
		super();
	}

	final Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: {
				player.play(index);
			}
				break;
			case 2: {
				player.pause();
			}
				break;
			case 3: {
				player.seekTo(seekTo);
			}
				break;
			case 4: {
				player.playNext();
			}
				break;
			case 5: {
				updateMusic();
			}
				break;

			case 6: {
				player.replay();
			}
				break;
			}
			super.handleMessage(msg);
		}

	};

	Runnable runSeekBarUpdate = new Runnable() {

		@Override
		public void run() {

			if (player.isMediaPlayerPlay()) {
				updateMusic();
			}

			handler.postDelayed(this, mTimerTime);
		}

	};

	public void playMusic(int flag) {

		index = flag;

		musicStatic = true;

		Runnable run = new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}

		};
		handler.post(run);

		// for update seekBar
		if (isUpdateSeekBar == false) {
			isUpdateSeekBar = true;
			handler.postDelayed(runSeekBarUpdate, 1000);
		}

	}

	public void pauseMusic() {
		Runnable run = new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 2;
				handler.sendMessage(message);
			}

		};
		handler.post(run);

	}

	public void replayMusic() {

		Runnable run = new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 6;
				handler.sendMessage(message);
			}

		};
		handler.post(run);
	}

	public void seek_to(int flag) {
		seekTo = flag;
		Runnable run = new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 3;
				handler.sendMessage(message);
			}

		};

		handler.post(run);

	}

	public void nextMusic() {

		musicStatic = true;

		Runnable run = new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 4;
				handler.sendMessage(message);
			}

		};

		handler.post(run);
	}

	public void updateMusic() {

		currentPoint = player.getCurPosition();
		Duration = player.getDuration();
		bufferingUpdatePpercent = player.getBufferingUpdatePpercent();

		if (!player.isMediaPlayerPlay()) {
			// seekBar.setProgress(0);
			return;
		}

		int publishProgress = ((int) (currentPoint * 100.0f / Duration));
//		int updatePpercent = ((int) (bufferingUpdatePpercent * 100.0f / Duration));

		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>publishProgress "
				+ publishProgress);
		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>bufferingUpdatePpercent " + bufferingUpdatePpercent);

		if (DefaultActivity.seekBar != null
				&& DefaultActivity.musicTitleView != null) {
			// Log.v("url", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>publishProgress "
			// +publishProgress);
			DefaultActivity.seekBar.setProgress(publishProgress);
			DefaultActivity.seekBar.setSecondaryProgress(bufferingUpdatePpercent);
			DefaultActivity.musicTitleView.setText(player.getMusicTitle());
			// seekBar.setSecondaryProgress(publishProgress+10);
		}

	}

	public void stop() {
		if (runSeekBarUpdate != null)
			handler.removeCallbacks(runSeekBarUpdate);
		player.stop();
		isUpdateSeekBar = false;
	}
}
