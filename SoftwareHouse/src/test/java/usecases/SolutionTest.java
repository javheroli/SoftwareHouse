
package usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.ContestService;
import services.DisciplineService;
import services.ExpertService;
import services.ProblemService;
import services.SolutionService;
import utilities.AbstractTest;
import domain.Answer;
import domain.Application;
import domain.Contest;
import domain.Problem;
import domain.Solution;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SolutionTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SolutionService		solutionService;

	@Autowired
	private ContestService		contestService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private DisciplineService	disciplineService;

	@Autowired
	private ExpertService		expertService;

	@Autowired
	private ApplicationService	applicationService;


	// Tests ------------------------------------------------------------------

	//Requirements: An actor who is authenticated as an expert must be able to:
	//17.5. Grade a solution made by a participant of a contest. Note that the
	//solution mark is the summation of all the marks to the answers that
	//compose the solution and that the participant who has elaborated the
	//solution must be kept anonymous.

	//Explicación: El siguiente caso de uso intenta probar la evaluación de soluciones almacenadas en la DB por parte de los expertos del concurso, una vez este ya está finalizado.
	//La evaluación se realizar mediante la introducción de notas para cada respuesta de la solución, siendo la nota final de la solución, el sumatorio de todas las notas de sus respuestas.
	@Test
	public void driver1() {
		final Object testingData[][] = {
			//Test positivo, expert1 evalua una solución para un concurso ya finalizado en el que ha sido elegido como juez
			{
				"expert1", "solution4", 6.0, 2.0, false, null
			},
			//Test positivo, otro juez del concurso evalua esa solución en lugar de expert1
			{
				"expert3", "solution4", 6.0, 2.0, false, null
			},
			//******************TEST NEGATIVOS**********************
			//Test negativo, un experto que no es juez del concurso intenta evaluar una solución del mismo
			{
				"expert2", "solution4", 6.0, 2.0, false, IllegalArgumentException.class
			},
			//Test negativo, un experto evalúa una solución produciendo una nota para la solución, una vez hecho esto, intenta volver a evaluar la misma solución
			{
				"expert1", "solution4", 6.0, 2.0, true, IllegalArgumentException.class
			},
			//Test negativo, un experto intenta evaluar una solución ya evaluada
			{
				"expert1", "solution1", 6.0, 2.0, false, IllegalArgumentException.class
			},
			//Test negativo, se introduce una nota para las respuestas que supera el máximo de nota para el problema al que corresponden
			{
				"expert1", "solution4", 7.6, 2.6, false, IllegalArgumentException.class
			},
			//Test negativo, se introduce una nota para las respuestas menor que 0.0
			{
				"expert1", "solution4", -0.001, -0.001, false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template1((String) testingData[i][0], (String) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (boolean) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	//Requirements: An actor who is authenticated as an apprentice must be able to:
	//16.6. Create and save a solution (composed of the answers to the problems)
	//for a contest for which he or she has applied previously and has an
	//application with an “ACCEPTED” status. The solution must be provided
	//in the period of time between the start and the end moment of the contest.

	//Explicación: El siguiente caso de uso intenta probar la creación de una solución por parte de un aprendiz que tenga una solicitud con estado "ACCEPTED" para un concurso.
	//Puesto que la solución debe crearse en el transcurso de tiempo entre el momento de inicio y momento de fin del concurso,
	//para probar este caso de uso, deberemos hacer que antes un manager cree un concurso con fecha de inicio un minuto más tarde de la fecha actual,
	//una vez creado y guardado en modo borrador, el editor elegido creará un único problema, lo asociará al concurso en cuestión y el manager lo guardará en modo final.
	//Después de este proceso, el aprendiz creará la aplicación, el manager la aprobará y el aprendiz (con puntos suficientes para hacerlo de manera gratuita) confirmará asistencia.
	//Por último, mediante código java, realizaremos que la transacción se demore 1 minuto para que el concurso empiece y el aprendiz pueda elaborar su solución introduciendo su respuesta al único problema existente.

	//IMPORTANTE RECORDAR: ESTE TEST VA A DEMORARSE APROXIMADAMENTE 2 MINUTOS EN EJECUTARSE COMPLETAMENTE DEBIDO A LO EXPLICADO ANTERIORMENTE (DEMORAS PROGRAMADAS)
	@Test
	public void driver2() {
		final Object testingData[][] = {
			//Test positivo, apprentice1 realiza una solution añadiendo texto a su respuesta al único problema preguntado
			{
				"apprentice1", "answer", true, null
			},
			//Test positivo, apprentice1 realiza una solution dejando en blanco su respuesta
			{
				"apprentice1", "", true, null
			},
			//Test negativo, se intenta crear la solución sin esperar a que el concurso empiece
			{
				"apprentice1", "answer", false, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template2((String) testingData[i][0], (String) testingData[i][1], (boolean) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void template1(final String user, final String solutionId, final double mark1, final double mark2, final boolean assessSecondTime, final Class<?> expected) {
		Class<?> caught = null;
		Solution solution;

		try {
			super.authenticate(user);

			solution = this.solutionService.findOne(super.getEntityId(solutionId));
			int i = 1;
			for (final Answer a : solution.getAnswers()) {
				if (i == 1)
					a.setMark(mark1);
				else
					a.setMark(mark2);
				i++;
			}

			solution = this.solutionService.save(solution);
			this.solutionService.flush();

			Assert.isTrue(solution.getMark() == (mark1 + mark2));

			if (assessSecondTime) {
				for (final Answer a : solution.getAnswers())
					a.setMark(0.0);
				solution = this.solutionService.save(solution);
				this.solutionService.flush();
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template2(final String user, final String answerText, final boolean wait, final Class<?> expected) {
		Class<?> caught = null;
		Solution solution;
		Contest contest;
		Calendar forStart;
		Calendar forEnd;
		Problem problem;
		Application application;
		Answer answer;

		try {
			super.authenticate("manager1");

			contest = this.contestService.create();
			contest.setTitle("title");
			contest.setDescription("description");
			contest.setRules(new ArrayList<String>());

			forStart = Calendar.getInstance();
			forStart.add(Calendar.MINUTE, 1);

			contest.setStartMoment(forStart.getTime());

			forEnd = Calendar.getInstance();
			forEnd.add(Calendar.HOUR_OF_DAY, 1);

			contest.setEndMoment(forEnd.getTime());
			contest.setRequiredPoints(50);
			contest.setDifficultyGrade(3);
			contest.setAvailablePlaces(5);
			contest.setPrice(5.);
			contest.setPrize(80.);
			contest.setDiscipline(this.disciplineService.findOne(super.getEntityId("discipline1")));
			contest.setEditor(this.expertService.findOne(super.getEntityId("expert2")));
			contest.setJudges(Arrays.asList(this.expertService.findOne(super.getEntityId("expert4"))));

			contest = this.contestService.save(contest, true);
			this.contestService.flush();

			super.unauthenticate();

			super.authenticate("expert2");

			problem = this.problemService.create(contest.getId());
			problem.setStatement("statement");
			problem.setMark(10.0);
			problem = this.problemService.save(problem);
			this.problemService.flush();

			super.unauthenticate();

			super.authenticate("manager1");
			contest = this.contestService.save(contest, false);
			this.contestService.flush();

			super.unauthenticate();

			super.authenticate(user);

			application = this.applicationService.create(contest.getId());
			application = this.applicationService.save(application);
			this.applicationService.flush();

			super.unauthenticate();

			super.authenticate("manager1");
			this.applicationService.approve(application);
			this.applicationService.flush();
			application = this.applicationService.findOne(application.getId());
			super.unauthenticate();

			super.authenticate(user);
			this.applicationService.accept(application);
			this.applicationService.flush();
			application = this.applicationService.findOne(application.getId());

			if (wait)
				TimeUnit.MINUTES.sleep(1);

			solution = this.solutionService.create(application.getId());
			answer = new Answer();
			answer.setText(answerText);
			solution.setAnswers(Arrays.asList(answer));

			solution = this.solutionService.save(solution);
			this.solutionService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
