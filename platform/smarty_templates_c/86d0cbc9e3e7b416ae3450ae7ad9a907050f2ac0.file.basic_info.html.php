<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:03
         compiled from "D:\FML\EMP\platform\Template\basic_info.html" */ ?>
<?php /*%%SmartyHeaderCode:21171501ba7770e7719-14028159%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '86d0cbc9e3e7b416ae3450ae7ad9a907050f2ac0' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\basic_info.html',
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
  'nocache_hash' => '21171501ba7770e7719-14028159',
  'function' => 
  array (
  ),
  'has_nocache_code' => false,
)); /*/%%SmartyHeaderCode%%*/?>
<?php if (!is_callable('smarty_modifier_capitalize')) include 'D:\FML\EMP\platform\TFCore\Smarty\plugins\modifier.capitalize.php';
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
	
	<!-- uploadify start -->
	<link href="common/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="common/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="common/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<!-- uploadify end -->
	
	<!-- kindeditor start -->
	<script charset="utf-8" src="common/kindeditor/kindeditor-min.js"></script>
	<script charset="utf-8" src="common/kindeditor/lang/zh_CN.js"></script>
	<!-- kindeditor end -->
	
	<script charset="utf-8">
		$().ready(function(){
            /***文件上传1***/		
			$('#file_upload').uploadify({
				'uploader'  : 'common/uploadify/uploadify.swf',
				'script'    : 'common/uploadify/uploadify.php',
				'cancelImg' : 'common/uploadify/cancel.png',
				'folder'    : 'uploads/images',
				'fileExt'   : '*.png',
				'fileDesc'  : 'Web Image Files(*.png)',
				'scriptData': {'width':48,'height':48,'mark':'<=','stay':'image'},
				'auto'      : true,
				'onComplete'  : function(event, ID, fileObj, response, data) {
					if(response == '-1'){
						$('#file_upload').uploadifyClearQueue();
						alert('The image should be less than 1 MB in PNG and 48px wide by 48px high');return false;
					}else{
						$('#icon_image').html('<img src="uploads/images/'+response.split("/").slice(-1)+'" width="80"/>');
						$('input[name=aicon]').val("uploads/images/"+response.split("/").slice(-1));					
					}
				}
			});
            /***文件上传2***/			
			$('#file_upload2').uploadify({
				'uploader'  : 'common/uploadify/uploadify.swf',
				'script'    : 'common/uploadify/uploadify.php',
				'cancelImg' : 'common/uploadify/cancel.png',
				'folder'    : 'uploads/images',
				'fileExt'   : '*.png',
				'fileDesc'  : 'Web Image Files(*.png)',
				'scriptData': {'width':320,'height':480,'mark':'<=','stay':'image'},
				'auto'      : true,
				'onComplete'  : function(event, ID, fileObj, response, data) {
					if(response == '-1'){
						$('#file_upload2').uploadifyClearQueue();
						alert('The image should be less than 1 MB in PNG and 320px wide by 480px high');return false;
					}else{
						$('#icon_load').html('<img src="uploads/images/'+response.split("/").slice(-1)+'" width="80"/>');
						$('input[name=aload_image]').val("uploads/images/"+response.split("/").slice(-1));
					}
				}
			});
			
			/*$('input[name=edit]').click(function() {
				$('input[name=submit]').removeAttr("disabled");
				$('input[name=edit]').attr("disabled","disabled");
				$(".cover").hide();
			});*/
			//$(".cover").height($(".form").height());
			
					
			/**
			 *下一页按钮
			 ***/
			$(".next").hover(function(){
				$("#draw").html("Click here to next step.");
			},function(){
				$("#draw").html("");
			})
			
		})
		
		/**
		 *文本框
		 ***/
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="adescription"]', {
				width : '500px',
				height : '200px',
				allowFileManager : true, 
				afterChange : function() {
					K('input[name=description_word_count]').val(this.count('text'));
				}
			});
			/*$(".cover").height($(".form").height());
			$(".cover").mousemove(function(){
			$(this).removeAttr("title").removeAttr("alt").attr("title","aaa").attr("alt","aaa")
			}) */
		});

        /**
		 *验证表单提交
		 ***/		
		function basicInfoForm(){
			if($("input[name=aname]").val() == '') {$(".msg").text('Please fill in the App Name.'); return false; }
			else if($("input[name=aname]").val().length > 13) {$(".msg").text('The App Name must be less than 13 character.'); return false; } 
			else if($("input[name=aicon]").val() == '') {$(".msg").text('Please upload the App Icon.'); return false; } 
			else if($("input[name=aload_image]").val() == '') {$(".msg").text('Please upload the App Loading Image.'); return false; } 
			else if($("input[name=adescription]").val() == '' || $("input[name=description_word_count]").val()<=0) {$(".msg").text('Please fill in "App Description" field.'); return false; } 
			else return true;
		}

	</script>

</head>
<body>
	

	<div id="container">
	
		<?php $_template = new Smarty_Internal_Template("Header.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '21171501ba7770e7719-14028159';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:03
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
			<p></p>
			<!-- <p class="nav"><a href="index.php">Home</a> > <a href="index.php">My Apps</a> > <span>Fill in <?php echo smarty_modifier_capitalize($_smarty_tpl->getVariable('step')->value);?>
</span></p>
			<h2 class="title"><?php echo ((mb_detect_encoding($_smarty_tpl->getVariable('step')->value, 'UTF-8, ISO-8859-1') === 'UTF-8') ? mb_strtoupper($_smarty_tpl->getVariable('step')->value,SMARTY_RESOURCE_CHAR_SET) : strtoupper($_smarty_tpl->getVariable('step')->value));?>
</h2> -->	
			<p class="nav"><span style="font-size:18px;font-weight:bold;">Step 1--Add App Information</span></p>
		</div>
		
		<?php $_template = new Smarty_Internal_Template("Course.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '21171501ba7770e7719-14028159';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:03
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
							
				<div class="content_p">
				<p class="nav" style="font-size:16px;margin-top:10px;font-weight:bold;">Basic Information</p>
					<form action="<?php echo $_smarty_tpl->getVariable('action')->value;?>
" method="post" onsubmit="return basicInfoForm();" style="position: relative;">
						<input type="hidden" name="acid" value="<?php echo $_smarty_tpl->getVariable('app_info')->value['acid'];?>
" />
						<table class="form">
							<tr>
								<th>App Name*</th>
								<td>
                                	<?php if ($_smarty_tpl->getVariable('stay')->value=='approved'){?>
                                    <?php echo $_smarty_tpl->getVariable('app_info')->value['aname'];?>

                                    <?php }else{ ?>
                                	<input type="text" name="aname" value="<?php echo $_smarty_tpl->getVariable('app_info')->value['aname'];?>
" /><br><span>(Less than 13 characters.)</span>
                                	<?php }?>
                                </td>
							</tr>
							<tr>
								<th valign="top">App Icon for Android Device/Market*</th>
								<td>
                                	<?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>
									<input type="file" id="file_upload" name="file_upload"/>
                                    <?php }?>
									<input type="hidden" name="aicon" value="<?php echo $_smarty_tpl->getVariable('app_info')->value['aicon'];?>
"/>
									<div id="icon_image">
									<?php if ($_smarty_tpl->getVariable('app_info')->value['aicon']!=''){?>
										<img src="<?php echo $_smarty_tpl->getVariable('app_info')->value['aicon'];?>
" width="80"/>
									<?php }?>
									</div>
                                    <?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>					
									<span>(The image should be less than 1 MB in PNG and 48px wide by 48px high)</span>
                               		<?php }?>
                               </td>
							</tr>
							<tr>
								<th valign="top">Loading Image*</th>
								<td>
                                	<?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>
									<input type="file" id="file_upload2" name="file_upload2"/>
                                    <?php }?>
									<input type="hidden" name="aload_image" value="<?php echo $_smarty_tpl->getVariable('app_info')->value['aload_image'];?>
"/>
                                    <div id="icon_load">
									<?php if ($_smarty_tpl->getVariable('app_info')->value['aload_image']!=''){?>
										<img src="<?php echo $_smarty_tpl->getVariable('app_info')->value['aload_image'];?>
" width="80"/>
									<?php }?>
									</div>
                                    <?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>					
									<span>(The image should be less than 1 MB in PNG and 320px wide by 480px high)</span>
                               		<?php }?>
                               </td>
							</tr>
							<tr>
								<th valign="top">App Description*</th>
								<td>
                                	<?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>
                                	<textarea name="adescription"><?php echo $_smarty_tpl->getVariable('app_info')->value['adescription'];?>
</textarea>
									<input type="hidden" name="description_word_count" value="0" />
                                    <?php }else{ ?>
                                    <div>
                                    	<?php echo $_smarty_tpl->getVariable('app_info')->value['adescription'];?>

                                    </div>
                                    <?php }?>
								</td>
							</tr>
							<tr>
								<td colspan="2"><span style="font-size:14px;font-weight:bold;">Advanced Information</span><br /><?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>(If you have created Sina Weibo account for this app, please fill in the following information got from the creation process. If not, it will get the EMP Sina Weibo account and associated information by default.)<?php }?>
                                </td>
							</tr>
							<tr>
								<th valign="top">Sina Weibo Account</th>
								<td>
                                	<?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>
                                	<textarea style="width:490px;height:50px;" name="weibo_name" ><?php echo $_smarty_tpl->getVariable('app_info')->value['weibo_name'];?>
</textarea>
									<br/>(Enter the name which you create a phone application in Sina Weibo.)
                                    <?php }else{ ?>
                                    <?php echo $_smarty_tpl->getVariable('app_info')->value['weibo_name'];?>

                                    <?php }?>
								</td>
							</tr>
							<tr>
								<th valign="top">Sina Weibo Consumer Key</th>
								<td>
                                	<?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>
									<textarea style="width:490px;height:50px;" name="weibo_key" <?php if ($_smarty_tpl->getVariable('stay')->value=='approved'){?> disabled="disabled"<?php }?>><?php echo $_smarty_tpl->getVariable('app_info')->value['weibo_key'];?>
</textarea>
									<br/>(Enter the Key generated when you create a phone application in Sina Weibo.)
									<?php }else{ ?>
                                    <?php echo $_smarty_tpl->getVariable('app_info')->value['weibo_name'];?>

                                    <?php }?>
                                </td>
							</tr>
							<tr>
								<th valign="top">Sina Weibo Consumer Secret</th>
								<td>
									<?php if ($_smarty_tpl->getVariable('stay')->value=='draft'){?>
                                    <textarea style="width:490px;height:50px;" name="weibo_secret" <?php if ($_smarty_tpl->getVariable('stay')->value=='approved'){?> disabled="disabled"<?php }?>><?php echo $_smarty_tpl->getVariable('app_info')->value['weibo_secret'];?>
</textarea>
									<br/>(Enter the Secret generated when you create a phone application in Sina Weibo.)
									<?php }else{ ?>
                                    <?php echo $_smarty_tpl->getVariable('app_info')->value['weibo_name'];?>

                                    <?php }?>
                                </td>
							</tr>
							<!-- <tr>
								<th>Status*</th>
								<td><p><input type="radio" name="astatus" value="1" class="radio" style="border: 0;"<?php if ($_smarty_tpl->getVariable('app_info')->value['astatus']==1||$_smarty_tpl->getVariable('app_info')->value['astatus']==''){?> checked="checked"<?php }?> /><label>Active</label>
									<input type="radio" name="astatus" value="0" class="radio" style="border: 0;"<?php if ($_smarty_tpl->getVariable('app_info')->value['astatus']==0&&$_smarty_tpl->getVariable('app_info')->value['astatus']!=''){?> checked="checked"<?php }?> /><label>Inactive</label>
								</p></td>
							</tr> -->
							<tr>
								<th></th>
								<td>
                                	<?php if ($_smarty_tpl->getVariable('stay')->value!='approved'){?>
                                	<input type="submit" value="Save" name="submit" class="input_btn"/>
                                    <?php }?>
									<!-- <input type="button" value="Edit" name="edit" class="input_btn"<?php if ($_smarty_tpl->getVariable('app_info')->value['aid']==''){?> style="display:none;"<?php }?> /> -->
								</td>
							</tr>	
							<tr>
								<th></th>
								<td><p class="msg"></p></td>
							</tr>
						</table>
						<!--<div class="cover"<?php if ($_smarty_tpl->getVariable('app_info')->value['aid']==''){?> style="display:none;"<?php }?>></div> -->
						<!-- <div class="cover"<?php if ($_smarty_tpl->getVariable('app_info')->value['aid']==''){?> style="display:none;"<?php }?> alt="aaa" title="bbb"></div> -->
					</form>
				</div>
				<p style="text-align: right">
					<!-- <a href="?c=main" class="button back">Back</a> -->
                    <?php if ($_smarty_tpl->getVariable('app_info')->value['aname']!=''){?>
					<a href="?c=main&a=create&step=select_template&aid=<?php echo $_smarty_tpl->getVariable('aid')->value;?>
" class="button next">Next</a>
                    <?php }?>
					<div class="clear"></div>
				</p>
				<p id="draw" style="text-align: right; height:15px;"></p>		
			</div>

		</div>	
		
		<div class="clear"></div>
		
		<?php $_template = new Smarty_Internal_Template("Footer.html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
$_template->properties['nocache_hash']  = '21171501ba7770e7719-14028159';
$_tpl_stack[] = $_smarty_tpl; $_smarty_tpl = $_template;?>
<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:03
         compiled from "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<div id="divFooter">

	<p id="footer"><a href="">Contact Us</a><a href="">Term of Use</a><a href="" class="last">Privacy Policy</a></p>

</div><?php $_smarty_tpl->updateParentVariables(0);?>
<?php /*  End of included template "D:\FML\EMP\platform\Template\Footer.html" */ ?>
<?php $_smarty_tpl = array_pop($_tpl_stack);?><?php unset($_template);?>
	
	</div>

</body>
</html>