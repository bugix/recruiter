	function grayOut_WorkExperience(checkBox_hasNoExperience) {
	    if ( checkBox_hasNoExperience.checked ) {
	        document.getElementById('position').value = "";
	        document.getElementById('position').setAttribute("disabled","disabled");
	        document.getElementById('jobField').value = "";
	        document.getElementById('jobField').setAttribute("disabled","disabled");
	        document.getElementById('cancelationPeriod').value = "";
	        document.getElementById('cancelationPeriod').setAttribute("disabled","disabled");
	        document.getElementById('startDateExperienceMonth').value = (new Date()).getMonth();
	        document.getElementById('startDateExperienceMonth').setAttribute("disabled","disabled");
	        document.getElementById('startDateExperienceYear').value = (new Date()).getYear() + 1900;
	        document.getElementById('startDateExperienceYear').setAttribute("disabled","disabled");
	        document.getElementById('endDateExperienceMonth').value = (new Date()).getMonth();
	        document.getElementById('endDateExperienceMonth').setAttribute("disabled","disabled");
	        document.getElementById('endDateExperienceYear').value = (new Date()).getYear() + 1900;
	        document.getElementById('endDateExperienceYear').setAttribute("disabled","disabled");
	        document.getElementById('currentPosition').checked="";
	        document.getElementById('currentPosition').setAttribute("disabled","disabled");
	     } else {
	         document.getElementById('position').removeAttribute("disabled");
	         document.getElementById('jobField').removeAttribute("disabled");
	         document.getElementById('cancelationPeriod').removeAttribute("disabled");
	         document.getElementById('startDateExperienceMonth').removeAttribute("disabled");
	         document.getElementById('startDateExperienceYear').removeAttribute("disabled");
	         document.getElementById('endDateExperienceMonth').removeAttribute("disabled");
	         document.getElementById('endDateExperienceYear').removeAttribute("disabled");
	         document.getElementById('currentPosition').removeAttribute("disabled");
	     }    
	}
	function initiallyCheckWorkExperienceBox(){
		grayOut_WorkExperience(document.getElementById('hasNoExperience'));
	}
