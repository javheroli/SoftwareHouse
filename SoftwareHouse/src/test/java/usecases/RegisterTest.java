
package usecases;

import java.util.ArrayList;
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

import security.Authority;
import security.UserAccount;
import services.AdministratorService;
import services.ApprenticeService;
import services.DisciplineService;
import services.ExpertService;
import services.InvestorService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Apprentice;
import domain.Discipline;
import domain.Expert;
import domain.Investor;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RegisterTest extends AbstractTest {

	// Systems under test

	@Autowired
	private ApprenticeService		apprenticeService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExpertService			expertService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private InvestorService			investorService;

	@Autowired
	private DisciplineService		disciplineService;


	// Supporting services

	// Tests

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final Object testingData[][] = {
			/*
			 * Con estos datos probamos el método save(Apprentice apprentice) de ApprenticeService:
			 * (Especificamos el nombre, los apellidos, la dirección (es opcional), el número de teléfono (es opcional), el correo
			 * electrónico, la foto de perfil (es opcional), el nombre de usuario, la contraseña y el rol del nuevo aprendiz que queremos crear.
			 * No vamos a especificar las solicitudes creadas por el aprendiz ("applications"), ni el número de puntos del aprendiz ("points"), ni los temas que el aprendiz
			 * ha publicado ("threads"), ni las respuestas que ha escrito ("posts") puesto que los valores de dichos atributos se asignan automáticamente en el método create()
			 * y sabemos que a la hora de crear un nuevo aprendiz siempre se invoca primero al método create() y después al save(Apprentice apprentice). Es reduntante hacer pruebas
			 * sobre el método create() ya que dicho método simplemente crea una instancia de Apprentice y le asigna colecciones vacías a los atributos "applications", "threads" y "posts",
			 * y le asigna el número 0 al atributo "points".
			 */
			{
				"Asen", "Rangelov Baykushev", "C/ Guadalimar 11, Sevilla", "912730456", "asen_ran@mail.bg", "http://foto1.es", "aseranbay", "aseranbay", "APPRENTICE", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"Javier", "Herraiz Olivas", null, null, "javheroli@gmail.es", null, "javheroli", "javheroli", "APPRENTICE", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"Luis", "Castillo Diez", null, null, "luis@gmail.es", "http://foto1.es", "luis7", "luis7", "EXPERT", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo aprendiz con el rol "EXPERT"
			},
			{
				"Víctor", "Giménez Fuentes", null, null, null, null, "victor", "victor", "APPRENTICE", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo aprendiz sin correo electrónico
			},
			{
				"Mario", "Lozano Rojas", null, null, "mario@gmail.es", null, "1234", "1234", "APPRENTICE", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: el nombre de usuario y la contraseña deben contener al menos 5 caracteres
			},
			{
				"Rubén", null, null, null, "ruben@gmail.es", null, "ruben", "ruben", "APPRENTICE", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo aprendiz sin apellidos
			},

			/*
			 * Con estos datos probamos el método save(Manager manager) de ManagerService:
			 * (Especificamos el username del ADMIN principal y el nombre, los apellidos, la dirección (es opcional), el número de teléfono (es opcional), el correo
			 * electrónico, la foto de perfil (es opcional), el nombre de usuario, la contraseña y el rol del nuevo organizador
			 * que queremos crear. No vamos a especificar los concursos que ha organizado ("contests"), ni los temas que ha publicado ("threads"),
			 * ni las respuestas que ha escrito ("posts"), puesto que los valores de dichos atributos se asignas automáticamente en el método create() y sabemos
			 * que a la hora de crear un nuevo organizador siempre se invoca primero al método create() y después al save(Manager manager). Es reduntante hacer pruebas
			 * sobre el método create() ya que dicho método simplemente crea una instancia de Manager y le asigna colecciones vacías a los atributos "contests", "threads" y "posts".
			 */

			{
				"admin", "Asen", "Rangelov Baykushev", "C/ Guadalimar 11, Sevilla", "912730456", "asen_ran@mail.bg", null, "aseranbay", "aseranbay", "MANAGER", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"admin", "Javier", "Herraiz Olivas", null, null, "javheroli@gmail.es", "http://www.foto1.es", "javheroli", "javheroli", "MANAGER", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"manager1", "Luis", "Castillo Diez", null, null, "luis@gmail.es", null, "luis7", "luis7", "MANAGER", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el usuario principal es un organizador
			},
			{
				"admin", "Víctor", "Giménez Fuentes", null, null, "luis@gmail.es", "foto1", "victor", "victor", "MANAGER", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo organizador con enlace a foto inválido
			},
			{
				"admin", "Mario", "Lozano Rojas", null, "898656754+", "mario@gmail.es", null, "1234", "1234", "MANAGER", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo organizador con número de teléfono inválido
			},

			/*
			 * Con estos datos probamos el método save(Expert expert) de ExpertService:
			 * (Especificamos el username del ADMIN principal y el nombre, los apellidos, la dirección (es opcional), el número de teléfono (es opcional), el correo
			 * electrónico, la foto de perfil (es opcional), las disciplinas que domina, el nombre de usuario, la contraseña y el rol del nuevo experto
			 * que queremos crear. No vamos a especificar los concursos que ha creado ("contestsForEdition"), ni los concursos que ha corregido ("contestsForEvaluation"),
			 * ni las investigaciones que ha llevado a cabo ("researches"), ni los temas que ha publicado ("threads"), ni las respuestas que ha escrito ("posts") puesto que
			 * los valores de dichos atributos se asignan automáticamente en el método create() y sabemos que a la hora de crear un nuevo experto siempre se invoca primero al
			 * método create() y después al save(Expert expert). Es reduntante hacer pruebas sobre el método create() ya que dicho método simplemente crea una instancia de Expert
			 * y le asigna colecciones vacías a los atributos "contestsForEdition", "contestsForEvaluation", "researches", "threads" y "posts".
			 */

			{
				"admin", "Asen", "Rangelov Baykushev", "C/ Guadalimar 11, Sevilla", "912730456", "asen_ran@mail.bg", null,
				new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline1")), this.disciplineService.findOne(super.getEntityId("discipline2")))), "aseranbay", "aseranbay", "EXPERT", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			},
			{
				"admin", "Luis", "Castillo Diez", null, null, "correo", null, new LinkedList<Discipline>(Arrays.asList(this.disciplineService.findOne(super.getEntityId("discipline1")), this.disciplineService.findOne(super.getEntityId("discipline2")))),
				"luis7", "luis7", "EXPERT", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo experto con correo electrónico inválido
			}, {
				"admin", "Víctor", "Giménez Fuentes", null, null, "luis@gmail.es", null, new LinkedList<Discipline>(), "victor", "victor", "EXPERT", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo expert sin disciplinas
			},

			/*
			 * Con estos datos probamos el método save(Investor investor) de InvestorService:
			 * (Especificamos el nombre, los apellidos, la dirección (es opcional), el número de teléfono (es opcional), el correo
			 * electrónico, la foto de perfil (es opcional), el nombre de usuario, la contraseña y el rol del nuevo inversor
			 * que queremos crear. No vamos a especificar las inversiones que ha realizado ("investments"), ni los temas que ha publicado
			 * ("threads"), ni las respuestas que ha escrito ("posts") puesto que los valores a dichos atributos se asignan automáticamente en el
			 * método create() y sabemos que a la hora de crear un nuevo inversor siempre se invoca primero al método create() y después al
			 * save(Investor investor). Es reduntante hacer pruebas sobre el método create() ya que dicho método simplemente crea una
			 * instancia de Investor y le asigna colecciones vacías a los atributo "investments", "threads" y "posts".
			 */

			{
				"Asen", "Rangelov Baykushev", "C/ Guadalimar 11, Sevilla", "912730456", "asen_ran@mail.bg", "http:/www.foto1.es", "aseranbay", "aseranbay", "INVESTOR", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			}, {
				null, "Castillo Diez", null, "+34765943453", "luis@gmail.es", null, "luis7", "luis7", "INVESTOR", ConstraintViolationException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo inversor sin nombre
			}, {
				"Víctor", "Giménez Fuentes", null, null, "victor@gmail.es", null, "victor", "victor", "APPRENTICE", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: se intenta crear un nuevo inversor con el rol "APPRENTICE"
			},

			/*
			 * Con estos datos probamos el método save(Administrator administrator) de AdministratorService:
			 * (Especificamos el username del ADMIN principal y el nombre, los apellidos, la dirección (es opcional), el número de teléfono (es opcional), el correo
			 * electrónico, la foto de perfil (es opcional), el nombre de usuario, la contraseña y el rol del nuevo administrador que queremos crear. No vamos a especificar
			 * los temas que ha publicado ("threads"), ni las respuestas que ha escrito ("posts") puesto que los valores a dichos atributos se asignan automáticamente en el
			 * método create() y sabemos que a la hora de crear un nuevo administrador siempre se invoca primero al método create() y después al save(Investor investor).
			 * Es reduntante hacer pruebas sobre el método create() ya que dicho método simplemente crea una instancia de Administrator y le asigna colecciones vacías a los
			 * atributos "threads" y "posts".
			 */
			{
				"admin", "Alberto", "Navarro Sánchez", null, null, "alberto@gmail.es", "http://www.foto1.es", "alberto", "alberto", "ADMIN", null
			// TEST DE CASO POSITIVO: se respetan todas las reglas de negocio
			}, {
				"expert", "Alberto", "Navarro Sánchez", null, null, "correo", null, "alberto", "alberto", "ADMIN", IllegalArgumentException.class
			// TEST DE CASO NEGATIVO: el usuario principal es experto
			},

		};

		for (int i = 0; i < 6; i++)
			try {
				super.startTransaction();
				this.template1((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (Class<?>) testingData[i][9]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 6; i < 11; i++)
			try {
				super.startTransaction();
				this.template2((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 11; i < 14; i++)
			try {
				super.startTransaction();
				this.template3((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(List<Discipline>) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 14; i < 17; i++)
			try {
				super.startTransaction();
				this.template4((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (Class<?>) testingData[i][9]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

		for (int i = 17; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template5((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}

	}
	/*
	 * REQUISITO FUNCIONAL: Un usuario no autentificado se debe poder registrar en el sistema como APPRENTICE
	 * Probamos los métodos create() y save(Apprentice apprentice) de la clase ApprenticeService mediante la siguiente plantilla:
	 */
	protected void template1(final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final String username, final String password, final String role, final Class<?> expected) {
		Class<?> caught;
		Apprentice apprentice;
		UserAccount userAccount;
		Authority authority;

		caught = null;

		try {
			apprentice = this.apprenticeService.create();

			apprentice.setName(name);
			apprentice.setSurname(surname);
			apprentice.setPostalAddress(postalAddress);
			apprentice.setPhone(phone);
			apprentice.setEmail(email);
			apprentice.setLinkPhoto(linkPhoto);

			authority = new Authority();
			authority.setAuthority(role);

			userAccount = new UserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			userAccount.setAuthorities(new ArrayList<Authority>());
			userAccount.addAuthority(authority);

			apprentice.setUserAccount(userAccount);

			this.apprenticeService.save(apprentice);
			super.flushTransaction();

			/*
			 * La comprobación del método save(Apprentice apprentice) termina aquí. Si no ha saltado ninguna
			 * excepción, el usuario principal se ha registrado como APPRENTICE con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder crear perfil de organizador
	 * Probamos los métodos create() y save(Manager manager) de la clase ManagerService mediante la siguiente plantilla:
	 */
	protected void template2(final String principalUsername, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final String username, final String password,
		final String role, final Class<?> expected) {
		Class<?> caught;
		Manager manager;
		UserAccount userAccount;
		Authority authority;

		caught = null;

		try {
			super.authenticate(principalUsername);
			manager = this.managerService.create();

			manager.setName(name);
			manager.setSurname(surname);
			manager.setPostalAddress(postalAddress);
			manager.setPhone(phone);
			manager.setEmail(email);
			manager.setLinkPhoto(linkPhoto);

			authority = new Authority();
			authority.setAuthority(role);

			userAccount = new UserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			userAccount.setAuthorities(new ArrayList<Authority>());
			userAccount.addAuthority(authority);

			manager.setUserAccount(userAccount);

			this.managerService.save(manager);
			super.unauthenticate();
			super.flushTransaction();

			/*
			 * La comprobación del método save(Manager manager) termina aquí. Si no ha saltado ninguna
			 * excepción, el administrador ha creado un nuevo perfil de MANAGER con éxito
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder crear perfil de experto
	 * Probamos los métodos create() y save(Expert expert) de la clase ExpertService mediante la siguiente plantilla:
	 */
	protected void template3(final String principalUsername, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final List<Discipline> disciplines, final String username,
		final String password, final String role, final Class<?> expected) {
		Class<?> caught;
		Expert expert;
		UserAccount userAccount;
		Authority authority;

		caught = null;

		try {
			super.authenticate(principalUsername);
			expert = this.expertService.create();

			expert.setName(name);
			expert.setSurname(surname);
			expert.setPostalAddress(postalAddress);
			expert.setPhone(phone);
			expert.setEmail(email);
			expert.setLinkPhoto(linkPhoto);
			expert.setDisciplines(disciplines);

			authority = new Authority();
			authority.setAuthority(role);

			userAccount = new UserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			userAccount.setAuthorities(new ArrayList<Authority>());
			userAccount.addAuthority(authority);

			expert.setUserAccount(userAccount);

			this.expertService.save(expert);
			super.unauthenticate();
			super.flushTransaction();

			/*
			 * La comprobación del método save(Expert expert) termina aquí. Si no ha saltado ninguna
			 * excepción, el administrador ha creado un nuevo perfil de EXPERT con éxito
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario no autentificado se debe poder registrar en el sistema como INVESTOR.
	 * Probamos los métodos create() y save(Investor investor) de la clase InvestorService mediante la siguiente plantilla:
	 */
	protected void template4(final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final String username, final String password, final String role, final Class<?> expected) {
		Class<?> caught;
		Investor investor;
		UserAccount userAccount;
		Authority authority;

		caught = null;

		try {
			investor = this.investorService.create();

			investor.setName(name);
			investor.setSurname(surname);
			investor.setPostalAddress(postalAddress);
			investor.setPhone(phone);
			investor.setEmail(email);
			investor.setLinkPhoto(linkPhoto);

			authority = new Authority();
			authority.setAuthority(role);

			userAccount = new UserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			userAccount.setAuthorities(new ArrayList<Authority>());
			userAccount.addAuthority(authority);

			investor.setUserAccount(userAccount);

			this.investorService.save(investor);
			super.flushTransaction();

			/*
			 * La comprobación del método save(Investor investor) termina aquí. Si no ha saltado ninguna
			 * excepción, el usuario principal se ha registrado como INVESTOR con éxito.
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * REQUISITO FUNCIONAL: Un usuario registrado como ADMIN debe poder crear otro perfil de administrador
	 * Probamos los métodos create() y save(Administrator administrator) de la clase AdministratorService mediante la siguiente plantilla:
	 */
	protected void template5(final String principalUsername, final String name, final String surname, final String postalAddress, final String phone, final String email, final String linkPhoto, final String username, final String password,
		final String role, final Class<?> expected) {
		Class<?> caught;
		Administrator administrator;
		UserAccount userAccount;
		Authority authority;

		caught = null;

		try {
			super.authenticate(principalUsername);
			administrator = this.administratorService.create();

			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setPostalAddress(postalAddress);
			administrator.setPhone(phone);
			administrator.setEmail(email);
			administrator.setLinkPhoto(linkPhoto);

			authority = new Authority();
			authority.setAuthority(role);

			userAccount = new UserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			userAccount.setAuthorities(new ArrayList<Authority>());
			userAccount.addAuthority(authority);

			administrator.setUserAccount(userAccount);

			this.administratorService.save(administrator);
			super.unauthenticate();
			super.flushTransaction();

			/*
			 * La comprobación del método save(Administrator administrator) termina aquí. Si no ha saltado ninguna
			 * excepción, el administrador ha creado un nuevo perfil de ADMINISTRADOR con éxito
			 * ____________________________________________________________________________________________________
			 */

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
