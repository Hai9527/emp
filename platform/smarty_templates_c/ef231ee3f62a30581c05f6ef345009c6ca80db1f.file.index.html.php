<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:29
         compiled from "D:\FML\EMP\platform\Template\index.html" */ ?>
<?php /*%%SmartyHeaderCode:26892501ba791961140-37593875%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'ef231ee3f62a30581c05f6ef345009c6ca80db1f' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\index.html',
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
    '5dbcde1c7033cac0cda9764dbd2019082ec70a9d' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Footer.html',
      1 => 1339481601,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '26892501ba791961140-37593875',
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
	

</head>
<body>
	

	<div id="container">
	
		<?php $_template = new Smarty_Internal_Template("Header.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '26892501ba791961140-37593875';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:29
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
					<!-- <p class="nav"><a href="index.php">Home</a> > <span>My Apps</span></p> -->
					<!-- <p class="nav">My Apps</p> -->
					<h2 class="title">My Apps</h2>					
				</div>
				
				<div class="content_p">
					
					<p class="btn"><a href="?c=main&a=create&step=basic_info" class="button">Create App</a></p>
					
					<table class="list">
						
						<tr>
							
							<th>ID</th>
							<th>App Name</th>
							<th>Status</th>
							<th>Date Submitted</th>
							<th>Date Approved</th>
							<th>Action</th>
							<th>Comment Feedback</th>
							<th>Online Link</th>
						
						</tr>
					 	<?php if ($_smarty_tpl->getVariable('list')->value!=''){?>
						<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_smarty_tpl->tpl_vars['key'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('list')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
 $_smarty_tpl->tpl_vars['key']->value = $_smarty_tpl->tpl_vars['item']->key;
?>
						<tr>							
							<td align="center"><?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
</td>
							<td><?php echo $_smarty_tpl->tpl_vars['item']->value['aname'];?>
</td>
							<td>
								<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==0){?>Draft
								<?php }elseif($_smarty_tpl->tpl_vars['item']->value['astatus']==1){?>Submitted
								<?php }elseif($_smarty_tpl->tpl_vars['item']->value['astatus']==2){?>Processed
								<?php }elseif($_smarty_tpl->tpl_vars['item']->value['astatus']==3){?>Approved
								<?php }elseif($_smarty_tpl->tpl_vars['item']->value['astatus']==-1){?>Disapproved<?php }?>								
							</td>
							<td><?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']!=0){?><?php echo $_smarty_tpl->tpl_vars['item']->value['asumbit_time'];?>
<?php }?></td>
							<td><?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==3){?><?php echo $_smarty_tpl->tpl_vars['item']->value['aapproved_time'];?>
<?php }?></td>
							<td>

								<a href="?c=main&a=create&step=basic_info&aid=<?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
&do=edit" class="button<?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==1||$_smarty_tpl->tpl_vars['item']->value['astatus']==2||$_smarty_tpl->tpl_vars['item']->value['astatus']==-1){?> disabled<?php }?>">Edit</a>&nbsp;&nbsp;

								<!-- <a <?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==1){?>href="javascript:void(0);" onclick="chick()"<?php }else{ ?> href="?c=main&a=create&step=basic_info&aid=<?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
&do=edit"<?php }?> class="button">Edit</a>&nbsp;&nbsp;-->
								<a href="?c=application&a=delete&aid=<?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
" onclick="return window.confirm('Are you sure you want to delete this record?')" class="button">Delete</a>&nbsp;&nbsp;
								
							</td>
							<td><a href="?c=comment&aid=<?php echo $_smarty_tpl->tpl_vars['item']->value['aid'];?>
" class="button">View Comment</a></td>						
							<td><?php if ($_smarty_tpl->tpl_vars['item']->value['astatus']==3){?><?php echo smarty_modifier_mb_truncate($_smarty_tpl->tpl_vars['item']->value['aonline_link'],30,"...");?>
<?php }?></td>						
						</tr>
						<?php }} else { ?>
						<tr>							
							<td colspan="7" style="text-align: center;">There is no app currently.</td>
						</tr>
						<?php } ?>						
						<?php }?>				
					</table>
					<div class="page"><div class="pageshow"><?php echo $_smarty_tpl->getVariable('show_page')->value;?>
</div></div>
				</div>
				
			</div>

		</div>	
		
		<div class="clear"></div>
		
		<?php $_template = new Smarty_Internal_Template("Footer.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '26892501ba791961140-37593875';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:29
         compiled from "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<div id="divFooter">

	<p id="footer"><a href="">Contact Us</a><a href="">Term of Use</a><a href="" class="last">Privacy Policy</a></p>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
	
	</div>

</body>
</html>