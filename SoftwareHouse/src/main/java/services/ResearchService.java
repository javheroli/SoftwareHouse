
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ResearchRepository;
import domain.Administrator;
import domain.Expert;
import domain.Investment;
import domain.Investor;
import domain.Research;

@Service
@Transactional
public class ResearchService {

	// Managed repository
	@Autowired
	private ResearchRepository		researchRepository;

	// Supporting services

	@Autowired
	private ExpertService			expertService;

	@Autowired
	private InvestorService			investorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ResearchService() {
		super();
	}

	// Simple CRUD methods

	public Research findOne(final int researchId) {
		Research result;
		result = this.researchRepository.findOne(researchId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Research> findAll() {
		Collection<Research> result;
		result = this.researchRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Research create() {
		Research result;
		Expert principal;
		Collection<Investment> investments;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		result = new Research();
		result.setStartDate(null);
		result.setEndDate(null);
		result.setBudget(0);
		result.setIsCancelled(false);

		investments = new ArrayList<Investment>();
		result.setInvestments(investments);

		return result;
	}

	public Research save(final Research research) {
		Research saved;
		Expert principal;
		Collection<Research> researches, updated;

		Assert.notNull(research);

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		if (research.getId() == 0) {
			Assert.isTrue(!research.getTeam().contains(principal));
			research.getTeam().add(principal);
		} else {

			Assert.isTrue(research.getTeam().contains(principal));

			Research instrumented;

			instrumented = this.findOne(research.getId());
			Assert.isTrue(research.getStartDate() == instrumented.getStartDate());
			Assert.isTrue(research.getEndDate() == instrumented.getEndDate());
			Assert.isTrue(research.getIsCancelled() == instrumented.getIsCancelled());
			Assert.isTrue(CollectionUtils.isEqualCollection(research.getInvestments(), instrumented.getInvestments()));
			Assert.isTrue(research.getBudget() == instrumented.getBudget());
			Assert.isTrue(CollectionUtils.isEqualCollection(research.getTeam(), instrumented.getTeam()));

		}

		if (research.getStartDate() == null && research.getBudget() >= research.getMinCost()) {
			Date actualDate;
			actualDate = new Date(System.currentTimeMillis() - 1);
			research.setStartDate(actualDate);

		}

		saved = this.researchRepository.save(research);

		Assert.notNull(saved);

		if (research.getId() == 0)
			for (final Expert expert : saved.getTeam()) {
				researches = expert.getResearches();
				updated = new ArrayList<Research>(researches);
				updated.add(saved);
				expert.setResearches(updated);
			}

		return saved;
	}

	// Other business methods

	public Collection<Research> findAllByExpert() {
		Collection<Research> result;
		Expert principal;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		result = this.researchRepository.findAllByExpert(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Research> findAllThatNeedFunding() {
		Collection<Research> result;
		Investor principal;

		principal = this.investorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.researchRepository.findAllThatNeedFunding();
		Assert.notNull(result);

		return result;
	}

	public void cancel(final int researchId) {
		Research research;
		Expert principal;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		research = this.findOne(researchId);
		Assert.notNull(research);

		Assert.isTrue(research.getTeam().contains(principal));

		Assert.notNull(research.getStartDate());
		Assert.isNull(research.getEndDate());

		Assert.isTrue(!research.getIsCancelled());

		research.setIsCancelled(true);

	}

	public void finalize(final int researchId) {
		Research research;
		Expert principal;
		Date actualDate;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		research = this.findOne(researchId);
		Assert.notNull(research);

		Assert.isTrue(research.getTeam().contains(principal));

		Assert.notNull(research.getStartDate());
		Assert.isNull(research.getEndDate());

		Assert.isTrue(!research.getIsCancelled());

		actualDate = new Date(System.currentTimeMillis() - 1);
		research.setEndDate(actualDate);

	}

	public Research reconstruct(final Research result, final BindingResult binding) {

		if (result.getId() == 0) {
			Collection<Investment> investments;

			result.setStartDate(null);
			result.setEndDate(null);
			result.setBudget(0);
			result.setIsCancelled(false);

			investments = new ArrayList<Investment>();
			result.setInvestments(investments);

			if (result.getTeam() == null) {
				Collection<Expert> team;
				team = new ArrayList<Expert>();
				result.setTeam(team);
			}

		} else {
			Research research;
			research = this.findOne(result.getId());
			result.setBudget(research.getBudget());
			result.setStartDate(research.getStartDate());
			result.setEndDate(research.getEndDate());
			result.setInvestments(research.getInvestments());
			result.setIsCancelled(research.getIsCancelled());
			result.setTeam(research.getTeam());
			if (result.getStartDate() != null)
				result.setMinCost(research.getMinCost());
		}

		this.validator.validate(result, binding);
		return result;
	}

	public Double getRatioResearchesNotStarted() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.researchRepository.ratioResearchesNotStarted();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getAvgInvestmentsPerResearch() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.researchRepository.avgInvestmentsPerResearch();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMinInvestmentsPerResearch() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.researchRepository.minInvestmentsPerResearch();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMaxInvestmentsPerResearch() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.researchRepository.maxInvestmentsPerResearch();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getStdDeviationInvestmentsPerResearch() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.researchRepository.stdDeviationInvestmentsPerResearch();

		if (result == null)
			result = 0.0;

		return result;
	}

}
