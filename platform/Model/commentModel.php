<?php
/**
 * Description of list_form.model.php
 * @author Animax
 */
class commentModel extends empModel {
    public function __construct() {
        parent::__construct('comment');
		$this->dataMatchConfig["com_id"] = 'comment_id';
		$this->dataMatchConfig["cuser_id"] = 'user_id';
		$this->dataMatchConfig["cuser_name"] = 'user_name';
		$this->dataMatchConfig["comment"] = 'comment_detail';
		$this->dataMatchConfig["aid"] = 'app_id';
		$this->dataMatchConfig["data_id"] = 'typedata_id';
		$this->dataMatchConfig["cuser_avatar"] = 'user_avatar';
		$this->dataMatchConfig["ctime"] = 'time';
		
    }
	
	public function getListComment($data_id,$aid){
		$where = ($data_id!='')?"data_id = $data_id":"";		
		return parent::list_data($where);
	}
	
}	
	
?>