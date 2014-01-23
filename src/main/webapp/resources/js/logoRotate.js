;(function(){
	var value = 0
	$("#image").rotate({ 
	   bind: 
	     { 
	        click: function(){
	            value +=180;
	            $(this).rotate({ animateTo:value})
	        }
	     } 
	   
	});
})();