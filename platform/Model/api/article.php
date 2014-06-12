<?php
/**
 * Description of article.php
 * @author Animax
 */
 class article{
	
	public $attribute;
 
    public function __construct($attribute) {

        $this->attribute = $attribute;
		
    }
	
	public function getAllArticle(){
		$result = array();
	
		 //if(array_key_exists("artid", $this->attribute)){
		
			$article =  new articleModel();
			
			$article_list = $article->get_data();
			
			/*$where = 'artid = '.$this->attribute['artid'];
			if(array_key_exists("aupdate_time", $this->attribute)){
				$where .= ' and aupdate_time > "'.$this->attribute['aupdate_time'].'"';
			}
			$application_info = $application->get_data($where);*/
			
			if(is_array($article_list) && count($article_list)>0)
				$result = array(
					'statusid' => 0,
					'aid' => $this->attribute['aid'],
					'mobileid' => array_key_exists("mobileid", $this->attribute)? $this->attribute['mobileid'] : '',
					'datas' => $article_list,
					'count' => 1
				);
			else
			
				$result = array( 'statusid' => 1 );
			
			return $result;

	}
	
		public function getAllArticleByID(){
		$result = array();
	
		/* if(array_key_exists("aid", $this->attribute)){
		
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
			
		} */
		$result = array( 'statusid' => 1 );
			
		return $result;

	}
 
 }
?>