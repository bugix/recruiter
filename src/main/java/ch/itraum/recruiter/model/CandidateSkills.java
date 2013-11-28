package ch.itraum.recruiter.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@SuppressWarnings("serial")
public class CandidateSkills extends AbstractPersistable<Integer> {
	
	@NotEmpty
	private String education;
	
	@NotEmpty
	private String experience;

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

}
