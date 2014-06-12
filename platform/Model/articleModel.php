<?php
/**
 * Description of article.model.php
 * @author Animax
 */
class articleModel extends empModel {
    public function __construct() {
        parent::__construct('article');
		$this->dataMatchConfig["artid"] = 'article_id';
		$this->dataMatchConfig["artname"] = 'article_title';
		$this->dataMatchConfig["artcreate_time"] = 'article_updatetime';
		$this->dataMatchConfig["artshort_description"] = 'article_summary';
		$this->dataMatchConfig["artcontent"] = 'article_content';
		$this->dataMatchConfig["artstatus"] = 'article_status';
		$this->dataMatchConfig["tid"] = 'tab_id';
		$this->dataMatchConfig["artcreate_time"] = 'article_time';
    }
	
	
	public function getAllArticle($tid,$artid,$page,$number){
		$where = "1";
		$where .= ($tid!='')?" and tid = $tid":"";
		$where .= ($artid!='')?" and artid = $artid":"";
		return parent::list_data($where,null,$page,$number,'artpublish_time','DESC');
	}	
	
	public function getArticleCount($tid, $eid){
			$where = "1";
			$where .= ($tid!='')?" and tid = $tid":"";
			$where .= ($artid!='')?" and artid = $artid":"";
			return parent::count($where);
	}
}