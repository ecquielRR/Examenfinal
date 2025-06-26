<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todos los Poemas</title>
    <%-- Incluye el fragmento de cabecera --%>
    <jsp:include page="_header.jspf" />
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f3f4f6;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            padding: 2rem 1rem;
        }
        .card {
            background-color: #fff;
            border-radius: 0.75rem;
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }
        .button-link {
            display: inline-block;
            padding: 0.625rem 1.25rem;
            border-radius: 0.5rem;
            font-weight: 600;
            text-align: center;
            transition: all 0.3s ease-in-out;
        }
        .btn-blue {
            background-color: #3b82f6;
            color: #ffffff;
        }
        .btn-blue:hover {
            background-color: #2563eb;
            transform: translateY(-2px);
        }
        .btn-green {
            background-color: #22c55e;
            color: #ffffff;
        }
        .btn-green:hover {
            background-color: #16a34a;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-4xl font-extrabold text-gray-900 mb-8 text-center">Todos los Poemas</h1>

        <!-- Mensajes de éxito y error -->
        <c:if test="${not empty successMessage}">
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg relative mb-6" role="alert">
                <span class="block sm:inline"><c:out value="${successMessage}"/></span>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg relative mb-6" role="alert">
                <span class="block sm:inline"><c:out value="${errorMessage}"/></span>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty listaDePoemas}"> <%-- CORREGIDO: Usar listaDePoemas --%>
                <div class="text-center text-gray-600 text-lg py-10">
                    <p>Aún no hay poemas. ¡Sé el primero en añadir uno!</p>
                    <a href="${pageContext.request.contextPath}/agregar/poema" class="mt-6 inline-block btn-blue button-link">
                        Añadir Nuevo Poema
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    <c:forEach var="poema" items="${listaDePoemas}"> <%-- CORREGIDO: var a "poema" y items a "listaDePoemas" --%>
                        <div class="card flex flex-col justify-between">
                            <div>
                                <h3 class="text-2xl font-bold text-gray-900 mb-2">
                                    <a href="${pageContext.request.contextPath}/detalle/poema/<c:out value='${poema.id}'/>" class="text-blue-700 hover:text-blue-900 transition duration-200">
                                        <c:out value="${poema.title}"/>
                                    </a>
                                </h3>
                                <p class="text-gray-600 mb-4">Por <span class="font-medium"><c:out value="${poema.author}"/></span></p>
                                <p class="text-gray-700 text-base line-clamp-3 mb-4"><c:out value="${poema.content}"/></p>
                            </div>
                            <div class="flex justify-end space-x-3 mt-4">
                                <%-- Solo mostrar botones de editar/eliminar si el poema pertenece al usuario logueado --%>
                                <c:if test="${sessionScope.id_usuario != null && poema.usuario.id == sessionScope.id_usuario}">
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
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="flex justify-center mt-8">
                    <a href="${pageContext.request.contextPath}/agregar/poema" class="btn-green button-link transform hover:scale-105 shadow-lg">
                        Añadir Nuevo Poema
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>