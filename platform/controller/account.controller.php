<?php
/**
 * This is the main controller
 *
 * @author Animax
 */
class account extends TFController {
	
	
	 /**
	 *   獲取當前的account
	*/
    public function index(){
			$view = global_function::adminPage('my_account');
			$where = '';
			
			$account = new accountModel();

			if(isset($_SESSION['uid']) && $_SESSION['uid'] != ''){
				$where .= "acid = ".$_SESSION['uid'];
				$account_info = $account->get_data($where);
				$view->addDisplayVar("account_info", $account_info);
			}
			return $view;
    }
	
	
	
	 /**
	 *   更新當前account的帳戶信息
	*/
	public function updateAccount(){
		if(parent::has("acid")){
			$passw = parent::get("acpasswd");
			if($passw != ''){
				$data = array(
					"acfname" => parent::get("acfname"),
					"aclname" => parent::get("aclname"),
					"acname" => parent::get("acname"),
					"acemail" => parent::get("acemail"),
					"acpasswd" => parent::get("acpasswd")
				);
			}else{
				$data = array(
					"acfname" => parent::get("acfname"),
					"aclname" => parent::get("aclname"),
					"acname" => parent::get("acname"),
					"acemail" => parent::get("acemail"),
				);
			}
			/*var_dump();
			die();*/
			$account = new accountModel();
			$account->update("acid = ".parent::get("acid"), $data);
		}
		header("Location: ?c=main");
	}
	
	
	
	public function checkPasswd(){
		if(parent::has("acid") && parent::has("acpasswd")){
			$result = 0;
			$account = new accountModel();
			$account_info = $account->get_data("acid = ".parent::get("acid"));
			if($account_info['acpasswd'] == parent::get("acpasswd")) $result = 1;
		}
		echo $result;
		exit;
	}
	
	public function check_email(){
		if(parent::has("acemail")){
			$account = new accountModel();
			$account_info = $account->list_data("acemail = '".parent::get("acemail")."'");
			if(empty($account_info)){
				echo 't';
			}else{
				echo 'n';
			}
			exit;
		}else{
			header("Location: ?c=main");
		}
	}
}
?>