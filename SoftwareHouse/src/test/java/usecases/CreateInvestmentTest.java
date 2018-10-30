
package usecases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.InvestmentService;
import services.ResearchService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Investment;
import domain.Research;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreateInvestmentTest extends AbstractTest {

	// System under test

	@Autowired
	private InvestmentService	investmentService;

	// Supporting services

	@Autowired
	private ResearchService		researchService;


	// Tests

	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método save(Investment investment) de InvestmentService:
			 * (Especificamos el username del INVESTOR principal, la cantidad de dinero a invertir y la investigación que recibirá la inversión.
			 * Especificamos también los datos de la tarjeta de crédito: el nombre del titular, el nombre comercial, el número,
			 * el mes de vencimiento, el año de vencimiento y el código CVV. No vamos a especificar el inversor que realiza la inversión ("investor"),
			 * puesto que el valor a dicho atributo se asigna automáticamente en el método create() y sabemos que a la hora de crear una nueva inversión
			 * siempre se invoca primero al método create() y después al save(Investment investment). Es reduntante
			 * hacer pruebas sobre el método create() ya que dicho método simplemente crea una instancia de Investment y le asigna al atributo "investor"
			 * el inversor principal
			 */

			{
				"investor3", 2000., "research2", "Andrés", "Banco Santander", "372009947661154", 11, 19, 656, null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			}, {
				"investor3", 2000., "research2", "Andrés", "Banco Santander", "372009947661154", 05, 18, 656, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: la tarjeta de crédito ha caducado
			}, {
				"investor3", 2000., "research1", "Andrés", "Banco Santander", "372009947661154", 05, 20, 656, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: research1 ha finalizada
			}, {
				"investor3", 2000., "research4", "Andrés", "Banco Santander", "372009947661154", 05, 20, 656, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: research4 ha sido cancelada
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (Double) testingData[i][1], super.getEntityId((String) testingData[i][2]), (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (int) testingData[i][6],
					(int) testingData[i][7], (int) testingData[i][8], (Class<?>) testingData[i][9]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como INVESTOR debe poder crear nueva inversión y asociarla a una investigación que necesita
	 * financiación (que no éste cancelada, ni finalizada). Para hacer esto, el inversor debe introducir los datos de una tarjeta de crédito válida que no
	 * esté caducada y que no caduque durante el mes actual.
	 * 
	 * Probamos los métodos create() y save(Investment investment) de la clase InvestmentService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final double amount, final int researchId, final String holderName, final String brandName, final String number, final int expirationMonth, final int expirationYear, final int CVV, final Class<?> expected) {
		Class<?> caught;
		Investment investment, saved;
		CreditCard creditCard;
		Research research;
		double budget;

		caught = null;

		try {
			super.authenticate(username);
			investment = this.investmentService.create();

			research = this.researchService.findOne(researchId);

			investment.setAmount(amount);
			investment.setResearch(research);

			creditCard = new CreditCard();
			creditCard.setHolderName(holderName);
			creditCard.setBrandName(brandName);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setNumber(number);
			creditCard.setCVV(CVV);

			investment.setCreditCard(creditCard);

			// Antes de invocar al método save, vamos a guardar en una variable el presupuesto actual de la investigación asociada

			budget = research.getBudget();

			saved = this.investmentService.save(investment);
			super.flushTransaction();
			super.unauthenticate();
			/*
			 * La comprobación del método save(Investment investment) termina aquí. Si no ha saltado ninguna
			 * excepción, se ha creado una nueva inversión con éxito.
			 * ____________________________________________________________________________________________________
			 */

			/*
			 * No obstante, vamos a comprobar que el atributo "investments" de la investigación asociada se ha actualizado correctamente
			 */

			Assert.isTrue(research.getInvestments().contains(saved));

			/*
			 * Comprobamos también que el presupuesto de la investigación asociada se ha actualizado correctamente.
			 */

			Assert.isTrue(saved.getResearch().getBudget() == budget + amount);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
