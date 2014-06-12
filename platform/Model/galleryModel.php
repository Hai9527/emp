<?php
/**
 * Description of gallery.model.php
 * @author Animax
 */
class galleryModel extends TFModel {
    public function __construct() {
        parent::__construct('gallery');
        $this->dataMatchConfig["gid"] = 'gallery_id';
		$this->dataMatchConfig["gname"] = 'gallery_name';
		$this->dataMatchConfig["gdefaut_icon"] = 'gallery_thumb';
		$this->dataMatchConfig["gcount"] = 'gallery_count';
    }
	
	
	public function getAllGallery($gid){
        global $TFConfig;
		$where = "gid = $gid";
		$result = parent::get_data($where);
		$result['gdefaut_icon'] = $TFConfig['path'].$value['gdefaut_icon'];
		return $result;		
	}
		
	/* public function api_getAllGallery(){
		
		$result = array();
		
		if(array_key_exists("gid", $this->attribute)){
			$gallery_list = parent::_toConvert($this->getAllGallery($this->attribute['gid']));
			if(is_array($gallery_list) && count($gallery_list)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $gallery_list,
					'count' => count($gallery_list)
				);
			else
				$result = array( 'statusid' => 1 );
		}		
			return $result;
	} */
	
}