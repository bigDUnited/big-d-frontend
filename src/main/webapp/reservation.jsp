<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <script src="js/reservation.js"></script>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservation</title>
    </head>
    <body>
        <h1>Select Location:</h1>

        <select id="locationSelect" onchange="loadRoutes();">
            <c:forEach var="location" items="${locations}">
                <option value="${location.locationId}">${location.locationName}</option>
            </c:forEach>
        </select>
        <c:if test="${not empty routes}">
            <select id="routeSelect" onchange="loadJourneys();">
                <c:forEach var="route" items="${routes}">
                    <option value="${route.routeId}">${route.departureLocation} - ${route.destinationLocation} </option>
                </c:forEach>
            </select>
        </c:if>
    </body>
</html>
