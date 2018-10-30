
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	@Index(columnList = "momentLastModification, numPosts")
})
public class Thread extends DomainEntity {

	private Date					startMoment;
	private String					title;
	private String					text;
	private List<String>			linksAttachments;
	private int						numPosts;
	private Date					momentLastModification;
	private Actor					writer;
	private Collection<Post>		posts;
	private Forum					forum;
	private Collection<Discipline>	disciplines;
	private Post					lastPost;
	private boolean					isBestAnswerEnabled;


	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
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

	@Min(0)
	public int getNumPosts() {
		return this.numPosts;
	}

	public void setNumPosts(final int numPosts) {
		this.numPosts = numPosts;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getMomentLastModification() {
		return this.momentLastModification;
	}

	public void setMomentLastModification(final Date momentLastModification) {
		this.momentLastModification = momentLastModification;
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
	@OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
	public Collection<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(final Collection<Post> posts) {
		this.posts = posts;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Forum getForum() {
		return this.forum;
	}

	public void setForum(final Forum forum) {
		this.forum = forum;
	}

	@NotNull
	@ManyToMany
	public Collection<Discipline> getDisciplines() {
		return this.disciplines;
	}

	public void setDisciplines(final Collection<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	@Valid
	@OneToOne(optional = true)
	public Post getLastPost() {
		return this.lastPost;
	}

	public void setLastPost(final Post lastPost) {
		this.lastPost = lastPost;
	}

	public boolean getIsBestAnswerEnabled() {
		return this.isBestAnswerEnabled;
	}

	public void setIsBestAnswerEnabled(final boolean isBestAnswerEnabled) {
		this.isBestAnswerEnabled = isBestAnswerEnabled;
	}

}
