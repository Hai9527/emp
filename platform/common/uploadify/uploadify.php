<?php
/*
Uploadify v2.1.4
Release Date: November 8, 2010

Copyright (c) 2010 Ronnie Garcia, Travis Nickels

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
if (!empty($_FILES)) {
	$tempFile = $_FILES['Filedata']['tmp_name'];
	//$targetPath = $_SERVER['DOCUMENT_ROOT'] . $_REQUEST['folder'] . '/';
	if($_REQUEST['stay'] == 'image'){
		$targetPath = dirname(__FILE__)."/../../uploads/images/";
	}else if($_REQUEST['stay'] == 'music'){
		$targetPath = dirname(__FILE__)."/../../uploads/music/";
	}else if($_REQUEST['stay'] == 'video'){
		$targetPath = dirname(__FILE__)."/../../uploads/video/";
	}else{
		$targetPath = dirname(__FILE__)."/../../images/uploads/";
	}
	
	
	$oldName = explode(".", $_FILES['Filedata']['name']);
	$reName = date("YmdHis")."_".mt_rand(1000,9999).".".end($oldName);
	$targetFile =  str_replace('//','/',$targetPath) . iconv("UTF-8","gbk",$reName);
	$targetFile_small =  str_replace('//','/',$targetPath) . iconv("UTF-8","gbk","thumbnail/thumb_".$reName);
	
	
	move_uploaded_file($tempFile,$targetFile);
	if(isset($_REQUEST['needWatermark']) && $_REQUEST['needWatermark'] == 1){
		include("../CreatMiniature.php");
		$cm=new CreatMiniature();
		$cm->SetVar($targetFile,"file");
		$cm->Cut($targetFile_small,100,100);
	}
	/** ÅÐ”àˆDÆ¬´óÐ¡ */
	if(isset($_REQUEST['width']) && $_REQUEST['width'] != '' && isset($_REQUEST['height']) && $_REQUEST['height'] != '' && isset($_REQUEST['mark']) && $_REQUEST['mark'] != ''){
		$im = getimagesize($targetFile); 
		if(($_REQUEST['mark'] =='>=' && ($_REQUEST['width'] > $im[0] || $_REQUEST['height'] > $im[1])) || 
		   ($_REQUEST['mark'] =='<=' && ($_REQUEST['width'] < $im[0] || $_REQUEST['height'] < $im[1])) || 
		   ($_REQUEST['mark'] =='=' && ($_REQUEST['width'] != $im[0] || $_REQUEST['height'] != $im[1]))
		){
			@ unlink($targetFile);
			@ unlink($targetFile_small);
			echo -1;exit;
		}
	}
	
	echo $targetFile;
}


?>