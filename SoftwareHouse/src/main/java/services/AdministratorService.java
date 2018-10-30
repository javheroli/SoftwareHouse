
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

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Post;

@Service
@Transactional
public class AdministratorService {

	// Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services

	@Autowired
	private Validator				validator;


	// Constructors
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods

	public Administrator create() {
		Administrator principal;
		Administrator result;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		Collection<domain.Thread> threads;
		Collection<Post> posts;

		result = new Administrator();

		threads = new ArrayList<domain.Thread>();
		posts = new ArrayList<Post>();

		result.setThreads(threads);
		result.setPosts(posts);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator saved;
		Authority authority;
		Assert.notNull(administrator);

		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);

		Administrator principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(administrator.getUserAccount().getAuthorities().contains(authority));
		Assert.isTrue(administrator.getUserAccount().getAuthorities().size() == 1);

		if (administrator.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			administrator.getUserAccount().setPassword(passwordEncoder.encodePassword(administrator.getUserAccount().getPassword(), null));
		} else {
			Assert.isTrue(administrator.getId() == principal.getId());
			Administrator instrumented;
			instrumented = this.findOne(administrator.getId());
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getThreads(), administrator.getThreads()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getPosts(), administrator.getPosts()));
		}
		saved = this.administratorRepository.save(administrator);
		Assert.notNull(saved);

		return saved;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Administrator result;

		Assert.notNull(userAccount);

		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator reconstruct(final Administrator result, final BindingResult binding) {
		Administrator administrator;
		Collection<domain.Thread> threads;
		Collection<Post> posts;

		if (result.getId() != 0) {
			administrator = this.findOne(result.getId());
			result.setUserAccount(administrator.getUserAccount());

			result.setThreads(administrator.getThreads());
			result.setPosts(administrator.getPosts());

		} else {
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.ADMIN);
			result.getUserAccount().addAuthority(authority);

			threads = new ArrayList<domain.Thread>();
			posts = new ArrayList<Post>();

			result.setThreads(threads);
			result.setPosts(posts);

		}

		this.validator.validate(result, binding);

		return result;
	}
}
