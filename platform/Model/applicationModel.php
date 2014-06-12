<?php
/**
 * Description of application.model.php
 * @author Animax
 */
class applicationModel extends empModel {
    public function __construct() {
        parent::__construct('application');
		$this->dataMatchConfig["aid"] = 'app_id';
		$this->dataMatchConfig["aname"] = 'app_name';
		$this->dataMatchConfig["aicon"] = 'app_icon';
		$this->dataMatchConfig["aload_image"] = 'app_load_image';
		$this->dataMatchConfig["akey"] = 'app_test_key';
    }
	
	public function getAppConfig($aid, $update_time){
		global $TFConfig;
		$where = "aid = $aid";
		
		if($update_time){
			$where .= ' and aupdate_time > "'.$update_time.'"';
		}
		$result = parent::list_data($where);
		foreach($post_data as $key=>$value){
			$result[$key]['aload_image'] = $TFConfig['path'].$value['aload_image'];
		}
		
		return $result;
	}
	
	
	public function api_getAppConfig(){
		$result = array();
	
		if(array_key_exists("aid", $this->attribute)){
			
			$application_info = parent::_toConvert($this->getAppConfig($this->attribute['aid'], $this->attribute['aupdate_time']));
			if(is_array($application_info) && count($application_info)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $application_info,
					'count' => 1
				);
			else
				$result = array( 'status' => 1 );
		}
		
		return $result;
		
	}
 
}