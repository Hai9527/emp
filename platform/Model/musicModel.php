<?php
/**
 * Description of music.model.php
 * @author Animax
 */
class musicModel extends empModel {
    public function __construct() {
        parent::__construct('music');
		
		$this->dataMatchConfig["mid"] = 'music_id';
		$this->dataMatchConfig["mname"] = 'music_title';
		$this->dataMatchConfig["micon"] = 'music_thumb';
		$this->dataMatchConfig["music"] = 'music_url';
		$this->dataMatchConfig["mstatus"] = 'music_status';
		$this->dataMatchConfig["mis_download"] = 'download';
		$this->dataMatchConfig["mcaption"] = 'music_caption';
		$this->dataMatchConfig["tid"] = 'tab_id';
    }
	
	
	public function getAllMusic($tid,$mid){
		$where = "1";
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($mid!='')?" and mid = $mid":"";
		
		return parent::list_data($where,null,null,null,'morder','DESC');
	}
	
}