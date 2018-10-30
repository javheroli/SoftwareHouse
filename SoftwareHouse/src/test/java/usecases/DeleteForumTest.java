
package usecases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ForumService;
import utilities.AbstractTest;
import domain.Forum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DeleteForumTest extends AbstractTest {

	// System under test

	@Autowired
	private ForumService	forumService;


	// Supporting services

	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método delete(Forum forum) de ForumService:
			 * (Especificamos el username del ADMIN principal y el foro que queremos borrar)
			 */
			{
				"admin", "forum3", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			}, {
				"manager3", "forum5", IllegalArgumentException.class
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
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder borrar cualquiera de los foros del sistema.
	 * Probamos el método delete(Forum forum) de la clase ForumService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int forumId, final Class<?> expected) {
		Class<?> caught;
		Forum forum;

		caught = null;

		try {
			super.authenticate(username);
			forum = this.forumService.findOne(forumId);

			this.forumService.delete(forum);
			super.flushTransaction();
			super.unauthenticate();

			/*
			 * La comprobación del método delete(Forum forum) termina aquí. Si no ha saltado ninguna
			 * excepción, el foro se ha borrado con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
