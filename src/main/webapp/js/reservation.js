
function loadRoutes() {
    var selectBox = document.getElementById("locationSelect");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;

    var url = '';
    if (url.indexOf('?') > -1) {
        url += '&locationId=' + selectedValue
    } else {
        url += '?locationId=' + selectedValue
    }
    window.location.href = url;
}

function loadJourneys() {
    var selectBox = document.getElementById("routeSelect");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;

    window.location.href = '?routeId=' + selectedValue;
}

console.log("Hello world");
