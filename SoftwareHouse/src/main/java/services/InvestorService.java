
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

import repositories.InvestorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Investment;
import domain.Investor;
import domain.Post;
import forms.ActorForm;

@Service
@Transactional
public class InvestorService {

	// Managed repository
	@Autowired
	private InvestorRepository	investorRepository;

	// Supporting services

	@Autowired
	private Validator			validator;


	// Constructors
	public InvestorService() {
		super();
	}

	// Simple CRUD methods

	public Investor create() {
		Investor result;
		Collection<Investment> investments;
		Collection<domain.Thread> threads;
		Collection<Post> posts;

		result = new Investor();

		investments = new ArrayList<Investment>();
		threads = new ArrayList<domain.Thread>();
		posts = new ArrayList<Post>();

		result.setInvestments(investments);
		result.setThreads(threads);
		result.setPosts(posts);

		return result;
	}

	public Investor save(final Investor investor) {
		Investor saved;
		Authority authority;
		Assert.notNull(investor);

		authority = new Authority();
		authority.setAuthority(Authority.INVESTOR);

		Assert.isTrue(investor.getUserAccount().getAuthorities().contains(authority));
		Assert.isTrue(investor.getUserAccount().getAuthorities().size() == 1);

		if (investor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			investor.getUserAccount().setPassword(passwordEncoder.encodePassword(investor.getUserAccount().getPassword(), null));
		} else {
			Investor principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(investor.getId() == principal.getId());
			Investor instrumented;
			instrumented = this.findOne(investor.getId());
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getThreads(), investor.getThreads()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getPosts(), investor.getPosts()));
			Assert.isTrue(CollectionUtils.isEqualCollection(instrumented.getInvestments(), investor.getInvestments()));

		}
		saved = this.investorRepository.save(investor);
		Assert.notNull(saved);

		return saved;
	}

	public Investor findOne(final int investorId) {
		Investor result;
		result = this.investorRepository.findOne(investorId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Investor> findAll() {
		Collection<Investor> result;
		result = this.investorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Investor findByUserAccount(final UserAccount userAccount) {
		Investor result;

		Assert.notNull(userAccount);

		result = this.investorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Investor findByPrincipal() {
		Investor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Investor reconstruct(final Investor result, final BindingResult binding) {
		Investor investor;

		investor = this.findOne(result.getId());
		result.setThreads(investor.getThreads());
		result.setPosts(investor.getPosts());
		result.setUserAccount(investor.getUserAccount());
		result.setInvestments(investor.getInvestments());

		this.validator.validate(result, binding);

		return result;
	}

	public Investor reconstruct(final ActorForm actorForm, final BindingResult binding) {
		Investor result;
		final Collection<Investment> investments;
		final Collection<domain.Thread> threads;
		final Collection<Post> posts;

		result = this.create();

		final Authority a = new Authority();
		a.setAuthority(Authority.INVESTOR);

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

		investments = new ArrayList<Investment>();
		threads = new ArrayList<domain.Thread>();
		posts = new ArrayList<Post>();

		result.setInvestments(investments);
		result.setThreads(threads);
		result.setPosts(posts);

		this.validator.validate(result, binding);

		if ((!actorForm.getUserAccount().getPassword().equals(actorForm.getConfirmPassword())) || !actorForm.getTermsAndConditions())
			result = null;

		return result;

	}

}
