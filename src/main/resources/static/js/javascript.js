function displayCalendar() {

    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1;
    var yyyy = today.getFullYear();

    if(dd<10) {
      dd = '0' + dd
    }

    if(mm<10) {
      mm = '0' + mm
    }

    today = dd + '-' + mm + '-' + yyyy;
    return today;
}

function welcomeUser() {
    var url = document.URL;
    var admin = "user-admin";
    var mentor = "user-mentor";
    var student = "user-student";
    var user;
    if(url.includes(admin)) {
        user = "Admin Admin";
    }
    else if(url.includes(mentor)) {
        user = "Konrad Gadzina";
    }
    else {
        user = "Artur Zborovskyy";
    }

    return user + ",<br>what do You want to do?";
}



