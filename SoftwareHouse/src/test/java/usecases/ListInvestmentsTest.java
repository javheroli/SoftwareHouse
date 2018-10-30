
package usecases;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.InvestmentService;
import utilities.AbstractTest;
import domain.Investment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ListInvestmentsTest extends AbstractTest {

	// System under test

	@Autowired
	private InvestmentService	investmentService;


	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el m�todo findAllByPrincipal() de InvestmentService:
			 * (Especificamos el username del INVESTOR principal, una inversi�n creada por el mismo y una inversi�n creada
			 * por otro INVESTOR)
			 */
			{
				"investor1", "investment1", "investment2", null
			// TEST DE CASO POSITIVO: investor1 es el que ha creado investment1, no investment2 la he creado otro inversor
			}, {
				"investor1", "investment1", "investment3", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: ambas inversiones las ha creado investor1
			}, {
				"investor3", "investment1", "investment3", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: investor3 no ha creado investment1, ni investment3
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	/*
	 * REQUISITO FUNCIONAL: Los inversores deben poder ver una lista con todas las inversiones que han realizado
	 * Probamos el m�todo "findAllByPrincipal()" de la clase InvestmentService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final int investmentId1, final int investmentId2, final Class<?> expected) {
		Class<?> caught;
		Collection<Investment> investments;
		Investment investment;

		caught = null;

		try {
			super.authenticate(username);
			investments = this.investmentService.findAllByPrincipal();

			/*
			 * El hecho de que no haya saltado ninguna excepci�n no significa que el m�todo est� bien implementado.
			 * Hay que comprobar tambi�n que el resultado devuelto es correcto y se corresponde con la realidad.
			 * Vamos a hacer esto mediante el uso de or�culo - vamos a calcular el resultado esperado y luego vamos a comparar
			 * el resultado devuelto con el resultado esperado. Si ambos resultados coinciden, podremos tener cierta confianza
			 * en que el m�todo funciona bien.
			 */

			/*
			 * Elegimos una inversi�n realizada por el inversor principal (investmentId1) y una inversi�n realizada por otro inversor (investmentId2).
			 * La primera inversi�n debe aparecer como elemento de la lista devuelta. La segunda inversi�n no debe
			 * pertenecer a la lista devuelta.
			 */

			investment = this.investmentService.findOne(investmentId1);
			Assert.isTrue(investments.contains(investment));

			investment = this.investmentService.findOne(investmentId2);
			Assert.isTrue(!investments.contains(investment));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
