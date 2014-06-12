package com.fmlditital.emp.video;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;
import android.widget.LinearLayout.LayoutParams;

import com.fmlditital.emp.R;
import com.fmlditital.emp.model.Global;

public class VideoPlayerActivity extends Activity {

	private VideoView videoView;

	private int i = 0;
	private ProgressBar progressBar;
	private MediaController controller = null;

	private String url = null;

	private String file = null;
	
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.videoplayer);

		url = this.getIntent().getExtras().getString("url");

		file = this.getIntent().getExtras().getString("file");

		videoView = (VideoView) findViewById(R.id.videoplayer_videoView);
		progressBar = (ProgressBar) findViewById(R.id.videoplayer_progress);

		controller = new MediaController(this);

		this.videoView.setMediaController(controller);
		if (file != null) {
			if (file.contains(Global.THE_APP_FILE_NAME))
				videoView.setVideoURI(Uri.parse(file));
		} else
			videoView.setVideoURI(Uri.parse(url));
		videoView.requestFocus();

		videoView.setOnPreparedListener(new OnPreparedListener() {

			public void onPrepared(MediaPlayer mp) {
				progressBar.setVisibility(View.GONE);
				// imageView.setVisibility(View.GONE);
				// mp.start();
				// Log.v("mplayer",
				// ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>videoView OnPreparedListener()");
			}
		});

		// play over
		videoView.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				VideoPlayerActivity.this.finish();
			}
		});

		controller.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				return true;

			}

		});
	}

	public void onResume() {
		super.onResume();
		videoView.seekTo(i);
		videoView.start();
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			VideoPlayerActivity.this.finish();
		}
		return super.onKeyUp(keyCode, event);
	}

	protected void onStop() {
		super.onStop();
		videoView.pause();

		i = videoView.getCurrentPosition();

	}

	public void onPause() {
		super.onPause();
		videoView.pause();
	}

}
