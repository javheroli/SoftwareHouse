
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

import repositories.ApprenticeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Apprentice;
import domain.Post;
import forms.ActorForm;

@Service
@Transactional
public class ApprenticeService {

	// Managed repository
	@Autowired
	private ApprenticeRepository	apprenticeRepository;

	// Supporting services

	@Autowired
	private Validator				validator;


	// Constructors
	public ApprenticeService() {
		super();
	}

	// Simple CRUD methods

	public Apprentice create() {
		Apprentice result;
		Collection<Application> applications;
		Collection<domain.Thread> threads;
		Collection<Post> posts;

		result = new Apprentice();

		applications = new ArrayList<Application>();
		threads = new ArrayList<domain.Thread>();
		posts = new ArrayList<Post>();

		result.setApplications(applications);
		result.setThreads(threads);
		result.setPosts(posts);
		result.setPoints(0);

		return result;
	}

	public Apprentice save(final Apprentice apprentice) {
		Apprentice saved;
		Authority authority;
		Assert.notNull(apprentice);

		authority = new Authority();
		authority.setAuthority(Authority.APPRENTICE);

		Assert.isTrue(apprentice.getUserAccount().getAuthorities().contains(authority));
		Assert.isTrue(apprentice.getUserAccount().getAuthorities().size() == 1);

		if (apprentice.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			apprentice.getUserAccount().setPassword(passwordEncoder.encodePassword(apprentice.getUserAccount().getPassword(), null));
		} else {
			Apprentice principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(apprentice.getId() == principal.getId());
			Apprentice instrumented;
			instrumented = this.findOne(apprentice.getId());
			Assert.isTrue(instrumented.getPoints() == apprentice.getPoints());
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getThreads(), apprentice.getThreads()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getPosts(), apprentice.getPosts()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getApplications(), apprentice.getApplications()));

		}
		saved = this.apprenticeRepository.save(apprentice);
		Assert.notNull(saved);

		return saved;
	}

	public Apprentice findOne(final int apprenticeId) {
		Apprentice result;
		result = this.apprenticeRepository.findOne(apprenticeId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Apprentice> findAll() {
		Collection<Apprentice> result;
		result = this.apprenticeRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Apprentice findByUserAccount(final UserAccount userAccount) {
		Apprentice result;

		Assert.notNull(userAccount);

		result = this.apprenticeRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Apprentice findByPrincipal() {
		Apprentice result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Apprentice reconstruct(final Apprentice result, final BindingResult binding) {
		Apprentice apprentice;

		apprentice = this.findOne(result.getId());
		result.setThreads(apprentice.getThreads());
		result.setPosts(apprentice.getPosts());
		result.setUserAccount(apprentice.getUserAccount());
		result.setApplications(apprentice.getApplications());
		result.setPoints(apprentice.getPoints());

		this.validator.validate(result, binding);

		return result;
	}

	public Apprentice reconstruct(final ActorForm actorForm, final BindingResult binding) {
		Apprentice result;
		final Collection<Application> applications;
		final Collection<domain.Thread> threads;
		final Collection<Post> posts;

		result = this.create();

		final Authority a = new Authority();
		a.setAuthority(Authority.APPRENTICE);

		final UserAccount ua = actorForm.getUserAccount();
		if (ua != null)
			ua.addAuthority(a);

		result.setUserAccount(actorForm.getUserAccount());
		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());
		result.setLinkPhoto(actorForm.getLinkPhoto());
		result.setPostalAddress(actorForm.getPostalAddress());

		applications = new ArrayList<Application>();
		threads = new ArrayList<domain.Thread>();
		posts = new ArrayList<Post>();

		result.setApplications(applications);
		result.setThreads(threads);
		result.setPosts(posts);

		result.setPoints(0);

		this.validator.validate(result, binding);

		if ((!actorForm.getUserAccount().getPassword().equals(actorForm.getConfirmPassword())) || !actorForm.getTermsAndConditions())
			result = null;

		return result;

	}

}
