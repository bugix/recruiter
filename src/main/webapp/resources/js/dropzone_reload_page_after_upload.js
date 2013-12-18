;(function(){
	Dropzone.options.dropzone = {
	  init: function() {
		    this.on("complete", function(){document.location.reload(true);});
			  }
	};	
})();
