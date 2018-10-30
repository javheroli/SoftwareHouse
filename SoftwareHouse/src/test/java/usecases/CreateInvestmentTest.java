
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
			 * Con estos datos probamos el m�todo save(Investment investment) de InvestmentService:
			 * (Especificamos el username del INVESTOR principal, la cantidad de dinero a invertir y la investigaci�n que recibir� la inversi�n.
			 * Especificamos tambi�n los datos de la tarjeta de cr�dito: el nombre del titular, el nombre comercial, el n�mero,
			 * el mes de vencimiento, el a�o de vencimiento y el c�digo CVV. No vamos a especificar el inversor que realiza la inversi�n ("investor"),
			 * puesto que el valor a dicho atributo se asigna autom�ticamente en el m�todo create() y sabemos que a la hora de crear una nueva inversi�n
			 * siempre se invoca primero al m�todo create() y despu�s al save(Investment investment). Es reduntante
			 * hacer pruebas sobre el m�todo create() ya que dicho m�todo simplemente crea una instancia de Investment y le asigna al atributo "investor"
			 * el inversor principal
			 */

			{
				"investor3", 2000., "research2", "Andr�s", "Banco Santander", "372009947661154", 11, 19, 656, null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			}, {
				"investor3", 2000., "research2", "Andr�s", "Banco Santander", "372009947661154", 05, 18, 656, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: la tarjeta de cr�dito ha caducado
			}, {
				"investor3", 2000., "research1", "Andr�s", "Banco Santander", "372009947661154", 05, 20, 656, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: research1 ha finalizada
			}, {
				"investor3", 2000., "research4", "Andr�s", "Banco Santander", "372009947661154", 05, 20, 656, IllegalArgumentException.class
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
	 * REQUISITO FUNCIONAL: Un usuario registrado como INVESTOR debe poder crear nueva inversi�n y asociarla a una investigaci�n que necesita
	 * financiaci�n (que no �ste cancelada, ni finalizada). Para hacer esto, el inversor debe introducir los datos de una tarjeta de cr�dito v�lida que no
	 * est� caducada y que no caduque durante el mes actual.
	 * 
	 * Probamos los m�todos create() y save(Investment investment) de la clase InvestmentService mediante la siguiente plantilla:
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

			// Antes de invocar al m�todo save, vamos a guardar en una variable el presupuesto actual de la investigaci�n asociada

			budget = research.getBudget();

			saved = this.investmentService.save(investment);
			super.flushTransaction();
			super.unauthenticate();
			/*
			 * La comprobaci�n del m�todo save(Investment investment) termina aqu�. Si no ha saltado ninguna
			 * excepci�n, se ha creado una nueva inversi�n con �xito.
			 * ____________________________________________________________________________________________________
			 */

			/*
			 * No obstante, vamos a comprobar que el atributo "investments" de la investigaci�n asociada se ha actualizado correctamente
			 */

			Assert.isTrue(research.getInvestments().contains(saved));

			/*
			 * Comprobamos tambi�n que el presupuesto de la investigaci�n asociada se ha actualizado correctamente.
			 */

			Assert.isTrue(saved.getResearch().getBudget() == budget + amount);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
