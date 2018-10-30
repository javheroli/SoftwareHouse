
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "status")
})
public class Application extends DomainEntity {

	private String		ticker;
	private Date		moment;
	private String		status;
	private String		motivationLetter;
	private CreditCard	creditCard;
	private Apprentice	applicant;
	private Contest		contest;


	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^ACCEPTED|DUE|PENDING|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getMotivationLetter() {
		return this.motivationLetter;
	}

	public void setMotivationLetter(final String motivationLetter) {
		this.motivationLetter = motivationLetter;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Apprentice getApplicant() {
		return this.applicant;
	}
	public void setApplicant(final Apprentice applicant) {
		this.applicant = applicant;
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
