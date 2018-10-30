
package usecases;

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
public class DeleteDisciplineTest extends AbstractTest {

	// System under test

	@Autowired
	private DisciplineService	disciplineService;


	// Supporting services

	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método delete(Discipline discipline) de DisciplineService:
			 * (Especificamos el username del ADMIN principal y el la disciplina que queremos borrar)
			 */
			{
				"admin", "discipline4", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			}, {
				"admin", "discipline10", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: discipline10 se utiliza en contest1
			}, {
				"manager3", "discipline4", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el usuario principal no es administrador
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder borrar cualquiera de las disciplinas no usadas en ningún concurso
	 * Probamos el método delete(Discipline discipline) de la clase DisciplineService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int disciplineId, final Class<?> expected) {
		Class<?> caught;
		Discipline discipline;

		caught = null;

		try {
			super.authenticate(username);
			discipline = this.disciplineService.findOne(disciplineId);

			this.disciplineService.delete(discipline);
			super.flushTransaction();
			super.unauthenticate();

			/*
			 * La comprobación del método delete(Discipline discipline) termina aquí. Si no ha saltado ninguna
			 * excepción, la disciplina se ha borrado con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
