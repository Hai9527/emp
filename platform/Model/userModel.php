<?php
/**
 * Description of user.model.php
 * @author Animax
 */
class userModel extends empModel {
    public function __construct() {
        parent::__construct('user');
		$this->dataMatchConfig["user_id"] = 'user_id';
		$this->dataMatchConfig["user_name"] = 'user_name';
		$this->dataMatchConfig["user_email"] = 'user_email';
		$this->dataMatchConfig["user_city"] = 'user_city';
		$this->dataMatchConfig["aid"] = 'app_id';
    }
	
	
	
}	
	
?>