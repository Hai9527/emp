<?php

include_once('phpmailer/class.phpmailer.php');
include_once('phpmailer/class.smtp.php');

class PhpEmailerManager
{

	public static $instance;
	public static function getInstance(){
		global $config;
		
		if (!isset(self::$instance)) {

			self::$instance = new PHPMailer;
			self::$instance->IsSMTP();                  // set mailer to use SMTP
			self::$instance->Port = '25';
			self::$instance->Host = 'smtp.fmldigital.com';  // specify main and backup server
			self::$instance->SMTPAuth = true;     // turn on SMTP authentication
			self::$instance->Username = 'preview@fmldigital.com';  // SMTP username
			self::$instance->Password = 'Pass@word1'; // SMTP password

			
			self::$instance->Priority = "3";
			self::$instance->IsHTML(true);

        }
        
		return self::$instance;
	}

	public static function sendMail($mailFrom, $mailFromName, $mailTo , $mailSubject, $mailBody){
		if(!isset(self::$instance)){
			self::getInstance();
		}
		self::$instance->From = $mailFrom;
		self::$instance->FromName = $mailFromName;

		//mailTo
		if(!empty($mailTo)){
			foreach($mailTo as $address => $recipients){
				self::$instance->AddAddress($address,$recipients);
			}
		}

		//mailCc
		if(!empty($mailCc)){
			foreach($mailCc as $address => $recipients){
				self::$instance->AddCC($address,$recipients);
			}
		}

		//mailBcc
		if(!empty($mailBcc)){
			foreach($mailBcc as $address => $recipients){
				self::$instance->AddBCC($address,$recipients);
			}
		}


		self::$instance->Subject = $mailSubject;
		self::$instance->Body    = $mailBody;

		if(self::$instance->Send())
		{
			return true;
		}else{
			if(self::$instance->IsError()){
                            die(self::$instance->ErrorInfo);
			}
			return false;
		}
	}
}

?>