;(function(){
	Dropzone.options.dropzone = {
			acceptedFiles: "application/pdf",
		  init: function() {
			    this.on("success", function(){document.location.reload(true);});
			    this.on("error", function(file, message){alert(message); document.location.reload(true);});
				  }
};	
})();
