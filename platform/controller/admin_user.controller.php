<?php
/**
 * Description of admin_user.controller.php
 * @author Animax
 */
class admin_user extends TFController {
	
		/**user list*/
		public function index(){
			
			$page = parent::has('page')? parent::get('page') : 1;
			$offset = 10;
		
			$view = global_function::adminPage('AdminHome', 1);
			
			$where = '1';
			$account = new accountModel();
			
			if(parent::has("acid")){
				$where .= " and acid = ".parent::get("acid");
			}
			
			if(parent::has("keyword")){
				$where .= " and (acname like '%".trim(parent::get("keyword"))."%' or acemail like '%".trim(parent::get("keyword"))."%')";
				$view->addDisplayVar("keyword", parent::get("keyword"));
			}
			
			if(parent::has("actype")){
				$where .= " and actype = ".parent::get("actype");
				$view->addDisplayVar("actype", parent::get("actype"));
			}
			
			/** ·­Ò³page start */
				$count = $account->count($where);
				$show_page = Pages2::show_page($count,$page,$offset);
			
			/** ·­Ò³page end */
				$where .= ' Limit '.($page-1)*$offset.', '.$offset;
				$userlist = $account->list_data($where);
				
				$view->addDisplayVar("userlist", $userlist);
				
				$view->addDisplayVar("show_page", $show_page);
			return $view;
		}
			
			
		/**edit user*/
		public function edit_user(){
			$view = global_function::adminPage('edit_user', 1);
			
			$where='';
			
			$action = '?c=admin_user&a=update_user';
			$title = 'Add User';
			
			if(parent::has("acid")){
					$action .= '&acid='.parent::get("acid");
					$title = 'Edit User';
					/** get this user info*/
					$where .= "acid = ".parent::get("acid");
					$edit_user = new accountModel();
					$user_info = $edit_user->get_data($where);
					
					$view->addDisplayVar("user_info", $user_info);
					
					/** get app info */
					$application = new applicationModel();
					$user_app = $application->list_data("acid = ".parent::get("acid"));
					
					$view->addDisplayVar("user_app", $user_app);
					
					$view->addDisplayVar("acid", parent::get("acid"));
			}
			$view->addDisplayVar("action", $action);
			$view->addDisplayVar("title", $title);
			
			return $view;
		}
			
		/***Do ±à¼­user ***/	
		public function update_user(){
			$account = new accountModel();
			
			$passwd = trim(parent::get('acpasswd'));
			$old_passwd = trim(parent::get('old_passwd'));
			/*var_dump($old_passwd);
			die();*/
			$data = array();
			if($passwd != ''){
				if(parent::has('acfname')) $data['acfname'] = parent::get('acfname');
				if(parent::has('aclname')) $data['aclname'] = parent::get('aclname');
				if(parent::has('acname')) $data['acname'] = parent::get('acname');
				if(parent::has('actype')) $data['actype'] = parent::get('actype');
				if(parent::has('acemail')) $data['acemail'] = parent::get('acemail');
				if(parent::has('acpasswd')) $data['acpasswd'] = trim(parent::get('acpasswd'));
				if(parent::has('acstatus')) $data['acstatus'] = parent::get('acstatus');
			}else{
				if(parent::has('acfname')) $data['acfname'] = parent::get('acfname');
				if(parent::has('aclname')) $data['aclname'] = parent::get('aclname');
				if(parent::has('acname')) $data['acname'] = parent::get('acname');
				if(parent::has('actype')) $data['actype'] = parent::get('actype');
				if(parent::has('acemail')) $data['acemail'] = parent::get('acemail');
				if(parent::has('acpasswd')) $data['acpasswd'] = trim(parent::get('old_passwd'));
				if(parent::has('acstatus')) $data['acstatus'] = parent::get('acstatus');
			}
			if(parent::has('acid')){	
				$account->update("acid = ".parent::get('acid'), $data);
			}else{
				$account->create($data);
			}
			/*header("Location: ?c=admin_user");*/
			/*if($old_passwd != ''){
				echo"<script>history.go(-1);</script>";
			}else{
				
			}*/
			echo"<script>history.go(-2);</script>";
		}

		/***ÐÞ¸Äuser×´Ì¬***/
		public function change_status(){
			if(parent::has("acid")){
				$account = new accountModel();
				$acstatus = parent::get("acstatus");
				
				$status = ($acstatus == 0) ? 1 : 0;
				
				$account->update("acid = ".parent::get('acid'), array("acstatus"=>$status));
				
				echo $status;
				
			}
			exit;
		}
		
		/***É¾³ýuser***/
		public function deleteUser(){
	
			$user = new accountModel();
			if(parent::has("acid")){
			
				$user->remove("acid = ". parent::get("acid"));
				echo '<script>history.go(-1);</script>';
				exit;
			}
		}
}
?>