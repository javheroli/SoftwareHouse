
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

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Contest;
import domain.Manager;
import domain.Post;

@Service
@Transactional
public class ManagerService {

	// Managed repository
	@Autowired
	private ManagerRepository		managerRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ManagerService() {
		super();
	}

	// Simple CRUD methods

	public Manager create() {
		Administrator principal;
		Manager result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<domain.Thread> threads;
		Collection<Post> posts;
		Collection<Contest> contests;

		result = new Manager();

		threads = new ArrayList<domain.Thread>();
		posts = new ArrayList<Post>();
		contests = new ArrayList<Contest>();

		result.setThreads(threads);
		result.setPosts(posts);
		result.setContests(contests);

		return result;
	}

	public Manager save(final Manager manager) {
		Manager saved;
		Authority authority;
		Assert.notNull(manager);

		authority = new Authority();
		authority.setAuthority(Authority.MANAGER);

		Assert.isTrue(manager.getUserAccount().getAuthorities().contains(authority));
		Assert.isTrue(manager.getUserAccount().getAuthorities().size() == 1);

		if (manager.getId() == 0) {
			Administrator principal;
			principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			manager.getUserAccount().setPassword(passwordEncoder.encodePassword(manager.getUserAccount().getPassword(), null));

		} else {
			Manager principal;
			principal = this.findByPrincipal();
			Assert.isTrue(manager.getId() == principal.getId());
			Manager instrumented;
			instrumented = this.findOne(manager.getId());
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getThreads(), manager.getThreads()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getPosts(), manager.getPosts()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getContests(), manager.getContests()));
		}
		saved = this.managerRepository.save(manager);
		Assert.notNull(saved);

		return saved;
	}

	public Manager findOne(final int managerId) {
		Manager result;
		result = this.managerRepository.findOne(managerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Manager> findAll() {
		Collection<Manager> result;
		result = this.managerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Manager findByUserAccount(final UserAccount userAccount) {
		Manager result;

		Assert.notNull(userAccount);

		result = this.managerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Manager reconstruct(final Manager result, final BindingResult binding) {
		Manager manager;
		Collection<domain.Thread> threads;
		Collection<Post> posts;
		Collection<Contest> contests;

		if (result.getId() != 0) {
			manager = this.findOne(result.getId());
			result.setUserAccount(manager.getUserAccount());

			result.setThreads(manager.getThreads());
			result.setPosts(manager.getPosts());
			result.setContests(manager.getContests());

		} else {
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.MANAGER);
			result.getUserAccount().addAuthority(authority);

			threads = new ArrayList<domain.Thread>();
			posts = new ArrayList<Post>();
			contests = new ArrayList<Contest>();

			result.setThreads(threads);
			result.setPosts(posts);
			result.setContests(contests);

		}

		this.validator.validate(result, binding);

		return result;
	}
}
