
package usecases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ResearchService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CancelResearchTest extends AbstractTest {

	// System under test

	@Autowired
	private ResearchService	researchService;


	// Supporting services

	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método cancel(int researchId) de ResearchService:
			 * (Especificamos el username del experto principal y una investigación)
			 */
			{
				"expert1", "research2", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			}, {
				"expert2", "research4", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: research4 ya ha sido cancelada
			}, {
				"expert2", "research2", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: expert2 no es miembro del equipo de research2
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
	 * REQUISITO FUNCIONAL: Un usuario registrado como EXPERT debe poder cancelar una investigación siempre que el experto sea un miembro del equipo de la investigación.
	 * La investigación no debe estar cancelada, ni finalizada, pero es necesario que haya comenzado (su fecha de inicio no debe ser nula)
	 * Probamos el método cancel(int researchId) de la clase ResearchService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int researchId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);

			this.researchService.cancel(researchId);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el experto principal ha cancelado la investigación con éxito

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
}
