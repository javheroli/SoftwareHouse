
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.ApprenticeService;
import services.ContestService;
import utilities.AbstractTest;
import domain.Application;
import domain.Apprentice;
import domain.Contest;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ApprenticeService	apprenticeService;

	@Autowired
	private ContestService		contestService;


	// Tests ------------------------------------------------------------------

	//Requirements: An actor who is authenticated as an manager must be able to:
	//18.2. Manage the applications for the contest that he or she organises, which
	//includes listing/displaying them and changing their status from
	//“PENDING” to “REJECTED” or “DUE”.

	//Explicación: El siguiente caso de uso intenta probar que el listado de solicitudes del manager principal se corresponde 
	// a los datos introducidos en el populate, una vez verificado mediante booleanos se intentará, o bien cambiar el status de una aplicación
	//de "PENDING" a "REJECTED" o "DUE"
	@Test
	public void driver1() {
		final Object testingData[][] = {
			//Test positivo, el manager1 accede a su listado de solicitudes en total tiene 7 (5 ACCEPTED, 1 DUE y 1 PENDING). Se intenta rechazar la que tiene con status PENDING para convertirla en REJECTED
			{
				"manager1", 7, 4, 1, 0, 2, true, false, null
			},
			//Test positivo, el manager1 accede a su listado de solicitudes en total tiene 7 (5 ACCEPTED, 1 DUE y 1 PENDING). Se intenta aprobar la que tiene con status PENDING para convertirla en DUE
			{
				"manager1", 7, 4, 1, 0, 2, false, false, null
			},
			//Test positivo, el manager2 accede a su listado de solicitudes en total tiene 3 (2 ACCEPTED y 1 REJECTED)
			{
				"manager2", 3, 2, 0, 1, 0, false, false, null
			},
			//Test positivo, el manager3 accede a su listado de solicitudes que en este caso está vacía
			{
				"manager3", 0, 0, 0, 0, 0, true, false, null
			},

			//Test negativo, un usuario sin autentificar intenta listar sus solicitudes
			{
				null, 0, 0, 0, 0, 0, true, false, IllegalArgumentException.class
			},
			//Test negativo, un experto intenta listar sus solicitudes
			{
				"expert1", 0, 0, 0, 0, 0, true, false, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template1((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (boolean) testingData[i][6], (boolean) testingData[i][7],
					(Class<?>) testingData[i][8]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: An actor who is authenticated as an apprentice must be able to:
	//16.3. Apply for a contest.

	//Explicación: El siguiente caso de uso intenta probar que un aprendiz intente realizar una solicitud con status "PENDING" a un concurso
	@Test
	public void driver2() {
		final Object testingData[][] = {
			//Test positivo, el apprentice3 intenta crear una solicitud para el concurso con título "Contest3" que  está disponible, esto es, aún no ha pasado y tienes plazas disponibles. El principal añade carta de motivación
			{
				"apprentice3", "contest3", "Motivation letter", null
			},
			//Test positivo, el apprentice3 intenta crear una solicitud para el concurso con título "Contest3" que  está disponible, esto es, aún no ha pasado y tienes plazas disponibles. Sin añadir carta de motivación
			{
				"apprentice3", "contest3", "", null
			},
			//Test negativo, el apprentice1 intenta crear una solicitud para un concurso al que ya tiene una solicitud con estado ACCEPTED
			{
				"apprentice1", "contest3", "", IllegalArgumentException.class
			},
			//Test negativo, el apprentice2 intenta crear una solicitud para un concurso al que ya tiene una solicitud con estado PENDING
			{
				"apprentice2", "contest3", "", IllegalArgumentException.class
			},
			//Test negativo, el apprentice4 intenta crear una solicitud para un concurso al que ya tiene una solicitud con estado DUE
			{
				"apprentice4", "contest3", "", IllegalArgumentException.class
			},
			//Test negativo, el apprentice1 intenta crear una solicitud para un concurso que no tiene solicitud pero que ya ha finalizado
			{
				"apprentice1", "contest2", "", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template2((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: An actor who is authenticated as an apprentice must be able to:
	//16.4. List the applications that he or she’s made, grouped by status.

	//Explicación: El siguiente caso de uso intenta probar que el listado de solicitudes del aprendiz principal se corresponde 
	// a los datos introducidos en el populate
	@Test
	public void driver3() {
		final Object testingData[][] = {
			//Test positivo, el apprentice1 accede a su listado de solicitudes en total tiene 2 (1 ACCEPTED, ! DUE).
			{
				"apprentice1", 2, 1, 0, 0, 1, null
			},
			//Test positivo, el apprentice2 accede a su listado de solicitudes en total tiene 3 (1 ACCEPTED, 1 REJECTED, 1 PENDING).
			{
				"apprentice2", 3, 1, 1, 1, 0, null
			},
			//Test positivo, el apprentice4 accede a su listado de solicitudes en total tiene 2 (1 ACCEPTED, 1 DUE).
			{
				"apprentice4", 2, 1, 0, 0, 1, null
			},
			//Test negativo, un usuario sin autentificar intenta acceder al listado de solicitudes
			{
				null, 2, 1, 0, 1, 0, IllegalArgumentException.class
			},
			//Test negativo, un experto intenta acceder al listado de solicitudes
			{
				"expert1", 2, 1, 0, 1, 0, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template3((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: An actor who is authenticated as an apprentice must be able to:
	//16.5. Enter a credit card to get an application accepted as long as its status is “DUE”.

	//Explicación: El siguiente caso de uso intenta probar el cambio de estado de una solicitud del aprendiz principal una vez está en "DUE" a "ACCEPTED"
	//Si el principal tiene una cantidad de puntos mayor que los requeridos por el concurso, podrá cambiar el estado de su solicitud a "ACCEPTED" sin introducir ninguna tarjeta de crédito,
	//sin embargo, si no tuviera suficientes será necesario introducirla.
	@Test
	public void driver4() {
		final Object testingData[][] = {
			//Test positivo, el apprentice1 cambia su solicitud para el concurso "Contest 3" de estado "DUE" a "ACCEPTED" únicamente confirmando asistencia puesto que tiene una cantidad de puntos mayor que los requeridos
			{
				"apprentice1", null, null, null, 0, 0, 0, null
			},
			//Test positivo, el apprentice4 cambia su solicitud para el concurso "Contest 3" de estado "DUE" a "ACCEPTED" introduciendo una tarjeta de crédito válida
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "3408 063771 43002", 444, 9, 19, null
			},
			//*************************TEST NEGATIVOS******************************************
			//Test negativo, se introduce una creditCard con brandName en blanco
			{
				"apprentice4", "", "Adrián López Domínguez", "3408 063771 43002", 444, 9, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con holderName en blanco
			{
				"apprentice4", "MasterCard", "", "3408 063771 43002", 444, 9, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con number en blanco
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "", 444, 9, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con number no válido
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "5", 444, 9, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con CVV fuera del rango (por abajo)
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "3408 063771 43002", 99, 9, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con CVV fuera del rango (por arriba)
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "3408 063771 43002", 1000, 9, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con expirationMonth fuera del rango (por abajo) 
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "3408 063771 43002", 444, 0, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con expirationMonth fuera del rango (por arriba)
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "3408 063771 43002", 444, 13, 19, ConstraintViolationException.class
			},
			//Test negativo, se introduce una creditCard con expirationYear fuera del rango (por abajo), En este caso da IllegalArgumentException debido a cómo hemos modelado en el servicio que una creditcard no puede estar expirada
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "3408 063771 43002", 444, 12, 9, IllegalArgumentException.class
			},
			//Test negativo, se introduce una creditCard con expirationYear fuera del rango (por abajo)
			{
				"apprentice4", "MasterCard", "Adrián López Domínguez", "3408 063771 43002", 444, 12, 100, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template4((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void template1(final String user, final int numberApplications, final int numberAccepted, final int numberPending, final int numberRejected, final int numberDue, final boolean reject, final boolean changeApplicationNotPending,
		final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Application> applications;
		Collection<Application> applicationsAccepted;
		Collection<Application> applicationsPending;
		Collection<Application> applicationsRejected;
		Collection<Application> applicationsDue;
		Application application;

		try {
			super.authenticate(user);
			applications = this.applicationService.findByPrincipalManager();
			applicationsAccepted = this.applicationService.findAcceptedByPrincipalManager();
			applicationsPending = this.applicationService.findPendingByPrincipalManager();
			applicationsRejected = this.applicationService.findRejectedByPrincipalManager();
			applicationsDue = this.applicationService.findDueByPrincipalManager();

			Assert.isTrue(applications.size() == numberApplications);
			Assert.isTrue(applicationsAccepted.size() == numberAccepted);
			Assert.isTrue(applicationsPending.size() == numberPending);
			Assert.isTrue(applicationsRejected.size() == numberRejected);
			Assert.isTrue(applicationsDue.size() == numberDue);

			if (applicationsPending.size() > 0 && reject) {
				application = applicationsPending.iterator().next();
				this.applicationService.reject(application);
				this.applicationService.flush();
				Assert.isTrue(this.applicationService.findOne(application.getId()).getStatus().equals("REJECTED"));
			} else if (applicationsPending.size() > 0 && !reject) {
				application = applicationsPending.iterator().next();
				this.applicationService.approve(application);
				this.applicationService.flush();
				Assert.isTrue(this.applicationService.findOne(application.getId()).getStatus().equals("DUE"));
			}

			if (applicationsAccepted.size() > 0 && changeApplicationNotPending) {
				application = applicationsAccepted.iterator().next();
				this.applicationService.reject(application);
				this.applicationService.flush();
			}
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template2(final String user, final String contestTitle, final String motivationLetter, final Class<?> expected) {
		Class<?> caught = null;
		Contest contest;
		Application application;
		Application saved;

		try {
			super.authenticate(user);

			contest = this.contestService.findOne(super.getEntityId(contestTitle));
			application = this.applicationService.create(contest.getId());
			application.setMotivationLetter(motivationLetter);

			saved = this.applicationService.save(application);
			this.applicationService.flush();

			Assert.isTrue(saved.getStatus().equals("PENDING"));

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template3(final String user, final int numberApplications, final int numberAccepted, final int numberPending, final int numberRejected, final int numberDue, final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Application> applications;
		Collection<Application> applicationsAccepted;
		Collection<Application> applicationsPending;
		Collection<Application> applicationsRejected;
		Collection<Application> applicationsDue;

		try {
			super.authenticate(user);
			applications = this.applicationService.findByPrincipalApprentice();
			applicationsAccepted = this.applicationService.findAcceptedByPrincipalApprentice();
			applicationsPending = this.applicationService.findPendingByPrincipalApprentice();
			applicationsRejected = this.applicationService.findRejectedByPrincipalApprentice();
			applicationsDue = this.applicationService.findDueByPrincipalApprentice();

			Assert.isTrue(applications.size() == numberApplications);
			Assert.isTrue(applicationsAccepted.size() == numberAccepted);
			Assert.isTrue(applicationsPending.size() == numberPending);
			Assert.isTrue(applicationsRejected.size() == numberRejected);
			Assert.isTrue(applicationsDue.size() == numberDue);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template4(final String user, final String brandName, final String holderName, final String number, final int cvv, final int expirationMonth, final int expirationYear, final Class<?> expected) {
		Class<?> caught = null;
		Application application;
		final Apprentice principal;
		CreditCard creditCard;

		try {
			super.authenticate(user);
			application = this.applicationService.findDueByPrincipalApprentice().iterator().next();
			principal = this.apprenticeService.findByPrincipal();

			if (principal.getPoints() < application.getContest().getRequiredPoints()) {
				creditCard = new CreditCard();
				creditCard.setBrandName(brandName);
				creditCard.setHolderName(holderName);
				creditCard.setNumber(number);
				creditCard.setExpirationMonth(expirationMonth);
				creditCard.setExpirationYear(expirationYear);
				creditCard.setCVV(cvv);

				application.setCreditCard(creditCard);
			}

			this.applicationService.accept(application);
			this.applicationService.flush();

			application = this.applicationService.findOne(application.getId());
			Assert.isTrue(application.getStatus().equals("ACCEPTED"));

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
