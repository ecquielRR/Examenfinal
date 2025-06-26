<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Poema</title>
    <%-- Incluye el fragmento de cabecera --%>
    <jsp:include page="_header.jspf" />
    <%-- Enlaza tus estilos CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <!-- Google Font - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1 class="main-title">Editar Poema</h1>

        
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                <c:out value="${successMessage}"/>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <c:out value="${errorMessage}"/>
            </div>
        </c:if>

        <div class="form-card">
           
            <form:form action="${pageContext.request.contextPath}/editar/poema/${poema.id}" method="post" modelAttribute="poema">
                <%-- Campo oculto para el ID del poema, necesario para la actualización --%>
                <form:hidden path="id"/>
                
                <div class="campo">
                    <label for="title">Título:</label>
                    <form:input type="text" path="title" id="title" class="form-input" placeholder="El título del poema"/>
                    <%-- Muestra errores de validación para el campo 'title' --%>
                    <form:errors path="title" cssClass="error-message"/>
                </div>
                
                <div class="campo">
                    <label for="author">Autor:</label>
                    <form:input type="text" path="author" id="author" class="form-input" placeholder="Nombre del autor"/>
                    <%-- Muestra errores de validación para el campo 'author' --%>
                    <form:errors path="author" cssClass="error-message"/>
                </div>
                
                <div class="campo">
                    <label for="content">Contenido:</label>
                    <form:textarea path="content" id="content" rows="10" class="form-input" placeholder="Escribe el contenido del poema aquí..."/>
                    <%-- Muestra errores de validación para el campo 'content' --%>
                    <form:errors path="content" cssClass="error-message"/>
                </div>
                
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/poemas" class="btn-secondary">Cancelar</a>
                    <%-- El botón de submit usará el método PUT configurado en el controlador --%>
                    <button type="submit" class="btn-primary">Actualizar Poema</button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>