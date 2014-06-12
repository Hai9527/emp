<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:26:57
         compiled from "D:\FML\EMP\platform\Template\admin/AdminHome.html" */ ?>
<?php /*%%SmartyHeaderCode:16693501ba7717a7288-93250887%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '8265f55bc43b6728401ac5a0a0991f27def5b302' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\admin/AdminHome.html',
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
  'nocache_hash' => '16693501ba7717a7288-93250887',
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
	
<script language="javascript" src="js/admin.js"></script>
<script language="javascript">
function changestatic(ob,acid,statustext){
		if(window.confirm('Are you sure you want to change the status?')){
			//alert("|"+statustext+"|");
			var acstatus = (statustext == "Inactive")?0:1;
			$.get("?c=admin_user&a=change_status",{acid:acid,acstatus:acstatus},
			function(data){ var datahtml = (data == 0)? "Inactive":"Active"; ob.parent().prev().html(datahtml)})
		}
	}
</script>

</head>
<body>
	

	<div id="container">
		<?php $_template = new Smarty_Internal_Template("Header.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '16693501ba7717a7288-93250887';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:26:57
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
					<p class="nav"><!-- <a href="?c=admin_user">Admin Homepage</a> >  --><span><a href="?c=admin_user">User Management</a></span> > <span> User List</span></p>
					<h2 class="title">User List</h2>					
				</div>
				<div class="search">
					<!--h3><a href="javascript:void();" onclick="toggleShow($(this));"><span>-</span>&nbsp;Search</a></h3-->
					<form action="" method="post" >
					<p style="margin: 5px 0;"><label>Username/Email:&nbsp;</label><input type="text" name="keyword" value="<?php echo $_smarty_tpl->getVariable('keyword')->value;?>
" />&nbsp;&nbsp;&nbsp;
						<label>User Type:&nbsp;</label><select style="width:130px;" name="actype">
								<option value="">---choose---
								<option value="1"<?php if ($_smarty_tpl->getVariable('actype')->value==1){?> selected="selected"<?php }?>>Admin
								<option value="0"<?php if ($_smarty_tpl->getVariable('actype')->value==0&&$_smarty_tpl->getVariable('actype')->value!=''){?> selected="selected"<?php }?>>App Creator 
						</select>
						<input type="submit" value="Search" style="width:100px;"/></p/>
						<!--span>(The keywords entered here should be based on username or email.)</span-->
					</p>
					</form>
				</div>
				<div>
					<p class="btn">
						<a class="iframe button" href="?c=admin_user&a=edit_user">Create User</a>&nbsp;&nbsp;
						<a class="iframe button" href="?c=export&a=exportAllUser">Export All Users</a>
					</p>
				</div>
				
				
					<div class="content_p">
						<table class="list">
							<tr>
								<th>ID</th>
								<th>User Name</th>
								<th>Email</th>
								<th>User Type</th>
								<th>Status </th>
								<th>Change Status</th>
								<th>Action</th>
							</tr>
							<?php if ($_smarty_tpl->getVariable('userlist')->value!=''){?>
							<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_smarty_tpl->tpl_vars['key'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('userlist')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
 $_smarty_tpl->tpl_vars['key']->value = $_smarty_tpl->tpl_vars['item']->key;
?>
							<tr>							
								<td align="center"><?php echo $_smarty_tpl->tpl_vars['item']->value['acid'];?>
</td>
								<td><?php echo $_smarty_tpl->tpl_vars['item']->value['acname'];?>
</td>
								<td><?php echo $_smarty_tpl->tpl_vars['item']->value['acemail'];?>
</td>
								<td><?php if ($_smarty_tpl->tpl_vars['item']->value['actype']==0){?>App Creator<?php }elseif($_smarty_tpl->tpl_vars['item']->value['actype']==1){?>Admin<?php }?></td>
								<td><?php if ($_smarty_tpl->tpl_vars['item']->value['acstatus']==0){?>Inactive<?php }elseif($_smarty_tpl->tpl_vars['item']->value['acstatus']==1){?>Active<?php }?></td>
								<td><a class="iframe button" href="javascript:" onclick="changestatic($(this),<?php echo $_smarty_tpl->tpl_vars['item']->value['acid'];?>
,$(this).parent().prev().html())">Change</a></td>
								<td><a href="?c=admin_user&a=edit_user&acid=<?php echo $_smarty_tpl->tpl_vars['item']->value['acid'];?>
" class="button">Edit</a>&nbsp;&nbsp;&nbsp;<a href="?c=admin_user&a=deleteUser&acid=<?php echo $_smarty_tpl->tpl_vars['item']->value['acid'];?>
" onclick="return window.confirm('Are you sure you want to delete this record?')"  class="button">Delete</a></td>
														
							</tr>
							<?php }} else { ?>
							<tr>							
								<td colspan="7" style="text-align:center;">There is no associated results now.</td>
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
$_template->properties['nocache_hash']  = '16693501ba7717a7288-93250887';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:26:57
         compiled from "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<div id="divFooter">

	<p id="footer"><a href="">Contact Us</a><a href="">Term of Use</a><a href="" class="last">Privacy Policy</a></p>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
		
		
	</div>
	

</body>
</html>