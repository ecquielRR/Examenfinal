<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${poema.title}"/></title>
    <%-- Incluye el fragmento de cabecera --%>
    <jsp:include page="_header.jspf" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <div class="container">
        <div class="poem-detail-card">
            <h1 class="text-4xl font-extrabold text-gray-900 mb-4"><c:out value="${poema.title}"/></h1>
            <p class="text-gray-600 text-lg mb-6">Por <span class="font-semibold"><c:out value="${poema.author}"/></span></p>
            <hr class="mb-6 border-gray-300">
 
            <div class="poem-detail-content"><c:out value="${poema.content}"/></div>

            <p class="poem-detail-meta">
                Publicado el <fmt:formatDate value="${poema.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
            </p>

            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/poemas" class="btn-secondary">
                    <i class="fas fa-arrow-left mr-2"></i> Volver a la Lista
                </a>
                <form action="${pageContext.request.contextPath}/eliminar/poema/<c:out value='${poema.id}'/>" method="post"
                      onsubmit="return confirm('¿Estás seguro de que quieres eliminar este poema? Esta acción no se puede deshacer.');">
                    <button type="submit" class="btn-danger">
                        <i class="fas fa-trash-alt mr-2"></i> Eliminar Poema
                    </button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>