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
        form.submit();
    };

    document.getElementById("deleteContactsButton").onclick = function() {
        var chosenCheckBox = table.querySelectorAll(':checked');
        if (chosenCheckBox && chosenCheckBox.length > 0) {
            var checkDeleting = confirm("Действительно удалить выбранные номера?");
            if (checkDeleting) {
                var form = document.getElementById("contactsForm");
                form.setAttribute("action", this.getAttribute("href"));
                form.submit();
            }
        }
        else
            alert("Пожалуйста, выберите контакты для удаления!");
    };
});