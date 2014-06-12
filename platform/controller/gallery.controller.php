<?php
/**
 * This is the gallery controller
 *
 * @author Animax
 */
class gallery extends TFController {
    public function index(){

    }


	/**
	 *   獲取gallery list
	*/
	public function getGalleryList($view){
				$page = parent::has('page')? parent::get('page') : 1;
				$number = 10;
				$offset = ($page-1)*$number;
				$where = "";
				
				if(parent::has("tid")){
					$where .= "tid = ".parent::get("tid");
				}
				$link = '?c=main&a=create&step=customize_feature&aid='.parent::get("aid").'&tid='.parent::get("tid");

				$gallery = new gallery_countModel();
				
				$count = $gallery->count($where);
				
				$show_page = Pages::show_page($count,$page,$link,$number);

				//$gallery_list = $gallery->list_data($where);
				$gallery_list = $gallery->list_data($where, null, $offset, $number, 'gorder','DESC');

				$gallery_photo = new gallery_photoModel();
				$counts = new countsModel();
				
				foreach($gallery_list as $key=>$value){
/*
					$numbers = new numbersModel();
					$numbers_info = $numbers->get_data("tid = ".parent::get("tid")." and data_id = ".$value['gid']);
					$gallery_list[$key]['players_num'] = $numbers_info['players_num'];
					$gallery_list[$key]['likes_num'] = $numbers_info['likes_num'];
					$gallery_list[$key]['comments_num'] = $numbers_info['comments_num'];
*/

					$gallery_list[$key]['players_num'] = 0;
					$gallery_list[$key]['likes_num'] = 0;
					$gallery_list[$key]['comments_num'] = 0;
					
					$photoList = $gallery_photo->getAllGalleryByID($value['gid']);
					foreach($photoList as $photo_key=>$photo_value){
						$result = $counts->_toConvert($counts->getAllCounts($photo_value['pid'],null,parent::get("tid")));
						if ($result && $result[0]) {
							$gallery_list[$key]['players_num'] += $result[0]['players_counts'];
							$gallery_list[$key]['likes_num'] += $result[0]['likes_counts'];
							$gallery_list[$key]['comments_num'] += $result[0]['comment_counts'];
						}
					}
					
				}
				
				$view->addDisplayVar("count", $count);
				$view->addDisplayVar("offset", $offset);
				$view->addDisplayVar("show_page", $show_page);
				$view->addDisplayVar("gallery_list", $gallery_list);

				return $view;
	}


	
	
	
	
	/**
	 *   gallery add&edit
	*/
	public function editGallery(){

				//$view = new TFView('tab_feature_edit.html');
				$view = global_function::adminPage('tab_feature_edit');

				$view->addDisplayVar("edit_page", 'tab_edit_gallery');

				$action = "?c=gallery&a=saveGallery&tid=".parent::get("tid");

				$title = "Add New";

				$gallery = new galleryModel();

				if(parent::has("gid")){

					$title = "Edit";

					$action = "?c=gallery&a=saveGallery&gid=".parent::get("gid");

					$where .= "gid = ".parent::get("gid");

					$gallery_info = $gallery->get_data($where);

					$view->addDisplayVar("gallery_info", $gallery_info);
					
					/** get photo list*/
					
					$photodb = new photoModel();
					
					$photo_list = $photodb->list_data($where);
					
					$view->addDisplayVar("photo_list", $photo_list);

				}

				$view->addDisplayVar("title", $title);

				$view->addDisplayVar("action", $action);

				return $view;

	}


	
	
	/**
	 *   gallery add&edit后save
	*/
	public function saveGallery(){

				$gname = parent::has("gname")? parent::get("gname") : '';
				$gdefaut_icon = parent::has("gdefaut_icon")? parent::get("gdefaut_icon") : '';
				$gis_download = parent::has("gis_download")? parent::get("gis_download") : '';
				$gstatus = parent::has("gstatus")? parent::get("gstatus") : '';

				$data = array(
							"gname" => htmlspecialchars_decode($gname),
							"gdefaut_icon" => $gdefaut_icon,
							"gis_download" => $gis_download,
							"gstatus" => $gstatus
						);
				$gallery = new galleryModel();

				if(parent::has("gid")){
					
					$gid = parent::get("gid");

					$gallery->update("gid = ". parent::get("gid"), $data);

				}else if(parent::has("tid")){
				
					$data["tid"] = parent::get("tid");
					
					$insert_id = $gallery->create($data);
					
					$gid = $insert_id;
					
					$gallery->update("gid = ". $insert_id, "gorder = ". $insert_id );

				}
				if($gid){
					$photodb = new photoModel();
					$photodb->remove("gid = ".$gid);
					if(parent::has('photo')){
						$photo = parent::get('photo');
						$pname = parent::get('pname');
						$p_description = parent::get('p_description');
						for($i=0;$i<count($photo);$i++){
							$temp = explode("/",$photo[$i]);
							$picon = str_replace(end($temp),"thumbnail/thumb_".end($temp),$photo[$i]);
							$photodb->create(array('pname'=>$pname[$i],'photo'=>$photo[$i],'picon'=>$picon,'p_description'=>str_replace('Desciption...','',$p_description[$i]),'gid'=>$gid));
						}
					}
				}

				echo '<script>self.parent.history.go(0); </script>';
				exit;

	}
	

	
	
	
	/**
	 *   delete gallery
	*/
	public function deleteGallery(){
		$gallery = new galleryModel();
		if(parent::has("gid")){
	
			$gallery->remove("gid = ". parent::get("gid"));
			echo '<script>history.go(-1);</script>';
			exit;
		}
	}
	

	
	
	/**
	 *   更改gallery排序
	*/
	public function goSort(){
				$gid = parent::has("id")? parent::get("id") : '';
				$oldOrder = parent::has("oldOrder")? parent::get("oldOrder") : '';
				$newOrder = parent::has("newOrder")? parent::get("newOrder") : '';
				$tid = parent::has("tid")? parent::get("tid") : '';
				$aid = parent::has("aid")? parent::get("aid") : '';
				if(!empty($gid) && !empty($oldOrder) && !empty($newOrder) && !empty($tid) && !empty($aid)){
					$ltr = ($newOrder>$oldOrder)?true:false;
					$offset = ($ltr)?$oldOrder-1:$newOrder-1;
					$number = ($ltr)?$newOrder-$oldOrder+1:$oldOrder-$newOrder+1;
					$db = new galleryModel();
					$order_arr = $db->list_data("`tid` = $tid", "`gid`,`gorder`", $offset, $number, "gorder", "DESC");
					$updateOrder = ($ltr)?$order_arr[$number-1]['gorder']:$order_arr[0]['gorder'];
					// echo $updateOrder;
					// print_r($order_arr);exit;
					
					if($ltr){
						foreach($order_arr as $value){
							$db->update("gid = ".$value['gid'], array("gorder"=>$value['gorder']+1));
						}
					}
					else{
						foreach($order_arr as $value){
							$db->update("gid = ".$value['gid'], array("gorder"=>$value['gorder']-1));
						}
					}
					$db->update("gid = ".$gid, array("gorder"=>$updateOrder));
					
					echo ceil($newOrder/10);exit;
				}
				exit;
			}

}
?>
