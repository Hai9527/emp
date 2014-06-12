package com.fmlditital.emp.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.fmlditital.emp.model.CommentModel;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.share.UserInfo;

public class Confi {
	private static Confi instance = null;

	private Confi() {

	}

	public static Confi getInstance() {
		if (instance == null)
			instance = new Confi();
		if (uIConfig == null)
			uIConfig = new UIConfig();
		return instance;
	}

	// public final static String API_URL =
	// "http://202.134.127.70/projects/EMP/phase1/Webservice/";

	public final static String API_URL = "http://202.134.127.70/projects/EMP/phase_dev/platform/";

	// public final static String API_URL =
	// "http://202.134.127.70/projects/EMP/phase_staging/platform/";

	public static String appId = "268";

	public static String SnsAppName = "Android Device ";

	// public static int listitemBackground = 0;
	// public static int listitemTextColor = 0;
	//
	// public static int isAcceptNotification = 0;

	// UI Config
	private static UIConfig uIConfig = null;

	public static void setuIConfig(UIConfig uIConfig) {
		Confi.uIConfig = uIConfig;
	}

	public static UIConfig getuIConfig() {
		if (uIConfig == null)
			uIConfig = new UIConfig();
		return uIConfig;
	}

	private static String APP_Type = null;

	public static String getAPP_Type() {
		return APP_Type;
	}

	public static void setAPP_Type(String aPP_Type) {
		APP_Type = aPP_Type;
	}

	// weibo app key
//	public static String WEIBO_APP_KEY = "815274840";
//	public static String WEIBO_APP_SECRET = "043312fec2e6d81939201e1edc87f48e";
	public static String WEIBO_APP_KEY = "437817123";
	public static String WEIBO_APP_SECRET = "a5d52dddfdd5a06780d0acd4bb74de16";
	
	// the Share ID for weibo
	public final static String SHARE_WEIBO_ID = "SHARE_WEIBO_ID";

	private List<UserInfo> userInfos = new ArrayList<UserInfo>();
	private static int userCount = 0;

	public List<UserInfo> getUserInfos() {
		return userInfos;
	}

	// image table
	private Map<String, String> imageTableMap = null;

	public Map<String, String> getImageTableMap() {
		if (imageTableMap == null)
			imageTableMap = new HashMap<String, String>();
		return imageTableMap;
	}

	// json table
	private Map<String, String> jsonTableMap = null;

	public Map<String, String> getJsonTableMap() {
		if (jsonTableMap == null)
			jsonTableMap = new HashMap<String, String>();
		return jsonTableMap;
	}

	public void setJsonTableMap() {

	}

	public void saveImageTableMap() {

	}

	/**
	 * 
	 * @param action
	 * @param json
	 * @return
	 */
	public static String buildApiURL(String action, JSONObject json) {
		String result = API_URL + "?c=api&a={0}&apiJSON={1}";
		// try {
		result = MessageFormat.format(result, action,
				URLEncoder.encode(json.toString()), "utf-8");
		// result = URLEncoder.encode(MessageFormat.format(result, action,
		// json.toString()), "utf-8");
		// result = URLDecoder.decode(MessageFormat.format(result, action,
		// json.toString()), "utf-8");
		// }
		// catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		return result;
	}

	/**
	 * ApiUrl
	 * 
	 * @return
	 */
	public static String getAppApiUrl() {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			// SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			// Date date = new Date();
			// df.format(date);
			// System.out.println(df.format(date));
			obj.put("aid", appId);
			// obj.put("update_time", df.format(date));
			result = buildApiURL(Global.TAB_KEY, obj);
		} catch (JSONException e) {

		}
		return result;
	}

	/**
	 * GalleryApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getGalleryApiUrl(String id) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", id);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.GALLERY_KEYL, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * GalleryDetailApiUrl
	 * 
	 * @param gid
	 * @return
	 */
	public static String getGalleryDetailApiUrl(String tid, String gid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("gid", gid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.GALLERY_KEYL, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * ArticleApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getArticleApiUrl(String id, String page) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", id);
			obj.put("page", page);
			obj.put("number", "10");
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.ARTICLE_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * ArticleDetailApiUrl
	 * 
	 * @param aid
	 * @return
	 */
	public static String getArticleDetailApiUrl(String artid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("artid", artid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.ARTICLE_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * Event upcoming ApiUrl
	 * 
	 * @param tid
	 * @return
	 */
	public static String getEventUpcomingApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("type", "upcoming");
			result = buildApiURL(Global.EVENT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * Event past ApiUrl
	 * 
	 * @param tid
	 * @return
	 */
	public static String getEventPastApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("type", "past");
			result = buildApiURL(Global.EVENT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * Event Page Api
	 * 
	 * @param tid
	 * @param type
	 * @param page
	 * @return
	 */
	public static String getEventPageApiUrl(String tid, String type, String page) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("type", type);
			obj.put("page", page);
			obj.put("number", "20");
			result = buildApiURL(Global.EVENT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * EventDetailApiUrl
	 * 
	 * @param eid
	 * @return
	 */
	public static String getEventDetailApiUrl(String tid, String eid,
			String type) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("eid", eid);
			obj.put("type", type);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.EVENT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	public static String getuUpadataEventApiUrl(String eid, String egaree) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("eid", eid);
			obj.put("egaree", egaree);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.EVENT_JOIN_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * CastApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getCastApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.CAST_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * CastDetailApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getCastDetailApiUrl(String cid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("cid", cid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.CAST_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * MusicApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getMusicApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.MUSIC_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * MusicDetailApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getMusicDetailApiUrl(String mid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("mid", mid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.MUSIC_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * VideoApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getVideoApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);

			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.VIDEO_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * Video popular ApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getVideoPopularApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("type", "popular");

			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.VIDEO_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * VideoDetailApiUrl
	 * 
	 * @param vid
	 * @return
	 */
	public static String getVideoDetailApiUrl(String vid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("vid", vid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.VIDEO_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * FormApiUrl
	 * 
	 * @param tid
	 * @return
	 */
	public static String getFormApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.FORM_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * FormApiUrl
	 * 
	 * @param tid
	 * @return
	 */
	public static String getFormApiUrl(int tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("json", 1);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.FORM_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * FormSumitApiUrl
	 * 
	 * @param tid
	 * @return
	 */
	public static String getFormSumitApiUrl(String tid, JSONObject obj) {
		String result = null;
		// JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.FORM_SUMIT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * NotificationApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getNotificationApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.NOTIFICATION_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * NotificationDetailApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getNotificationDetailApiUrl(String vid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("vid", vid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.NOTIFICATION_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * PageApiUrl
	 * 
	 * @param id
	 * @return
	 */
	public static String getPageApiUrl(String tid) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.PAGE_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * Comment List Api Url
	 * 
	 * @param tid
	 * @param data_id
	 * @return
	 */
	public static String getCommentListApiUrl(String tid, String data_id) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("tid", tid);
			obj.put("data_id", data_id);
			// obj.put("update_time", new Date().toString());
			result = buildApiURL(Global.COMMENT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * insert Comment Api Url
	 * 
	 * @param tid
	 * @param dataId
	 * @param dataName
	 * @param commentModel
	 * @return
	 */
	public static String getinsertCommentApiUrl(String tid, String dataId,
			String dataName, CommentModel commentModel) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("aid", appId);
			obj.put("tid", tid);
			obj.put("data_id", dataId);
			obj.put("data_name", dataName);
			obj.put("cuser_id", commentModel.getUserId());
			obj.put("cuser_name", commentModel.getUserName());
			obj.put("cuser_avatar", commentModel.getUserPortrait() + ".jpg");
			obj.put("comment", commentModel.getCommentDetail());
			result = buildApiURL(Global.COMMENT_INSTERT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * accumulative counts Comment List Api Url
	 * 
	 * @param tid
	 * @return
	 */
	public static String getAccumulativecountsApiUrl(String tid, String data_id) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("aid", appId);
			obj.put("tid", tid);
			obj.put("data_id", data_id);
			result = buildApiURL(Global.ACCUMULATIVE_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * Insert Accumulative Counts
	 * 
	 * @param tid
	 * @param data_id
	 * @param acc_type
	 * @return
	 */
	public static String getInsertAccumulativeCountsApiUrl(String tid,
			String data_id, String acc_type) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("aid", appId);
			obj.put("tid", tid);
			obj.put("data_id", data_id);
			obj.put("acc_type", acc_type);
			result = buildApiURL(Global.ACCUMULATIVE_INSERT_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * All Counts Api Url
	 * 
	 * @param tid
	 * @param data_id
	 * @param acc_type
	 * @return
	 */
	public static String getAllCountsApiUrl(String tid, String data_id) {
		String result = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("aid", appId);
			obj.put("tid", tid);
			obj.put("data_id", data_id);
			// obj.put("acc_type", acc_type);
			result = buildApiURL(Global.ALLCOUNTS_KEY, obj);
		} catch (JSONException e) {
		}
		return result;
	}

	/**
	 * if login weibo
	 * 
	 * @return
	 */
	public boolean isWeiBoLogin() {
		if (userInfos != null && userInfos.size() > 0) {
			for (UserInfo userInfo : userInfos) {
				if (userInfo.getKey().endsWith(SHARE_WEIBO_ID))
					return true;
			}
		}
		return false;
	}

	/**
	 * get UserInfo
	 * 
	 * @return
	 */
	public void getUserInfoFormFile(Context context) {
		
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			userInfos.clear();

			String foldername = Global.THE_APP_FILE_NAME + File.separator
					+ ".configuration";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			 

			File targetFile = new File(foldername + File.separator
					+ "user");
			targetFile.delete();//----------------测试用
			FileInputStream fos = null;
			ObjectInputStream ois = null;
			try {
				fos = new FileInputStream(targetFile);
				ois = new ObjectInputStream(fos);

				userCount = ois.readInt();
				for (int i = 0; i < userCount; i++) {
					UserInfo userInfo = (UserInfo) ois.readObject();
					System.out.println(i+"=======getUserInfoFormFile:::"+userInfo.getId());
					userInfos.add(userInfo);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
						fos = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (ois != null) {
					try {
						ois.close();
						ois = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		} 
	}

	/**
	 * save UserInfo
	 * 
	 * @return
	 */
	public void saveUserInfoFormFile(Context context) {
		userCount = userInfos.size();
 
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Global.THE_APP_FILE_NAME + File.separator
					+ ".configuration";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}

			File targetFile = new File(foldername + File.separator + "user");
			if (targetFile.exists())
				targetFile.delete();
			FileOutputStream fs = null;
			ObjectOutputStream os = null;
			try {
				fs = new FileOutputStream(targetFile);

				os = new ObjectOutputStream(fs);
				os.writeInt(userCount);
				for (UserInfo userInfo : userInfos) {
					os.writeObject(userInfo);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (fs != null) {
					try {
						fs.close();
						fs = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (os != null) {
					try {
						os.close();
						os = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

}
