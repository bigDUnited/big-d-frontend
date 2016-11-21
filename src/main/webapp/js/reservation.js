
function getSelectValueBySelectId(selectId) {
    var selectBox = document.getElementById(selectId);
    return selectBox.options[selectBox.selectedIndex].value;
}

function loadRoutes() {
    var selectedValue = getSelectValueBySelectId("locationSelect");
    window.location.href = window.location.href + '?locationId=' + selectedValue
}

function loadJourneys() {
    var selectedValue = getSelectValueBySelectId("routeSelect");
    window.location.href = window.location.href + '&routeId=' + selectedValue;
}

function createReservation() {
    var carValue = getSelectValueBySelectId("carSelect");
    var numOfPeople = document.getElementById("numOfPeople").value;
    var journeyValue = getSelectValueBySelectId("journeySelect");
    
    if ( carValue && numOfPeople && journeyValue ){
        window.location.href = window.location.href + '&carType=' + carValue + '&numOfPeople=' + numOfPeople + '&journeyId=' + journeyValue;
    }
}
