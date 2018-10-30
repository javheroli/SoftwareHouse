
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "startMoment, endMoment, isDraft")
})
public class Contest extends DomainEntity {

	private String					title;
	private String					description;
	private List<String>			rules;
	private Date					startMoment;
	private Date					endMoment;
	private int						duration;
	private int						difficultyGrade;
	private int						requiredPoints;
	private boolean					isDraft;
	private int						availablePlaces;
	private double					price;
	private double					prize;
	private Collection<Problem>		problems;
	private Discipline				discipline;
	private Manager					manager;
	private Expert					editor;
	private Collection<Expert>		judges;
	private Collection<Application>	applications;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	public List<String> getRules() {
		return this.rules;
	}

	public void setRules(final List<String> rules) {
		this.rules = rules;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getEndMoment() {
		return this.endMoment;
	}

	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	@Transient
	public int getDuration() {
		this.duration = (int) ((this.getEndMoment().getTime() - this.getStartMoment().getTime()) / 60000);
		return this.duration;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}

	@Range(min = 1, max = 5)
	public int getDifficultyGrade() {
		return this.difficultyGrade;
	}

	public void setDifficultyGrade(final int difficultyGrade) {
		this.difficultyGrade = difficultyGrade;
	}

	@Min(0)
	public int getRequiredPoints() {
		return this.requiredPoints;
	}

	public void setRequiredPoints(final int requiredPoints) {
		this.requiredPoints = requiredPoints;
	}

	public boolean getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(final boolean isDraft) {
		this.isDraft = isDraft;
	}

	@Min(0)
	public int getAvailablePlaces() {
		return this.availablePlaces;
	}

	public void setAvailablePlaces(final int availablePlaces) {
		this.availablePlaces = availablePlaces;
	}

	@DecimalMin("0.0")
	@Digits(integer = 4, fraction = 2)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	@DecimalMin("0.0")
	@Digits(integer = 4, fraction = 2)
	public double getPrize() {
		return this.prize;
	}

	public void setPrize(final double prize) {
		this.prize = prize;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Discipline getDiscipline() {
		return this.discipline;
	}

	public void setDiscipline(final Discipline discipline) {
		this.discipline = discipline;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Expert getEditor() {
		return this.editor;
	}

	public void setEditor(final Expert editor) {
		this.editor = editor;
	}

	@NotEmpty
	@ManyToMany
	public Collection<Expert> getJudges() {
		return this.judges;
	}

	public void setJudges(final Collection<Expert> judges) {
		this.judges = judges;
	}

	@NotNull
	@OneToMany(mappedBy = "contest")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

}
