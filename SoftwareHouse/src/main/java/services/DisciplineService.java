
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DisciplineRepository;
import domain.Administrator;
import domain.Contest;
import domain.Discipline;
import domain.Expert;

@Service
@Transactional
public class DisciplineService {

	// Managed repository
	@Autowired
	private DisciplineRepository	disciplineRepository;

	// Supporting services
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ContestService			contestService;

	@Autowired
	private ExpertService			expertService;

	@Autowired
	private ThreadService			threadService;


	// Constructors
	public DisciplineService() {
		super();
	}

	// Simple CRUD methods

	public Discipline findOne(final int disciplineId) {
		Discipline result;
		result = this.disciplineRepository.findOne(disciplineId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Discipline> findAll() {
		Collection<Discipline> result;
		result = this.disciplineRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Discipline create() {
		Discipline result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Discipline();

		return result;
	}

	public Discipline save(final Discipline discipline) {
		Discipline saved;
		Administrator principal;

		Assert.notNull(discipline);
		Assert.isTrue(discipline.getId() == 0);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		saved = this.disciplineRepository.save(discipline);
		Assert.notNull(saved);

		return saved;

	}

	public void delete(final Discipline discipline) {
		Administrator principal;
		Collection<Discipline> disciplines, updated;
		final Collection<Contest> contests;
		final Collection<Expert> experts;
		final Collection<domain.Thread> threads;

		Assert.notNull(discipline);
		Assert.isTrue(discipline.getId() != 0);

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);

		contests = this.contestService.findAllByDiscipline(discipline.getId());
		Assert.isTrue(contests.isEmpty());

		experts = this.expertService.findAllByDiscipline(discipline.getId());

		for (final Expert expert : experts) {
			disciplines = expert.getDisciplines();
			updated = new ArrayList<Discipline>(disciplines);
			updated.remove(discipline);
			expert.setDisciplines(updated);

		}

		threads = this.threadService.findAllByDiscipline(discipline.getId());

		for (final domain.Thread thread : threads) {
			disciplines = thread.getDisciplines();
			updated = new ArrayList<Discipline>(disciplines);
			updated.remove(discipline);
			thread.setDisciplines(updated);

		}

		this.disciplineRepository.delete(discipline);
	}

	// Other business methods

	public Collection<Discipline> findUsedDisciplines() {
		Collection<Discipline> result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.disciplineRepository.findUsedDisciplines();
		Assert.notNull(result);

		return result;
	}

}
