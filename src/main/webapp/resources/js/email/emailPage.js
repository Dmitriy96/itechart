document.addEventListener('DOMContentLoaded', function() {

    setSelectedTemplate();

    function setSelectedTemplate() {
        var chosenValue = document.getElementById("chosenValue").value;
        if (chosenValue) {
            document.getElementById("template").value = chosenValue;
            document.getElementById("defaultInputBlock").classList.add("hidden");
            document.getElementById("chosenTemplate").classList.remove("hidden");
        }
    }
    function validate() {
        if (!document.getElementById("recipients").value) {
            return false;
        }
        if (!document.getElementById("subject").value) {
            return false;
        }
        return true;
    }
    document.getElementById("sendButton").onclick = function() {
        if (validate()) {
            var emailForm = document.getElementById("emailForm");
            emailForm.setAttribute("action", this.getAttribute("data-url"));
            emailForm.submit();
        }
    };
    document.getElementById("backButton").onclick = function() {
        window.history.back();
    };
    document.getElementById("template").onchange = function() {
        var emailForm = document.getElementById("emailForm");
        emailForm.setAttribute("action", this.getAttribute("data-url"));
        emailForm.submit();
    };
});