<?php
/**
 * Description of videoalbum_counts.model.php
 * @author Animax
 */
class videoalbum_countsModel extends empModel {
    public function __construct() {
        parent::__construct('videoalbum_counts');
        $this->dataMatchConfig["valbum_id"] = 'video_albums_id';
		$this->dataMatchConfig["valbum_name"] = 'video_albums_name';
		$this->dataMatchConfig["valbum_cover_image"] = 'video_albums_cover';
		$this->dataMatchConfig["valbum_status"] = 'video_albums_status';
		$this->dataMatchConfig["valbum_is_download"] = 'video_album_downloadable';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["valbum_count"] = 'video_albums_count';
    }
	
	public function getAllVideoalbums($tid){
		$where = ($tid)? "tid = ".$tid : '';
		return parent::list_data($where);
	}
	
	
}

?>