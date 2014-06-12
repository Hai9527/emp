<?php
/**
 * This is the page controller
 *
 * @author Animax
 */
class page extends TFController {
    public function index(){
		
    }
	
	/**
	 * 添加PAGE模塊的頁面
	*/
	public function getPageList($view){
		if(parent::get("tid")){
			$homepage = new homepageModel();
			$page_info = $homepage->get_data("tid = ".parent::get("tid"));	
			
			$view->addDisplayVar("page_info", $page_info);
		}else{
			header("Location:?c=main&a=create&step=add_feature&aid=".parent::get("aid"));
		}
		return $view;
	}
	
	
	
	/**
	 * 默認home的頁面
	*/
	public function getHomeinfo($view){
		$tab = new tabModel();
		$tab_info = $tab->get_data("aid = ".parent::get("aid")." and tdefault = -1");

		if($tab_info['tid']){
			$homepage = new homepageModel();
			$page_info = $homepage->get_data("tid = ".$tab_info['tid']);	

			
			$view->addDisplayVar("tid", $tab_info['tid']);
			$view->addDisplayVar("page_info", $page_info);
		}else{
			header("Location:?c=main&a=create&step=add_feature&aid=".parent::get("aid"));
		}
		return $view;
	}
	
	
	
	/**
	 * 保存page模塊的頁面的數據
	*/
	public function savePage(){
		if(parent::has("tid")){
			$tid = parent::get("tid");
			$htemplate = parent::has("htemplate") ? parent::get("htemplate") : 0;
			$hcontent = parent::has("hcontent") ? parent::get("hcontent") : '';
			$himage = parent::has("himage") ? parent::get("himage") : '';;
			
			$data = array(
						"htemplate" => $htemplate,
						"hcontent" => $hcontent,
						"himage" => $himage
					);
			
			$homepage = new homepageModel();
			$count = $homepage->count("tid = ".$tid);
			
			if($count>0){
				$homepage->update("tid = ".$tid, $data);
			}else{
				$data['tid'] = $tid;
				$homepage->create($data);
			}
		}
		echo '<script>history.go(-1);</script>';
	
	}
}
?>
