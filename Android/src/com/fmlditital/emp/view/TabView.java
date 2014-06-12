package com.fmlditital.emp.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.interf.CommentInteractCallback;
import com.fmlditital.emp.model.TouchModel;
import com.fmlditital.emp.tool.Tools;

public class TabView extends TableLayout {
	private List<TouchModel> list = null;

	private UIConfig uiConfig = null;

	private TableRow tableRow = null;
	private Context context = null;

	// private int selectIndex = 0;

	public TabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		if (list == null)
			list = new ArrayList<TouchModel>();

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

	public void addTitle(String title) {

		TouchModel touchModel = new TouchModel();
		touchModel.setTitle(title);
		touchModel.setButton(buildButton(title));

		list.add(touchModel);

	}

	private Button buildButton(final String title) {
		Button button = new Button(context);
		button.setTextSize(16);
		button.setHeight(Tools.dip2px(context, 38));
		button.setPadding(0, 0, 0, 0);
		button.setText(title);
		button.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				display(title);
				commentCallback.showDetailOrCommentList(title);

			}

		});

		return button;
	}

	public void display(String title) {

		tableRow.removeAllViews();
		tableRow.setBackgroundColor(Color.parseColor(uiConfig
				.getApp_background_color()));

		for (int i = 0; i < list.size(); i++) {
			TouchModel touchModel = list.get(i);
			if (title.equals(touchModel.getTitle())) {
				if (i == 0) {
					touchModel.getButton()
							.setBackgroundColor(
									Color.parseColor(uiConfig
											.getApp_background_color()));
					touchModel.getButton().setTextColor(
							Color.parseColor(uiConfig
									.getTopbar_background_color()));
				}

				else if (i == list.size() - 1) {
					// touchModel.getTextView().setBackgroundResource(
					// R.drawable.aaa);

					touchModel.getButton()
							.setBackgroundColor(
									Color.parseColor(uiConfig
											.getApp_background_color()));
					touchModel.getButton().setTextColor(
							Color.parseColor(uiConfig
									.getTopbar_background_color()));
					// touchModel.getButton().setBackgroundColor(
					// context.getResources().getColor(R.color.red));
				}
			} else {

				touchModel.getButton()
						.setBackgroundColor(
								Color.parseColor(uiConfig
										.getTopbar_background_color()));
				touchModel.getButton().setTextColor(
						Color.parseColor(uiConfig.getApp_background_color()));
				// touchModel.getTextView().setBackgroundResource(R.drawable.bb);
				// touchModel.getButton().setBackgroundColor(
				// context.getResources().getColor(R.color.white));

			}
			tableRow.addView(touchModel.getButton());
		}

		tableRow.invalidate();
	}

}
