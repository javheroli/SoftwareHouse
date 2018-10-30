
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.InvestmentRepository;
import domain.Actor;
import domain.Investment;
import domain.Investor;
import domain.Research;

@Service
@Transactional
public class InvestmentService {

	// Managed repository
	@Autowired
	private InvestmentRepository	investmentRepository;

	// Supporting services

	@Autowired
	private InvestorService			investorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	// Constructors
	public InvestmentService() {
		super();
	}

	// Simple CRUD methods

	public Investment findOne(final int investmentId) {
		Investment result;
		result = this.investmentRepository.findOne(investmentId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Investment> findAll() {
		Collection<Investment> result;
		result = this.investmentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Investment> findAllByPrincipal() {
		Collection<Investment> result;
		Investor principal;

		principal = this.investorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.investmentRepository.findAllByInvestor(principal.getId());
		Assert.notNull(result);
		return result;
	}

	public Investment create() {
		Investment result;
		Investor principal;

		principal = this.investorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Investment();
		result.setInvestor(principal);

		return result;
	}

	public Investment save(final Investment investment) {
		Investment saved;
		Investor principal;
		Calendar calendar;
		Research research;

		Collection<Investment> investments, updated;

		Assert.notNull(investment);

		principal = this.investorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(!investment.getResearch().getIsCancelled());
		Assert.isNull(investment.getResearch().getEndDate());

		calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		year = year % 100;
		month = month + 1;

		Assert.isTrue(year <= investment.getCreditCard().getExpirationYear());
		if (year == investment.getCreditCard().getExpirationYear())
			Assert.isTrue(month < investment.getCreditCard().getExpirationMonth());

		research = investment.getResearch();

		saved = this.investmentRepository.save(investment);

		Assert.notNull(saved);

		investments = principal.getInvestments();
		updated = new ArrayList<Investment>(investments);
		updated.add(saved);
		principal.setInvestments(updated);

		investments = research.getInvestments();
		updated = new ArrayList<Investment>(investments);
		updated.add(saved);
		research.setInvestments(updated);

		research.setBudget(research.getBudget() + investment.getAmount());

		if (research.getStartDate() == null && research.getBudget() >= research.getMinCost()) {
			Date actualDate;
			actualDate = new Date(System.currentTimeMillis() - 1);
			research.setStartDate(actualDate);
		}

		return saved;
	}

	// Other business methods

	public Double getTotalAmountInvestedByInvestor(final int investorId) {
		Double result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.investmentRepository.getTotalAmountInvestedByInvestor(investorId);

		if (result == null)
			result = 0.;

		return result;

	}

	public Investment reconstruct(final Investment result, final BindingResult binding) {

		Investor principal;
		principal = this.investorService.findByPrincipal();
		result.setInvestor(principal);

		this.validator.validate(result, binding);
		return result;
	}
}
