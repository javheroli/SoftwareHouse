
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ExpertRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Contest;
import domain.Expert;
import domain.Post;
import domain.Research;

@Service
@Transactional
public class ExpertService {

	// Managed repository
	@Autowired
	private ExpertRepository		expertRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ExpertService() {
		super();
	}

	// Simple CRUD methods

	public Expert create() {
		Administrator principal;
		Expert result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<domain.Thread> threads;
		Collection<Post> posts;
		Collection<Contest> contestsForEdition;
		Collection<Contest> contestsForEvaluation;
		Collection<Research> researches;

		result = new Expert();

		threads = new ArrayList<domain.Thread>();
		posts = new ArrayList<Post>();
		contestsForEdition = new ArrayList<Contest>();
		contestsForEvaluation = new ArrayList<Contest>();
		researches = new ArrayList<Research>();

		result.setThreads(threads);
		result.setPosts(posts);
		result.setContestsForEdition(contestsForEdition);
		result.setContestsForEvaluation(contestsForEvaluation);
		result.setResearches(researches);

		return result;
	}

	public Expert save(final Expert expert) {
		Expert saved;
		Authority authority;
		Assert.notNull(expert);

		authority = new Authority();
		authority.setAuthority(Authority.EXPERT);

		Assert.isTrue(expert.getUserAccount().getAuthorities().contains(authority));
		Assert.isTrue(expert.getUserAccount().getAuthorities().size() == 1);

		if (expert.getId() == 0) {
			Administrator principal;
			principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			expert.getUserAccount().setPassword(passwordEncoder.encodePassword(expert.getUserAccount().getPassword(), null));

		} else {
			Expert principal;
			principal = this.findByPrincipal();
			Assert.isTrue(expert.getId() == principal.getId());
			Expert instrumented;
			instrumented = this.findOne(expert.getId());
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getThreads(), expert.getThreads()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getPosts(), expert.getPosts()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getContestsForEdition(), expert.getContestsForEdition()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getContestsForEvaluation(), expert.getContestsForEvaluation()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getResearches(), expert.getResearches()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getDisciplines(), expert.getDisciplines()));
		}
		saved = this.expertRepository.save(expert);
		Assert.notNull(saved);

		return saved;
	}

	public Expert findOne(final int expertId) {
		Expert result;
		result = this.expertRepository.findOne(expertId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Expert> findAll() {
		Collection<Expert> result;
		result = this.expertRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Expert findByUserAccount(final UserAccount userAccount) {
		Expert result;

		Assert.notNull(userAccount);

		result = this.expertRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Expert findByPrincipal() {
		Expert result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Expert reconstruct(final Expert result, final BindingResult binding) {
		Expert expert;
		Collection<domain.Thread> threads;
		Collection<Post> posts;
		Collection<Contest> contestsForEvaluation;
		Collection<Contest> contestsForEdition;
		Collection<Research> researches;

		if (result.getId() != 0) {

			expert = this.findOne(result.getId());
			result.setUserAccount(expert.getUserAccount());

			result.setThreads(expert.getThreads());
			result.setPosts(expert.getPosts());
			result.setContestsForEdition(expert.getContestsForEdition());
			result.setContestsForEvaluation(expert.getContestsForEvaluation());
			result.setResearches(expert.getResearches());
			result.setDisciplines(expert.getDisciplines());

		} else {
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.EXPERT);
			result.getUserAccount().addAuthority(authority);

			threads = new ArrayList<domain.Thread>();
			posts = new ArrayList<Post>();
			contestsForEdition = new ArrayList<Contest>();
			contestsForEvaluation = new ArrayList<Contest>();
			researches = new ArrayList<Research>();

			result.setThreads(threads);
			result.setPosts(posts);
			result.setContestsForEdition(contestsForEdition);
			result.setContestsForEvaluation(contestsForEvaluation);
			result.setResearches(researches);

		}

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Expert> findAllByDiscipline(final int disciplineId) {
		Collection<Expert> result;

		result = this.expertRepository.findAllByDiscipline(disciplineId);
		Assert.notNull(result);

		return result;
	}

}
