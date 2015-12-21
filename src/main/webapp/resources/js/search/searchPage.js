document.addEventListener('DOMContentLoaded', function() {

    document.getElementById("cancelButton").onclick = function() {
        window.history.back();
    };
    document.getElementById("searchButton").onclick = function() {
        var inputElements = document.getElementsByTagName("input");
        for (var i = 0; i < inputElements.length; i++) {
            if (inputElements[i].value) {
                document.getElementById("searchForm").submit();
            }
        }
        var selectElements = document.getElementsByTagName("select");
        for (i = 0; i < selectElements.length; i++) {
            if (selectElements[i].value != "NONE") {
                document.getElementById("searchForm").submit();
            }
        }
        document.getElementById("emptyFieldsError").classList.remove("hidden");
        return false;
    };
});