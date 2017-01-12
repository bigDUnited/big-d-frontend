<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Big D Ferry Index</title>

        <link rel="stylesheet" href="css/index.css" />
        <link rel="shortcut icon" href="favicon.ico" />

        <script src="js/index.js"></script>
    </head>
    <body onload="load();">
        <div id="index-main-container">
            <p class="index-intro-text">${intro}</p>
            <div class="create-reservation-wrapper">
                <p>Create a new Reservation :</p>
                <a id="create-reservation-link" data-path="${createReservationPath}" onclick="createReservation()">Load</a>
            </div>
            <div class="get-reservation-wrapper">
                <p>Get a new Reservation by ID :</p>
                <input id="user-reservation-id-input" onkeydown="getReservation('enter')">
                <a id="get-reservation-link" data-path="${getReservationPath}" onclick="getReservation('click')">Load</a>
                <div>
                    <span id="error-message">Error</span>
                </div>
            </div>
            <div class="get-reservations-wrapper">
                <p>List of all current reservations :</p>
                <c:choose>
                    <c:when test="${empty errorObject}">
                        <c:if test="${not empty reservations}">
                            <div>
                                <c:forEach var="reservation" items="${reservations}">
                                    <a href="/big-d-frontend/getReservation?reservationId=${reservation.referenceNumber}">${reservation.referenceNumber};</a>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <span id="error-message-two">Error: ${errorObject}</span>
                    </c:otherwise>
                </c:choose>
            </div>
            <div>
                <a href="http://www.claimyourprize.com/notavirus.exe" target="_blank">
                    <img src="images/winner.gif">    
                </a>
            </div>
        </div>
    </body>
</html>
