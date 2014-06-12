package com.fmlditital.emp.music;

public class MusicPlayState {
	
 
	
	//播放状态
	
	public static final int MPS_STATUS_STOP = 0;           //停止
	
	public static final int MPS_STATUS_PLAYING = 1;		 //播放
	
	public static final int MPS_STATUS_PAUSE = 2;			//暂停 
	
	public static final int MPS_STATUS = MPS_STATUS_PLAYING;  
	
	//播放方式
	public static final int MPS_MODE_RANDOM= 0;   //随机
	
	public static final int MPS_MODE_CYCLE = 1;    //循环
	
	public static final int MPS_MODE_LIST = 2;     //列表
	
	public static final int MPS_MODE = MPS_MODE_CYCLE;	//
	
	
//	public static final String PLAY_STATE_NAME = "PLAY_STATE_NAME";
//	public static final String PLAY_MUSIC_INDEX = "PLAY_MUSIC_INDEX";
//	public static final String MUSIC_INVALID = "MUSIC_INVALID";
//	public static final String MUSIC_PREPARE = "MUSIC_PREPARE";
	public static final String MUSIC_PLAY = "MUSIC_PLAY";
	public static final String MUSIC_PAUSE = "MUSIC_PAUSE";
	public static final String MUSIC_STOP = "MUSIC_STOP";
	
	public static final String MUSIC_NEXT= "MUSIC_NEXT";
	public static final String MUSIC_PRE= "MUSIC_PRE";
	public static final String MUSIC_SEEK_TO= "MUSIC_SEEK_TO";
	
	public static final String MUSIC_START_SERVICE = "MUSIC_START_SERVICE";

}
