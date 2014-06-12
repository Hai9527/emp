<?php
/**
 * Description of icon.model.php
 * @author Animax
 */
class iconModel extends TFModel {
    public function __construct() {
        parent::__construct('icon');
    }
	public function getIcon($iid){
		$result = parent::get_data("iid = ".$iid);
		return $result['icon'];
	}
}