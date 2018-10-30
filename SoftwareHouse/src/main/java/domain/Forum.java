
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Forum extends DomainEntity {

	private String				name;
	private String				description;
	private String				linkPicture;
	private Collection<Thread>	threads;
	private int					numPosts;
	private Post				lastPost;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Length(min = 1, max = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	public String getLinkPicture() {
		return this.linkPicture;
	}

	public void setLinkPicture(final String linkPicture) {
		this.linkPicture = linkPicture;
	}

	@NotNull
	@OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
	public Collection<Thread> getThreads() {
		return this.threads;
	}

	public void setThreads(final Collection<Thread> threads) {
		this.threads = threads;
	}

	@Min(0)
	public int getNumPosts() {
		return this.numPosts;
	}

	public void setNumPosts(final int numPosts) {
		this.numPosts = numPosts;
	}

	@Valid
	@OneToOne(optional = true)
	public Post getLastPost() {
		return this.lastPost;
	}

	public void setLastPost(final Post lastPost) {
		this.lastPost = lastPost;
	}

}
