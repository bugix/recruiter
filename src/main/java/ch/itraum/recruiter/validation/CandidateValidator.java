package ch.itraum.recruiter.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.itraum.recruiter.model.Candidate;

public class CandidateValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Candidate.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Candidate candidate = (Candidate)target;
		
		if(candidate.getFirstName() == null){
			errors.rejectValue("", "noFirstName");
		}
	}

}
