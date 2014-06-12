package com.fmlditital.emp.music;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.Log;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.MusicModel;

public class MusicPlayer implements OnCompletionListener, OnErrorListener,
		OnBufferingUpdateListener {

	private EMPApp app = EMPApp.getSingleton();

	public static List<BaseModel> data = null;

	private final static String TAG = "MusicPlayer";

	public final static String BROCAST_NAME = "com.fmlditital.emp.music";

	private MediaPlayer mMediaPlayer= null;

	private  int mCurPlayIndex=0;

//	public static int getmCurPlayIndex() {
//		mCurPlayIndex = reviceIndex(mCurPlayIndex);
//		return mCurPlayIndex;
//	}

	private int mPLayMode;

	private Random mRandom;

	private int bufferingUpdatePpercent = 0;
	
	private static String musicUri=null;


	public  static String getMusicUri() {
		return musicUri;
	}

	private void defaultParam() {
		if (mMediaPlayer == null)
			mMediaPlayer = new MediaPlayer();

		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnBufferingUpdateListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mCurPlayIndex = -1;
	}

	// public MusicPlayer(Context context){
	public MusicPlayer(List<BaseModel> mData) {
		// mContext = context;
		data = mData;
		defaultParam();

		mRandom = new Random();
		mRandom.setSeed(System.currentTimeMillis());
	}

	public boolean replay() {
		mMediaPlayer.start();
		return true;
	}

	public void play(int position) {

		mCurPlayIndex = position;
		mCurPlayIndex = reviceIndex(mCurPlayIndex);

		musicUri=((MusicModel) data
				.get(mCurPlayIndex)).getUrl();
		try {
			if (((MusicModel) data.get(mCurPlayIndex)).getUrl().startsWith(
					"http://")) {
				if (mMediaPlayer == null)
					mMediaPlayer = new MediaPlayer();
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource(((MusicModel) data
						.get(mCurPlayIndex)).getUrl());
				mMediaPlayer.prepare();// prepareä¹‹å�Žè‡ªåŠ¨æ’­æ”¾
				mMediaPlayer.start();
			}

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public boolean pause() {
		mMediaPlayer.pause();
		return true;
	}

	public void stop() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	public void playNext() {

		mCurPlayIndex++;
		mCurPlayIndex = reviceIndex(mCurPlayIndex);
//		Log.v("", ">>>>>>>>>>>>>>>>>>>>mCurPlayIndex) "+mCurPlayIndex);

		musicUri=((MusicModel) data
				.get(mCurPlayIndex)).getUrl();
		
		try {
			mMediaPlayer.reset();
			if (((MusicModel) data.get(mCurPlayIndex)).getUrl().startsWith(
					"http://")) {
				mMediaPlayer.setDataSource(((MusicModel) data
						.get(mCurPlayIndex)).getUrl());
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			}

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public boolean playPre() {

		mCurPlayIndex--;
		mCurPlayIndex = reviceIndex(mCurPlayIndex);

		if (prepare(mCurPlayIndex) == false) {
			return false;
		}

		return replay();
	}

	public void seekTo(int rate) {

		int r = reviceSeekValue(rate);
		int time = mMediaPlayer.getDuration();
		int curTime = (int) ((float) r / 100 * time);
		// mMediaPlayer.reset();
		// mMediaPlayer.prepare();
		mMediaPlayer.seekTo(curTime);

	}

	public int getCurPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	public int getDuration() {

		return mMediaPlayer.getDuration();

	}

	public int getPlayMode() {
		return mPLayMode;
	}

	public static int reviceIndex(int index) {
		if (index < 0) {
			index = data.size() - 1;
		}

		if (index >= data.size()) {
			index = 0;
		}

		return index;
	}

	private int reviceSeekValue(int value) {
		if (value < 0) {
			value = 0;
		}
		if (value > 100) {
			value = 100;
		}
		return value;
	}

	public String getMusicTitle() {
		return ((MusicModel) data.get(mCurPlayIndex)).getTitle();
	}

	private int getRandomIndex() {
		int size = data.size();
		if (size == 0) {
			return -1;
		}
		return Math.abs(mRandom.nextInt() % size);
	}

	private boolean prepare(int index) {
		mCurPlayIndex = index;
		mMediaPlayer.reset();
		String path = ((MusicModel) data.get(mCurPlayIndex)).getUrl();
		try {
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.prepare();

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();

			return false;
		}

		return true;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {

		playNext();

	}

	public boolean isMediaPlayerPlay() {
		return mMediaPlayer.isPlaying();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		bufferingUpdatePpercent = percent;
		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>percent "
				+ percent);
	}

	public int getBufferingUpdatePpercent() {
		return bufferingUpdatePpercent;
	}
	
//	public static int getDataSise() {
//		
//		return data.size();
//	}

}
