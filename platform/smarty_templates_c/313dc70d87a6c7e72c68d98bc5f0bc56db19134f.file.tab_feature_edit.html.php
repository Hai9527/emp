<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:41
         compiled from "D:\FML\EMP\platform\Template\tab_feature_edit.html" */ ?>
<?php /*%%SmartyHeaderCode:14186501ba79d859372-44323708%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '313dc70d87a6c7e72c68d98bc5f0bc56db19134f' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\tab_feature_edit.html',
      1 => 1339481601,
      2 => 'file',
    ),
    'b8e3c367ca2de862fdb4be2192f4c4c39b907f2e' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\Main.Master.html',
      1 => 1339481601,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '14186501ba79d859372-44323708',
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
	
<link rel="stylesheet" href="css/popup.css">
<!-- webcalendar.js-->
<script type="text/javascript" src="js/WebCalendar.js"></script>
<!-- webcalendar.js-->

<!-- uploadify start -->
<link href="common/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="common/uploadify/swfobject.js"></script>
<script type="text/javascript" src="common/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<!-- uploadify end -->

<!--htmleditor start-->
<script charset="utf-8" src="common/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="common/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8">
<!--htmleditor end-->

	//htmleditor
	KindEditor.ready(function(K) {
		if($('#description').length>0)
		editor_description = K.create('#description', {
			width : '500px',
			height : '200px',
			allowFileManager : true,
			afterChange : function() {
				K('input[name=description_word_count]').val(this.count('text'));
			}
		});
		
		if($('#content').length>0)
		editor_content = K.create('#content', {
			width : '500px',
			height : '200px',
			allowFileManager : true, 
			afterChange : function() {
				K('input[name=content_word_count]').val(this.count('text'));
			}
		});
		
		var y = parseInt($(".content_p").height()+180);
		var x = parseInt($(".content_p").width()+150);

		parent.$.colorbox.resize({width:x, height:y});

	})

</script>

</head>
<body>
	

	<div id="container">
			
			<div class="content popcontent">
				
				<div class="content_p">
					<form action="<?php echo $_smarty_tpl->getVariable('action')->value;?>
" onsubmit="return pageEditForm();" method="post">
						<h2 class="popTitle"><?php echo $_smarty_tpl->getVariable('title')->value;?>
</h2>
						<input type="hidden" name="aid" value="<?php echo $_smarty_tpl->getVariable('aid')->value;?>
" />
						<!--input type="hidden" name="ticon" value="" /-->
						<?php $_template = new Smarty_Internal_Template("tabpage/".($_smarty_tpl->getVariable('edit_page')->value).".html", $_smarty_tpl->smarty, $_smarty_tpl, $_smarty_tpl->cache_id, $_smarty_tpl->compile_id, null, null);
 echo $_template->getRenderedTemplate();?><?php $_template->updateParentVariables(0);?><?php unset($_template);?>
						
					</form>
				</div>				
			</div>
	</div>	

</body>
</html>