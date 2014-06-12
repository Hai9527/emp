<?php
/**
 * Description of apiController.php
 * @author Animax
 */
class apiController extends TFController {

	protected $attribute;

    public function __construct() {
		if(isset($_REQUEST['apiJSON']) && $_REQUEST['apiJSON'] != ''){
			if(is_string($_REQUEST['apiJSON'])){ $this->attribute = json_decode(stripslashes($_REQUEST['apiJSON']), true); }
		}
	}

	
	/* application information (包括tab的記錄,template,color等) */
	public function api_getAppConfig(){
		global $TFConfig;

		$result = array();

		if(array_key_exists("aid", $this->attribute)){
			$app_tab = new app_tabModel();
			$result = $app_tab->getAppConfig($this->attribute['aid']);

			foreach($result as $key=>$value){
				$result[$key]['amarket_icon'] = $TFConfig['path'].$value['amarket_icon'];
				$result[$key]['aindex_image'] = $TFConfig['path'].$value['aindex_image'];
				$result[$key]['ticon'] = $TFConfig['path'].$value['ticon'];
				$result[$key]['aload_image'] = $TFConfig['path'].$value['aload_image'];
			}
			$app = global_function::arrayFormat($result, 'aid', array('aid', 'aname','amarket_icon','aindex_image','aload_image','temid','akey','style'), array('tid', 'fnid', 'ticon','tname','tstatus','fnname','tdefault','torder'), 'tab');


			$application_info = $app_tab->_toConvert($app);
			//var_dump($application_info);exit();
			if(is_array($application_info) && count($application_info)>0){

				$app = new applicationModel();
				$app_info = $app->get_data("aid = ".$this->attribute['aid']);
				
				$application_info[0]['style'] = array(
						"app_background_color" => $app_info['app_background_color'],
						"app_text_color" => $app_info['app_text_color'],
						"navbar_background_color" => $app_info['navbar_background_color'],
						"navbar_text_color" => $app_info['navbar_text_color'],
						"topbar_background_color" => $app_info['topbar_background_color'],
						"topbar_text_color" => $app_info['topbar_text_color'],
						"app_background_image" => $TFConfig['path'].$app_info['app_background_image'],
						"icon_type" => $app_info['icon_type'],
						"opacity" => 0.75
				); 
				
				$result = array(
					'statusid' => 0,
					'datas' => $application_info,
					'count' => 1
				);
			}else
				$result = array( 'statusid' => 1 );
		}

		return $result;
	}

	
	
	/* account_app */
	public function api_getTestPreview(){
		$result = array();

		if(array_key_exists("acid", $this->attribute)){
			$acc_app = new acc_appModel();
			$result = $acc_app->getTestPreview($this->attribute['acid'], $this->attribute['acpasswd']);

			$acc_app_list = global_function::arrayFormat($result, 'acid', array('acid', 'acname'), array('aid', 'aname'), 'app');

			$acc_app_info = $acc_app->_toConvert($acc_app_list);
			if(is_array($acc_app_info) && count($acc_app_info)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $acc_app_info,
					'count' => 1
				);
			else
				$result = array( 'statusid' => 1 );
		}

		return $result;


	}

	
	
	/* TAB-page */
	public function api_getHomepage(){
		global $TFConfig;

		$result = array();

		$homepage = new homepageModel();
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$result = $homepage->getHomepage($tid);
		foreach($result as $key=>$value){
			$result[$key]['himage'] = $TFConfig['path'].$value['himage'];
		}
		$page_list = $homepage->_toConvert($result);
		if(is_array($page_list) && count($page_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $page_list,
				'count' => count($page_list)
			);
		else
			$result = array( 'statusid' => 1 );
			
			
		return $result;
	}

	
	
	/* TAB-gallery(photos) */
	public function api_getAllGalleryByID(){
		global $TFConfig;

		$result = array();
		if(array_key_exists("gid", $this->attribute)){
		
			/** get isdownload info */
			$gallery = new galleryModel();
			$gallery_info = $gallery->get_data("gid = ".$this->attribute['gid']);
			
			$gallery_photo = new gallery_photoModel();
			$result = $gallery_photo->getAllGalleryByID($this->attribute['gid']);
			//TF_dump($result);
			foreach($result as $key=>$value){
				$result[$key]['picon'] = $TFConfig['path'].$value['picon'];
				$result[$key]['photo'] = $TFConfig['path'].$value['photo'];
				$result[$key]['gdefaut_icon'] = $TFConfig['path'].$value['gdefaut_icon'];
				$result[$key]['isdownload'] = $gallery_info['gis_download'];
			}

			$gallery_list = $gallery_photo->_toConvert($result);

			foreach($gallery_list as $k=>$v){
				$gallery_list[$k] = array_merge($v, $this->getcounts($this->attribute['tid'],$v['photo_id']));
			}
			if(is_array($gallery_list) && count($gallery_list)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $gallery_list,
					'count' => count($gallery_list)
				);
			else
				$result = array( 'statusid' => 1 );
		}
		return $result;
	}

	
	/* TAB-all gallery */
	public function api_getAllGallery(){
        global $TFConfig;

		$result = array();
		$gallery_count = new gallery_countModel();
		$result = $gallery_count->getAllGallery($this->attribute['tid']);
		TF_dump($result);
		foreach($result as $key=>$value){
			$result[$key]['picon'] = $TFConfig['path'].$value['picon'];
			$result[$key]['photo'] = $TFConfig['path'].$value['photo'];
			$result[$key]['gdefaut_icon'] = $TFConfig['path'].$value['gdefaut_icon'];
		}
		$gallery_list = $gallery_count->_toConvert($result);
		if(is_array($gallery_list) && count($gallery_list)>0){
				$result = array(
					'statusid' => 0,
					'datas' => $gallery_list,
					'count' => count($gallery_list)
				);
		}else{
			$result = array( 'statusid' => 1 );
		}


		return $result;
	}
	
	/* TAB-gallery return判斷 */
	public function api_getGallery(){
		if(array_key_exists("gid", $this->attribute)) return $this->api_getAllGalleryByID();
		else  return $this->api_getAllGallery();
	}
	

	/* TAB-all video albums */
	public function api_getAllVideoalbums(){
		global $TFConfig;
		
		$result = array();
		$videoalbums_count = new videoalbum_countsModel();
		$result = $videoalbums_count->getAllVideoalbums($this->attribute['tid']);
		//TF_dump($result);
		
		foreach($result as $key=>$value){
			$result[$key]['valbum_cover_image'] = $TFConfig['path'].$value['valbum_cover_image'];
		}
		
		$videoalbums_list = $videoalbums_count->_toConvert($result);
		//var_dump($videoalbums_list);
		if(is_array($videoalbums_list) && count($videoalbums_list)>0){
				$result = array(
					'statusid' => 0,
					'datas' => $videoalbums_list,
					'count' => count($videoalbums_list)
				);
		}else{
			$result = array( 'statusid' => 1 );
		}


		return $result;
		
	}
	
	/* TAB-all video albums(Videos) */
	public function api_getAllVideoAlbumByID(){
		global $TFConfig;
		$result = array();
		if(array_key_exists("valbum_id", $this->attribute)){
			
			/** get isdownload info */
			$videoalbum = new videoalbumsModel();
			$videoalbum_info = $videoalbum->get_data("valbum_id = ".$this->attribute['valbum_id']);
			
			$videoalbums_video = new videoalbum_videoModel();
			$result = $videoalbums_video->getAllVideoAlbumByID($this->attribute['valbum_id']);
		
			foreach($result as $key=>$value){
				$result[$key]['vicon'] = $TFConfig['path'].$value['vicon'];
				$result[$key]['video'] = $TFConfig['path'].$value['video'];
				$result[$key]['valbum_is_download'] = $videoalbums_info['valbum_is_download'];
			}
			
			$videoalbums_list = $videoalbums_video->_toConvert($result);
			//TF_dump($videoalbums_list);exit;
			
			foreach($videoalbums_list as $k=>$v){
				$videoalbums_list[$k] = array_merge($v, $this->getcounts($this->attribute['tid'],$v['video_id']));
			}
			if(is_array($videoalbums_list) && count($videoalbums_list)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $videoalbums_list,
					'count' => count($videoalbums_list)
				);
			else
				$result = array( 'statusid' => 1 );
		}
		return $result;
	}
	
	
	/* TAB-videoalbum return判斷 */
	public function api_getVideoAlbum(){
		if(array_key_exists("valbum_id", $this->attribute)) return $this->api_getAllVideoAlbumByID();
		else  return $this->api_getAllVideoalbums();
	}


	/* TAB-all musci albums */
	public function api_getAllMusicalbums(){
		global $TFConfig;
		$result = array();
		$musicalums_count = new musicalbum_countsModel();
		$result = $musicalums_count->getAllMusicalbums($this->attribute['tid']);
		
		foreach($result as $key=>$value){
			$result[$key]['malbum_cover_image'] = $TFConfig['path'].$value['malbum_cover_image'];
		}
		
		$musicalbums_list = $musicalums_count->_toConvert($result);
		
		if(is_array($musicalbums_list) && count($musicalbums_list)>0){
				$result = array(
					'statusid' => 0,
					'datas' => $musicalbums_list,
					'count' => count($musicalbums_list)
				);
		}else{
			$result = array( 'statusid' => 1 );
		}


		return $result;
	}
	
	
	/* TAB-all music albums(Music) */
	public function api_getAllMusicAlbumByID(){
		global $TFConfig;
		$result = array();
		if(array_key_exists("malbum_id", $this->attribute)){
			$musicalbums = new musicalbumsModel();
			$musicalbums_info = $musicalbums->get_data("malbum_id = ".$this->attribute['malbum_id']);
			
			$musicalbums_music = new musicalbum_musicModel();
			$result = $musicalbums_music->getAllMusicAlbumByID($this->attribute['malbum_id']);
			
			foreach($result as $key=>$value){
				$result[$key]['micon'] = $TFConfig['path'].$value['micon'];
				$result[$key]['music'] = $TFConfig['path'].$value['music'];
				$result[$key]['malbum_is_download'] = $videoalbums_info['malbum_is_download'];
			}
			
			$musicalbums_list = $musicalbums_music->_toConvert($result);
			foreach($musicalbums_list as $k=>$v){
				$musicalbums_list[$k] = array_merge($v, $this->getcounts($this->attribute['tid'],$v['music_id']));
			}
			if(is_array($musicalbums_list) && count($musicalbums_list)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $musicalbums_list,
					'count' => count($musicalbums_list)
				);
			else
				$result = array( 'statusid' => 1 );
		}
		return $result;
	}
	
	
	/* TAB-musicalbum return判斷 */
	public function api_getMusicAlbum(){
		if(array_key_exists("malbum_id", $this->attribute)) return $this->api_getAllMusicAlbumByID();
		else  return $this->api_getAllMusicalbums();
	}

	
	
	/* TAB-allvideo */
	public function api_getAllVideo(){
        global $TFConfig;

		$result = array();

		$video = new videoModel();
		
		$vid = array_key_exists("vid", $this->attribute)? $this->attribute['vid']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		
		/** 種類排序 */
		$type = array_key_exists("type", $this->attribute)? $this->attribute['type']:'';
		
		/** 分頁  $page->當前頁(第幾頁); $number->每頁顯示多少條*/
		$page = array_key_exists("page", $this->attribute)? $this->attribute['page']:1;
		$number = array_key_exists("number", $this->attribute)? $this->attribute['number']:10;
		
		/** 頁面顯示記錄數 page count */
		$page_count = $video->getVideoCount($tid, $vid);
		
		/***stard***/
		$video_order = new video_orderModel();
		
		$result	= $video_order->getAllVideoOrder($tid, $vid, (($type == "popular")?"play_number":"vid"), (int)(($page-1)*$number), (int)$number);
		
		/***end***/

		foreach($result as $key=>$value){
			$result[$key]['vicon'] = $TFConfig['path'].$value['vicon'];
			$result[$key]['video'] = $TFConfig['path'].$value['video'];
		}
		
		$video_list = $video->_toConvert($result);
		TF_dump($video_list);exit;
		foreach($video_list as $k=>$v){
			$video_list[$k] = array_merge($v, $this->getcounts($this->attribute['tid'],$v['video_id']));
		}		
		if(is_array($video_list) && count($video_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $video_list,
				'count' => $page_count,
				'page' => $page
			);
		else
			$result = array( 'statusid' => 1 );
			
		/* print_r("<pre>");
		print_r($result);exit; */
		return $result;
	}

	
	
	/* TAB-all links */
	public function api_getAllLink(){
		global $TFConfig;
		
		$result = array();
		$link = new linkModel();
		$eid = array_key_exists("eid", $this->attribute)? $this->attribute['eid']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		
		$result = $link->getAllArticle($tid,$eid);
	}
	
	/* TAB-all events */
	public function api_getAllEvents(){
		global $TFConfig;

		$result = array();
		$event = new eventModel();
		$eid = array_key_exists("eid", $this->attribute)? $this->attribute['eid']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		
		/** 種類排序 */
		$type = array_key_exists("type", $this->attribute)? $this->attribute['type']:'';
		
		/** 分頁  $page->當前頁(第幾頁); $number->每頁顯示多少條*/
		$page = array_key_exists("page", $this->attribute)? $this->attribute['page']:1;
		$number = array_key_exists("number", $this->attribute)? $this->attribute['number']:10;
		
		/** 頁面顯示記錄數 page count */
		$page_count = $event->getEventCount($tid, $eid,$type);
		$pages = ceil($page_count/$number);

		$result = $event->getAllEvents($tid,$eid,$type,(int)(($page-1)*$number),(int)$number);
		
		var_dump($result);exit;
		
		foreach($result as $key=>$value){
			$result[$key]['eimage'] = $TFConfig['path'].$value['eimage'];
		}
		$events_list = $event->_toConvert($result);
		
		
		if(is_array($events_list) && count($events_list)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $events_list,
					'count' => $page_count ,
					'all_pages' => $pages ,
					'page' => $page
				);
			else
				$result = array( 'statusid' => 1 );
				
		/* print_r("<pre>");
		print_r($result);exit; */
		return $result;
	}

	
	
	
	/* TAB-all articles */
	public function api_getAllArticle(){
		global $TFConfig;
		$result = array();

		$article = new articleModel();
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$artid = array_key_exists("artid", $this->attribute)? $this->attribute['artid']:'';
		
		
		/** 分頁  $page->當前頁(第幾頁); $number->每頁顯示多少條  **/
		$page = array_key_exists("page", $this->attribute)? $this->attribute['page']:1;
		$number = array_key_exists("number", $this->attribute)? $this->attribute['number']:10;
		
		/** 頁面顯示記錄數 page count */
		$page_count =  $article->getArticleCount($tid,$artid);

		//$result = $article->getAllArticle($tid,$artid);
		/** start **/
		$result = $article->getAllArticle($tid, $artid,(int)(($page-1)*$number), (int)$number);
		/** end **/
		
		foreach($result as $key=>$value){
			$result[$key]['articon'] = $TFConfig['path'].$value['articon'];
			$result[$key]['artcontent'] = str_replace('"', '\"',$value['artcontent']);
		}
		$article_list = $article->_toConvert($result);

		foreach($article_list as $k=>$v){
			$article_list[$k] = array_merge($v, $this->getcounts($tid,$v['article_id']));
		}
		
		settype($page, "string");
		if(is_array($article_list) && count($article_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $article_list,
				'count' => $page_count,
				'page' => $page
			);
		else
			$result = array( 'statusid' => 1 );
		
		
		return $result;
	}

	
	
	/* TAB send notification*/
	public function api_sendNotification(){
		$result = array();
		$notification = new notificationModel();
		$nid = array_key_exists("nid", $this->attribute)? $this->attribute['nid']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$notification_list = $notification->_toConvert($notification->getAllnotification($tid, $nid));
		var_dump($notification_list);
		
	}
	/* TAB-all notification */
	public function api_getAllnotification(){

		$result = array();

		$notification = new notificationModel();
		$nid = array_key_exists("nid", $this->attribute)? $this->attribute['nid']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$notification_list = $notification->_toConvert($notification->getAllnotification($tid, $nid));
		if(is_array($notification_list) && count($notification_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $notification_list,
				'count' => count($notification_list)
			);
		else
			$result = array( 'statusid' => 1 );
		return $result;
	}

	
	
	
	/* TAB-all music */
	public function api_getAllMusic(){
		global $TFConfig;

		$result = array();

		$music = new musicModel();
		$mid = array_key_exists("mid", $this->attribute)? $this->attribute['mid']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$result = $music->getAllMusic($tid,$mid);
		foreach($result as $key=>$value){
			$result[$key]['micon'] = $TFConfig['path'].$value['micon'];
			$result[$key]['music'] = $TFConfig['path'].$value['music'];
		}
		$music_list = $music->_toConvert($result);

		foreach($music_list as $k=>$v){
			$music_list[$k] = array_merge($v, $this->getcounts($this->attribute['tid'],$v['mid']));

		}

		if(is_array($music_list) && count($music_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $music_list,
				'count' => count($music_list)
			);
		else
			$result = array( 'statusid' => 1 );
		return $result;
	}

	
	
	
	/* TAB-all cast */
	public function api_getAllCast(){
		global $TFConfig;

		$result = array();
		$cast = new castModel();
		$cid = array_key_exists("cid", $this->attribute)? $this->attribute['cid']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$result = $cast->getAllCast($tid, $cid);
		foreach($result as $key=>$value){
			$result[$key]['cicon'] = $TFConfig['path'].$value['cicon'];
			$result[$key]['cimage'] = $TFConfig['path'].$value['cimage'];
		}
		$cast_list = $cast->_toConvert($result);

		if(is_array($cast_list) && count($cast_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $cast_list,
				'count' => count($cast_list)
			);
		else
			$result = array( 'statusid' => 1 );

		return $result;
	}

	
	/* phase2 new form api*/
	public function api_getform(){
		$from_list = array();
		$fields = array();
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		
		$form = new formModel();
		$from_list = $form->_toConvert($form->getAllForm($tid));
		var_dump($from_list);
		
		$from_view_list = new form_viewModel();
		$fields = $from_view_list->getHitsFromView($tid);
		//TF_dump($fields);
		
		$form_info = array_merge($from_list, $fields);
		var_dump($fields);
		
		
	}
	
	
	/* TAB-all form */
	public function api_getAllForm(){
		$result = array();

		if(array_key_exists("tid", $this->attribute)){

			$form = new formModel();
			$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
			$from_list = $form->_toConvert($form->getAllForm($tid));
			//TF_dump($from_list);
			/** get form view */
			$from_view_list = new form_viewModel();
			
			$isJson = array_key_exists("json", $this->attribute)? $this->attribute['json']:'';
			if ($isJson && $isJson != ''){
				$fields = $from_view_list->getHitsFromView($tid);
				
				$from_list['fields'] = ($fields);
	
				if(is_array($from_list) && count($from_list)>0)
						$result = array(
							'statusid' => 0,
							'datas' => $from_list,
							'count' => 1
						);
					else
						$result = array( 'statusid' => 1 );
			} else {
				$fields = $from_view_list->getFromView($tid);
				$from_list['fields'] = htmlspecialchars(global_function::getFormCode($fields));
	
				if(is_array($from_list) && count($from_list)>0)
						$result = array(
							'statusid' => 0,
							'datas' => $from_list,
							'count' => 1
						);
					else
						$result = array( 'statusid' => 1 );
			}
		}
		return $result;
	}

	
	
	/* TAB-list form info */
	public function api_getListFrom(){
		$result = array();

		$listform = new list_formModel();
		$lid = array_key_exists("lid", $this->attribute)? $this->attribute['lid']:'';
		$listfrom_list = $listform->_toConvert($listform->getListFrom($lid));
		if(is_array($listfrom_list) && count($listfrom_list)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $listfrom_list,
					'count' => count($listfrom_list)
				);
			else
				$result = array( 'statusid' => 1 );

			return $result;
	}

	
	
	/* TAB- get comment_list */
	public function api_getListComment(){

		$result = array();

		$listcomment = new commentModel();
		$data_id = array_key_exists("data_id", $this->attribute)? $this->attribute['data_id']:'';
		$comment_list = $listcomment->_toConvert($listcomment->getListComment($data_id));
		if(is_array($comment_list) && count($comment_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $comment_list,
				'count' => count($comment_list)
			);
		else
			$result = array( 'statusid' => 1 );
		return $result;
	}

	
	/* TAB-get counts (3種統計結果)*/
	public function api_getCounts(){

		$result = array();

		//$counts = new numbersModel();
		$counts = new countsModel();
		$data_id = array_key_exists("data_id", $this->attribute)? $this->attribute['data_id']:'';
		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$aid = array_key_exists("aid", $this->attribute)? $this->attribute['aid']:'';
		$counts_list = $counts->_toConvert($counts->getAllCounts($data_id, $aid, $tid));
		if(is_array($counts_list) && count($counts_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $counts_list,
				'count' => count($counts_list)
			);
		else
			$result = array( 'statusid' => 1 );
		return $result;
	}

	
	
	/* TAB-get counts (3種統計結果) old*/
	public function getcounts( $tid=null, $data_id=null){
	
		$counts = new countsModel();
		$result = $counts->_toConvert($counts->getAllCounts($data_id,null,$tid));
		$result = $result[0];
		if(!$result){
			$result =array("comment_counts"=>0,"players_counts"=>0,"likes_counts"=>0);
		}
		/*echo $tid."|".$data_id;
		$numbers = new numbersModel();
		$result =array();
		if( $tid!=null && $data_id!=null ){
			$result = $numbers->_toConvert($numbers->get_data("tid = ".$tid." and data_id = ".$data_id));
		}else{
			$result =array("comment_counts"=>0,"players_counts"=>0,"likes_counts"=>0);
		}
		*/
		return $result;
	}

	
	
	
	/* 关键字搜索 */
	public function api_getKeywordSearch(){
		$result = array();

		$tid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$aid = array_key_exists("aid", $this->attribute)? $this->attribute['aid']:'';
		$keyword = array_key_exists("keyword", $this->attribute)? $this->attribute['keyword']:'';
		
		$app_tab = new app_tabModel();
		$search_list = $app_tab->getTabKeywordSearch($aid,$tid,$keyword);
		//var_dump($search_list);exit;
		if(is_array($search_list) && count($search_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $search_list,
				'count' => count($search_list)
			);
		else
			$result = array( 'statusid' => 1 );
		return $result;
		
	}

	
	
	
	
	
	
	}