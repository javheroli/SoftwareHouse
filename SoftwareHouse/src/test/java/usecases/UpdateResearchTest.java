
package usecases;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ExpertService;
import services.InvestmentService;
import services.ResearchService;
import utilities.AbstractTest;
import domain.Expert;
import domain.Investment;
import domain.Research;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UpdateResearchTest extends AbstractTest {

	// System under test

	@Autowired
	private ResearchService		researchService;

	// Supporting services

	@Autowired
	private ExpertService		expertService;

	@Autowired
	private InvestmentService	investmentService;


	// Tests

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Para probar el método save(Research research) de ResearchService cuando el objeto pasado como
			 * parámetro de entrada es una investigación instrumentada que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del experto principal, la investigación que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo título("title"), nuevo tema ("subject"), nueva descripción ("description"), nueva página web ("projectWebpage"), nueva fecha de inicio
			 * ("startDate"), nueva fecha de fin ("endDate"), nuevo presupuesto mínimo ("minCost"), nuevo presupuesto ("budget"), si está cancelada ("isCancelled"), nuevo equipo ("team") y
			 * nuevas inversiones ("investments").
			 * 
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Research, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(Research research)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad Research:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 1) team
			 * 
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Research research):
			 * 
			 * 1) budget
			 * 2) isCancelled
			 * 3) investments
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 1) startDate
			 * 2) endDate
			 * 
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los usuarios no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a research hacking).
			 */
			{
				"expert4", "research3", "New Text", "New Subject", "New Description", "http://www.projectWebpage.com", null, null, 4000., 1500., false,
				new LinkedList<Expert>(Arrays.asList(this.expertService.findOne(super.getEntityId("expert1")), this.expertService.findOne(super.getEntityId("expert4")))),
				new LinkedList<Investment>(Arrays.asList(this.investmentService.findOne(super.getEntityId("investment6")))), null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio
			},
			{
				"expert2", "research3", "New Text", "New Subject", "New Description", "http://www.projectWebpage.com", null, null, 4000., 1500., false,
				new LinkedList<Expert>(Arrays.asList(this.expertService.findOne(super.getEntityId("expert1")), this.expertService.findOne(super.getEntityId("expert4")))),
				new LinkedList<Investment>(Arrays.asList(this.investmentService.findOne(super.getEntityId("investment6")))), IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: expert2 no es miembro del equipo de research3
			},
			{
				"expert4", "research3", "New Text", "New Subject", "New Description", "http://www.projectWebpage.com", null, null, 4000., 5000., false,
				new LinkedList<Expert>(Arrays.asList(this.expertService.findOne(super.getEntityId("expert1")), this.expertService.findOne(super.getEntityId("expert4")))),
				new LinkedList<Investment>(Arrays.asList(this.investmentService.findOne(super.getEntityId("investment6")))), IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta cambiar el presupuesto
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				Date startDate;
				if (testingData[i][6] == null)
					startDate = null;
				else
					startDate = new Date((Long) testingData[i][6]);
				Date endDate;
				if (testingData[i][7] == null)
					endDate = null;
				else
					endDate = new Date((Long) testingData[i][7]);

				this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], startDate, endDate,
					(double) testingData[i][8], (double) testingData[i][9], (boolean) testingData[i][10], (List<Expert>) testingData[i][11], (List<Investment>) testingData[i][12], (Class<?>) testingData[i][13]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como EXPERT debe poder modificar cualquiera de las investigaciones en las que participa.
	 * Probamos el método save(Research research) de la clase researchService mediante la siguiente plantilla:
	 * (¡OJO! El objeto research que se pasa como parámetro de entrada ahora es un objeto instrumentado, es decir, una investigación existente en
	 * la base de datos.)
	 */

	protected void template(final String username, final int researchId, final String title, final String subject, final String description, final String projectWebpage, final Date startDate, final Date endDate, final double minCost, final double budget,
		final boolean isCancelled, final List<Expert> team, final List<Investment> investments, final Class<?> expected) {
		Class<?> caught;
		Research research, instrumented;

		caught = null;

		try {
			instrumented = this.researchService.findOne(researchId);

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocándo a los setters,
			 * se van a realizar llamadas implícitas al método save de ResearchRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de ResearchRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			research = new Research();

			research.setId(instrumented.getId());
			research.setVersion(instrumented.getVersion());

			super.authenticate(username);

			research.setTitle(title);
			research.setSubject(subject);
			research.setDescription(description);
			research.setProjectWebpage(projectWebpage);
			research.setStartDate(startDate);
			research.setEndDate(endDate);
			research.setMinCost(minCost);
			research.setBudget(budget);
			research.setIsCancelled(isCancelled);
			research.setTeam(team);
			research.setInvestments(investments);

			this.researchService.save(research);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, la investigación se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
