
package usecases;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ActorService;
import services.DisciplineService;
import services.ForumService;
import services.PostService;
import services.ThreadService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Discipline;
import domain.Forum;
import domain.Post;
import domain.Thread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UpdateThreadTest extends AbstractTest {

	// System under test

	@Autowired
	private ThreadService		threadService;

	// Supporting services

	@Autowired
	private PostService			postService;

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
			 * Para probar el método save(Thread thread) de ThreadService cuando el objeto pasado como
			 * parámetro de entrada es un tema instrumentado que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del principal, el tema que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo título ("title"), nuevo texto ("text"), nuevos enlaces de ficheros adjuntos ("linksAttachments"), nueva lista de disciplinas ("disciplines"), nuevo momento de publicación ("startMoment"),
			 * nuevo momento de última modificación ("momentLastModification"), nuevo número de respuestas("numPosts"), nuevo autor ("writer"), nuevo foro ("forum"), nuevas respuestas ("posts"), nueva última respuesta ("lastPost") y
			 * nuevo valor del atributo ("isBestAnswerEnabled").
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Thread, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(Thread thread)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad thread:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 1) startMoment
			 * 2) writer
			 * 3) forum
			 * 4) disciplines
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Thread thread):
			 * 
			 * 1) posts
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 1) numPosts
			 * 2) lastPost
			 * 3) momentLastModification
			 * 4) isBestAnswerEnabled
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los usuarios no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
			 */
			{
				"manager1", "thread9", "New Title", "New Text", new LinkedList<String>(), new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline16")))), 1525183200000l, 1525800720000l, 4, "manager1",
				"forum2", new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post31")), this.postService.findOne(super.getEntityId("post33")))), "post34", false, null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio
			},
			{
				"manager1", "thread9", "New Title", "New Text", new LinkedList<String>(), new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline16")))), 1525183200000l, 1525800720000l, 4, "manager3",
				"forum2", new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post31")), this.postService.findOne(super.getEntityId("post33")))), "post34", false, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta cambiar el autor del tema
			},
			{
				"manager1", "thread9", "New Title", "New Text", new LinkedList<String>(), new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline15")))), 1525183200000l, 1525800720000l, 4, "manager1",
				"forum2", new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post31")), this.postService.findOne(super.getEntityId("post33")))), "post34", false, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta cambiar la disciplina del tema
			},
			{
				"manager1", "thread9", "New Title", "New Text", new LinkedList<String>(), new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline16")))), 1525204920000l, 1525800720000l, 4, "manager1",
				"forum2", new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post31")), this.postService.findOne(super.getEntityId("post33")))), "post34", false, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta cambiar la fecha de publicación
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (List<String>) testingData[i][4], (List<Discipline>) testingData[i][5], new Date(
					(Long) testingData[i][6]), new Date((Long) testingData[i][7]), (int) testingData[i][8], super.getEntityId((String) testingData[i][9]), super.getEntityId((String) testingData[i][10]), (List<Post>) testingData[i][11], super
					.getEntityId((String) testingData[i][12]), (boolean) testingData[i][13], (Class<?>) testingData[i][14]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como cualquier actor debe poder modificar cualquiera de sus temas.
	 * Probamos el método save(Thread thread) de la clase ThreadService mediante la siguiente plantilla:
	 * (¡OJO! El objeto thread que se pasa como parámetro de entrada ahora es un objeto instrumentado, es decir, un tema existente en
	 * la base de datos.)
	 */

	protected void template(final String username, final int threadId, final String title, final String text, final List<String> linksAttachments, final List<Discipline> disciplines, final Date startMoment, final Date momentLastModification,
		final int numPosts, final int writerId, final int forumId, final List<Post> posts, final int lastPostId, final boolean isBestAnswerEnabled, final Class<?> expected) {
		Class<?> caught;
		Thread thread, instrumented;
		Post lastPost;
		Actor writer;
		Forum forum;

		caught = null;

		try {
			instrumented = this.threadService.findOne(threadId);

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocándo a los setters,
			 * se van a realizar llamadas implícitas al método save de ThreadRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de ThreadRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			thread = new Thread();

			thread.setId(instrumented.getId());
			thread.setVersion(instrumented.getVersion());

			super.authenticate(username);

			thread.setTitle(title);
			thread.setText(text);
			thread.setLinksAttachments(linksAttachments);
			thread.setDisciplines(disciplines);
			thread.setNumPosts(numPosts);
			thread.setPosts(posts);
			thread.setMomentLastModification(momentLastModification);
			thread.setStartMoment(startMoment);
			thread.setIsBestAnswerEnabled(isBestAnswerEnabled);

			lastPost = this.postService.findOne(lastPostId);
			thread.setLastPost(lastPost);

			writer = this.actorService.findOne(writerId);
			thread.setWriter(writer);

			forum = this.forumService.findOne(forumId);
			thread.setForum(forum);

			thread.setLastPost(lastPost);

			this.threadService.save(thread);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el tema se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
