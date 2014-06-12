package com.fmlditital.emp.app;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.NavigationModel;

public class EMPApp extends Application {

	// Navigation
	private List<NavigationModel> tabList = null;

	private static EMPApp singleton;

	// Gallery data
	private List<BaseModel> dataGallery = null;
	private List<BaseModel> dataGalleryThumb = null;

	// news data
	private List<BaseModel> dataAriticle = null;
	private List<BaseModel> bottomDataAriticle = null;

	// Event data
	private List<BaseModel> dataEvent = null;
	private List<BaseModel> dataEventPast = null;

	// Music data
	private List<BaseModel> dataMusic = null;
	private List<BaseModel> bottomDataDataMusic = null;

	// //download data
	// private DownloadManager downloadManager=null;

	// Cast data
	private List<BaseModel> dataCast = null;

	// Notification data
	private List<BaseModel> dataNotificationModel = null;
	private List<BaseModel> bottomDataNotificationModel = null;

	// Video data
	private List<BaseModel> dataVideo = null;
	private List<BaseModel> dataVideopopular = null;

	// private int newsCount = 0, newsPage = 0;

	// public int getNewsCount() {
	// return newsCount;
	// }
	//
	// public void setNewsCount(int newsCount) {
	// this.newsCount = newsCount;
	// }
	//
	// public int getNewsPage() {
	// return newsPage;
	// }
	//
	// public void setNewsPage(int newsPage) {
	// this.newsPage = newsPage;
	// }

	// home Data data
	private NavigationModel homeModel = null;

	private Map<String, String> sawMap = null;
	private Map<String, String> likeMap  = null;

	public Map<String, String> getSawMap() {
		if (sawMap == null)
			sawMap = new HashMap<String, String>();
		return sawMap;
	}

	public Map<String, String> getLikeMap() {
		if (likeMap == null)
			likeMap = new HashMap<String, String>();
		return likeMap;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		singleton = this;
		// downloadManager=DownloadManager.getInstance();
		// downloadManager.start();
		// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>> downloadManager.start();  " );
	}

	public static EMPApp getSingleton() {
		return singleton;
	}

	public NavigationModel getHomeModel() {
		return homeModel;
	}

	public void setHomeModel(NavigationModel homeModel) {
		this.homeModel = homeModel;
	}

	public List<NavigationModel> getTabList() {
		if (tabList == null)
			tabList = new ArrayList<NavigationModel>();
		return tabList;
	}

	public void setTabList(List<NavigationModel> tabList) {
		this.tabList = tabList;
	}

	public List<BaseModel> getDataGalleryThumb() {
		if (dataGalleryThumb == null)
			dataGalleryThumb = new ArrayList<BaseModel>();
		return dataGalleryThumb;
	}

	public void setDataGalleryThumb(List<BaseModel> dataGalleryThumb) {
		// this.dataGalleryThumb.clear();
		this.dataGalleryThumb = dataGalleryThumb;
	}

	public List<BaseModel> getDataGallery() {
		if (dataGallery == null)
			dataGallery = new ArrayList<BaseModel>();
		return dataGallery;
	}

	public List<BaseModel> getDataAriticle() {
		if (dataAriticle == null)
			dataAriticle = new ArrayList<BaseModel>();
		return dataAriticle;
	}

	public void setDataAriticle(List<BaseModel> dataAriticle) {
		this.dataAriticle = dataAriticle;
	}

	public List<BaseModel> getBottomDataAriticle() {
		if (bottomDataAriticle == null)
			bottomDataAriticle = new ArrayList<BaseModel>();
		return bottomDataAriticle;
	}

	// public void setBottomDataAriticle(List<BaseModel> bottomDataAriticle) {
	// this.bottomDataAriticle = bottomDataAriticle;
	// }

	public List<BaseModel> getDataEvent() {
		if (dataEvent == null)
			dataEvent = new ArrayList<BaseModel>();
		return dataEvent;
	}

	public void setDataEvent(List<BaseModel> dataEvent) {
		this.dataEvent = dataEvent;
	}

	public List<BaseModel> getDataEventPast() {
		if (dataEventPast == null)
			dataEventPast = new ArrayList<BaseModel>();
		return dataEventPast;
	}

	public void setDataEventPast(List<BaseModel> dataEventPast) {
		this.dataEventPast = dataEventPast;
	}

	public List<BaseModel> getDataMusic() {
		if (dataMusic == null)
			dataMusic = new ArrayList<BaseModel>();
		return dataMusic;
	}

	public List<BaseModel> getBottomDataDataMusic() {
		if (bottomDataDataMusic == null)
			bottomDataDataMusic = new ArrayList<BaseModel>();
		return bottomDataDataMusic;
	}

	// public void setDataMusic(List<BaseModel> dataMusic) {
	// this.dataMusic = dataMusic;
	// }

	public List<BaseModel> getDataNotificationModel() {
		if (dataNotificationModel == null)
			dataNotificationModel = new ArrayList<BaseModel>();
		return dataNotificationModel;
	}

	public List<BaseModel> getBottomDataNotificationModel() {
		if (bottomDataNotificationModel == null)
			bottomDataNotificationModel = new ArrayList<BaseModel>();
		return bottomDataNotificationModel;
	}

	public List<BaseModel> getDataCast() {
		if (dataCast == null)
			dataCast = new ArrayList<BaseModel>();
		return dataCast;
	}

	public void setDataCast(List<BaseModel> dataCast) {
		this.dataCast = dataCast;
	}

	public List<BaseModel> getDataVideo() {
		if (dataVideo == null)
			dataVideo = new ArrayList<BaseModel>();
		return dataVideo;
	}

	public void setDataVideo(List<BaseModel> dataVideo) {
		this.dataVideo = dataVideo;
	}

	public List<BaseModel> getDataVideopopular() {
		if (dataVideopopular == null)
			dataVideopopular = new ArrayList<BaseModel>();
		return dataVideopopular;
	}

	public void setDataVideopopular(List<BaseModel> dataVideopopular) {
		this.dataVideopopular = dataVideopopular;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	private HashMap<String, SoftReference<Drawable>> drawableCache;

	public HashMap<String, SoftReference<Drawable>> getDrawableCache() {
		if (drawableCache == null)
			drawableCache = new HashMap<String, SoftReference<Drawable>>();
		return drawableCache;
	}

	// public void setDrawableCache(
	// HashMap<String, SoftReference<Drawable>> drawableCache) {
	// this.drawableCache = drawableCache;
	// }

	// private HashMap<String, SoftReference<Drawable>> bitmapCache;

}
