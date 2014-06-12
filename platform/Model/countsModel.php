<?php
/**
 * Description of numbersModel.model.php
 * @author Animax
 */
class countsModel extends empModel {
    public function __construct() {
        parent::__construct('counts');
		$this->dataMatchConfig["aid"] = 'app_id';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["data_id"] = 'data_type_id';
		$this->dataMatchConfig["play_number"] = 'players_counts';
		$this->dataMatchConfig["likes_num"] = 'likes_counts';
		$this->dataMatchConfig["comment_num"] = 'comment_counts';
    }
	
	public function getAllCounts($data_id,$aid,$tid){
		$where = "1";
		$where .= ($data_id!='')?" and data_id = $data_id":"";		
		$where .= ($aid!='')?" and aid = $aid":"";		
		$where .= ($tid!='')?" and tid = $tid":"";		
		return parent::list_data($where);
	}
}