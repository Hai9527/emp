<?php
/**
 * Description of download.model.php
 * @author Animax
 */
class downloadModel extends TFModel {
    public function __construct() {
        parent::__construct('download');
		$this->dataMatchConfig["did"] = 'download_id';
		$this->dataMatchConfig["dname"] = 'download_title';
		$this->dataMatchConfig["daddress"] = 'download_url';
		$this->dataMatchConfig["dtype"] = 'download_type';
    }
	
	public function getDownloads($did){
		$where = "did = $did";
		return parent::get_data($where);
	}
	
}