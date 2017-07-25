<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Registracija</title>
		<link rel="icon" type="image/png" href="/Citanje.NET/avatars/book-icon.png">

		<!-- Behavioral Meta Data -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	  <link href='http://fonts.googleapis.com/css?family=Roboto:400,900,900italic,700italic,700,500italic,400italic,500,300italic,300' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
		<link href='style/css/style.css' rel='stylesheet' type='text/css'>

		<!-- jQuery Datetime picker -->
		<link href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css" rel='stylesheet' type='text/css'></link>

		<!-- Custom CSS -->
		<link href='style/css/custom.css' rel='stylesheet' type='text/css'>

		<!-- Latest compiled and minified JavaScript -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

		<!-- Bootstrap -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	</head>
	<body>
		<a name="ancre"></a>

		<!-- CACHE -->
		<div class="cache"></div>

		<!-- HEADER -->

		<div id="wrapper-header">
			<div id="main-header" class="object">
				<div class="logo">
					<a href="/Citanje.NET/"><img src="style/img/logo.png" alt="citanje-logo" id="citanje-logo"></a>
				</div>
				<div id="stripes"></div>
			</div>
		</div>

		<!-- PORTFOLIO -->
		<div id="wrapper-container">
			<div class="container object" id="container-object">
				<div id="main-container-image">
					<section class="work">
						<p>${errorMessage}</p>
						<form action="/Citanje.NET/registracija" method="post">
							<fieldset>
								<div class="polja">
									Ime:
									<input class="form-control prijava-polje" type="text" name="ime" value="${zapis.ime}">
									<c:if test="${zapis.imaPogresku('ime')}">
									<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('ime')}"/></p>
									</c:if>
								</div>

								<div class="polja">
									Prezime:
									<input class="form-control prijava-polje" type="text" name="prezime" value="${zapis.prezime}">
									<c:if test="${zapis.imaPogresku('prezime')}">
									<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('prezime')}"/></p>
									</c:if>
								</div>

								<div class="polja">
									<b>*</b> Korisničko ime:
									<input class="form-control prijava-polje" type="text" name="korisnickoIme" value="${zapis.korisnickoIme}">
									<c:if test="${zapis.imaPogresku('korisnickoIme')}">
									<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('korisnickoIme')}"/></p>
									</c:if>
								</div>

								<div class="polja">
									Lozinka:
									<input class="form-control prijava-polje" type="password" name="lozinka" value="${zapis.lozinka}">
									<c:if test="${zapis.imaPogresku('lozinka')}">
									<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('lozinka')}"/></p>
									</c:if>
								</div>

								<div class="polja">
									<b>*</b> Email:
									<input class="form-control prijava-polje" type="text" name="email" value="${zapis.email}">
									<c:if test="${zapis.imaPogresku('email')}">
									<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('email')}"/></p>
									</c:if>
								</div>

								<div class="polja">
									Lokacija:
									<input class="form-control prijava-polje" type="text" name="lokacija" value="${zapis.lokacija}">
									<c:if test="${zapis.imaPogresku('lokacija')}">
									<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('lokacija')}"/></p>
									</c:if>
								</div>

								<div class="polja">
									Datum rođenja:
									<input class="form-control prijava-polje datumRod" id="datumRod" type="text"
									readonly="readonly" name="datumRod" value="${zapis.datumRod}">
									<c:if test="${zapis.imaPogresku('datumRod')}">
									<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('datumRod')}"/></p>
								</c:if>
								</div>

								<div class="polja"><b>*</b> Polja moraju biti unikatna</div>
								<input class="btn btn-primary polja" type="submit" value="Registracija">
							</fieldset>
						</form>
					</section>
				</div>
			</div>

			<div id="wrapper-thank">
				<div class="thank">
					<div class="thank-text">Cita<span style="letter-spacing:-5px;">nje.</span>NET</div>
				</div>
			</div>

			<div id="wrapper-copyright">
				<div class="copyright">
					<div class="copy-text object">
						Created by
						<a style="color:#D0D1D4;" > StuDevs</a>
					</div>
				</div>
			</div>

		</div>

		<!-- SCRIPT -->
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.scrollTo.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.localScroll.min.js"></script>
		<script type="text/javascript" src="scripts/jquery-animate-css-rotate-scale.js"></script>
		<script type="text/javascript" src="scripts/fastclick.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.animate-colors-min.js"></script>
		<script type="text/javascript" src="scripts/jquery.animate-shadow-min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<script type="text/javascript" src="scripts/main.js"></script>
		<script type="text/javascript" src="scripts/datepicker-hr.js"></script>
		<script type="text/javascript">
			$(function() {
        $.datepicker.setDefaults($.datepicker.regional['hr']);
				$( "#datumRod" ).datepicker({ dateFormat: 'dd mm yy', changeYear: true, changeMonth: true, yearRange: "1900:2017" });
			});
		</script>

	</body>
</html>