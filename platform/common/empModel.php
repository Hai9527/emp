<?php
/**
 * Description of application.model.php
 * @author Animax
 */
class empModel extends TFModel {
	public $dataMatchConfig;
	
    public function __construct($model) {
        parent::__construct($model);
		$this->dataMatchConfig = array();
    }
	
	public function _toConvert($datas) {
		if ($datas) {
			$list_data = array();
			foreach ($datas as $key=>$item) {
				if ($this->dataMatchConfig[$key]) {
					if (is_array($item)){
						$list_data[$this->dataMatchConfig[$key]] = $this->_toConvert($item);
					} else {
						$list_data[$this->dataMatchConfig[$key]] = $item;
					}
				} else {
					if (is_array($item)){
						$list_data[$key] = $this->_toConvert($item);
					} else {
						$list_data[$key] = $item;
					}
				}
			}
			return $list_data;
		}		
		return $datas;
	}
}