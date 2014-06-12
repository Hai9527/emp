<?php
/**
 * Description of postForm.php
 * @author Animax
 */
 class postForm{
	
	public $attribute;
 
    public function __construct($attribute) {

        $this->attribute = $attribute;
		
    }
	
	public function getPostForm(){
		$result = array();
	
		if(array_key_exists("aid", $this->attribute)){
		
			$application =  new applicationModel();
			
			$where = 'aid = '.$this->attribute['aid'];
			if(array_key_exists("aupdate_time", $this->attribute)){
				$where .= ' and aupdate_time > "'.$this->attribute['aupdate_time'].'"';
			}
			$application_info = $application->get_data($where);
			
			if(is_array($application_info) && count($application_info)>0)
				$result = array(
					'statusid' => 0,
					'aid' => $this->attribute['aid'],
					'mobileid' => array_key_exists("mobileid", $this->attribute)? $this->attribute['mobileid'] : '',
					'datas' => $application_info,
					'count' => 1
				);
			else
			
				$result = array( 'statusid' => 1 );
			
			return $result;
			
		} 
		$result = array( 'statusid' => 1 );
			
		return $result;

	}
 
 }
?>