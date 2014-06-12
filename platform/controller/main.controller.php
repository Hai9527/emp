<?php
/**
 * This is the main controller
 *
 * @author Animax
 */
class main extends TFController {
	/**
	 * platform首頁
	*/
    public function index(){
	
		$page = parent::has('page')? parent::get('page') : 1;
 		$offset = 10;
		$link = '?c=main';
        $view = global_function::adminPage('index');

		/** 读取application的列表信息 */
		$where = '';
		$db = new applicationModel();

			if($_SESSION['uid']){
				$where .= "acid = ".$_SESSION['uid'];
			}
		
		/** page start **/
		$count = $db->count($where);
		$show_page = Pages::show_page($count,$page,$link,$offset);
		/** page end **/
		$where .= ' Limit '.($page-1)*$offset.', '.$offset;
		$list = $db->list_data($where);
		$view->addDisplayVar("list", $list);
		$view->addDisplayVar("show_page", $show_page);

        return $view;
    }

	
	/**
	 * App創建
	*/
	public function create(){
		if(parent::has('step')){
			$step = parent::get('step');
			
			/** 判读是否有aid, 如果没有，跳到创建application的页面 **/
			if(!parent::has("aid") && $step != 'basic_info') header("Location: ?c=main&a=create&step=basic_info");
			
			switch($step){
				case 'basic_info': $view = $this->basic_info();  break;
				case 'select_template': $view = $this->select_template();  break;
				case 'add_feature': $view = $this->add_feature();  break;
				case 'customize_feature': $view = $this->customize_feature();  break;
				case 'customize_appearance': $view = $this->customize_appearance();  break;
				case 'customize_preview': $view = $this->customize_preview();  break;
				case 'customize_publish': $view = $this->customize_publish();  break;
				case 'complete': $view = $this->complete();  break;
				default: header("Location: index.php");
			}

			$view->addDisplayVar("step", str_replace("_", " ", $step));
			$view->addDisplayVar("aid", parent::get("aid"));
			
			return $view;
		}else{
			header("Location: index.php");
		}
	}

	
	/**
	 * Basic_info頁面(add&edit)
	*/
	public function basic_info(){
		if(global_function::getStatus()==3){ 
				$stay = 'approved';
			}else{
				$stay = 'draft';
			}
		$view = global_function::adminPage('basic_info');
		$view->addDisplayVar("stay", $stay); 
		
		if(parent::has('do')){
				$db = new applicationModel();
				$data = array(
					"aname"=>addslashes(parent::get("aname")),
					"aicon"=>parent::get("aicon"),
					"aload_image"=>parent::get("aload_image"),
					"adescription"=>addslashes(parent::get("adescription")),
					"weibo_name"=>addslashes(parent::get("weibo_name")),
					"weibo_key"=>parent::get("weibo_key"),
					"weibo_secret"=>parent::get("weibo_secret"),
					"acreate_time"=>date("Y-m-d H:i:s"),
					"acid"=>$_SESSION['uid']
			   );
			   if(parent::has("aid")){
				   if(parent::get('do') == 'edit'){
						$app_info = $db->get_data("aid = ".parent::get("aid"));
						$view->addDisplayVar("app_info", $app_info);
						$view->addDisplayVar("action", "?c=main&a=create&step=basic_info&aid=".parent::get("aid")."&do=save");
					}else{
						$app_updateId = $db->update("aid = ".parent::get("aid"), $data);
						header("Location: ?c=main&a=create&step=basic_info&aid=".parent::get("aid")."&do=edit");
					}
			   }else{
					$name = parent::get("aname");
					if($name != ''){
						$template = new templateModel();
						$default_template = $template->get_data();
						unset($default_template['template']);
						/** 創建app **/
						$data["akey"] = base64_encode(substr(md5(time()),1, -1));
						$result = array_merge($default_template,$data);
						//TF_dump($result);exit;
						$app_insertId = $db->create($result);
						
						/** 創建app的同時插入默認的home_tab **/
						if($app_insertId){
							$function = new functionModel(); 
							$ticon = $function->getFunctionIcon(1);
							$tab = new tabModel();
							$tab->create(array("tname"=>"home","fnid"=>1,"tstatus"=>1,"ticon"=>$ticon,"aid"=>$app_insertId,"tdefault"=>-1));
							}
						header("Location: ?c=main&a=create&step=basic_info&aid=".$app_insertId."&do=edit");
					}
				}
			}else{
				$view->addDisplayVar("action", "?c=main&a=create&step=basic_info&do=save");
			}
			return $view;
	
	}

	/*public function step_note(){

		$view = global_function::adminPage('step_note');

		switch(parent::get("step")){
			case 'basic_info': $back = "?c=main&a=create&step=basic_info&aid=".parent::get("aid")."&do=edit";
							   $next = "?c=main&a=create&step=select_template&aid=".parent::get("aid");
							   $message = "The app information has been saved successfully. Next, you will be directed to Step 2: Select Templates.";
							   break;
			case 'select_template' :  $back = "?c=main&a=create&step=select_template&aid=".parent::get("aid");
									  $next = "?c=main&a=create&step=add_feature&aid=".parent::get("aid");
									  $message = "The template selected has been saved successfully. Next, you will be directed to Step 3: Add Feature.";
								break;
			case 'customize_appearance' :  $back = "?c=main&a=create&step=customize_appearance&aid=".parent::get("aid")."&do=edit";
										   $next = "?c=main&a=create&step=customize_publish&aid=".parent::get("aid");
										   $message = "The app appearance customization has been saved successfully. Next, you will be directed to Step 6: Publish.";
								break;
			default:break;
		}
		$view->addDisplayVar("back", $back);
		$view->addDisplayVar("next", $next);
		$view->addDisplayVar("message", $message);

		return $view;
	}*/
	
	
	/**
	 * Aadd_feature頁面
	*/
	public function add_feature(){
		//$view = new TFView('add_feature.html');
        $view = global_function::adminPage('add_feature');	
		/** get function info */
		$function = new functionModel();
		
		$function_list = $function->list_data();
	   
		$temp = array();
		
		$offset = ceil(count($function_list)/2);
		$i=0;
		foreach($function_list as $key=>$value){
			$temp[$i][] = $value;
			if(($key+1)%$offset==0) $i++;
		}
		$function_list = $temp;	   
		
		/** get tab list*/
		$app_tab = new app_tabModel();
		$app_tab_list = $app_tab->list_data("aid = ".parent::get("aid"), null, null, null, 'torder','asc');
		
	   $view->addDisplayVar("function_list", $function_list);
	   $view->addDisplayVar("app_tab_list", $app_tab_list);
	   return $view;
	}

	
	
	/**
	 * customize_feature頁面
	*/
	public function customize_feature(){
        $view = global_function::adminPage('customize_feature');
	   
	   /** get application information **/
		$app_tab = new applicationModel();
		$app_info = $app_tab->get_data("aid = ".parent::get("aid"));
		$app_tab = new app_tabModel();
	   

	   /** 如果有tid则，获取对应的功能（music,video,news等) **/

		$app_tab = new app_tabModel();
		if(parent::has("tid")){
				$tid = parent::get("tid");
				$app_tab_info = $app_tab->get_data("tid = ".$tid);
			
				/** 通过tid查出来的数据找出对应的功能。并且获取其功能的信息列表和添加与编辑的路径 **/
				switch($app_tab_info['fnname']){				
					case 'cast': $view = cast::getCastList($view); break;
					case 'event': $view = event::getEventList($view); break;
					case 'form': $view = form::getFormList($view); break;
					case 'gallery': $view = gallery::getGalleryList($view); break;
					case 'home': $view = home::getHomeList($view); break;
					case 'music': $view = music::getMusicList($view); break;
					case 'notification': notification::getNotificationList($view); break;
					case 'page': $view = page::getPageList($view); break;
					case 'video': $view = video::getVideoList($view); break;
					case 'article': $view = article::getArticleList($view); break;		
					
				}
				$view->addDisplayVar("tid", $tid);
			}else{
				$view = page::getHomeinfo($view);
		}
	   
		/** get tab list **/
		$app_tab_list = $app_tab->list_data("aid = ".parent::get("aid"), null, null, null, 'torder','asc');
		
		$view->addDisplayVar("app_tab_list", $app_tab_list);
		$view->addDisplayVar("app_info", $app_info);
		$view->addDisplayVar("app_tab_info", $app_tab_info);
		
		return $view;
	}

	
	/**
	 * customize_appearance頁面
	*/
	public function customize_appearance(){
		//$view = new TFView('customize_appearance.html');
        $view = global_function::adminPage('customize_appearance');
		template::getAppAppearance($view);
		
		/** getTab list */
		$tab = new tabModel();
		$tab_list = $tab->list_data("aid = ".parent::get("aid"), null, 0, 5, 'torder','asc');
		$view->addDisplayVar("tab_list", $tab_list);
		
		return $view;
	}

	
	
	/**
	 * customize_preview頁面
	*/
	public function customize_preview(){
       //$view = new TFView('customize_preview.html');
        $view = global_function::adminPage('customize_preview');
		
		$application = new applicationModel();
		$application_info = $application->get_data("aid = ".parent::get("aid"));
		$view->addDisplayVar("akey", $application_info['akey']);
		
		return $view;
	}

	
	
	
	/**
	 * customize_publish頁面
	*/
	public function customize_publish(){
       //$view = new TFView('customize_publish.html');
        $view = global_function::adminPage('customize_publish');
		
		/***get tab if content***/
		$table = new app_tabModel();
		$where = '1';
		if(parent::has("aid")){
				$where .= " and aid = ".parent::get("aid");
				$where .= " and tstatus = 1";
			}
		$tab_content = $table->list_data($where);

		$tab_not_content = array();
		$noneNull = 0;
		foreach ($tab_content as $key=>$value){
			$datacount = new TFModel($value['table_name']);
			$datacount_content = $datacount->count('tid =' . $value['tid']);
			if ($datacount_content == 0 ){
				$noneNull = 1;
				array_push($tab_not_content,$value);
			}
		}
		
		$application = new applicationModel();
		$application_info = $application->get_data("aid = ".parent::get("aid"));
		//var_dump($application_info);exit;
		$view->addDisplayVar("application_info", $application_info);
		$view->addDisplayVar("tab_content", $tab_not_content);
		$view->addDisplayVar("noneNull", $noneNull);
		return $view;
	}
	
	
	
	
	/**
	 * 提交信息的處理
	*/
	public function complete(){
       //$view = new TFView('complete.html');
        $view = global_function::adminPage('complete');
		
		/** 用户提交记录用户提交时间 */
		$application = new applicationModel();
		//$application_info = $application->get_data("aid= ".parent::get("aid"));
		if(parent::get("answer") == 1){
			if(global_function::getStatus() == 0){
				$data = array("asumbit_time"=>date("Y-m-d H:i:s",time()), "astatus" => 1);
				$application->update("aid= ".parent::get("aid"), $data);
			}
			
			
			/***creator summited  send email**/
			//if(!global_function::isSuperAdmin() && global_function::getStatus() == 1){
			if(global_function::getStatus() == 1){
				$account_app = new app_accountModel();
				$account_app_info = $account_app->get_data("aid = ".parent::get('aid'));
				$mailSubject = ' An New App is waiting for your submission.';
				$mailContent = file_get_contents("email_template/summit.html");
				$mailBody = str_replace(array("{Username}","{App Name}","{ID}","{UserID}"), array($account_app_info['acname'], $account_app_info['aname'],$account_app_info['aid'],$account_app_info['acid']), $mailContent);
				
				/** get super admin information */
				$accout = new accountModel();
				$super_info = $accout->list_data("is_super = 1");
				$send_arr = array();
				foreach($super_info as $key=>$value){
					$send_arr[$value['acemail']] = $value['acname'];
				}
				PhpEmailerManager::sendMail('preview@fmldigital.com', 'emp', $send_arr, $mailSubject, $mailBody);
			}
				
		}
		$view->addDisplayVar("astatus", $application_info['astatus']);
		//echo $application_info['astatus'];exit;
		return $view;
	}
	
	
	
	
	
	/**
	 * Select_template頁面
	*/
	public function select_template(){
		$view = global_function::adminPage('select_template');
		$template_image = new template_imageModel();
		$music_image = $template_image->list_data("temid = 1"); 
		$event_image = $template_image->list_data("temid = 2"); 
		$movie_image = $template_image->list_data("temid = 3");
		/** 获取当前app的模版（Templates）类型。用（temid：1，2，3）表示 */
		template::getAppTemid($view);
		$view->addDisplayVar("music_image", $music_image);
		$view->addDisplayVar("event_image", $event_image);
		$view->addDisplayVar("movie_image", $movie_image);
		return $view;
	}

}
?>
