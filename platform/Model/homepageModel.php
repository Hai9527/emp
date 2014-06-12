<?php
/**
 * Description of homepage.model.php
 * @author Animax
 */
class homepageModel extends empModel {
    public function __construct() {
        parent::__construct('homepage');
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["hcontent"] = 'page_editor';
		$this->dataMatchConfig["himage"] = 'page_background';
		$this->dataMatchConfig["htemplate"] = 'page_template';
    }
	
	public function getHomepage($tid){
		$where = ($tid!='')?"tid = $tid":"";
		return parent::list_data($where);
	}

}