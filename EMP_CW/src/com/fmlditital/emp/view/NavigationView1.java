package com.fmlditital.emp.view;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent.Callback;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.NavigationGridAdapter;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.NavigationModel;
import com.fmlditital.emp.tool.Tools;

public class NavigationView1 extends TableLayout implements Callback {

	private EMPApp app = EMPApp.getSingleton();
	private List<NavigationModel> tabList = app.getTabList();

	private UIConfig uiConfig = null;

	private Context context = null;
	private TableRow tableRow = null;
	private NavigationUnit[] navigationUnits = null;

	private PopupWindow popup = null;
	private LinearLayout popupLinearLayout = null;
	private GridView mGrid = null;
	private NavigationGridAdapter adapter = null;
	private Animation animation = null;
	private List<NavigationModel> mGridData = null;

	public NavigationView1(Context context) {
		super(context);
		this.context = context;
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		this.setStretchAllColumns(true);
		tableRow = new TableRow(context);
		TableLayout.LayoutParams tLayoutParams = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		tLayoutParams.topMargin = Tools.dip2px(context, 5);
		tLayoutParams.bottomMargin = Tools.dip2px(context, 5);
		this.addView(tableRow, tLayoutParams);

		uiConfig = Confi.getInstance().getuIConfig();
		this.setBackgroundColor(Color.parseColor(uiConfig
				.getNavbar_background_color()));
		this.getBackground().setAlpha(230);
		addView();
	}

	private void addView() {
		if (tabList.size() > 5) {
			navigationUnits = new NavigationUnit[5];
			for (int i = 0; i < navigationUnits.length - 1; i++) {
				final int positionTemp = i;
				navigationUnits[i] = new NavigationUnit(context, tabList.get(i)
						.getIcon(), tabList.get(i).getTitle(),
						uiConfig.getNavbar_text_color());
				navigationUnits[i].setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						NavigationModel model = (NavigationModel) tabList
								.get(positionTemp);

						Intent intent = getIntent(model.getFunction(),
								model.getTitle(), model.getTab_id());
						context.startActivity(intent);
					}
				});

				tableRow.addView(navigationUnits[i]);

			}

			navigationUnits[4] = new NavigationUnit(context, context
					.getResources().getDrawable(R.drawable.more), context
					.getResources().getString(R.string.more),
					uiConfig.getNavbar_text_color());

			navigationUnits[4].setOnClickListener(new MoreOnClickListener());
			tableRow.addView(navigationUnits[4]);

			mGridData = tabList.subList(4, tabList.size());
			mGrid = new GridView(context);
			mGrid.setNumColumns(3);
			mGrid.setHorizontalSpacing(2);
			mGrid.setVerticalSpacing(2);
			mGrid.setCacheColorHint(Color.TRANSPARENT);
			adapter = new NavigationGridAdapter(context, mGridData);
			mGrid.setAdapter(adapter);

			mGrid.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					NavigationModel model = (NavigationModel) parent
							.getItemAtPosition(position);

					Intent intent = getIntent(model.getFunction(),
							model.getTitle(), model.getTab_id());

					context.startActivity(intent);
				}

			});

			popupLinearLayout = new LinearLayout(context);
			popupLinearLayout.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			popupLinearLayout.addView(mGrid);
			popupLinearLayout.setBackgroundColor(Color.parseColor(uiConfig
					.getNavbar_background_color()));

			popupLinearLayout.getBackground().setAlpha(75);
			popup = new PopupWindow(popupLinearLayout,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			popupLinearLayout.getBackground().setAlpha(75);

			popup.setAnimationStyle(R.style.PopupAnimation);
			popup.setAnimationStyle(android.R.style.Animation_Dialog);
			popup.update();
			popup.setOutsideTouchable(true);
			popup.setFocusable(true);
			ColorDrawable dw = new ColorDrawable(0xb0000000);
			popup.setBackgroundDrawable(dw);
		}

		else {
			navigationUnits = new NavigationUnit[tabList.size()];
			for (int i = 0; i < navigationUnits.length; i++) {
				final int positionTemp = i;
				navigationUnits[i] = new NavigationUnit(context, tabList.get(i)
						.getIcon(), tabList.get(i).getTitle(),
						uiConfig.getNavbar_text_color());
				navigationUnits[i].setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						NavigationModel model = (NavigationModel) tabList
								.get(positionTemp);

						Intent intent = getIntent(model.getFunction(),
								model.getTitle(), model.getTab_id());
						context.startActivity(intent);
					}

				});

				tableRow.addView(navigationUnits[i]);

			}

		}

	}

	private Intent getIntent(String function, String tabName, String tid) {
		Intent intent = null;
		Bundle bundle = new Bundle();
		bundle.putString("tabName", tabName);
		bundle.putString("tid", tid);
		bundle.putInt("back", 0);

		if (function.equals("page"))
			bundle.putString("page", tid);

		for (int i = 0; i < Global.functions.length; i++) {
			if (function.equals(Global.functions[i])) {
				intent = new Intent(context, Global.classes[i]);
				intent.putExtras(bundle);
				return intent;
			}
		}
		return intent;
	}

	class MoreOnClickListener implements OnClickListener {

		public void onClick(View v) {

			if (!popup.isShowing()) {
				popup.showAtLocation(popupLinearLayout, Gravity.BOTTOM, 0, 65);

			}
		}
	}

	public boolean getPopupWindowState() {
		if (popup.isShowing())
			return true;
		else
			return false;
	}

	public void closePopupWindow() {
		if (popup.isShowing()) {
			popup.dismiss();
			NavigationView1.this.setVisibility(View.VISIBLE);
		}
	}

}
