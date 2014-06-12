package com.fmlditital.emp.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.EventModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;
import com.fmlditital.emp.view.TabView;

public class EventActivity extends AdapterViewActivity {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private LinearLayout detailLinearLayout = null;

	private ListView upcomingListView = null, pastListView = null;
	private List<BaseModel> pastData = null;
	private String pastUrl = null;
	private AsyncJson pastAsyncJson = null;
	protected BaseAdapter pastAdapter = null, upcomingAdapter = null;

	private TabView touchView = null;
	private String upcoming = Global.UPCOMING, past = Global.PAST;
	private String currentPage = upcoming; 

	private UIConfig uiConfig = null;

	private String pageUrl = null;
	private int page = 0, allPage = 0;
//    private TextView attend,mightAttend;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiConfig = Confi.getInstance().getuIConfig();

		pastUrl = Confi.getInstance().getEventPastApiUrl(tid);

		touchView = new TabView(this);
		touchView.addTitle(upcoming);
		touchView.addTitle(past);
		touchView.display(upcoming);
		touchView.addCommentCallback(this);

		data = app.getDataEvent();
		data.clear();
		pastData = app.getDataEventPast();

		// for ListView
		upcomingListView = (ListView) ViewFactory.getAdapterView(this,
				AdapterSelect.eventkey);
		pastListView = (ListView) ViewFactory.getAdapterView(this,
				AdapterSelect.eventkey);
		// for adapter
		upcomingAdapter = ViewFactory.getDataAdapter(this,
				AdapterSelect.eventkey, data);
		pastAdapter = ViewFactory.getDataAdapter(this, AdapterSelect.eventkey,
				pastData);
		// set for adapter
		upcomingListView.setAdapter(upcomingAdapter);
		pastListView.setAdapter(pastAdapter);

		detailLinearLayout = new LinearLayout(this);
		detailLinearLayout.setOrientation(LinearLayout.VERTICAL);
		// bodyLayout.addView(touchView);//未发生事件和已发生事件的切换
		detailLinearLayout.addView(upcomingListView);
		bodyLayout.addView(detailLinearLayout);

		dataUrl = Confi.getInstance().getEventUpcomingApiUrl(tid);
		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);
        
//		attend=new TextView(this);
//		System.out.println("attend:::::::: "+attend);
//		mightAttend=new TextView(this);
//		mightAttend.setText("Might Attend "+jionCount[1]);
//		upcomingListView.addView(mightAttend);
		upcomingListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(EventActivity.this,
						EventDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("tid", tid);
				bundle.putString("tabName", title);
				bundle.putInt("index", position);
				if (currentPage == upcoming)
					bundle.putString("type", "upcoming");
				else if (currentPage == past)
					bundle.putString("type", "past");
				intent.putExtras(bundle);
				EventActivity.this.startActivity(intent);
			}
		});

		pastListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(EventActivity.this,
						EventDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("tid", tid);
				bundle.putString("tabName", title);
				bundle.putInt("index", position);
				if (currentPage == upcoming)
					bundle.putString("type", "upcoming");
				else if (currentPage == past)
					bundle.putString("type", "past");
				intent.putExtras(bundle);
				EventActivity.this.startActivity(intent);
			}
		});
	}

	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(dataUrl)) {
			data.clear();
			try {
				page = json.getInt("page");
				allPage = json.getInt("all_pages");
				JSONArray jsonArray = json.getJSONArray("datas");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					jionCount[0] = jsonObject2.getString("egaree").trim();
					jionCount[1] = jsonObject2.getString("emaybe").trim();
					jionCount[2] = jsonObject2.getString("eunagree").trim();
					System.out.println("======egaree====== "+jsonObject2.getString("egaree").trim());
					System.out.println("======emaybe====== "+jsonObject2.getString("emaybe").trim());
					System.out.println("======eunagree====== "+jsonObject2.getString("eunagree").trim());
					if (jsonObject2.getString("event_status").equals("1")) {
						EventModel model = new EventModel();
						model.setId(jsonObject2.getString("event_id"));
						model.setTitle(jsonObject2.getString("event_title"));
						Log.v("", ">>>>>>>>>event_title >>>>>>>>>>>>>>>"
								+ jsonObject2.getString("event_title"));
						model.setBegintime(jsonObject2
								.getString("event_begintime"));
						model.setEndtime(jsonObject2.getString("event_endtime"));
						model.setLocation(jsonObject2.getString("elocation"));
						model.setImage(jsonObject2.getString("event_image"));
						model.setEmail(jsonObject2.getString("email"));
						model.setSummary(jsonObject2.getString("event_content"));
						model.setAttend(jsonObject2.getString("egaree").trim());
						model.setMightAttend(jsonObject2.getString("emaybe").trim());
						data.add(model);
						upcomingListView.invalidate();
						upcomingAdapter.notifyDataSetChanged();
					}
				}
			} catch (JSONException e) {
				callJoinFailure(dataUrl, e.getMessage().toString());
			}

			// if(allPage>page) {
			// TextView textView =new TextView (this);
			// textView.setText("More");
			// Button button =new Button(this);
			// button.setText("More");
			// button.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
			// upcomingListView.addFooterView(button);

			// }

		}

		if (url.equals(pastUrl)) {
			pastData.clear();
			try {
				page = json.getInt("page");
				allPage = json.getInt("all_pages");
				JSONArray jsonArray = json.getJSONArray("datas");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					System.out.println("====past==egaree====== "+jsonObject2.getString("egaree").trim());
					System.out.println("====past==emaybe====== "+jsonObject2.getString("emaybe").trim());
					System.out.println("====past==eunagree====== "+jsonObject2.getString("eunagree").trim());
					jionCount[0] = jsonObject2.getString("egaree").trim();
					jionCount[1] = jsonObject2.getString("emaybe").trim();
					jionCount[2] = jsonObject2.getString("eunagree").trim();
					if (jsonObject2.getString("event_status").equals("1")) {
						EventModel model = new EventModel();
						model.setId(jsonObject2.getString("event_id"));
						model.setTitle(jsonObject2.getString("event_title"));
						Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>event_title "
								+ jsonObject2.getString("event_title"));
						model.setBegintime(jsonObject2
								.getString("event_begintime"));
						model.setEndtime(jsonObject2.getString("event_endtime"));
						model.setLocation(jsonObject2.getString("elocation"));
						model.setImage(jsonObject2.getString("event_image"));
						model.setEmail(jsonObject2.getString("email"));
						model.setSummary(jsonObject2.getString("event_content"));
						model.setAttend(jsonObject2.getString("egaree").trim());
						model.setMightAttend(jsonObject2.getString("emaybe").trim());
						pastData.add(model);
						pastAdapter.notifyDataSetChanged();
					}
				}

			} catch (JSONException e) {
				callJoinFailure(dataUrl, e.getMessage().toString());
			}

			// if(allPage>page) {
			// Button button =new Button(this);
			// button.setText("More");
			// button.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
			// pastListView.addFooterView(button);
			// }
		}
	}

	public void callJoinFailure(String url, String failMessage) {
		super.callJoinFailure(url, failMessage);
	}

	private void showBody(String flag) {
		if (flag.equals(upcoming)) {
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(upcomingListView);
			currentPage = upcoming;
		} else if (flag.equals(past)) {
			if (pastData == null || pastData.size() == 0) {
				pastAsyncJson = new AsyncJson(this);
				pastAsyncJson.addInterFace(this);
				pastAsyncJson.execute(pastUrl);
			}
			currentPage = past;
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(pastListView);
		}
		detailLinearLayout.invalidate();
	}

	public void showDetailOrCommentList(String title) {
		super.showDetailOrCommentList(title);
		if (title.equals(upcoming)) {
			showBody(title);
		} else if (title.equals(past)) {
			showBody(title);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, REFRESH, 0, getResources().getString(R.string.refresh))
				.setIcon(android.R.drawable.ic_popup_sync);

		menu.add(0, DOWNLOAD, 0, getResources().getString(R.string.download))
				.setIcon(android.R.drawable.stat_sys_download_done);
		menu.add(0, SETTING, 0, getResources().getString(R.string.setting))
				.setIcon(android.R.drawable.ic_menu_preferences);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case REFRESH: {
			if (currentPage == upcoming) {
				AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
				asyncRefreshJson.addInterFace(this);
				asyncRefreshJson.execute(dataUrl);
			} else if (currentPage == past) {

				AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
				asyncRefreshJson.addInterFace(this);
				asyncRefreshJson.execute(pastUrl);

			}
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(EventActivity.this,
					DownloadActivity.class);
			//intent.putExtra("tabName", "Download");
			intent.putExtra("tabName", "下载");
			EventActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(EventActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			EventActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	private String[] jionCount = { "0", "0", "0" };
	private String type = null;
	private String[] paseContext = { "Attending (count)",
			"Might Attend(count)", "Not Attend (count)" };
	private String[] shareContext = new String[4];
	private class UpdateJoinAsync extends AsyncTask<String, Void, Void> {

		protected Void doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				StringBuilder builder = new StringBuilder();
				URLConnection tc = url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				JSONObject jsonObject = new JSONObject(builder.toString());
				JSONArray jsonArray = jsonObject.getJSONArray("datas");
				// for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
				jionCount[0] = jsonObject2.getString("egaree").trim();
				jionCount[1] = jsonObject2.getString("emaybe").trim();
				jionCount[2] = jsonObject2.getString("eunagree").trim();

				joinCount();
				in.close();

			} catch (MalformedURLException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			} catch (JSONException e) {
				// e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {

		}
	}

	public void joinCount() {
		shareContext[0] = "Share to Sina WeiBo";
		shareContext[1] = "Attending (count)";
		shareContext[2] = "Might Attend(count)";
		shareContext[3] = "Not Attend (count)";

		if (type.equals("upcoming")) {
			for (int i = 1; i < shareContext.length; i++) {
				shareContext[i] = shareContext[i].replace("count",
						jionCount[i - 1]);
			}
		} else if (type.equals("past")) {
			for (int i = 0; i < paseContext.length; i++) {
				paseContext[i] = paseContext[i].replace("count", jionCount[i]);
			}
		}

	}
}
