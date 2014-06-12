<?php
/**
 * Description of videoalbums.model.php
 * @author Animax
 */
class videoalbumsModel extends empModel {
    public function __construct() {
        parent::__construct('videoalbums');
        $this->dataMatchConfig["valbum_id"] = 'video_albums_id';
		$this->dataMatchConfig["valbum_name"] = 'video_albums_name';
		$this->dataMatchConfig["valbum_cover_image"] = 'video_albums_cover';
		$this->dataMatchConfig["valbum_status"] = 'video_albums_status';
		$this->dataMatchConfig["valbum_is_download"] = 'video_album_downloadable';
		$this->dataMatchConfig["tid"] = 'tab_id';
    }
	
	
	
}

?>