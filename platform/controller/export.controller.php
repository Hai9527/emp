<?php
/**
 * Description of export.controller.php
 * @author Animax
 */
class export extends TFController {

	
	/**
	 *   導出所有的APP
	*/
	public function exportAllApp(){
			$application = new app_accountModel();
			$application_list = $application->list_data();
			
			foreach($application_list as $key=>$value){
					switch($value['astatus']){
						case 0:
							$application_list[$key]['astatus'] = 'Draft';
						break;
						case -1:
							$application_list[$key]['astatus'] = 'Disapproved';
						break;
						case 1:
							$application_list[$key]['astatus'] = 'Submitted';
						break;
						case 2:
							$application_list[$key]['astatus'] = 'Processing';
						break;
						case 3:
							$application_list[$key]['astatus'] = 'Approved';
						break;
				}
				
			switch($value['temid']){
				case 1:
					$application_list[$key]['temid'] = 'music_template';
				break;
				case 2:
					$application_list[$key]['temid'] = 'event_template';
				break;
				case 3:
					$application_list[$key]['temid'] = 'movie_template';
				break;
			}
		}
		/* print_r("<pre>");
		print_r($application_list);exit(); */
		
		$result = $this->export($application_list, array('aname'=>'App Name',
														 'acname' => 'App Creator',
														 'astatus'=>'Status',
														 'asumbit_time'=>'Submitted Date',
														 'aapproved_time'=>'Approved Date',
														 'temid'=>'Selected Template',
														 'akey'=>'Key'
														 ));
		
		echo $result;
		exit;
	}
	
	
	
	
	/**
	 *   導出所有的User
	*/
	public function exportAllUser(){
		$account = new accountModel();
		$account_list = $account->list_data();
		
		foreach($account_list as $key=>$value){
			switch($value['actype']){
				case 0:
					$account_list[$key]['actype'] = 'App Creator';
				break;
				case 1:
					$account_list[$key]['actype'] = 'Admin';
				break;
			}
			
			switch($value['acstatus']){
				case 0:
					$account_list[$key]['acstatus'] = 'Inactive';
				break;
				case 1:
					$account_list[$key]['acstatus'] = 'Active';
				break;
			}
		}
		
		$result = $this->export($account_list, array('acname'=>'Username',
													 'acfname'=>'First Name',
													 'aclname'=>'Last Name',
													 'acpasswd'=>'Password',
													 'acemail'=>'Email Address',
													 'actype'=>'User Type',
													 'acstatus'=>'Status'));
		
		echo $result;
		exit;
	}
	
	
	
	/**
	 *   導出所有的Conment
	*/
	public function exportComment($arr, $fields){
		$result = $this->export($arr, $fields);
		
		echo $result;
		exit;
	}
	
	
	
	/**
	 *   導出功能
	*/
	public function export($data, $field){
	
				$filename = date('YmdHis').".csv";

				header("Content-type:text/csv; charset=utf-8");
				header("Content-Disposition:attachment;filename=".$filename);
				header('Cache-Control:must-revalidate,post-check=0,pre-check=0');
				header('Expires:0');
				header('Pragma:public'); 
				
				$content = ""; 
				$content .= join(",", $field);
				$content .= chr(13);
				foreach($data as $key=>$value) {
					foreach($field as $k=>$v){
						$content .= $this->i($value[$k]).",";
					}
					$content .= chr(13);
				}
				return $content;
	}
	
	
	/**
	 *   導出格式 
	*/
	function static escapeCSV($str){
			$str = str_replace(array(',','"',"\n\r"),array('','""',''),$str);
			if($str == ""){
				$str = '""';
			}
			return $str;
	}
	function static i($strInput){
			return iconv('utf-8','gb2312',self::escapeCSV($strInput));
			return $this->escapeCSV($strInput);
	}
}
?>