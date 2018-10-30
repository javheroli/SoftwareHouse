
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

import repositories.ContestRepository;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Apprentice;
import domain.Contest;
import domain.Expert;
import domain.Investor;
import domain.Manager;
import domain.Problem;
import domain.Solution;

@Service
@Transactional
public class ContestService {

	// Managed repository
	@Autowired
	private ContestRepository		contestRepository;

	// Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExpertService			expertService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SolutionService			solutionService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ContestService() {
		super();
	}

	// Simple CRUD methods
	public Contest create() {
		Contest result;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Contest();
		result.setProblems(new ArrayList<Problem>());
		result.setManager(principal);
		result.setJudges(new ArrayList<Expert>());
		result.setApplications(new ArrayList<Application>());
		result.setIsDraft(true);

		return result;

	}

	public Contest save(final Contest contest, final boolean isDraft) {
		final Contest result;
		Manager principal;
		Double sumMarksProblems = 0.;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == contest.getManager().getId());
		Assert.isTrue(contest.getApplications().size() == 0);
		Assert.isTrue(contest.getIsDraft());

		if (!isDraft) {
			Assert.isTrue(contest.getId() != 0);
			Assert.isTrue(contest.getProblems().size() > 0);

			for (final Problem p : contest.getProblems())
				sumMarksProblems += p.getMark();
			Assert.isTrue(sumMarksProblems == 10.);
		}

		Assert.isTrue(contest.getStartMoment().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(contest.getEndMoment().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(contest.getEndMoment().after(contest.getStartMoment()));

		Assert.isTrue(contest.getEditor().getDisciplines().contains(contest.getDiscipline()));
		for (final Expert e : contest.getJudges())
			Assert.isTrue(e.getDisciplines().contains(contest.getDiscipline()));

		contest.setIsDraft(isDraft);

		result = this.contestRepository.save(contest);

		for (final String r : contest.getRules())
			if (r.equals(""))
				result.getRules().remove(r);

		principal.getContests().add(result);
		result.getEditor().getContestsForEdition().add(result);
		for (final Expert e : result.getJudges())
			e.getContestsForEvaluation().add(result);

		return result;

	}

	public void delete(final Contest contest) {
		Manager principal;

		Assert.isTrue(contest.getIsDraft());
		Assert.isTrue(contest.getId() != 0);

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == contest.getManager().getId());

		this.contestRepository.delete(contest);

		principal.getContests().remove(contest);
		contest.getEditor().getContestsForEdition().remove(contest);
		for (final Expert e : contest.getJudges())
			e.getContestsForEvaluation().remove(contest);

	}

	public Contest findOne(final int contestId) {
		Contest result;

		result = this.contestRepository.findOne(contestId);
		Assert.notNull(result);

		return result;
	}

	public Contest reconstruct(final Contest result, final BindingResult binding) {
		final Contest contest;
		Manager principal;

		result.setApplications(new ArrayList<Application>());

		if (result.getId() == 0) {
			principal = this.managerService.findByPrincipal();
			Assert.notNull(principal);

			result.setIsDraft(true);
			result.setManager(principal);
			result.setProblems(new ArrayList<Problem>());
		} else {
			contest = this.contestRepository.findOne(result.getId());

			result.setProblems(contest.getProblems());
			result.setDiscipline(contest.getDiscipline());
			result.setEditor(contest.getEditor());
			result.setJudges(contest.getJudges());
			result.setManager(contest.getManager());
			result.setIsDraft(contest.getIsDraft());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.contestRepository.flush();
	}

	//Other bussiness methods

	public Collection<Contest> findAllPublished() {
		final Collection<Contest> result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(!(principal instanceof Investor));

		result = this.contestRepository.findAllPublished();
		Assert.notNull(result);

		return result;

	}

	public Collection<Contest> findAllCreatedByPrincipalManager() {
		final Collection<Contest> result;
		final Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.findAllCreatedByManager(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Contest> findAllPrincipalChosenAsEditor() {
		final Collection<Contest> result;
		final Expert principal;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.findAllDraftByEditor(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Contest> findAllPendingToAssessPrincipalIsJudge() {
		final Collection<Contest> result;
		final Expert principal;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.findAllFinishedPendingToAssessByJudge(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public boolean hasContestWinner(final int contestId) {
		boolean result = true;
		Collection<Solution> solutionsNotAssessed;
		Contest contest;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		contest = this.findOne(contestId);
		Assert.notNull(contest);

		solutionsNotAssessed = this.contestRepository.findSolutionsNotAssessedByContest(contestId);
		Assert.notNull(solutionsNotAssessed);

		if (solutionsNotAssessed.size() > 0 || contest.getApplications().size() == 0)
			result = false;

		return result;
	}

	public Collection<Apprentice> findWinnersByContest(final int contestId) {
		Collection<Apprentice> result;

		result = this.contestRepository.findWinnersByContest(contestId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Contest> findAllByDiscipline(final int disciplineId) {
		Collection<Contest> result;

		result = this.contestRepository.findAllByDiscipline(disciplineId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Contest> findAllForAdmin() {
		Collection<Contest> result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	//Dashboard methods

	public Double getAvgApplicationsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.avgApplicationsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMinApplicationsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.minApplicationsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMaxApplicationsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.maxApplicationsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getStdDeviationApplicationsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.stdDeviationApplicationsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getAvgProblemsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.avgProblemsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMinProblemsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.minProblemsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMaxProblemsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.maxProblemsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getStdDeviationProblemsPerContest() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.stdDeviationProblemsPerContest();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Collection<Contest> findEvaluatedContests() {
		Collection<Contest> result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.contestRepository.findEvaluatedContests();
		Assert.notNull(result);

		return result;

	}

	public List<List<Double>> getAvgMinMaxMarksForEvaluatedContests() {
		Collection<Contest> contests;
		List<List<Double>> result;
		List<Double> avgMarks, minMarks, maxMarks;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		contests = this.contestRepository.findEvaluatedContests();
		Assert.notNull(contests);

		result = new ArrayList<List<Double>>();
		avgMarks = new ArrayList<Double>();
		minMarks = new ArrayList<Double>();
		maxMarks = new ArrayList<Double>();

		for (final Contest contest : contests) {
			avgMarks.add(this.solutionService.getAvgMarkByContest(contest.getId()));
			minMarks.add(this.solutionService.getMinMarkByContest(contest.getId()));
			maxMarks.add(this.solutionService.getMaxMarkByContest(contest.getId()));
		}

		result.add(avgMarks);
		result.add(minMarks);
		result.add(maxMarks);

		return result;

	}

}
