<?php
/**
 * Description of form_view.model.php
 * @author Animax
 */
class form_viewModel extends empModel {
    public function __construct() {
        parent::__construct('form_view');
		$this->dataMatchConfig["fid"] = 'form_id';
		$this->dataMatchConfig["pname"] = 'form_name';
		$this->dataMatchConfig["picon"] = 'form_desc';
		$this->dataMatchConfig["photo"] = 'form_message';
    }


	public function getFromView($tid){
		$where = "tid = $tid";
		return parent::list_data($where);
	}

	public function getHitsFromView($tid){
		//$where = "tid = $tid AND (vis_hits = 1 OR NOT fv_value is NULL)";
		$where = "tid = $tid AND vis_hits = 1";
		return parent::list_data($where);
	}
	
	public function api_getFromView(){
		$result = array();

		$fvid = array_key_exists("tid", $this->attribute)? $this->attribute['tid']:'';
		$fromview_list = parent::_toConvert($this->getFromView($tid));
		if(is_array($fromview_list) && count($fromview_list)>0)
				$result = array(
					'statusid' => 0,
					'datas' => $fromview_list,
					'count' => count($fromview_list)
				);
			else
				$result = array( 'statusid' => 1 );
			
			return $result;
	}

}