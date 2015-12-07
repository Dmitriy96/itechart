document.addEventListener('DOMContentLoaded', function(){
    var imgInput = document.getElementById('imgInput');
    imgInput.onchange = function(event) {
        var output = document.getElementById('output');
        output.src = URL.createObjectURL(event.target.files[0]);
    };
});
/*
window.onload = function() {
    var imgInput = document.getElementById('imgInput');
    imgInput.onchange = function(event) {
        var output = document.getElementById('output');
        output.src = URL.createObjectURL(event.target.files[0]);
    };
};*/
