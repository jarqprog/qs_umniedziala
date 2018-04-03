function changeLogo() {
    // not used yet
    if (document.getElementById("imgClickAndChange").src == "./img/logo.jpg") {
        document.getElementById("imgClickAndChange").src = "./img/bean.jpg";
    } else {
        document.getElementById("imgClickAndChange").src = "./img/logo.jpg";
    }
}

function displayUser() {
    var toDisplay = "user online: ";
    var user = "Konrad Gadzina";
    if(user == 'undefined') {
        user = "n/a";
    }
    document.write(toDisplay + user);
}

function displayCalendar() {

    // implement function!
    var toDisplay = "2018-04-05 15:24";
    document.write(toDisplay);
}

function displayLoginButton() {
    // implement function!
    // if user isn't logged: "login" else: "logout"

    var toDisplay = "logout";
    document.write(toDisplay);
}
