<?php
/**
 * Description of list_form.model.php
 * @author Animax
 */
class accumulativeModel extends empModel {
    public function __construct() {
        parent::__construct('accumulative');
		$this->dataMatchConfig["accid"] = 'accumulative_id';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["aid"] = 'app_id';
		$this->dataMatchConfig["data_id"] = 'typedata_id';
		$this->dataMatchConfig["acc_type"] = 'accumulative_type';
		$this->dataMatchConfig["acc_counts"] = 'accumulative_counts';
		
    }
	
	
	/* 獲取同一條件匹配的情況下多少記錄 */
	public function getAccumulativeCounts($data_id,$aid){
		
		$where = ($data_id!='')?"data_id = $data_id":"";
		
		return parent::list_data($where);
		
	}
	
	
	/* API:獲取同一條件匹配的情況下多少記錄 */
	public function api_getAccumulativeCounts(){

		$result = array();
		
		$data_id = array_key_exists("data_id", $this->attribute)? $this->attribute['data_id']:'';		
		$accumulative_list = parent::_toConvert($this->getAccumulativeCounts($data_id));
		if(is_array($accumulative_list) && count($accumulative_list)>0)
			$result = array(
				'statusid' => 0,
				'datas' => $accumulative_list,
				'count' => count($accumulative_list)
			);
		else
			$result = array( 'statusid' => 1 );
		return $result;
	}
}
?>