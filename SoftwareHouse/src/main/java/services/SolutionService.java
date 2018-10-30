
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SolutionRepository;
import domain.Actor;
import domain.Administrator;
import domain.Answer;
import domain.Application;
import domain.Apprentice;
import domain.Contest;
import domain.Expert;
import domain.Problem;
import domain.Solution;

@Service
@Transactional
public class SolutionService {

	// Managed repository
	@Autowired
	private SolutionRepository	solutionRepository;

	// Supporting services

	@Autowired
	private ExpertService		expertService;

	@Autowired
	private ContestService		contestService;

	@Autowired
	private ApprenticeService	apprenticeService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private ProblemService		problemService;

	@Autowired
	private Validator			validator;


	// Constructors
	public SolutionService() {
		super();
	}

	// Simple CRUD methods
	public Solution create(final int applicationId) {
		final Solution result;
		final Application application;
		Collection<Problem> problems;
		final List<Answer> answers = new ArrayList<Answer>();

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		problems = application.getContest().getProblems();

		result = new Solution();
		result.setApplication(application);

		for (final Problem p : problems) {
			final Answer a = new Answer();
			a.setNumber(p.getNumber());
			answers.add(a);

		}
		result.setAnswers(answers);

		return result;

	}

	public Solution save(final Solution solution) {
		final Solution saved;
		final Actor principal;
		double markFinal = 0.;
		int number = 1;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (principal instanceof Apprentice) {
			Assert.isTrue(solution.getMark() == null);
			Assert.isTrue(solution.getAnswers().size() == solution.getApplication().getContest().getProblems().size());

			for (final Answer a : solution.getAnswers()) {
				Assert.isTrue(a.getMark() == null);
				a.setNumber(number);
				final Problem p = this.problemService.findByContestAndNumber(solution.getApplication().getContest().getId(), a.getNumber());
				a.setProblem(p);
				number++;
			}

			Assert.isTrue(solution.getApplication().getStatus().equals("ACCEPTED"));
			Assert.isTrue(solution.getApplication().getApplicant() == principal);
			Assert.isTrue(solution.getApplication().getContest().getStartMoment().before(new Date(System.currentTimeMillis())));
			Assert.isTrue(solution.getApplication().getContest().getEndMoment().after(new Date(System.currentTimeMillis())));

			saved = this.solutionRepository.save(solution);

		} else {
			Assert.isTrue(solution.getId() != 0);
			Assert.isTrue(solution.getMark() == null);
			Assert.isTrue(solution.getAnswers().size() == solution.getApplication().getContest().getProblems().size());
			Assert.isTrue(solution.getApplication().getContest().getJudges().contains(principal));

			Assert.isTrue(solution.getApplication().getContest().getStartMoment().before(new Date(System.currentTimeMillis())));
			Assert.isTrue(solution.getApplication().getContest().getEndMoment().before(new Date(System.currentTimeMillis())));

			for (final Answer a : solution.getAnswers()) {
				a.setNumber(number);
				final Problem p = this.problemService.findByContestAndNumber(solution.getApplication().getContest().getId(), a.getNumber());
				Assert.isTrue(a.getMark() != null);
				Assert.isTrue(a.getMark() <= p.getMark());
				markFinal += a.getMark();
				number++;
			}
			solution.setMark(markFinal);

			saved = this.solutionRepository.save(solution);
		}

		return saved;

	}

	public Solution findOne(final int solutionId) {
		Solution result;

		result = this.solutionRepository.findOne(solutionId);
		Assert.notNull(result);

		return result;
	}

	public Solution reconstruct(final Solution result, final BindingResult binding) {
		Solution solution;
		Actor principal;
		final List<Answer> answers;
		int i = 0;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (result.getId() != 0) {
			solution = this.findOne(result.getId());
			result.setApplication(solution.getApplication());

			answers = solution.getAnswers();
			for (final Answer a : answers) {
				final Answer ans = result.getAnswers().get(i);
				ans.setProblem(a.getProblem());

				if (principal instanceof Expert) {
					ans.setText(a.getText());
					i++;
					this.validator.validate(ans, binding);
				}
			}

		}

		this.validator.validate(result, binding);

		return result;

	}
	//Other bussiness methods

	public Collection<Solution> findAllByContest(final int contestId) {
		Collection<Solution> result;
		Expert principal;
		Contest contest;

		contest = this.contestService.findOne(contestId);
		Assert.isTrue(contest.getEndMoment().before(new Date(System.currentTimeMillis())));
		Assert.isTrue(!contest.getIsDraft());

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		result = this.solutionRepository.findAllByContest(contestId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Solution> findAllByPrincipalApprentice() {
		Collection<Solution> result;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.solutionRepository.findAllByApprentice(principal.getId());
		Assert.notNull(result);

		return result;

	}

	public Solution findByApprenticePrincipalAndContest(final int contestId) {
		Solution result;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.solutionRepository.findByApprenticeAndContest(principal.getId(), contestId);

		return result;
	}

	public void flush() {
		this.solutionRepository.flush();
	}

	//Dashboard methods
	
	public Double getAvgMarkByContest(final int contestId) {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.solutionRepository.getAvgMarkByContest(contestId);

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMinMarkByContest(final int contestId) {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.solutionRepository.getMinMarkByContest(contestId);

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMaxMarkByContest(final int contestId) {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.solutionRepository.getMaxMarkByContest(contestId);

		if (result == null)
			result = 0.0;

		return result;
	}

}
