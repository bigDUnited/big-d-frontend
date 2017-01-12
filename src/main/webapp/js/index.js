
function isInt(value) {
    return !isNaN(value) &&
            parseInt(Number(value)) == value &&
            !isNaN(parseInt(value, 10));
}

function getReservation(type) {
    if (event.keyCode === 13 || "click" === type) {
        var selectedId = document.getElementById("user-reservation-id-input").value;

        if (isInt(selectedId)) {
            console.log("selectedId : ", selectedId);
            var getReservationLinkElem = document.getElementById("get-reservation-link").dataset.path;
            window.location.href = window.location.href.replace("/home", getReservationLinkElem + "?reservationId=" + selectedId);
        } else {
            var errorMessageElem = document.getElementById("error-message");
            errorMessageElem.innerHTML = "Error: Not an integer, skip!";
            errorMessageElem.style.display = "block";
        }
    }
}

function createReservation() {
    var createReservationLinkElem = document.getElementById("create-reservation-link").dataset.path;
    window.location.href = window.location.href.replace("/home", createReservationLinkElem);

}

function load() {
    console.log("Index js loaded...");
}
