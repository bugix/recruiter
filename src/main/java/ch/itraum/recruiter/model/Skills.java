package ch.itraum.recruiter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@SuppressWarnings("serial")
public class Skills extends AbstractPersistable<Integer>{
	
	@OneToOne
	private Candidate candidate;
	
//	Group Education
	@NotEmpty
	private String institution;
	
	private String topic;
	
	private String degree;
	
	@Transient
	private String startDateEducationMonth;
	
	@Transient
	private String startDateEducationYear;

	private Date startDateEducation = new Date();
	
	@Transient
	private String endDateEducationMonth;
	
	@Transient
	private String endDateEducationYear;

	
	
	private Date endDateEducation = new Date();
	
	private Boolean prospectiveEnd;
	
	
//	Group Experience
	private String position;
	
	private String jobField;
	
	private String cancelationPeriod;
	
	@Transient
	private String startDateExperienceMonth;
	
	@Transient
	private String startDateExperienceYear;
	
		
	private Date startDateExperience = new Date();
	
	@Transient
	private String endDateExperienceMonth;
	
	@Transient
	private String endDateExperienceYear;
	
	
	private Date endDateExperience = new Date();
	
	
	private Boolean currentPosition;

	
	
	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Date getStartDateEducation() {
		return startDateEducation;
	}

	public void setStartDateEducation(Date startDateEducation) {
		this.startDateEducation = startDateEducation;
	}

	public Date getEndDateEducation() {
		return endDateEducation;
	}

	public void setEndDateEducation(Date endDateEducation) {
		this.endDateEducation = endDateEducation;
	}
		
	
	public Date getStartDateExperience() {
		return startDateExperience;
	}

	public void setStartDateExperience(Date startDateExperience) {
		this.startDateExperience = startDateExperience;
	}
	
	
	public Date getEndDateExperience() {
		return endDateExperience;
	}

	public void setEndDateExperience(Date endDateExperience) {
		this.endDateExperience = endDateExperience;
	}
	
	
	public Boolean getProspectiveEnd() {
		return prospectiveEnd;
	}

	public void setProspectiveEnd(Boolean prospectiveEnd) {
		this.prospectiveEnd = prospectiveEnd;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getJobField() {
		return jobField;
	}

	public void setJobField(String jobField) {
		this.jobField = jobField;
	}

	public String getCancelationPeriod() {
		return cancelationPeriod;
	}

	public void setCancelationPeriod(String cancelationPeriod) {
		this.cancelationPeriod = cancelationPeriod;
	}



	public Boolean getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Boolean currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	
	
//	StartDateEducation
	public String getStartDateEducationMonth() {
		
		String month = String.valueOf(startDateEducation.getMonth());

		return month;
	}

	public void setStartDateEducationMonth(String startDateEducationMonth) {
		startDateEducation.setMonth(Integer.parseInt(startDateEducationMonth));
	}

	public String getStartDateEducationYear() {

		String year = String.valueOf(startDateEducation.getYear() + 1900);

		return year;
	}

	public void setStartDateEducationYear(String startDateEducationYear) {
		
		startDateEducation.setYear(Integer.parseInt(startDateEducationYear) - 1900);

	}
	
	
//	EndDateEducation
	public String getEndDateEducationMonth() {
		
		String month = String.valueOf(endDateEducation.getMonth());

		return month;
	}

	public void setEndDateEducationMonth(String endDateEducationMonth) {
		endDateEducation.setMonth(Integer.parseInt(endDateEducationMonth));
	}

	public String getEndDateEducationYear() {

		String year = String.valueOf(endDateEducation.getYear() + 1900);

		return year;
	}

	public void setEndDateEducationYear(String endDateEducationYear) {
		
		endDateEducation.setYear(Integer.parseInt(endDateEducationYear) - 1900);

	}
	
	
	
//	StartDateExperience
	
		
	public String getStartDateExperienceMonth() {
		
		String month = String.valueOf(startDateExperience.getMonth());

		return month;
	}
		
	public void setStartDateExperienceMonth(String startDateExperienceMonth) {
		
		startDateExperience.setMonth(Integer.parseInt(startDateExperienceMonth));
	}


	public String getStartDateExperienceYear() {

		String year = String.valueOf(startDateExperience.getYear() + 1900);

		return year;
	}

	public void setStartDateExperienceYear(String startDateExperienceYear) {
		
		startDateExperience.setYear(Integer.parseInt(startDateExperienceYear) - 1900);

	}
	
		
	
	
//	EndDateExperience
	
	public String getEndDateExperienceMonth() {
		
		String month = String.valueOf(endDateExperience.getMonth());

		return month;
	}

	public void setEndDateExperienceMonth(String endDateExperienceMonth) {
		endDateExperience.setMonth(Integer.parseInt(endDateExperienceMonth));
	}
	
	
	public String getEndDateExperienceYear() {

		String year = String.valueOf(endDateExperience.getYear() + 1900);

		return year;
	}

	public void setEndDateExperienceYear(String endDateExperienceYear) {
		
		endDateExperience.setYear(Integer.parseInt(endDateExperienceYear) - 1900);

	}
	
		
	
}
