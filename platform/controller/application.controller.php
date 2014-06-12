<?php
/**
 * This is the application controller
 *
 * @author Animax
 */
class application extends TFController {
    public function index(){
		
    }
	
	 /**
	 *   delete application
	*/
	public function delete(){
			if(parent::has("aid")){		
				$application = new applicationModel();
				$application->remove("aid = ".parent::get("aid"));
				echo '<script>history.go(-1);</script>';
			}
	}
	
	
	
	 /**
	 *   ±£´æloadddingimage
	*/
	public function saveLoaddingImage(){
			if(parent::has("aid")){		
				$application = new applicationModel();
				$application->update("aid = ".parent::get("aid"), array("aload_image"=>parent::get("aload_image")));
				echo '<script>history.go(-1);</script>';
			}
	}
	
	/*public function editApp(){
			if(parent::has('do')){
				$app_info = new applicationModel();
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
						$data["akey"] = base64_encode(substr(md5(time()),1, -1));
						$app_insertId = $db->create($data);
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
		}*/
?>
