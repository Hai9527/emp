<?php
/**
 * This is the video controller
 *
 * @author Animax
 */
class video extends TFController {
    public function index(){

    }

	/**
	 * 獲取video列表數據
	*/
	public function getVideoList($view){
		$page = parent::has('page')? parent::get('page') : 1;
 		$number = 10;
 		$offset = ($page-1)*$number;
		$where = "";
	
		if(parent::has("tid")){
			$where .= "tid = ".parent::get("tid");
		}
		$link = '?c=main&a=create&step=customize_feature&aid='.parent::get("aid").'&tid='.parent::get("tid");
		
		$video = new videoModel();
		
		$count = $video->count($where);
		
		$show_page = Pages::show_page($count,$page,$link,$number);
		
		//$where .= ' Limit '.($page-1)*$number.', '.$number;
		
		//$video_list = $video->list_data($where);
		$video_list = $video->list_data($where, null, $offset, $number, 'vorder','DESC');

		$counts = new countsModel();
		foreach($video_list as $key=>$value){
/*
			$numbers = new numbersModel();
			$numbers_info = $numbers->get_data("tid = ".parent::get("tid")." and data_id = ".$value['vid']);
			$video_list[$key]['players_num'] = $numbers_info['players_num'];
			$video_list[$key]['likes_num'] = $numbers_info['likes_num'];
			$video_list[$key]['comments_num'] = $numbers_info['comments_num'];
*/
			
			$result = $counts->_toConvert($counts->getAllCounts($value['vid'],null,parent::get("tid")));
			
			$result = $result[0];
			if(!$result){
				$video_list[$key]['players_num'] = 0;
				$video_list[$key]['likes_num'] = 0;
				$video_list[$key]['comments_num'] = 0;
			} else {
				$video_list[$key]['players_num'] = $result['players_counts'];
				$video_list[$key]['likes_num'] = $result['likes_counts'];
				$video_list[$key]['comments_num'] = $result['comment_counts'];
			}
		}

		$view->addDisplayVar("count", $count);
		$view->addDisplayVar("offset", $offset);
		$view->addDisplayVar("show_page", $show_page);
		$view->addDisplayVar("video_list", $video_list);

		return $view;
	}

	
	
	/**
	 * video的add或edit頁面
	*/
	public function editVideo(){

        //$view = new TFView('tab_feature_edit.html');
        $view = global_function::adminPage('tab_feature_edit');

    	$view->addDisplayVar("edit_page", 'tab_edit_video');

		$action = "?c=video&a=saveVideo&tid=".parent::get("tid");

		$title = "Add New";

		$video = new videoModel();

		if(parent::has("vid")){

			$title = "Edit";

			$action = "?c=video&a=saveVideo&vid=".parent::get("vid");

			$where .= "vid = ".parent::get("vid");

			$video_info = $video->get_data($where);

			$view->addDisplayVar("video_info", $video_info);

		}

		$view->addDisplayVar("title", $title);

		$view->addDisplayVar("action", $action);

		return $view;

	}

	
	
	/**
	 * add或edit之後save數據
	*/
	public function saveVideo(){

		$vname = parent::has("vname")? parent::get("vname") : '';
		$vicon = parent::has("vicon")? parent::get("vicon") : '';
		$video = parent::has("video")? parent::get("video") : '';
		$vcaption = parent::has("vcaption")? parent::get("vcaption") : '';
		$vis_download = parent::has("vis_download")? parent::get("vis_download") : 0;
		$vstatus = parent::has("vstatus")? parent::get("vstatus") : '';

		$data = array(
					"vname" => htmlspecialchars_decode($vname),
					"vicon" => $vicon,
					"video" => $video,
					"vcaption" => $vcaption,
					"vis_download" => $vis_download,
					"vsize" => filesize($video),
					"vstatus" => $vstatus
				);
		$video = new videoModel();

		if(parent::has("vid")){

			$video->update("vid = ". parent::get("vid"), $data);

		}else if(parent::has("tid")){
		
			$data["tid"] = parent::get("tid");

			/* $data["artcreate_time"] = date("Y-m-d H:i:s"); */


			$insert_id = $video->create($data);
			
			$video->update("vid = ". $insert_id, "vorder = ". $insert_id );

		}

		echo '<script>self.parent.history.go(0); </script>';
		exit;

	}

	
	
	
	/**
	 * delete video的某一條記錄
	*/
	public function deleteVideo(){
		$video = new videoModel();
		if(parent::has("vid")){
			$video->remove("vid = ". parent::get("vid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}
	
	
	
	
	/**
	 * 修改video的order
	*/
	public function goSort(){
		$vid = parent::has("id")? parent::get("id") : '';
		$oldOrder = parent::has("oldOrder")? parent::get("oldOrder") : '';
		$newOrder = parent::has("newOrder")? parent::get("newOrder") : '';
		$tid = parent::has("tid")? parent::get("tid") : '';
		$aid = parent::has("aid")? parent::get("aid") : '';
		if(!empty($vid) && !empty($oldOrder) && !empty($newOrder) && !empty($tid) && !empty($aid)){
			$ltr = ($newOrder>$oldOrder)?true:false;
			$offset = ($ltr)?$oldOrder-1:$newOrder-1;
			$number = ($ltr)?$newOrder-$oldOrder+1:$oldOrder-$newOrder+1;
			$db = new videoModel();
			$order_arr = $db->list_data("`tid` = $tid", "`vid`,`vorder`", $offset, $number, "vorder", "DESC");
			$updateOrder = ($ltr)?$order_arr[$number-1]['vorder']:$order_arr[0]['vorder'];
			
			if($ltr){
				foreach($order_arr as $value){
					$db->update("vid = ".$value['vid'], array("vorder"=>$value['vorder']+1));
				}
			}
			else{
				foreach($order_arr as $value){
					$db->update("vid = ".$value['vid'], array("vorder"=>$value['vorder']-1));
				}
			}
			$db->update("vid = ".$vid, array("vorder"=>$updateOrder));
			
			echo ceil($newOrder/10);exit;
		}
		exit;
	}
}
?>
