package ch.itraum.recruiter.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import ch.itraum.recruiter.model.Candidate;
import ch.itraum.recruiter.model.Document;
import ch.itraum.recruiter.model.Skills;
import ch.itraum.recruiter.repository.CandidateRepository;
import ch.itraum.recruiter.repository.DocumentRepository;
import ch.itraum.recruiter.repository.SkillsRepository;

@Controller
//@SessionAttributes({"candidate","candidateSkills"})
public class FrontendController {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private SkillsRepository skillsRepository;

//	@Autowired
//	private MessageSource messageSource;
	
	@ModelAttribute(value = "yearList")
	public List<String> getYearList() {
		
		List<String> yearList = new LinkedList<String>();
		
		for (int i = 1970; i < 2024; i++)
		{
			yearList.add("" + i);
		}

		return yearList;
	}
	
	@ModelAttribute(value = "monthList")
	public Map<String, String> getMonthMap()
	{
		Map<String, String> monthMap = new LinkedHashMap<String, String>();
		
		monthMap.put("0", "Januar");
		monthMap.put("1", "Februar");
		monthMap.put("2", "März");
		monthMap.put("3", "April");
		monthMap.put("4", "Mai");
		monthMap.put("5", "Juni");
		monthMap.put("6", "Juli");
		monthMap.put("7", "August");
		monthMap.put("8", "September");
		monthMap.put("9", "Oktober");
		monthMap.put("10", "November");
		monthMap.put("11", "Dezember");
	
		return monthMap;
	}
	
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String getCandidate(Model model, HttpSession session) {
		
//		System.out.println("*** Session data ***");
//		Enumeration<String> e = session.getAttributeNames();
//		while (e.hasMoreElements()){
//			String s = e.nextElement();
//			System.out.println(s);
//			System.out.println("**" + session.getAttribute(s));
//		}
		 
//		model.addAttribute(getCandidateFromDB());
		model.addAttribute(getCandidateFromSession());
//		model.addAttribute(messageSource);
		return "frontend/candidate";
	}
 
	@RequestMapping(value = "/candidate", method = RequestMethod.POST)
	public String postCandidate(@Valid Candidate validCandidate,
			BindingResult result, Model model, @RequestParam("buttonPressed") String buttonPressed) {

		model.addAttribute(getCandidateFromSession());
		
		if (buttonPressed.equals("contactData_Forward")) {
			System.out.println("\n\n\n\n\nButton: contactData_Forward");
			if (result.hasErrors()){
				System.out.println("\n\n\n\n\nFehler im Kandidaten\n\n\n\n\n");
				return "frontend/candidate";
			}else{
				//Candidate needs to be saved anyway before skills and documents can be saved. 
				//So here is where this happens.
				//save Candidate to DB and save the received Candidate containing the DB ID into the HTTP Session
				getCurrentSession().setAttribute("candidate", candidateRepository.save(fillCandidateWithDataFrom(validCandidate)));
				return "redirect:/skills";
			}
		} else if (buttonPressed.equals("contactData_Back")) {
			//save current candidate object as is. Validation will effect further processing only if "forward" was pressed.
			getCurrentSession().setAttribute("candidate", fillCandidateWithDataFrom(validCandidate)); //if there is already an skills object in the session, we need it's ID
			return "redirect:/";
		}else  if (buttonPressed.equals("contactData_Cancel")) {
			return "redirect:/confirmCancellation";
		}else {
			return "frontend/unexpectedAction";
		}
	}
	
	private Candidate fillCandidateWithDataFrom(Candidate curCandidate){
//		Candidate resCandidate = getCandidateFromDB();
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
	
	private Skills fillSkillsWithDataFrom(Skills curSkills){
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
		return resSkills;
	}
	
	
	@RequestMapping(value = "/skills", method = RequestMethod.GET)
	public String getCandidateSkills(Model model) {
		model.addAttribute(getSkillsFromSession());
		System.out.println("\n\n\n\n\ngetCandidateSkills - GET\n\n\n\n\n");
		return "frontend/skills";
	}
	
	
	@RequestMapping(value = "/skills", method = RequestMethod.POST)
	public String postCandidateSkills(@Valid Skills validSkills,
			BindingResult result, Model model, @RequestParam("buttonPressed") String buttonPressed) {

		model.addAttribute(getSkillsFromSession());
		System.out.println("\n\n\n\n\ngetCandidateSkills - POST\n\n\n\n\n");
		
		if (buttonPressed.equals("contactSkills_Forward")) {
			System.out.println("\n\n\n\n\nButton: contactSkills_Forward");
			if (result.hasErrors()){
				System.out.println("\n\n\n\n\nFehler im Skill Sheet\n\n\n\n\n");
				return "frontend/skills";
			}else{
				validSkills.setCandidate(getCandidateFromSession()); //this Candidate is already validated
				//save Skills to DB and save the received Skills containing the DB ID into the HTTP Session
				getCurrentSession().setAttribute("skills", skillsRepository.save(fillSkillsWithDataFrom(validSkills)));
				System.out.println("OK! Tried to save the Skills.");
				return "redirect:/documents";
			}
		}else  if (buttonPressed.equals("contactSkills_Back")) {
			//save current skills object as is. Validation will effect further processing only if "forward" was pressed.
			getCurrentSession().setAttribute("skills", fillSkillsWithDataFrom(validSkills)); //if there is already an skills object in the session, we need it's ID
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
		
	@RequestMapping(value = "/submitApplication", method = RequestMethod.POST)
	public String postSubmitApplication(Model model, @RequestParam("buttonPressed") String buttonPressed) {

		model.addAttribute(getCandidateFromSession());
		model.addAttribute(getSkillsFromSession());
		model.addAttribute("documents", getDocumentsForSessionCandidate());
		
		if (buttonPressed.equals("submitApplication_Submit")) {
			return "redirect:/thankYou";
		}else  if (buttonPressed.equals("submitApplication_Back")) {
			return "redirect:/documents";
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
		return "frontend/agreement";
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postAgreement(Model model, @RequestParam("buttonPressed") String buttonPressed) {

		if (buttonPressed.equals("agreement_Accept")) {
			return "redirect:/candidate";
		}else  if (buttonPressed.equals("agreement_Decline")) {
			return "redirect:/confirmCancellation";
		}else {
			return "frontend/unexpectedAction";
		}
	}
	
	//If there is already an ID saved in the HttpSession the corresponding object will be fetched and returned. 
	//Otherwise a new Object will be returned
	private Candidate getCandidateFromDB(){
		Object sessionObjectID = getCurrentSession().getAttribute("candidateID");
		Candidate resultCandidate;
		if(sessionObjectID != null){
			int candidateSessionID = (int)sessionObjectID;
			System.out.println("Candidate ID from Session: " + candidateSessionID + "\n\n\n\n\n");
			resultCandidate = candidateRepository.findOne(candidateSessionID);
		}else{
			System.out.println("\n\n\n\n\nNo Candidate yet\n\n\n\n\n");
			resultCandidate = new Candidate();
		}		
		System.out.println("\n\n\n\n\ngetCandidateFromSession: ID="+resultCandidate.getId()+"\n\n\n\n\n");
		return resultCandidate;
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
	
	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String getDocuments(Model model) {

		List<Document> documents = getDocumentsForSessionCandidate();
//		Document doc = new Document();
//		doc.setName("Das ist der Filename");
//		documents.add(doc);
//		documents.add(doc);
//		documents.add(doc);
//		documents.add(doc);
//		documents.add(doc);

		model.addAttribute("documents", documents);
		System.out.println("\n\n\n\n\ngetDocuments\n\n\n\n\n");

		return "frontend/documents";
	}

	private List<Document> getDocumentsForSessionCandidate(){
//		List<Integer> ids = new ArrayList<Integer>();
//		ids.add(getCandidateFromSession().getId());
//		Iterable<Document> docs = documentRepository.findAll(ids);
//		List<Document> docList = new ArrayList<Document>();
		List<Document> docList = documentRepository.findByCandidate_Id(getCandidateFromSession().getId());
		System.out.println("\n\n\n\n\n");
		for(Document doc: docList){
			System.out.println("Doc ID: " + doc.getId() + ", Doc Name: " + doc.getName());
		}
		return docList;
	}
	
//	@RequestMapping(value = "/documents", method = RequestMethod.POST)
//	public String postDocuments(Model model, @RequestParam("buttonPressed") String buttonPressed) {
//
//		List<Document> documents = getDocumentsForSessionCandidate();
//		model.addAttribute(documents);
//		
//		if (buttonPressed.equals("documents_Forward")) {
//			return "frontend/unexpectedAction";
//		}else  if (buttonPressed.equals("documents_Back")) {
//			return "redirect:/skills";
//		}else {
//			return "frontend/unexpectedAction";
//		}
//	}
	
	private void deleteDocumentsFromDB(String csv_IDs){
		String[] strIDs = csv_IDs.split(",");
		for(int i = 0; i< strIDs.length; i++){
			documentRepository.delete(Integer.parseInt(strIDs[i]));
		}
	}

	@RequestMapping(value = "/documents", method = RequestMethod.POST)
	public String postDocumentsDelete(Model model, @RequestParam("buttonPressed") String buttonPressed, @RequestParam(value="chbDocuments", required=false) String chbDocuments) {

		List<Document> documents = getDocumentsForSessionCandidate();
		model.addAttribute("documents", documents);
		
		if (buttonPressed.equals("documents_Forward")) {
			return "redirect:/submitApplication";
		}else  if (buttonPressed.equals("documents_Back")) {
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

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void fileUploadSubmit(@RequestParam("file") Part file) throws IOException {
		System.out.println("\n\n\n\n\nfileUploadSubmit\n\n\n\n\n");

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
