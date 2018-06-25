$(function () {
    $("#btn").click(function () {
        $.ajax({
            type: "POST",
            url: "home",
            dataType: "JSON",
            success: function (games) {
                $('#result').empty()
                $('#result').html("your previous games <br/>")
                for (var i = 0; i < games.length; i++) {
                    $('#result').append("secret number: " + games[i].secretNum)
                    $('#result').append("<ul></ul>")
                    for (var l = 0; l < games[i].list.length; l++) {
                        $('#result ul:last-child').append('<li> your attempt: ' + games[i].list[l].attempt + '</li>')
                    }
                }
            },
            error: function (error) {
                console.log(error)
            }
        });
    })
})