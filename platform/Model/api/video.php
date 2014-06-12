<?php
/**
 * Description of video.php
 * @author Animax
 */
 class video{
	
	public $attribute;
 
    public function __construct($attribute) {

        $this->attribute = $attribute;
		
    }
	
	public function getAllVideo(){
		$result = array();
		
			$video =  new videoModel();
			
			$video_list = $video->get_data();
			
			if(is_array($video_list) && count($video_list)>0)
				$result = array(
					'statusid' => 0,
					//'aid' => $this->attribute['aid'],
					//'mobileid' => array_key_exists("mobileid", $this->attribute)? $this->attribute['mobileid'] : '',
					'datas' => $video_list,
					'count' => count($video_list)
				);
			else
				$result = array( 'statusid' => 1 );

		return $result;

	}
 
 }
?>