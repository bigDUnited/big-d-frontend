
function findGetParameter(parameterName) {
    var result = null,
            tmp = [];
    var items = location.search.substr(1).split("&");
    for (var index = 0; index < items.length; index++) {
        tmp = items[index].split("=");
        if (tmp[0] === parameterName)
            result = decodeURIComponent(tmp[1]);
    }
    return result;
}

function setSelectValueBySelectId(selectId, selectValue) {
    var selectBox = document.getElementById(selectId);
    selectBox.value = selectValue;
}

function getSelectValueBySelectId(selectId) {
    var selectBox = document.getElementById(selectId);
    return selectBox.options[selectBox.selectedIndex].value;
}

function loadRoutes() {
    var selectedValue = getSelectValueBySelectId("locationSelect");

    if (window.location.href.indexOf("locationId") > -1) {
        window.location.href = window.location.href.split('?')[0] + '?locationId=' + selectedValue;

    } else {
        window.location.href = window.location.href + '?locationId=' + selectedValue
    }
}

function loadJourneys() {
    var selectedValue = getSelectValueBySelectId("routeSelect");

    if (window.location.href.indexOf("routeId") > -1) {
        //window.location.href = window.location.href.split('?')[0]
    } else {
        window.location.href = window.location.href + '&routeId=' + selectedValue;
    }
}

function createReservation() {
    var vehicleType = getSelectValueBySelectId("carSelect");
    var numOfPeople = document.getElementById("numOfPeople").value;
    var journeyValue = getSelectValueBySelectId("journeySelect");

    if (vehicleType && numOfPeople && journeyValue) {
        window.location.href = window.location.href + '&vehicleType=' + vehicleType + '&numOfPeople=' + numOfPeople + '&journeyId=' + journeyValue;
    }
}

function load() {
    if (window.location.href.indexOf("locationId") > -1) {
        setSelectValueBySelectId("locationSelect", findGetParameter("locationId"));
    }

    if (window.location.href.indexOf("routeId") > -1) {
        setSelectValueBySelectId("routeSelect", findGetParameter("routeId"));
    }

    if (window.location.href.indexOf("journeyId") > -1) {
        setSelectValueBySelectId("journeySelect", findGetParameter("journeyId"));
    }

    if (window.location.href.indexOf("vehicleType") > -1) {
        setSelectValueBySelectId("carSelect", findGetParameter("vehicleType"));
    }

    if (window.location.href.indexOf("numOfPeople") > -1) {
        document.getElementById("numOfPeople").value = findGetParameter("vehicleType");
    }
}