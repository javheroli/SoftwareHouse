
package usecases;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ResearchService;
import utilities.AbstractTest;
import domain.Research;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ListResearchTest extends AbstractTest {

	// System under test

	@Autowired
	private ResearchService	researchService;


	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el m�todo findAllThatNeedFunding() de ResearchService:
			 * (Especificamos el username del INVESTOR principal, una investigaci�n que necesite financiaci�n y otra que no la necesite)
			 */
			{
				"investor1", "research2", "research1", null
			// TEST DE CASO POSITIVO: research2 necesita financiaci�n, research1 no la necesita
			}, {
				"investor1", "research2", "research3", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ambas investigaciones necesitan financiaci�n
			}, {
				"investor2", "research1", "research4", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ninguna de las investigaciones necesita financiaci�n
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	/*
	 * REQUISITO FUNCIONAL: Los inversores deben poder ver una lista con todas las investigaciones que necesitan financiaci�n
	 * Las investigaciones que necesitan financiaci�n son aquellas que no est�n finalizadas y no han sido canceladas
	 * Probamos el m�todo "findAllThatNeedFunding()" de la clase ResearchService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int researchId1, final int researchId2, final Class<?> expected) {
		Class<?> caught;
		Collection<Research> researchs;
		Research research;

		caught = null;

		try {
			super.authenticate(username);
			researchs = this.researchService.findAllThatNeedFunding();

			/*
			 * El hecho de que no haya saltado ninguna excepci�n no significa que el m�todo est� bien implementado.
			 * Hay que comprobar tambi�n que el resultado devuelto es correcto y se corresponde con la realidad.
			 * Vamos a hacer esto mediante el uso de or�culo - vamos a calcular el resultado esperado y luego vamos a comparar
			 * el resultado devuelto con el resultado esperado. Si ambos resultados coinciden, podremos tener cierta confianza
			 * en que el m�todo funciona bien.
			 */

			/*
			 * Elegimos una investigaci�n que necesita financiaci�n(researchId1) y otra investigaci�n que no necesita financiaci�n(researchId2).
			 * La primera investigaci�n debe aparecer como elemento de la lista devuelta. La segunda investigaci�n no debe
			 * pertenecer a la lista devuelta.
			 */

			research = this.researchService.findOne(researchId1);
			Assert.isTrue(researchs.contains(research));

			research = this.researchService.findOne(researchId2);
			Assert.isTrue(!researchs.contains(research));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
