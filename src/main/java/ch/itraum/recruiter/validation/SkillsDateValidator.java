package ch.itraum.recruiter.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import ch.itraum.recruiter.model.Skills;

public class SkillsDateValidator implements Validator {

    /**
    * This Validator validates just Skills instances
    */
	@Override
    public boolean supports(Class clazz) {
        return Skills.class.equals(clazz);
    }

	@Override
	public void validate(Object target, Errors errors) {
		Skills skills = (Skills)target;
		if(skills.getStartDateEducation().compareTo(skills.getEndDateEducation()) >= 0){
			errors.reject("educationEndsBeforeStart", "End must be later than Start");
		}
		if(!skills.getHasNoExperience() && skills.getStartDateExperience().compareTo(skills.getEndDateExperience()) >= 0){
			errors.reject("experienceEndsBeforeStart", "End must be later than Start");
		}
	}

}
