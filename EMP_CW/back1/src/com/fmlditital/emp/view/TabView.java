package com.fmlditital.emp.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.interf.CommentInteractCallback;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.tool.Tools;

public class TabView extends TableLayout {
	// private List<TouchModel> list = null;
	private List<MyImageButton> list = null;
	private UIConfig uiConfig = null;

	private TableRow tableRow = null;
	private Context context = null;

	private ImageView iv = null;
	private TextView tv = null;
	private String navigationType = Confi.getInstance().getAPP_Type();
	private MyImageButton myImageButton;

	// private int selectIndex = 0;

	public TabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		if (list == null)
			list = new ArrayList<MyImageButton>();

		this.context = context;
		// this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// Tools.dip2px(context, 50)));
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		this.setStretchAllColumns(true);
		tableRow = new TableRow(context);
		tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		uiConfig = Confi.getInstance().getuIConfig();

		this.addView(tableRow);

	}

	private CommentInteractCallback commentCallback = null;

	public void addCommentCallback(CommentInteractCallback commentCallback) {
		this.commentCallback = commentCallback;
	}

	public void addTitle(final String title) {

		myImageButton = new MyImageButton(context);
		myImageButton.setTextViewText(title);
		// myImageButton.setTextViewColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));
		myImageButton.setTextSize(16);
		myImageButton.setHeight(Tools.dip2px(context, 38));
		// TouchModel touchModel = new TouchModel();
		// touchModel.setTitle(title);
		// touchModel.setButton(buildButton(title));
		myImageButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				display(title);
				commentCallback.showDetailOrCommentList(title);
			}
		});
		list.add(myImageButton);

	}

	// private Button buildButton(final String title) {
	// Button button = new Button(context);
	// button.setTextSize(16);
	// button.setHeight(Tools.dip2px(context, 38));
	// button.setPadding(0, 0, 0, 0);
	// button.setText(title);
	// button.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
	// button.setOnClickListener(new OnClickListener() {
	//
	// public void onClick(View v) {
	// display(title);
	// commentCallback.showDetailOrCommentList(title);
	//
	// }
	//
	// });
	//
	// return button;
	// }

	public void display(String title) {

		tableRow.removeAllViews();
		tableRow.setBackgroundColor(Color.parseColor(uiConfig
				.getApp_background_color()));

		for (int i = 0; i < list.size(); i++) {
			MyImageButton myib = list.get(i);
			if (title.equals(myib.getTitle())) { // selected
				if (navigationType.equals(Global.APP_STYLE[0])) { // Music
																	// Template
					if (i == 0) {
						myib.setImageButtonColor(R.drawable.tab_music_01, Color
								.parseColor(uiConfig.getApp_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));
					} else if (i == list.size() - 1) {
						myib.setImageButtonColor(R.drawable.tab_music_04, Color
								.parseColor(uiConfig.getApp_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));
					}

				} else if (navigationType.equals(Global.APP_STYLE[1])) { // Event
																			// Template

					if (i == 0) {
						myib.setImageButtonColor(R.drawable.tab_event_03, Color
								.parseColor(uiConfig.getApp_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_background_color()));

					} else if (i == list.size() - 1) {
						myib.setImageButtonColor(R.drawable.tab_event_04, Color
								.parseColor(uiConfig.getApp_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_background_color()));
					}

				} else if (navigationType.equals(Global.APP_STYLE[2])) {// Movie
																		// Template
					if (i == 0) {
						myib.setImageButtonColor(R.drawable.tab_movie_01, Color
								.parseColor(uiConfig.getApp_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));

					} else if (i == list.size() - 1) {
						myib.setImageButtonColor(R.drawable.tab_movie_03, Color
								.parseColor(uiConfig.getApp_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));
					}

				}
			} else { // unselected
				if (navigationType.equals(Global.APP_STYLE[0])) {
					if (i == 0) {
						myib.setImageButtonColor(R.drawable.tab_music_03, Color
								.parseColor(uiConfig
										.getTopbar_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));
					} else if (i == list.size() - 1) {
						myib.setImageButtonColor(R.drawable.tab_music_02, Color
								.parseColor(uiConfig
										.getTopbar_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));
					}

				} else if (navigationType.equals(Global.APP_STYLE[1])) {
					if (i == 0) {
						myib.setImageButtonColor(R.drawable.tab_event_03, Color
								.parseColor(uiConfig
										.getTopbar_background_color()));

						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));

					} else if (i == list.size() - 1) {
						myib.setImageButtonColor(R.drawable.tab_event_04, Color
								.parseColor(uiConfig
										.getTopbar_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));

					}

				} else if (navigationType.equals(Global.APP_STYLE[2])) {
					if (i == 0) {
						myib.setImageButtonColor(R.drawable.tab_movie_03, Color
								.parseColor(uiConfig
										.getTopbar_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));
					} else if (i == list.size() - 1) {
						myib.setImageButtonColor(R.drawable.tab_movie_02, Color
								.parseColor(uiConfig
										.getTopbar_background_color()));
						myib.setTextViewColor(Color.parseColor(uiConfig
								.getTopbar_text_color()));
					}

				}

			}
			tableRow.addView(myib);
		}
		tableRow.invalidate();
		//
		// for (int i = 0; i < list.size(); i++) {
		// MyImageButton myib = list.get(i);
		// if (title.equals(myib.getTitle())) {
		// if (i == 0) {
		// touchModel.getButton()
		// .setBackgroundColor(
		// Color.parseColor(uiConfig
		// .getApp_background_color()));
		// touchModel.getButton().setTextColor(
		// Color.parseColor(uiConfig
		// .getTopbar_background_color()));
		// }
		//
		// else if (i == list.size() - 1) {
		// // touchModel.getTextView().setBackgroundResource(
		// // R.drawable.aaa);
		//
		// touchModel.getButton()
		// .setBackgroundColor(
		// Color.parseColor(uiConfig
		// .getApp_background_color()));
		// touchModel.getButton().setTextColor(
		// Color.parseColor(uiConfig
		// .getTopbar_background_color()));
		// // touchModel.getButton().setBackgroundColor(
		// // context.getResources().getColor(R.color.red));
		// }
		// } else {
		//
		// touchModel.getButton()
		// .setBackgroundColor(
		// Color.parseColor(uiConfig
		// .getTopbar_background_color()));
		// touchModel.getButton().setTextColor(
		// Color.parseColor(uiConfig.getApp_background_color()));
		// // touchModel.getTextView().setBackgroundResource(R.drawable.bb);
		// // touchModel.getButton().setBackgroundColor(
		// // context.getResources().getColor(R.color.white));
		//
		// }
		// tableRow.addView(touchModel.getButton());
		// }
		//
		// tableRow.invalidate();
	}

}
