
function goFront() {
    var subString = window.location.href.substring(0, window.location.href.indexOf("/getReservation"));
    window.location.href = subString;
}

function goBack() {
    window.history.back();
}
