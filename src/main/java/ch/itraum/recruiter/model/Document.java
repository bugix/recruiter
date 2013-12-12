package ch.itraum.recruiter.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@SuppressWarnings("serial")
public class Document extends AbstractPersistable<Integer> {
	
	@NotEmpty
	private String name;
	
	@NotNull
	@Lob
	private byte[] content;

	@NotNull
	@ManyToOne
	private Candidate candidate;

		public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
}
