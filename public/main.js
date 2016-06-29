function getPhotos() {
    $.ajax({
        "type": "GET",
        "url": "/photos",
        "success": function(data) {
          $("#photos").empty();
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

function logout() {
  $.ajax({
    "type": "POST",
    "url": "/logout",
    "success": function() {
      $("#notLoggedIn").show();
      $("#loggedIn").hide();
    }
  })
}

$.ajax({
  "type": "GET",
  "url": "/user",
  "success": function(data) {
    if (data) {
      $("#notLoggedIn").hide();
      getPhotos();
    }
    else {
      $("#loggedIn").hide();
    }
  }
});
