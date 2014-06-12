<?php
/**
 * Description of admin.controller.php
 * @author Animax
 */
class admin extends TFController {
	public function index(){
	
		header("Location: ?c=admin_user");
		
        /*$view = new TFView('admin/AdminHome.html');
		$view = global_function::adminPage('AdminHome', 1);
		
		$where = '';
		$account = new accountModel();
		
		if(parent::has("acid")){
			$where .= "acid = ".parent::get("acid");
		}
		$userlist = $account->list_data($where);
		$view->addDisplayVar("userlist", $userlist);*/

        //return $view;
	}
	
	
	
	
	
}