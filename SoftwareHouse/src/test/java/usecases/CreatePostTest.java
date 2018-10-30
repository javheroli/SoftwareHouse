
package usecases;

import java.util.ArrayList;
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
import org.springframework.util.Assert;

import services.ActorService;
import services.PostService;
import services.ThreadService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreatePostTest extends AbstractTest {

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
			 * Con estos datos probamos el m�todo save(Post post) de PostService cuando queremos asociar la respuesta a un tema:
			 * (Especificamos el username del actor principal, el texto, los enlaces de ficheros adjuntos y el tema al que la respuesta estar� asociada.
			 * No vamos a especificar el momento de publicaci�n ("publicationMoment"), ni el n�mero de respuestas ("numPosts"),
			 * ni el autor ("writer"), ni las respuestas ("posts"), ni si la respuesta es fiable ("isReliable"), ni si est� marcada como borrada ("isDeleted"),
			 * ni si est� marcada como 'Mejor respuesta' ("isBestAnswer"), puesto que los valores de estos atributos se asignan autom�ticamente en el
			 * m�todo create() y sabemos que a la hora de crear una nueva respuesta siempre se invoca primero al m�todo create() y despu�s al save(Post post). Es reduntante
			 * hacer pruebas sobre el m�todo create() ya que dicho m�todo simplemente crea una instancia de Post y le asigna al atributo "publicationMoment"
			 * el momento actual, al atributo "numPost" le asigna 0, al "writer" - el actor principal, al "posts" una colecci�n vac�a, y a los atributos "isReliable", "isBestAnswer"
			 * y "isDeleted" - valor FALSE.
			 */

			{
				"investor3", "Post text", new LinkedList<String>(Arrays.asList("http://www.link1.es", "http://www.link2.es")), "thread1", null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			}, {
				"apprentice1", null, new LinkedList<String>(), "thread1", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: la respuesta no tiene texto
			}, {
				null, "Post text", new LinkedList<String>(), "thread1", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: usuario no autentificado
			},
			// Vamos a probar ahora el mismo m�todo, pero esta vez vamos a asociar la respuesta a otra respuesta:
			// (Especificamos el username del actor principal, el texto, los enlaces de ficheros adjuntos y la respuesta a la que la respuesta estar� asociada.
			{
				"expert1", "Post text", new LinkedList<String>(), "post24", null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			}, {
				"expert1", "Post text", new LinkedList<String>(), "post28", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: post28 est� marcado como "BORRADO"
			}, {
				"expert2", "Post text", new LinkedList<String>(Arrays.asList("link")), "post24", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se ha introducido enlace inv�lido
			}

		};

		for (int i = 0; i < 3; i++)
			try {
				super.startTransaction();
				this.template1((String) testingData[i][0], (String) testingData[i][1], (List<String>) testingData[i][2], super.getEntityId((String) testingData[i][3]), (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 3; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template2((String) testingData[i][0], (String) testingData[i][1], (List<String>) testingData[i][2], super.getEntityId((String) testingData[i][3]), (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}
	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como cualquier actor debe poder escribir una respuesta. La nueva respuesta se debe asociar
	 * bien a un tema, o bien a otra respuesta. Es decir, cada foro tiene �rboles de temas y respuestas. Tiene tantos �rboles como temas tenga
	 * de manera que cada tema es la ra�z de un �rbol distinto. Los v�rtices restantes del �rbol son las respuestas. Es decir, a la hora de crear
	 * una nueva respuesta, dicha respuesta se puede asociar bien a un tema (la ra�z del �rbol) o bien a cualquiera de los dem�s v�rtices (las dem�s respuestas).
	 * 
	 * Un actor no puede escribir respuestas a respuestas marcadas como "BORRADAS".
	 * 
	 * 
	 * Vamos a distinguir dos casos. Primero, vamos a crear una respuesta a un tema. Es decir, a la respuesta a crear
	 * le vamos a asignar un tema. Usaremos la siguiente plantilla para probar los m�todos create() y save de PostService:
	 */

	protected void template1(final String username, final String text, final List<String> linksAttachments, final int threadId, final Class<?> expected) {
		Class<?> caught;
		Post post, saved;
		final Post lastPostForum, lastPostThread;
		domain.Thread thread;
		final int numPostsForum, numPostsThread;
		Actor writer;

		/*
		 * "numPosts" y "lastPost" son atributos derivados de las entidades Thread y Forum.
		 * "numPosts" representa el n�mero de respuestas que tiene el tema. Vamos a almacenar en una variable el n�mero de respuestas que tiene el
		 * tema especificado y el foro al que dicho tema pertenece, y vamos a probar que despu�s de crear la nueva respuesta a dicho comentario, el valor de este atributo se incrementa en 1.
		 * (Aunque el caso m�s interesante es cuando se crea respuesta a otra respuesta. Es entonces cuando se usa recursividad para actualizar los valores de los atributos "numPosts").
		 * El atributo "lastPost" tambi�n es un atributo derivado que hace referencia a la �ltima respueta escrita en un tema o foro. Vamos a comprobar que al crear nueva respuesta
		 * dicho atributo se actualiza correctamente.
		 */

		thread = this.threadService.findOne(threadId);

		numPostsThread = thread.getNumPosts();
		numPostsForum = thread.getForum().getNumPosts();

		lastPostThread = thread.getLastPost();
		lastPostForum = thread.getForum().getLastPost();

		caught = null;

		try {
			super.authenticate(username);
			post = this.postService.create();

			post.setText(text);
			post.setLinksAttachments(linksAttachments);

			post.setThread(thread);
			post.setTopic(thread);
			post.setParentPost(null);

			saved = this.postService.save(post);
			super.flushTransaction();
			super.unauthenticate();

			/*
			 * La comprobaci�n del m�todo save termina aqu�. Si no ha saltado ninguna
			 * excepci�n, se ha creado una nueva respuesta con �xito.
			 * ____________________________________________________________________________________________________
			 */

			/*
			 * No obstante, en la siguiente porci�n de c�digo vamos a comprobar que la propiedad "posts" del
			 * tema especificado se ha actualizado correctamente (es decir, que la colecci�n de respuestas del tema
			 * ya contiene la nueva respuesta creada).
			 */

			Assert.isTrue(thread.getPosts().contains(saved));

			// Vamos a comprobar tambi�n que el atributo "posts" del actor principal se actualiza correctamente:
			writer = this.actorService.findOne(super.getEntityId(username)); // en el PopulateDatabase.xml los usernames y los beannames de los actores coinciden
			Assert.isTrue(writer.getPosts().contains(saved));

			/*
			 * Como hemos dicho anteriormente, vamos a comprobar tambi�n que el n�mero total de respuestas tanto en el tema especificado como en el foro correspondiente
			 * se ha actualizado correctamente (es decir, se ha incrementado en 1).
			 */

			thread = saved.getThread();
			Assert.isTrue(thread.getNumPosts() == numPostsThread + 1);
			Assert.isTrue(thread.getForum().getNumPosts() == numPostsForum + 1);

			// Comprobamos tambi�n la actualizaci�n del atributo "lastPost" del tema especificado
			Assert.isTrue(thread.getLastPost().getId() != lastPostThread.getId());
			Assert.isTrue(thread.getLastPost().getId() == saved.getId());

			Assert.isTrue(thread.getForum().getLastPost().getId() != lastPostForum.getId());
			Assert.isTrue(thread.getForum().getLastPost().getId() == saved.getId());

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * En esta segunda plantilla vamos a crear una respuesta a otra respuesta. Es decir, a la respuesta a crear
	 * le vamos a asignar una respuesta ya existente. Usaremos la siguiente plantilla para probar los m�todos create() y save de PostService:
	 */

	protected void template2(final String username, final String text, final List<String> linksAttachments, final int parentPostId, final Class<?> expected) {
		Class<?> caught;
		Post post, saved, parentPost;
		List<Integer> numPosts, updated;
		Actor writer;

		parentPost = this.postService.findOne(parentPostId);

		/*
		 * "numPosts" es un atributo derivado de la entidad Post. Este atributo representa el n�mero total de respuestas que tiene la respuesta.
		 * Esto es, el n�mero de respuestas que est�n en la colecci�n "posts" de la respuesta m�s la suma de los "numPosts" de cada una de ellas.
		 * Cuando se crea una nueva respuesta a una respuesta ya existente, el atributo "numPosts" de �sta se debe incrementar en 1. Pero tambi�n se deben
		 * incrementar en 1 los atributos "numPosts" de todas las respuestas que son v�rtices antecesores de dicha respuesta en el �rbol y tambi�n
		 * se debe incrementar en 1 el "numPosts" del tema (el ra�z del �rbol).
		 * Vamos a probar que todas estas actualizaciones se realizan correctamente. En una lista vamos a almacenar los valores de los atributos "numPosts"
		 * de todas las respuestas "antecesores" de la nueva respuesta a crear. Al final de la lista, a�adiremos el valor del atributo "numPosts" del comentario "ra�z".
		 */

		numPosts = new ArrayList<Integer>();
		numPosts.add(parentPost.getNumPosts());

		Post iterator;
		iterator = parentPost;
		while (iterator.getParentPost() != null) {
			iterator = iterator.getParentPost();
			numPosts.add(iterator.getNumPosts());
		}

		numPosts.add(iterator.getThread().getNumPosts());

		caught = null;

		try {
			super.authenticate(username);
			post = this.postService.create();

			post.setText(text);
			post.setLinksAttachments(linksAttachments);
			post.setParentPost(parentPost);
			post.setThread(null);
			post.setTopic(parentPost.getTopic());

			saved = this.postService.save(post);
			super.unauthenticate();
			super.flushTransaction();

			/*
			 * La comprobaci�n del m�todo save termina aqu�. Si no ha saltado ninguna
			 * excepci�n, se ha creado una nueva respuesta con �xito.
			 * ____________________________________________________________________________________________________
			 */

			/*
			 * No obstante, en la siguiente porci�n de c�digo vamos a comprobar que la propiedad "posts" del
			 * la respuesta especificada se ha actualizado correctamente (es decir, que la colecci�n de respuestas de la respuesta
			 * ya contiene la nueva respuesta creada).
			 */

			Assert.isTrue(parentPost.getPosts().contains(saved));

			// Vamos  comprobar tambi�n que el atributo "posts" del actor principal se ha actualizado correctamente

			writer = this.actorService.findOne(super.getEntityId(username)); // en el PopulateDatabase.xml los usernames y los beannames de los actores coinciden
			Assert.isTrue(writer.getPosts().contains(saved));

			// Comprobamos tambi�n la actualizaci�n correcta de los atributos "numPosts" de las respuestas "antecesores". Vamos a crear una nueva lista con los
			// valores de los atributos supuestamente actualizados. Al final probamos que cada elemento de la nueva lista es igual al elemento correspondiente de la
			// primera lista + 1.

			updated = new ArrayList<Integer>();
			updated.add(parentPost.getNumPosts());

			iterator = parentPost;
			while (iterator.getParentPost() != null) {
				iterator = iterator.getParentPost();
				updated.add(iterator.getNumPosts());
			}

			updated.add(iterator.getThread().getNumPosts());

			for (int i = 0; i < numPosts.size(); i++)
				Assert.isTrue(updated.get(i) == numPosts.get(i) + 1);

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);

	}

}
