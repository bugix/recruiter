package ch.itraum.recruiter.model;

import javax.persistence.Entity;

//import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@SuppressWarnings("serial")
public class Candidate extends AbstractPersistable<Integer> {
	
	private String title;
	
	@NotEmpty
	private String firstName;
	
	
	
	@NotEmpty
	private String lastName;
	
//	@NotEmpty
	private String street;
	
//	@NotEmpty
	private Integer plz;
	
//	@NotEmpty
	private String city;
	
//	@Digits(integer=14, fraction=0)
//	@NotEmpty
	private String phoneFix;
	
//	@Digits(integer=14, fraction=0)
	private String phoneMobile;
		
	@Email
	@NotEmpty
	private String email;


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public Integer getPlz() {
		return plz;
	}


	public void setPlz(Integer plz) {
		this.plz = plz;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPhoneFix() {
		return phoneFix;
	}


	public void setPhoneFix(String phoneFix) {
		this.phoneFix = phoneFix;
	}


	public String getPhoneMobile() {
		return phoneMobile;
	}


	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
