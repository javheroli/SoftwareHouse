
package usecases;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ExpertService;
import services.ResearchService;
import utilities.AbstractTest;
import domain.Expert;
import domain.Research;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreateResearchTest extends AbstractTest {

	// System under test

	@Autowired
	private ResearchService	researchService;

	// Supporting services

	@Autowired
	private ExpertService	expertService;


	// Tests

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método save(Research research) de ResearchService:
			 * (Especificamos el username del EXPERT principal, el título, el tema, la descripción, la página web, el presupuesto mínimo y los miembros del equipo
			 * de la nueva investigación. No vamos a especificar la fecha de inicio ("startDate"), ni la fecha de fin ("endDate"), ni el presupuesto actual ("budget"), ni las inversiones ("investments").
			 * Tampoco vamos a especificar si la investigación ha sido cancelada o no ("isCancelled"). Los valores de todos estos atributos se asignan automáticamente
			 * al objeto en el método create() y sabemos que a la hora de crear una nueva investigación siempre se invoca primero al método create() y después al save(Research research). Es reduntante
			 * hacer pruebas sobre el método create() ya que dicho método simplemente crea una instancia de Research y asigna valor nulo a los atributos "startDate" y "endDate", 0 a "budget", false a "isCancelled" y una
			 * colección vacía al atributo "investments").
			 */
			{
				"expert2", "Research Title", "Research Subject", "Research Description", "http://www.project1.es", 3000., new LinkedList<Expert>(Arrays.asList(this.expertService.findOne(super.getEntityId("expert1")))), null
			// TEST DE CASO POSITIVO: se respetan todas las susodichas reglas de negocio 
			}, {
				"expert2", "Research Title", "Research Subject", "Research Description", "http://www.project1.es", 3000., new LinkedList<Expert>(Arrays.asList(this.expertService.findOne(super.getEntityId("expert2")))), IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el experto principal intenta añadirse a sí mismo como compañero de trabajo
			}, {
				"expert2", "Research Title", "Research Subject", "Research Description", "Página web", 3000., new LinkedList<Expert>(Arrays.asList(this.expertService.findOne(super.getEntityId("expert1")))), ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: la URL no es válida
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Double) testingData[i][5], (List<Expert>) testingData[i][6],
					(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como EXPERT debe poder crear una nueva investigación
	 * Probamos los métodos create() y save(Research research) de la clase ResearchService mediante la siguiente plantilla:
	 */
	protected void template(final String username, final String title, final String subject, final String description, final String projectWebpage, final double minCost, final List<Expert> team, final Class<?> expected) {
		Class<?> caught;
		Research research;

		caught = null;

		try {
			super.authenticate(username);
			research = this.researchService.create();

			research.setTitle(title);
			research.setSubject(subject);
			research.setDescription(description);
			research.setProjectWebpage(projectWebpage);
			research.setMinCost(minCost);
			research.setTeam(team);

			this.researchService.save(research);
			super.flushTransaction();
			super.unauthenticate();
			/*
			 * La comprobación del método save(Research research) termina aquí. Si no ha saltado ninguna
			 * excepción, se ha creado una nueva investigacion con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
