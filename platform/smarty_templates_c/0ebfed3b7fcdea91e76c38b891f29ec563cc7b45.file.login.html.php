<?php /* Smarty version Smarty-3.0.6, created on 2012-06-25 17:51:42
         compiled from "D:\FML\EMP\platform\Template\login.html" */ ?>
<?php /*%%SmartyHeaderCode:303274fe834aec29bc7-11389374%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '0ebfed3b7fcdea91e76c38b891f29ec563cc7b45' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\login.html',
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
  'nocache_hash' => '303274fe834aec29bc7-11389374',
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
	
<script language="javascript">
	/**
	 *提交表单验证
	 ***/
	function formCheck(){
		if($("input[name=acname]").val() == ''){
			$(".msg").html('Please fill in username');
			return false;
		}else if($("input[name=acpasswd]").val() == ''){
			$(".msg").html('Please fill in password');
			return false;
		}else if(checkPasswd() == 0){
			$(".msg").html('Please fill in correct username and password.');
			return false;
		}else if(checkPasswd() == 2){
			$(".msg").html('Your account is not activated.');
			return false;
		}/*else if(verificationCheck() != 1){
			$(".msg").html('Verification Code is wrong');
			return false;
		}*/else	return true;
	}
	
	/**
	 *
	 ***/
	function verificationCheck(){
		var result = 0;
		$.ajax({type:"get",
				async:false,
				url:"?c=log&a=checkVerificationCode",
				data:"yzm="+$("input[name=verification]").val(),
				success:function(data){result = data; }
		})	
		return result;
	}
	
	/**
	 *检验密码
	 ***/
	function checkPasswd(){
		var result = 0;
		$.ajax({type:"get",
				async:false,
				url:"?c=log&a=checkPasswd",
				data:"acname="+$("input[name=acname]").val()+"&acpasswd="+$("input[name=acpasswd]").val(),
				success:function(data){result = data; }
		})	
		return result;
	}
</script>

</head>
<body>
	

	<div id="container">
	
		<?php $_template = new Smarty_Internal_Template("Header.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '303274fe834aec29bc7-11389374';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-06-25 17:51:43
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
					<h2 class="title">Login Page</h2>					
				</div>
				
				<div class="content_p">
				
					<form action="?c=log&a=dologin" onsubmit="return formCheck()" method="post">
						
						<table class="form">						
							<tr>							
								<th>Username</th>
								<td><input type="text" name="acname" value=""></td>
							</tr>
							
							<tr>
								<th>Password</th>
								<td><input type="password" name="acpasswd" value="">
									<a href="?c=log&a=forgotpassword" style="display: block; margin-top: 5px;">Forgot Password</a>
								</td>
							</tr>
							
							<!--tr>
								<th>Verification code</th>
								<td><input type="text" name="verification" value=""></td>
							</tr>
							<tr>
								<th></th>
								<td><img src="?c=log&a=getImg" alt="" onmousedown="this.src='?c=log&a=getImg&t='+(new Date().getTime());" />&nbsp;&nbsp;
									<a href="javascript:;" onclick="$(this).prev().attr('src', '?c=log&a=getImg&t='+(new Date().getTime()))">看不清,换一张</a>
								</td>
							</tr-->
							<tr>
								<th>&nbsp;</th>						
								<td><input type="submit" value="Login" class="input_btn"/>
									<input type="reset" value="Reset" class="input_btn" />
								</td>
							</tr>	
							
						</table>	
						
						<p class="msg"></p>
						
					</form>
				
				</div>				
			</div>
		</div>			
		<div class="clear"></div>		
		<?php $_template = new Smarty_Internal_Template("Footer.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '303274fe834aec29bc7-11389374';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-06-25 17:51:43
         compiled from "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<div id="divFooter">

	<p id="footer"><a href="">Contact Us</a><a href="">Term of Use</a><a href="" class="last">Privacy Policy</a></p>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
	
	</div>

</body>
</html>