
package usecases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ThreadService;
import utilities.AbstractTest;
import domain.Thread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DeleteThreadTest extends AbstractTest {

	// System under test

	@Autowired
	private ThreadService	threadService;


	// Supporting services

	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método delete(Thread thread) de ThreadService:
			 * (Especificamos el username del ADMIN principal y el tema que queremos borrar)
			 */
			{
				"admin", "thread8", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			}, {
				"expert6", "thread1", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el usuario principal debe ser administrador
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
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder borrar cualquiera de los temas del sistema.
	 * Probamos el método delete(Thread thread) de la clase ThreadService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int threadId, final Class<?> expected) {
		Class<?> caught;
		Thread thread;

		caught = null;

		try {
			super.authenticate(username);
			thread = this.threadService.findOne(threadId);

			this.threadService.delete(thread);
			super.unauthenticate();
			super.flushTransaction();

			/*
			 * La comprobación del método delete(Thread thread) termina aquí. Si no ha saltado ninguna
			 * excepción, el tema se ha borrado con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
