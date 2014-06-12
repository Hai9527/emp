<?php
/**
 * This is the feature controller
 *
 * @author Animax
 */
class feature extends TFController {
    public function index(){
		
    }

	
	/**
	 *   Add Feature Page popup
	*/
	public function popup(){
	
		//$view = new TFView('add_feature_box.html');
        $view = global_function::adminPage('add_feature_box');
	
		if(parent::has('fnid')){
		
			$action = "?c=feature&a=dopopup&fnid=".parent::get('fnid'); 	
			
			/** get Function Name */
			$function = new functionModel();
			$title = $function->getFunctionTitle(parent::get('fnid'));
			
			/** get All default icon*/
			$icon_list = $this->getDefaultIcon();
			
			$view->addDisplayVar("aid", parent::get("aid"));
			$view->addDisplayVar("action", $action);
			$view->addDisplayVar("title", $title);
			$view->addDisplayVar("icon_list", $icon_list);
			
			return $view;
			
		}else{
		
			header("Location: index.php");
			
		}
	}
	

	
	/**
	 *   Do 添加Feature 
	*/
	public function dopopup(){
				if(parent::has('fnid')){
				
					$fnid = parent::get("fnid");			
					$aid = parent::get("aid");						
					$tname = parent::has("tname")? parent::get("tname") : "";			
					$icontype = parent::has("icontype")? parent::get("icontype") : "0";			
					$color = parent::has("color")? parent::get("color") : "1";			
					$tstatus = parent::has("tstatus")? parent::get("tstatus") : "1";
					$ticon = parent::has("ticon")? parent::get("ticon") : "1";
					$uploadicon = parent::has("uploadicon")? parent::get("uploadicon") : "";
					
					$data = array(
								"fnid"   => $fnid,
								"aid"   => $aid,
								"tname"   => htmlspecialchars($tname),
								"tstatus"   => $tstatus,
								"ticon_type"   => $icontype,
								"ticon_color"   => $color
							);
							
					switch($icontype){
						case 1:  $data['ticon'] = $ticon; break;
						case 2:  $data['ticon'] = $uploadicon; break;
						default: $function = new functionModel(); $data['ticon'] = $function->getFunctionIcon(parent::get('fnid'));
					}
					
					//echo $data['ticon'];exit;
					
					$tab = new tabModel();
					$tab_id = $tab->create($data);
					
					/** 插入form字段信息*/
					
					$function = new functionModel();
					$function_info = $function->get_data("fnid = ".$fnid);
					if($function_info['fnname'] == 'form') global_function::createFormField($tab_id);
					$view = global_function::adminPage('feature_note');
					
					return $view;
					
				}else{
					header("Location: ?c=main&a=create&step=add_feature&aid="+$aid);
				}
	}
	
	
	
	
	/**
	 *   edit Feature
	*/

	public function popupedit(){
	
		//$view = new TFView('add_feature_box.html');
        $view = global_function::adminPage('add_feature_box');
		
		if(parent::has('tid')){
				$tab = new tabModel();	
				$tab_extand = $tab->get_data("tid = ".parent::get('tid'));
			
				$action = "?c=feature&a=popupupdate&&tid=".parent::get('tid'); 
				
				/** get All default icon*/
				$icon_list = $this->getDefaultIcon();
				
				/** get tab information */			
				$tab = new app_tabModel();
				$tab_info = $tab->get_data("tid = ".parent::get('tid'));
				
				//var_dump($tab_info);exit;
				
				$title = $tab_info['fntitle'];
				
				$view->addDisplayVar("icon_list", $icon_list);
				$view->addDisplayVar("tab_info", $tab_info);
				$view->addDisplayVar("title", $title);
				$view->addDisplayVar("action", $action);

				return $view;
			
		}else{			
			header("Location: ?c=main&a=create&step=add_feature&aid=".$tab_extand['aid']);
			
		}
	}
	

	
	
	
	
	/**
	 *   update popup(更新新edit的feature)
	*/
	public function popupupdate(){
				if(parent::has('tid')){
					$tab = new tabModel();	
					$tab_extand = $tab->get_data("tid = ".parent::get('tid'));
				
					$tid = parent::get("tid");												
					$tname = parent::has("tname")? parent::get("tname") : "";			
					$icontype = parent::has("icontype")? parent::get("icontype") : "0";			
					$color = parent::has("color")? parent::get("color") : "1";			
					$tstatus = parent::has("tstatus")? parent::get("tstatus") : "1";
					$ticon = parent::has("ticon")? parent::get("ticon") : "1";
					$uploadicon = parent::has("uploadicon")? parent::get("uploadicon") : "";
					
					$data = array(
								"tname"   => htmlspecialchars_decode($tname),
								"tstatus"   => $tstatus,
								"ticon_type"   => $icontype,
								"ticon_color"   => $color
							);
							
					switch($icontype){
						case 1:  $data['ticon'] = $ticon; break;
						case 2:  $data['ticon'] = $uploadicon; break;
						default: $function = new functionModel(); $data['ticon'] = $function->getFunctionIcon($tab_extand['fnid']);
					}
					
					$tab = new tabModel();
					$tab_id = $tab->update("tid = ".$tid, $data);
					
					$view = global_function::adminPage('feature_note');
					
					return $view;
			
		}
	
	}
	

	
	
	
	/**
	 *   獲取所有icon(黑白灰三套)
	*/
	public function getDefaultIcon($itype=1){
				/** get All default icon*/
				$icon = new iconModel();
				
				$icon_list = $icon->list_data("itype = ".$itype);	
				$temp = array();
				
				$i=0;
				foreach($icon_list as $key=>$value){
					$temp[$i][] = $value;
					if(($key+1)%5==0) $i++;
				}
				return $temp;
			}
}
?>
