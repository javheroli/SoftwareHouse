
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	private Collection<Contest>	contests;


	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Contest> getContests() {
		return this.contests;
	}

	public void setContests(final Collection<Contest> contests) {
		this.contests = contests;
	}

}
