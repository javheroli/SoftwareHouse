
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "amount")
})
public class Investment extends DomainEntity {

	private double		amount;
	private CreditCard	creditCard;
	private Research	research;
	private Investor	investor;


	@Min(0)
	public double getAmount() {
		return this.amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
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
	public Research getResearch() {
		return this.research;
	}

	public void setResearch(final Research research) {
		this.research = research;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Investor getInvestor() {
		return this.investor;
	}

	public void setInvestor(final Investor investor) {
		this.investor = investor;
	}

}
