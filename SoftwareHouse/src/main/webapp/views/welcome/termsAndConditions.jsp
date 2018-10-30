<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
	<jstl:when test="${language == 'en'}">
		<div>
			
			<p>Welcome to SoftwareHouse!</p>
			<p>These terms and conditions outline the rules and regulations
				for the use of SoftwareHouse's Website.</p>
			<p>SoftwareHouse is located at: Av. Reina Mercedes s/n,
				41012 Seville, Spain</p>
			<p>By accessing this website we assume you accept these terms and
				conditions. Do not continue to use SoftwareHouse's website if you
				do not agree to take all of the terms and conditions stated on this
				page.</p>
			<p>The following terminology applies to these Terms and
				Conditions, Privacy Statement and Disclaimer Notice and all
				Agreements: "Client", "You" and "Your" refers to you, the person log
				on this website and compliant to the Company's terms and conditions.
				"The Company", "Ourselves", "We", "Our" and "Us", refers to our
				Company. "Party", "Parties", or "Us", refers to both the Client and
				ourselves. All terms refer to the offer, acceptance and
				consideration of payment necessary to undertake the process of our
				assistance to the Client in the most appropriate manner for the
				express purpose of meeting the Client's needs in respect of
				provision of the Company's stated services, in accordance with and
				subject to, prevailing law of Netherlands. Any use of the above
				terminology or other words in the singular, plural, capitalization
				and/or he/she or they, are taken as interchangeable and therefore as
				referring to same.</p>
				<p>
				<strong>ABUSE POLICY</strong>
			</p>
			<p>SoftwareHouse worries about the usage and purpose users give to the System Service
			provided, it is therefore that every web administrator have the power to delete
			any thread or post uploaded to the System that contains sensitive or inappropiate data.
			</p>
			<p>The reasons why an thread or post can be marked as inappropiate are:
			</p>
			<p>- Spam.</p>
			<p>- Xenophobic, racist, sexist or offensive language </p>
			<p>- Advertising any political or social ideology</p>
			<h3>
				<strong>Cookies</strong>
			</h3>
			<p>We employ the use of cookies. By accessing SoftwareHouse's
				website, you agreed to use cookies in agreement with the
				SoftwareHouse's privacy policy.</p>
			<p>Most interactive websites use cookies to let us retrieve the
				user's details for each visit. Cookies are used by our website to
				enable the functionality of certain areas to make it easier for
				people visiting our website. Some of our affiliate/advertising
				partners may also use cookies.</p>
			<h3>
				<strong>License</strong>
			</h3>
			<p>Unless otherwise stated, SoftwareHouse and/or its
				licensors own the intellectual property rights for all material on
				SoftwareHouse All intellectual property rights are reserved.
				You may access this from www.softwarehouse.com for your own
				personal use subjected to restrictions set in these terms and
				conditions.</p>
			<p>You must not:</p>
			<ul>
				<li>Republish material from www.softwarehouse.com</li>
				<li>Sell, rent or sub-license material from
					www.softwarehouse.com</li>
				<li>Reproduce, duplicate or copy material
					from www.softwarehouse.com</li>
				<li>Redistribute content from SoftwareHouse.</li>
			</ul>
			<p>This Agreement shall begin on the date hereof.</p>
			<p>Parts of this website offer an opportunity for users to post
				and exchange opinions and information in certain areas of the
				website. SoftwareHouse does not filter, edit, publish or review
				Comments prior to their presence on the website. Comments do not
				reflect the views and opinions of SoftwareHouse, its agents and/or
				affiliates. Comments reflect the views and opinions of the person
				who post their views and opinions. To the extent permitted by
				applicable laws SoftwareHouse shall not be liable for the Comments
				or for any liability, damages or expenses caused and/or suffered as
				a result of any use of and/or posting of and/or appearance of the
				Comments on this website.</p>
			<p>SoftwareHouse reserves the right to monitor all
				Comments and to remove any Comments which can be considered
				inappropriate, offensive or causes breach of these Terms and
				Conditions.</p>
			<p>You warrant and represent that:</p>
			<ul>
				<li>You are entitled to post the Comments on our website and
					have all necessary licenses and consents to do so;</li>
				<li>The Comments do not invade any intellectual property right,
					including without limitation copyright, patent or trademark of any
					third party;</li>
				<li>The Comments do not contain any defamatory, libelous,
					offensive, indecent or otherwise unlawful material which is an
					invasion of privacy</li>
				<li>The Comments will not be used to solicit or promote
					business or custom or present commercial activities or unlawful
					activity.</li>
			</ul>
			<p>You hereby grant SoftwareHouse a non-exclusive license
				to use, reproduce, edit and authorize others to use, reproduce and
				edit any of your Comments in any and all forms, formats or media.</p>
			<h3>
				<strong>Hyperlinking to our Content</strong>
			</h3>
			<p>The following organizations may link to our Website without
				prior written approval:</p>
			<ul>
				<li>Government agencies;</li>
				<li>Search engines;</li>
				<li>News organizations;</li>
				<li>Online directory distributors may link to our Website in
					the same manner as they hyperlink to the Websites of other listed
					businesses; and</li>
				<li>System wide Accredited Businesses except soliciting
					non-profit organizations, charity shopping malls, and charity
					fundraising groups which may not hyperlink to our Web site.</li>
			</ul>
			<p>These organizations may link to our home page, to publications
				or to other Website information so long as the link: (a) is not in
				any way deceptive; (b) does not falsely imply sponsorship,
				endorsement or approval of the linking party and its products and/or
				services; and (c) fits within the context of the linking party's
				site.</p>
			<p>We may consider and approve other link requests from the
				following types of organizations:</p>
			<ul>
				<li>commonly-known consumer and/or business information
					sources;</li>
				<li>dot.com community sites;</li>
				<li>associations or other groups representing charities;</li>
				<li>online directory distributors;</li>
				<li>internet portals;</li>
				<li>accounting, law and consulting firms; and</li>
				<li>educational institutions and trade associations.</li>
			</ul>
			<p>We will approve link requests from these organizations if we
				decide that: (a) the link would not make us look unfavorably to
				ourselves or to our accredited businesses; (b) the organization does
				not have any negative records with us; (c) the benefit to us from
				the visibility of the hyperlink compensates the absence of
				SoftwareHouse; and (d) the link is in the context of general
				resource information.</p>
			<p>These organizations may link to our home page so long as the
				link: (a) is not in any way deceptive; (b) does not falsely imply
				sponsorship, endorsement or approval of the linking party and its
				products or services; and (c) fits within the context of the linking
				party's site.</p>
			<p>If you are one of the organizations listed in paragraph 2
				above and are interested in linking to our website, you must inform
				us by sending an e-mail to softwarehouse@acme.com. Please include your
				name, your organization name, contact information as well as the URL
				of your site, a list of any URLs from which you intend to link to
				our Website, and a list of the URLs on our site to which you would
				like to link. Wait 2-3 weeks for a response.</p>
			<p>Approved organizations may hyperlink to our Website as
				follows:</p>
			<ul>
				<li>By use of our corporate name; or</li>
				<li>By use of the uniform resource locator being linked to; or</li>
				<li>By use of any other description of our Website being linked
					to that makes sense within the context and format of content on the
					linking party's site.</li>
			</ul>
			<p>No use of SoftwareHouse's logo or other artwork will be
				allowed for linking absent a trademark license agreement.</p>
			<h3>
				<strong>iFrames</strong>
			</h3>
			<p>Without prior approval and written permission, you may not
				create frames around our Webpages that alter in any way the visual
				presentation or appearance of our Website.</p>
			<h3>
				<strong>Content Liability</strong>
			</h3>
			<p>We shall not be hold responsible for any content that appears
				on your Website. You agree to protect and defend us against all
				claims that is rising on your Website. No link(s) should appear on
				any Website that may be interpreted as libelous, obscene or
				criminal, or which infringes, otherwise violates, or advocates the
				infringement or other violation of, any third party rights.</p>
			<h3>
				<strong>Reservation of Rights</strong>
			</h3>
			<p>We reserve the right to request that you remove all links or
				any particular link to our Website. You approve to immediately
				remove all links to our Website upon request. We also reserve the
				right to amen these terms and conditions and it's linking policy at
				any time. By continuously linking to our Website, you agree to be
				bound to and follow these linking terms and conditions.</p>
			<h3>
				<strong>Removal of links from our website</strong>
			</h3>
			<p>If you find any link on our Website that is offensive for any
				reason, you are free to contact and inform us any moment. We will
				consider requests to remove links but we are not obligated to or so
				or to respond to you directly.</p>
			<p>We do not ensure that the information on this website is
				correct, we do not warrant its completeness or accuracy; nor do we
				promise to ensure that the website remains available or that the
				material on the website is kept up to date.</p>
			<h3>
				<strong>Disclaimer</strong>
			</h3>
			<p>To the maximum extent permitted by applicable law, we exclude
				all representations, warranties and conditions relating to our
				website and the use of this website. Nothing in this disclaimer
				will:</p>
			<ul>
				<li>limit or exclude our or your liability for death or
					personal injury;</li>
				<li>limit or exclude our or your liability for fraud or
					fraudulent misrepresentation;</li>
				<li>limit any of our or your liabilities in any way that is not
					permitted under applicable law; or</li>
				<li>exclude any of our or your liabilities that may not be
					excluded under applicable law.</li>
			</ul>
			<p>The limitations and prohibitions of liability set in this
				Section and elsewhere in this disclaimer: (a) are subject to the
				preceding paragraph; and (b) govern all liabilities arising under
				the disclaimer, including liabilities arising in contract, in tort
				and for breach of statutory duty.</p>
			<p>As long as the website and the information and services on the
				website are provided free of charge, we will not be liable for any
				loss or damage of any nature.</p>
			<h3>
				<strong>Credit</strong>
			</h3>
		

		</div>
	</jstl:when>
	<jstl:otherwise>
		<div>
			<p>
				<strong>POLÍTICA DE ABUSO</strong>
			</p>
			<p>SoftwareHouse se preocupa por el uso que los usuarios dan al servicio
				ofrecido, es por ello que todo administrador de la web tiene potestad para 
				eliminar cualquier tema o respuesta subida a la plataforma por algún usuario
				si éste contiene datos sensibles o inapropiados. 
			</p>
			<p>Las motivos por las que un hilo o respuesta puede ser marcada como inapropiado son:
			</p>
			<p>- Spam.</p>
			<p>- Lenguaje xenófobo, racista, machista, ofensivo, discriminatorio, etc.</p>
			<p>- Propaganda de algún grupo social o político</p>
		
			<p>
				<strong>INFORMACIÓN RELEVANTE</strong>
			</p>
			<p>Es requisito necesario para la adquisición de los productos
				que se ofrecen en este sitio, que lea y acepte los siguientes
				Términos y Condiciones que a continuación se redactan. El uso de
				nuestros servicios así como la compra de nuestros productos
				implicará que usted ha leído y aceptado los Términos y Condiciones
				de Uso en el presente documento. Todas los productos que son
				ofrecidos por nuestro sitio web pudieran ser creadas, cobradas,
				enviadas o presentadas por una página web tercera y en tal caso
				estarían sujetas a sus propios Términos y Condiciones. En algunos
				casos, para adquirir un producto, será necesario el registro por
				parte del usuario, con ingreso de datos personales fidedignos y
				definición de una contraseña.</p>
			<p>El usuario puede elegir y cambiar la clave para su acceso de
				administración de la cuenta en cualquier momento, en caso de que se
				haya registrado y que sea necesario para la compra de alguno de
				nuestros productos. www.softwarehouse.com no asume la
				responsabilidad en caso de que entregue dicha clave a terceros.</p>
			<p>Todas las compras y transacciones que se lleven a cabo por
				medio de este sitio web, están sujetas a un proceso de confirmación
				y verificación, el cual podría incluir la verificación del stock y
				disponibilidad de producto, validación de la forma de pago,
				validación de la factura (en caso de existir) y el cumplimiento de
				las condiciones requeridas por el medio de pago seleccionado. En
				algunos casos puede que se requiera una verificación por medio de
				correo electrónico.</p>
			<p>Los precios de los productos ofrecidos en esta Tienda Online
				es válido solamente en las compras realizadas en este sitio web.</p>
			<p>
				<strong>LICENCIA</strong>
			</p>
			<p>SoftwareHouse a través de su sitio web concede una licencia
				para que los usuarios utilicen; los productos que son vendidos en
				este sitio web de acuerdo a los Términos y Condiciones que se
				describen en este documento.</p>
			<p>
				<strong>USO NO AUTORIZADO</strong>
			</p>
			<p>En caso de que aplique (para venta de software, templetes, u
				otro producto de diseño y programación) usted no puede colocar uno
				de nuestros productos, modificado o sin modificar, en un CD, sitio
				web o ningún otro medio y ofrecerlos para la redistribución o la
				reventa de ningún tipo.</p>
			<p>
				<strong>PROPIEDAD</strong>
			</p>
			<p>Usted no puede declarar propiedad intelectual o exclusiva a
				ninguno de nuestros productos, modificado o sin modificar. Todos los
				productos son propiedad de los proveedores del contenido. En caso de
				que no se especifique lo contrario, nuestros productos se
				proporcionan sin ningún tipo de garantía, expresa o implícita. En
				ningún esta compañía será ;responsables de ningún daño incluyendo,
				pero no limitado a, daños directos, indirectos, especiales,
				fortuitos o consecuentes u otras pérdidas resultantes del uso o de
				la imposibilidad de utilizar nuestros productos.</p>
			<p>
				<strong>POLÍTICA DE REEMBOLSO Y GARANTÍA</strong>
			</p>
			<p>En el caso de productos que sean&nbsp; mercancías irrevocables
				no-tangibles, no realizamos reembolsos después de que se envíe el
				producto, usted tiene la responsabilidad de entender antes de
				comprarlo. &nbsp;Le pedimos que lea cuidadosamente antes de
				comprarlo. Hacemos solamente excepciones con esta regla cuando la
				descripción no se ajusta al producto. Hay algunos productos que
				pudieran tener garantía y posibilidad de reembolso pero este será
				especificado al comprar el producto. En tales casos la garantía solo
				cubrirá fallas de fábrica y sólo se hará efectiva cuando el producto
				se haya usado correctamente. La garantía no cubre averías o daños
				ocasionados por uso indebido. Los términos de la garantía están
				asociados a fallas de fabricación y funcionamiento en condiciones
				normales de los productos y sólo se harán efectivos estos términos
				si el equipo ha sido usado correctamente. Esto incluye:</p>
			<p>
				- De acuerdo a las especificaciones técnicas indicadas para cada
				producto.<br>- En condiciones ambientales acorde con las
				especificaciones indicadas por el fabricante.<br>- En uso
				específico para la función con que fue diseñado de fábrica.<br>-
				En condiciones de operación eléctricas acorde con las
				especificaciones y tolerancias indicadas.
			</p>
			<p>
				<strong>COMPROBACIÓN ANTIFRAUDE</strong>
			</p>
			<p>La compra del cliente puede ser aplazada para la comprobación
				antifraude. También puede ser suspendida por más tiempo para una
				investigación más rigurosa, para evitar transacciones fraudulentas.</p>
			<p>
				<strong>PRIVACIDAD</strong>
			</p>
			<p>Este sitio web garantiza que la información personal que usted
				envía cuenta con la seguridad necesaria. Los datos ingresados por
				usuario o en el caso de requerir una validación de los pedidos no
				serán entregados a terceros, salvo que deba ser revelada en
				cumplimiento a una orden judicial o requerimientos legales.</p>
			<p>La suscripción a boletines de correos electrónicos
				publicitarios es voluntaria y podría ser seleccionada al momento de
				crear su cuenta.</p>
			<p>SoftwareHouse reserva los derechos de cambiar o de modificar
				estos términos sin previo aviso.</p>



		</div>
	</jstl:otherwise>
</jstl:choose>
