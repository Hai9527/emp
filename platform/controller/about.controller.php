<?php
/**
 * This is the about controller
 *
 * @author Animax
 */
class about extends TFController {
    public function index(){

    }

	public function getAbout($view){
		$about = new homepageModel();
		$where = "";
		if(parent::has("tid")){
			$where .= "tid = ".parent::get("tid");
		}
		$about_list = $about->list_data($where);

		$view->addDisplayVar("about_list", $about_list);

		return $view;
	}


}
?>