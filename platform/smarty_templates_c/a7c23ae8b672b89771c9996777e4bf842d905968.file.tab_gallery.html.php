<?php /* Smarty version Smarty-3.0.6, created on 2012-08-03 18:27:38
         compiled from "D:\FML\EMP\platform\Template\tabpage/tab_gallery.html" */ ?>
<?php /*%%SmartyHeaderCode:20821501ba79abdf437-89652585%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'a7c23ae8b672b89771c9996777e4bf842d905968' => 
    array (
      0 => 'D:\\FML\\EMP\\platform\\Template\\tabpage/tab_gallery.html',
      1 => 1339481600,
      2 => 'file',
    ),
  ),
  'nocache_hash' => '20821501ba79abdf437-89652585',
  'function' => 
  array (
  ),
  'has_nocache_code' => false,
)); /*/%%SmartyHeaderCode%%*/?>
<?php if (!is_callable('smarty_modifier_mb_truncate')) include 'D:\FML\EMP\platform\TFCore\Smarty\plugins\modifier.mb_truncate.php';
?><p class="btn"><a class="iframe button" href="?c=gallery&a=editGallery&tid=<?php echo $_smarty_tpl->getVariable('tid')->value;?>
">Add New</a></p>
<table class="list">
	<tr>
		<th width="12%">Order</th>
		<th>ID</th>
		<th>Album Title</th>
		<th>Total Photos</th>
		<th>Downloadable</th>
		<th>Total "View"</th>
		<th>Total "Like"</th>
		<th>Total "Comment"</th>
		<th>Stauts</th>
		<th>Action</th>
	</tr>
	<?php if ($_smarty_tpl->getVariable('gallery_list')->value!=''){?>
	<?php  $_smarty_tpl->tpl_vars['item'] = new Smarty_Variable;
 $_smarty_tpl->tpl_vars['key'] = new Smarty_Variable;
 $_from = $_smarty_tpl->getVariable('gallery_list')->value; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array');}
if ($_smarty_tpl->_count($_from) > 0){
    foreach ($_from as $_smarty_tpl->tpl_vars['item']->key => $_smarty_tpl->tpl_vars['item']->value){
 $_smarty_tpl->tpl_vars['key']->value = $_smarty_tpl->tpl_vars['item']->key;
?>
	<tr>							
		<td align="center">
			<span class="number"><?php echo $_smarty_tpl->getVariable('offset')->value+$_smarty_tpl->tpl_vars['key']->value+1;?>
</span>
			<input type="hidden" value="<?php echo $_smarty_tpl->tpl_vars['item']->value['gid'];?>
" name="id" />
			<input type="hidden" value="<?php echo $_smarty_tpl->tpl_vars['item']->value['tid'];?>
" name="tid" />
			<input type="text" name="order" class="order" />&nbsp;&nbsp;
			<a href="javascript:;" onclick="" class="button go">GO</a>
		</td>
		<td><?php echo $_smarty_tpl->tpl_vars['item']->value['gid'];?>
</td>
		<td><?php echo smarty_modifier_mb_truncate($_smarty_tpl->tpl_vars['item']->value['gname'],13,"...");?>
</td>
		<td><?php echo $_smarty_tpl->tpl_vars['item']->value['gcount'];?>
</td>
		<td>
			<?php if ($_smarty_tpl->tpl_vars['item']->value['gis_download']==0){?>No
			<?php }elseif($_smarty_tpl->tpl_vars['item']->value['gis_download']==1){?>Yes
			<?php }?>
		</td>
		<td><?php echo $_smarty_tpl->tpl_vars['item']->value['players_num'];?>
</td>
		<td><?php echo $_smarty_tpl->tpl_vars['item']->value['likes_num'];?>
</td>
		<td><?php echo $_smarty_tpl->tpl_vars['item']->value['comments_num'];?>
</td>
		<td>
			<?php if ($_smarty_tpl->tpl_vars['item']->value['gstatus']==0){?>Inactive
			<?php }elseif($_smarty_tpl->tpl_vars['item']->value['gstatus']==1){?>Active
			<?php }?>
		</td>
		<td><a href="?c=gallery&a=editGallery&gid=<?php echo $_smarty_tpl->tpl_vars['item']->value['gid'];?>
" class="iframe button">Edit</a>&nbsp;&nbsp;&nbsp;<a href="?c=gallery&a=deleteGallery&gid=<?php echo $_smarty_tpl->tpl_vars['item']->value['gid'];?>
" onclick="return confirm('Are you sure delete this record.')" class="button">Delete</a></td>					
	</tr>
	<?php }} else { ?>
	<tr>							
		<td colspan="7">There is no records currently.</td>
	</tr>	
	<?php } ?>	
	<?php }?>

</table>
<div class="page">
	<div class="pageshow"><?php echo $_smarty_tpl->getVariable('show_page')->value;?>
</div>
</div>