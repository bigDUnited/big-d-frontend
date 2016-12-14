<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
        <title>Create Reservation</title>

        <link rel="stylesheet" href="css/reservation.css" />
        <link rel="shortcut icon" href="favicon.ico" />

        <script src="js/reservation.js"></script>
    </head>
    <body onload="load();">
        <div class="header-wrapper">
            <span class="header-title">Create a Reservation:</span>
            <span class="header-description">Welcome to our ferry reservation system. Making a reservation is 
                really easy! Just choose a location, route and journey and click the 
                Reserve button. After that you will be taken to a reservation summary 
                page that will show all your details</span>
        </div>
        <div class="main-content-wrapper">
            <c:choose>
                <c:when test="${empty errorObject}">
                    <div class="stage-wrapper">
                        <label for="locationSelect">Choose location:</label>
                        <select id="locationSelect" onchange="loadRoutes();">
                            <option selected="selected" value="-1">Select Location</option>
                            <c:forEach var="location" items="${locations}">
                                <option value="${location.locationId}">${location.locationName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <hr>
                    <c:if test="${not empty routes}">
                        <div class="stage-wrapper">
                            <label for="routeSelect">Choose route:</label>
                            <select id="routeSelect" onchange="loadJourneys();">
                                <option selected="selected" value="-1">Select Route</option>
                                <c:forEach var="route" items="${routes}">
                                    <option value="${route.routeId}">${route.departureLocation} - ${route.destinationLocation} </option>
                                </c:forEach>
                            </select>
                        </div>
                        <hr>
                    </c:if>
                    <c:if test="${not empty journeys}">
                        <div class="stage-wrapper">
                            <div>
                                <span class="label-like">Departure:</span>
                                <span class="selected-like">${journeys.departureLocation}</span>
                            </div>
                            <div>
                                <span class="label-like">Destination:</span>
                                <span class="selected-like">${journeys.destinationLocation}</span>
                            </div>
                            <div>
                                <label for="journeySelect">Choose journey:</label>
                                <select id="journeySelect">
                                    <option value="-1">Select Journey</option>
                                    <c:forEach var="journey" items="${journeys.journeysList}">
                                        <option value="${journey.journeyId}">[ ${journey.departureDate} > ${journey.arrivalDate} ] - ${journey.ferryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div>
                                <label for="numOfPeople">Choose number of people (1-5):</label>
                                <input id="numOfPeople" type="number" value="1" min="1" max="5"/>
                            </div>
                            <div>
                                <label for="carSelect">Choose vehicle type</label>
                                <select id="carSelect">
                                    <option value="-1">Select Vehicle Type</option>
                                    <option value="car">Car</option>
                                    <option value="bus">Bus</option>
                                    <option value="truck">Truck</option>
                                    <option value="airplane">Airplane</option>
                                    <option value="spaceship">Space Ship</option>
                                </select>
                            </div>
                            <hr>
                            <button class="create-reservation-btn" onclick="createReservation();">Create Reservation</button>
                        </div>
                        <hr>
                    </c:if>
                    <c:if test="${not empty summary}">
                        <div class="stage-wrapper reservation-summary-wrapper">
                            <h2>Successful Reservation!</h2>
                            <div>
                                <span class="reservation-label">Reference Number:</span>
                                <span class="reservation-content">${summary.referenceNumber}</span>
                            </div>
                            <div>
                                <span class="reservation-label">Ferry name:</span>
                                <span class="reservation-content">${summary.ferryName}</span>
                            </div>
                            <div>
                                <span class="reservation-label">Departure Location:</span>
                                <span class="reservation-content">${summary.departureLocation}</span>
                            </div>
                            <div>
                                <span class="reservation-label">Destination Location:</span>
                                <span class="reservation-content">${summary.destinationLocation}</span>
                            </div>
                            <div>
                                <span class="reservation-label">Departure Date:</span>
                                <span class="reservation-content">${summary.departureDate}</span>
                            </div>
                            <div>
                                <span class="reservation-label">Arrival Date:</span>
                                <span class="reservation-content">${summary.arrivalDate}</span>
                            </div>
                            <div>
                                <span class="reservation-label">Number of people:</span>
                                <span class="reservation-content">${summary.numberOfPeople}</span>
                            </div>
                            <div>
                                <span class="reservation-label">Vehicle type:</span>
                                <span class="reservation-content">${summary.vehicleType}</span>
                            </div>
                            <h2>Thank you for using our reservation system :)</h2>
                        </div>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <span>Error: ${errorObject}</span>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
