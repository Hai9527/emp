<?php
/**
 * Description of link.model.php
 * @author Animax
 */
class linkModel extends empModel {
    public function __construct() {
        parent::__construct('link');
		
		$this->dataMatchConfig["lid"] = 'link_id';
		$this->dataMatchConfig["tid"] = 'tab_id';
		
    }
	
	
	public function getAllLink($tid, $lid){
		$where = "1";
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($lid!='')?" and vid = $lid":"";
		//echo $where;exit;
		return parent::list_data($where, null, null, null, 'DESC');
	}
				
}