
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
import services.ApplicationService;
import services.ContestService;
import services.ForumService;
import services.PostService;
import services.ResearchService;
import services.ThreadService;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DashboardTest extends AbstractTest {

	// System under test

	// Supporting services

	@Autowired
	ForumService		forumService;

	@Autowired
	ThreadService		threadService;

	@Autowired
	ContestService		contestService;

	@Autowired
	ResearchService		researchService;

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	PostService			postService;

	@Autowired
	ActorService		actorService;


	// Tests

	@Test
	public void driver() {

		final Object testingData[][] = {

			{
				"admin", null
			// TEST DE CASO POSITIVO: el usuario principal es administrador del sistema
			}, {
				null, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: usuario no autentificado
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/*
	 * REQUISITO FUNCIONAL: el(/los) administrador(es) del sistema deben poder consultar un dashboard en el que se muestren distintas métricas
	 * y estadísticas sobre el sistema
	 * Vamos a usar la siguiente plantilla para probar que los métodos que devuelven dichas métricas y estadísticas funcionan bien.
	 * De nuevo, para demostrar la veracidad de los resultados devueltos vamos a usar oráculo. En nuestro caso, el oráculo sería simplemente
	 * calcular a mano el resultado esperado partiendo de los objetos que tenemos en la base de datos. Tenemos que comprobar que el resultado
	 * esperado coincide con el resultado devuelto.
	 */

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;

		Double expectedResultDouble;
		final Double getRatioAcceptedApplications, getRatioDueApplications, getRatioPendingApplications, getRatioRejectedApplications, getRatioResearchesNotStarted;
		final Double getRatioDeletedPosts;
		Double getAvgThreadsPerForum, getMinThreadsPerForum, getMaxThreadsPerForum, getStdDeviationThreadsPerForum;
		Double getAvgPostsPerThread, getMinPostsPerThread, getMaxPostsPerThread, getStdDeviationPostsPerThread;
		Double getAvgApplicationsPerContest, getMinApplicationsPerContest, getMaxApplicationsPerContest, getStdDeviationApplicationsPerContest;
		Double getAvgProblemsPerContest;
		final Double getMinProblemsPerContest, getMaxProblemsPerContest, getStdDeviationProblemsPerContest;
		final Double getAvgInvestmentsPerResearch, getMinInvestmentsPerResearch, getMaxInvestmentsPerResearch, getStdDeviationInvestmentsPerResearch;
		final Double lowestMark, averageMark, highestMark;

		Collection<Actor> findActorsWith10PercentMorePostsThanAverage;

		caught = null;

		try {
			super.authenticate(username);

			//Oráculo: la media de temas por foro es
			expectedResultDouble = 1.0;
			getAvgThreadsPerForum = this.forumService.getAvgThreadsPerForum();
			Assert.isTrue(getAvgThreadsPerForum.equals(expectedResultDouble));

			//Oráculo: el mínimo de temas por foro es
			expectedResultDouble = 0.0;
			getMinThreadsPerForum = this.forumService.getMinThreadsPerForum();
			Assert.isTrue(getMinThreadsPerForum.equals(expectedResultDouble));

			//Oráculo: el máximo de temas por foro es
			expectedResultDouble = 4.0;
			getMaxThreadsPerForum = this.forumService.getMaxThreadsPerForum();
			Assert.isTrue(getMaxThreadsPerForum.equals(expectedResultDouble));

			//Oráculo: la desviación típica de temas por foro es
			expectedResultDouble = 1.1832159566199232;
			getStdDeviationThreadsPerForum = this.forumService.getStdDeviationThreadsPerForum();
			Assert.isTrue(getStdDeviationThreadsPerForum.equals(expectedResultDouble));

			//Oráculo: la media de respuestas por tema es 
			expectedResultDouble = 3.3;
			getAvgPostsPerThread = this.threadService.getAvgPostsPerThread();

			Assert.isTrue(getAvgPostsPerThread.equals(expectedResultDouble));

			//Oráculo: el mínimo de respuestas por tema es
			expectedResultDouble = 0.0;
			getMinPostsPerThread = this.threadService.getMinPostsPerThread();

			Assert.isTrue(getMinPostsPerThread.equals(expectedResultDouble));

			//Oráculo: el máximo de respuestas por tema es
			expectedResultDouble = 12.0;
			getMaxPostsPerThread = this.threadService.getMaxPostsPerThread();

			Assert.isTrue(getMaxPostsPerThread.equals(expectedResultDouble));

			//Oráculo: la desviación típica de respuestas por tema es
			expectedResultDouble = 6.584831053261731;
			getStdDeviationPostsPerThread = this.threadService.getStdDeviationPostsPerThread();

			Assert.isTrue(getStdDeviationPostsPerThread.equals(expectedResultDouble));

			//Oráculo: la media de solicitudes por concurso es
			expectedResultDouble = 2.5;
			getAvgApplicationsPerContest = this.contestService.getAvgApplicationsPerContest();

			Assert.isTrue(getAvgApplicationsPerContest.equals(expectedResultDouble));

			//Oráculo: el mínimo de solicitudes por concurso es
			expectedResultDouble = 0.0;
			getMinApplicationsPerContest = this.contestService.getMinApplicationsPerContest();

			Assert.isTrue(getMinApplicationsPerContest.equals(expectedResultDouble));

			//Oráculo: el máximo de solicitudes por concurso es
			expectedResultDouble = 4.0;
			getMaxApplicationsPerContest = this.contestService.getMaxApplicationsPerContest();

			Assert.isTrue(getMaxApplicationsPerContest.equals(expectedResultDouble));

			//Oráculo: la desviación típica de solicitudes por concurso es
			expectedResultDouble = 1.5;
			getStdDeviationApplicationsPerContest = this.contestService.getStdDeviationApplicationsPerContest();

			Assert.isTrue(getStdDeviationApplicationsPerContest.equals(expectedResultDouble));

			//Oráculo: la media de problemas por concurso es
			expectedResultDouble = 2.5;
			getAvgProblemsPerContest = this.contestService.getAvgProblemsPerContest();

			Assert.isTrue(getAvgProblemsPerContest.equals(expectedResultDouble));

			//Oráculo: el mínimo de problemas por concurso es
			expectedResultDouble = 1.0;
			getMinProblemsPerContest = this.contestService.getMinProblemsPerContest();

			Assert.isTrue(getMinProblemsPerContest.equals(expectedResultDouble));

			//Oráculo: el máximo de problemas por concurso es
			expectedResultDouble = 4.0;
			getMaxProblemsPerContest = this.contestService.getMaxProblemsPerContest();

			Assert.isTrue(getMaxProblemsPerContest.equals(expectedResultDouble));

			//Oráculo: la desviación típica de problemas por concurso es
			expectedResultDouble = 1.118033988749895;
			getStdDeviationProblemsPerContest = this.contestService.getStdDeviationProblemsPerContest();

			Assert.isTrue(getStdDeviationProblemsPerContest.equals(expectedResultDouble));

			//Oráculo: la media de inversiones por investigación es
			expectedResultDouble = 1.75;
			getAvgInvestmentsPerResearch = this.researchService.getAvgInvestmentsPerResearch();

			Assert.isTrue(getAvgInvestmentsPerResearch.equals(expectedResultDouble));

			//Oráculo: el mínimo de inversiones por investigación es
			expectedResultDouble = 0.0;
			getMinInvestmentsPerResearch = this.researchService.getMinInvestmentsPerResearch();

			Assert.isTrue(getMinInvestmentsPerResearch.equals(expectedResultDouble));

			//Oráculo: el máximo de inversiones por investigación es
			expectedResultDouble = 3.0;
			getMaxInvestmentsPerResearch = this.researchService.getMaxInvestmentsPerResearch();

			Assert.isTrue(getMaxInvestmentsPerResearch.equals(expectedResultDouble));

			//Oráculo: la desviación típica de inversiones por investigación es
			expectedResultDouble = 1.0897247358851685;
			getStdDeviationInvestmentsPerResearch = this.researchService.getStdDeviationInvestmentsPerResearch();

			Assert.isTrue(getStdDeviationInvestmentsPerResearch.equals(expectedResultDouble));

			//Oráculo: el ratio de solicitudes con estado "ACCEPTED" es
			expectedResultDouble = 0.6;
			getRatioAcceptedApplications = this.applicationService.getRatioAcceptedApplications();

			Assert.isTrue(getRatioAcceptedApplications.equals(expectedResultDouble));

			//Oráculo: el ratio de solicitudes con estado "PENDING" es
			expectedResultDouble = 0.1;
			getRatioPendingApplications = this.applicationService.getRatioPendingApplications();

			Assert.isTrue(getRatioPendingApplications.equals(expectedResultDouble));

			//Oráculo: el ratio de solicitudes con estado "DUE" es
			expectedResultDouble = 0.2;
			getRatioDueApplications = this.applicationService.getRatioDueApplications();

			Assert.isTrue(getRatioDueApplications.equals(expectedResultDouble));

			//Oráculo: el ratio de solicitudes con estado "CANCELLED" es
			expectedResultDouble = 0.1;
			getRatioRejectedApplications = this.applicationService.getRatioRejectedApplications();

			Assert.isTrue(getRatioRejectedApplications.equals(expectedResultDouble));

			//Oráculo: el ratio de investigaciones que no han empezado todavía es
			expectedResultDouble = 0.25;
			getRatioResearchesNotStarted = this.researchService.getRatioResearchesNotStarted();
			Assert.isTrue(getRatioResearchesNotStarted.equals(expectedResultDouble));

			//Oráculo: el ratio de respuestas borradas es
			expectedResultDouble = 0.02941;
			getRatioDeletedPosts = this.postService.getRatioDeletedPosts();
			Assert.isTrue(getRatioDeletedPosts.equals(expectedResultDouble));

			// Listado de actores que han publicado un 10% más respuestas que la media
			//Oráculo: hay 6 actores que han publicado un 10% más respuestas que la media
			// Uno de ellos es expert6.
			// expert5 no es uno de ellos

			findActorsWith10PercentMorePostsThanAverage = this.actorService.findActorsWith10PercentMorePostsThanAverage();
			Assert.isTrue(findActorsWith10PercentMorePostsThanAverage.size() == 6);
			Assert.isTrue(findActorsWith10PercentMorePostsThanAverage.contains(this.actorService.findOne(super.getEntityId("expert6"))));
			Assert.isTrue(!findActorsWith10PercentMorePostsThanAverage.contains(this.actorService.findOne(super.getEntityId("expert5"))));

			// Oráculo:
			// La nota más alta de contest1 es: 
			expectedResultDouble = 8.25;
			highestMark = this.contestService.getAvgMinMaxMarksForEvaluatedContests().get(2).get(0);
			Assert.isTrue(highestMark.equals(expectedResultDouble));
			// La nota media de contest1 es: 
			expectedResultDouble = 5.166666666666667;
			averageMark = this.contestService.getAvgMinMaxMarksForEvaluatedContests().get(0).get(0);
			Assert.isTrue(averageMark.equals(expectedResultDouble));
			// La nota más baja de contest1 es: 
			expectedResultDouble = 3.25;
			lowestMark = this.contestService.getAvgMinMaxMarksForEvaluatedContests().get(1).get(0);
			Assert.isTrue(lowestMark.equals(expectedResultDouble));
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
