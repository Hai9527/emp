<?php
/**
 * This is the music controller
 *
 * @author Animax
 */
class music extends TFController {
    public function index(){
		
    }
	
	
	/**
	 *   獲取Music list
	*/
	public function getMusicList($view){
				$page = parent::has('page')? parent::get('page') : 1;
				$number = 10;
				$offset = ($page-1)*$number;
				$where = "";
			
				if(parent::has("tid")){
					$where .= "tid = ".parent::get("tid");
				}
				$link = '?c=main&a=create&step=customize_feature&aid='.parent::get("aid").'&tid='.parent::get("tid");
				
				$music = new musicModel();
				$count = $music->count($where);
				
				$show_page = Pages::show_page($count,$page,$link,$number);
			
				//$where .= ' Limit '.($page-1)*$number.', '.$number;
				//$music_list = $music->list_data($where);
				$music_list = $music->list_data($where, null, $offset, $number, 'morder','DESC');
				
				$counts = new countsModel();
				foreach($music_list as $key=>$value){
/*
					$numbers = new numbersModel();
					$numbers_info = $numbers->get_data("tid = ".parent::get("tid")." and data_id = ".$value['mid']);
					$music_list[$key]['players_num'] = $numbers_info['players_num'];
					$music_list[$key]['likes_num'] = $numbers_info['likes_num'];
					$music_list[$key]['comments_num'] = $numbers_info['comments_num'];
*/
					
					$result = $counts->_toConvert($counts->getAllCounts($value['mid'],null,parent::get("tid")));
					$result = $result[0];
					
					if(!$result){
						$music_list[$key]['players_num'] = 0;
						$music_list[$key]['likes_num'] = 0;
						$music_list[$key]['comments_num'] = 0;
					} else {
						$music_list[$key]['players_num'] = $result['players_counts'];
						$music_list[$key]['likes_num'] = $result['likes_counts'];
						$music_list[$key]['comments_num'] = $result['comment_counts'];
					}
				}
				//var_dump($music_list);exit;
				$view->addDisplayVar("count", $count);
				$view->addDisplayVar("offset", $offset);
				$view->addDisplayVar("music_list", $music_list);
				$view->addDisplayVar("show_page", $show_page);

				return $view;
		}
	
	
	
	
	/**
	 *   music add&edit
	*/
	public function editMusic(){
	
			//$view = new TFView('tab_feature_edit.html');
			$view = global_function::adminPage('tab_feature_edit');
			
			$view->addDisplayVar("edit_page", 'tab_edit_music');
			
			$action = "?c=music&a=saveMusic&tid=".parent::get("tid");
			
			$title = "Add New";
			
			$music = new musicModel();
			
			if(parent::has("mid")){
			
				$title = "Edit";
				
				$action = "?c=music&a=saveMusic&mid=".parent::get("mid");
			
				$where .= "mid = ".parent::get("mid");
				
				$music_info = $music->get_data($where);
				
				$view->addDisplayVar("music_info", $music_info);			
				
			}
			$view->addDisplayVar("title", $title);	
			
			$view->addDisplayVar("action", $action);
			
			return $view;
			
		}
	
	
	
	
	/**
	 *   music add&edit后save
	*/
	public function saveMusic(){
	
			$mname = parent::has("mname")? parent::get("mname") : '';
			$micon = parent::has("micon")? parent::get("micon") : '';
			$music = parent::has("music")? parent::get("music") : '';
			$mcaption = parent::has("mcaption")? parent::get("mcaption") : '';
			$mstatus = parent::has("mstatus")? parent::get("mstatus") : '';
			$mis_download = parent::has("mis_download")? parent::get("mis_download") : 0;
			
			$data = array(
						"mname" => htmlspecialchars_decode($mname),
						"micon" => $micon,
						"music" => $music,
						"mcaption" => $mcaption,
						"mstatus" => $mstatus,
						"mis_download" => $mis_download,
						"msize" => filesize($music)
					);
					
			//TF_dump($data);
			$music = new musicModel();
			
			if(parent::has("mid")){
			
				$music->update("mid = ". parent::get("mid"), $data);
				
			}else if(parent::has("tid")){
			
				$data["tid"] = parent::get("tid");
				
				$insert_id = $music->create($data);
				
				$music->update("mid = ". $insert_id, "morder = ". $insert_id );
			
			}
			
			echo '<script>self.parent.history.go(0); </script>';
			exit;
		
	}
	
	
	
	
	/**
	 *   delete單一記錄
	*/
	public function deleteMusic(){
	
		$music = new musicModel();
		if(parent::has("mid")){
		
			$music->remove("mid = ". parent::get("mid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}
	
	
	
	/**
	 *   更改music的排序
	*/
	public function goSort(){
		$mid = parent::has("id")? parent::get("id") : '';
		$oldOrder = parent::has("oldOrder")? parent::get("oldOrder") : '';
		$newOrder = parent::has("newOrder")? parent::get("newOrder") : '';
		$tid = parent::has("tid")? parent::get("tid") : '';
		$aid = parent::has("aid")? parent::get("aid") : '';
		if(!empty($mid) && !empty($oldOrder) && !empty($newOrder) && !empty($tid) && !empty($aid)){
			$ltr = ($newOrder>$oldOrder)?true:false;
			$offset = ($ltr)?$oldOrder-1:$newOrder-1;
			$number = ($ltr)?$newOrder-$oldOrder+1:$oldOrder-$newOrder+1;
			$db = new musicModel();
			$order_arr = $db->list_data("`tid` = $tid", "`mid`,`morder`", $offset, $number, "morder", "DESC");
			$updateOrder = ($ltr)?$order_arr[$number-1]['morder']:$order_arr[0]['morder'];
			
			if($ltr){
				foreach($order_arr as $value){
					$db->update("mid = ".$value['mid'], array("morder"=>$value['morder']+1));
				}
			}
			else{
				foreach($order_arr as $value){
					$db->update("mid = ".$value['mid'], array("morder"=>$value['morder']-1));
				}
			}
			$db->update("mid = ".$mid, array("morder"=>$updateOrder));
			
			echo ceil($newOrder/10);exit;
		}
		exit;
	}
	
}
?>
