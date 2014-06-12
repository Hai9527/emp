<?php
/**
 * Description of numbersModel.model.php
 * @author Animax
 */
class numbersModel extends empModel {
    public function __construct() {
        parent::__construct('numbers');
		$this->dataMatchConfig["aid"] = 'app_id';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["data_id"] = 'data_type_id';
		$this->dataMatchConfig["comments_num"] = 'comment_counts';
		$this->dataMatchConfig["players_num"] = 'players_counts';
		$this->dataMatchConfig["likes_num"] = 'likes_counts';
    }
	
	public function getListCounts($data_id,$aid,$tid){
		$where = ($data_id!='')?"data_id = $data_id":"";		
		return parent::list_data($where);
	}
}