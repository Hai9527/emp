	function toPage(num){
		$(".search form").attr("action", window.location.href.split('&page')[0]+"&page="+num).submit();
	}
	
	function toggleShow(ob){
		if(ob.parent().find("span").html()=='-'){
			ob.parent().find("span").html("+");
			ob.parent().next().hide();
		}else{
			ob.parent().find("span").html("-");
			ob.parent().next().show();
		}
	}
