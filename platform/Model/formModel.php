<?php
/**
 * Description of form.model.php
 * @author Animax
 */
class formModel extends empModel {
    public function __construct() {
        parent::__construct('form');
 		$this->dataMatchConfig["fid"] = 'form_id';
 		$this->dataMatchConfig["fintroduction"] = 'form_introduction';
 		$this->dataMatchConfig["lmessage"] = 'form_message';
    }


	public function getAllForm($tid){
		$where = ($tid!='')?"tid = $tid":"";
		return parent::get_data($where);
		
	}
	
}