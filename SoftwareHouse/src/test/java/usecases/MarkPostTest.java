
package usecases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.PostService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MarkPostTest extends AbstractTest {

	// System under test

	@Autowired
	private PostService	postService;


	// Supporting services

	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método mark(int postId) de PostService:
			 * (Especificamos el username del principal y una respuesta)
			 */
			{
				"apprentice4", "post3", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			}, {
				"apprentice4", "post4", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: apprentice4 está intentado marcar como 'Mejor respuesta' una respuesta suya
			}, {
				"apprentice2", "post13", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: en esta tema ya hay una respuesta marcada como 'Mejor respuesta'
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
	 * REQUISITO FUNCIONAL: Un usuario registrado como cualquier actor debe marcar como "Mejor respuesta" una respuesta escrita en algún tema publicado por el mismo.
	 * No debe existir ninguna respuesta marcada como "Mejor respuesta" en el tema. Además, la respuesta a marcar debe estar directamente asociada al tema.
	 * No se puede marcar una respuesta asociada a otra respuesta del tema. Por último, el autor de la respuesta a marcar no debe ser el propio aprendiz principal.
	 * Probamos el método mark(int postId) de la clase PostService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int postId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);

			this.postService.mark(postId);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, la respuesta ha sido marcada como "Mejor respuesta"

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
}
