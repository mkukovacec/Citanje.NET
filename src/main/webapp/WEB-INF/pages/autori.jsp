<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Popis autora</title>

    <!-- Behavioral Meta Data -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,900,900italic,700italic,700,500italic,400italic,500,300italic,300' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <link href='http:/Citanje.NET/style/css/style.css' rel='stylesheet' type='text/css'>

    <!-- Custom CSS -->
    <link href='http:/Citanje.NET/style/css/custom.css' rel='stylesheet' type='text/css'>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <link rel="icon" type="image/png" href="/Citanje.NET/avatars/book-icon.png">
  </head>
  <body>
    <a name="ancre"></a>

    <!-- CACHE -->
    <div class="cache"></div>

    <!-- HEADER -->

    <div id="wrapper-header">
      <div id="main-header" class="object">
        <div class="logo">
          <a href="/Citanje.NET/"><img src="http:/Citanje.NET/style/img/logo.png" alt="citanje-logo" id="citanje-logo"></a>
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
            <h3 class="polja">Autori:</h3>
            <ul>
              <c:forEach var="a" items="${sviAutori}">
                <li class="polja"><a href="/Citanje.NET/autor/${a.id}">${a.imeAutora} ${a.prezimeAutora}</a></li>
              </c:forEach>
            </ul>
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
    <script type="text/javascript" src="http:/Citanje.NET/scripts/jquery.scrollTo.min.js"></script>
    <script type="text/javascript" src="http:/Citanje.NET/scripts/jquery.localScroll.min.js"></script>
    <script type="text/javascript" src="http:/Citanje.NET/scripts/jquery-animate-css-rotate-scale.js"></script>
    <script type="text/javascript" src="http:/Citanje.NET/scripts/fastclick.min.js"></script>
    <script type="text/javascript" src="http:/Citanje.NET/scripts/jquery.animate-colors-min.js"></script>
    <script type="text/javascript" src="http:/Citanje.NET/scripts/jquery.animate-shadow-min.js"></script>
    <script type="text/javascript" src="http:/Citanje.NET/scripts/main.js"></script>
  </body>
</html>