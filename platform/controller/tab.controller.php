<?php
/**
 * This is the tab controller
 *
 * @author Animax
 */
class tab extends TFController {
    public function index(){

    }

	
	/**
	 *  delete掉某一個tab
	*/
	public function delete(){
		if(parent::has("tid")){
			$tab = new tabModel();
			$tab->remove("tid = ".parent::get("tid"));
			echo '<script>history.go(-1);</script>';
		}
		exit;
	}
	
	
	/**
	 *  改變tab的狀態
	*/
	public function changeStatus(){
		if(parent::has("tid")){
			$tab = new tabModel();
			$tab->remove("tid = ".parent::get("tid"));
		}
		exit;
	}
	
	
	
	/**
	 *   customize_feature  TAB_BAR的切換
	*/
	public function sortTab(){
		if(parent::has("aid")){
			$tab = new tabModel();
			//$tab_arr = array_reverse(parent::get("tab_arr"));
			$tab_arr = parent::get("tab_arr");
			$i=1;
			foreach($tab_arr as $value){
				$tab->update("tid = ".$value, array("torder"=>$i));
				$i++;
			}
		}
		exit;
	}
	
	
	/**
	 *   排序
	*/
	public function goSort(){
		$ord1 = parent::has("ord1")? parent::get("ord1") : '';
		$ord2 = parent::has("ord2")? parent::get("ord2") : '';
		$tid = parent::has("tid")? parent::get("tid") : '';
		$aid = parent::has("aid")? parent::get("aid") : '';
		if(!empty($ord1) && !empty($ord2) && !empty($tid) && !empty($aid)){
			$tab = new tabModel();
			$tab_list = $tab->list_data("aid = ".$aid, null, null, null, "torder", 'asc');
			$tid_arr = array();
			foreach($tab_list as $key=>$value){
				array_push($tid_arr, $value['tid']);
			}
			
			$temp = array();
			if($ord1<$ord2){
				foreach($tid_arr as $key=>$value){
					if($key!=($ord1-1)){
						array_push($temp, $value);
						if($key == ($ord2-1)){
							array_push($temp, $tid_arr[$ord1-1]);
						}
					}
				}
			
			}else{
				foreach($tid_arr as $key=>$value){
					if($key != ($ord1-1)){
						if($key==($ord2-1)){
							array_push($temp, $tid_arr[$ord1-1]);
						}
						array_push($temp, $value);
					}
				}
			}
			//$temp = array_reverse($temp);
			
			$i=0;
			foreach($temp as $value){
				$tab->update("tid = ".$value, array("torder"=>$i));
				$i++;
			}
			echo 'aaa';
		}
		exit;
	}

}
?>
