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
                <p id="create-reservation-link" data-path="${createReservationPath}" onclick="createReservation()">Create a new Reservation</p>
            </div>
            <div class="get-reservation-wrapper">
                <p id="get-reservation-link" data-path="${getReservationPath}" onclick="getReservation('click')">Get a new Reservation by ID</p>
                <input id="user-reservation-id-input" onkeydown="getReservation('enter')">
            </div>
        </div>
    </body>
</html>
