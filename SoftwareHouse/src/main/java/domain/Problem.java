
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "number")
})
public class Problem extends DomainEntity {

	private int		number;
	private String	statement;
	private String	linkPicture;
	private double	mark;
	private Contest	contest;


	@Min(0)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@URL
	public String getLinkPicture() {
		return this.linkPicture;
	}

	public void setLinkPicture(final String linkPicture) {
		this.linkPicture = linkPicture;
	}

	@DecimalMax("10.0")
	@DecimalMin("0.0")
	public double getMark() {
		return this.mark;
	}

	public void setMark(final double mark) {
		this.mark = mark;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Contest getContest() {
		return this.contest;
	}

	public void setContest(final Contest contest) {
		this.contest = contest;
	}

}
