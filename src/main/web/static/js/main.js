$(function () {
    $(".number").click(function () {
        if ($('.field').text().length < 4 && !$(this).attr('class').includes('control')) {
            $('.field').append($(this).text())
        } else {
            if ($(this).attr('class').includes('delete')) {
                $('.field').text($('.field').text().slice(0, -1))
            }
            if ($(this).attr('class').includes('send') && $('.field').text().length == 4) {
                sendAjax($('.field').text());
            }
        }
    })
    $("body").keydown(function (e) {
        l(String.fromCharCode(e.keyCode))
        if ((e.which < 58 && e.which > 47) && $('.field').text().length < 4) {
            $('.field').append(String.fromCharCode(e.which))
        }
        if (e.which == 13 && $('.field').text().length == 4) {
            sendAjax($('.field').text());
        }
        if (e.which == 8) {
            $('.field').text($('.field').text().slice(0, -1))
        }

    });
})

function sendAjax(text) {
    $(".manga").empty()
    $.ajax({
        type: "POST",
        url: "game",
        data: text,
        success: function (response) {
            $(".attempts ul").append('<li>' + response.text + "&rarr;" + response.result +'</li>')
            for (var i = 0; i < response.bulls; i++)
                $(".manga").prepend($('<img>').attr("src", '/static/pictures/bull.jpg'))
            for (var i = 0; i < response.cows; i++)
                $(".manga").prepend($('<img>').attr("src", '/static/pictures/cow.png'))
            if(response.end){
                $("#alert").text("CONGRATULATIONS! You win! ").append("<a href='game'>try again</a> or <a href= 'home'>go home<a> ")
            }
        },
        error: function (error) {
            l(error)
        }
    });
    $('.field').text('');
}

function l(text) {
    console.log(text)
}