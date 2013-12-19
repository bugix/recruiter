;(function(){
	Dropzone.options.dropzone = {
  	      dictDefaultMessage: "Dateien hier her ziehen zum Hochladen.",
	      dictFallbackMessage: "Ihr Browser unterstützt keine Drag'n'Drop Datei Uploads.",
	      dictFallbackText: "Bitte benutzen Sie das Alternativformular unten, um Ihre Dateien hochzuladen wie in alten Tagen.",
	      dictFileTooBig: "Datei ist zu gross ({{filesize}}MB). Max Dateigrösse: {{maxFilesize}}MB.",
	      dictInvalidFileType: "Sie können keine Dateien von diesem Typ hochladen.",
	      dictResponseError: "Der Server antwortete mit dem Code: {{statusCode}}.",
	      dictCancelUpload: "Hochladen abbrechen",
	      dictCancelUploadConfirmation: "Sind Sie sicheer, dass Sie den Upload abbrechen wollen?",
	      dictRemoveFile: "Datei entfernen",
	      dictRemoveFileConfirmation: null,
	      dictMaxFilesExceeded: "Sie können maximal {{maxFiles}} Dateien hochladen.",
		  acceptedFiles: "application/pdf",
		  init: function() {
			    this.on("success", function(){document.location.reload(true);});
			    this.on("error", function(file, message){alert(message); document.location.reload(true);});
				  }	      
	};	
})();
