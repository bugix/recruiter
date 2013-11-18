package ch.itraum.recruiter.model;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@SuppressWarnings("serial")
public class Job extends AbstractPersistable<Integer> {
	
	private String titel;

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}
}
