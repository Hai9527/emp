<?php
/**
 * This is the event controller
 *
 * @author Animax
 */
class event extends TFController {
    public function index(){
		
    }
	

	/**
	 *   獲取event_list
	*/
	public function getEventList($view){
				$page = parent::has('page')? parent::get('page') : 1;
				$offset = 10;
				$where = "";
				
				if(parent::has("tid")){
					$where .= "tid = ".parent::get("tid");
				}
				$link = '?c=main&a=create&step=customize_feature&aid='.parent::get("aid").'&tid='.parent::get("tid");

				$event = new eventModel();
				
				$count = $event->count($where);
				
				$show_page = Pages::show_page($count,$page,$link,$offset);
				
				$where .= ' Limit '.($page-1)*$offset.', '.$offset;
				
				$event_list = $event->list_data($where);
				//var_dump($event_list);exit;

				$view->addDisplayVar("event_list", $event_list);
				
				$view->addDisplayVar("show_page", $show_page);

				return $view;
	}	
	

	
	
	
	/**
	 *   Event add&edit
	*/

	public function editEvent(){
	
        //$view = new TFView('tab_feature_edit.html');
        $view = global_function::adminPage('tab_feature_edit');
		
		$view->addDisplayVar("edit_page", 'tab_edit_event');
		
		$action = "?c=event&a=saveEvent&tid=".parent::get("tid");
		
		$title = "Add New";
		
		$event = new eventModel();
		
		if(parent::has("eid")){
		
			$title = "Edit";
			
			$action = "?c=event&a=saveEvent&eid=".parent::get("eid");
		
			$where .= "eid = ".parent::get("eid");
			
			$event_info = $event->get_data($where);
			
			$event_info['etitle'] = htmlspecialchars($event_info['etitle']);
			
			$view->addDisplayVar("event_info", $event_info);	
			
		}
		
		$view->addDisplayVar("title", $title);	
		
		$view->addDisplayVar("action", $action);
		
		return $view;
		
	}
	

	
	
	
	/**
	 *   event add&edit后save
	*/

	public function saveEvent(){
	
				$etitle = parent::has("etitle")? parent::get("etitle") : '';
				$estart_time = parent::has("estart_time")? parent::get("estart_time") : '';
				$eend_time = parent::has("eend_time")? parent::get("eend_time") : '';
				$elocation = parent::has("elocation")? parent::get("elocation") : '';
				$edescription = parent::has("edescription")? parent::get("edescription") : '';
				$estatus = parent::has("estatus")? parent::get("estatus") : '';
				$eholster = parent::has("eholster")? parent::get("eholster") : '';
				$ephone = parent::has("ephone")? parent::get("ephone") : '';
				$email = parent::has("email")? parent::get("email") : '';
				$eimage = parent::has("eimage")? parent::get("eimage") : '';
				/*$egaree = parent::has("egaree")? parent::get("egaree") : 0;
				$eunagree = parent::has("eunagree")? parent::get("eunagree") : 0;
				$emaybe = parent::has("emaybe")? parent::get("emaybe") : 0;*/
				
				$data = array(
							"etitle" => addslashes($etitle),
							"estart_time" => $estart_time,
							"eend_time" => $eend_time,
							"elocation" => addslashes($elocation),
							"edescription" => $edescription,
							"estatus" => $estatus,
							"eholster" => addslashes($eholster),
							"ephone" => $ephone,
							"email" => $email,
							"eimage" => $eimage
							/*"egaree" => $egaree,
							"eunagree" => $eunagree,
							"emaybe" => $emaybe*/
						);
				//var_dump($data);exit;
				$event = new eventModel();
				
				if(parent::has("eid")){
				
					$event->update("eid = ". parent::get("eid"), $data);
					
				}else if(parent::has("tid")){
				
					$data["tid"] = parent::get("tid");
					
					$insert_id = $event->create($data);
				
				}
				
				echo '<script>self.parent.history.go(0); </script>';
				exit;
			
		}
	
	

	
	
	/**
	 *   delete event單一記錄.
	*/

	public function deleteEvent(){
	
		$event = new eventModel();
		if(parent::has("eid")){
		
			$event->remove("eid = ". parent::get("eid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}
}
?>
