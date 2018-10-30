
package usecases;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ActorService;
import services.DisciplineService;
import services.ForumService;
import services.ThreadService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Discipline;
import domain.Forum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreateThreadTest extends AbstractTest {

	// System under test

	@Autowired
	private ThreadService		threadService;

	// Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ForumService		forumService;

	@Autowired
	private DisciplineService	disciplineService;


	// Tests

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método save(Thread thread) de ThreadService:
			 * (Especificamos el username del actor principal, el título, el texto, los enlaces de ficheros adjuntos, las disciplinas y el foro donde se
			 * publicará el nuevo tema.
			 * No vamos a especificar el momento de publicación ("startMoment"), ni el número de respuestas ("numPosts"), ni el momento de la última actualización
			 * ("momentLastModification"), ni el autor ("writer"), ni las respuestas ("posts"), ni la última respuesta ("lastPost"), ni si se permite marcar alguna
			 * de las respuestas del tema como ´Mejor respuesta´ ("isBestAnswerEnabled"), puesto que los valores de estos atributos se asignan automáticamente en el
			 * método create() y sabemos que a la hora de crear un nuevo tema siempre se invoca primero al método create() y después al save(Thread thread). Es reduntante
			 * hacer pruebas sobre el método create() ya que dicho método simplemente crea una instancia de Thread y les asigna a los atributos "startMoment" y "momentLastModidification"
			 * el momento actual, al atributo "numPost" le asigna 0, al "writer" - el actor principal, al "posts" una colección vacía, al "lastPost" - valor nulo y al "isBestAnswerEnabled"
			 * le asigna TRUE si el actor principal es aprendiz y FALSE en caso contrario.
			 */

			{
				"manager1", "Thread title", "thread text", new LinkedList<String>(Arrays.asList("http://www.link1.es", "http://www.link2.es")), new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline10")))),
				"forum6", null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			},
			{
				null, "Thread title", "thread text", new LinkedList<String>(Arrays.asList("http://www.link1.es", "http://www.link2.es")), new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline10")))),
				"forum6", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: usuario no autentificado
			},
			{
				"manager1", "Thread title", "thread text", new LinkedList<String>(Arrays.asList("http://www.link1.es", "link2")), new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline10")))), "forum6",
				IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: uno de los enlaces no es válido
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (List<String>) testingData[i][3], (List<Discipline>) testingData[i][4], super.getEntityId((String) testingData[i][5]),
					(Class<?>) testingData[i][6]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como cualquier actor debe poder publicar nuevo tema en algún foro del sistema.
	 * 
	 * Probamos los métodos create() y save(Thread thread) de la clase ThreadService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final String title, final String text, final List<String> linksAttachments, final List<Discipline> disciplines, final int forumId, final Class<?> expected) {
		Class<?> caught;
		domain.Thread thread, saved;
		Forum forum;
		Actor actor;

		caught = null;

		try {
			super.authenticate(username);
			thread = this.threadService.create();

			forum = this.forumService.findOne(forumId);

			thread.setForum(forum);

			thread.setTitle(title);
			thread.setText(text);
			thread.setDisciplines(disciplines);
			thread.setLinksAttachments(linksAttachments);

			saved = this.threadService.save(thread);
			super.flushTransaction();
			super.unauthenticate();
			/*
			 * La comprobación del método save(Thread thread) termina aquí. Si no ha saltado ninguna
			 * excepción, se ha creado una nueva inversión con éxito.
			 * ____________________________________________________________________________________________________
			 */

			/*
			 * No obstante, vamos a comprobar que el atributo "threads" del foro correspondiente se ha actualizado correctamente
			 */

			Assert.isTrue(forum.getThreads().contains(saved));

			/*
			 * Vamos a comprobar también que el atributo "threads" del actor principal se ha actualizado correctamente
			 */
			actor = this.actorService.findOne(super.getEntityId(username)); // en el PopulateDatabase.xml los usernames y los beannames de los actores coinciden
			Assert.isTrue(actor.getThreads().contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
