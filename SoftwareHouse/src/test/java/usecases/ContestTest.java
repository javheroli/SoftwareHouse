
package usecases;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApprenticeService;
import services.ContestService;
import services.DisciplineService;
import services.ExpertService;
import services.ManagerService;
import services.ProblemService;
import utilities.AbstractTest;
import domain.Apprentice;
import domain.Contest;
import domain.Expert;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ContestTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ContestService		contestService;

	@Autowired
	private ExpertService		expertService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private DisciplineService	disciplineService;

	@Autowired
	private ApprenticeService	apprenticeService;


	// Tests ------------------------------------------------------------------

	//Requirements: An actor who is authenticated as an expert must be able to:
	//17.1. List/Display the contests that are saved in draft mode in which he or
	//she has been chosen to make up the problems.

	//Explicación: El siguiente caso de uso intenta extraer de la DB el listado de concursos que el experto principal tiene asignado como editor y aún están en modo borrador,
	// para poder añadir problemas al mismo.
	@Test
	public void driver1() {
		final Object testingData[][] = {
			//Test positivo, el expert1 lista sus concursos en los que ha sido elegido como editor y aún están en modo borrador, resultado esperado es una lista conteniendo al concurso con título "Contest 4"
			{
				"expert1", 1, "contest4", null
			},
			//Test positivo, el expert2 lista sus concursos en los que ha sido elegido como editor y aún están en modo borrador, resultado esperado es una lista vacía
			{
				"expert2", 0, null, null
			},
			//Test negativo, un aprendiz intenta listar concursos en modo borrador
			{
				"apprentice1", 1, "contest4", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template1((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: 17. An actor who is authenticated as an expert must be able to:
	//17.4. List/Display the contest that are saved in final mode and have passed
	//in which he or she has been chosen as a judge in order to grade the
	//solutions elaborated by the participants.

	//Explicación: El siguiente caso de uso intenta extraer de la DB el listado de concursos que el experto principal tiene asignado como juez, que ya han finalizado,
	//y además que aún tengan al menos 1 solución sin evaluar, para poder evaluarlas. En el momento en el que todas las soluciones estén evaluadas
	@Test
	public void driver2() {
		final Object testingData[][] = {
			//Test positivo, el expert1 lista sus concursos en los que ha sido elegido como juez y aún están pendientes de evaluar completamente, resultado esperado una lista conteniendo el concurso con título "Contest 2"
			{
				"expert1", 1, "contest2", null
			},
			//Test positivo, el expert3 tambíen es juez del concurso con título "Contest 2" por tanto también lo contiene en su listado
			{
				"expert3", 1, "contest2", null
			},
			//Test positivo, el expert2 no tiene concursos pendientes de evaluar
			{
				"expert2", 0, null, null
			},
			//Test negativo, un aprendiz intenta listar sus concursos pendientes de evaluar
			{
				"apprentice1", 1, "contest4", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template2((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: 20. An actor who is authenticated as an administrator must be able to:
	//20.7. List all System’s contest (draft and final mode).

	//Explicación: El siguiente caso de uso intenta extraer todos los concursos guardados en el sistema estando logueados como administrador del sistema
	@Test
	public void driver3() {
		final Object testingData[][] = {
			//Test positivo, el admin del sistema lista todos los concursos del sistema
			{
				"admin", null
			},
			//Test negativo, un manager intenta listar todos los concursos del sistema
			{
				"manager1", IllegalArgumentException.class
			},
			//Test negativo, un experto intenta listar todos los concursos del sistema
			{
				"expert1", IllegalArgumentException.class
			},
			//Test negativo, un aprendiz intenta listar todos los concursos del sistema
			{
				"apprentice1", IllegalArgumentException.class
			},
			//Test negativo, un investor intenta listar todos los concursos del sistema
			{
				"investor1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template3((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: An actor who is authenticated as a manager must be able to:
	//18.1. Manage contests, which includes listing/displaying them

	//Explicación: El siguiente caso de uso intenta extraer todos los concursos creados por el manager principal
	@Test
	public void driver4() {
		final Object testingData[][] = {
			//Test positivo, el manager1 lista sus 2 concursos creados en el sistema, conteniendo entre ellos al concurso con título "Contest 3"
			{
				"manager1", 2, "contest3", null
			},
			//Test positivo, el manager2 lista su concurso creado en concreto el concurso con título "Contest 2"
			{
				"manager2", 1, "contest2", null
			},
			//Test positivo, el manager3 lista su concurso creado en concreto el concurso con título "Contest 4"
			{
				"manager3", 1, "contest4", null
			},
			//Test negativo, un experto intenta listar sus concursos creados
			{
				"expert1", 1, "contest1", IllegalArgumentException.class
			},
			//Test negativo, un aprendiz intenta listar sus concursos creados
			{
				"apprentice1", 1, "contest1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template4((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: 16. An actor who is authenticated as an apprentice must be able to:
	//16.2. List/Display de contests that are saved in final mode.

	//Explicación: El siguiente caso de uso intenta mostrar al principal los concursos del sistema ya publicados, es decir, que ya están guardados en modo final
	//Los concursos guardados en modo final pueden listados por aprendices, managers y expertos.
	@Test
	public void driver5() {
		final Object testingData[][] = {
			//Test positivo, apprentice1 accede al listado de concursos publicados
			{
				"apprentice1", 3, "contest1", null
			},
			//Test positivo, manager1 accede al listado de concursos publicados
			{
				"manager1", 3, "contest1", null
			},
			//Test positivo, expert1 accede al listado de concursos publicados
			{
				"expert1", 3, "contest1", null
			},
			//Test negativo, un usuario sin autentificar intenta listar los concursos publicados
			{
				null, 3, "contest1", IllegalArgumentException.class
			},
			//Test negativo, investor1 intenta listar los concursos publicados
			{
				"investor1", 3, "contest1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template5((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: 18. An actor who is authenticated as a manager must be able to:
	//18.1. Manage contests, which includes listing/displaying them, saving them
	//in draft mode (which means that contests are not published yet) and
	//modifying and deleting them as long as they are saved in draft mode.
	//However, a contest can also be saved in final mode as long as it
	//contains at least one question added by the chosen expert. A contest
	//saved in final mode can no longer be modified or deleted and is
	//considered published. Note that the expert chosen to make up the
	//problems for a contest must be proficient in all the disciplines that the
	//contest challenges.

	//Explicación: El siguiente caso de uso realiza todo el proceso de creación y publicación de un concurso.
	//En primer lugar el manager logueado crea el concurso y lo guarda en modo borrador.
	//Una vez guardado, nos deslogueamos como el manager y nos autentificamos como el experto editor elegido, creamos un problema y lo asociamos al concurso recién creado.
	//Por último, cuando el concurso ya tiene el problema asignado, nos volvemos a loguear como el manager que lo creó, mediante un booleano se probará el borrado del concurso
	// aún en modo borrador o bien, su guardado en modo final y por consiguiente su publicación.
	@SuppressWarnings("unchecked")
	@Test
	public void driver6() throws ParseException {
		final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		final Object testingData[][] = {
			//Test positivo, el manager1 realiza el caso de uso correctamente creando un concurso, esperando a que el editor añada el problema y una vez ocurra, guardando el concurso en modo final
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null,
				false, null
			},
			//Test positivo, el manager1 realiza el caso de uso correctamente creando un concurso, esperando a que el editor añada el problema y una vez ocurra, el manager decide borrar el concurso
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), true, null,
				false, null
			},
			//Test positivo, el manager1 realiza el caso de uso correctamente creando un concurso sin reglas, esperando a que el editor añada el problema y una vez ocurra, guardando el concurso en modo final
			{
				"manager1", "title", "description", new ArrayList<String>(), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null, false,
				null
			},
			//***************************TEST NEGATIVOS************************************
			//Test negativo, se introduce un título en blanco para el concurso
			{
				"manager1", "", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null,
				false, ConstraintViolationException.class
			},
			//Test negativo, se introduce una descripción en blanco para el concurso
			{
				"manager1", "title", "", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null, false,
				ConstraintViolationException.class
			},
			//Test negativo, se introduce un grado de dificultad fuera del rango [1,5] para el concurso en concreto 0
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 0, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null,
				false, ConstraintViolationException.class
			},
			//Test negativo, se introduce un grado de dificultad fuera del rango [1,5] para el concurso en concreto 6
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 6, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null,
				false, ConstraintViolationException.class
			},
			//Test negativo, se introduce un número de puntos requeridos menor que 0
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, -1, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null,
				false, ConstraintViolationException.class
			},
			//Test negativo, se introduce un número de plazas disponibles menor que 0
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, -1, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null,
				false, ConstraintViolationException.class
			},
			//Test negativo, se introduce un precio menor que 0. , en concreto -0.01
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, -0.01, 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false,
				null, false, ConstraintViolationException.class
			},
			//Test negativo, se introduce un premio menor que 0. , en concreto -0.01
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., -0.01, "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false,
				null, false, ConstraintViolationException.class
			},
			//Test negativo, el manager1 introduce como editor alguien que no es experto en la disciplina escogida
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert1", Arrays.asList("expert4", "expert5"), false, null,
				false, IllegalArgumentException.class
			},
			//Test negativo, el manager1 introduce como juez alguien que no es experto en la disciplina escogida
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert1", "expert5"), false, null,
				false, IllegalArgumentException.class
			},
			//Test negativo, el manager1 introduce como fecha de inicio una fecha ya pasada
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2017 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert2", "expert5"), false, null,
				false, IllegalArgumentException.class
			},
			//Test negativo, el manager1 introduce como fecha de fin una fecha ya pasada
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2017 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert2", "expert5"), false, null,
				false, IllegalArgumentException.class
			},
			//Test negativo, el manager1 introduce como fecha de fin anterior a la fecha de incio
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 18:00"), formatter.parse("19/09/2019 16:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert2", "expert5"), false, null,
				false, IllegalArgumentException.class
			},
			//Test negativo, el manager principal intenta crear un concurso para otro manager distinto del sistema
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false,
				"manager2", false, IllegalArgumentException.class
			},
			//Test negativo, el manager intenta actualizar el concurso una vez ya guardado en modo final
			{
				"manager1", "title", "description", Arrays.asList("rule1", "rule2"), formatter.parse("19/09/2019 16:00"), formatter.parse("19/09/2019 18:00"), 3, 50, 30, 5., 100., "discipline1", "expert2", Arrays.asList("expert4", "expert5"), false, null,
				true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template6((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (List<String>) testingData[i][3], (Date) testingData[i][4], (Date) testingData[i][5], (int) testingData[i][6], (int) testingData[i][7],
					(int) testingData[i][8], (double) testingData[i][9], (double) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Collection<String>) testingData[i][13], (boolean) testingData[i][14],
					(String) testingData[i][15], (boolean) testingData[i][16], (Class<?>) testingData[i][17]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: 16. An actor who is authenticated as an apprentice must be able to:
	//16.7. Display the winner/s of the contests once they are finished and assessed.

	//Explicación: El siguiente caso de uso intenta extraer el ganador de un concurso, se tiene que dar la condición de
	// de que el concurso esté finalizado y todas las soluciones realizadas para el mismo, evaluadas.
	@Test
	public void driver7() {
		final Object testingData[][] = {
			//Test positivo, apprentice1 accede a el ganador del concurso "Contest 1" , que es él mismo.
			{
				"apprentice1", "contest1", "apprentice1", true, null
			},
			//Test positivo, apprentice1 accede a el ganador del concurso "Contest 2", que aún no tiene ganador
			{
				"apprentice1", "contest2", null, false, null
			},
			//Test negativo, un usuario sin autentificar intenta acceder al ganador de un concurso
			{
				null, "contest1", "apprentice1", true, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template7((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (boolean) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void template1(final String user, final int numberContest, final String contestTitle, final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Contest> contests;
		Contest contest;

		try {
			super.authenticate(user);

			contests = this.contestService.findAllPrincipalChosenAsEditor();
			Assert.isTrue(contests.size() == numberContest);
			if (contestTitle != null) {
				contest = this.contestService.findOne(super.getEntityId(contestTitle));
				Assert.isTrue(contests.contains(contest));
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template2(final String user, final int numberContest, final String contestTitle, final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Contest> contests;
		Contest contest;

		try {
			super.authenticate(user);

			contests = this.contestService.findAllPendingToAssessPrincipalIsJudge();
			Assert.isTrue(contests.size() == numberContest);
			if (contestTitle != null) {
				contest = this.contestService.findOne(super.getEntityId(contestTitle));
				Assert.isTrue(contests.contains(contest));
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template3(final String user, final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Contest> contests;

		try {
			super.authenticate(user);

			contests = this.contestService.findAllForAdmin();
			Assert.isTrue(contests.size() == 4);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template4(final String user, final int numberContest, final String contestTitle, final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Contest> contests;
		Contest contest;

		try {
			super.authenticate(user);

			contests = this.contestService.findAllCreatedByPrincipalManager();
			Assert.isTrue(contests.size() == numberContest);
			if (contestTitle != null) {
				contest = this.contestService.findOne(super.getEntityId(contestTitle));
				Assert.isTrue(contests.contains(contest));
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template5(final String user, final int numberContest, final String contestTitle, final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Contest> contests;
		Contest contest;

		try {
			super.authenticate(user);

			contests = this.contestService.findAllPublished();
			Assert.isTrue(contests.size() == numberContest);
			if (contestTitle != null) {
				contest = this.contestService.findOne(super.getEntityId(contestTitle));
				Assert.isTrue(contests.contains(contest));
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template6(final String user, final String title, final String description, final List<String> rules, final Date startMoment, final Date endMoment, final int difficultyGrade, final int requiredPoints, final int availablePlaces,
		final double price, final double prize, final String discipline, final String editor, final Collection<String> judges, final boolean delete, final String anotherManager, final boolean updateInFinalMode, final Class<?> expected) {
		Class<?> caught = null;
		final Collection<Expert> jueces = new ArrayList<Expert>();
		Contest contest;
		Contest saved;
		Problem problem;

		try {
			super.authenticate(user);

			contest = this.contestService.create();
			contest.setTitle(title);
			contest.setDescription(description);
			contest.setRules(rules);
			contest.setStartMoment(startMoment);
			contest.setEndMoment(endMoment);
			contest.setRequiredPoints(requiredPoints);
			contest.setDifficultyGrade(difficultyGrade);
			contest.setAvailablePlaces(availablePlaces);
			contest.setPrice(price);
			contest.setPrize(prize);
			contest.setDiscipline(this.disciplineService.findOne(super.getEntityId(discipline)));
			contest.setEditor(this.expertService.findOne(super.getEntityId(editor)));
			for (final String s : judges) {
				final Expert e = this.expertService.findOne(super.getEntityId(s));
				jueces.add(e);
			}
			contest.setJudges(jueces);

			if (anotherManager != null)
				contest.setManager(this.managerService.findOne(super.getEntityId(anotherManager)));

			saved = this.contestService.save(contest, true);
			this.contestService.flush();

			super.unauthenticate();

			//Una vez guardado en modo borrador, nos autentificamos como el editor y añadimos un problema al concurso creado
			super.authenticate(editor);
			problem = this.problemService.create(saved.getId());
			problem.setStatement("Statement");
			problem.setMark(10.);
			this.problemService.save(problem);
			this.problemService.flush();
			super.unauthenticate();

			//Una vez tenemos el problema asociado con el contest volvemos a autentificarnos como el manager para borrar el concurso si procede o guardarlo en modo final
			super.authenticate(user);

			if (delete) {
				this.contestService.delete(saved);
				this.contestService.flush();
			} else {
				saved = this.contestService.save(saved, false);
				this.contestService.flush();
				if (updateInFinalMode) {
					saved.setTitle("UPDATED");
					this.contestService.save(saved, false);
					this.contestService.flush();
				}
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template7(final String user, final String contestTitle, final String apprenticeWinner, final boolean hasFinished, final Class<?> expected) {
		Class<?> caught = null;
		Contest contest;
		Collection<Apprentice> winners;
		Apprentice winner;
		boolean finished;

		try {
			super.authenticate(user);

			contest = this.contestService.findOne(super.getEntityId(contestTitle));
			finished = this.contestService.hasContestWinner(contest.getId());
			Assert.isTrue(hasFinished == finished);

			if (finished) {
				winners = this.contestService.findWinnersByContest(contest.getId());
				Assert.isTrue(winners.size() > 0);
				winner = this.apprenticeService.findOne(super.getEntityId(apprenticeWinner));
				winners.contains(winner);

			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
