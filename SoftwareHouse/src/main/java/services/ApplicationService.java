
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import domain.Administrator;
import domain.Application;
import domain.Apprentice;
import domain.Contest;
import domain.CreditCard;
import domain.Manager;

@Service
@Transactional
public class ApplicationService {

	// Managed repository
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services
	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ApprenticeService		apprenticeService;

	@Autowired
	private ContestService			contestService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ApplicationService() {
		super();
	}
	private CreditCard generateFictitiousCreditCard() {
		final CreditCard result = new CreditCard();
		result.setHolderName("FICTICIA");
		result.setBrandName("FICTICIA");
		result.setNumber("0000000000000000");
		result.setExpirationMonth(1);
		result.setExpirationYear(10);
		result.setCVV(100);
		return result;
	}

	private String generateTicker(final Application application) {
		String result;
		String year;
		String date;
		String month;
		String hours;
		String minutes;
		Calendar now;
		String difficultyGrade;
		String availablePlaces;

		now = Calendar.getInstance();
		year = String.valueOf(now.get(Calendar.YEAR));

		month = String.valueOf(now.get(Calendar.MONTH) + 1);
		month = month.length() == 1 ? "0".concat(month) : month;

		date = String.valueOf(now.get(Calendar.DATE));
		date = date.length() == 1 ? "0".concat(date) : date;

		hours = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		hours = hours.length() == 1 ? "0".concat(hours) : hours;

		minutes = String.valueOf(now.get(Calendar.MINUTE));
		minutes = minutes.length() == 1 ? "0".concat(minutes) : minutes;

		difficultyGrade = String.valueOf(application.getContest().getDifficultyGrade());

		availablePlaces = String.valueOf(application.getContest().getAvailablePlaces());
		availablePlaces = availablePlaces.length() == 1 ? "00".concat(availablePlaces) : availablePlaces;
		availablePlaces = availablePlaces.length() == 2 ? "0".concat(availablePlaces) : availablePlaces;

		final Random r = new Random();
		final char a = (char) (r.nextInt(26) + 'a');
		final char b = (char) (r.nextInt(26) + 'a');
		final char c = (char) (r.nextInt(26) + 'a');
		final char d = (char) (r.nextInt(26) + 'a');
		final String code = String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d);

		result = date + month + year + "-" + hours + ":" + minutes + "-" + difficultyGrade + "-" + availablePlaces + "-" + code;

		return result;
	}

	// Simple CRUD methods
	public Application create(final int contestId) {
		Application result;
		Contest contest;
		String ticker;
		CreditCard creditCard;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		contest = this.contestService.findOne(contestId);

		result = new Application();
		result.setContest(contest);

		ticker = this.generateTicker(result);

		result.setApplicant(principal);
		result.setTicker(ticker);
		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setStatus("PENDING");

		creditCard = this.generateFictitiousCreditCard();
		result.setCreditCard(creditCard);

		return result;
	}

	public Application save(final Application application) {
		Apprentice principal;
		boolean hasApplication;
		Application saved;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == application.getApplicant().getId());

		Assert.isTrue(application.getContest().getStartMoment().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(application.getContest().getEndMoment().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(!application.getContest().getIsDraft());
		Assert.isTrue(application.getContest().getAvailablePlaces() > 0);

		Assert.isTrue(application.getStatus().equals("PENDING"));

		hasApplication = this.hasPrincipalApplicationForContest(application.getContest().getId());
		Assert.isTrue(!hasApplication);

		saved = this.applicationRepository.save(application);

		application.getApplicant().getApplications().add(saved);
		application.getContest().getApplications().add(saved);

		return saved;
	}
	public void accept(final Application application) {
		Apprentice principal;
		int availablePlaces;
		Calendar calendar;

		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(application.getStatus().equals("DUE"));

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == application.getApplicant().getId());

		Assert.isTrue(application.getContest().getAvailablePlaces() > 0);

		Assert.isTrue(application.getContest().getStartMoment().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(application.getContest().getEndMoment().after(new Date(System.currentTimeMillis())));

		if (application.getApplicant().getPoints() >= application.getContest().getRequiredPoints()) {
			Assert.isTrue(application.getCreditCard().getBrandName().equals("FICTICIA"));
			Assert.isTrue(application.getCreditCard().getHolderName().equals("FICTICIA"));
			Assert.isTrue(application.getCreditCard().getNumber().equals("0000000000000000"));

		} else {
			Assert.isTrue(!application.getCreditCard().getBrandName().equals("FICTICIA"));
			Assert.isTrue(!application.getCreditCard().getHolderName().equals("FICTICIA"));
			Assert.isTrue(!application.getCreditCard().getNumber().equals("0000000000000000"));

			calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);

			year = year % 100;
			month = month + 1;

			Assert.isTrue(year <= application.getCreditCard().getExpirationYear());
			if (year == application.getCreditCard().getExpirationYear())
				Assert.isTrue(month < application.getCreditCard().getExpirationMonth());
		}

		application.setStatus("ACCEPTED");
		this.applicationRepository.save(application);

		availablePlaces = application.getContest().getAvailablePlaces();
		application.getContest().setAvailablePlaces(availablePlaces - 1);

		//Si al aceptar esta solicitud resulta que el concurso se queda sin plazas disponibles, automáticamente 
		//el resto de solicitudos con estado "PENDING" y "DUE" se pondrán a "REJECTED"
		if (application.getContest().getAvailablePlaces() == 0)
			for (final Application a : this.findByContestWithStatusPendingOrDue(application.getContest().getId())) {
				a.setStatus("REJECTED");
				this.applicationRepository.save(a);
			}

	}

	public void reject(final Application application) {
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == application.getContest().getManager().getId());

		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(application.getContest().getStartMoment().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(application.getContest().getEndMoment().after(new Date(System.currentTimeMillis())));

		application.setStatus("REJECTED");
		this.applicationRepository.save(application);

	}

	public void approve(final Application application) {
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() == application.getContest().getManager().getId());

		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(application.getContest().getStartMoment().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(application.getContest().getEndMoment().after(new Date(System.currentTimeMillis())));

		Assert.isTrue(application.getContest().getAvailablePlaces() > 0);

		application.setStatus("DUE");
		this.applicationRepository.save(application);

	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}

	public Application reconstruct(final Application result, final BindingResult binding) {
		final Application application;
		Apprentice principal;
		String ticker;
		CreditCard creditCard;

		if (result.getId() == 0) {
			principal = this.apprenticeService.findByPrincipal();
			Assert.notNull(principal);

			ticker = this.generateTicker(result);

			result.setApplicant(principal);
			result.setTicker(ticker);
			result.setMoment(new Date(System.currentTimeMillis() - 1));
			result.setStatus("PENDING");

			creditCard = this.generateFictitiousCreditCard();
			result.setCreditCard(creditCard);

		} else {
			application = this.findOne(result.getId());

			result.setStatus("DUE");
			result.setApplicant(application.getApplicant());
			result.setContest(application.getContest());
			result.setMoment(application.getMoment());
			result.setTicker(application.getTicker());
			result.setMotivationLetter(application.getMotivationLetter());
		}

		this.validator.validate(result, binding);

		return result;
	}

	// Other business methods
	public Collection<Application> findByPrincipalApprentice() {
		Collection<Application> result;
		Collection<Application> applicationsToRejectAutomatically;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findAllByApprentice(principal.getId());
		Assert.notNull(result);

		applicationsToRejectAutomatically = this.findFinishedWithStatusPendingOrDueByApprentice(principal.getId());

		if (applicationsToRejectAutomatically.size() != 0)
			for (final Application a : applicationsToRejectAutomatically) {
				a.setStatus("REJECTED");
				this.applicationRepository.save(a);
			}

		return result;
	}

	public Collection<Application> findByPrincipalManager() {
		Collection<Application> result;
		Collection<Application> applicationsToRejectAutomatically;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findAllByManager(principal.getId());
		Assert.notNull(result);

		applicationsToRejectAutomatically = this.findFinishedWithStatusPendingOrDueByManager(principal.getId());

		if (applicationsToRejectAutomatically.size() != 0)
			for (final Application a : applicationsToRejectAutomatically) {
				a.setStatus("REJECTED");
				this.applicationRepository.save(a);
			}

		return result;
	}

	public boolean hasPrincipalApplicationForContest(final int contestId) {
		Apprentice principal;
		Collection<Application> exists;
		boolean result = true;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		exists = this.applicationRepository.findByApprenticeAndContest(principal.getId(), contestId);
		Assert.notNull(exists);

		if (exists.size() == 0)
			result = false;
		return result;

	}

	public Collection<Application> findByContestWithStatusPendingOrDue(final int contestId) {
		Collection<Application> result;

		result = this.applicationRepository.findByContestWithStatusPendingOrDue(contestId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findFinishedWithStatusPendingOrDueByManager(final int managerId) {
		Collection<Application> result;

		result = this.applicationRepository.findFinishedWithStatusPendingOrDueByManager(managerId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Application> findFinishedWithStatusPendingOrDueByApprentice(final int apprenticeId) {
		Collection<Application> result;

		result = this.applicationRepository.findFinishedWithStatusPendingOrDueByApprentice(apprenticeId);
		Assert.notNull(result);

		return result;

	}

	public Application findByPrincipalApprenticeAndByContest(final int contestId) {
		Application result;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findByApplicantAndByContest(principal.getId(), contestId);
		Assert.notNull(result);

		if (result.getContest().getStartMoment().before(new Date(System.currentTimeMillis() - 1)) && (result.getStatus().equals("DUE") || result.getStatus().equals("PENDING"))) {
			result.setStatus("REJECTED");
			this.applicationRepository.save(result);
		}

		return result;
	}

	public Collection<Application> findPendingByPrincipalManager() {
		Collection<Application> result;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findPendingByManager(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findDueByPrincipalManager() {
		Collection<Application> result;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findDueByManager(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findRejectedByPrincipalManager() {
		Collection<Application> result;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findRejectedByManager(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findAcceptedByPrincipalManager() {
		Collection<Application> result;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findAcceptedByManager(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findPendingByPrincipalApprentice() {
		Collection<Application> result;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findPendingByApprentice(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findDueByPrincipalApprentice() {
		Collection<Application> result;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findDueByApprentice(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findRejectedByPrincipalApprentice() {
		Collection<Application> result;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findRejectedByApprentice(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findAcceptedByPrincipalApprentice() {
		Collection<Application> result;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.findAcceptedByApprentice(principal.getId());
		Assert.notNull(result);

		return result;
	}
	public void flush() {
		this.applicationRepository.flush();
	}

	public Double getRatioPendingApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioPendingApplications();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getRatioDueApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioDueApplications();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getRatioAcceptedApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioAcceptedApplications();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getRatioRejectedApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioRejectedApplications();

		if (result == null)
			result = 0.0;

		return result;
	}

}
