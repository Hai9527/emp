<?php
/**
 *  ����ͼƬ�����������ͼƬ���룬ˮӡ���
 *  ��ˮӡͼ����Ŀ��ͼƬ�ߴ�ʱ��ˮӡͼ���Զ���ӦĿ��ͼƬ����С
 *  ˮӡͼ�������ø������ĺϲ���
 *
 *  Copyright(c) 2005 by ustb99. All rights reserved
 *
 *  To contact the author write to {@link mailto:ustb80@163.com}
 *
 * @author żȻ
 * @version $Id: thumb.class.php,v 1.9 2006/09/30 09:31:56 zengjian Exp $
 * @package system
 */
 
 
 /** 
  * ThumbHandler 
  * @access public 
  */ 
  
 /* 
  ʹ�÷���: 
 	�Զ�����: 
 	����ᰴ��ͼƬ�ĳߴ���в��������������Σ�����Ŀ��ߴ�������� 
  
 	$t->setSrcImg("img/test.jpg"); 
 	$t->setCutType(1);//��һ���OK�� 
 	$t->setDstImg("tmp/new_test.jpg"); 
 	$t->createImg(60,60); 
  
 	�ֹ�����: 
 	����ᰴ��ָ����λ�ô�Դͼ��ȡͼ 
  
 	$t->setSrcImg("img/test.jpg"); 
 	$t->setCutType(2);//ָ��Ϊ�ֹ����� 
 	$t->setSrcCutPosition(100, 100);// Դͼ������� 
 	$t->setRectangleCut(300, 200);// ���гߴ� 
 	$t->setDstImg("tmp/new_test.jpg"); 
 	$t->createImg(300,200);  
 */ 
 class ThumbHandler 
 { 
     var $dst_img;// Ŀ���ļ� 
     var $h_src; // ͼƬ��Դ��� 
     var $h_dst;// ��ͼ��� 
     var $h_mask;// ˮӡ��� 
     var $img_create_quality = 100;// ͼƬ�������� 
     var $img_display_quality = 80;// ͼƬ��ʾ����,Ĭ��Ϊ75 
     var $img_scale = 0;// ͼƬ���ű��� 
     var $src_w = 0;// ԭͼ��� 
     var $src_h = 0;// ԭͼ�߶� 
     var $dst_w = 0;// ��ͼ�ܿ�� 
     var $dst_h = 0;// ��ͼ�ܸ߶� 
     var $fill_w;// ���ͼ�ο� 
     var $fill_h;// ���ͼ�θ� 
     var $copy_w;// ����ͼ�ο� 
     var $copy_h;// ����ͼ�θ� 
     var $src_x = 0;// ԭͼ������ʼ������ 
     var $src_y = 0;// ԭͼ������ʼ������ 
     var $start_x;// ��ͼ������ʼ������ 
     var $start_y;// ��ͼ������ʼ������ 
     var $mask_word;// ˮӡ���� 
     var $mask_img;// ˮӡͼƬ 
     var $mask_pos_x = 0;// ˮӡ������ 
     var $mask_pos_y = 0;// ˮӡ������ 
     var $mask_offset_x = 5;// ˮӡ����ƫ�� 
     var $mask_offset_y = 5;// ˮӡ����ƫ�� 
     var $font_w;// ˮӡ����� 
     var $font_h;// ˮӡ����� 
     var $mask_w;// ˮӡ�� 
     var $mask_h;// ˮӡ�� 
     var $mask_font_color = "#ffffff";// ˮӡ������ɫ 
     var $mask_font = 2;// ˮӡ���� 
     var $font_size;// �ߴ� 
     var $mask_position = 0;// ˮӡλ�� 
     var $mask_img_pct = 50;// ͼƬ�ϲ��̶�,ֵԽ�󣬺ϲ�����Խ�� 
     var $mask_txt_pct = 50;// ���ֺϲ��̶�,ֵԽС���ϲ�����Խ�� 
     var $img_border_size = 0;// ͼƬ�߿�ߴ� 
     var $img_border_color;// ͼƬ�߿���ɫ 
     var $_flip_x=0;// ˮƽ��ת���� 
     var $_flip_y=0;// ��ֱ��ת���� 
  
     var $cut_type=0;// �������� 
  
  
     var $img_type;// �ļ����� 
  
     // �ļ����Ͷ���,��ָ�������ͼƬ�ĺ��� 
     var $all_type = array( 
         "jpg"  => array("output"=>"imagejpeg"), 
         "gif"  => array("output"=>"imagegif"), 
         "png"  => array("output"=>"imagepng"), 
         "wbmp" => array("output"=>"image2wbmp"), 
         "jpeg" => array("output"=>"imagejpeg")); 
  
     /** 
      * ���캯�� 
      */ 
     function ThumbHandler() 
     { 
         $this->mask_font_color = "#ffffff"; 
         $this->font = 2; 
         $this->font_size = 12; 
     } 
  
     /** 
      * ȡ��ͼƬ�Ŀ� 
      */ 
     function getImgWidth($src) 
     { 
         return imagesx($src); 
     } 
  
     /** 
      * ȡ��ͼƬ�ĸ� 
      */ 
     function getImgHeight($src) 
     { 
         return imagesy($src); 
     } 
  
     /** 
      * ����ͼƬ����·�� 
      * 
      * @param    string    $src_img   ͼƬ����·�� 
      */ 
     function setSrcImg($src_img, $img_type=null) 
     { 
         if(!file_exists($src_img)) 
         { 
             die("ͼƬ������"); 
         } 
 		 
 		if(!empty($img_type)) 
 		{ 
 			$this->img_type = $img_type; 
 		} 
 		else 
 		{ 
 		    $this->img_type = $this->_getImgType($src_img); 
 		} 
          
         $this->_checkValid($this->img_type); 
  
         // file_get_contents����Ҫ��php�汾>4.3.0 
         $src = ''; 
         if(function_exists("file_get_contents")) 
         { 
             $src = file_get_contents($src_img); 
         } 
         else 
         { 
             $handle = fopen ($src_img, "r"); 
             while (!feof ($handle)) 
             { 
                 $src .= fgets($fd, 4096); 
             } 
             fclose ($handle); 
         } 
         if(empty($src)) 
         { 
             die("ͼƬԴΪ��"); 
         } 
         $this->h_src = @ImageCreateFromString($src); 
         $this->src_w = $this->getImgWidth($this->h_src); 
         $this->src_h = $this->getImgHeight($this->h_src); 
     } 
  
     /** 
      * ����ͼƬ����·�� 
      * 
      * @param    string    $dst_img   ͼƬ����·�� 
      */ 
     function setDstImg($dst_img) 
     { 
         $arr  = explode('/',$dst_img); 
         $last = array_pop($arr); 
         $path = implode('/',$arr); 
         $this->_mkdirs($path); 
         $this->dst_img = $dst_img; 
     } 
  
     /** 
      * ����ͼƬ����ʾ���� 
      * 
      * @param    string      $n    ���� 
      */ 
     function setImgDisplayQuality($n) 
     { 
         $this->img_display_quality = (int)$n; 
     } 
  
     /** 
      * ����ͼƬ���������� 
      * 
      * @param    string      $n    ���� 
      */ 
     function setImgCreateQuality($n) 
     { 
         $this->img_create_quality = (int)$n; 
     } 
  
     /** 
      * ��������ˮӡ 
      * 
      * @param    string     $word    ˮӡ���� 
      * @param    integer    $font    ˮӡ���� 
      * @param    string     $color   ˮӡ������ɫ 
      */ 
     function setMaskWord($word) 
     { 
         $this->mask_word .= $word; 
     } 
  
     /** 
      * ����������ɫ 
      * 
      * @param    string     $color    ������ɫ 
      */ 
     function setMaskFontColor($color="#ffffff") 
     { 
         $this->mask_font_color = $color; 
     } 
  
     /** 
      * ����ˮӡ���� 
      * 
      * @param    string|integer    $font    ���� 
      */ 
     function setMaskFont($font=2) 
     { 
         if(!is_numeric($font) && !file_exists($font)) 
         { 
             die("�����ļ�������"); 
         } 
         $this->font = $font; 
     } 
  
     /** 
      * �������������С,����truetype������Ч 
      */ 
     function setMaskFontSize($size = "12") 
     { 
         $this->font_size = $size; 
     } 
  
     /** 
      * ����ͼƬˮӡ 
      * 
      * @param    string    $img     ˮӡͼƬԴ 
      */ 
     function setMaskImg($img) 
     { 
         $this->mask_img = $img; 
     } 
  
     /** 
      * ����ˮӡ����ƫ�� 
      * 
      * @param    integer     $x    ����ƫ���� 
      */ 
     function setMaskOffsetX($x) 
     { 
         $this->mask_offset_x = (int)$x; 
     } 
  
     /** 
      * ����ˮӡ����ƫ�� 
      * 
      * @param    integer     $y    ����ƫ���� 
      */ 
     function setMaskOffsetY($y) 
     { 
         $this->mask_offset_y = (int)$y; 
     } 
  
     /** 
      * ָ��ˮӡλ�� 
      * 
      * @param    integer     $position    λ��,1:����,2:����,3:����,0/4:���� 
      */ 
     function setMaskPosition($position=0) 
     { 
         $this->mask_position = (int)$position; 
     } 
  
     /** 
      * ����ͼƬ�ϲ��̶� 
      * 
      * @param    integer     $n    �ϲ��̶� 
      */ 
     function setMaskImgPct($n) 
     { 
         $this->mask_img_pct = (int)$n; 
     } 
  
     /** 
      * �������ֺϲ��̶� 
      * 
      * @param    integer     $n    �ϲ��̶� 
      */ 
     function setMaskTxtPct($n) 
     { 
         $this->mask_txt_pct = (int)$n; 
     } 
  
     /** 
      * ��������ͼ�߿� 
      * 
      * @param    (����)     (������)    (����) 
      */ 
     function setDstImgBorder($size=1, $color="#000000") 
     { 
         $this->img_border_size  = (int)$size; 
         $this->img_border_color = $color; 
     } 
  
     /** 
      * ˮƽ��ת 
      */ 
     function flipH() 
     { 
         $this->_flip_x++; 
     } 
  
     /** 
      * ��ֱ��ת 
      */ 
     function flipV() 
     { 
         $this->_flip_y++; 
     } 
  
     /** 
      * ���ü������� 
      * 
      * @param    (����)     (������)    (����) 
      */ 
     function setCutType($type) 
     { 
         $this->cut_type = (int)$type; 
     } 
  
     /** 
      * ����ͼƬ���� 
      * 
      * @param    integer     $width    ���μ��� 
      */ 
     function setRectangleCut($width, $height) 
     { 
 		$this->fill_w = (int)$width; 
 		$this->fill_h = (int)$height; 
     } 
  
 	/** 
 	 * ����Դͼ������ʼ����� 
 	 * 
 	 * @param    (����)     (������)    (����) 
 	 */ 
 	function setSrcCutPosition($x, $y) 
 	{ 
 		$this->src_x  = (int)$x; 
         $this->src_y  = (int)$y; 
 	} 
  
     /** 
      * ����ͼƬ,������ 
      * @param    integer    $a     ��ȱ�ٵڶ�������ʱ���˲����������ٷֱȣ� 
      *                             ������Ϊ���ֵ 
      * @param    integer    $b     ͼƬ���ź�ĸ߶� 
      */ 
     function createImg($a, $b=null) 
     { 
         $num = func_num_args(); 
         if(1 == $num) 
         { 
             $r = (int)$a; 
             if($r  < 1) 
             { 
                 die("ͼƬ���ű�������С��1"); 
             } 
             $this->img_scale = $r; 
             $this->_setNewImgSize($r); 
         } 
  
         if(2 == $num) 
         { 
             $w = (int)$a; 
             $h = (int)$b; 
             if(0 == $w) 
             { 
                 die("Ŀ���Ȳ���Ϊ0"); 
             } 
             if(0 == $h) 
             { 
                 die("Ŀ��߶Ȳ���Ϊ0"); 
             } 
             $this->_setNewImgSize($w, $h); 
         } 
  
         if($this->_flip_x%2!=0) 
         { 
             $this->_flipH($this->h_src); 
         } 
  
         if($this->_flip_y%2!=0) 
         { 
             $this->_flipV($this->h_src); 
         } 
         $this->_createMask(); 
         $this->_output(); 
  
         // �ͷ� 
         if(imagedestroy($this->h_src) && imagedestroy($this->h_dst)) 
         { 
             Return true; 
         } 
         else 
         { 
             Return false; 
         } 
     } 
  
     
 ?>