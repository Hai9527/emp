<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:41
         compiled from "D:\FML\EMP\platform\Template\tabpage/tab_edit_gallery.html" */ ?>
<?php /*%%SmartyHeaderCode:11729501ba79db54521-94721040%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '6e3bb4497a001b6e058b9b389d27e66596c54032' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\tabpage/tab_edit_gallery.html',
      1 => 1339481600,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '11729501ba79db54521-94721040',
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
			'fileExt'   : '*.png',
			'fileDesc'  : 'Web Image Files(*.png)',
			'scriptData': {'width':210,'height':325,'mark':'<=','stay':'image'},
			'auto'      : true,
			'onComplete'  : function(event, ID, fileObj, response, data) {
					if(response == '-1'){
						$('#file_upload').uploadifyClearQueue();
						alert('The image should be less than 1 MB in PNG and 210px wide by 325px high.');return false;
					}else{
					$('#icon_image').html('<img src="uploads/images/'+response.split("/").slice(-1)+'" width="25" height="25"/>');
					$('input[name=gdefaut_icon]').val("uploads/images/"+response.split("/").slice(-1));
					}
				}
		});

		$('#file_upload3').uploadify({
			'uploader'  : 'common/uploadify/uploadify.swf',
			'script'    : 'common/uploadify/uploadify.php',
			'cancelImg' : 'common/uploadify/cancel.png',
			'folder'    : 'uploads/images',
			'fileExt'   : '*.jpg;*.gif;*.png',
			'fileDesc'  : 'Web Image Files(*.jpg;*.gif;*.png)',
			'scriptData': {'needWatermark':1,'stay':'image'},
			'auto'      : true,
			'multi'		: true,
		    'onSelectOnce'    : function(event,data) {
				if(data.fileCount>5){
					alert("Maximum 5 photos can be uploaded one time.");
					$('#file_upload3').uploadifyClearQueue();
					return false;
				}else {
					$("#file_upload3Queue .uploadifyQueueItem").show();
					return true;
				}
		    },
			'onComplete'  : function(event, ID, fileObj, response, data) {
				$("#file_upload3Queue").prepend('<div class="uploadifyBox new" onmouseover="$(this).find(\'.show\').show()" onmouseout="$(this).find(\'.show\').hide()">'+
													'<img class="uploadifyImg" src="uploads/images/'+response.split("/").slice(-1)+'" />'+
													'<div>'+
														'<p class="uploadifytil"><span>'+fileObj.name.split(".")[0]+'</span><input type="hidden" name="photo[]" value="uploads/images/'+response.split("/").slice(-1)+'" /><input type="hidden" name="pname[]" value="'+fileObj.name.split(".")[0]+'" /></p>'+
														'<p class="uploadifycon"><textarea name="p_description[]" readonly="readonly" >Add Caption</textarea></p>'+
														'<a href="javascript:void(0);" class="delete show" onclick="if(confirm(\'Are you sure to delete this photo?\')){$(this).parent().parent().remove();}">Del</a>'+
														'<a href="javascript:void(0);" onclick="editThisBox($(this));" class="edit show">Edit</a>'+
														'<a href="javascript:void(0);" onclick="saveThisBox($(this));" class="save">Save</a>'+
													'</div>'+
												'</div>');
				$(".new .edit").click();
			}
		});
		$("#file_upload3Queue").html($(".more").html());
		$(".more").remove();
	})
	
	/**
	 *点击形成可编辑图片名和图片简介
	 ***/
	function editThisBox(ob){
		var op = ob.parent().find(".uploadifytil");
		ob.parent().find(".uploadifytil").html('<input type="hidden" name="photo[]" value="'+op.find("input[name='photo[]']").val()+'" />'+
											 '<input type="text" name="pname[]" value="'+op.find("input[name='pname[]']").val()+'" />');
											 
		ob.parent().find(".uploadifycon").html('<textarea name="p_description[]" class="border" onkeyup="checkFontNum($(this));">'+ob.parent().find("textarea").val()+'</textarea>');
		ob.parent().find(".edit").removeClass('show').hide(); ob.parent().find(".save").addClass('show');
	}
	
	/**
	 *点击形成不可编辑的图片名和图片简介
	 ***/
	function saveThisBox(ob){
		var op = ob.parent().find(".uploadifytil");
		ob.parent().find(".uploadifytil").html('<span>'+op.find("input[name='pname[]']").val()+'</span>'+
											   '<input type="hidden" name="photo[]" value="'+op.find("input[name='photo[]']").val()+'" />'+
											   '<input type="hidden" name="pname[]" value="'+op.find("input[name='pname[]']").val()+'" />');
											 
		ob.parent().find(".uploadifycon").html('<textarea name="p_description[]" readonly="readonly">'+ob.parent().find("textarea").val()+'</textarea>');
		ob.parent().find(".save").removeClass('show').hide(); ob.parent().find(".edit").addClass('show');
	}
	
	/**
	 *提交表单验证
	 ***/
	function pageEditForm(){
		if($("input[name=gname]").val()=='') {$(".msg").text('Please fill in Album Title field.'); return false; }
		else if($("input[name=gdefaut_icon]").val() == '') {$(".msg").text('Please upload Album Cover.'); return false; }
		else if($(".uploadifyBox").length < 1) {$(".msg").text('Please upload Photos.'); return false; }
		else return true;
	}
	
	/**
	 *验证图片说明字数
	 ***/
	function checkFontNum(ob){
		if(ob.val().length>15){
			alert("The Caption must be less than 30 characters.");
			ob.val(ob.val().slice(0,15));
		}
	}
</script>
<table class="form">
	<tr>
		<th>Album Title *</th>
		<td><input type="text" name="gname" value="<?php echo $_smarty_tpl->getVariable('gallery_info')->value['gname'];?>
" /></td>
	</tr>
	<tr>
		<th>Album Cover * </th>
		<td>
			<input type="file" id="file_upload" name="file_upload"/>
			<input type="hidden" name="gdefaut_icon" value="<?php echo $_smarty_tpl->getVariable('gallery_info')->value['gdefaut_icon'];?>
" />
			<div id="icon_image">
			<?php if ($_smarty_tpl->getVariable('gallery_info')->value['gdefaut_icon']!=''){?>
				<img src="<?php echo $_smarty_tpl->getVariable('gallery_info')->value['gdefaut_icon'];?>
" width="25" height="25"/>
			<?php }?>
			</div>
			<span>(The image should be less than 1 MB in PNG and 210px wide by 325px high.)</span>
		</td>
	</tr>
	<tr>
		<th valign="top">Downloadable</th>
		<td><input type="checkbox" name="gis_download" class="checkbox" <?php if ($_smarty_tpl->getVariable('gallery_info')->value['gis_download']==1){?> checked="checked"<?php }?> value="1"/></td>
	</tr>
	<tr>
		<th>Album Status*</th>
		<td><p><input type="radio" name="gstatus" value="1" class="radio" style="border: 0;"<?php if ($_smarty_tpl->getVariable('gallery_info')->value['gstatus']==1||$_smarty_tpl->getVariable('gallery_info')->value['gstatus']==''){?> checked="checked"<?php }?> /><label>Active</label>
			<input type="radio" name="gstatus" value="0" class="radio" style="border: 0;"<?php if ($_smarty_tpl->getVariable('gallery_info')->value['gstatus']==0&&$_smarty_tpl->getVariable('gallery_info')->value['gstatus']!=''){?> checked="checked"<?php }?> /><label>Inactive</label>
		</p></td>
	</tr>
	<tr>
		<th valign="top">Upload Photo(s)*</th>
		<td>
			<input type="file" id="file_upload3" name="file_upload3"/>
			<span>(The photo should be less than 1 MB and maximum 5 photos can be uploaded one time. They will show in the List Page and Detail Page.)</span>
			<div class="more">
				<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('photo_list')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
?>
					<div onmouseout="$(this).find('.show').hide()" onmouseover="$(this).find('.show').show()" class="uploadifyBox">
						<img src="<?php echo $_smarty_tpl->tpl_vars['item']->value['photo'];?>
" class="uploadifyImg"><div>
						<p class="uploadifytil">
							<span><?php echo $_smarty_tpl->tpl_vars['item']->value['pname'];?>
</span>
							<input type="hidden" value="<?php echo $_smarty_tpl->tpl_vars['item']->value['photo'];?>
" name="photo[]">
							<input type="hidden" value="<?php echo $_smarty_tpl->tpl_vars['item']->value['pname'];?>
" name="pname[]">
						</p>
						<p class="uploadifycon">
							<textarea readonly="readonly" name="p_description[]"><?php echo $_smarty_tpl->tpl_vars['item']->value['p_description'];?>
</textarea>
						</p>
						<a onclick="if(confirm('Are you sure to delete this photo?')){$(this).parent().parent().remove();}" class="delete show" href="javascript:void(0);" style="display: none;">Del</a>
						<a class="edit show" onclick="editThisBox($(this));" href="javascript:void(0);" style="display: none;">Edit</a>
						<a class="save" onclick="saveThisBox($(this));" href="javascript:void(0);">Save</a></div></div>
				<?php }} ?>
			</div>
		</td>
	</tr>
	<tr>
		<th></th>
		<td><input type="submit" value="Save" class="input_btn" /></td>
	</tr>
	<tr>
		<th></th>
		<td><p class="msg"></p></td>
	</tr>					
</table>
