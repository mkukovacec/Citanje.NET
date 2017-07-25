<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Dodaj djelo</title>
		<link rel="icon" type="image/png" href="/Citanje.NET/avatars/book-icon.png">
		<!-- Behavioral Meta Data -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	  	<link href='http://fonts.googleapis.com/css?family=Roboto:400,900,900italic,700italic,700,500italic,400italic,500,300italic,300' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
		<link href='style/css/style.css' rel='stylesheet' type='text/css'>

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
					<form action="spremiDjelo" method="post">
						<fieldset>
							<div class="polja">
								Naslov
								<input class="form-control prijava-polje" type="text" name="naslov"  value="${zapis.naslov}" >
								<c:if test="${zapis.imaPogresku('naslov')}">
								<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('naslov')}"/></p>
								</c:if>
							</div>

							<div class="polja">
								Ime autora
								<input class="form-control prijava-polje" type="text" name="imeAutora"  value="${zapis.imeAutora}" id="imeAutora">
								<c:if test="${zapis.imaPogresku('imeAutora')}">
								<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('imeAutora')}"/></p>
								</c:if>
							</div>

							<div class="polja">
								Prezime autora
								<input class="form-control prijava-polje" type="text" name="prezimeAutora" value="${zapis.prezimeAutora}" id="prezimeAutora">
								<c:if test="${zapis.imaPogresku('prezimeAutora')}">
								<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('prezimeAutora')}"/></p>
								</c:if>
							</div>

							<div class="polja">
								Žanr
								<input class="form-control prijava-polje" type="text" name="zanr"  value="${zapis.zanr}" >
								<c:if test="${zapis.imaPogresku('zanr')}">
								<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('zanr')}"/></p>
								</c:if>
							</div>

							<div class="polja">
								Godina izdanja
								<input class="form-control prijava-polje" type="text" name="godinaIzdanja"  value="${zapis.godinaIzdanja}" >
								<c:if test="${zapis.imaPogresku('godinaIzdanja')}">
								<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('godinaIzdanja')}"/></p>
								</c:if>
							</div>

							<div class="polja">
								Kratak sadržaj
								<textarea class="form-control kratak-sadrzaj" rows="10" cols="10" name="kratakSadrzaj">${zapis.kratakSadrzaj}</textarea>
								<c:if test="${zapis.imaPogresku('kratakSadrzaj')}">
								<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('kratakSadrzaj')}"/></p>
								</c:if>
							</div>

							<div class="polja" id="biografija">
								Biografija autora
								<textarea class="form-control biografija" rows="10" cols="10" name="biografija">${zapis.biografija}</textarea>
								<c:if test="${zapis.imaPogresku('biografija')}">
								<p class="pogreska"><c:out value="${zapis.dohvatiPogresku('biografija')}"/></p>
								</c:if>
							</div>

							<input class="btn btn-primary polja" type="submit" name="metoda" value="Pohrani">
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
		<script type="text/javascript" src="scripts/main.js"></script>
		<script type="text/javascript">
			var onKeyup = function () {
				$.ajax({
					dataType: "json",
					url: "/Citanje.NET/postojiAutor",
					method: "GET",
					data: {
						ime: $("#imeAutora").val(),
						prezime: $("#prezimeAutora").val()
					},

					success: function(data) {
						console.log(data);
						if (data.imaAutor) {
							$("#biografija").hide();
						} else {
							$("#biografija").show();
						}
					},

					error: function(xhr) {
						$("#biografija").show();
					}
				});
			};

			$(function() {
				var timer;

				$("#imeAutora").keyup(function() {
					timer && clearTimeout(timer);
					timer = setTimeout(onKeyup, 300);
				});
			});

			$(function() {
				var timer;

				$("#prezimeAutora").keyup(function() {
					timer && clearTimeout(timer);
					timer = setTimeout(onKeyup, 300);
				});
			});
		</script>
<!--

//-->
	</body>
</html>