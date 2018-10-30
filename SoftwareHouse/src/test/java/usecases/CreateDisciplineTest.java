
package usecases;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.DisciplineService;
import utilities.AbstractTest;
import domain.Discipline;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreateDisciplineTest extends AbstractTest {

	// System under test

	@Autowired
	private DisciplineService	disciplineService;


	// Supporting services

	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método save(Discipline discipline) de DisciplineService:
			 * (Especificamos el username del ADMIN principal y el nombre de la nueva disciplina.
			 */
			{
				"admin", "Discipline name", null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			}, {
				"expert2", "Discipline name", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el usuario principal no es administrador 
			}, {
				"admin", null, ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: la disciplina no tiene nombre
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder crear una nueva disciplina.
	 * Probamos los métodos create() y save(Discipline discipline) de la clase DisciplineService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final String name, final Class<?> expected) {
		Class<?> caught;
		Discipline discipline;

		caught = null;

		try {
			super.authenticate(username);
			discipline = this.disciplineService.create();

			discipline.setName(name);

			this.disciplineService.save(discipline);
			super.unauthenticate();
			super.flushTransaction();

			/*
			 * La comprobación del método save(Discipline discipline) termina aquí. Si no ha saltado ninguna
			 * excepción, se ha creado una nueva disciplina con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
