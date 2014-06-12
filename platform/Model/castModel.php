<?php
/**
 * Description of article.model.php
 * @author Animax
 */
class castModel extends empModel {
    public function __construct() {
        parent::__construct('cast');
		$this->dataMatchConfig["cid"] = 'cast_id';
		$this->dataMatchConfig["cname"] = 'cast_title';
		$this->dataMatchConfig["cicon"] = 'cast_icon';
		$this->dataMatchConfig["cimage"] = 'cast_image';
		$this->dataMatchConfig["ccontent"] = 'cast_content';
		$this->dataMatchConfig["cstatus"] = 'cast_status';
		$this->dataMatchConfig["tid"] = 'tab_id';
    }
	
	
	public function getAllCast($tid, $cid){
		$where = 1;
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($cid!='')?" and cid = $cid":"";
		return parent::list_data($where, null, null, null, "corder", "DESC");
	}
	
}