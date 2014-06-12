<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:00
         compiled from "D:\FML\EMP\platform\Template\admin/app_list.html" */ ?>
<?php /*%%SmartyHeaderCode:2198501ba774e1bcc6-91871887%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '24aa28dcfb2ddc68da62300d85cba2307eef4c5e' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\admin/app_list.html',
      1 => 1339481599,
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
    '5dbcde1c7033cac0cda9764dbd2019082ec70a9d' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Footer.html',
      1 => 1339481601,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '2198501ba774e1bcc6-91871887',
  'function' => 
  array (
  ),
  'has_nocache_code' => false,
)); /*/%%SmartyHeaderCode%%*/?>
<?php if (!is_callable('smarty_modifier_mb_truncate')) include 'D:\FML\EMP\platform\TFCore\Smarty\plugins\modifier.mb_truncate.php';
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
	
<script language="javascript" src="js/admin.js"></script>

</head>
<body>
	

	<div id="container">	
		<?php $_template = new Smarty_Internal_Template("Header.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '2198501ba774e1bcc6-91871887';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:01
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
		<div id="divBody">
			<div class="content">
				<div class="breadcrumb">
					<p class="nav"><!-- <a href="?c=admin_user">Admin Homepage</a> >  --><span><a href="?c=admin_user">App Management</a></span> > <span> App List</span></p>
					<h2 class="title">App List</h2>					
				</div>				
				<div class="search">
					<!--h3><a href="javascript:void();" onclick="toggleShow($(this));"><span>-</span>&nbsp;Search</a></h3-->
					<form action="" method="post">
					<p style="margin: 5px 0;"><label>App Name/App Creator:</label><input type="text" name="keyword" value="<?php echo $_smarty_tpl->getVariable('keyword')->value;?>
"/>&nbsp;&nbsp;&nbsp;
					    <label>App Status:</label><select style="width:130px;" name="astatus">
							<option value="">---Choose---
							<option value="1"<?php if ($_smarty_tpl->getVariable('astatus')->value==1){?> selected="selected"<?php }?>>Submitted
							<option value="3"<?php if ($_smarty_tpl->getVariable('astatus')->value==3){?> selected="selected"<?php }?>>Approved
							<option value="-1"<?php if ($_smarty_tpl->getVariable('astatus')->value==-1){?> selected="selected"<?php }?>>Disapproved
							<option value="0"<?php if ($_smarty_tpl->getVariable('astatus')->value==0&&$_smarty_tpl->getVariable('astatus')->value!=''){?> selected="selected"<?php }?>>Draft
							<option value="2"<?php if ($_smarty_tpl->getVariable('astatus')->value==2){?> selected="selected"<?php }?>>Processed
						</select>&nbsp;&nbsp;&nbsp;
					<input type="submit" value="Search" style="width:100px;"/></p/>
					<!--span>(The keywords entered here should be based on username or email.)</span-->
					</form>
				</div>
				<div>
					<p class="btn"><a class="iframe button" href="?c=export&a=exportAllApp">Export All Apps</a></p>
				</div>
				
				
				<div class="content_p">
					<table class="list">
						<tr>
							<th>ID</th>
							<th>App Name</th>
							<th>App Creator</th>
							<th>Status </th>
							<th>Date Submitted</th>
							<th>Date Approved</th>
							<th>Action</th>
							<!-- <th>Preview</th> -->
							<th>Online link</th>
						</tr>
						<?php if ($_smarty_tpl->getVariable('applist')->value!=''){?>
						<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_smarty_tpl->tpl_vars['key'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('applist')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
 $_smarty_tpl->tpl_vars['key']->value = $_smarty_tpl->tpl_vars['item']->key;
?>
						<tr>							
							<td align="center"><?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
</td>
							<td><?php echo $_smarty_tpl->tpl_vars['item']->value['aname'];?>
</td>
							<td><?php echo $_smarty_tpl->tpl_vars['item']->value['acname'];?>
</td>
							<td>
							<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==1){?>Submitted<?php }?>
							<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==2){?>Processed<?php }?>
							<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==3){?>Approved<?php }?>
							<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==0){?>Draft<?php }?>
							<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==-1){?>Disapproved<?php }?>
							</td>
							<!-- <td><?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==1){?><?php echo $_smarty_tpl->tpl_vars['item']->value['asumbit_time'];?>
<?php }?></td> -->
							<td><?php echo $_smarty_tpl->tpl_vars['item']->value['asumbit_time'];?>
</td>
							<td><?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==3){?><?php echo $_smarty_tpl->tpl_vars['item']->value['aapproved_time'];?>
<?php }?></td>
							<td><a href="?c=admin_app&a=edit_app&aid=<?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
" class="button">Change Status</a>&nbsp;&nbsp;<a href="?c=main&a=create&step=basic_info&aid=<?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
&do=edit" class="button<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==1||$_smarty_tpl->tpl_vars['item']->value['astatus']==2||$_smarty_tpl->tpl_vars['item']->value['astatus']==-1){?> disabled<?php }?>">Edit</a></td>
							<!-- <td><a href="">View</td> -->
							<td><?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==3){?><?php echo smarty_modifier_mb_truncate($_smarty_tpl->tpl_vars['item']->value['aonline_link'],30,"...");?>
<?php }?></td>							
						</tr>
						<?php }} else { ?>
						<tr>							
							<td colspan="9" style="text-align: center;">There is no associated results now.</td>
						</tr>
						<?php } ?>						
						<?php }?>
					</table>	
					<div class="page"><div class="pageshow"><?php echo $_smarty_tpl->getVariable('show_page')->value;?>
</div></div>
				</div>
				<!-- <p class="btn" style="margin-top:20px;"><a class="iframe button" href="">< Prev</a>&nbsp;&nbsp;<a class="iframe button" href="">Next ></a></p> -->
				
			</div>
		
		</div>
		<?php $_template = new Smarty_Internal_Template("Footer.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '2198501ba774e1bcc6-91871887';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:01
         compiled from "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<div id="divFooter">

	<p id="footer"><a href="">Contact Us</a><a href="">Term of Use</a><a href="" class="last">Privacy Policy</a></p>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
		
	</div>
	

</body>
</html>