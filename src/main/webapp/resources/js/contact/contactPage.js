document.addEventListener('DOMContentLoaded', function() {

    document.getElementById("editContactButton").onclick = function () {
        location.href = this.getAttribute("data-url");
    };
    document.getElementById("contactsButton").onclick = function () {
        location.href = this.getAttribute("data-url");
    };
});