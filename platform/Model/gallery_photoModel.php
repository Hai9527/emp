<?php
/**
 * Description of photo.model.php
 * @author Animax
 */
class gallery_photoModel extends empModel {
    public function __construct() {
        parent::__construct('gallery_photo');
        $this->dataMatchConfig["pid"] = 'photo_id';
		$this->dataMatchConfig["pname"] = 'photo_title';
		$this->dataMatchConfig["picon"] = 'photo_thumb';
		$this->dataMatchConfig["photo"] = 'photo_image';
		$this->dataMatchConfig["p_description"] = 'photo_description';
    }
	
	public function getAllGalleryByID($gid){
		$where = "gid = $gid";
		return parent::list_data($where);
	}
	
}