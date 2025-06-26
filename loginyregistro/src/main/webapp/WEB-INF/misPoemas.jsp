<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Poemas</title>
    <%-- Incluye el fragmento de cabecera --%>
    <jsp:include page="_header.jspf" />
    <%-- Enlaza tus estilos CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <!-- Google Font - Inter -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <!-- Font Awesome para íconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <div class="container">
        <h1 class="main-title">Mis Poemas</h1>

        <%-- Mensajes de éxito y error --%>
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

        <c:choose>
            <c:when test="${empty listaDePoemas}">
                <div class="text-center text-gray-600 text-lg py-10">
                    <p>Aún no has creado ningún poema. ¡Empieza ahora!</p>
                    <a href="${pageContext.request.contextPath}/agregar/poema" class="mt-6 inline-block btn-primary">
                        Añadir Nuevo Poema
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    <c:forEach var="poema" items="${listaDePoemas}">
                        <div class="poem-card">
                            <div>
                                <h3 class="text-2xl font-bold text-gray-900 mb-2">
                                    <a href="${pageContext.request.contextPath}/detalle/poema/<c:out value='${poema.id}'/>" class="text-blue-700 hover:text-blue-900 transition duration-200">
                                        <c:out value="${poema.title}"/>
                                    </a>
                                </h3>
                                <p class="text-gray-600 mb-4">Por <span class="font-medium"><c:out value="${poema.author}"/></span></p>
                                <div class="poem-content-preview"><c:out value="${poema.content}"/></div>
                            </div>
                            <div class="card-actions">
                                <a href="${pageContext.request.contextPath}/editar/poema/<c:out value='${poema.id}'/>"
                                   class="text-blue-600 hover:text-blue-800 transition duration-200 text-xl"
                                   title="Editar Poema">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <form action="${pageContext.request.contextPath}/eliminar/poema/<c:out value='${poema.id}'/>" method="post"
                                      onsubmit="return confirm('¿Estás seguro de que quieres eliminar este poema? Esta acción no se puede deshacer.');">
                                    <button type="submit"
                                            class="text-red-600 hover:text-red-800 transition duration-200 text-xl"
                                            title="Eliminar Poema">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="flex justify-center mt-8">
                    <a href="${pageContext.request.contextPath}/agregar/poema" class="btn-primary">
                        Añadir Nuevo Poema
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>