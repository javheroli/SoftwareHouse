
package usecases;

import javax.validation.ConstraintViolationException;

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
public class CreateForumTest extends AbstractTest {

	// System under test

	@Autowired
	private ForumService	forumService;


	// Supporting services

	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método save(Forum forum) de ForumService:
			 * (Especificamos el username del ADMIN principal, el nombre, la descripción y la imagen (es opcional) del nuevo foro.
			 * No vamos a especificar los temas del foro ("threads"), ni el número de respuestas ("numPosts"), ni la última respuesta ("lastPost").
			 * Los valores de todos estos atributos se asignan automáticamente al objeto en el método create() y sabemos que a la hora de crear un nuevo foro
			 * siempre se invoca primero al método create() y después al save(Forum forum). Es reduntante hacer pruebas sobre el método create() ya que dicho método
			 * simplemente crea una instancia de Forum y asigna valor nulo al atributo "lastPost", 0 al atributo "numPosts" y una colección vacía al atributo "threads".
			 */
			{
				"admin", "forum Title", "forum Description", "http://www.foto1.es", null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			}, {
				"apprentice3", "forum Title", "forum Description", "http://www.foto1.es", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el usuario principal es un aprendiz
			}, {
				"admin", "forum Title", "forum Description", "image", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: la URL no es válida
			},

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder crear un nuevo foro.
	 * Probamos los métodos create() y save(Forum forum) de la clase ForumService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final String name, final String description, final String linkPicture, final Class<?> expected) {
		Class<?> caught;
		Forum forum;

		caught = null;

		try {
			super.authenticate(username);
			forum = this.forumService.create();

			forum.setName(name);
			forum.setDescription(description);
			forum.setLinkPicture(linkPicture);

			this.forumService.save(forum);
			super.flushTransaction();
			super.unauthenticate();
			/*
			 * La comprobación del método save(Forum forum) termina aquí. Si no ha saltado ninguna
			 * excepción, se ha creado un nuevo foro con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
