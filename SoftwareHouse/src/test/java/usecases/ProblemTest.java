
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ContestService;
import services.ProblemService;
import utilities.AbstractTest;
import domain.Contest;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProblemTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ProblemService	problemService;

	@Autowired
	private ContestService	contestService;


	// Tests ------------------------------------------------------------------

	//Requirements: An actor who is authenticated as an expert must be able to:
	//17.2. Create a problem and associate it to a contest saved in draft mode in
	//which he or she has been chosen to make up the problems.
	//17.3. Manage the problems that he or she has created, which includes
	//modifying and deleting them as long as the corresponding contest is
	//saved in draft mode.

	//Explicación: El siguiente caso de uso intenta probar la creación y guardado de un problema asociándolo a un concurso de la base de datos,
	//una vez guardado, se actualiza el valor de sus atributos y se guarda de nuevo, por último se borra el problema creado.
	@Test
	public void driver1() {
		final Object testingData[][] = {
			//Test positivo, el experto con username = expert1, editor del concurso con título "Contest 4" guardado aún en modo borrador, realiza el caso de uso descrito (con enlace a imagen opcional)
			{
				"expert1", "contest4", "Statement", 5.0, "https://thumbs.dreamstime.com/b/businessman-looking-problem-people-work-metaphor-separated-white-35411422.jpg", null
			},
			//Test positivo, el experto con username = expert1, editor del concurso con título "Contest 4" guardado aún en modo borrador, realiza el caso de uso descrito (sin enlace a imagen opcional)
			{
				"expert1", "contest4", "Statement", 5.0, "", null
			},
			//************************ TEST NEGATIVOS *********************************************
			//Test negativo, un experto intenta añadir un problema para un concurso en el que no es el editor escogido
			{
				"expert2", "contest4", "Statement", 5.0, "https://thumbs.dreamstime.com/b/businessman-looking-problem-people-work-metaphor-separated-white-35411422.jpg", IllegalArgumentException.class
			},
			//Test negativo, el editor de un concurso intenta añadir problemas al mismo cuando ese concurso ya ha sido guardado en modo final
			{
				"expert2", "contest3", "Statement", 5.0, "https://thumbs.dreamstime.com/b/businessman-looking-problem-people-work-metaphor-separated-white-35411422.jpg", IllegalArgumentException.class
			},

			//Test negativo, se intenta crear un problema con enunciado en blanco 
			{
				"expert1", "contest4", "", 5.0, "https://thumbs.dreamstime.com/b/businessman-looking-problem-people-work-metaphor-separated-white-35411422.jpg", ConstraintViolationException.class
			},
			//Test negativo, se intenta crear un problema con una url inválida para el enlace de imagen opcional 
			{
				"expert1", "contest4", "Statement", 5.0, "urlinvalida", ConstraintViolationException.class
			},
			//Test negativo, se intenta crear un problema con una nota mayor que 10
			{
				"expert1", "contest4", "Statement", 10.00000001, "", ConstraintViolationException.class
			},
			//Test negativo, se intenta crear un problema con una nota menor que 0
			{
				"expert1", "contest4", "Statement", -0.0000001, "", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template1((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (double) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void template1(final String user, final String contestEntity, final String statement, final double mark, final String linkPicture, final Class<?> expected) {
		Class<?> caught = null;
		final Contest contest;
		Problem problem;
		Problem saved;
		int numProblems;

		try {
			super.authenticate(user);

			contest = this.contestService.findOne(super.getEntityId(contestEntity));
			numProblems = contest.getProblems().size();

			problem = this.problemService.create(contest.getId());
			problem.setStatement(statement);
			problem.setLinkPicture(linkPicture);
			problem.setMark(mark);

			saved = this.problemService.save(problem);
			this.problemService.flush();

			Assert.isTrue(saved.getNumber() == numProblems + 1);
			Assert.isTrue(contest.getProblems().size() == numProblems + 1);

			saved.setStatement("UPDATED");
			saved.setLinkPicture("");
			saved.setMark(2.0);

			saved = this.problemService.save(saved);
			this.problemService.flush();
			Assert.isTrue(saved.getStatement().equals("UPDATED"));

			this.problemService.delete(saved);
			this.problemService.flush();

			Assert.isTrue(contest.getProblems().size() == numProblems);
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
