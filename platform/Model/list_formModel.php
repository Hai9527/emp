<?php
/**
 * Description of list_form.model.php
 * @author Animax
 */
class list_formModel extends empModel {
    public function __construct() {
        parent::__construct('list_form');
		$this->dataMatchConfig["fid"] = 'form_id';
		$this->dataMatchConfig["pname"] = 'form_name';
		$this->dataMatchConfig["picon"] = 'form_desc';
		$this->dataMatchConfig["photo"] = 'form_message';
    }


	public function getListFrom($lid){
		$where = ($lid!='')?"lid = $lid":"";
		return parent::list_data($where);
	}

}