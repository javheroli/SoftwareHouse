
package usecases;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.PostService;
import utilities.AbstractTest;
import domain.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ListPostsTest extends AbstractTest {

	// System under test

	@Autowired
	private PostService	postService;


	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método loadPostsByThread(threadId) de PostService:
			 * (Especificamos un tema del sistema, una respuesta que pertenezca al tema y otra respuesta que pertenezca a otro tema)
			 */
			{
				"thread3", "post6", "post11", null
			// TEST DE CASO POSITIVO: post6 pertenece a thread3. post11 no pertenece a thread3
			}, {
				"thread5", "post13", "post21", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ambas respuestas pertenecen a thread5
			}, {
				"thread9", "post13", "post21", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ninguna de las respuestas pertenece a thread9
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template(super.getEntityId((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	/*
	 * REQUISITO FUNCIONAL: Cualquier usuario debe poder consultar una lista con las respuestas escritas en un determinado tema.Las respuestas deben estar ordenados según su fecha de publicación en orden creciente
	 * Probamos el método "loadPostsByThread(threadId)" de la clase postService mediante la siguiente plantilla:
	 */
	protected void template(final int threadId, final int postId1, final int postId2, final Class<?> expected) {
		Class<?> caught;
		Collection<Post> posts;
		Post post;

		caught = null;

		try {

			posts = this.postService.loadPostsByThread(threadId);

			/*
			 * El hecho de que no haya saltado ninguna excepción no significa que el método esté bien implementado.
			 * Hay que comprobar también que el resultado devuelto es correcto y se corresponde con la realidad.
			 * Vamos a hacer esto mediante el uso de oráculo - vamos a calcular el resultado esperado y luego vamos a comparar
			 * el resultado devuelto con el resultado esperado. Si ambos resultados coinciden, podremos tener cierta confianza
			 * en que el método funciona bien.
			 */

			/*
			 * Elegimos una respuesta que pertenece al tema especificado(postId1) y una respuesta que pertenece a otro tema(postId2).
			 * La primera respuesta debe aparecer como elemento de la lista devuelta. La segunda no debe
			 * pertenecer a la lista devuelta.
			 */

			post = this.postService.findOne(postId1);
			Assert.isTrue(posts.contains(post));

			post = this.postService.findOne(postId2);
			Assert.isTrue(!posts.contains(post));

			final Iterator<Post> postsIter1 = posts.iterator();
			final Iterator<Post> postsIter2 = posts.iterator();

			// Vamos a comprobar que la lista de temas está ordenada según la fecha de publicación en orden creciente
			if (postsIter1.hasNext())
				postsIter1.next();

			while (postsIter1.hasNext()) {
				final Post p1 = postsIter1.next();
				final Post p2 = postsIter2.next();
				Assert.isTrue(p2.getPublicationMoment().before(p1.getPublicationMoment()));
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
