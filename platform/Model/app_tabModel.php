<?php
/**
 * Description of application.model.php
 * @author Animax
 */
class app_tabModel extends empModel {
    public function __construct() {
		
        parent::__construct('app_tab');
		$this->dataMatchConfig["aid"] = 'app_id';
		$this->dataMatchConfig["aname"] = 'app_name';
		$this->dataMatchConfig["amarket_icon"] = 'app_icon';
		$this->dataMatchConfig["aindex_image"] = 'app_load_image'; 
		$this->dataMatchConfig["temid"] = 'Template_type'; 	
		$this->dataMatchConfig["fnid"] = 'function_id'; 
		$this->dataMatchConfig["fnname"] = 'function_name';
		$this->dataMatchConfig["tab"] = 'tabs';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["tname"] = 'tab_name';
		$this->dataMatchConfig["ticon"] = 'tab_icon';
		$this->dataMatchConfig["tdefault"] = 'tab_type';
		
			
	}
 
 
	/* 獲取同一條件匹配的情況下多少記錄 */ 
	public function getAppConfig($aid, $update_time = null, $tstatus = 1){
		$where = "aid = $aid";
		if($tstatus){
			$where .=' and tstatus = '.$tstatus;
		}
		if($update_time){
			//$where = "1";
			$where .= ' and aupdate_time > "'.$update_time.'"';
		}
		
		//echo $where;exit;
		return parent::list_data($where, null,null,null,'torder','ASC');
		
		
	}

	
	/* 关键字搜索 */
	/* public function getTabKeywordSearch($aid,$tid){
		$where = "aid = $aid";
		$where .=' and tid = '.$tid;
		$app_tabfunction_info = $app_tabfunction->get_data($where);
		
	} */
	
	public function getTabKeywordSearch($aid,$tid,$keyword){
		$app_tabfunction = new app_tabModel();
		$con = "aid = $aid";
		$con .=' and tid = '.$tid;
		$app_tabfunction_info = $app_tabfunction->get_data($con);
		//var_dump($app_tabfunction_info);exit;
		switch($app_tabfunction_info['fnid']){
			case 2: $model = "article"; $what = "artname"; $where = "artname like '%$keyword%'"; break;
			case 3: $model = "video"; $what = "vname"; $where = "vname like '%$keyword%'";break;
			case 5: $model = "event"; $what = "ename"; $where = "ename like '%$keyword%'";break;
			case 6: $model = "music"; $what = "mname"; $where = "mname like '%$keyword%'";break;
		}
		//echo $model."Model()";exit;
		$model = $model."Model";
		$temp_model = new $model();	
		//var_dump($temp_model);
		return $temp_model->list_data($where, $what);
	}
}