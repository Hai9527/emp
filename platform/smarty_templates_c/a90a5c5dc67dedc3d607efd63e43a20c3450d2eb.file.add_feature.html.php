<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:26
         compiled from "D:\FML\EMP\platform\Template\add_feature.html" */ ?>
<?php /*%%SmartyHeaderCode:21330501ba78ebc3a79-24392448%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'a90a5c5dc67dedc3d607efd63e43a20c3450d2eb' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\add_feature.html',
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
    '5dbcde1c7033cac0cda9764dbd2019082ec70a9d' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Footer.html',
      1 => 1339481601,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '21330501ba78ebc3a79-24392448',
  'function' => 
  array (
  ),
  'has_nocache_code' => false,
)); /*/%%SmartyHeaderCode%%*/?>
<?php if (!is_callable('smarty_modifier_capitalize')) include 'D:\FML\EMP\platform\TFCore\Smarty\plugins\modifier.capitalize.php';
if (!is_callable('smarty_modifier_mb_truncate')) include 'D:\FML\EMP\platform\TFCore\Smarty\plugins\modifier.mb_truncate.php';
?><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	
	<link rel="stylesheet" href="css/feature.css">
	<link rel="stylesheet" href="css/colorbox.css" />
	<script src="js/jquery.colorbox.js"></script>
	<script language="javascript">
		$(document).ready(function(){
			$(".iframe").colorbox({iframe:true,onClosed:function(){ history.go(0);}});

			
			/** 下一页按钮 **/
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
		});
		

		
		/** 改变order **/
		function goSort(ob){
			var parent = ob.parent();
			var ord1 = parent.find(".number").text();
			var ord2 = parent.find("input[name=ord]").val();
			if(ord1!=ord2 && ord2 != '' && ord2 > 1 && ord2 <= $("tr td .number").length){
				$.get("?c=tab&a=goSort",
					 {ord1:ord1, ord2:ord2, tid:parent.find("input[name=tid]").val(), aid: <?php echo $_smarty_tpl->getVariable('aid')->value;?>
},
					 function(data){
						var current = $(".list tr:eq("+ord1+")");
						var movehtml = current.html();
						if(ord1>ord2){
							$(".list tr:eq("+ord2+")").before("<tr>"+movehtml+"</tr>");
							//$(".list tr:eq("+(parseInt(ord2)-1)+")").before("<tr>"+movehtml+"</tr>");
							current.remove();
						}else{
							$(".list tr:eq("+ord2+")").after("<tr>"+movehtml+"</tr>");
							current.remove();
						}
						for(var i=0; i<$("tr td .number").length; i++){
							$("tr td .number:eq("+i+")").text(i+1);
						}
				});
			}else{
				return false;
			}
		}
	</script>

</head>
<body>
	

	<div id="container">
	
		<?php $_template = new Smarty_Internal_Template("Header.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '21330501ba78ebc3a79-24392448';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:26
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
			<p class="nav"><span style="font-size:18px;font-weight:bold;">Step 3-- Add Feature</span></p>
		</div>
	
		<?php $_template = new Smarty_Internal_Template("Course.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '21330501ba78ebc3a79-24392448';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:26
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
			
			<div class="content">
			
				<div class="breadcrumb">
					<!-- <p class="nav"><a href="index.php">Home</a> > <a href="index.php">My Apps</a> > <span><?php echo smarty_modifier_capitalize($_smarty_tpl->getVariable('step')->value);?>
</span></p>
					<h2 class="title"><?php echo ((mb_detect_encoding($_smarty_tpl->getVariable('step')->value, 'UTF-8, ISO-8859-1') === 'UTF-8') ? mb_strtoupper($_smarty_tpl->getVariable('step')->value,SMARTY_RESOURCE_CHAR_SET) : strtoupper($_smarty_tpl->getVariable('step')->value));?>
</h2> -->					
					<h2 class="title">Add Feature</h2>					
				</div>
				
				<div class="content_p">
				<?php if ($_smarty_tpl->getVariable('function_list')->value!=''){?>	
					<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_smarty_tpl->tpl_vars['key'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('function_list')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
 $_smarty_tpl->tpl_vars['key']->value = $_smarty_tpl->tpl_vars['item']->key;
?>
					<div class="content_p_l <?php if (($_smarty_tpl->tpl_vars['key']->value+1)%2!=0){?>fl<?php }else{ ?>fr<?php }?>">
						<?php  $_smarty_tpl->tpl_vars['childitem'] = new Smarty_Variable;
 $_smarty_tpl->tpl_vars['k'] = new Smarty_Variable;
 $_from = $_smarty_tpl->tpl_vars['item']->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['childitem']->key => $_smarty_tpl->tpl_vars['childitem']->value){
 $_smarty_tpl->tpl_vars['k']->value = $_smarty_tpl->tpl_vars['childitem']->key;
?>
						<div class="list">
							<div class="thumbnail fl"><img src="<?php echo $_smarty_tpl->tpl_vars['childitem']->value['fnicon'];?>
" alt /></div>
							<div class="definition">
								<h4><?php echo $_smarty_tpl->tpl_vars['childitem']->value['fntitle'];?>
<?php if ($_smarty_tpl->tpl_vars['childitem']->value['fndefault']!=-1){?><a href="?c=feature&a=popup&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
&fnid=<?php echo $_smarty_tpl->tpl_vars['childitem']->value['fnid'];?>
" class="iframe button">Add</a><?php }?></h4>
								<p><?php echo $_smarty_tpl->tpl_vars['childitem']->value['fndescription'];?>
</p>
							</div>
							<div class="clear"></div>
						</div>
						<?php }} ?>
					</div>
					<?php }} ?>
					
					<div class="clear"></div>
				<?php }?>	
				</div>
				
				<!-- List table -->
				
				<div class="content_p">
					
					<table class="list" style="background-color:#DDDDDD">
						
						<tr>
							
							<th width="12%">Order</th>
							<th>ID</th>
							<th>Icon</th>
							<th>Name</th>
							<th>Function</th>
							<th>Status</th>
							<th width="15%">Action</th>
						
						</tr>
					<?php if ($_smarty_tpl->getVariable('app_tab_list')->value!=''){?>
						<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_smarty_tpl->tpl_vars['key'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('app_tab_list')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
 $_smarty_tpl->tpl_vars['key']->value = $_smarty_tpl->tpl_vars['item']->key;
?>
						<tr>							
							<td width="12%" align="center"><span class="number"><?php echo $_smarty_tpl->tpl_vars['key']->value+1;?>
</span><?php if ($_smarty_tpl->tpl_vars['item']->value['tdefault']!=-1){?><input type="hidden" value="<?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
" name="tid"><input type="text" name="ord" class="order">&nbsp;&nbsp;<a href="javascript:;" onclick="goSort($(this))" class="button">GO</a><?php }?></td>
							<td align="center"><?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
</td>
							<td align="center" class="icon"><img src="<?php echo $_smarty_tpl->tpl_vars['item']->value['ticon'];?>
" alt /></td>
							<td><?php echo smarty_modifier_mb_truncate($_smarty_tpl->tpl_vars['item']->value['tname'],10,"...");?>
</td>
							<td><?php echo $_smarty_tpl->tpl_vars['item']->value['fntitle'];?>
</td>
							<td>
								<?php if ($_smarty_tpl->tpl_vars['item']->value['tstatus']==0){?>Inactive
								<?php }elseif($_smarty_tpl->tpl_vars['item']->value['tstatus']==1){?>Active
								<?php }?>
							</td>
							<td align="center"><a href="?c=feature&a=popupedit&tid=<?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
" class="iframe button">Edit</a><?php if ($_smarty_tpl->tpl_vars['item']->value['tdefault']!=-1){?>&nbsp;&nbsp;&nbsp;<a href="?c=tab&a=delete&tid=<?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
" class="button" onclick="return window.confirm('Are you sure you want to delete this record?')">Detete</a><?php }?></td>
						</tr>
						<?php }} else { ?>
						<tr>							
							<td colspan="7" style="text-align:center;">search no result.</td>
						</tr>
						<?php } ?>
					<?php }?>
					</table>
				
				</div>
				<p style="text-align: right">
					<a href="?c=main&a=create&step=select_template&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
" class="button back">Back</a>
					<a href="?c=main&a=create&step=customize_feature&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
" class="button next">Next</a>
					
					<div class="clear"></div>
				</p>
				<p id="draw" style="text-align: right; height:15px;"></p>	
			</div>

		</div>	
		
		<div class="clear"></div>
		
		<?php $_template = new Smarty_Internal_Template("Footer.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '21330501ba78ebc3a79-24392448';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:27
         compiled from "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<div id="divFooter">

	<p id="footer"><a href="">Contact Us</a><a href="">Term of Use</a><a href="" class="last">Privacy Policy</a></p>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
	
	</div>

</body>
</html>