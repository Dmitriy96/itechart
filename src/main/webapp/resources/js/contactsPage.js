document.addEventListener('DOMContentLoaded', function() {

    var table = document.getElementById('contactsTable');
    emptyTableCheck(table);

    function emptyTableCheck(table) {
        var emptyContactsTableRow = document.getElementById('emptyContacts');
        if (table.lastElementChild.childElementCount == 0) {
            emptyContactsTableRow.classList.remove('hidden');
        }
    }

    document.getElementById("sendEmailButton").onclick = function() {
        var form = document.getElementById("contactsForm");
        form.setAttribute("action", this.getAttribute("href"));
        form.setAttribute("method", "post");
        form.submit();
        return false;
    };

    document.getElementById("deleteContactsButton").onclick = function() {
        var chosenCheckBox = table.querySelectorAll(':checked');
        if (chosenCheckBox && chosenCheckBox.length > 0) {
            var checkDeleting = confirm("Действительно удалить выбранные номера?");
            if (checkDeleting) {
                var form = document.getElementById("contactsForm");
                form.setAttribute("action", this.getAttribute("href"));
                form.setAttribute("method", "post");
                form.submit();
            }
        }
        else
            alert("Пожалуйста, выберите контакты для удаления!");
        return false;
    };

    var showContactButtons = document.getElementsByName("showContact");
    var editContactButtons = document.getElementsByName("editContact");
    for (var i = 0; i < showContactButtons.length; i++) {
        showContactButtons[i].onclick = function() {
            var form = document.getElementById("contactsForm");
            form.setAttribute("action", this.getAttribute("data-url"));
            form.submit();
        };
        editContactButtons[i].onclick = function() {
            var form = document.getElementById("contactsForm");
            form.setAttribute("action", this.getAttribute("data-url"));
            form.submit();
        };
    }
});