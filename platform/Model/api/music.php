<?php
/**
 * Description of music.php
 * @author Animax
 */
 class music{
	
	public $attribute;
 
    public function __construct($attribute) {

        $this->attribute = $attribute;
		
    }
	
	public function getAllMusic(){
		$result = array();
		
			$music =  new musicModel();
			
			$music_list = $music->get_data();
			
			if(is_array($music_list) && count($music_list)>0)
				$result = array(
					'statusid' => 0,
					//'aid' => $this->attribute['aid'],
					//'mobileid' => array_key_exists("mobileid", $this->attribute)? $this->attribute['mobileid'] : '',
					'datas' => $music_list,
					'count' => count($music_list)
				);
			else
				$result = array( 'statusid' => 1 );

		return $result;

	}
 
 }
?>