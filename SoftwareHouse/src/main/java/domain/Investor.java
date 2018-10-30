
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Investor extends Actor {

	private Collection<Investment>	investments;


	@NotNull
	@OneToMany(mappedBy = "investor")
	public Collection<Investment> getInvestments() {
		return this.investments;
	}

	public void setInvestments(final Collection<Investment> investments) {
		this.investments = investments;
	}

}
