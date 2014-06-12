<?php
/**
 * Description of emp_acc_app.model.php
 * @author Animax
 */
class acc_appModel extends empModel {
    public function __construct() {
        parent::__construct('acc_app');
		$this->dataMatchConfig["aid"] = 'app_id';
		$this->dataMatchConfig["aname"] = 'app_name';
		$this->dataMatchConfig["acid"] = 'user_id';
		$this->dataMatchConfig["acname"] = 'usr_name';
		$this->dataMatchConfig["app"] = 'Apps';
    }


		
	public function getTestPreview($acid,$acpasswd){
			$where = "acid = $acid";
			
			if($acpasswd){
			$where .= ' and acpasswd = "'.$acpasswd.'"';
		}
		
		return parent::list_data($where);
	}

	
}