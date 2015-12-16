document.addEventListener('DOMContentLoaded', function() {

    resolvePagination();

    document.getElementById("editContactButton").onclick = function () {
        location.href = this.getAttribute("data-url");
    };
    document.getElementById("contactsButton").onclick = function () {
        location.href = this.getAttribute("data-url");
    };
    document.getElementById("previousPage").onclick = function() {
        if (!this.classList.contains("disabled")) {

        }
    };
    function resolvePagination() {
        var hasNext = document.getElementById("hasNext").value;
        var hasPrevious = document.getElementById("hasPrevious").value;
        if (hasNext == "true") {
            document.getElementById("previousPage").classList.remove("disabled");
        }
        if (hasPrevious == "true")
            document.getElementById("previousPage").classList.remove("disabled");
    }
});