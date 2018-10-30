
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
			 * Con estos datos probamos el método findAllThatNeedFunding() de ResearchService:
			 * (Especificamos el username del INVESTOR principal, una investigación que necesite financiación y otra que no la necesite)
			 */
			{
				"investor1", "research2", "research1", null
			// TEST DE CASO POSITIVO: research2 necesita financiación, research1 no la necesita
			}, {
				"investor1", "research2", "research3", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ambas investigaciones necesitan financiación
			}, {
				"investor2", "research1", "research4", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ninguna de las investigaciones necesita financiación
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	/*
	 * REQUISITO FUNCIONAL: Los inversores deben poder ver una lista con todas las investigaciones que necesitan financiación
	 * Las investigaciones que necesitan financiación son aquellas que no están finalizadas y no han sido canceladas
	 * Probamos el método "findAllThatNeedFunding()" de la clase ResearchService mediante la siguiente plantilla:
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
			 * El hecho de que no haya saltado ninguna excepción no significa que el método esté bien implementado.
			 * Hay que comprobar también que el resultado devuelto es correcto y se corresponde con la realidad.
			 * Vamos a hacer esto mediante el uso de oráculo - vamos a calcular el resultado esperado y luego vamos a comparar
			 * el resultado devuelto con el resultado esperado. Si ambos resultados coinciden, podremos tener cierta confianza
			 * en que el método funciona bien.
			 */

			/*
			 * Elegimos una investigación que necesita financiación(researchId1) y otra investigación que no necesita financiación(researchId2).
			 * La primera investigación debe aparecer como elemento de la lista devuelta. La segunda investigación no debe
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
