<?php
/**
 * Description of AdminController.php
 * @author Animax
 */
class AdminController extends TFController {
	protected function isLogin() {
		if (!$_SESSION['admin'] || $_SESSION['admin'] == "") {
			self::TF_JumpToOtherAction("admin","login");
		}
	}
}