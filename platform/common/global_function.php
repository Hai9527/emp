<?php
/**
 * Description of global
 * @author Animax
 */
class global_function {
	/*
	 * Do something global operate.
	 */
	 
	public static function pretreatment(){
		// do someting global operate on here.
	}

	/**
	 * get the client ip
	 *
	 * return @return String
	 */
	function getRealIpAddr()
	{
		if (!emptyempty($_SERVER['HTTP_CLIENT_IP'])){
			$ip=$_SERVER['HTTP_CLIENT_IP'];
		}
		elseif (!emptyempty($_SERVER['HTTP_X_FORWARDED_FOR'])){
			$ip=$_SERVER['HTTP_X_FORWARDED_FOR'];
		}
		else{
			$ip=$_SERVER['REMOTE_ADDR'];
		}
		return $ip;
	}

	/**
	 * Generate The Random String
	 *
	 * @param int $length
	 * return @return String
	 */
	function generateTheRandomString($length = 6){
		$c= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789^*~!#+-";
		srand((double)microtime()*1000000);
		for($i=0; $i<$l; $i++) {
			$rand.= $c[rand()%strlen($c)];
		}
		return $rand;
	}

	/**
	 * Verify email address
	 * @param String $email
	 * @param String $test_mx
	 *
	 * return @return Bool
	 */
	function isEmailAddress($email, $test_mx = false){
		if(eregi("^([_a-z0-9-]+)(\.[_a-z0-9-]+)*@([a-z0-9-]+)(\.[a-z0-9-]+)*(\.[a-z]{2,4})$", $email)){
			if($test_mx){
				list($username, $domain) = split("@", $email);
				return getmxrr($domain, $mxrecords);
			}else
			return true;
		} else
		return false;
	}

	/**
	 * Resize the image
	 * 
	 * @param String $filename path to the image
	 * @param String $tmpname temporary path to thumbnail 
	 * @param int $xmax
	 * @param int $ymax
	 */
	function resizeImage($filename, $tmpname, $xmax, $ymax){
		$ext = explode(".", $filename);
		$ext = $ext[count($ext)-1];

		if($ext == "jpg" || $ext == "jpeg")
		$im = imagecreatefromjpeg($tmpname);
		elseif($ext == "png")
		$im = imagecreatefrompng($tmpname);
		elseif($ext == "gif")
		$im = imagecreatefromgif($tmpname);

		$x = imagesx($im);
		$y = imagesy($im);

		if($x <= $xmax && $y <= $ymax)
		return $im;

		if($x >= $y) {
			$newx = $xmax;
			$newy = $newx * $y / $x;
		}
		else {
			$newy = $ymax;
			$newx = $x / $y * $newy;
		}

		$im2 = imagecreatetruecolor($newx, $newy);
		imagecopyresized($im2, $im, 0, 0, 0, 0, floor($newx), floor($newy), $x, $y);
		return $im2;
	}
	
	public function arrayFormat($arr, $field, $fields1, $fields2, $data='data'){
			
		$result = array();
		
		if(is_array($arr) && count($arr)>0){
		
			$field_arr = array();
			
			foreach($arr as $key=>$value){
			
				$field_arr[$key] = $value[$field];
			}
			
			
			$field_arr = array_unique($field_arr);
			
			$i = 0;
			
			foreach($field_arr as $key=>$value){
				
				foreach($fields1 as $v1){
					
					$result[$i][$v1] = $arr[$key][$v1];
				
				}
			
				$temp_arr = array();
				
				$j = 0;
				
				foreach($arr as $k=>$v){
					
					if($value == $v[$field]){
						
						foreach($fields2 as $v2){
					
							$temp_arr[$j][$v2] = $v[$v2];
				
						}
						
						$j++;
						
					}
				}		
				
				
				$result[$i][$data] = $temp_arr;
				
				$i++;
			}
			
		}
		
		return $result;
	
	}
	
	public function getFormCode($field_arr){

		$html = '';
		
		if(is_array($field_arr) && count($field_arr)>0){
		
			/** from js */
			
			$html .= "<script language='javascript'>";
						$html .= "function done(){";
						$i = 0;
						foreach($field_arr as $key=>$value){
							if($value['fv_required'] == 1){
								if(in_array($value['fv_type'], array(1, 4, 5))){
									if($i>0) $html .= 'else ';
									$html .= "if(document.getElementsByName('".$value['fv_name']."')[0].value == '') { alert('".$value['prompting_message']."'); return false; }";
								}else{
									$html .= "if(checkedBoxToggle('".$value['fv_name']."')) { alert('".$value['prompting_message']."'); return false; }";
								}
								$i++;
							}
						}

				$html .= "else document.apiForm.submit();";
				
			$html .= "}";
			
			$html .= "function checkedBoxToggle(name){".
				"var resualt=false; ".
					"for(var i=0;i<document.getElementsByName(name).length;i++){".
						"if(document.getElementsByName(name)[i].checked){".
						  "resualt=true;".
						"}".
					"}".         
			"}";
			$html .= "</script>";
			/** from html */
			
			$html .= "<form action='http://myapp/subscribe' mothed='post' name='apiForm'>";
		
			foreach($field_arr as $key=>$value){
				$star = ($value['fv_required'] == 1)? " *":"";
				switch($value['fv_type']){
					case 1: $html .= "<label>".$value['fv_title'].$star."</label><input type='text' name='".$value['fv_name']."' value='".$value['fv_value']."' ><br />";break;
					case 2: $html .= "<label>".(($field_arr[$key-1]['fv_title']==$value['fv_title'])?"":$value['fv_title'])."</label><input type='checkbox' name='".$value['fv_name']."' value='".$value['fv_value']."'>".$value['fv_text']."<br />";break;
					case 3: $html .= "<label>".(($field_arr[$key-1]['fv_title']==$value['fv_title'])?"":$value['fv_title'])."</label><input type='radio' name='".$value['fv_name']."' value='".$value['fv_value']."'>".$value['fv_text']."<br />";break;
					case 4: $html .= "<label>".$value['fv_title']."</label><textarea name='".$value['fv_name']."'>".$value['fv_value']." </textarea><br />";break;
				}
			}
			
			$html .= "</form>";
		
		}
		
		return $html;
	
	}
	
	public function getVerificationCode(){
		Header("Content-type:image/png");

		$authnum_session = '';
		$str = 'abcdefghijkmnpqrstuvwxyz1234567890';

		$l = strlen($str); //得到字串的长度;
		for($i=1;$i<=4;$i++)
		{
			$num=rand(0,$l-1);

			$authnum_session.= $str[$num];
		}
		//session_register("$authnum_session");
		$_SESSION['yzm'] = $authnum_session;

		srand((double)microtime()*1000000);
		$im = imagecreate(50,20);

		$black = ImageColorAllocate($im, 0,0,0);
		$white = ImageColorAllocate($im, 255,255,255);
		$gray = ImageColorAllocate($im, 200,200,200);

		imagefill($im,68,30,$gray);

		$li = ImageColorAllocate($im, 220,220,220);
		for($i=0;$i<3;$i++)
		{
			imageline($im,rand(0,30),rand(0,21),rand(20,40),rand(0,21),$li);
		}

		imagestring($im, 5, 8, 2, $authnum_session, $white);
		for($i=0;$i<90;$i++)
		{
			imagesetpixel($im, rand()%70 , rand()%30 , $gray);
		}
		ImagePNG($im);
		ImageDestroy($im);	
	
	}
	
	public static function adminPage($viewPage, $path = null){
		if(isset($_SESSION['username']) && $_SESSION['username'] != ''){
			$view = new TFView((($path != null)?'admin/':'').$viewPage.'.html');
			$view->addDisplayVar("username", $_SESSION['username']);
			$view->addDisplayVar("utype", $_SESSION['utype']);
			$view->addDisplayVar("uid", $_SESSION['uid']);
			if($path != null){
				$application = new applicationModel();
				$new_count = $application->count("astatus = 1");
				
				$view->addDisplayVar("path", "admin");
				$view->addDisplayVar("new_count", $new_count);
			}
			return $view;
		}else{
			header("Location: ?c=log&a=login");
		}
	}	
	
	public static function createFormField($tid){
		$sql = "insert into emp_form_view ".
			   "(fvid,tid,fv_title,fv_name,fv_type,fv_value,fv_required,regular_expression,prompting_message,vis_hits,fv_text) values".
			   "(null,'$tid','Email Address','email',1,null,1,null,'please fill in the Email Address.',1,null),".
			   "(null,'$tid','Contact Name','contact',1,null,1,null,'please fill in the Contact Name.',1,null),".
			   "(null,'$tid','Full Address','address',1,null,0,null,'please fill in the Address',0,null),".
			   "(null,'$tid','Province/State','state',1,null,0,null,'please fill in the Province/State.',0,null),".
			   "(null,'$tid','Zip/postal Code','zip',1,null,0,null,'please fill in the Zip/postal Code.',0,null),".
			   "(null,'$tid','Preferred Format','format',3,1,0,null,'please select the Preferred Format.',0,'HTML'),".
			   "(null,'$tid','Preferred Format','format',3,2,0,null,'please select the Preferred Format.',0,'TEXT'),".
			   "(null,'$tid','Preferred Format','format',3,3,0,null,'please select the Preferred Format.',0,'MOBILE'),".
			   "(null,'$tid','Phone Number','phone',1,null,0,null,'please fill in your Phone Number.',0,null),".
			   "(null,'$tid','City','city',1,null,0,null,'please fill in the City.',0,null),".
			   "(null,'$tid','Country','country',1,null,0,null,'please fill in the Country.',0,null)";
		//echo $sql;exit;
		mysql_query($sql);
	}
	

	public static function getStatus(){
		if(isset($_REQUEST['aid']) && !empty($_REQUEST['aid'])){
			$application =  new applicationModel();
			$application_info=$application->get_data("aid = ".$_REQUEST['aid']);
			return $application_info['astatus'];
		}else return false;
	}
	
	public static function isSuperAdmin(){
		$result = false;
		if(isset($_SESSION['username']) && $_SESSION['username'] != ''){
			$accout = new accountModel();
			$super_info = $accout->list_data("is_super = 1");
			$superid = array();
			foreach($super_info as $key=>$value){
				$superid[] = $value['acid'];
			}
			if(in_array($_SESSION['username'], $superid)) $result = true;
		}
		return $result;
	}

	/* public static function d_htmlspecialchars($string) {
		if(is_array($string)) {
			foreach($string as $key => $val) {
				$string[$key] = d_htmlspecialchars($val);
			}
		} else {
			$string = str_replace('&', '&', $string);
			$string = str_replace('"', '"', $string);
			$string = str_replace(''', ''', $string);
			$string = str_replace('', '>', $string);
			$string = preg_replace('/&(#d;)/', '&1', $string);
		}
			return $string;
	} */
	
}


