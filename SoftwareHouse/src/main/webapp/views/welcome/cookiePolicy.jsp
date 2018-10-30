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


	<p>
		<strong>IMPORTANT NOTICE: PLEASE READ CAREFULLY BEFORE USING
			THIS WEBSITE: </strong>This policy explains how cookies are used on this
		website. The policy may be amended from time to time. By using this
		site you agree to the placement of cookies on your computer in
		accordance with the terms of this policy. If you do not wish to accept
		cookies from this site please either disable cookies or refrain from
		using this site.
	</p>
	<h3>1. What are Cookies?</h3>
	<p>A cookie is a text-only string of information that a website
		transfers to the cookie file of the browser on your computer's hard
		disk so that the website can recognise you when you revisit and
		remember certain information about you. This can include which pages
		you have visited, choices you have made from menus, any specific
		information you have entered into forms and the time and date of your
		visit.</p>
	<h3>2. Types of Cookies</h3>
	<p>There are two main types of cookies:</p>
	<ul>
		<li><strong>Session cookies: </strong>these are temporary cookies
			that expire at the end of a browser session; that is, when you leave
			the site. Session cookies allow the website to recognise you as you
			navigate between pages during a single browser session and allow you
			to use the website most efficiently. For example, session cookies
			enable a website to remember that a user has placed items in an
			online shopping basket.</li>
		<li><strong>Persistent cookies:</strong> in contrast to session
			cookies, persistent cookies are stored on your equipment between
			browsing sessions until expiry or deletion. They therefore enable the
			website to "recognise" you on your return, remember your preferences,
			and tailor services to you.</li>
	</ul>
	<p>In addition to session cookies and persistent cookies, there may
		be other cookies which are set by the website which you have chosen to
		visit, such as this website, in order to provide us or third parties
		with information.</p>
	<h3>3. Our use of Cookies</h3>
	<p>
		<strong>SoftwareHouse </strong> currently use the following types of
		cookies on this website.
	</p>
	<p>We use these session cookies:</p>
	<ul>
		<li>"JSESSIONID": this is a cookie that keeps your session status.</li>
		<li>"language": this cookie is set whenever you change the language in which your system must show its messages. </li>
	</ul>
	<p>SoftwareHouse doesn't use any persistent cookies.</p>

	
	<h3>4. Refusing Cookies on this Site</h3>
	<p>Most browsers are initially set to accept cookies. However, you
		have the ability to disable cookies if you wish, generally through
		changing your internet software browsing settings. It may also be
		possible to configure your browser settings to enable acceptance of
		specific cookies or to notify you each time a new cookie is about to
		be stored on your computer enabling you to decide whether to accept or
		reject the cookie. </p>

</div>
</jstl:when>
<jstl:otherwise>

	<div>
	
	
		<p>
			<strong>AVISO IMPORTANTE: POR FAVOR LEE DETENIDAMENTE ANTES DE USAR ESTA WEB: </strong>
			
			Esta norma explica cómo son usadas las cookies en este 
			sitio web. La norma puede ser modificada cada cierto tiempo. Al usar esta web
			accede al almacenamiento de cookies en su ordenador de 
			acuerdo con los términos de esta norma. Si no desea aceptar
			cookies de esta web por favor deshabilite las cookies o asténgase de
			usar este sitio web.
			
		</p>
		<h3>1. ¿Qué son las cookies?</h3>
		<p>Una cookie es una cadena de texto de información que un sitio web
			transfiere al archivo de cookies del navegador en el disco duro de su ordenador
			para que el sitio web en cuestión pueda reconocerle cuando lo vuelve a visitar y 
			pueda recordar cierta información sobre usted. Esta información puede incluir qué páginas
			ha visitado, elecciones que ha hecho desde los menús, alguna información específica
			que haya introducido en formularios y la fecha y hora de su visita
		</p>
		<h3>2. Tipos de Cookies</h3>
		<p>Hay dos tipos de cookies principales:</p>
		<ul>
			<li><strong>Cookies de sesión: </strong>Son cookies temporales
				que expiran al final de una sesión del navegador; esto es, cuando usted
				abandona el sitio web. Cookies de sesión permiten a la web reconocerle 
				al navegar entre diferentes páginas usando una única sesión del navegador
				y permite usar el sitio web más eficientemente. Por ejemplo, cookies de sesión
				permiten al sitio web recordar que un usuario ha añadido objetos en un 
				carrito de tienda online.
				</li>
			<li><strong>Cookies de persistencia:</strong> al contrario que las cookies de sesión,
				las cookies de persistencia son almacenadas en su equipo entre sesiones del navegador
				hasta que expiran o se eliminan. Por tanto, habilitan a la web a reconocerle cuando vuelve,
				recordar sus preferencias, y realizar servicios a medida para usted.
				</li>
		</ul>
		<p>Además de las cookies de sesión y las cookies de persistencia, pueden 
			haber otras cookies que pueden ser almacenadas por la web que usted ha elegido
			visitar, como el caso de este sitio web, para proporcionarnos a nosotros o terceros información.
	 </p>
		<h3>3. Nuestro uso de cookies</h3>
		<p>
			<strong>SoftwareHouse </strong> actualmente usa los siguientes tipos de cookies
		</p>
		<p>Usamos estas cookies de sesión:</p>
		<ul>
			<li>"JSESSIONID": esta es una cookie que almacena el estado de su sesión</li>
			<li>"idioma": esta cookie es modificada cuando usted modifica el idioma en el que el sistema debe mostrarle los mensajes. </li>
		</ul>
		<p>SoftwareHouse no usa ningún tipo de cookies de persistencia.</p>
	
		
		<h3>4. Rechazar las cookies en esta web</h3>
		<p>Muchos navegadores están inicialmente configurados para aceptar cookies. Sin embargo,
			usted puede deshabilitarlas si lo desea, generalmente cambiando la configuración de su
			navegador. Puede también ser posible configurar su navegador para aceptar unas cookies 
			específicas o para notificar cada vez que una nueva cookie está a punto de almacenarse en su
			ordenador, dando la posibilidad de decidir si aceptar o rechazar dicha cookie.
		</p>
	
	</div>

</jstl:otherwise>
</jstl:choose>
