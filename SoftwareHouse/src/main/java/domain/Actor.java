
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String				name;
	private String				surname;
	private String				postalAddress;
	private String				phone;
	private String				email;
	private String				linkPhoto;
	private UserAccount			userAccount;
	private Collection<Thread>	threads;
	private Collection<Post>	posts;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getPostalAddress() {
		return this.postalAddress;
	}
	public void setPostalAddress(final String postalAddress) {
		this.postalAddress = postalAddress;
	}

	@Pattern(regexp = "^$|^\\+?\\d+$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@URL
	public String getLinkPhoto() {
		return this.linkPhoto;
	}

	public void setLinkPhoto(final String linkPhoto) {
		this.linkPhoto = linkPhoto;
	}

	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@Valid
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotNull
	@OneToMany(mappedBy = "writer")
	public Collection<Thread> getThreads() {
		return this.threads;
	}

	public void setThreads(final Collection<Thread> threads) {
		this.threads = threads;
	}

	@NotNull
	@OneToMany(mappedBy = "writer")
	public Collection<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(final Collection<Post> posts) {
		this.posts = posts;
	}

}
