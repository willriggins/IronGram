function getPhotos() {
    $.ajax({
        "type": "GET",
        "url": "/photos",
        "success": function(data) {
            for (var i in data) {
                var elem = $("<img>");
                elem.attr("src", "photos/" + data[i].filename);
                $("#photos").append(elem);
            }
        }
    })
}

function login() {
    var data = {
        "name": $("#username").val(),
        "password": $("#password").val()
    };

    $.ajax({
        "type": "POST",
        "data": JSON.stringify(data),
        "contentType": "application/json",
        "url": "/login",
        "success": function() {
            $("#notLoggedIn").hide();
            $("#loggedIn").show();
            getPhotos();
        }
    });
}

$("#loggedIn").hide();