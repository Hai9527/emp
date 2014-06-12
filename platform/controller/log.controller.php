<?php
/**
 * Description of log.controller.php
 * @author Animax
 */
class log extends TFController {
	public function index(){
	
	}
	
	
	/**
	 *   Login
	*/
	public function login(){
		if(isset($_SESSION['username']) && $_SESSION['username'] != ''){
			if($_SESSION['utype'] == 1) header("Location: ?c=admin");
			else header("Location: ?c=main");
		}
		$view = new TFView('login.html');
		
        return $view;
	}
	

	
	
	/**
	 *   login的驗證(登录成功则记入session)
	*/

	public function dologin(){
	
		if(parent::has("acname") && parent::has("acpasswd")){
			
			$account = new accountModel();
		
			$acname = parent::get("acname");
			
			$acpasswd = parent::get("acpasswd");
			
			$where = " binary acname = '".$acname."' and binary acpasswd = '".$acpasswd."'";
			
			$account_info = $account->get_data($where);
			
			if(is_array($account_info) && count($account_info)>0){
			
				$_SESSION['username'] = $account_info['acname'];	
				
				$_SESSION['utype'] = $account_info['actype'];			
				
				$_SESSION['uid'] = $account_info['acid'];				
			
			}
			
			if($_SESSION['utype'] == 1) header("Location: ?c=admin");
			else header("Location: ?c=main");
		}
			
	}
	
	
	/**
	 *   去到忘記密碼的頁面
	*/
	public function forgotpassword(){
		$view = new TFView('forgotpassword.html');
		
        return $view;
	}
	
	
	
	/**
	 *   忘记密码进行Email地址确认，并且把用户名密码以邮件方式发送邮件给客户
	*/

	public function doforgotpassword(){
		//var_dump($this->checkaccount());
		if($this->checkaccount()){
			if(parent::has("acemail")){
				$account = new accountModel();
				$account_info = $account->get_data("acemail = '".parent::get("acemail")."'");	
				$mailSubject = 'Your New Password';
				
				$mailContent = file_get_contents("email_template/forgotpassword.html");
				$mailBody = str_replace(array("{username}","{password}"), array($account_info['acname'], $account_info['acpasswd']), $mailContent);

				PhpEmailerManager::sendMail('preview@fmldigital.com', 'emp', array(parent::get("acemail")=>$account_info['acname']) , $mailSubject, $mailBody);	
			}
			header("Location: ?c=log&a=login");
		}else{
			echo '<script>history.go(-1)</script>';
		}
		exit;
	}
	
	
	
	/**
	 *   判斷是否存在這個account和email
	*/
	public function checkaccount(){
		$result = 0;
		if(parent::has("acemail")){
			$account = new accountModel();
			$account_count = $account->count("acemail = '".parent::get("acemail")."'");	
			$result = ($account_count>0)? 1 : 0;
		}else if(parent::has("acname")){
			$account = new accountModel();
			$account_count = $account->count("acname = '".parent::get("acname")."'");	
			$result = ($account_count>0)? 1 : 0;
		}
		if(parent::get("ajax") == 1) {echo $result; exit;}
		return $result;
	}
	
	
	
	
	
	/**
	 *   退出登錄
	*/
	public function logout(){
		
		if(isset($_SESSION['username']) && $_SESSION['username'] != ''){
			session_unregister("username");
			session_unregister("utype");
			session_destroy();
		}
		header("location:?c=log&a=login");

	}
	
	
	
	/**
	 *  驗證碼
	*/
	public function checkVerificationCode(){
		if(parent::has('yzm')){
			if($_SESSION['yzm'] == parent::get('yzm')){
				echo 1;
			}
		}
		exit;
	}
	
	
	
	/**
	 *   check password
	*/
	public function checkPasswd(){
		$result = 0;
		if(parent::has("acname") && parent::has("acpasswd")){
			$account = new accountModel();
			$account_count = $account->count(" binary acname = '".parent::get("acname")."' and binary acpasswd = '".parent::get("acpasswd")."'");	
			if($account_count>0){
				$result = 1;
				$account_info = $account->get_data(" binary acname = '".parent::get("acname")."' and binary acpasswd = '".parent::get("acpasswd")."'");	
				if($account_info['acstatus'] == 0) $result = 2;
			}
		}
		echo $result;exit;
	}
	
	
	
	/**
	 *   驗證碼圖片
	*/
	public function getImg(){
		global_function::getVerificationCode();
		exit;
	}
}