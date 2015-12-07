document.addEventListener('DOMContentLoaded', function(){

    var inp = document.getElementsByTagName('input');
    for (var i=0;i<inp.length;i++) {
        if (inp[i].type != 'file') continue;
        inp[i].relatedElement = inp[i].parentNode.getElementsByTagName('label')[0];
        inp[i].onchange /*= inp[i].onmouseout*/ = function () {
            this.relatedElement.innerHTML = this.value;
        };
    };
});