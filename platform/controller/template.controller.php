<?php
/**
 * This is the template controller
 *
 * @author Animax
 */
class template extends TFController {
    public function index(){
		
    }
		
	
	
	/**
	 * 獲取temid
	*/
	public function getAppTemid($view){
		if(parent::has("aid")){
			$application = new applicationModel();
			$application_info = $application->get_data("aid = ".parent::get("aid"));
			//TF_dump($application_info);
			$view->addDisplayVar("temid", $application_info['temid']);
			return $view;
		}
		exit;
	}
	
	
	
	/**
	 * 保存所選擇的template
	*/
	public function saveAppTemid(){
		if(parent::has("aid")){
			
			$template = new templateModel();
			$template_info = $template->get_data("temid = ".parent::get("temid"));
			array_shift($template_info);
			array_shift($template_info);
				
			$template_info['temid'] = parent::get("temid");
		
			$application = new applicationModel();
			$application->update("aid = ".parent::get("aid"), $template_info);
			
		}
		header("Location: ?c=main&a=create&step=add_feature&aid=".parent::get("aid"));
		//echo '<script>history.go(-1);</script>';
		exit;
	}
	
	
	
	/**
	 * 保存所選擇的template
	*/
	public function getAppAppearance($view){
		if(parent::has("aid")){
			$application = new applicationModel();
			$application_info = $application->get_data("aid = ".parent::get("aid"));
			$view->addDisplayVar("application_info", $application_info);
			return $view;
		}
		exit;
	}
	
	
	
	/**
	 * customize_appearance頁面更改顏色后保存
	*/
	public function saveAppAppearance(){
		if(parent::has("aid")){
			$data = array(
				"app_background_color"=>parent::get("app_background_color"),
				"app_background_image"=>parent::get("app_background_image"),
				"app_text_color"=>parent::get("app_text_color"),
				"navbar_background_color"=>parent::get("navbar_background_color"),
				"navbar_text_color"=>parent::get("navbar_text_color"),
				"topbar_background_color"=>parent::get("topbar_background_color"),
				"topbar_text_color"=>parent::get("topbar_text_color"),
				"icon_type"=>parent::get("icon_type"),
			);
			$application = new applicationModel();
			$application->update("aid = ".parent::get("aid"), $data);
		}
		echo '<script>history.go(-1);</script>';
		exit;
	}
}
?>
