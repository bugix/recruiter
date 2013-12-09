package ch.itraum.recruiter.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import ch.itraum.recruiter.model.Candidate;
import ch.itraum.recruiter.model.Document;
import ch.itraum.recruiter.model.Skills;
import ch.itraum.recruiter.repository.CandidateRepository;
import ch.itraum.recruiter.repository.DocumentRepository;
import ch.itraum.recruiter.repository.SkillsRepository;
import ch.itraum.recruiter.tools.recruiterHelper;

@Controller
public class FrontendController {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private SkillsRepository skillsRepository;
	
	@Autowired
	private SessionLocaleResolver localeResolver;

	@ModelAttribute(value = "yearList")
	public Map<String, String> getYearList() {
		
		Map<String, String> yearList = new LinkedHashMap<String, String>();
		
		for (int i = 1970; i < 2024; i++)
		{
			yearList.put("" + i, "" + i);
		}

		return yearList;
	}
	
	@ModelAttribute(value = "monthList")
	public Map<String, String> getMonthMap()
	{
		Map<String, String> monthMap = new LinkedHashMap<String, String>();
		
		monthMap.put("0", "january");
		monthMap.put("1", "february");
		monthMap.put("2", "march");
		monthMap.put("3", "april");
		monthMap.put("4", "may");
		monthMap.put("5", "june");
		monthMap.put("6", "july");
		monthMap.put("7", "august");
		monthMap.put("8", "september");
		monthMap.put("9", "october");
		monthMap.put("10", "november");
		monthMap.put("11", "december");
	
		return monthMap;
	}
	
	@ModelAttribute(value = "languageList")
	public Map<String, String> getLanguageMap()
	{
		Map<String, String> languageMap = new LinkedHashMap<String, String>();
		
		languageMap.put(recruiterHelper.LANGUAGE_GERMAN, "Deutsch");
		languageMap.put(recruiterHelper.LANGUAGE_ENGLISH, "English");
	
		return languageMap;
	}
	
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String getCandidate(Model model) {
		Candidate candidate = getCandidateFromSession();
		model.addAttribute(candidate);
		return "frontend/candidate";
	}
 
	@RequestMapping(value = "/candidate", method = RequestMethod.POST)
	public String postCandidate(@Valid Candidate validCandidate,
			BindingResult result, Model model, @RequestParam("buttonPressed") String buttonPressed) {
//		model.addAttribute(getCandidateFromSession());
		if (buttonPressed.equals("contactData_Forward")) {
			if (result.hasErrors()){
//				getCurrentSession().setAttribute("candidate", fillCandidateFromSessionWithDataFrom(validCandidate));
//				return "redirect:/candidate";
				return "frontend/candidate";
			}else{
				//Candidate needs to be saved anyway before skills and documents can be saved. 
				//So here is where this happens.
				//save Candidate to DB and save the received Candidate containing the DB ID into the HTTP Session
				getCurrentSession().setAttribute("candidate", candidateRepository.save(fillCandidateFromSessionWithDataFrom(validCandidate)));
				return "redirect:/skills";
			}
		} else if (buttonPressed.equals("contactData_Back")) {
			//save current candidate object as is. Validation will effect further processing only if "forward" was pressed.
			getCurrentSession().setAttribute("candidate", fillCandidateFromSessionWithDataFrom(validCandidate)); //if there is already an skills object in the session, we need it's ID
			return "redirect:/";
		}else  if (buttonPressed.equals("contactData_Cancel")) {
			return "redirect:/confirmCancellation";
		}else {
			return "frontend/unexpectedAction";
		}
	}

	private Candidate fillCandidateFromSessionWithDataFrom(Candidate curCandidate){
		Candidate resCandidate = getCandidateFromSession();
		resCandidate.setFirstName(curCandidate.getFirstName());
		resCandidate.setLastName(curCandidate.getLastName());
		resCandidate.setEmail(curCandidate.getEmail());
		resCandidate.setCity(curCandidate.getCity());
		resCandidate.setPhoneFix(curCandidate.getPhoneFix());
		resCandidate.setPhoneMobile(curCandidate.getPhoneMobile());
		resCandidate.setPlz(curCandidate.getPlz());
		resCandidate.setStreet(curCandidate.getStreet());
		resCandidate.setTitle(curCandidate.getTitle());
		return resCandidate;
	}
	
	private Skills fillSkillsFromSessionWithDataFrom(Skills curSkills){
		Skills resSkills = getSkillsFromSession();
		resSkills.setCancelationPeriod(curSkills.getCancelationPeriod());
		resSkills.setCandidate(curSkills.getCandidate());
		resSkills.setCurrentPosition(curSkills.getCurrentPosition());
		resSkills.setDegree(curSkills.getDegree());
		resSkills.setEndDateEducation(curSkills.getEndDateEducation());
		resSkills.setEndDateExperience(curSkills.getEndDateExperience());
		resSkills.setInstitution(curSkills.getInstitution());
		resSkills.setJobField(curSkills.getJobField());
		resSkills.setPosition(curSkills.getPosition());
		resSkills.setProspectiveEnd(curSkills.getProspectiveEnd());
		resSkills.setStartDateEducation(curSkills.getStartDateEducation());
		resSkills.setStartDateExperience(curSkills.getStartDateExperience());
		resSkills.setTopic(curSkills.getTopic());
		resSkills.setHasNoExperience(curSkills.getHasNoExperience());
		return resSkills;
	}
	
	
	@RequestMapping(value = "/skills", method = RequestMethod.GET)
	public String getCandidateSkills(Model model) {
		model.addAttribute(getSkillsFromSession());
		return "frontend/skills";
	}
	
	
	@RequestMapping(value = "/skills", method = RequestMethod.POST)
	public String postCandidateSkills(@Valid Skills validSkills,
			BindingResult result, Model model, @RequestParam("buttonPressed") String buttonPressed) {
//		model.addAttribute(getSkillsFromSession());
		if (buttonPressed.equals("contactSkills_Forward")) {
			if (result.hasErrors()){
				return "frontend/skills";
			}else{
				validSkills.setCandidate(getCandidateFromSession()); //this Candidate is already validated
				//save Skills to DB and save the received Skills containing the DB ID into the HTTP Session
				Skills skillsWithID = skillsRepository.save(fillSkillsFromSessionWithDataFrom(validSkills));
				//we have to copy back some values from validSkills to the object we get back from the DB, 
				//because they are not part of the SQL Model and are therefore not delivered back.
				skillsWithID.takeAllAttributesExceptIDFrom(validSkills);
				getCurrentSession().setAttribute("skills", skillsWithID);
				return "redirect:/documents";
			}
		}else  if (buttonPressed.equals("contactSkills_Back")) {
			//save current skills object as is. Validation will effect further processing only if "forward" was pressed.
			getCurrentSession().setAttribute("skills", fillSkillsFromSessionWithDataFrom(validSkills)); //if there is already an skills object in the session, we need it's ID
			return "redirect:/candidate";
		}else  if (buttonPressed.equals("contactSkills_Cancel")) {
			return "redirect:/confirmCancellation";
		}else {
			return "frontend/unexpectedAction";
		}
	}
	
	@RequestMapping(value = "/submitApplication", method = RequestMethod.GET)
	public String getSubmitApplication(Model model) {
		model.addAttribute(getCandidateFromSession());
		model.addAttribute(getSkillsFromSession());
		model.addAttribute("documents", getDocumentsForSessionCandidate());
//		model.addAttribute("hasNoExperience", getCurrentSession().getAttribute("hasNoExperience"));		
		return "frontend/submitApplication";
	}
	
	@RequestMapping(value = "/thankYou", method = RequestMethod.GET)
	public String getThankYou() {
		deleteEverythingFromSession();
		return "frontend/thankYou";
	}
		
	@RequestMapping(value = "/confirmCancellation", method = RequestMethod.GET)
	public String getConfirmCancellation() {
		deleteEverythingFromDB();
		//Only now we can delete the session objects because we need their 
		//information for deleting the data in the DB
		deleteEverythingFromSession();
		return "frontend/confirmCancellation";
	}
	
	@RequestMapping(value = "/confirmCancellation", method = RequestMethod.POST)
	public String postConfirmCancellation(Model model, @RequestParam("buttonPressed") String buttonPressed) {
		if (buttonPressed.equals("confirmCancellation_BackToStart")) {
			return "redirect:/";
		}else {
			return "frontend/unexpectedAction";
		}
	}
		
	@RequestMapping(value = "/submitApplication", method = RequestMethod.POST)
	public String postSubmitApplication(Model model, @RequestParam("buttonPressed") String buttonPressed) {

		model.addAttribute(getCandidateFromSession());
		model.addAttribute(getSkillsFromSession());
		model.addAttribute("documents", getDocumentsForSessionCandidate());
		
		if (buttonPressed.equals("submitApplication_Submit")) {
			return "redirect:/thankYou";
		}else  if (buttonPressed.equals("submitApplication_Back")) {
			return "redirect:/letterOfMotivation";
		}else  if (buttonPressed.equals("submitApplication_Cancel")) {
			return "redirect:/confirmCancellation";
		}else {
			return "frontend/unexpectedAction";
		}
	}
	
	private void deleteEverythingFromDB(){
		Candidate sessionCandidate = (Candidate)getCurrentSession().getAttribute("candidate");
		if(sessionCandidate != null){
			//Only if the candidate has an ID it was saved to the DB
			if(sessionCandidate.getId() != null){
				List<Document> documents = getDocumentsForSessionCandidate();
				for(Document doc: documents){
					documentRepository.delete(doc.getId());
				}
				//There is only one skills object for the candidate
				//And if it was saved to the DB it has an ID
				Skills sessionSkills = (Skills)getCurrentSession().getAttribute("skills");
				if(sessionSkills != null && sessionSkills.getId() != null){
					skillsRepository.delete(sessionSkills.getId());
				}
				candidateRepository.delete(sessionCandidate.getId());
			}
		}
	}
	
	private void deleteEverythingFromSession(){
		getCurrentSession().removeAttribute("candidate");
		getCurrentSession().removeAttribute("skills");
		getCurrentSession().removeAttribute("documents");
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getAgreement(Model model) {
		String language = getCurrentOrDefaultLanguageFromSession();
		model.addAttribute("selectedLanguage", language);
		return "frontend/agreement";
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postAgreement(HttpServletRequest request, Model model, @RequestParam("buttonPressed") String buttonPressed) {

		if (buttonPressed.equals("agreement_Accept")) {
			String lang = getCurrentOrDefaultLanguageFromSession();
			getCurrentSession().setAttribute("curLanguage", lang);
			return "redirect:/candidate";
		}else  if (buttonPressed.equals("agreement_Decline")) {
			return "redirect:/confirmCancellation";
		}else  if (buttonPressed.equals(recruiterHelper.LANGUAGE_GERMAN)||buttonPressed.equals(recruiterHelper.LANGUAGE_ENGLISH)) {
			request.setAttribute("lang", buttonPressed);
			return "redirect:/";
		}else {
			return "frontend/unexpectedAction";
		}
	}
	
	//If there is already a candidate saved in the HttpSession he will be returned. 
	//Otherwise a new candidate will be returned
	private Candidate getCandidateFromSession(){
		Object sessionCandidate = getCurrentSession().getAttribute("candidate");
		Candidate resultCandidate;
		if(sessionCandidate != null){
			resultCandidate = (Candidate)sessionCandidate;
		}else{
			resultCandidate = new Candidate();
		}		
		return resultCandidate;
	}
	
	//If there is already a skills object saved in the HttpSession it will be returned. 
	//Otherwise a new skills object will be returned
	private Skills getSkillsFromSession(){
		Object sessionSkills = getCurrentSession().getAttribute("skills");
		Skills resultSkills;
		if(sessionSkills != null){
			resultSkills = (Skills)sessionSkills;
		}else{
			resultSkills = new Skills();
		}		
		return resultSkills;
	}
	
	private String getCurrentOrDefaultLanguageFromSession(){
		Object tryLang = getCurrentSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		String lang;
		if(tryLang != null){
			lang = ((Locale)tryLang).toString();
		}else{
			lang = recruiterHelper.LANGUAGE_DEFAULT;
		}
		return lang;
	}
	
	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String getDocuments(Model model) {
		List<Document> documents = getDocumentsForSessionCandidate();

		Document motivationalLetter = new Document();
		Boolean letterFound = false;
		
//		String textAreaContent = "";
		
		for(Document doc: documents){
			if(doc.getName().equals(recruiterHelper.FILE_NAME_MOTIVATIONSSCHREIBEN)){
				motivationalLetter = doc;
				letterFound = true;
			}
		}
		if(letterFound){
			documents.remove(motivationalLetter);
//			textAreaContent = new String (motivationalLetter.getContent());
		}
//		model.addAttribute("textAreaContent", textAreaContent);
		model.addAttribute("documents", documents);
		model.addAttribute("language", getCurrentSession().getAttribute("curLanguage"));
		return "frontend/documents";
	}

	private List<Document> getDocumentsForSessionCandidate(){
		List<Document> docList = documentRepository.findByCandidate_Id(getCandidateFromSession().getId());
		return docList;
	}
	
	private void deleteDocumentsFromDB(String csv_IDs){
		String[] strIDs = csv_IDs.split(",");
		for(int i = 0; i< strIDs.length; i++){
			documentRepository.delete(Integer.parseInt(strIDs[i]));
		}
	}
	
	@RequestMapping(value = "/documents", method = RequestMethod.POST)
	public String postDocumentsDelete(Model model, @RequestParam("buttonPressed") String buttonPressed, @RequestParam(value="chbDocuments", 
			required=false) String chbDocuments) {

		List<Document> documents = getDocumentsForSessionCandidate();
		model.addAttribute("documents", documents);
		
		if (buttonPressed.equals("documents_Forward")) {
//			if(textfield != null && !textfield.isEmpty()){
//				Document letterOfMotivation = getLetterOfMotivationFromListIfPossibleElseCreateANewOne(documents);
//				saveLetterOfMotivationAsDocumentFileToDB(letterOfMotivation, textfield.getBytes());
//			}
			return "redirect:/letterOfMotivation";
//			return "redirect:/submitApplication";
		}else  if (buttonPressed.equals("documents_Back")) {
//			Document letterOfMotivation = getLetterOfMotivationFromListIfPossibleElseCreateANewOne(documents);
//			saveLetterOfMotivationAsDocumentFileToDB(letterOfMotivation, textfield.getBytes());
			return "redirect:/skills";
		}else  if (buttonPressed.equals("documents_Delete")) {
			if(chbDocuments != null){
				deleteDocumentsFromDB(chbDocuments);
			}
			return "redirect:/documents";
		}else  if (buttonPressed.equals("documents_Cancel")) {
			return "redirect:/confirmCancellation";
		}else {
			return "frontend/unexpectedAction";
		}
	}
	
	@RequestMapping(value = "/letterOfMotivation", method = RequestMethod.GET)
	public String getLetterOfMotivation(Model model) {
		List<Document> documents = getDocumentsForSessionCandidate();

		String textAreaContent = "";
		
		for(Document doc: documents){
			if(doc.getName().equals(recruiterHelper.FILE_NAME_MOTIVATIONSSCHREIBEN)){
				textAreaContent = new String(doc.getContent());
			}
		}
		model.addAttribute("textFieldLetterOfMotivation", textAreaContent);
		return "frontend/letterOfMotivation";
	}

	@RequestMapping(value = "/letterOfMotivation", method = RequestMethod.POST)
	public String postLetterOfMotivation(Model model, @RequestParam("buttonPressed") String buttonPressed, 
			@RequestParam(value="textFieldLetterOfMotivation", required=false) String textFieldLetterOfMotivation) {

		List<Document> documents = getDocumentsForSessionCandidate();
		
		if (buttonPressed.equals("letterOfMotivation_Forward")) {
//			if(textFieldLetterOfMotivation != null && !textFieldLetterOfMotivation.isEmpty()){
//				Document letterOfMotivation = getLetterOfMotivationFromListIfPossibleElseCreateANewOne(documents);
//				saveLetterOfMotivationAsDocumentFileToDB(letterOfMotivation, textFieldLetterOfMotivation.getBytes());
//			}
			manageDBStuff4LetterOfMotivation(textFieldLetterOfMotivation);
			return "redirect:/submitApplication";
		}else  if (buttonPressed.equals("letterOfMotivation_Back")) {
//			Document letterOfMotivation = getLetterOfMotivationFromListIfPossibleElseCreateANewOne(documents);
//			saveLetterOfMotivationAsDocumentFileToDB(letterOfMotivation, textFieldLetterOfMotivation.getBytes());
			manageDBStuff4LetterOfMotivation(textFieldLetterOfMotivation);
			return "redirect:/documents";
		}else  if (buttonPressed.equals("letterOfMotivation_Cancel")) {
			return "redirect:/confirmCancellation";
		}else {
			return "frontend/unexpectedAction";
		}
	}
	
	private void manageDBStuff4LetterOfMotivation(String letterOfMotivationText){
		List<Document> documents = getDocumentsForSessionCandidate();
		Document letterOfMotivation = getLetterOfMotivationFromListIfPossibleElseCreateANewOne(documents);
		if(letterOfMotivation.getContent() == null || letterOfMotivation.getContent().length == 0){//if there is no letter in the DB yet
			if(letterOfMotivationText.isEmpty()){
				//Do nothing
			}else{
				saveLetterOfMotivationAsDocumentFileToDB(letterOfMotivation, letterOfMotivationText.getBytes());
			}
		}else{//if there is already a letter in the DB
			if(letterOfMotivationText.isEmpty()){
				//delete letter from DB
				documentRepository.delete(letterOfMotivation.getId());
			}else{
				saveLetterOfMotivationAsDocumentFileToDB(letterOfMotivation, letterOfMotivationText.getBytes());				
			}
		}
	}
	
	private Document getLetterOfMotivationFromListIfPossibleElseCreateANewOne(List<Document> documents){
		Document letterOfMotivation = new Document();
		
		for(Document doc: documents){
			if(doc.getName().equals(recruiterHelper.FILE_NAME_MOTIVATIONSSCHREIBEN)){
				letterOfMotivation = doc;
			}
		}
		return letterOfMotivation;
	}
	
	private void saveLetterOfMotivationAsDocumentFileToDB(Document letterOfMotivation, byte[] imgDataBa){
		letterOfMotivation.setContent(imgDataBa);
		letterOfMotivation.setName(recruiterHelper.FILE_NAME_MOTIVATIONSSCHREIBEN);
		letterOfMotivation.setCandidate(getCandidateFromSession());

		documentRepository.save(letterOfMotivation);
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void fileUploadSubmit(@RequestParam("file") Part file) throws IOException {

		Document document = new Document();

		byte[] imgDataBa = new byte[(int) file.getSize()];
		DataInputStream dataIs = new DataInputStream(file.getInputStream());
		dataIs.readFully(imgDataBa);

		document.setContent(imgDataBa);
		document.setName(getFileName(file));
		document.setCandidate(getCandidateFromSession());

		documentRepository.save(document);
	}
	
		
	private HttpSession getCurrentSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == allow create
	}

	private String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}

		return null;
	}
}
