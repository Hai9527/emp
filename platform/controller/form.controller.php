<?php
/**
 * This is the form controller
 *
 * @author Animax
 */
class form extends TFController {
    public function index(){
		
    }
	
	/**
	 *   獲取form_list
	*/
	public function getFormList($view){
	
				$form = new formModel();
				$form_info = $form->get_data("tid = ".parent::get("tid"));
			
				$view->addDisplayVar("form_info", $form_info);	
				
				/**  获取fromview数据  **/
				$form_view = new form_viewModel();
				
				$form_view_list = $form_view->list_data("tid = ".parent::get("tid")." order by fvid");
				
				$current = array();
				
				foreach($form_view_list as $key=>$value){
					$temp_arr = array();
					foreach($form_view_list as $k=>$v){
						if($value['fv_name'] == $v['fv_name'] && !in_array($value['fv_name'], $current)){
							array_push($temp_arr, $v);
						}
						$form_view_list[$key]['temp'] = $temp_arr;
					}
					array_push($current, $value['fv_name']);
				}
				
				$list = array();
				foreach($form_view_list as $key=>$value){
					if(count($value['temp'])>0){
						array_push($list, $value);
					}
				}
				
				$view->addDisplayVar("form_view_list", $list);
				return $view;
	}
	

	
	
	/**
	 *   add&edit form
	*/
	public function saveForm(){
		if(parent::has("tid")){
		
			/** from base information*/
			$tid = parent::get("tid");
			
			$fintroduction = parent::has("fintroduction") ? parent::get("fintroduction") : '';
			$lmessage = parent::has("lmessage") ? parent::get("lmessage") : '';
				
			$data = array(
					"fintroduction" => addslashes($fintroduction),
					"lmessage" => addslashes($lmessage)
			);
			
			$form = new formModel();
			
			$count = $form->count("tid = ".$tid);
			if($count>0){
				$form->update("tid = ".$tid, $data);
			}else{
				$data['tid'] = $tid;
				$form->create($data);
			}
			
			
			
			/** from field information **/
			if(parent::has("fvid")){
				$form_view = new form_viewModel();
			
				$fvid = parent::get("fvid");
				$vis_hits = parent::get("vis_hits");
				$fv_required = parent::get("fv_required");
				$fv_name = parent::get("fv_name");
				
				for($i=0; $i<count($fvid); $i++){
					//$form_view->update("fvid = ".$fvid[$i], array("vis_hits"=>$vis_hits[$i], "fv_required"=>$fv_required[$i]));
					$form_view->update("tid = ".$tid." and fv_name = '".$fv_name[$i]."'", array("vis_hits"=>$vis_hits[$i], "fv_required"=>$fv_required[$i]));
				}
			}
			
		}
		echo '<script>history.go(-1);</script>';
	
	}
	
	
	
	
	public function formList(){
	
		if(parent::has("tid")){
		
			$view = global_function::adminPage('tab_feature_edit');
			
			$view->addDisplayVar("edit_page", 'tab_form_manager');
			
			$title = "Form Data Management";
			
			/** get Form Field*/
			$form_view = new form_viewModel();
			
			$form_view_list = $form_view->list_data("tid = ".parent::get("tid")." and vis_hits = 1 group by fv_name");
			//var_dump($form_view_list);exit();
			
			$view->addDisplayVar("form_view_list", $form_view_list);
			
			/** get List info */
			$list_form = new list_formModel();
			$form_list = $list_form->list_data("tid = ".parent::get("tid"));

			if(count($form_list)>0){
				foreach($form_list as $key=>$value){
					$form_list[$key]['lform_text'] = unserialize($value['lform_text']);
				}
			}
			
			
			$view->addDisplayVar("form_list", $form_list);
			$view->addDisplayVar("title", $title);
			
			return $view;
		}
	}
	

	
	/**
	 *   delete form
	*/

	public function deleteForm(){

		$form = new list_formModel();
		if(parent::has("lid")){

			$form->remove("lid = ". parent::get("lid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}

}
?>
