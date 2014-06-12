<?php
/**
 * Description of api.controller.php
 * @author Animax
 */
class api extends apiController {

/** getAppConfig */
	public function application(){
		echo json_encode($this->api_getAppConfig()); exit;
	}

/** getTestPreview */
	public function testpreview(){
		echo json_encode($this->api_getTestPreview()); exit;
	}



/**getHomepage and about**/
	public function homepage(){
		echo json_encode($this->api_getHomepage()); exit;
	}

/** getAllGallery  getAllGalleryByID  */
	public function gallery(){
		echo json_encode($this->api_getGallery()); exit;
	}

/** getAllVideoalbums**/
	public function AllVideoalbums(){
		echo json_encode($this->api_getAllVideoalbums()); exit;
	}
	
/** getAllMusicalbums**/
	public function AllMusicalbums(){
		echo json_encode($this->api_getMusicAlbum()); exit;
	}
	
	/** getAllVideo */
	public function video(){
		echo json_encode($this->api_getAllVideo()); exit;
	}

/** getAllEvents
	getAllEventByID
 */
	public function event(){
		echo json_encode($this->api_getAllEvents()); exit;
	}


/** getAllArticle
	getAllArticleByID
 */
	public function article(){
		echo json_encode($this->api_getAllArticle()); exit;
	}


/** getAllnotification */

	public function notification(){
	 	echo json_encode($this->api_getAllnotification()); exit;
	}
	
	
/** send notification**/
	public function sendnotification(){
		echo json_encode($this->api_sendNotification());exit;
	}



/** getAllMusic */
	public function music(){
	 	echo json_encode($this->api_getAllMusic()); exit;
	}

/** getAllCast
	getAllCastByID
 */
	public function cast(){
		echo json_encode($this->api_getAllCast()); exit;
	}



/** phase2 new getForm*/
	public function form_infomation(){
		echo json_encode($this->api_getform()); exit;
	}
	

/** getForm*/
	public function form(){
		echo json_encode($this->api_getAllForm()); exit;
	}


/** getListform*/
	public function listform(){
		echo json_encode($this->api_getListFrom()); exit;
	}

/**getListComment**/
	public function listcomment(){
		echo json_encode($this->api_getListComment()); exit;
	}

/**insert into user_information**/
	public function insertUser(){
		if($_REQUEST){
			/* var_dump($_REQUEST['apiJSON']);
			var_dump(json_decode($_REQUEST['apiJSON'], true)); */
			$result = json_decode(str_replace("\\","",$_REQUEST['apiJSON']), true);
			/* var_dump($result);exit; */
			$data = array();
			
			$data['user_id'] = $result['user_id'];
			$data['user_name'] = $result['user_name'];
			$data['user_email'] = $result['user_email'];
			$data['user_city'] = $result['user_city'];
			$data['aid'] = $result['aid'];
			

			$user_info = new userModel();
			$user_id = $user_info->create($data);
			if($user_id) $back = array("status"=>1);
			else $back = array("status"=>0);

			echo json_encode($back);
			exit;
		}
	}
	

/** insert into comment*/
	public function insertComment(){
		if($_REQUEST){
			//var_dump($_REQUEST['apiJSON']);exit;
			$result = json_decode(str_replace("\\","",$_REQUEST['apiJSON']), true);
			//var_dump($result);
			$data = array();
			$tab = new tabModel();
			$tab_info = $tab->get_data("tid = ".$result['tid']);

			$data['aid'] = $result['aid'];
			$data['tid'] = $result['tid'];
			$data['tab_name'] = $tab_info['tname'];
			$data['cuser_id'] = $result['cuser_id'];
			$data['cuser_name'] = $result['cuser_name'];
			$data['cuser_avatar'] = $result['cuser_avatar'];
			$data['comment'] = urldecode($result['comment']);
			$data['data_id'] = $result['data_id'];
			$data['data_name'] = $result['data_name'];
			$data['ctime'] = date("Y-m-d H:i:s");

			$comment_info = new commentModel();
			$comment_id = $comment_info->create($data);
			/** update accumulative comment count */
			$this->updateComment($result);

			if($comment_id) $back = array("status"=>1);
			else $back = array("status"=>0);

			echo json_encode($back);
			exit;
		}
	}

	public function updateComment($result){
		$where = "aid = ".$result['aid']." and tid = ".$result['tid']." and data_id = ".$result['data_id'];

		$comment_info = new commentModel();
		$count_num = $comment_info->count($where);

		$where .= " and acc_type = 3";
		$accumulative = new accumulativeModel();
		$count = $accumulative->count($where);
		if($count>0){
			$accumulative->update($where, array("acc_counts"=>$count_num));
		}else{
			$accumulative->create(array(
					"aid"=>$result['aid'],
					"tid"=>$result['tid'],
					"data_id"=>$result['data_id'],
					"acc_type"=>3,
					"acc_counts"=>$count_num
				)
			);
		}

	}



/** insert into form list*/
	public function insertListForm(){
		if($_REQUEST){
			$result = json_decode(stripcslashes($_REQUEST['apiJSON']), true);
			$data = array();

			$data['tid'] = $result['tid'];
			$data['mobile_id'] = $result['mobileid'];
			$data['lform_text'] = serialize($result['formdata']);
			$data['lpost_time'] = date("Y-m-d H:i:s");

			$list_form = new listformModel();
			$listform_id = $list_form->create($data);
			if($listform_id) $back = array("status"=>1);
			else $back = array("status"=>0);

			echo json_encode($back);
			exit;
		}
	}


/**getAccumulativeCounts**/
	public function accumulativecounts(){

		global $apiJSON;

		$accumulative_counts = new accumulativeModel();

		$result = json_encode($accumulative_counts->api_getAccumulativeCounts());

		echo $result;

		exit;
	}


/** insert into accumulative_counts*/
	public function insertAccumulativeCounts(){
		if($_REQUEST){
			$result = json_decode(stripcslashes($_REQUEST['apiJSON']), true);
			$data = array();

			$data['aid'] = $result['aid'];
			$data['tid'] = $result['tid'];
			/* $data['accid'] = $result['accid']; */
			$data['data_id'] = $result['data_id'];
			$data['acc_type'] = $result['acc_type'];

			$accumulative_counts = new accumulativeModel();

			if($accumulative_counts->count($data)>0){
				$accumulative_counts->update($data, "acc_counts=acc_counts+1");
			}else{
				$data['acc_counts'] = 1;
				$accumulative_counts->create($data);
			}
			$number = new countsModel();
			$number_info = $number->get_data("tid = ".$data['tid']." and data_id = ".$data['data_id']);
			//var_dump($number_info);exit;

			echo json_encode(array("status"=>1,"data"=>array("likes_count"=>$number_info['likes_num'],"player_count"=>$number_info['play_number'],"comment_count"=>$number_info['comment_num'])));
			exit;
		}
		else{
			echo json_encode(array("status"=>0));
			exit;
		}
	}

	public function upadataEvent(){
		$result = json_decode(stripcslashes(parent::get("apiJSON")), true);
		
		if($result["eid"]){
			if(isset($result["egaree"])){
				$event = new eventModel();
				switch($result["egaree"]){
					case -1: $set = "eunagree = eunagree+1"; break;
					case 1: $set = "egaree = egaree+1"; break;
					default: $set = "emaybe = emaybe+1"; break;
				}
				$event->update("eid = ".$result["eid"], $set);
				echo json_encode(array("status"=>1));
			}
		}else{
			echo json_encode(array("status"=>0));
		}
		exit;
	}

	/**getCounts**/
	public function allCounts(){

		echo json_encode($this->api_getCounts()); exit;
	}
	
	
	/**¹Ø¼ü×ÖËÑË÷**/
	public function keySearch(){
		echo json_encode($this->api_getKeywordSearch()); exit;
	}
}



