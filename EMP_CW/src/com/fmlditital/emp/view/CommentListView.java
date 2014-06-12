package com.fmlditital.emp.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.CommentListAdapter;
import com.fmlditital.emp.adapter.CommentListTwoAdapter;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.comment.CommentManage;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.interf.AddCommentCallback;
import com.fmlditital.emp.interf.JsonInterface;
import com.fmlditital.emp.interf.LoginfinishCallback;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.CommentModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.share.AuthorWeiboActivity;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.ShareText;
import com.fmlditital.emp.tool.NetWorkState;

public class CommentListView extends LinearLayout implements JsonInterface,
		LoginfinishCallback, OnClickListener {
	private static final String LAYOUT_INFLATER_SERVICE = null;

	private Context context;

	private LinearLayout linearLayout = null;
	private LayoutInflater factory = null;

	private String tid = null, data_id = null, data_name = null;
	private ImageView addImageView = null;
	private TextView addCommentTextView = null;

	private List<BaseModel> commentData = null;
	private ListView commentListView = null;
	// private CommentListAdapter commentListAdapter = null;
	// private CommentListTwoAdapter commentListTwoAdapter = null;
	private BaseAdapter commentListAdapter = null;
	private String commentListUrl = null, insetCommenturl = null;
	private String commentContext = null;

	private AsyncJson commentListAsync = null, insetAsync = null;

	private AddCommentCallback addCommentCallback = null;

	private Dialog alertDialog;

	private Button CommentDialogButAdd, CommentDialogButCanel;

	private LinearLayout CommentDialogLinearLayout, CommentDialogOutlayout;

	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	private LinearLayout.LayoutParams CommentDialogLayout = null;

	private AlertDialog weiboDialog;

	private static String appStyle = Confi.getInstance().getAPP_Type();

	private EditText editText;

	private int num = 140;

	private TextView txtNo;
    private CommentModel data;
	public void setAddCallback(AddCommentCallback addCommentCallback) {
		this.addCommentCallback = addCommentCallback;
	}

	public CommentListView(Context context, String tid, String data_id,
			String data_name) {
		super(context);

		factory = LayoutInflater.from(context);
		linearLayout = (LinearLayout) factory.inflate(R.layout.commentlistview,
				null);
		this.context = context;
		this.tid = tid;
		this.data_id = data_id;
		this.data_name = data_name;

		this.addView(linearLayout);
		index();

		if (appStyle.equals(Global.APP_STYLE[2])) {
			commentData = new ArrayList<BaseModel>();
			commentListAdapter = new CommentListTwoAdapter(context, commentData);
			commentListView.setAdapter(commentListAdapter);

			commentListView
					.setDivider(new ColorDrawable(Color.parseColor(Confi
							.getInstance().getuIConfig()
							.getTopbar_background_color())));
			commentListView.setDividerHeight(2);
			commentListUrl = Confi.getInstance().getCommentListApiUrl(tid,
					data_id);

			commentListAsync = new AsyncJson(context);
			commentListAsync.addInterFace(this);
			commentListAsync.execute(commentListUrl);
		} else {
			// for comment
			commentData = new ArrayList<BaseModel>();
			commentListAdapter = new CommentListAdapter(context, commentData);
			commentListView.setAdapter(commentListAdapter);

			commentListView
					.setDivider(new ColorDrawable(Color.parseColor(Confi
							.getInstance().getuIConfig()
							.getTopbar_background_color())));
			commentListView.setDividerHeight(2);
			commentListUrl = Confi.getInstance().getCommentListApiUrl(tid,
					data_id);

			commentListAsync = new AsyncJson(context);
			commentListAsync.addInterFace(this);
			commentListAsync.execute(commentListUrl);
		}
	}

	private void index() {
		commentListView = (ListView) linearLayout
				.findViewById(R.id.commentlistview_listView);
		addCommentTextView = (TextView) linearLayout
				.findViewById(R.id.commentlistview_textView_add);
		addImageView = (ImageView) linearLayout
				.findViewById(R.id.commentlistview_imageView_add);
		addCommentTextView.setOnClickListener(this);
		addImageView.setOnClickListener(this);

		/**
		 * list Comment color
		 * */
		addCommentTextView.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getApp_text_color()));

	}

	public void onClick(View v) {
		int vid = v.getId();
		switch (vid) {
		case R.id.commentlistview_textView_add:
		case R.id.commentlistview_imageView_add: {
			if (NetWorkState.isNetworkAvailable(context)) {
				addComment(tid, data_id, data_name);
			} else {
				final AlertDialog alertDialog = new AlertDialog.Builder(context)
						.create();
				alertDialog.show();
				alertDialog.setContentView(R.layout.commentwifidialog);
				LinearLayout commentListBg = (LinearLayout) alertDialog
						.findViewById(R.id.wifidialog_linearlayout_bg);
				commentListBg.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));
				commentListBg.getBackground().setAlpha(150);
				TextView txtTitle = (TextView) alertDialog
						.findViewById(R.id.wifidialog_textview_title);
				txtTitle.setText(R.string.check_network_message);
				txtTitle.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));
				// æŽ¥æ”¶OKButton
				Button dialogBtnOk = (Button) alertDialog
						.findViewById(R.id.wifidialog_button_ok);
				// ä¿®æ”¹OKButtonå­—ä½“é¢œè‰²
				dialogBtnOk.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));
				// ä¿®æ”¹OKButtoné¢œè‰²
				dialogBtnOk.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));
				dialogBtnOk.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						context.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
						alertDialog.cancel();
					}
				});
				// æŽ¥æ”¶CanelButton
				Button dialogBtnCanel = (Button) alertDialog
						.findViewById(R.id.wifidialog_button_cancel);
				// ä¿®æ”¹CanelButtonå­—ä½“é¢œè‰²
				dialogBtnCanel.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));
				// è®¾ç½®CanelButtoné¢œè‰²
				dialogBtnCanel.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));
				// CanelButtonç»‘å®šç›‘å�¬å™¨
				dialogBtnCanel.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						alertDialog.cancel();
					}
				});
				// AlertDialog.Builder builder = new Builder(context);
				// // builder.setIcon(null);
				// // builder.setTitle(R.string.check_network_title);
				// builder.setMessage(context.getResources().getString(
				// R.string.check_network_message));
				// builder.setPositiveButton(R.string.yes,
				// new DialogInterface.OnClickListener() {
				// 
				// public void onClick(DialogInterface dialog,
				// int which) {
				// dialog.dismiss();
				// context.startActivity(new Intent(
				// Settings.ACTION_WIRELESS_SETTINGS));
				// }
				// });
				// builder.setNegativeButton(R.string.cancel,
				// new DialogInterface.OnClickListener() {
				//
				// 
				// public void onClick(DialogInterface dialog,
				// int which) {
				// dialog.dismiss();
				// }
				//
				// });
				// builder.create().show();
			}

		}
			break;
		}
	}

	private void addComment(String tid, String data_id, String data_name) {
		if (Confi.getInstance().isWeiBoLogin()) {
			// CommentManage.getInstance().showDialog(context, tid, data_id,
			// data_name);
			showDialog(tid, data_id, data_name);
		} else {
			Intent intent = new Intent(context, AuthorWeiboActivity.class);
			intent.putExtra(CommentManage.COMMENT_MANAGE_LOGIN_FLAG,
					CommentManage.COMMENT_MANAGE_LOGIN_FLAG);
			AuthorWeiboActivity.addLoginfinishCallback(this);

			context.startActivity(intent);
		}
	}

	private void showDialog(final String tid, final String dataId,
			final String data_name) {
		alertDialog = new Dialog(context);
		// LayoutInflater factory = LayoutInflater.from(context);
		// final View view = factory.inflate(R.layout.commentdialog, null);
		// alertDialog.setView(view);
		alertDialog.show();

		alertDialog.setContentView(R.layout.commentdialog);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		CommentDialogLayout = new LinearLayout.LayoutParams(
				DefaultActivity.getScreenWidth() / 112 * 100,
				LayoutParams.WRAP_CONTENT);
		CommentDialogLinearLayout = (LinearLayout) alertDialog
				.findViewById(R.id.commentdialog_LinearLayout_bg);

		CommentDialogLinearLayout.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		CommentDialogLinearLayout.getBackground().setAlpha(150);

		CommentDialogLinearLayout.setLayoutParams(CommentDialogLayout);

		TextView CommentDialogTitle = (TextView) alertDialog
				.findViewById(R.id.commentdialog_TextView_Title);
		CommentDialogTitle.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		CommentDialogButAdd = (Button) alertDialog
				.findViewById(R.id.commentdialog_Button_butAdd);
		CommentDialogButAdd.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		CommentDialogButAdd.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		CommentDialogButCanel = (Button) alertDialog
				.findViewById(R.id.commentdialog_Button_butCancel);
		CommentDialogButCanel.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		CommentDialogButCanel.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));

		txtNo = (TextView) alertDialog
				.findViewById(R.id.commentdialog_TextView_txtNo);
		txtNo.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		txtNo.setText("140");

		editText = (EditText) alertDialog
				.findViewById(R.id.commentdialog_editText);
		editText.setSelection(0);
		editText.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;

			public void afterTextChanged(Editable s) {
				int number = num - s.length();
				txtNo.setText("" + number);
				selectionStart = editText.getSelectionStart();
				selectionEnd = editText.getSelectionEnd();
				if (temp.length() > num) {
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					editText.setText(s);
					editText.setSelection(tempSelection);
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				temp = s;
			}

		});
		CommentDialogButAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				commentContext = editText.getText().toString();

				if (!commentContext.equals("")) {

					CommentModel commentModel = new CommentModel();
					commentModel.setCommentDetail(URLEncoder
							.encode(commentContext));
//					Log.v("", commentModel.getCommentDetail()+"=======>>>>>>>>>>>>>>>>>insetCommenturl "+insetCommenturl);
					commentModel = CommentManage.getInstance()
							.packagedCommentModel(commentModel);
					insetCommenturl = Confi.getInstance()
							.getinsertCommentApiUrl(tid, dataId, data_name,
									commentModel);

					Log.v("", commentModel.getUserName()+"======="+data_name+">>>>>>>>>>>>>>>>>insetCommenturl "+insetCommenturl);
					
					insetAsync = new AsyncJson(context);
					insetAsync.addInterFace(CommentListView.this);
					insetAsync.execute(insetCommenturl);
					alertDialog.cancel();
				} else {
					LayoutInflater factory = LayoutInflater.from(context);
					View view = factory.inflate(R.layout.mytoast, null);
					Toast myToast = new Toast(context);
					myToast.setView(view);
					LinearLayout myToastBg = (LinearLayout) view
							.findViewById(R.id.myToastBg);
					myToastBg.setBackgroundColor(Color.parseColor(uiConfig
							.getTopbar_background_color()));
					TextView txtToast = (TextView) view
							.findViewById(R.id.myToast_TextView_txtToast);
					//txtToast.setText("Please add comment text.");
					txtToast.setText("请输入评论.");
					txtToast.setTextColor(Color.parseColor(uiConfig
							.getTopbar_text_color()));
					myToast.setGravity(Gravity.TOP, 0, 200);
					myToast.show();

					// Toast.makeText(context, "please add comment context",
					// Toast.LENGTH_LONG).show();
				}
			}

		});
		CommentDialogButCanel.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View v) {
				alertDialog.cancel();
			}
		});

		// LayoutInflater factory = LayoutInflater.from(context);
		// final View view = factory.inflate(R.layout.commentdialog, null);
		// view.setBackgroundColor(R.color.green);
		// AlertDialog dialog = new AlertDialog.Builder(context)
		// // .setIcon(android.R.drawable.btn_star)
		// .setView(view)
		// .setPositiveButton(R.string.add,
		// new DialogInterface.OnClickListener() {
		//
		// 
		// public void onClick(DialogInterface dialog,
		// int which) {
		//
		// EditText editText = (EditText) view
		// .findViewById(R.id.commentdialog_editText);
		//
		// commentContext = editText.getText().toString();
		//
		// if (!commentContext.equals("")) {
		//
		// CommentModel commentModel = new CommentModel();
		// commentModel.setCommentDetail(URLEncoder
		// .encode(commentContext));
		// commentModel = CommentManage.getInstance()
		// .packagedCommentModel(commentModel);
		// insetCommenturl = Confi.getInstance()
		// .getinsertCommentApiUrl(tid,
		// dataId, data_name,
		// commentModel);
		//
		// insetAsync = new AsyncJson(context);
		// insetAsync
		// .addInterFace(CommentListView.this);
		// insetAsync.execute(insetCommenturl);
		//
		// } else {
		// Toast.makeText(context,
		// "please add comment context",
		// Toast.LENGTH_LONG).show();
		// }
		//
		// }
		//
		// })
		// .setNegativeButton(R.string.cancel,
		// new DialogInterface.OnClickListener() {
		//
		// 
		// public void onClick(DialogInterface dialog,
		// int which) {
		// }
		//
		// }).create();
		// dialog.show();
	}

	private LayoutInflater getSystemService(String layoutInflaterService) {
		return null;
	}

	public void callJoinSuccess(String url, JSONObject json) {
		Log.d("::::"+url.equals(commentListUrl), "user_name================ "+url);
		if (url.equals(commentListUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");
				Log.d("::::"+jsonArray.length(), "jsonArray.length()================ ");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					CommentModel model = new CommentModel();
					model.setUserId(jsonObject2.getString("user_id"));
					model.setUserName(jsonObject2.getString("user_name"));
					model.setUserPortrait(jsonObject2.getString("user_avatar"));
					model.setCommentTime(jsonObject2.getString("time"));
					model.setCommentDetail(jsonObject2
							.getString("comment_detail"));
					commentData.add(model);
					Log.d("", "user_name================ "+jsonObject2.getString("user_name"));
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
				callJoinFailure(url, e.getMessage().toString());
			}
			commentListAdapter.notifyDataSetChanged();
		} else if (url.equals(insetCommenturl)) {
			String result = null;
			try {
				result = json.getString("status");
			} catch (JSONException e) {
				e.printStackTrace();
				callJoinFailure(url, e.getMessage().toString());
			}

			if (result.equals("1")) {

				addCommentCallback.addCommentCallback(commentListUrl);

				commentData.clear();
				commentListAsync = new AsyncJson(context);
				commentListAsync.addInterFace(this);
				commentListAsync.execute(commentListUrl);
				shareToWeiboDialog();
			}
		}
	}

	private void shareToWeiboDialog() {
		weiboDialog = new AlertDialog.Builder(context).create();
		weiboDialog.show();
		weiboDialog.setContentView(R.layout.weibodialog);
		LinearLayout weibolayout = (LinearLayout) weiboDialog
				.findViewById(R.id.weiboLayout);
		weibolayout.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		weibolayout.getBackground().setAlpha(150);
		TextView txtTitle = (TextView) weibolayout
				.findViewById(R.id.weiboDialog_textView_txtTitle);
		txtTitle.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		Button weiboButShare = (Button) weiboDialog
				.findViewById(R.id.weiboDialog_button_share);
		weiboButShare.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		weiboButShare.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		weiboButShare.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				shareTo();
				weiboDialog.cancel();
			}
		});
		Button weiboButNo = (Button) weiboDialog
				.findViewById(R.id.weiboDialog_button_No);
		weiboButNo.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		weiboButNo.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		weiboButNo.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				weiboDialog.cancel();
			}
		});
		// AlertDialog dialog = new AlertDialog.Builder(context)
		// .setMessage(R.string.if_share_to_weibo)
		// .setPositiveButton(R.string.share,
		// new DialogInterface.OnClickListener() {
		//
		// 
		// public void onClick(DialogInterface dialog,
		// int which) {
		// shareTo();
		// }
		//
		// })
		// .setNegativeButton(R.string.no_thanks,
		// new DialogInterface.OnClickListener() {
		//
		// 
		// public void onClick(DialogInterface dialog,
		// int which) {
		// }
		//
		// }).create();
		// dialog.show();
	}

	private void shareTo() {
		ShareInfo shareInfo = new ShareText();
		shareInfo.constructContext(commentContext);
		ShareManage.getInstance().share2weibo((Activity) context, shareInfo);
	}

	public void callJoinFailure(String url, String failMessage) {

	}

	public void loginfinishCallback() {
		showDialog(tid, data_id, data_name);
	}

	public void callJoinMore(String url, JSONObject json) {

	}

}
