	<?php
	class Pages2{
		function show_page($count,$page,$offset)
		{
		   $Page_size = $offset;  //取得每页显示的最大订单数
		   $page_count  = ceil($count/$Page_size);  //计算得出总页数
		 
		   $init=1;
		   $page_len=11;
		   $max_p=$page_count;
		   $pages=$page_count;
		 
		   //判断当前页码
		   $page=(empty($page)||$page<0)?1:$page;
		   $start=$Page_size*($page-1);
		 
		   //分页功能代码
		   $page_len = ($page_len%2)?$page_len:$pagelen+1;  //页码个数
		   $pageoffset = ($page_len-1)/2;  //页码个数左右偏移量
		 
		   $key="";
		   //$key="Total $count Record&nbsp;";
		   //$key.="$page/$pages &nbsp;"; //第几页,Total几页
		   if($page!=1){
				 $key.="<a href=\"javascript:toPage(1);\">First</a> ";        //First
				 $key.="<a href=\"javascript:toPage(".($page-1).");\">Previous</a>"; //Previous
		   }
		   else
		   {
				 $key.="<label>First</label>";//First
				 $key.="<label>Previous</label>";   //Previous
		   }
		   if($pages>$page_len)
		   {
				 //如果当前页小于等于左偏移
				 if($page<=$pageoffset){
					 $init=1;
					 $max_p = $page_len;
			   }
			   else  //如果当前页大于左偏移
			   {   
				   //如果当前页码右偏移超出最大分页数
					 if($page+$pageoffset>=$pages+1){
						  $init = $pages-$page_len+1;
					 }
					 else
					 {
						//左右偏移都存在时的计算
						$init = $page-$pageoffset;
						$max_p = $page+$pageoffset;
					  }
				  }
		   }
		   for($i=$init;$i<=$max_p;$i++)
		   {
			   if($i==$page){$key.='<label>'.$i.'</label>';}
			   else {$key.=" <a href=\"javascript:toPage(".$i.");\">".$i."</a>";}
		   }
		   if($page!=$pages)
		   {
			   $key.=" <a href=\"javascript:toPage(".($page+1).");\">Next</a> ";//Next
			   $key.="<a href=\"javascript:toPage(".$pages.");\">Last</a>"; //Last
		   }
		   else
		   {
			   $key.="<label>Next</label>";   //Next
			   $key.="<label>Last</label>";    //Last
		   }
		   if($page_count<2) $key = '';
		   return $key;
		} 
	}
	?>