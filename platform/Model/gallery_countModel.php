<?php
/**
 * Description of photo.model.php
 * @author Animax
 */
class gallery_countModel extends empModel {
    public function __construct() {
        parent::__construct('gallery_count');
		$this->dataMatchConfig["pid"] = 'photo_id';
		$this->dataMatchConfig["pname"] = 'photo_title';
		$this->dataMatchConfig["picon"] = 'photo_thumb';
		$this->dataMatchConfig["photo"] = 'photo_image';
    }
	
	public function getAllGallery($tid){
		$where = ($tid)? "tid = ".$tid : '';
		return parent::list_data($where);
	}
	
}