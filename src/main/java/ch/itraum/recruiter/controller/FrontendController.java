package ch.itraum.recruiter.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ch.itraum.recruiter.model.Candidate;
import ch.itraum.recruiter.model.CandidateSkills;
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
//	private SmartValidator validator;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getCandidate(Model model, HttpSession session) {
		
		System.out.println("*** Session data ***");
		Enumeration<String> e = session.getAttributeNames();
		while (e.hasMoreElements()){
			String s = e.nextElement();
			System.out.println(s);
			System.out.println("**" + session.getAttribute(s));
		}
		 
//		model.addAttribute(getCandidateFromDB());
		model.addAttribute(getCandidateFromSession());
		return "frontend/candidate";
	}
 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postCandidate(@Valid Candidate validCandidate,
			BindingResult result, Model model, @RequestParam("buttonPressed") String buttonPressed) {

		model.addAttribute(getCandidateFromSession());
		
		if (buttonPressed.equals("contactData_Forward")) {
			System.out.println("\n\n\n\n\nButton: contactData_Forward");
//			validator.validate(validCandidate,  result, Candidate.class);
			if (result.hasErrors()){
				System.out.println("\n\n\n\n\nFehler im Kandidaten\n\n\n\n\n");
				return "frontend/candidate";
			}else{
				//Candidate needs to be saved anyway before skills and documents can be saved. 
				//So here is where it happens.
				getCurrentSession().setAttribute("candidate", candidateRepository.save(fillCandidateWithData(validCandidate)));
				return "redirect:/skills";
			}
		} else if (buttonPressed.equals("contactData_Back")) {
			return "frontend/unexpectedAction";
//		}else  if (buttonPressed.equals("contactSkills_Forward")) {
//			Candidate candidateDB = candidateRepository.save(getCandidateFromSession());
//			System.out.println("\n\n\n\n\ncandidateDB.firstName: " + candidateDB.getFirstName());
//			Skills resSkills = new Skills();
//			resSkills.setInstitution("ETH");
//			resSkills.setCandidate(candidateDB);
//			Skills curSkills = skillsRepository.save(resSkills);
//			System.out.println("OK! Tried to fake save the Skills.");
//			return "frontend/candidate";
		}else {
//			Candidate curCandidate = candidateRepository.save(fillCandidateWithData(validCandidate));
//			getCurrentSession().setAttribute("candidateID", curCandidate.getId());
			return "frontend/unexpectedAction";
		}

		
	}
	
	private Candidate fillCandidateWithData(Candidate curCandidate){
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
//			validator.validate(validCandidate,  result, Candidate.class);
			if (result.hasErrors()){
//			if (false){
				System.out.println("\n\n\n\n\nFehler im Skill Sheet\n\n\n\n\n");
				return "frontend/skills";
			}else{
//				Candidate candidateDB = candidateRepository.save(getCandidateFromSession());
//				validSkills.setInstitution("ETH");
				validSkills.setCandidate(getCandidateFromSession());
				getCurrentSession().setAttribute("skills", validSkills);
				
				Skills curSkills = skillsRepository.save(validSkills);
				System.out.println("OK! Tried to save the Skills.");
				return "redirect:/documents";
			}
		}else  if (buttonPressed.equals("contactSkills_Back")) {
			//save current skills object as is, it will only be validated when pressing "forward"
			getCurrentSession().setAttribute("skills", validSkills);
			return "redirect:/";
		}else {
			return "frontend/unexpectedAction";
		}
		
//		
//		if (result.hasErrors()&& buttonPressed.equals("contactSkills_Forward")) {
//			System.out.println("\n\n\n\n\nFehler in den Skills!\n\n\n\n\n");
////			return "frontend/candidateSkills";
//		} else if (buttonPressed.equals("contactSkills_Back")) {
//			return "frontend/candidate";
//		}else {
//			Skills resSkills = new Skills();
//			resSkills.setInstitution("ETH");
//			resSkills.setCandidate(getCandidateFromSession());
//			Skills curSkills = skillsRepository.save(resSkills);
//			System.out.println("OK! Tried to save the Skills.");
////			Candidate curCandidate = candidateRepository.save(candidate);
////			System.out.println("\n\n\n\n\nCandidate ID from Repository: " + curCandidate.getId());
////			getCurrentSession().setAttribute("candidateID", curCandidate.getId());
//		}
//
//		return "frontend/candidateSkills";
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

		model.addAttribute(new CandidateSkills());
		System.out.println("\n\n\n\n\ngetDocuments\n\n\n\n\n");

		return "frontend/documents";
	}

	@RequestMapping(value = "/documents", method = RequestMethod.POST)
	public String postDocuments(Model model, @RequestParam("buttonPressed") String buttonPressed) {

		model.addAttribute(new CandidateSkills());
		System.out.println("\n\n\n\n\npostDocuments\n\n\n\n\n");

		return "frontend/documents";
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
