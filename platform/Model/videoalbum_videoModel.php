<?php
/**
 * Description of videoalbum_video.model.php
 * @author Animax
 */
class videoalbum_videoModel extends empModel {
    public function __construct() {
        parent::__construct('videoalbum_video');
        $this->dataMatchConfig["vid"] = 'video_id';
        $this->dataMatchConfig["vname"] = 'video_title';
		$this->dataMatchConfig["vicon"] = 'video_thumb';
		$this->dataMatchConfig["video"] = 'video_url';
		$this->dataMatchConfig["vcaption"] = 'video_caption';
		
    }
	
	public function getAllVideoAlbumByID($valbum_id){
		$where = "valbum_id = $valbum_id";
		return parent::list_data($where);
	}
	
}