<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:10
         compiled from "D:\FML\EMP\platform\Template\customize_feature.html" */ ?>
<?php /*%%SmartyHeaderCode:11300501ba77e284714-18243148%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '0114df1782a95ca3061a94c99d736813a268b209' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\customize_feature.html',
      1 => 1339481601,
      2 => 'file',
    ),
    'b8e3c367ca2de862fdb4be2192f4c4c39b907f2e' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Main.Master.html',
      1 => 1339481601,
      2 => 'file',
    ),
    'aa67b1c828ad0707da8a0ce3062f2710a08ebc29' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Header.html',
      1 => 1339481601,
      2 => 'file',
    ),
    '104a3d2989211e27388d9aeb552db5a0bc92afc6' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Course.html',
      1 => 1339481601,
      2 => 'file',
    ),
    'e20cb9bbf2f0a55c77b33106d3bbef13858db5fa' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Tab.html',
      1 => 1339742721,
      2 => 'file',
    ),
    '5dbcde1c7033cac0cda9764dbd2019082ec70a9d' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Footer.html',
      1 => 1339481601,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '11300501ba77e284714-18243148',
  'function' => 
  array (
  ),
  'has_nocache_code' => false,
)); /*/%%SmartyHeaderCode%%*/?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EMP</title>
	<link rel="stylesheet" href="css/main.css">
	<link rel="stylesheet" href="css/admin.css">
	<script src="js/jquery-1.7.1.js"></script>
	<script src="js/script.js" language="javascript"></script>
	<script>
		$().ready(function(){
			$("a.disabled").click(function(){return false;})
		})
		/*function chick(){
			alert('Your app is submitting to Android Market, you can only edit it when the first version is approved by Android Market and published online.');
			return false;
		}*/
	</script>
	
	<link rel="stylesheet" href="css/tab.css">
	<!-- pop up -->
	<link rel="stylesheet" href="css/colorbox.css" />
	<script src="js/jquery.colorbox.js"></script>
	
	<!-- sortable start -->
	<link rel="stylesheet" href="js/themes/base/jquery.ui.all.css">
	<script src="js/ui/jquery.ui.core.js"></script>
	<script src="js/ui/jquery.ui.widget.js"></script>
	<script src="js/ui/jquery.ui.mouse.js"></script>
	<script src="js/ui/jquery.ui.sortable.js"></script>
	<link rel="stylesheet" href="css/demos.css">
	<!-- sortable end -->
	
	<!-- uploadify start -->
	<link href="common/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="common/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="common/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<!-- uploadify end -->
	
	<!--htmleditor start-->
	<script charset="utf-8" src="common/kindeditor/kindeditor-min.js"></script>
	<script charset="utf-8" src="common/kindeditor/lang/zh_CN.js"></script>
	<!--htmleditor end-->
	<script type="text/javascript">
	$().ready(function(){
		if($('.list tr').length < 3){
			$('.list tr td .order').hide();
			$('.list tr td .go').hide();
		}
	})
	
	$(function() {
		// sortable
		$("#sortable li").hover(function(){$(this).find("a.close").show();},function(){$(this).find("a.close").hide();});
	
		$( "#sortable" ).sortable({items: "li:not(.ui-state-disabled)", opacity: 0.6, cursor: 'move', border: "1px dashed #D3D3D3",update: updateSort});
		$( "#sortable" ).disableSelection();
		
		// colorbox
		$(".iframe").colorbox({iframe:true});
		
		//change order go button
		$('.go').bind('click',function(){goSort($(this))});
		
		/***下一页按钮***/
		$(".next").hover(function(){
			$("#draw").html("Click here to next step.");
		},function(){
			$("#draw").html("");
		})
		
		$(".back").hover(function(){
			$("#draw").html("Click here to previous step.");
		},function(){
			$("#draw").html("");
		})
	})
	
	/**
	 *文本框
	 ***/
	KindEditor.ready(function(K) {
		editor = K.create('.description', {
			width : '500px',
			height : '200px',
			allowFileManager : true,
			afterChange : function() {
				K('input[name=description_word_count]').val(this.count('text'));
			}
		});
	})
	function updateSort(){
		var tab_arr = new Array();
		$("li:not(.ui-state-disabled)").each(function(){
			tab_arr.push($(this).attr("alt"));
		})
		$.get("?c=tab&a=sortTab",{tab_arr:tab_arr,aid:<?php echo $_smarty_tpl->getVariable('aid')->value;?>
});
	}
	
	/**
	 *删除Feature
	 ***/
	function deleteTab(tid, ob){
		if(confirm("Are you sure to delete this feature?")){
			$.get("?c=tab&a=changeStatus",{tid:tid},function(data){
				ob.parent().remove();
			})
		}		
	}
	
	/**
	 *改变order
	 ***/
	function goSort(ob){
		$('.go').unbind();
		var parent = ob.parent();
		var oldOrder = parent.find(".number").text();
		var newOrder = parent.find("input[name=order]").val();
		if(oldOrder!=newOrder && newOrder != '' && 0 < newOrder && newOrder <= <?php echo (($tmp = @$_smarty_tpl->getVariable('count')->value)===null||$tmp==='' ? 0 : $tmp);?>
){
			$.get("?c=<?php echo $_smarty_tpl->getVariable('app_tab_info')->value['fnname'];?>
&a=goSort",
				{oldOrder:oldOrder, newOrder:newOrder, id:parent.find("input[name=id]").val(), tid:parent.find("input[name=tid]").val(), aid: <?php echo $_smarty_tpl->getVariable('aid')->value;?>
},
				function(data){
					//alert(data);
					window.location="?c=main&a=create&step=customize_feature&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
&tid="+parent.find("input[name=tid]").val()+"&page="+data;
				}
			);
		}
		else if( newOrder <= 0 ||  newOrder > <?php echo (($tmp = @$_smarty_tpl->getVariable('count')->value)===null||$tmp==='' ? 0 : $tmp);?>
 ){
			alert("Order number should be between 1 to <?php echo $_smarty_tpl->getVariable('count')->value;?>
.");
			$('.go').bind('click',function(){goSort($(this))});
			return false;
		}
		else{
			alert("Unknown error.");
			$('.go').bind('click',function(){goSort($(this))});
			return false;
		}
	}
	</script>

</head>
<body>
	

	<div id="container">
	
		<?php $_template = new Smarty_Internal_Template("Header.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '11300501ba77e284714-18243148';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:10
         compiled from "D:\FML\EMP\platform\Template\Header.html" */ ?>
<div id="divHeader">

	<div id="logo">	
	
		<h1><a href="index.php">EMP</a></h1>
	
	</div>
	
	<div id="h_content">
		<?php if ($_smarty_tpl->getVariable('username')->value!=''){?>
		<div id="login">
				
			<p>
				<span>Hi, <?php echo $_smarty_tpl->getVariable('username')->value;?>
</span>&nbsp;|&nbsp;
				<a href="?c=log&a=logout">Logout</a>
				<?php if ($_smarty_tpl->getVariable('path')->value=='admin'){?>&nbsp;|&nbsp;
				<?php if ($_smarty_tpl->getVariable('new_count')->value==0){?>
				<span>New Submitted Apps&nbsp;(0)</span>
				<?php }else{ ?>
				<a href="?c=admin_app&ord=submit">New Submitted Apps&nbsp;(<?php echo $_smarty_tpl->getVariable('new_count')->value;?>
)</a>
				<?php }?>
				<?php }?>
				
				<?php if ($_smarty_tpl->getVariable('utype')->value==1){?>&nbsp;|&nbsp;
				<?php if ($_smarty_tpl->getVariable('path')->value=='admin'){?>
					<a href="?c=main">Go to EMP Site</a>
				<?php }else{ ?>
					<a href="?c=admin">Back to Admin View</a>
				<?php }?>
				<?php }?>
			</p>
			
		</div>
		
		<div id="menu">
			
			<p>
				<?php if ($_smarty_tpl->getVariable('path')->value=='admin'){?>
				<a href="?c=admin_user">User Management</a>
				<a href="?c=admin_app" class="last">App Management</a>
				<?php }else{ ?>
				<a href="?c=main">My Apps</a>
				<a href="?c=main&a=create&step=basic_info">Building App</a>
				<a href="?c=account" class="last">My Account</a>
				<?php }?>
			
			</p>
		
		</div>
		<?php }?>
		<!-- <?php if ($_smarty_tpl->getVariable('utype')->value==1){?>
		<div id="switch">
			<?php if ($_smarty_tpl->getVariable('path')->value=='admin'){?>
			<a href="?c=main">Go to EMP Site</a>
			<?php }else{ ?>
			<a href="?c=admin">Back to Admin View</a>
			<?php }?>
		</div>
		<?php }?> -->
	</div>
	
	<div class="clear"></div>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Header.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
		<div class="breadcrumb">
			<p class="nav"><span style="font-size:18px;font-weight:bold;">Step 4 -- Customize Feature</span></p>
		</div>
	
		<?php $_template = new Smarty_Internal_Template("Course.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '11300501ba77e284714-18243148';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:10
         compiled from "D:\FML\EMP\platform\Template\Course.html" */ ?>
		<div id="divCourse">

			<div id="course">
			
				<a href="?c=main&a=create&step=basic_info&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
&do=edit"<?php if ($_smarty_tpl->getVariable('step')->value=='basic info'){?> class="select"<?php }?>>1.Add App 
Information</a>
				<a href="?c=main&a=create&step=select_template&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
"<?php if ($_smarty_tpl->getVariable('step')->value=='select template'){?> class="select"<?php }?>>2.Select Template</a>
				<a href="?c=main&a=create&step=add_feature&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
"<?php if ($_smarty_tpl->getVariable('step')->value=='add feature'){?> class="select"<?php }?>>3.Add Feature</a>
				<a href="?c=main&a=create&step=customize_feature&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
"<?php if ($_smarty_tpl->getVariable('step')->value=='customize feature'){?> class="select"<?php }?>>4.Customize Feature</a>
				<a href="?c=main&a=create&step=customize_appearance&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
"<?php if ($_smarty_tpl->getVariable('step')->value=='customize appearance'){?> class="select"<?php }?>>5.Customize Appearance</a>
				<!-- <a href="?c=main&a=create&step=customize_preview&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
"<?php if ($_smarty_tpl->getVariable('step')->value=='customize preview'){?> class="select"<?php }?>>6.Preview</a> -->
				<a href="?c=main&a=create&step=customize_publish&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
"<?php if ($_smarty_tpl->getVariable('step')->value=='customize publish'){?> class="select"<?php }?>>6.Publish</a>
					
				<div class="clear"></div>
			
			</div>
			<div class="clear"></div>
		</div>
<?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Course.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
		
		<div id="divBody">
			
			<?php $_template = new Smarty_Internal_Template("Tab.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '11300501ba77e284714-18243148';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:10
         compiled from "D:\FML\EMP\platform\Template\Tab.html" */ ?>
<?php if (!is_callable('smarty_modifier_mb_truncate')) include 'D:\FML\EMP\platform\TFCore\Smarty\plugins\modifier.mb_truncate.php';
?>			<div class="tabList">
				<div class="scroll">
					<ul id="sortable">
						<!--li class="ui-state-default<?php if ($_smarty_tpl->getVariable('tid')->value==0){?> selected<?php }?> ui-state-disabled"><a href="?c=main&a=create&step=customize_feature&aid=<?php echo $_smarty_tpl->getVariable('app_info')->value['aid'];?>
">Loading Page</a></li-->
						<?php if ($_smarty_tpl->getVariable('app_tab_list')->value!=''){?>
							<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('app_tab_list')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
?>
						<li class="ui-state-default<?php if ($_smarty_tpl->getVariable('tid')->value==$_smarty_tpl->tpl_vars['item']->value['tid']){?> selected<?php }?><?php if ($_smarty_tpl->tpl_vars['item']->value['tdefault']==-1){?> ui-state-disabled<?php }?>" alt="<?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
"><a href="?c=main&a=create&step=customize_feature&aid=<?php echo $_smarty_tpl->getVariable('app_info')->value['aid'];?>
&tid=<?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
" title="<?php echo $_smarty_tpl->tpl_vars['item']->value['tname'];?>
"><?php echo smarty_modifier_mb_truncate($_smarty_tpl->tpl_vars['item']->value['tname'],10,"...");?>
</a><?php if ($_smarty_tpl->tpl_vars['item']->value['tdefault']!=-1){?><a href="javascript:void(0);" onclick="deleteTab(<?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
, $(this));" class="close">x</a><?php }?></li>
							<?php }} ?>
						<?php }?>
						
					</ul><br/><br/><br/>
				</div>
				<ul>You can drag and drop the tab to reorder. The new order can also be reflected as the same on the <a href="?c=main&a=create&step=add_feature&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
">Feature List </a>page.</ul>
			</div>
			<p></p><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Tab.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
			
			<div class="content">
				<!-- <div class="breadcrumb">
					<p class="nav"><a href="index.php">Home</a> > <a href="index.php">My Apps</a> > <span><?php echo (($tmp = @$_smarty_tpl->getVariable('app_tab_info')->value['tname'])===null||$tmp==='' ? 'Loadding Page' : $tmp);?>
</span></p>
					<h2 class="title"><?php echo ((mb_detect_encoding((($tmp = @$_smarty_tpl->getVariable('app_tab_info')->value['tname'])===null||$tmp==='' ? 'Home' : $tmp), 'UTF-8, ISO-8859-1') === 'UTF-8') ? mb_strtoupper((($tmp = @$_smarty_tpl->getVariable('app_tab_info')->value['tname'])===null||$tmp==='' ? 'Home' : $tmp),SMARTY_RESOURCE_CHAR_SET) : strtoupper((($tmp = @$_smarty_tpl->getVariable('app_tab_info')->value['tname'])===null||$tmp==='' ? 'Home' : $tmp)));?>
</h2> 					
				</div>-->
				
				<div class="content_p load">
					<?php $_template = new Smarty_Internal_Template("tabpage/tab_".((($tmp = @$_smarty_tpl->getVariable('app_tab_info')->value['fnname'])===null||$tmp==='' ? "page" : $tmp)).".html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
 echo $_template->getRenderedTemplate();?><?php $_template->updateParentVariables(0);?><?php unset($_template);?>
				</div>
				
			</div>
			<p style="text-align: right">
					<a href="?c=main&a=create&step=add_feature&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
" class="button back">Back</a>
					<a href="?c=main&a=create&step=customize_appearance&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
" class="button next">Next</a>
					
					<div class="clear"></div>
			</p>
			<p id="draw" style="text-align: right; height:15px;"></p>	
		</div>	
		
		<div class="clear"></div>
		
		<?php $_template = new Smarty_Internal_Template("Footer.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '11300501ba77e284714-18243148';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:10
         compiled from "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<div id="divFooter">

	<p id="footer"><a href="">Contact Us</a><a href="">Term of Use</a><a href="" class="last">Privacy Policy</a></p>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
	
	</div>

</body>
</html>