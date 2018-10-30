
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "endDate, minCost, budget, isCancelled")
})
public class Research extends DomainEntity {

	private String					title;
	private String					subject;
	private String					description;
	private String					projectWebpage;
	private Date					startDate;
	private Date					endDate;
	private double					minCost;
	private double					budget;
	private boolean					isCancelled;
	private Collection<Expert>		team;
	private Collection<Investment>	investments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@URL
	public String getProjectWebpage() {
		return this.projectWebpage;
	}

	public void setProjectWebpage(final String projectWebpage) {
		this.projectWebpage = projectWebpage;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Min(0)
	public double getMinCost() {
		return this.minCost;
	}

	public void setMinCost(final double minCost) {
		this.minCost = minCost;
	}

	@Min(0)
	public double getBudget() {
		return this.budget;
	}

	public void setBudget(final double budget) {
		this.budget = budget;
	}

	public boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(final boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	@NotNull
	@ManyToMany(mappedBy = "researches")
	public Collection<Expert> getTeam() {
		return this.team;
	}

	public void setTeam(final Collection<Expert> team) {
		this.team = team;
	}

	@NotNull
	@OneToMany(mappedBy = "research")
	public Collection<Investment> getInvestments() {
		return this.investments;
	}

	public void setInvestments(final Collection<Investment> investments) {
		this.investments = investments;
	}

}
