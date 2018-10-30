
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Expert extends Actor {

	private Collection<Discipline>	disciplines;
	private Collection<Contest>		contestsForEdition;
	private Collection<Contest>		contestsForEvaluation;
	private Collection<Research>	researches;


	@NotEmpty
	@ManyToMany
	public Collection<Discipline> getDisciplines() {
		return this.disciplines;
	}

	public void setDisciplines(final Collection<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	@NotNull
	@OneToMany(mappedBy = "editor")
	public Collection<Contest> getContestsForEdition() {
		return this.contestsForEdition;
	}

	public void setContestsForEdition(final Collection<Contest> contestsForEdition) {
		this.contestsForEdition = contestsForEdition;
	}

	@NotNull
	@ManyToMany(mappedBy = "judges")
	public Collection<Contest> getContestsForEvaluation() {
		return this.contestsForEvaluation;
	}

	public void setContestsForEvaluation(final Collection<Contest> contestsForEvaluation) {
		this.contestsForEvaluation = contestsForEvaluation;
	}

	@NotNull
	@ManyToMany
	public Collection<Research> getResearches() {
		return this.researches;
	}

	public void setResearches(final Collection<Research> researches) {
		this.researches = researches;
	}

}
