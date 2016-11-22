<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <script src="js/reservation.js"></script>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservation</title>
        <link rel="shortcut icon" href="favicon.ico" />
    </head>
    <body onload="load();">
        <c:choose>
            <c:when test="${empty errorObject}">
                <div>
                    <h1>Select Location:</h1>
                    <select id="locationSelect" onchange="loadRoutes();">
                        <option selected="selected" value="-1">Select Location</option>
                        <c:forEach var="location" items="${locations}">
                            <option value="${location.locationId}">${location.locationName}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:if test="${not empty routes}">
                    <div>
                        <h1>Select Route</h1>
                        <select id="routeSelect" onchange="loadJourneys();">
                            <option selected="selected" value="-1">Select Route</option>
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
                            <option value="-1">Select Journey</option>
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
                                <option value="-1">Select Vehicle Type</option>
                                <option value="car">Car</option>
                                <option value="bus">Bus</option>
                                <option value="truck">Truck</option>
                                <option value="airplane">Airplane</option>
                                <option value="spaceship">Space Ship</option>
                            </select>
                        </div>
                        <button onclick="createReservation();">Create Reservation</button>
                    </div>
                </c:if>
                <c:if test="${not empty summary}">
                    <div>
                        <h2>Successful Reservation!</h2>
                        <h3>Reference Number: ${summary.referenceNumber}</h3>
                        <h3>Ferry name: ${summary.ferryName}</h3>
                        <h3>Departure Location: ${summary.departureLocation}</h3>
                        <h3>Destination Location: ${summary.destinationLocation}</h3>
                        <h3>Departure Date: ${summary.departureDate}</h3>
                        <h3>Arrival Date ${summary.arrivalDate}</h3>
                        <h3>Number of people: ${summary.numberOfPeople}</h3>
                        <h3>Vehicle type: ${summary.vehicleType}</h3>
                        <span>Error is not possible for the current version of the backend mock - fix/ and then fix the logic here</span>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <span>Error: ${errorObject}</span>
            </c:otherwise>
        </c:choose>
    </body>
</html>
