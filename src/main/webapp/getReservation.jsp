<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get Reservation</title>

        <link rel="stylesheet" href="css/getReservation.css" />
        <link rel="shortcut icon" href="favicon.ico" />

        <script src="js/getReservation.js"></script>
    </head>
    <body>
        <button class="back-btn" onclick="goFront()">Back to Home</button>
        <button class="back-btn" onclick="goBack()">Revert</button>
        <c:choose>
            <c:when test="${empty errorObject}">
                <c:if test="${not empty reservation}">
                    <h2>Hello, your current reservation is:</h2>
                    <div class="stage-wrapper reservation-summary-wrapper">
                        <div>
                            <span class="reservation-label">Reference Number:</span>
                            <span class="reservation-content">${reservation.referenceNumber}</span>
                        </div>
                        <div>
                            <span class="reservation-label">Ferry name:</span>
                            <span class="reservation-content">${reservation.ferryName}</span>
                        </div>
                        <div>
                            <span class="reservation-label">Departure Location:</span>
                            <span class="reservation-content">${reservation.departureLocation}</span>
                        </div>
                        <div>
                            <span class="reservation-label">Destination Location:</span>
                            <span class="reservation-content">${reservation.destinationLocation}</span>
                        </div>
                        <div>
                            <span class="reservation-label">Departure Date:</span>
                            <span class="reservation-content">${reservation.departureDate}</span>
                        </div>
                        <div>
                            <span class="reservation-label">Arrival Date:</span>
                            <span class="reservation-content">${reservation.arrivalDate}</span>
                        </div>
                        <div>
                            <span class="reservation-label">Number of people:</span>
                            <span class="reservation-content">${reservation.numberOfPeople}</span>
                        </div>
                        <div>
                            <span class="reservation-label">Vehicle type:</span>
                            <span class="reservation-content">${reservation.vehicleType}</span>
                        </div>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <span class="error-message">Error: ${errorObject}</span>
            </c:otherwise>
        </c:choose>
    </body>
</html>
