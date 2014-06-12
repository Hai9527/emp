<?php
/**
 * Description of musicalbum_music.model.php
 * @author Animax
 */
class musicalbum_musicModel extends empModel {
    public function __construct() {
        parent::__construct('musicalbum_music');
        $this->dataMatchConfig["vid"] = 'video_id';
        $this->dataMatchConfig["vname"] = 'video_title';
		$this->dataMatchConfig["vicon"] = 'video_thumb';
		$this->dataMatchConfig["video"] = 'video_url';
		$this->dataMatchConfig["vcaption"] = 'video_caption';
		
    }
	
	public function getAllMusicAlbumByID($malbum_id){
		$where = "malbum_id = $malbum_id";
		return parent::list_data($where);
	}
	
}