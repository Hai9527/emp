<?php
/**
 * Description of musicalbums.model.php
 * @author Animax
 */
class musicalbumsModel extends empModel {
    public function __construct() {
        parent::__construct('musicalbums');
        $this->dataMatchConfig["malbum_id"] = 'music_albums_id';
		$this->dataMatchConfig["malbum_name"] = 'music_albums_name';
		$this->dataMatchConfig["malbum_cover_image"] = 'music_albums_cover';
		$this->dataMatchConfig["malbum_status"] = 'music_albums_status';
		$this->dataMatchConfig["malbum_is_download"] = 'music_album_downloadable';
		$this->dataMatchConfig["tid"] = 'tab_id';
    }
	
	
	
}

?>