<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Usuario</title>
    <%-- Enlaza tus estilos CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <!-- Google Font - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
    <div class="form-card"> <%-- Reutiliza la clase form-card para el contenedor principal --%>
        <h2 class="text-3xl font-extrabold text-center text-gray-900 mb-8">Registrarse</h2>

        <%-- Mensajes de error generales (como los de RedirectAttributes) --%>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <span class="block sm:inline"><c:out value="${errorMessage}"/></span>
            </div>
        </c:if>

        <%-- Formulario de registro --%>
        <form:form action="${pageContext.request.contextPath}/procesar/registro" method="post" modelAttribute="registro">
            <div class="campo">
                <label for="nombre">Nombre:</label>
                <form:input type="text" path="nombre" id="nombre" placeholder="Tu Nombre"
                       class="form-input" autocomplete="name"/>
                <form:errors path="nombre" cssClass="error-message"/>
            </div>
            <div class="campo">
                <label for="apellido">Apellido:</label>
                <form:input type="text" path="apellido" id="apellido" placeholder="Tu Apellido"
                       class="form-input" autocomplete="family-name"/>
                <form:errors path="apellido" cssClass="error-message"/>
            </div>
            <div class="campo">
                <label for="correo">Correo Electrónico:</label>
                <form:input type="email" path="correo" id="correo" placeholder="tu@ejemplo.com"
                       class="form-input" autocomplete="email"/>
                <form:errors path="correo" cssClass="error-message"/>
                <%-- CORRECCIÓN: Para errores globales, usa form:errors con path="*".
                     Spring MVC lo asocia automáticamente con el BindingResult. --%>
                <form:errors path="*" cssClass="error-message"/>
            </div>
            <div class="campo">
                <label for="contrasena">Contraseña:</label>
                <form:password path="contrasena" id="contrasena" placeholder="********"
                       class="form-input" autocomplete="new-password"/>
                <form:errors path="contrasena" cssClass="error-message"/>
            </div>
            <div class="campo">
                <label for="confirmarContraseña">Confirmar Contraseña:</label>
                <form:password path="confirmarContraseña" id="confirmarContraseña" placeholder="********"
                       class="form-input" autocomplete="new-password"/>
                <form:errors path="confirmarContraseña" cssClass="error-message"/>
            </div>
            <button type="submit" class="btn-form">
                Registrarse
            </button>
        </form:form>
        <p class="text-center text-gray-600 text-sm mt-6">
            ¿Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/login" class="text-blue-600 hover:text-blue-800 font-semibold transition duration-200">Inicia sesión aquí</a>
        </p>
    </div>
</body>
</html>