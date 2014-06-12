<?php
/**
 * Description of admin.controller.php
 * @author Animax
 */
class admin_app extends TFController {

	public function index(){
			
		$page = parent::has('page')? parent::get('page') : 1;
		$offset = 10;
		
		//$view = new TFView('admin/app_list.html');
		$view = global_function::adminPage('app_list', 1);
		
		$where = '1';
		$application = new app_accountModel();
		
		if(parent::has("aid")){
			$where .= " and aid = ".parent::get("aid");
		}
		
		if(parent::has("keyword")){
			$where .= " and (aname like '%".parent::get("keyword")."%' or acname like '%".parent::get("keyword")."%')";
			$view->addDisplayVar("keyword", parent::get("keyword"));
		}
		
		if(parent::has("astatus")){
			$where .= " and astatus = ".parent::get("astatus");
			$view->addDisplayVar("astatus", parent::get("astatus"));
		}
	/** 翻页page start */
		$count = $application->count($where);
		$show_page = Pages2::show_page($count,$page,$offset);
	/** 翻页page end */
		
		$where .= ' order by';
		if(parent::has("ord")) $where .= ' ord desc,';
		$where .= ' acreate_time desc Limit '.($page-1)*$offset.', '.$offset;
		$applist = $application->list_data($where);
		//TF_dump($applist);exit;
		
		//TF_dump($applist);
		$view->addDisplayVar("applist", $applist);
		
		$view->addDisplayVar("show_page", $show_page);

		return $view;
	}
			
	public function edit_app(){
	
		//$view = new TFView('admin/edit_app.html');
		$view = global_function::adminPage('edit_app', 1);
		
		$where='';
		
		if(parent::has("aid")){
			$where .= "aid = ".parent::get("aid");
			
			/** get current app infor*/
			//$edit_app = new applicationModel();
			$edit_app = new app_accountModel();
			$app_info = $edit_app->get_data($where);
			$view->addDisplayVar("app_info", $app_info);
			
			/** get current tab of app list */
			$app_tab = new app_tabModel();
			$app_tab_list = $app_tab->list_data($where);
			$view->addDisplayVar("app_tab_list", $app_tab_list);
			
			
			/** get note information */
			$note_account = new note_accountModel();
			
			$note_list = $note_account->list_data("aid = ".parent::get("aid"));
			
			$view->addDisplayVar("note_list", $note_list);
			
			$view->addDisplayVar("aid", parent::get("aid"));
		}
			
		return $view;
	}

	public function update(){
	
		$status = array("-1"=>"Disapproved","0"=>"Draft","1"=>"Submittd","2"=>"Processing","3"=>"Approved");
		$data = array();
		$note = array();
	
		if(parent::has('aid')){		
			/** data get data*/
			if(parent::has('astatus')) $data['astatus'] = parent::get('astatus');
			$data['aonline_link'] = parent::get('aonline_link');
			if(parent::get('astatus') == -1 || parent::get('astatus') == 3) $data['aapproved_time'] = date("Y-m-d H:i:s");
			//if(parent::get('astatus') == 3) $data['aapproved_time'] = date("Y-m-d H:i:s");

			/** note get data*/
			$note['aid'] = parent::get('aid');
			$note['acid'] = $_SESSION['uid'];
			$note['annote'] = '';
			if(parent::has('anmessage')) $note['anmessage'] = parent::get('anmessage');
			if(parent::get('fastatus')!=parent::get('astatus')){
				$note['annote'] .= $status[parent::get('fastatus')];
				$note['annote'] .= "=>".$status[parent::get('astatus')];
				$note['annote'] .= "; ";
			}
			
			//var_dump(parent::get('aonline_link'));exit;
			/*if(!parent::has('fonlinelink')){
				if(parent::get('fonlinelink') != parent::get('aonline_link')){
					$note['annote'] .= "Add Online Link:".parent::get('aonline_link').";";
				}
			}else{
				if(!parent::has('aonline_link')){
					$note['annote'] .= "Delete Online Link.";"";
				}else if(parent::get('fonlinelink') != parent::get('aonline_link')){
					$note['annote'] .= "Edit Online Link:".parent::get('aonline_link').";";
				}
			}*/
			
			
			/* if(parent::get('fonlinelink')==''){
				$note['annote'] .= "Add Online Link:".parent::get('aonline_link');
				$note['anmessage'] .= "Online link:".parent::get('aonline_link');
			}else if(parent::get('fonlinelink')!=parent::get('aonlinelink')){
				$note['annote'] .= "Edit Online Link:".parent::get('aonline_link');
				$note['anmessage'] .= "Online link:".parent::get('aonline_link');
			} */
			
			
			
			
			$note['ancreate_time'] = date("Y-m-d H:i:s");
			
			
			$application = new applicationModel();
			$application->update("aid = ".parent::get('aid'), $data);
			
			
			$app_notes = new app_notesModel();
			$app_notes->create($note);
			//var_dump($app_notes);exit;
			
			
			/**send email */
			$app_account = new app_accountModel();
			$app_account_info = $app_account->get_data("aid = ".parent::get('aid'));
			switch(parent::get('astatus')){
				case -1: {
					$mailSubject = "Your App Is Disapproved by Android Market";
					$path = "email_template/disapproved.html"; break;
				}
				case 2: {
					$mailSubject = "Your App Is In Processing";
					$path = "email_template/processing.html"; break;
				}
				case 3: {
					$mailSubject = "Congratulation! Your App Has Been Approved by Android Market";
					$path = "email_template/approved.html"; break;
				}
			}
			
			$html = file_get_contents($path);
			
			/** mail的發送 **/
			$mailBody = str_replace(array("{username}","{app_name}","{app_link}","{reason}"), array($app_account_info['acname'],$app_account_info['aname'],$app_account_info['aonline_link'],$note['anmessage']), $html);
			
			PhpEmailerManager::sendMail('preview@fmldigital.com', 'emp', array($app_account_info['acemail']=>$app_account_info['acname']) , $mailSubject, $mailBody);
		}
		
		header("Location: ?c=admin_app");
		
	}
	
}
?>