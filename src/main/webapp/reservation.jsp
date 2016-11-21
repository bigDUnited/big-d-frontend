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
        <div>
            <h1>Select Location:</h1>
            <select id="locationSelect" onchange="loadRoutes();">
                <c:forEach var="location" items="${locations}">
                    <option value="${location.locationId}">${location.locationName}</option>
                </c:forEach>
            </select>
        </div>
        <c:if test="${not empty routes}">
            <div>
                <h1>Select Route</h1>
                <select id="routeSelect" onchange="loadJourneys();">
                    <c:forEach var="route" items="${routes}">
                        <option value="${route.routeId}">${route.departureLocation} - ${route.destinationLocation} </option>
                    </c:forEach>
                </select>
            </div>
        </c:if>
        <c:if test="${not empty journeys}">
            <div>
                <h1>Select Journey</h1>
                <div>Departure: ${journeys.departureLocation}</div>
                <div>Destination: ${journeys.destinationLocation}</div>
                <select id="journeySelect">
                    <c:forEach var="journey" items="${journeys.journeysList}">
                        <option value="${journey.journeyId}">[ ${journey.departureDate} > ${journey.arrivalDate} ] - ${journey.ferryName}</option>
                    </c:forEach>
                </select>
                <div>
                    <span>Number of people : </span>
                    <input id="numOfPeople" type="number" value="1" min="1" max="5"/>
                </div>
                <div>
                    <span>Vehicle Type: </span>
                    <select id="carSelect">
                        <option value="car">Car</option>
                        <option value="bus">Bus</option>
                        <option value="marek">Marek</option>
                    </select>
                </div>
                <button onclick="createReservation();">Create Reservation</button>
            </div>
        </c:if>
    </body>
</html>
