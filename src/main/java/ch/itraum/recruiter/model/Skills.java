package ch.itraum.recruiter.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Date;


@Entity
@SuppressWarnings("serial")
public class Skills extends AbstractPersistable<Integer>{
	
	@OneToOne
	private Candidate candidate;
	
	
//	Group Education
	private String institution;
	
	private String topic;
	
	private String degree;
	
	private Date startDateEducation;
	
	private Date endDateEducation;
	
	private Boolean prospectiveEnd;
	
	
//	Group Experience
	private String position;
	
	private String jobField;
	
	private String cancelationPeriod;
	
	private Date startDateExperience;
	
	private Date endDateExperience;
	
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

	public Boolean getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Boolean currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	
}
