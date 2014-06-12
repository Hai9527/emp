<?php
/**
 * Description of event.model.php
 * @author Animax
 */
class eventModel extends empModel {
    public function __construct() {
        parent::__construct('event');
		$this->dataMatchConfig["eid"] = 'event_id';
		$this->dataMatchConfig["etitle"] = 'event_title';
		$this->dataMatchConfig["estart_time"] = 'event_begintime';
		$this->dataMatchConfig["eend_time"] = 'event_endtime';
		$this->dataMatchConfig["edescription"] = 'event_content';
		$this->dataMatchConfig["estatus"] = 'event_status';
		$this->dataMatchConfig["eimage"] = 'event_image';
		$this->dataMatchConfig["tid"] = 'tab_id';
	
    }
	
	
	public function getAllEvents($tid,$eid,$type,$page,$number){
		//var_dump($type);exit;
		$where = "1";
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($eid!='')?" and eid = $eid":"";
		$where .= " and eend_time ".(($type=='upcoming')?">=":"<")." '".date("Y-m-d H:i:s")."'";
		return parent::list_data($where,null,$page, $number,"estart_time","DESC");
	}
	
	
	public function getEventCount($tid, $eid,$type){
			$where = "1";
			$where .= ($tid!='')?" and tid = $tid":"";
			$where .= ($eid!='')?" and eid = $eid":"";
			$where .= " and eend_time ".(($type=='upcoming')?">=":"<")." '".date("Y-m-d H:i:s")."'";
			//echo $where;
			return parent::count($where);
	}

	
}