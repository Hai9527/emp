<?php
/**
 * This is the notification controller
 *
 * @author Animax
 */
class notification extends TFController {
    public function index(){

    }


	/**
	 *获取Notification列表
	 ***/
	public function getNotificationList($view){
		$page = parent::has('page')? parent::get('page') : 1;
 		$offset = 10;
		$where = "";
		
		if(parent::has("tid")){
			$where .= "tid = ".parent::get("tid");
		}
		$link = '?c=main&a=create&step=customize_feature&aid='.parent::get("aid").'&tid='.parent::get("tid");
		
		$notification = new notificationModel();
		
		$count = $notification->count($where);
		
		$show_page = Pages::show_page($count,$page,$link,$offset);
		
		$where .= ' ORDER BY ncreate_time DESC';
		$where .= ' Limit '.($page-1)*$offset.', '.$offset;
		
		$notification_list = $notification->list_data($where);

		$view->addDisplayVar("notification_list", $notification_list);
		
		$view->addDisplayVar("show_page", $show_page);

		return $view;
	}


	/**
	 *形成Notification添加页或者编辑页
	 ***/
	public function editNotification(){

        //$view = new TFView('tab_feature_edit.html');
        $view = global_function::adminPage('tab_feature_edit');

		$view->addDisplayVar("edit_page", 'tab_edit_notification');

		$action = "?c=notification&a=saveNotification&tid=".parent::get("tid");

		$title = "Add New";

		$notification = new notificationModel();

		if(parent::has("nid")){


			$action = "?c=notification&a=saveNotification&nid=".parent::get("nid");

			$where .= "nid = ".parent::get("nid");

			$notification_info = $notification->get_data($where);

			$title = $notification_info['nsubject'];
			$view->addDisplayVar("notification_info", $notification_info);

		}
		$view->addDisplayVar("title", $title);

		$view->addDisplayVar("action", $action);

		return $view;

	}

	/**
	 *进行添加或者编辑
	 ***/
	public function saveNotification(){

		$nsubject = parent::has("nsubject")? parent::get("nsubject") : '';
		$ncontent = parent::has("ncontent")? parent::get("ncontent") : '';

		$data = array(
					"nsubject" => htmlspecialchars_decode($nsubject),
					"ncontent" => $ncontent
				);
		$notification = new notificationModel();

		if(parent::has("nid")){

			$notification->update("nid = ". parent::get("nid"), $data);

		}else if(parent::has("tid")){

			$data["tid"] = parent::get("tid");

			$data["ncreate_time"] = date("Y-m-d H:i:s"); 


			$insert_id = $notification->create($data);

		}

		echo '<script>self.parent.history.go(0); </script>';
		exit;

	}
	
	/**
	 *删除Notification
	 ***/
	public function deleteNotification(){

		$notification = new notificationModel();
		if(parent::has("nid")){

			$notification->remove("nid = ". parent::get("nid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}

}
?>
