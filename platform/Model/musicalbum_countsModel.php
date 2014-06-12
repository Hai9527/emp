<?php
/**
 * Description of musicalbum_counts.model.php
 * @author Animax
 */
class musicalbum_countsModel extends empModel {
    public function __construct() {
        parent::__construct('musicalbum_counts');
        $this->dataMatchConfig["malbum_id"] = 'music_albums_id';
		$this->dataMatchConfig["malbum_name"] = 'music_albums_name';
		$this->dataMatchConfig["malbum_cover_image"] = 'music_albums_cover';
		$this->dataMatchConfig["malbum_status"] = 'music_albums_status';
		$this->dataMatchConfig["malbum_is_download"] = 'music_album_downloadable';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["malbum_count"] = 'music_albums_count';
    }
	
	public function getAllMusicalbums($tid){
		$where = ($tid)? "tid = ".$tid : '';
		return parent::list_data($where);
	}
	
	
}

?>