<?php
/**
 * Description of gallery.php
 * @author Animax
 */
 class gallery{
	
	public $attribute;
 
    public function __construct($attribute) {

        $this->attribute = $attribute;
		
    }
	
	public function getAllGallery(){
		$result = array();
		
			$gallery =  new galleryModel();
			
			$gallery_list = $gallery->get_data();
			
			if(is_array($gallery_list) && count($gallery_list)>0)
				$result = array(
					'statusid' => 0,
					//'aid' => $this->attribute['aid'],
					//'mobileid' => array_key_exists("mobileid", $this->attribute)? $this->attribute['mobileid'] : '',
					'datas' => $gallery_list,
					'count' => count($gallery_list)
				);
			else
			
				$result = array( 'statusid' => 1 );
			
		return $result;

	}
 
 
	public function getAllGalleryByID(){
		$result = array();
		if(array_key_exists("gid", $this->attribute)){
		
			$gallery_photo =  new gallery_photoModel();
			
			$where = 'gid = '.$this->attribute['gid'];

			$photo_list = $gallery_photo->get_data($where);
			
			if(is_array($photo_list) && count($photo_list)>0)
				$result = array(
					'statusid' => 0,
					//'aid' => $this->attribute['aid'],
					//'mobileid' => array_key_exists("mobileid", $this->attribute)? $this->attribute['mobileid'] : '',
					'datas' => $photo_list,
					'count' => 1
				);
			else
			
				$result = array( 'statusid' => 1 );
			
		} 
			
		return $result;

	}
 }
?>