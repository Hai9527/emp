<?php
/**
 * This is the cast controller
 *
 * @author Animax
 */
class cast extends TFController {
    public function index(){
		
    }
	
	
	/**
	 *   獲取Cast_list
	*/
	public function getCastList($view){
			$page = parent::has('page')? parent::get('page') : 1;
			$number = 10;
			$offset = ($page-1)*$number;
			$where = "";

			if(parent::has("tid")){
				$where .= "tid = ".parent::get("tid");
			}
			$link = '?c=main&a=create&step=customize_feature&aid='.parent::get("aid").'&tid='.parent::get("tid");
			
			$cast = new castModel();
			$count = $cast->count($where);
			
			$show_page = Pages::show_page($count,$page,$link,$number);
			
			// $where .= ' Limit '.($page-1)*$number.', '.$number;
			// $cast_list = $cast->list_data($where);
			$cast_list = $cast->list_data($where, null, $offset, $number, 'corder','DESC');


			$view->addDisplayVar("count", $count);
			$view->addDisplayVar("offset", $offset);
			$view->addDisplayVar("show_page", $show_page);
			$view->addDisplayVar("cast_list", $cast_list);

			return $view;
	}
	
	
	
	
	 /**
	 *   cast add&edit
	*/
	public function editCast(){
		
			//$view = new TFView('tab_feature_edit.html');
			$view = global_function::adminPage('tab_feature_edit');
			
			$view->addDisplayVar("edit_page", 'tab_edit_cast');
			
			$action = "?c=cast&a=saveCast&tid=".parent::get("tid");
			
			$title = "Add New";
			
			$cast = new castModel();
			
			if(parent::has("cid")){
			
				$title = "Edit";
				
				$action = "?c=cast&a=saveCast&cid=".parent::get("cid");
			
				$where .= "cid = ".parent::get("cid");
				
				$cast_info = $cast->get_data($where);
				
				$view->addDisplayVar("cast_info", $cast_info);			
				
			}
			
			$view->addDisplayVar("title", $title);	
			
			$view->addDisplayVar("action", $action);
			
			return $view;
		
	}
	
	
	
	
	
	 /**
	 *   cast add&edit后save
	*/
	public function saveCast(){
	
			$cname = parent::has("cname")? parent::get("cname") : '';
			$cicon = parent::has("cicon")? parent::get("cicon") : '';
			$ccontent = parent::has("ccontent")? parent::get("ccontent") : '';
			$cstatus = parent::has("cstatus")? parent::get("cstatus") : '';
			$cimage = parent::has("cimage")? parent::get("cimage") : '';
			
			$data = array(
						"cname" => htmlspecialchars_decode($cname),
						"cicon" => $cicon,
						"ccontent" => addslashes($ccontent),
						"cstatus" => $cstatus,
						"cimage" => $cimage,
					);
			$cast = new castModel();
			
			if(parent::has("cid")){
			
				$cast->update("cid = ". parent::get("cid"), $data);
				
			}else if(parent::has("tid")){
				
				$data["tid"] = parent::get("tid");
				
				$insert_id = $cast->create($data);
				
				$cast->update("cid = ". $insert_id, "corder = ". $insert_id );
			
			}
			
			echo '<script>self.parent.history.go(0); </script>';
			exit;
		
	}
	
	
	
	
	 /**
	 *   delete cast單一記錄
	*/
	public function deleteCast(){
	
		$cast = new castModel();
		if(parent::has("cid")){
		
			$cast->remove("cid = ". parent::get("cid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}
	
	
	
	
	
	 /**
	 *   更改cast的排序
	*/
	public function goSort(){
		$cid = parent::has("id")? parent::get("id") : '';
		$oldOrder = parent::has("oldOrder")? parent::get("oldOrder") : '';
		$newOrder = parent::has("newOrder")? parent::get("newOrder") : '';
		$tid = parent::has("tid")? parent::get("tid") : '';
		$aid = parent::has("aid")? parent::get("aid") : '';
		if(!empty($cid) && !empty($oldOrder) && !empty($newOrder) && !empty($tid) && !empty($aid)){
			$ltr = ($newOrder>$oldOrder)?true:false;
			$offset = ($ltr)?$oldOrder-1:$newOrder-1;
			$number = ($ltr)?$newOrder-$oldOrder+1:$oldOrder-$newOrder+1;
			$db = new castModel();
			$order_arr = $db->list_data("`tid` = $tid", "`cid`,`corder`", $offset, $number, "corder", "DESC");
			$updateOrder = ($ltr)?$order_arr[$number-1]['corder']:$order_arr[0]['corder'];
			
			if($ltr){
				foreach($order_arr as $value){
					$db->update("cid = ".$value['cid'], array("corder"=>$value['corder']+1));
				}
			}
			else{
				foreach($order_arr as $value){
					$db->update("cid = ".$value['cid'], array("corder"=>$value['corder']-1));
				}
			}
			$db->update("cid = ".$cid, array("corder"=>$updateOrder));
			
			echo ceil($newOrder/10);exit;
		}
		exit;
	}
}
?>
