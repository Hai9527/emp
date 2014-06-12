<?php
/**
 * This is the cast controller
 *
 * @author Animax
 */

class article extends TFController {
    public function index(){
		
    }
	
	
	
	 /**
	 *   get article_list
	*/
	public function getArticleList($view){
			$page = parent::has('page')? parent::get('page') : 1;
			$number = 10;
			$offset = ($page-1)*$number;
			$where = "";
			
			if(parent::has("tid")){
				$where .= "tid = ".parent::get("tid");
			}
			$link = '?c=main&a=create&step=customize_feature&aid='.parent::get("aid").'&tid='.parent::get("tid");
			
			$article = new articleModel();
			
			$count = $article->count($where);
			
			$show_page = Pages::show_page($count,$page,$link,$number);
			
			//$where .= ' Limit '.($page-1)*$number.', '.$number;
			$article_list = $article->list_data($where, null, $offset, $number, 'aorder','DESC');
			
			$counts = new countsModel();
			foreach($article_list as $key=>$value){
				$result = $counts->_toConvert($counts->getAllCounts($value['artid'],null,parent::get("tid")));
				
				$result = $result[0];
				if(!$result){
					$article_list[$key]['comments_num'] = 0;
				} else {
					$article_list[$key]['comments_num'] = $result['comment_counts'];
				}
			}
			
			$view->addDisplayVar("count", $count);
			$view->addDisplayVar("offset", $offset);
			$view->addDisplayVar("show_page", $show_page);
			$view->addDisplayVar("article_list", $article_list);

			return $view;
	}
	
	
	
	
	
	 /**
	 *   article add&edit
	*/
	public function editArticle(){
	
				//$view = new TFView('tab_feature_edit.html');
				$view = global_function::adminPage('tab_feature_edit');
				
				$view->addDisplayVar("edit_page", 'tab_edit_article');
				
				$tid = parent::get('tid');
				
				$action = "?c=article&a=saveArticle&tid=".$tid;
				
				$title = "Add New";
				
				$article = new articleModel();
				
				if(parent::has("artid")){
				
					$title = "Edit";
				
					$action = "?c=article&a=saveArticle&artid=".parent::get("artid");
					
					$where .= "artid = ".parent::get("artid");
					
					$article_info = $article->get_data($where);
					
					//$article_info['artname'] = global_function::d_htmlspecialchars($article_info['artname']);
					
					$view->addDisplayVar("article_info", $article_info);	

					//TF_dump($article_info);	exit;
					
				}
				else {
					
				}
				
				$view->addDisplayVar("title", $title);	
				
				$view->addDisplayVar("action", $action);	
				
				return $view;
				
	}
	
	
	
	
	 /**
	 *   article add&edit后save
	*/
	public function saveArticle(){
		
				$artname = parent::has("artname")? parent::get("artname") : '';
				$articon = parent::has("articon")? parent::get("articon") : '';
				$artpublish_time = parent::has("artpublish_time")? parent::get("artpublish_time") : '';
				$artstatus = parent::has("artstatus")? parent::get("artstatus") : 0;
				$artshort_description = parent::has("artshort_description")? parent::get("artshort_description") : '';
				$artcontent = parent::has("artcontent")? parent::get("artcontent") : '';
				
				$data = array(
							"artname" => htmlspecialchars_decode($artname),
							"articon" => $articon,
							"artpublish_time" => $artpublish_time,
							"artstatus" => $artstatus,
							"artshort_description" => addslashes($artshort_description),
							"artcontent" => $artcontent
						);
				$article = new articleModel();
				
				if(parent::has("artid")){
				
					$article->update("artid = ". parent::get("artid"), $data);
					
				}else if(parent::has("tid")){
				
					$data["tid"] = parent::get("tid");
					
					//$data["artcreate_time"] = date("Y-m-d H:i:s");
					
					$insert_id = $article->create($data);
					
					$article->update("artid = ". $insert_id, "aorder = ". $insert_id );
				}
				
				echo '<script>self.parent.history.go(0); </script>';
				exit;
	
	}
	
	
	
	
	
	 /**
	 *   delete article 
	*/
	public function deleteArticle(){
	
		$article = new articleModel();
		if(parent::has("artid")){
		
			$article->remove("artid = ". parent::get("artid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}
	
	
	
	
	 /**
	 *   更改article排序
	*/
	public function goSort(){
		$artid = parent::has("id")? parent::get("id") : '';
		$oldOrder = parent::has("oldOrder")? parent::get("oldOrder") : '';
		$newOrder = parent::has("newOrder")? parent::get("newOrder") : '';
		$tid = parent::has("tid")? parent::get("tid") : '';
		$aid = parent::has("aid")? parent::get("aid") : '';
		if(!empty($artid) && !empty($oldOrder) && !empty($newOrder) && !empty($tid) && !empty($aid)){
			$ltr = ($newOrder>$oldOrder)?true:false;
			$offset = ($ltr)?$oldOrder-1:$newOrder-1;
			$number = ($ltr)?$newOrder-$oldOrder+1:$oldOrder-$newOrder+1;
			$db = new articleModel();
			$order_arr = $db->list_data("`tid` = $tid", "`artid`,`aorder`", $offset, $number, "aorder", "DESC");
			$updateOrder = ($ltr)?$order_arr[$number-1]['aorder']:$order_arr[0]['aorder'];
			// echo $updateOrder;
			// print_r($order_arr);exit;
			
			if($ltr){
				foreach($order_arr as $value){
					$db->update("artid = ".$value['artid'], array("aorder"=>$value['aorder']+1));
				}
			}
			else{
				foreach($order_arr as $value){
					$db->update("artid = ".$value['artid'], array("aorder"=>$value['aorder']-1));
				}
			}
			$db->update("artid = ".$artid, array("aorder"=>$updateOrder));
			
			echo ceil($newOrder/10);exit;
		}
		exit;
	}
}
?>
