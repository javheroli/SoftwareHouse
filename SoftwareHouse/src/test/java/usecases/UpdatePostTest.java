
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
import services.PostService;
import services.ThreadService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Post;
import domain.Thread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UpdatePostTest extends AbstractTest {

	// System under test

	@Autowired
	private PostService		postService;

	// Supporting services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ThreadService	threadService;


	// Tests

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Para probar el método save(Post post) de PostService cuando el objeto pasado como
			 * parámetro de entrada es una respuesta instrumentada que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del principal, la respuesta que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo texto ("text"), nuevos enlaces de ficheros adjuntos ("linksAttachments"), nuevo momento de publicación ("publicationMoment"),
			 * si la respuesta está marcada como 'Mejor respuesta' ("isBestAnswer"), si es fiable ("isReliable"), si está marcada como BORRADA ("isDeleted"),
			 * nuevo número de respuestas("numPosts"), nuevas respuestas ("posts"), nueva respuesta 'padre' ("parentPost"),nuevo tema 'padre' ("thread"), nuevo autor ("writer") y nuevo asunto ("topic").
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de post, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(post post)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad Post:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 1) publicationMoment
			 * 2) writer
			 * 3) parentPost
			 * 4) thread
			 * 5) topic
			 * 6) isReliable
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Post post):
			 * 
			 * 1) posts
			 * 2) isDeleted
			 * 3) isBestAnswer
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 1) numPosts
			 * 
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los usuarios no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
			 */
			{
				"apprentice2", "post33", "New Text", new LinkedList<String>(), 1525782420000l, false, false, false, 1, new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post34")))), null, "thread9", "apprentice2", "thread9",
				null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio
			},
			{
				"apprentice2", "post33", "New Text", new LinkedList<String>(), 1525782420000l, false, false, false, 1,
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post34")), this.postService.findOne(super.getEntityId("post32")))), null, "thread9", "apprentice2", "thread9", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta cambiar añadir nueva respuesta a la lista de respuestas hijas
			},
			{
				"apprentice2", "post33", "New Text", new LinkedList<String>(), 1525782420000l, true, false, false, 1, new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post34")))), null, "thread9", "apprentice2", "thread9",
				IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta marcar la respuesta como "Mejor respuesta"
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				Integer parentPostId;
				if (testingData[i][10] == null)
					parentPostId = null;
				else
					parentPostId = super.getEntityId((String) testingData[i][10]);
				Integer threadId;
				if (testingData[i][11] == null)
					threadId = null;
				else
					threadId = super.getEntityId((String) testingData[i][11]);

				this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (List<String>) testingData[i][3], new Date((Long) testingData[i][4]), (boolean) testingData[i][5],
					(boolean) testingData[i][6], (boolean) testingData[i][7], (int) testingData[i][8], (List<Post>) testingData[i][9], parentPostId, threadId, super.getEntityId((String) testingData[i][12]), super.getEntityId((String) testingData[i][13]),
					(Class<?>) testingData[i][14]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como cualquier actor debe poder modificar cualquiera de sus respuestas salvo las que estén marcadas como BORRADAS.
	 * Probamos el método save(Post post) de la clase PostService mediante la siguiente plantilla:
	 * (¡OJO! El objeto post que se pasa como parámetro de entrada ahora es un objeto instrumentado, es decir, una respuesta existente en
	 * la base de datos.)
	 */

	protected void template(final String username, final int postId, final String text, final List<String> linksAttachments, final Date publicationMoment, final boolean isBestAnswer, final boolean isReliable, final boolean isDeleted, final int numPosts,
		final List<Post> posts, final Integer parentPostId, final Integer threadId, final int writerId, final int topicId, final Class<?> expected) {
		Class<?> caught;
		Post post, instrumented;
		Post parentPost;
		Actor writer;
		final Thread thread, topic;

		caught = null;

		try {
			instrumented = this.postService.findOne(postId);

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocándo a los setters,
			 * se van a realizar llamadas implícitas al método save de PostRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de PostRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			post = new Post();

			post.setId(instrumented.getId());
			post.setVersion(instrumented.getVersion());

			super.authenticate(username);

			post.setText(text);
			post.setLinksAttachments(linksAttachments);
			post.setPublicationMoment(publicationMoment);
			post.setIsBestAnswer(isBestAnswer);
			post.setIsReliable(isReliable);
			post.setIsDeleted(isDeleted);
			post.setNumPosts(numPosts);
			post.setPosts(posts);

			if (parentPostId != null) {
				parentPost = this.postService.findOne(parentPostId);
				post.setParentPost(parentPost);
			} else
				post.setParentPost(null);

			if (threadId != null) {
				thread = this.threadService.findOne(threadId);
				post.setThread(thread);
			} else
				post.setThread(null);

			writer = this.actorService.findOne(writerId);
			post.setWriter(writer);

			topic = this.threadService.findOne(topicId);
			post.setTopic(topic);

			this.postService.save(post);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, la respuesta se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
