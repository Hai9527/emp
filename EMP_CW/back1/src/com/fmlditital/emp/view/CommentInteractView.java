package com.fmlditital.emp.view;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.interf.CommentInteractCallback;
import com.fmlditital.emp.interf.JsonInterface;
import com.fmlditital.emp.interf.SawCallback;

public class CommentInteractView extends LinearLayout implements JsonInterface,
		OnClickListener {
	// , SawCallback {

	private EMPApp app = EMPApp.getSingleton();

	public static enum CommentInteractStyle {
		saw, listen, photo
	}

	private Map<String, String> sawMap = app.getSawMap();
	private Map<String, String> likeMap = app.getLikeMap();

	private CommentInteractStyle commentInteractStyle;
	private Context context;
	private TableLayout tableLayout = null;
	private ImageView sawImageView, likeImageView, editImageView;
	private TextView sawTextView, likeTextView, editTextView;
	private LayoutInflater factory = null;
	private String tid = null, data_id = null;
	private String[] insertCommentType = { "1", "2" }; // 1 for saw or play 2
														// for like

	private String countUrl = null, likeInsertURl = null, sawInsertUrl = null;
	private static AsyncJson countAsync = null, likeAsync = null,
			sawAsync = null;

	private CommentInteractCallback commentCallback = null;

	public void addCommentCallback(CommentInteractCallback commentCallback) {
		this.commentCallback = commentCallback;
	}


	public CommentInteractView(Context context, String tid, String data_id,
			CommentInteractStyle commentInteractStyle) {
		super(context);

		this.tid = tid;
		this.data_id = data_id;
		factory = LayoutInflater.from(context);
		tableLayout = (TableLayout) factory.inflate(
				R.layout.comment_interact_view, null);
		this.addView(tableLayout);
		this.context = context;
		this.commentInteractStyle = commentInteractStyle;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		// this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// 45));
		index();

		countUrl = Confi.getInstance().getAllCountsApiUrl(tid, data_id);
		likeInsertURl = Confi.getInstance().getInsertAccumulativeCountsApiUrl(
				tid, data_id, insertCommentType[1]);
		sawInsertUrl = Confi.getInstance().getInsertAccumulativeCountsApiUrl(
				tid, data_id, insertCommentType[0]);

		if (commentInteractStyle == CommentInteractStyle.photo) {
			if (sawMap.get(tid + data_id) == null
					|| !sawMap.get(tid + data_id).equals(tid + data_id)) {
				sawMap.put(tid + data_id, tid + data_id);
				sawAsync = new AsyncJson(context);
				sawAsync.addInterFace(this);
				sawAsync.execute(sawInsertUrl);
			} else {
				countAsync = new AsyncJson(context);
				countAsync.addInterFace(this);
				countAsync.execute(countUrl);
			}

		} else {
			countAsync = new AsyncJson(context);
			countAsync.addInterFace(this);
			countAsync.execute(countUrl);
		}

	}

	private void index() {
		sawImageView = (ImageView) tableLayout
				.findViewById(R.id.comment_interact_view_imageView_saw);

		sawImageView.setOnClickListener(this);
		likeImageView = (ImageView) tableLayout
				.findViewById(R.id.comment_interact_view_imageView_like);
		likeImageView.setOnClickListener(this);
		editImageView = (ImageView) tableLayout
				.findViewById(R.id.comment_interact_view_imageView_edit);
		editImageView.setOnClickListener(this);
		sawTextView = (TextView) tableLayout
				.findViewById(R.id.comment_interact_view_textView_sawcount);
		likeTextView = (TextView) tableLayout
				.findViewById(R.id.comment_interact_view_textView_likecount);
		editTextView = (TextView) tableLayout
				.findViewById(R.id.comment_interact_view_textView_editcount);

		/**
		 * icon color
		 * */
		if (Confi.getInstance().getuIConfig().getApp_icon_type() .equals("0")) {
			if (commentInteractStyle == CommentInteractStyle.saw) {
				sawImageView.setImageResource(R.drawable.eye_black);
			} else if (commentInteractStyle == CommentInteractStyle.listen) {
				sawImageView.setImageResource(R.drawable.music_listen_black);
			} else if (commentInteractStyle == CommentInteractStyle.photo) {
				sawImageView.setImageResource(R.drawable.eye_black);
			}

			likeImageView.setImageResource(R.drawable.good_black);
			editImageView.setImageResource(R.drawable.comment_black);
		} else if (Confi.getInstance().getuIConfig().getApp_icon_type() .equals("1")) {
			if (commentInteractStyle == CommentInteractStyle.saw) {
				sawImageView.setImageResource(R.drawable.eye_gray);
			} else if (commentInteractStyle == CommentInteractStyle.listen) {
				sawImageView.setImageResource(R.drawable.music_listen_gray);
			} else if (commentInteractStyle == CommentInteractStyle.photo) {
				sawImageView.setImageResource(R.drawable.eye_gray);
			}

			likeImageView.setImageResource(R.drawable.good_gray);
			editImageView.setImageResource(R.drawable.comment_gray);

		} else if (Confi.getInstance().getuIConfig().getApp_icon_type() .equals("2")) {

			if (commentInteractStyle == CommentInteractStyle.saw) {
				sawImageView.setImageResource(R.drawable.eye_white);
			} else if (commentInteractStyle == CommentInteractStyle.listen) {
				sawImageView.setImageResource(R.drawable.music_listen_whilte);
			} else if (commentInteractStyle == CommentInteractStyle.photo) {
				sawImageView.setImageResource(R.drawable.eye_white);
			}
			likeImageView.setImageResource(R.drawable.good_white);
			editImageView.setImageResource(R.drawable.comment_white);
		}

		/**
		 * font color
		 * */
		sawTextView.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getApp_text_color()));
		likeTextView.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getApp_text_color()));
		editTextView.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getApp_text_color()));

	}

	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		if (url.equals(countUrl)) {
			try {
				if (json.getString("statusid").equals("1")) {
					sawTextView.setText("0");
					likeTextView.setText("0");
					editTextView.setText("0");
				} else if (json.getString("statusid").equals("0")) {
					JSONArray jsonArray = json.getJSONArray("datas");
					JSONObject jSONObject = (JSONObject) jsonArray.opt(0);
					sawTextView.setText(jSONObject.getString("players_counts"));
					likeTextView.setText(jSONObject.getString("likes_counts"));
					editTextView
							.setText(jSONObject.getString("comment_counts"));
				}
			} catch (JSONException e) {
				callJoinFailure(url, e.getMessage().toString());
			}
		}

		if (url.equals(likeInsertURl)) {
			try {
				if (json.getInt("status") == 1) {
					JSONObject jSONObject = json.getJSONObject("data");
					likeTextView.setText(jSONObject.getString("likes_count"));
				}
			} catch (JSONException e) {
				callJoinFailure(url, e.getMessage().toString());
			}
		}

		if (url.equals(sawInsertUrl)) {
			try {
				if (json.getInt("status") == 1) {
					JSONObject jSONObject = json.getJSONObject("data");
					sawTextView.setText(jSONObject.getString("player_count"));
					likeTextView.setText(jSONObject.getString("likes_count"));
					editTextView
					.setText(jSONObject.getString("comment_count"));
					sawTextView.invalidate();
				}
			} catch (JSONException e) {
				callJoinFailure(url, e.getMessage().toString());
			}
		}

	}

	@Override
	public void callJoinFailure(String url, String failMessage) {

	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();

		switch (vid) {
		case R.id.comment_interact_view_imageView_saw: {

			if (commentInteractStyle != CommentInteractStyle.photo) {
				if (sawMap.get(tid + data_id) == null
						|| !sawMap.get(tid + data_id).equals(tid + data_id)) {
					sawMap.put(tid + data_id, tid + data_id);
					sawAsync = new AsyncJson(context);
					sawAsync.addInterFace(this);
					sawAsync.execute(sawInsertUrl);
				}
				commentCallback.actionCallback();
			}


		}
			break;
		case R.id.comment_interact_view_imageView_like: {
			if (likeMap.get(tid + data_id) == null
					|| !likeMap.get(tid + data_id).equals(tid + data_id)) {
				likeMap.put(tid + data_id, tid + data_id);
				likeAsync = new AsyncJson(context);
				likeAsync.addInterFace(this);
				likeAsync.execute(likeInsertURl);
			}

		}
			break;

		case R.id.comment_interact_view_imageView_edit: {
			commentCallback.editCallback();
		}
			break;

		}
	}

	@Override
	public void callJoinMore(String url, JSONObject json) {
		// TODO Auto-generated method stub

	}
}
