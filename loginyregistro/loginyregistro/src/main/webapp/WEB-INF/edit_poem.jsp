<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Poema</title>
    <jsp:include page="_header.jspf" />
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f3f4f6;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 2rem 1rem;
        }
        .form-card {
            background-color: #fff;
            border-radius: 0.75rem;
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
            padding: 2rem;
        }
        .form-input {
            appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200
        }
        .btn-primary {
            background-color: #3b82f6;
            color: #ffffff;
            padding: 0.75rem 1.5rem;
            border-radius: 0.5rem;
            font-weight: 600;
            text-align: center;
            transition: all 0.3s ease-in-out;
        }
        .btn-primary:hover {
            background-color: #2563eb;
            transform: translateY(-2px);
        }
        .btn-secondary {
            background-color: #6b7280;
            color: #ffffff;
            padding: 0.75rem 1.5rem;
            border-radius: 0.5rem;
            font-weight: 600;
            text-align: center;
            transition: all 0.3s ease-in-out;
        }
        .btn-secondary:hover {
            background-color: #4b5563;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-4xl font-extrabold text-gray-900 mb-8 text-center">Editar Poema</h1>

        <div class="form-card">
            <form:form action="${pageContext.request.contextPath}/editar/poema/${poema.id}" method="post" modelAttribute="poema"> <%-- CORREGIDO: action y modelAttribute --%>
                <form:hidden path="id" /> <%-- Campo oculto para el ID del poema --%>
                <div class="mb-5">
                    <label for="title" class="block text-gray-700 text-sm font-semibold mb-2">Título</label>
                    <form:input type="text" path="title" id="title" placeholder="Título del poema"
                               class="form-input"/>
                    <form:errors path="title" cssClass="text-red-500 text-xs mt-1"/>
                </div>
                <div class="mb-5">
                    <label for="author" class="block text-gray-700 text-sm font-semibold mb-2">Autor</label>
                    <form:input type="text" path="author" id="author" placeholder="Nombre del autor"
                               class="form-input"/>
                    <form:errors path="author" cssClass="text-red-500 text-xs mt-1"/>
                </div>
                <div class="mb-6">
                    <label for="content" class="block text-gray-700 text-sm font-semibold mb-2">Contenido</label>
                    <form:textarea path="content" id="content" rows="10" placeholder="Escribe el contenido del poema aquí..."
                                  class="form-input resize-y"/>
                    <form:errors path="content" cssClass="text-red-500 text-xs mt-1"/>
                </div>
                <div class="flex justify-end space-x-4">
                    <a href="${pageContext.request.contextPath}/poemas" class="btn-secondary shadow-md">Cancelar</a> <%-- CORREGIDO: /poemas --%>
                    <button type="submit" class="btn-primary shadow-md">Actualizar Poema</button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
