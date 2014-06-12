<?php
/**
 * Description of video.model.php
 * @author Animax
 */
class videoModel extends empModel {
    public function __construct() {
        parent::__construct('video');
		
		$this->dataMatchConfig["vid"] = 'video_id';
		$this->dataMatchConfig["vname"] = 'video_title';
		$this->dataMatchConfig["vicon"] = 'video_thumb';
		$this->dataMatchConfig["video"] = 'video_url';
		$this->dataMatchConfig["vtime"] = 'video_time';
		$this->dataMatchConfig["vcaption"] = 'video_caption';
		$this->dataMatchConfig["vstatus"] = 'video_status';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["comments_num"] = 'comment_counts';
		$this->dataMatchConfig["players_num"] = 'players_counts';
		$this->dataMatchConfig["likes_num"] = 'likes_counts';
    }
	
	
	public function getAllVideo($tid, $vid, $type){
		$where = "1";
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($vid!='')?" and vid = $vid":"";
		//echo $where;exit;
		return parent::list_data($where, null, null, null, $type);
	}
				
	public function getVideoCount($tid, $vid){
		$where = "1";
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($vid!='')?" and vid = $vid":"";
		//echo $where;exit;
		return parent::count($where);
	}
}