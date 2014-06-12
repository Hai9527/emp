<?php
/**
 * This is the comment controller
 *
 * @author Animax
 */
class comment extends TFController {
	
	/**
	 *   comment_list
	*/
    public function index(){
				$page = parent::has('page')? parent::get('page') : 1;
				$offset = 20;
				
				
				$view = global_function::adminPage('comment_list');
				
				$comment = new commentModel();
				$filer = array();
				$where = "1";
				$tid = NULL;
				if(parent::has("aid")) {
					$where .= " and aid = ".parent::get("aid");
					$filer['aid'] = parent::get("aid");
				} 
				if(parent::has("tid")) {
					$where .= " and tid = ".parent::get("tid");
					$filer['tid'] = parent::get("tid");
					$tid = parent::get("tid");
				}
    			if(parent::has("gid")) {
					$where .= " and data_id = ".parent::get("gid");
					$filer['gid'] = parent::get("gid");
				}
				/* if(parent::has("data_name")) {
					$where .= " and data_name like '%".parent::get("data_name")."%'";
					$filer['data_name'] = parent::get("data_name");
				} */
				if(parent::has("from_date")){
					//$date = date('Y-m-d');
					$date = date('Y-m-d',time()+86400);
					/* var_dump($to_date);
					die(); */
					if(parent::has("from_date") && parent::has("to_date"))  {
						$where .= " and ctime >= '".parent::get("from_date")."' and ctime < '". date('Y-m-d',strtotime(parent::get("to_date")) + 86400)."'";
						$filer['from_date'] = parent::get("from_date");
						$filer['to_date'] = parent::get("to_date");
					}else{
						$where .= " and ctime >= '".parent::get("from_date")."' and ctime < '".$date."'";
						$filer['from_date'] = parent::get("from_date");
						$filer['to_date'] = $date;
					}
				}
				
				
				$count = $comment->count($where);
				$show_page = Pages2::show_page($count,$page,$offset);
				
				/** ����comment��� **/
				if(parent::has("export")){
					$export_list = $comment->list_data($where);
					//TF_dump($export_list);
					$export = new export();
					$export->exportComment($export_list, array('data_name'=>'Title',
															   'comment'=>'Comment',
															   'cuser_name'=>'App User',
															   'ctime'=>'Create Time'));
					exit;
		}
		
		$where .= ' Limit '.($page-1)*$offset.', '.$offset;
		$comment_list = $comment->list_data($where);
		//TF_dump($comment_list);exit;
		$view->addDisplayVar("comment_list", $comment_list);
		$view->addDisplayVar("tid", $this->getTab(parent::get("aid"), $tid));
		$view->addDisplayVar("filer", $filer);

		$view->addDisplayVar("show_page", $show_page);
		
 		return $view;
   }
   
   
   /**
	 *   �����l��֮һ:TAB 
	*/
   public function getTab($aid, $tid = NULL){
			$html = '';
			$tab = new tabModel();
			$tab_list = $tab->list_data("aid = ".$aid);
			//var_dump($tab_list);exit;
			if(!empty($tab_list)){
				$html .= '<select name="tid" style="width: 120px;" onchange="addLister()">';
				$html .= '<option value="">--select--</option>';
				foreach($tab_list as $value){
					if(strlen($value['tname'])>18){
						$html .= '<option value="'.$value['tid'].'"'.(($tid == $value['tid'])?' selected="selected"':'').'>'.substr($value['tname'],0,18).'...</option>';
					}else{
						$html .= '<option value="'.$value['tid'].'"'.(($tid == $value['tid'])?' selected="selected"':'').'>'.$value['tname'].'</option>';
					}
				}
				$html .= '</select>';
			}
			return $html;
   }
   
   
   
    /**
	 *   �����l��֮һ:TAB ->gallery->�ЃɌӔ���(���x��TAB��gallery�ĕr��,�͕����F��һ���l���x��)
	*/
   public function getGallery($gid){
		$html = '';

		if(parent::has("tid")){
		
			$tid = parent::get("tid");
			
			/**
				�ж��Ƿ�ΪGallery
			*/
			$tab_function = new app_tabModel();
			$tab_function_info = $tab_function->get_data("tid = ".$tid);
			if($tab_function_info['fnname'] != "gallery") return "";
			
			/**
				��ȡGallery�б�
			*/
			$gallery = new galleryModel();
			$gallery_list = $gallery->list_data("tid = ".$tid);
			if(!empty($gallery_list)){
				$html .= '<select name="gid" style="width: 120px; margin-left: 15px;">';
				$html .= '<option value="">--all--</option>';
				foreach($gallery_list as $value){
					$html .= '<option value="'.$value['gid'].'"'.(($gid == $value['gid'])?' selected="selected"':'').'>'.$value['gname'].'</option>';
				}
				$html .= '</select>';
			}
		
		}
		echo $html;exit;
   }

	
	
	/**
	 *   delete comment��һӛ�
	*/
	public function deleteComment(){
		$comment = new commentModel();
		if(parent::has("com_id")){
			$comment -> remove("com_id = ". parent::get("com_id"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}
}


?>
