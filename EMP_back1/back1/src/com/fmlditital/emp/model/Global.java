package com.fmlditital.emp.model;

import android.os.Environment;

import com.fmlditital.emp.MailActivity;
import com.fmlditital.emp.PageActivity;
import com.fmlditital.emp.article.ArticleActivity;
import com.fmlditital.emp.cast.CastActivity;
import com.fmlditital.emp.event.EventActivity;
import com.fmlditital.emp.form.FormActivity;
import com.fmlditital.emp.gallery.GalleryActivity;
import com.fmlditital.emp.music.MusicActivity;
import com.fmlditital.emp.notification.NotificationActicity;
import com.fmlditital.emp.video.VideoActivity;

public class Global {

	public static final String TAB_KEY = "application";

	public static final String GALLERY_KEYL = "gallery";

	public static final String ARTICLE_KEY = "article";

	public static final String EVENT_KEY = "event";
	public static final String EVENT_JOIN_KEY = "upadataEvent";

	public static final String CAST_KEY = "cast";

	public static final String MUSIC_KEY = "music";

	public static final String VIDEO_KEY = "video";

	public static final String FORM_KEY = "form";
	public static final String FORM_SUMIT_KEY = "insertListForm";

	public static final String NOTIFICATION_KEY = "notification";

	public static final String PAGE_KEY = "homepage";

	public static final String COMMENT_INSTERT_KEY = "insertComment";

	public static final String COMMENT_KEY = "listcomment";

	public static final String ACCUMULATIVE_KEY = "accumulativecounts";// accumulativecounts
	public static final String ACCUMULATIVE_INSERT_KEY = "insertAccumulativeCounts";

	public static final String ALLCOUNTS_KEY = "allCounts";

	public final static String THE_APP_FILE_NAME = Environment
			.getExternalStorageDirectory() + "/EMP";

	public final static String  PageFunction =   "page";
	public final static String  FormFunction =   "form";
	public static String[] functions = { PageFunction, "video", "music", "event",
			"gallery", "article", FormFunction, "mail", "cast", "notification" };
	
	public static Class[] classes = { PageActivity.class, VideoActivity.class,
			MusicActivity.class, EventActivity.class, GalleryActivity.class,
			ArticleActivity.class, FormActivity.class, MailActivity.class,
			CastActivity.class, NotificationActicity.class };

	public   static final String APP_STYLE[] = {"1","2","3"};
	
	
	public final static String  DETAIL =   "Detail";
	public final static String  COMMENT =   "Comment";
	
	public final static String  UPCOMING =   "Upcoming";
	public final static String  PAST =   "Past";
	
	public final static String  RECENT =   "Recent";
	public final static String  POPULAR =   "Popular";
	
	public final static String  DOWNLOADING =   "Downloading";
	public final static String  DOWNLOADED =   "Downloaded";
	
}
