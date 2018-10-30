
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

import services.ThreadService;
import utilities.AbstractTest;
import domain.Thread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ListThreadsTest extends AbstractTest {

	// System under test

	@Autowired
	private ThreadService	threadService;


	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método loadThreadsByForum(forumId) de ThreadService:
			 * (Especificamos un foro del sistema, un tema que pertenezca al foro y otro tema que pertenezca a otro foro)
			 */
			{
				"forum9", "thread3", "thread2", null
			// TEST DE CASO POSITIVO: thread3 pertenece a forum9. thread2 no pertenece a forum9
			}, {
				"forum3", "thread5", "thread6", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ambos temas pertenecen a forum5
			}, {
				"forum2", "thread5", "thread6", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ningún tema pertenece a forum2
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template(super.getEntityId((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	/*
	 * REQUISITO FUNCIONAL: Cualquier usuario debe poder consultar una lista con los temas de un determinado foro. Los temas deben estar ordenados según su fecha de última modificación en orden decreciente
	 * Probamos el método "loadThreadsByForum(forumId)" de la clase threadService mediante la siguiente plantilla:
	 */
	protected void template(final int forumId, final int threadId1, final int threadId2, final Class<?> expected) {
		Class<?> caught;
		Collection<Thread> threads;
		Thread thread;

		caught = null;

		try {

			threads = this.threadService.loadThreadsByForum(forumId);

			/*
			 * El hecho de que no haya saltado ninguna excepción no significa que el método esté bien implementado.
			 * Hay que comprobar también que el resultado devuelto es correcto y se corresponde con la realidad.
			 * Vamos a hacer esto mediante el uso de oráculo - vamos a calcular el resultado esperado y luego vamos a comparar
			 * el resultado devuelto con el resultado esperado. Si ambos resultados coinciden, podremos tener cierta confianza
			 * en que el método funciona bien.
			 */

			/*
			 * Elegimos un tema que pertenece al foro especificado(threadId1) y un tema que pertenece a otro foro(threadId2).
			 * El primer tema debe aparecer como elemento de la lista devuelta. El segundo no debe
			 * pertenecer a la lista devuelta.
			 */

			thread = this.threadService.findOne(threadId1);
			Assert.isTrue(threads.contains(thread));

			thread = this.threadService.findOne(threadId2);
			Assert.isTrue(!threads.contains(thread));

			final Iterator<Thread> threadsIter1 = threads.iterator();
			final Iterator<Thread> threadsIter2 = threads.iterator();

			// Vamos a comprobar que la lista de temas está ordenada según la fecha de la última actualización en orden decreciente
			if (threadsIter1.hasNext())
				threadsIter1.next();

			while (threadsIter1.hasNext()) {
				final Thread t1 = threadsIter1.next();
				final Thread t2 = threadsIter2.next();
				Assert.isTrue(t2.getMomentLastModification().after(t1.getMomentLastModification()));
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
