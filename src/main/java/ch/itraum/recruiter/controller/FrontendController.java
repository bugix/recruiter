package ch.itraum.recruiter.controller;

import java.io.DataInputStream;
import java.io.IOException;

import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.itraum.recruiter.model.Candidate;
import ch.itraum.recruiter.model.Document;
import ch.itraum.recruiter.repository.CandidateRepository;
import ch.itraum.recruiter.repository.DocumentRepository;

@Controller
public class FrontendController {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getCandidate(Model model) {

		model.addAttribute(new Candidate());

		return "frontend/candidate";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postCandidate(@Valid Candidate candidate,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "frontend/candidate";
		} else {
			candidateRepository.save(candidate);
		}

		return "frontend/documents";
	}

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String getDocuments(Model model) {

		// model.addAttribute(new Candidate());

		return "frontend/documents";
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

		documentRepository.save(document);
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
