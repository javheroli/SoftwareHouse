
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import domain.Contest;
import domain.Expert;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	// Managed Repository
	@Autowired
	private ProblemRepository	problemRepository;

	// Supporting services

	@Autowired
	private ExpertService		expertService;

	@Autowired
	private ContestService		contestService;

	@Autowired
	private Validator			validator;


	// Constructors

	public ProblemService() {
		super();
	}

	// Simple CRUD methods

	public Problem create(final int contestId) {
		Problem result;
		Contest contest;
		Expert principal;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		contest = this.contestService.findOne(contestId);
		Assert.notNull(contest);

		Assert.isTrue(contest.getEditor().getId() == principal.getId());

		result = new Problem();
		Assert.notNull(result);

		result.setContest(contest);

		return result;
	}

	public Problem findOne(final int problemId) {
		Problem result;

		result = this.problemRepository.findOne(problemId);

		Assert.notNull(result);

		return result;
	}

	public Problem save(final Problem problem) {
		final Problem result;
		Expert principal;
		int numberProblemsInContest;

		Assert.notNull(problem);

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == problem.getContest().getEditor().getId());

		Assert.isTrue(problem.getContest().getIsDraft());
		Assert.isTrue(problem.getContest().getEndMoment().after(problem.getContest().getStartMoment()));

		if (problem.getId() == 0) {
			numberProblemsInContest = problem.getContest().getProblems().size();
			problem.setNumber(numberProblemsInContest + 1);
		}

		result = this.problemRepository.save(problem);
		if (problem.getId() == 0)
			result.getContest().getProblems().add(result);

		return result;

	}

	public void delete(final Problem problem) {
		Expert principal;

		Assert.isTrue(problem.getId() != 0);

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == problem.getContest().getEditor().getId());
		Assert.isTrue(problem.getContest().getIsDraft());

		this.problemRepository.delete(problem);

		problem.getContest().getProblems().remove(problem);

		//Actualizado de los number de los problemas, hay que actualizar únicamente los que tengan un number mayor que
		//el number del problema borrado

		for (final Problem p : problem.getContest().getProblems())
			if (p.getNumber() > problem.getNumber()) {
				p.setNumber(p.getNumber() - 1);
				this.problemRepository.save(p);
			}

	}

	public Problem reconstruct(final Problem result, final BindingResult binding) {
		Problem problem;

		if (result.getId() != 0) {
			problem = this.problemRepository.findOne(result.getId());

			result.setContest(problem.getContest());
			result.setNumber(problem.getNumber());

		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.problemRepository.flush();
	}

	// Other business methods
	public Problem findByContestAndNumber(final int contestId, final int numberId) {
		Problem result;

		result = this.problemRepository.findByContestAndByNumber(contestId, numberId);

		return result;
	}
	//Dashboard methods

}
