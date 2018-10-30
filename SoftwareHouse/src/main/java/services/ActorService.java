
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;

@Service
@Transactional
public class ActorService {

	// Managed repository
	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services
	@Autowired
	private AdministratorService	adminsitratorService;


	// Constructors
	public ActorService() {
		super();
	}

	// Simple CRUD methods

	// Other business methods
	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Collection<Actor> findAllButAdmins() {
		Actor principal;
		Collection<Actor> result;
		Collection<Administrator> administrators;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		administrators = this.adminsitratorService.findAll();
		Assert.notNull(administrators);

		result.removeAll(administrators);
		return result;

	}

	public Collection<Actor> findActorsWith10PercentMorePostsThanAverage() {
		Collection<Actor> result;
		Administrator principal;

		principal = this.adminsitratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.actorRepository.actorsWith10PercentMorePostsThanAverage();
		Assert.notNull(result);

		return result;
	}

}
