
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "mark")
})
public class Solution extends DomainEntity {

	private Double			mark;
	private Application		application;
	private List<Answer>	answers;


	@Min(0)
	public Double getMark() {
		return this.mark;
	}

	public void setMark(final Double mark) {
		this.mark = mark;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(final List<Answer> answers) {
		this.answers = answers;
	}

}
