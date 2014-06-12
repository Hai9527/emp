<?php
/**
 * Description of function.model.php
 * @author Animax
 */
class functionModel extends TFModel {
    public function __construct() {
        parent::__construct('function');
    }
	
	public function getFunctionName($fnid){
		$result = parent::get_data("fnid = ".$fnid);
		return $result['fnname'];
	}
	
	public function getFunctionTitle($fnid){
		$result = parent::get_data("fnid = ".$fnid);
		return $result['fntitle'];
	}
	
	public function getFunctionIcon($fnid){
		$result = parent::get_data("fnid = ".$fnid);
		return $result['fnicon'];
	}
}