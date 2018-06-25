<!DOCTYPE>
<html>
<head>
    <title>Play game</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">    <link rel="stylesheet" type="text/css" href="/static/css/game.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/static/js/main.js"></script>
</head>
<body>
<main>
<div class="game line">
    <div class="field" contenteditable="false"></div>
    <div class="keyboard">
        <table>
            <tr>
                <td>
                    <div class="number">1</div>
                </td>
                <td>
                    <div class="number">2</div>
                </td>
                <td>
                    <div class="number">3</div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="number">4</div>
                </td>
                <td>
                    <div class="number">5</div>
                </td>
                <td>
                    <div class="number">6</div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="number">7</div>
                </td>
                <td>
                    <div class="number">8</div>
                </td>
                <td>
                    <div class="number">9</div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="number delete control">&lArr;</div>
                </td>
                <td>
                    <div class="number">0</div>
                </td>
                <td>
                    <div class="number send control">&#10004;</div>
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="result line">
    <div class="attempts">
        <ul></ul>
    </div>
    <div class="manga">
    </div>
</div>
    <h1 id="alert"></h1>
</main>
</body>
</html>