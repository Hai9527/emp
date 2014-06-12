<?php
/**
 * Description of notification.model.php
 * @author Animax
 */
class notificationModel extends empModel {
    public function __construct() {
        parent::__construct('notification');
		$this->dataMatchConfig["nid"] = 'notification_id';
		$this->dataMatchConfig["nsubject"] = 'notification_title';
		$this->dataMatchConfig["ncreate_time"] = 'notification_time';
		$this->dataMatchConfig["ncontent"] = 'notification_content';
		$this->dataMatchConfig["nstatus"] = 'notification_status';
		$this->dataMatchConfig["aid"] = 'app_id';
    }
	
	public function getAllnotification($tid, $nid){
		$where = "1";
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($nid!='')?" and nid = $nid":"";
		return parent::list_data($where, null, null, null, "ncreate_time");
	}
	
	
	public function getNotificationByCondition(){
		
	}
	
}