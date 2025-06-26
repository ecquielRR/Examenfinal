<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión</title>
    <%-- Incluye tus estilos CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <!-- Google Font - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
    <div class="form-card"> <%-- Reutiliza la clase form-card para el contenedor principal --%>
        <h2 class="text-3xl font-extrabold text-center text-gray-900 mb-8">Iniciar Sesión</h2>

        <%-- Mensajes de éxito y error --%>
        <c:if test="${not empty loginError}">
            <div class="alert alert-error">
                <span class="block sm:inline"><c:out value="${loginError}"/></span>
            </div>
        </c:if>
        <c:if test="${not empty logoutMessage}">
            <div class="alert alert-success">
                <span class="block sm:inline"><c:out value="${logoutMessage}"/></span>
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                <span class="block sm:inline"><c:out value="${successMessage}"/></span>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <span class="block sm:inline"><c:out value="${errorMessage}"/></span>
            </div>
        </c:if>

        <%-- Formulario de inicio de sesión --%>
        <form:form action="${pageContext.request.contextPath}/procesar/login" method="post" modelAttribute="login">
            <div class="campo">
                <label for="correo">Correo Electrónico:</label>
                <form:input type="email" path="correo" id="correo" placeholder="tu@ejemplo.com"
                       class="form-input" required="true" autocomplete="email"/>
                <form:errors path="correo" cssClass="error-message"/>
                <%-- Errores globales, como "correo no existente" --%>
                <c:if test="${not empty login.globalErrors}">
                    <c:forEach items="${login.globalErrors}" var="error">
                        <p class="error-message"><c:out value="${error.defaultMessage}"/></p>
                    </c:forEach>
                </c:if>
            </div>
            <div class="campo">
                <label for="contrasena">Contraseña:</label>
                <form:password path="contrasena" id="contrasena" placeholder="********"
                       class="form-input" required="true" autocomplete="current-password"/>
                <form:errors path="contrasena" cssClass="error-message"/>
            </div>
            <button type="submit" class="btn-form">
                Iniciar Sesión
            </button>
        </form:form>
        <p class="text-center text-gray-600 text-sm mt-6">
            ¿No tienes una cuenta? <a href="${pageContext.request.contextPath}/registro" class="text-blue-600 hover:text-blue-800 font-semibold transition duration-200">Regístrate aquí</a>
        </p>
    </div>
</body>
</html>