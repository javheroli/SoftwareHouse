
package usecases;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ActorService;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ListActorsTest extends AbstractTest {

	// System under test

	@Autowired
	private ActorService	actorService;


	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método findAllButAdmins() de ActorService:
			 * (Especificamos el username del USER principal, un follow-up creado por el mismo y un follow-up creado
			 * por otro USER)
			 */
			{
				"expert2", "investor3", "administrator", null
			// TEST DE CASO POSITIVO: se cumplen todas las reglas de negocio
			}, {
				"manager3", "administrator", "administrator", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el adminastador no debe aparecer en la lista
			}, {
				null, "apprentice3", "administrator", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: usuario no autentificado
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	/*
	 * REQUISITO FUNCIONAL: Los usuarios registrados deben poder ver una lista con todos los usuarios del sistema salvo el/los administrador(es)
	 * Probamos el método "findAllButAdmins()" de la clase ActorService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int actorId1, final int actorId2, final Class<?> expected) {
		Class<?> caught;
		Collection<Actor> actors;
		Actor actor;

		caught = null;

		try {
			super.authenticate(username);
			actors = this.actorService.findAllButAdmins();

			/*
			 * El hecho de que no haya saltado ninguna excepción no significa que el método esté bien implementado.
			 * Hay que comprobar también que el resultado devuelto es correcto y se corresponde con la realidad.
			 * Vamos a hacer esto mediante el uso de oráculo - vamos a calcular el resultado esperado y luego vamos a comparar
			 * el resultado devuelto con el resultado esperado. Si ambos resultados coinciden, podremos tener cierta confianza
			 * en que el método funciona bien.
			 */

			/*
			 * Elegimos un aprendiz, experto, inversor u organizador (actorId1) y un administrador (actorId2).
			 * El actor especificado debe aparecer como elemento de la lista devuelta. El administrador no debe
			 * pertenecer a la lista devuelta.
			 */

			actor = this.actorService.findOne(actorId1);
			Assert.isTrue(actors.contains(actor));

			actor = this.actorService.findOne(actorId2);
			Assert.isTrue(!actors.contains(actor));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
