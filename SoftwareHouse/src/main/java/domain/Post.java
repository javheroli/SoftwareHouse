
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "publicationMoment, isDeleted")
})
public class Post extends DomainEntity {

	private Date				publicationMoment;
	private String				text;
	private List<String>		linksAttachments;
	private boolean				isBestAnswer;
	private boolean				isReliable;
	private boolean				isDeleted;
	private int					numPosts;
	private Collection<Post>	posts;
	private Post				parentPost;
	private Thread				thread;
	private Actor				writer;
	private Thread				topic;


	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotBlank
	@Column(columnDefinition = "varchar(1000)")
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@ElementCollection
	public List<String> getLinksAttachments() {
		return this.linksAttachments;
	}

	public void setLinksAttachments(final List<String> linksAttachments) {
		this.linksAttachments = linksAttachments;
	}

	public boolean getIsBestAnswer() {
		return this.isBestAnswer;
	}

	public void setIsBestAnswer(final boolean isBestAnswer) {
		this.isBestAnswer = isBestAnswer;
	}

	public boolean getIsReliable() {
		return this.isReliable;
	}

	public void setIsReliable(final boolean isReliable) {
		this.isReliable = isReliable;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(final boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Min(0)
	public int getNumPosts() {
		return this.numPosts;
	}

	public void setNumPosts(final int numPosts) {
		this.numPosts = numPosts;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentPost")
	public Collection<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(final Collection<Post> posts) {
		this.posts = posts;
	}

	@Valid
	@ManyToOne(optional = true)
	public Post getParentPost() {
		return this.parentPost;
	}

	public void setParentPost(final Post parentPost) {
		this.parentPost = parentPost;
	}

	@Valid
	@ManyToOne(optional = true)
	public Thread getThread() {
		return this.thread;
	}

	public void setThread(final Thread thread) {
		this.thread = thread;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getWriter() {
		return this.writer;
	}

	public void setWriter(final Actor writer) {
		this.writer = writer;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Thread getTopic() {
		return this.topic;
	}

	public void setTopic(final Thread topic) {
		this.topic = topic;
	}

}
