
package usecases;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ForumService;
import services.PostService;
import services.ThreadService;
import utilities.AbstractTest;
import domain.Forum;
import domain.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UpdateForumTest extends AbstractTest {

	// System under test

	@Autowired
	private ForumService	forumService;

	// Supporting services

	@Autowired
	private ThreadService	threadService;

	@Autowired
	private PostService		postService;


	// Tests

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Para probar el método save(Forum forum) de ForumService cuando el objeto pasado como
			 * parámetro de entrada es un foro instrumentado que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del ADMIN principal, el foro que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo nombre ("name"), nueva descripción ("description"), nuevo enlace de imagen ("linkPicture"), nuevos temas ("threads"),
			 * nuevo número de respuestas ("numPosts") y nueva última respuesta ("lastPost").
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Forum, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(Forum forum)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad forum:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Forum forum):
			 * 
			 * 1) threads
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 1) numPosts
			 * 2) lastPost
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los usuarios no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
			 */
			{
				"admin", "forum1", "New Name", "New description", "http://www.newPicture.com",
				new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread1")), this.threadService.findOne(super.getEntityId("thread2")))), 5, "post5", null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio
			},
			{
				"admin", "forum1", "New Name", "New description", "http://www.newPicture.com",
				new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread1")), this.threadService.findOne(super.getEntityId("thread2")))), 6, "post5", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta cambiar el número de respuestas
			},
			{
				"admin", "forum1", "New Name", "New description", "New Picture", new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread1")), this.threadService.findOne(super.getEntityId("thread2")))), 5, "post5",
				ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: enlace no válido
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (List<domain.Thread>) testingData[i][5], (int) testingData[i][6],
					super.getEntityId((String) testingData[i][7]), (Class<?>) testingData[i][8]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder modificar cualquiera de los foros del sistema.
	 * Probamos el método save(Forum forum) de la clase ForumService mediante la siguiente plantilla:
	 * (¡OJO! El objeto forum que se pasa como parámetro de entrada ahora es un objeto instrumentado, es decir, un foro existente en
	 * la base de datos.)
	 */

	protected void template(final String username, final int forumId, final String name, final String description, final String linkPicture, final List<domain.Thread> threads, final int numPosts, final int lastPostId, final Class<?> expected) {
		Class<?> caught;
		Forum forum, instrumented;
		Post lastPost;

		caught = null;

		try {
			instrumented = this.forumService.findOne(forumId);

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocándo a los setters,
			 * se van a realizar llamadas implícitas al método save de ForumRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de ForumRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			forum = new Forum();

			forum.setId(instrumented.getId());
			forum.setVersion(instrumented.getVersion());

			super.authenticate(username);

			forum.setName(name);
			forum.setDescription(description);
			forum.setLinkPicture(linkPicture);
			forum.setThreads(threads);
			forum.setNumPosts(numPosts);

			lastPost = this.postService.findOne(lastPostId);

			forum.setLastPost(lastPost);

			this.forumService.save(forum);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el foro se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
