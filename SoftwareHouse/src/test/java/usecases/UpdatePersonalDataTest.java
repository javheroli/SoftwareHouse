
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

import services.AdministratorService;
import services.ApplicationService;
import services.ApprenticeService;
import services.ContestService;
import services.DisciplineService;
import services.ExpertService;
import services.InvestmentService;
import services.InvestorService;
import services.ManagerService;
import services.PostService;
import services.ResearchService;
import services.ThreadService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Application;
import domain.Apprentice;
import domain.Contest;
import domain.Discipline;
import domain.Expert;
import domain.Investment;
import domain.Investor;
import domain.Manager;
import domain.Post;
import domain.Research;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UpdatePersonalDataTest extends AbstractTest {

	// Systems under test

	@Autowired
	private ApprenticeService		apprenticeService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExpertService			expertService;

	@Autowired
	private InvestorService			investorService;

	@Autowired
	private AdministratorService	administratorService;

	// Supporting services

	@Autowired
	private ThreadService			threadService;

	@Autowired
	private PostService				postService;

	@Autowired
	private ContestService			contestService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private ResearchService			researchService;

	@Autowired
	private InvestmentService		investmentService;

	@Autowired
	private DisciplineService		disciplineService;


	// Tests

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {

		final Object testingData[][] = {
			{
				/*
				 * Para probar el método save(Apprentice apprentice) de ApprenticeService cuando el objeto pasado como
				 * parámetro de entrada es un aprendiz instrumentado que ya existe en el sistema, vamos a usar los siguientes datos:
				 * (Especificamos el username del aprendiz principal, el aprendiz que queremos editar y los nuevos valores de sus atributos.
				 * En concreto: nuevo nombre ("name"), nuevos apellidos ("surname"), nueva dirección ("postalAddress") (es opcional),
				 * nuevo número de teléfono ("phone") (es opcional), nuevo correo electrónico ("email"), nueva foto de perfil ("linkPicture") (es opcional),
				 * nueva colección de temas publicados ("threads"), nueva colección de respuestas escritas ("posts"), nueva colección de solicitudes creadas ("applications"),
				 * nuevos puntos ("points").
				 * 
				 * 
				 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Apprentice, en realidad,
				 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
				 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
				 * save(Apprentice apprentice)), O BIEN porque el valor del atributo se actualiza automáticamente.
				 * 
				 * Hablando concretamente de la entidad Apprentice:
				 * 
				 * 
				 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
				 * 
				 * 
				 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Apprentice apprentice):
				 * 
				 * 1) threads;
				 * 2) posts;
				 * 3) applications;
				 * 
				 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
				 * 
				 * 1) points;
				 * 
				 * 
				 * _________________________________________________________________________________________________________
				 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
				 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los aprendices no puedan cambiar
				 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
				 */

				"apprentice3", "apprentice3", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread3")))),
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post3")), this.postService.findOne(super.getEntityId("post7")), this.postService.findOne(super.getEntityId("post9")))),
				new LinkedList<Application>(Arrays.asList(this.applicationService.findOne(super.getEntityId("application3")))), 15, null

			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"apprentice3", "apprentice3", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread3")))),
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post3")), this.postService.findOne(super.getEntityId("post7")), this.postService.findOne(super.getEntityId("post9")))),
				new LinkedList<Application>(Arrays.asList(this.applicationService.findOne(super.getEntityId("application3")))), 25, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta cambiar los puntos del aprendiz
			},
			{
				"apprentice2", "apprentice3", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread3")))),
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post3")), this.postService.findOne(super.getEntityId("post7")), this.postService.findOne(super.getEntityId("post9")))),
				new LinkedList<Application>(Arrays.asList(this.applicationService.findOne(super.getEntityId("application3")))), 15, IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: apprentice2 intenta modificar los datos de apprentice3
			},

			/*
			 * Para probar el método save(Manager manager) de ManagerService cuando el objeto pasado como
			 * parámetro de entrada es un organizador instrumentado que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del organizador principal, el organizador que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo nombre ("name"), nuevos apellidos ("surname"), nueva dirección ("postalAddress") (es opcional),
			 * nuevo número de teléfono ("phone") (es opcional), nuevo correo electrónico ("email"), nueva foto de perfil ("linkPicture") (es opcional),
			 * nueva colección de temas publicados ("threads"), nueva colección de respuestas escritas ("posts"), nueva colección de concursos organizados ("contests").
			 * 
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Manager, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(Manager manager)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad Manager:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Manager manager):
			 * 
			 * 1) threads;
			 * 2) posts;
			 * 3) contests;
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los organizadores no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
			 */
			{
				"manager2", "manager2", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(), new LinkedList<Post>(),
				new LinkedList<Contest>(Arrays.asList(this.contestService.findOne(super.getEntityId("contest2")))), null

			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"manager2", "manager2", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(), new LinkedList<Post>(), new LinkedList<Contest>(), IllegalArgumentException.class

			// TEST DE CASO NEGATIVO: se intenta borrar los concursos del organizador
			},
			{
				"manager2", "manager2", "New name", "New Surname", "new Postal Address", "(912)730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(), new LinkedList<Post>(),
				new LinkedList<Contest>(Arrays.asList(this.contestService.findOne(super.getEntityId("contest2")))), ConstraintViolationException.class

			// TEST DE CASO NEGATIVO: el número de teléfono no es válido
			},

			/*
			 * Para probar el método save(Expert expert) de ExpertService cuando el objeto pasado como
			 * parámetro de entrada es un experto instrumentado que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del experto principal, el experto que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo nombre ("name"), nuevos apellidos ("surname"), nueva dirección ("postalAddress") (es opcional),
			 * nuevo número de teléfono ("phone") (es opcional), nuevo correo electrónico ("email"), nueva foto de perfil ("linkPicture") (es opcional),
			 * nueva colección de temas publicados ("threads"), nueva colección de respuestas escritas ("posts"), nueva colección de disciplinas ("disciplines"),
			 * nueva colección de concursos creados ("contestsForEdition"), nueva colección de concursos corregidos ("contestsForEvaluation"), nueva colección de investigaciones ("researches").
			 * 
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Expert, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(Expert expert)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad Expert:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 
			 * 1) disciplines
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Expert expert):
			 * 
			 * 1) threads;
			 * 2) posts;
			 * 3) contestsForEvaluation;
			 * 4) contestsForEdition;
			 * 5) researches;
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los expertos no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
			 */
			{
				"expert4",
				"expert4",
				"New name",
				"New Surname",
				"new Postal Address",
				"912730452",
				"asen_ran@mail.bg",
				null,
				new LinkedList<domain.Thread>(),
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post30")))),
				new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline1")), this.disciplineService.findOne(super.getEntityId("discipline6")), this.disciplineService.findOne(super.getEntityId("discipline9")),
					this.disciplineService.findOne(super.getEntityId("discipline13")), this.disciplineService.findOne(super.getEntityId("discipline14")))), new LinkedList<Contest>(), new LinkedList<Contest>(),
				new LinkedList<Research>(Arrays.asList(this.researchService.findOne(super.getEntityId("research1")), this.researchService.findOne(super.getEntityId("research2")), this.researchService.findOne(super.getEntityId("research3")))), null

			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"expert4",
				"expert4",
				"New name",
				"New Surname",
				"new Postal Address",
				"912730452",
				"asen_ran@mail.bg",
				null,
				new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread1")))),
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post30")))),
				new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline1")), this.disciplineService.findOne(super.getEntityId("discipline6")), this.disciplineService.findOne(super.getEntityId("discipline9")),
					this.disciplineService.findOne(super.getEntityId("discipline13")), this.disciplineService.findOne(super.getEntityId("discipline14")))), new LinkedList<Contest>(), new LinkedList<Contest>(),
				new LinkedList<Research>(Arrays.asList(this.researchService.findOne(super.getEntityId("research1")), this.researchService.findOne(super.getEntityId("research2")), this.researchService.findOne(super.getEntityId("research3")))),
				IllegalArgumentException.class

			// TEST DE CASO NEGATIVO: se intenta añadir un tema a la lista de temas publicados
			},
			{
				"expert4",
				"expert4",
				"New name",
				"New Surname",
				"new Postal Address",
				"912730452",
				"new mail",
				null,
				new LinkedList<domain.Thread>(),
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post30")))),
				new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline1")), this.disciplineService.findOne(super.getEntityId("discipline6")), this.disciplineService.findOne(super.getEntityId("discipline9")),
					this.disciplineService.findOne(super.getEntityId("discipline13")), this.disciplineService.findOne(super.getEntityId("discipline14")))), new LinkedList<Contest>(), new LinkedList<Contest>(),
				new LinkedList<Research>(Arrays.asList(this.researchService.findOne(super.getEntityId("research1")), this.researchService.findOne(super.getEntityId("research2")), this.researchService.findOne(super.getEntityId("research3")))),
				ConstraintViolationException.class

			// TEST DE CASO NEGATIVO: el correo electrónico no es válido
			},

			/*
			 * Para probar el método save(Investor investor) de InvestorService cuando el objeto pasado como
			 * parámetro de entrada es un inversor instrumentado que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del inversor principal, el inversor que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo nombre ("name"), nuevos apellidos ("surname"), nueva dirección ("postalAddress") (es opcional),
			 * nuevo número de teléfono ("phone") (es opcional), nuevo correo electrónico ("email"), nueva foto de perfil ("linkPicture") (es opcional),
			 * nueva colección de temas publicados ("threads"), nueva colección de respuestas escritas ("posts"), nueva colección de inversiones realizadas ("investments").
			 * 
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Investor, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(Investor investor)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad Investor:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Investor investor):
			 * 
			 * 1) threads;
			 * 2) posts;
			 * 3) investments;
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los inversores no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
			 */
			{
				"investor2", "investor2", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread10")))),
				new LinkedList<Post>(), new LinkedList<Investment>(Arrays.asList(this.investmentService.findOne(super.getEntityId("investment4")))), null

			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},

			{
				"investor2", "investor2", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread10")))),
				new LinkedList<Post>(), new LinkedList<Investment>(Arrays.asList(this.investmentService.findOne(super.getEntityId("investment2")))), IllegalArgumentException.class

			// TEST DE CASO NEGATIVO: se intenta modificar la lista de inversiones realizadas
			},

			{
				"investor1", "investor2", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(Arrays.asList(this.threadService.findOne(super.getEntityId("thread10")))),
				new LinkedList<Post>(), new LinkedList<Investment>(Arrays.asList(this.investmentService.findOne(super.getEntityId("investment4")))), IllegalArgumentException.class

			// TEST DE CASO NEGATIVO: investor1 intenta modificar los datos de investor2
			},

			/*
			 * Para probar el método save(Administrator administrator) de AdministratorService cuando el objeto pasado como
			 * parámetro de entrada es un administrador instrumentado que ya existe en el sistema, vamos a usar los siguientes datos:
			 * (Especificamos el username del administrador principal, el administrador que queremos editar y los nuevos valores de sus atributos.
			 * En concreto: nuevo nombre ("name"), nuevos apellidos ("surname"), nueva dirección ("postalAddress") (es opcional),
			 * nuevo número de teléfono ("phone") (es opcional), nuevo correo electrónico ("email"), nueva foto de perfil ("linkPicture") (es opcional),
			 * nueva colección de temas publicados ("threads"), nueva colección de respuestas escritas ("posts").
			 * 
			 * 
			 * ¡¡¡OJO!!! Aunque en la plantilla usada proporcionamos la libertad de especificar los valores de todos los atributos de Administrator, en realidad,
			 * esto no se puede hacer. Algunos de los atributos NO se deben poder especificar libremente BIEN porque se debe guardar siempre el valor original
			 * asignado al atributo en el momento de su creación, O BIEN porque el valor del atributo se debe modificar a través de otro método (no el
			 * save(Administrator administrator)), O BIEN porque el valor del atributo se actualiza automáticamente.
			 * 
			 * Hablando concretamente de la entidad Administrator:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque se debe guardar siempre el valor original asignado en el momento de su creación:
			 * 
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se debe modificar a través de otro método (no el save(Administrator administrator):
			 * 
			 * 1) threads;
			 * 2) posts;
			 * 
			 * Atributos que NO se deben poder especificar libremente porque su valor se actualiza automáticamente:
			 * 
			 * 
			 * 
			 * _________________________________________________________________________________________________________
			 * Para todos estos atributos el valor especificado debe coincidir con el valor del objeto intrumentado (guardado en la base de datos)
			 * De todas formas, nosotros hemos implementado las vistas y los controladores de manera que los administradores no puedan cambiar
			 * los susodichos atributos en los formularios de edición (ni siquiera recurriendo a POST hacking).
			 */
			{
				"admin", "administrator", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(), new LinkedList<Post>(), null

			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},

			{
				"manager2", "administrator", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(), new LinkedList<Post>(), IllegalArgumentException.class

			// TEST DE CASO NEGATIVO: manager2 intenta modificar los datos del administrador
			},

			{
				"admin", "administrator", "New name", "New Surname", "new Postal Address", "912730452", "asen_ran@mail.bg", "http://foto1.es", new LinkedList<domain.Thread>(),
				new LinkedList<Post>(Arrays.asList(this.postService.findOne(super.getEntityId("post14")))), IllegalArgumentException.class

			// TEST DE CASO NEGATIVO: se intenta añadir una respuesta a la lista de respuestas del administrador
			}

		};

		for (int i = 0; i < 3; i++)
			try {
				super.startTransaction();
				this.template1((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (List<domain.Thread>) testingData[i][8], (List<Post>) testingData[i][9], (List<Application>) testingData[i][10], (int) testingData[i][11], (Class<?>) testingData[i][12]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 3; i < 6; i++)
			try {
				super.startTransaction();
				this.template2((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (List<domain.Thread>) testingData[i][8], (List<Post>) testingData[i][9], (List<Contest>) testingData[i][10], (Class<?>) testingData[i][11]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 6; i < 9; i++)
			try {
				super.startTransaction();
				this.template3((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (List<domain.Thread>) testingData[i][8], (List<Post>) testingData[i][9], (List<Discipline>) testingData[i][10], (List<Contest>) testingData[i][11], (List<Contest>) testingData[i][12],
					(List<Research>) testingData[i][13], (Class<?>) testingData[i][14]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 9; i < 12; i++)
			try {
				super.startTransaction();
				this.template4((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (List<domain.Thread>) testingData[i][8], (List<Post>) testingData[i][9], (List<Investment>) testingData[i][10], (Class<?>) testingData[i][11]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 12; i < 15; i++)
			try {
				super.startTransaction();
				this.template5((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (List<domain.Thread>) testingData[i][8], (List<Post>) testingData[i][9], (Class<?>) testingData[i][10]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como APPRENTICE debe poder modificar sus datos personales
	 */
	protected void template1(final String principalUsername, final int apprenticeId, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final List<domain.Thread> threads,
		final List<Post> posts, final List<Application> applications, final int points, final Class<?> expected) {
		Class<?> caught;
		Apprentice apprentice, instrumented;

		caught = null;

		try {

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocando a los setters,
			 * se van a realizar llamadas implícitas al método save de ApprenticeRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de ApprenticeRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			instrumented = this.apprenticeService.findOne(apprenticeId);

			apprentice = new Apprentice();

			apprentice.setId(instrumented.getId());
			apprentice.setVersion(instrumented.getVersion());
			apprentice.setUserAccount(instrumented.getUserAccount());

			super.authenticate(principalUsername);

			apprentice.setName(name);
			apprentice.setSurname(surname);
			apprentice.setPostalAddress(postalAddress);
			apprentice.setPhone(phone);
			apprentice.setEmail(email);
			apprentice.setLinkPhoto(linkPhoto);
			apprentice.setThreads(threads);
			apprentice.setPosts(posts);
			apprentice.setApplications(applications);
			apprentice.setPoints(points);

			this.apprenticeService.save(apprentice);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el aprendiz se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como MANAGER debe poder modificar sus datos personales
	 */
	protected void template2(final String principalUsername, final int managerId, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final List<domain.Thread> threads,
		final List<Post> posts, final List<Contest> contests, final Class<?> expected) {
		Class<?> caught;
		Manager manager, instrumented;

		caught = null;

		try {

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocando a los setters,
			 * se van a realizar llamadas implícitas al método save de ManagerRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de ManagerRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			instrumented = this.managerService.findOne(managerId);

			manager = new Manager();

			manager.setId(instrumented.getId());
			manager.setVersion(instrumented.getVersion());
			manager.setUserAccount(instrumented.getUserAccount());

			super.authenticate(principalUsername);

			manager.setName(name);
			manager.setSurname(surname);
			manager.setPostalAddress(postalAddress);
			manager.setPhone(phone);
			manager.setEmail(email);
			manager.setLinkPhoto(linkPhoto);
			manager.setThreads(threads);
			manager.setPosts(posts);
			manager.setContests(contests);

			this.managerService.save(manager);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el organizador se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como EXPERT debe poder modificar sus datos personales
	 */
	protected void template3(final String principalUsername, final int expertId, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final List<domain.Thread> threads,
		final List<Post> posts, final List<Discipline> disciplines, final List<Contest> contestsForEdition, final List<Contest> contestsForEvaluation, final List<Research> researches, final Class<?> expected) {
		Class<?> caught;
		Expert expert, instrumented;

		caught = null;

		try {

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocando a los setters,
			 * se van a realizar llamadas implícitas al método save de ExpertRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de ExpertRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			instrumented = this.expertService.findOne(expertId);

			expert = new Expert();

			expert.setId(instrumented.getId());
			expert.setVersion(instrumented.getVersion());
			expert.setUserAccount(instrumented.getUserAccount());

			super.authenticate(principalUsername);

			expert.setName(name);
			expert.setSurname(surname);
			expert.setPostalAddress(postalAddress);
			expert.setPhone(phone);
			expert.setEmail(email);
			expert.setLinkPhoto(linkPhoto);
			expert.setThreads(threads);
			expert.setPosts(posts);
			expert.setDisciplines(disciplines);
			expert.setContestsForEdition(contestsForEdition);
			expert.setContestsForEvaluation(contestsForEvaluation);
			expert.setResearches(researches);

			this.expertService.save(expert);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el experto se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como INVESTOR debe poder modificar sus datos personales
	 */
	protected void template4(final String principalUsername, final int investorId, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final List<domain.Thread> threads,
		final List<Post> posts, final List<Investment> investments, final Class<?> expected) {
		Class<?> caught;
		Investor investor, instrumented;

		caught = null;

		try {

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocando a los setters,
			 * se van a realizar llamadas implícitas al método save de InvestorRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de InvestorRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			instrumented = this.investorService.findOne(investorId);

			investor = new Investor();

			investor.setId(instrumented.getId());
			investor.setVersion(instrumented.getVersion());
			investor.setUserAccount(instrumented.getUserAccount());

			super.authenticate(principalUsername);

			investor.setName(name);
			investor.setSurname(surname);
			investor.setPostalAddress(postalAddress);
			investor.setPhone(phone);
			investor.setEmail(email);
			investor.setLinkPhoto(linkPhoto);
			investor.setThreads(threads);
			investor.setPosts(posts);
			investor.setInvestments(investments);

			this.investorService.save(investor);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el inversor se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder modificar sus datos personales
	 */
	protected void template5(final String principalUsername, final int adminId, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final List<domain.Thread> threads,
		final List<Post> posts, final Class<?> expected) {
		Class<?> caught;
		Administrator administrator, instrumented;

		caught = null;

		try {

			/*
			 * Si hacemos las modificaciones directamente sobre el objeto instrumentado invocando a los setters,
			 * se van a realizar llamadas implícitas al método save de AdministratorRepository (este comportamiento de
			 * Spring lo hemos explicado en el documento "Estudio realizado sobre el uso de setters en los servicios"
			 * entregado en D08-Lessons learnt). Nosotros no queremos que pase esto porque el método save de AdministratorRepository
			 * no comprueba si se cumplen las reglas de negocio. Por lo tanto, vamos a crear una copia del objeto instrumentado.
			 */

			instrumented = this.administratorService.findOne(adminId);

			administrator = new Administrator();

			administrator.setId(instrumented.getId());
			administrator.setVersion(instrumented.getVersion());
			administrator.setUserAccount(instrumented.getUserAccount());

			super.authenticate(principalUsername);

			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setPostalAddress(postalAddress);
			administrator.setPhone(phone);
			administrator.setEmail(email);
			administrator.setLinkPhoto(linkPhoto);
			administrator.setThreads(threads);
			administrator.setPosts(posts);

			this.administratorService.save(administrator);
			super.unauthenticate();
			super.flushTransaction();

			// Si no ha saltado ninguna excepción, el administrador se ha actualizado correctamente

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
