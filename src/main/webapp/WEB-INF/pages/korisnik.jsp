<%@page import="hr.fer.opp.projekt.model.Korisnik"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Korisnik</title>
		<link rel="icon" type="image/png"
			href="/Citanje.NET/avatars/book-icon.png">

		<!-- Behavioral Meta Data -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

			<style type="text/css">
			.korisnik-lista {
				list-style: none outside none;
			}
		</style>
		<link
			href='http://fonts.googleapis.com/css?family=Roboto:400,900,900italic,700italic,700,500italic,400italic,500,300italic,300'
			rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Pacifico'
			rel='stylesheet' type='text/css'>
		<link href='http:/Citanje.NET/style/css/style.css' rel='stylesheet'
			type='text/css'>

		<!-- jQuery Datetime picker -->
		<link href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css" rel='stylesheet' type='text/css'></link>

		<!-- Custom CSS -->
		<link href='http:/Citanje.NET/style/css/custom.css' rel='stylesheet'
			type='text/css'>

		<!-- Latest compiled and minified JavaScript -->
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

		<!-- Bootstrap -->
		<link rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
			integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
			crossorigin="anonymous">
		<link rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
			integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
			crossorigin="anonymous">
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
			integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
			crossorigin="anonymous"></script>
	</head>
	<body>
		<a name="ancre"></a>

		<!-- CACHE -->
		<div class="cache"></div>

		<!-- HEADER -->

		<div id="wrapper-header">
			<div id="main-header" class="object">
				<div class="logo">
					<a href="/Citanje.NET/">
					<img
						src="http:/Citanje.NET/style/img/logo.png" alt="citanje-logo"
						id="citanje-logo">
					</a>
				</div>
				<div class="row" id="main_tip_search">
					<c:set var="trenutni" value="${sessionScope['current.user']}" />
					<c:set var="isAdmin" value="${sessionScope['current.admin']}" />
					<c:if test="${trenutni!=null}">
						<div class="col-xs-4 pull-right">
							Prijavljeni ste kao <a href="/Citanje.NET/korisnik/${trenutni}">${trenutni}</a>
						</div>
						<div class="col-xs-4 pull-right">
							<a href="/Citanje.NET/odjava">
								<button type="button" class="btn btn-warning"
									data-toggle="button" aria-pressed="false" autocomplete="off">
									Odjava</button>
							</a>
						</div>
						<c:if test="${isAdmin==true}">
							<div class="col-xs-4 pull-right">
								<a href="/Citanje.NET/kontrolpanel">
									<button type="button" class="btn btn-warning"
										data-toggle="button" aria-pressed="false" autocomplete="off">
										Kontrol panel</button>
								</a>
							</div>
						</c:if>
					</c:if>
					<c:if test="${trenutni==null}">
						<div class="col-xs-4 pull-right">
							<a href="/Citanje.NET/registracija">
								<button type="button" class="btn btn-primary"
									data-toggle="button" aria-pressed="false" autocomplete="off">Registracija</button>
							</a>
						</div>
						<div class="col-xs-4 pull-right">
							<a href="/Citanje.NET/prijava">
								<button type="button" class="btn btn-primary"
									data-toggle="button" aria-pressed="false" autocomplete="off">Prijava</button>
							</a>
						</div>
					</c:if>
				</div>
				<div id="stripes"></div>
			</div>
		</div>

		<!-- PORTFOLIO -->
		<div id="wrapper-container">
			<div class="container object" id="container-object">
				<div id="main-container-image">
					<section class="work">
						<c:set var="trenutni" value="${sessionScope['current.user']}" />
						<c:set var="isAdmin" value="${sessionScope['current.admin']}" />
						<div class="polja">
							<h1>${korisnik.korisnickoIme }</h1>
						</div>

						<br>

						<object class="polja"
							data="${pageContext.request.contextPath}/avatars/${korisnik.korisnickoIme}.png"
							type="image/png" style="width: 90px; height: 90px;">
							<img class="polja"
								src="${pageContext.request.contextPath}/avatars/default-avatar.png"
								style="width: 90px; height: 90px;">
						</object>
						<br>
						<c:if test="${trenutni!=null }">
							<c:if
								test="${korisnik.korisnickoIme.equals(trenutni)||isAdmin==true }">
								<br>
								<form class="polja"
									action="/Citanje.NET/korisnik/${korisnik.korisnickoIme }/slika"
									method="post" enctype="multipart/form-data" accept="image/*">
									<input type="file" id="file" name="file"> <br> <input
										type="submit" value="Upload">
								</form>
								<legend class="legend"></legend>
								<c:choose>
									<c:when
										test="${uredi==true && (korisnik.korisnickoIme == trenutni || isAdmin==true) }">
										<form
											action="/Citanje.NET/korisnik/${korisnik.korisnickoIme }/uredi"
											method="post">
											<fieldset>
												<div class="polja">
													Ime: <input class="form-control prijava-polje" type="text"
														name="ime" value="${zapis.ime}"><br>
													<c:if test="${zapis.imaPogresku('ime')}">
														<p class="pogreska">
															<c:out value="${zapis.dohvatiPogresku('ime')}" />
														</p>
													</c:if>
												</div>
												<div class="polja">
													Prezime: <input class="form-control prijava-polje"
														type="text" name="prezime" value="${zapis.prezime}">
													<br>
													<c:if test="${zapis.imaPogresku('prezime')}">
														<p class="pogreska">
															<c:out value="${zapis.dohvatiPogresku('prezime')}" />
														</p>
													</c:if>
												</div>

												<div class="polja">
													<b>*</b> Korisničko ime: <input
														class="form-control prijava-polje" type="text"
														name="korisnickoIme" value="${zapis.korisnickoIme}"
														readonly="readonly"><br>
													<c:if test="${zapis.imaPogresku('korisnickoIme')}">
														<p class="pogreska">
															<c:out value="${zapis.dohvatiPogresku('korisnickoIme')}" />
														</p>
													</c:if>
												</div>

												<div class="polja">
													Lozinka: <input class="form-control prijava-polje"
														type="password" name="lozinka" value="${zapis.lozinka}"><br>
													<c:if test="${zapis.imaPogresku('lozinka')}">
														<p class="pogreska">
															<c:out value="${zapis.dohvatiPogresku('lozinka')}" />
														</p>
													</c:if>
												</div>

												<div class="polja">
													<b>*</b> Email: <input class="form-control prijava-polje"
														type="text" name="email" value="${zapis.email}"
														readonly="readonly"><br>
													<c:if test="${zapis.imaPogresku('email')}">
														<p class="pogreska">
															<c:out value="${zapis.dohvatiPogresku('email')}" />
														</p>
													</c:if>
												</div>

												<div class="polja">
													Lokacija: <input class="form-control prijava-polje"
														type="text" name="lokacija" value="${zapis.lokacija}"><br>
													<c:if test="${zapis.imaPogresku('lokacija')}">
														<p class="pogreska">
															<c:out value="${zapis.dohvatiPogresku('lokacija')}" />
														</p>
													</c:if>
												</div>

												<div class="polja">
													Datum rođenja: <input class="form-control prijava-polje datumRod"
														id="datumRod" readonly="readonly"
														type="text" name="datumRod" value="${zapis.datumRod}"><br>
													<c:if test="${zapis.imaPogresku('datumRod')}">
														<p class="pogreska">
															<c:out value="${zapis.dohvatiPogresku('datumRod')}" />
														</p>
													</c:if>
												</div>

												<div class="polja">
													<b>*</b> Polje mora biti unikatno
												</div>
												<input class="btn btn-success polja" type="submit"
													value="Pohrani promjene"> <a
													class="btn btn-warning polja"
													href="/Citanje.NET/korisnik/${korisnik.korisnickoIme}">Odustani</a>
											</fieldset>

										</form>

									</c:when>
									<c:otherwise>

										<p class="polja">Ime i Prezime: ${korisnik.ime }
											${korisnik.prezime }</p>
										<p class="polja">E-mail: ${korisnik.email }</p>
										<p class="polja">Lokacija: ${korisnik.lokacija}</p>
										<p class="polja">Datum rođenja: ${korisnik.datumRod}</p>

									</c:otherwise>
								</c:choose>
								<c:if
									test="${korisnik.korisnickoIme.equals(trenutni)||isAdmin==true}">
									<a class="btn btn-primary polja"
										href="/Citanje.NET/korisnik/${korisnik.korisnickoIme }/uredi">Uređivanje
										profila</a>
								</c:if>
							</c:if>
							<c:if
								test="${!korisnik.korisnickoIme.equals(trenutni) && korisnik.isAdmin==false }">
								<a class="btn btn-primary polja"
									href="/Citanje.NET/report/${korisnik.korisnickoIme}">Prijavi
									korisnika</a>
							</c:if>
						</c:if>

						<a class="btn btn-primary polja"
							href="/Citanje.NET/korisnik/${korisnik.korisnickoIme}/komentari">Komentari</a>
						<a class="btn btn-primary polja"
							href="/Citanje.NET/korisnik/${korisnik.korisnickoIme}/biljeske">Bilješke</a>
						<legend class="legend"></legend>
							<c:if test="${fn:length(korisnik.unio) > 0}">
								<div class="polja">
									Unio djela:
									<c:forEach var="d" items="${korisnik.unio}">
										<li class="polja"><a href="/Citanje.NET/djelo/${d.id}">${d.naslov}</a></li>
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${fn:length(korisnik.komentariNa) > 0}">
								<legend class="legend"></legend>
								<div class="polja">
									Komentirao na:
									<c:forEach var="d" items="${korisnik.komentariNa}">
										<li class="polja"><a href="/Citanje.NET/djelo/${d.id}">${d.naslov}</a></li>
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${fn:length(korisnik.biljeskeNa) > 0}">
								<legend class="legend"></legend>
								<div class="polja">
									Bilješke na:
									<c:forEach var="d" items="${korisnik.biljeskeNa}">
										<li class="polja"><a href="/Citanje.NET/djelo/${d.id}">${d.naslov}</a></li>
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${fn:length(korisnik.autori) > 0}">
								<legend class="legend"></legend>
								<div class="polja">
									Zadnji uredio autore:
									<c:forEach var="a" items="${korisnik.autori}">
										<li class="polja"><a href="/Citanje.NET/autor/${a.id}">${a.imeAutora}
												${a.prezimeAutora}</a></li>
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${fn:length(korisnik.djela) > 0}">
								<legend class="legend"></legend>
								<div class="polja">
									Zadnji uredio djela:
									<c:forEach var="d" items="${korisnik.djela}">
										<li class="polja"><a href="/Citanje.NET/djelo/${d.id}">${d.naslov}</a></li>
									</c:forEach>
								</div>
							</c:if>
					</section>
				</div>
			</div>

			<div id="wrapper-thank">
				<div class="thank">
					<div class="thank-text">
						Cita<span style="letter-spacing: -5px;">nje.</span>NET
					</div>
				</div>
			</div>

			<div id="wrapper-copyright">
				<div class="copyright">
					<div class="copy-text object">
						Created by <a style="color: #D0D1D4;"> StuDevs</a>
					</div>
				</div>
			</div>

		</div>

		<!-- SCRIPT -->
		<script type="text/javascript"
			src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript"
			src="http:/Citanje.NET/scripts/jquery.scrollTo.min.js"></script>
		<script type="text/javascript"
			src="http:/Citanje.NET/scripts/jquery.localScroll.min.js"></script>
		<script type="text/javascript"
			src="http:/Citanje.NET/scripts/jquery-animate-css-rotate-scale.js"></script>
		<script type="text/javascript"
			src="http:/Citanje.NET/scripts/fastclick.min.js"></script>
		<script type="text/javascript"
			src="http:/Citanje.NET/scripts/jquery.animate-colors-min.js"></script>
		<script type="text/javascript"
			src="http:/Citanje.NET/scripts/jquery.animate-shadow-min.js"></script>
		<script type="text/javascript" src="http:/Citanje.NET/scripts/main.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<script type="text/javascript" src="http:/Citanje.NET/scripts/datepicker-hr.js"></script>
		<script type="text/javascript">
			$(function() {
        $.datepicker.setDefaults($.datepicker.regional['hr']);
				$( "#datumRod" ).datepicker({ dateFormat: 'dd mm yy', changeYear: true, changeMonth: true, yearRange: "1900:2017" });
			});
		</script>
	</body>
</html>