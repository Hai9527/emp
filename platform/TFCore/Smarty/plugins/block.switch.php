<?php
 
/**
* Smarty {switch}{/switch} block plugin
*
* Type: block function<br>
* Name: switch<br>
* Purpose: container for {page}...{/page} blocks
* author: messju mohr <<a href="mailto:messju@lammfellpuschen.de">messju@lammfellpuschen.de</a>>
* very slightly modified: dasher <<a href="mailto:dasher@inspiredthinking.co.uk">dasher@inspiredthinking.co.uk</a>>
* @param array
* Params: expr: string|numeric to be tested
* @param string contents of the block
* @param Smarty
* @return string
*/
function smarty_block_switch($params, $content, &$smarty, &$pages) {
 
if (is_null($content) && !array_key_exists('expr', $params)) {
$smarty->trigger_error("{switch}: parameter 'expr' not given");
}
return $content;
}
?>