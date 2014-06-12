<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:10
         compiled from "D:\FML\EMP\platform\Template\tabpage/tab_page.html" */ ?>
<?php /*%%SmartyHeaderCode:27567501ba77e7ceb95-19935440%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'be5689574dc6ab9a75833145b34a17ca92de38a3' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\tabpage/tab_page.html',
      1 => 1339481600,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '27567501ba77e7ceb95-19935440',
  'function' => 
  array (
  ),
  'has_nocache_code' => false,
)); /*/%%SmartyHeaderCode%%*/?>
<script language="javascript">
	$().ready(function(){
		$('#file_upload').uploadify({
			'uploader'  : 'common/uploadify/uploadify.swf',
			'script'    : 'common/uploadify/uploadify.php',
			'cancelImg' : 'common/uploadify/cancel.png',
			'folder'    : 'uploads/images',
			'fileExt'   : '*.jpg;*.png',
			'fileDesc'  : 'Web Image Files(*.jpg;*.png)',
			'scriptData': {'width':320,'height':480,'mark':'<=','stay':'image'},
			'auto'      : true,
			'onComplete'  : function(event, ID, fileObj, response, data) {
				if(response == '-1'){
						$('#file_upload').uploadifyClearQueue();
						alert('The image should be less than 1 MB in PNG and 320px wide by 480px high');return false;
					}else{
					$('#icon_image').html('<img src="uploads/images/'+response.split("/").slice(-1)+'" width="80"/>');
					$('input[name=himage]').val("uploads/images/"+response.split("/").slice(-1));					
				}
			}
		});
		
		$('input[name=edit]').click(function() {
			$('input[name=submit]').removeAttr("disabled");
			$('input[name=edit]').attr("disabled","disabled");
		});

		if($("input[name=htemplate]:checked").val() == 0) {
			$("#background_image").addClass("hide");
			$(".required").show();
		}
		
		$("input[name=htemplate]").change(function(){
			if($("input[name=htemplate][value='0']").attr("checked") != "checked") {
				$("#background_image").removeClass("hide"); $(".required").hide();}
			else {$("#background_image").addClass("hide"); $(".required").show();}
		})
	})
	
	/**
	 *提交表单验证
	 ***/
	function pageForm(){
		if($("input[name=htemplate]:checked").length<1) {$(".msg").text('Please select Layout Type.'); return false; }
		else if($("input[name=himage]").val() == '' && $("#background_image").css("display")!="none") {
			$(".msg").text('Please upload Background Image.'); return false; }
		else if($("#background_image").css("display")=="none" && $("input[name=description_word_count]").val()<=0){ $(".msg").text('Please fill in Html Editor.'); return false;}
		else return true;
	}
</script>
<div style="font-size:14px;">Layout Type</div>

<div class="app_template">
<form action="?c=page&a=savePage&tid=<?php echo $_smarty_tpl->getVariable('tid')->value;?>
" onsubmit="return pageForm();" method="post">
	<table width="100%">
		<tr>
			<td width="20%">
				<input type="radio" name="htemplate" class="radio" value="0"<?php if ($_smarty_tpl->getVariable('page_info')->value['htemplate']==0){?> checked="checked"<?php }?> />1.Full Screen HTML Editor<br />
				<img src="images/layout01.jpg" alt="" />
			</td>
			<td width="20%">
				<input type="radio" name="htemplate" class="radio" value="1"<?php if ($_smarty_tpl->getVariable('page_info')->value['htemplate']==1){?> checked="checked"<?php }?> />2.Left Column Text Area<br />
				<img src="images/layout01.jpg" alt="" />
			</td>
			<td width="20%">
				<input type="radio" name="htemplate" class="radio" value="2"<?php if ($_smarty_tpl->getVariable('page_info')->value['htemplate']==2){?> checked="checked"<?php }?> />3.Top Text Area<br />
				<img src="images/layout01.jpg" alt="" />
			</td>
			<td width="20%">
				<input type="radio" name="htemplate" class="radio" value="3"<?php if ($_smarty_tpl->getVariable('page_info')->value['htemplate']==3){?> checked="checked"<?php }?> />4.Right Column Text Area<br />
				<img src="images/layout01.jpg" alt="" />
			</td>
			<td width="20%">
				<input type="radio" name="htemplate" class="radio" value="4"<?php if ($_smarty_tpl->getVariable('page_info')->value['htemplate']==4){?> checked="checked"<?php }?> />5.Bottom Text Area<br />
				<img src="images/layout01.jpg" alt="" />
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table class="form">
					<tr id="background_image">
						<th valign="top">Background Image*</th>
						<td>
							<input type="file" id="file_upload" name="file_upload"/>
							<input type="hidden" name="himage" value="<?php echo $_smarty_tpl->getVariable('page_info')->value['himage'];?>
" />
							<div id="icon_image">
							<?php if ($_smarty_tpl->getVariable('page_info')->value['himage']!=''){?>
								<img src="<?php echo $_smarty_tpl->getVariable('page_info')->value['himage'];?>
" width="80"/>
							<?php }?>
							</div>	
							<span>The image should be less than 1 MB in PNG and 320px wide by 480px high</span>
						</td>
						
					</tr>
					<tr>
						<th valign="top">Html Editor<span class="required" style="display:none;">*</span></th>
						<td><textarea name="hcontent" class="description"><?php echo $_smarty_tpl->getVariable('page_info')->value['hcontent'];?>
</textarea>
							<input type="hidden" name="description_word_count" value="0" />
						</td>
					</tr>
					<tr>
						<th></th>
						<td><input type="submit" value="Save" name="submit" class="input_btn"/>
							<!-- <input type="button" value="Edit" name="edit" class="input_btn"<?php if ($_smarty_tpl->getVariable('tid')->value==''){?> style="display:none;"<?php }?> /> -->
						</td>
					</tr>
					<tr>
						<th></th>
						<td><p class="msg"></p></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</div>

