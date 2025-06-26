<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login y Registro</title>
<link rel="stylesheet" href="/css/styles.css">
</head>
<body>
	<div class="registro">
	<h1>Registrarse</h1>
		<form:form action="/procesa/registro" method="POST" modelAttribute="usuario" >
			<div class="labels">
				<form:label path="nombreUsuario">Nombre de Usuario: </form:label>
				<form:input path="nombreUsuario" />
				<form:errors path="nombreUsuario" />
			</div>
			<div class="labels">
				<form:label path="correo">Correo: </form:label>
				<form:input path="correo" />
				<form:errors path="correo" />
			</div>
			<div class="labels">
				<form:label path="nombre">Nombre: </form:label>
				<form:input path="nombre" />
				<form:errors path="nombre" />
			</div>
			<div class="labels">
				<form:label path="apellido">Apellido: </form:label>
				<form:input path="apellido" />
				<form:errors path="apellido" />
			</div>
			<div class="labels">
				<form:label path="fechaNacimiento">Fecha de Nacimiento: </form:label>
				<form:input path="fechaNacimiento" placeholder="yyyy-mm-dd" />
				<form:errors path="fechaNacimiento" />
			</div>
			<div class="labels">
				<form:label path="contraseña">Contraseña: </form:label>
				<form:input path="contraseña" type="password"/>
				<form:errors path="contraseña" />
			</div>
			<div class="labels">
				<form:label path="confirmarContraseña">Confirmar Contraseña: </form:label>
				<form:input path="confirmarContraseña" type="password" />
				<form:errors path="confirmarContraseña" />
			</div>
			<div>
			<button> Registrarse </button>
			</div>
		</form:form>
	</div>
	<div class="login">
	<h1>Acceder</h1>
		<form:form action="/procesa/login" method="POST" modelAttribute="usuarioLogin">
			<div class="labels">
				<form:label path="nombreUsuario">Usuario: </form:label>
				<form:input path="nombreUsuario" />
				<form:errors path="nombreUsuario" />
			</div>
			<div class="labels">
				<form:label path="contraseña">Contraseña: </form:label>
				<form:input path="contraseña" type="password"/>
				<form:errors path="contraseña" />
			</div>
			<div>
			<button> Acceder </button>
			</div>
		</form:form>
	</div>
</body>
</html>