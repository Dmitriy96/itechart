document.addEventListener('DOMContentLoaded', function() {

    var table = document.getElementById('contactsTable');
    emptyTableCheck(table);
    updateCheckboxes();
    resolvePagination();
    createCheckboxListeners();
    updateSearchAttributes();

    function emptyTableCheck(table) {
        var emptyContactsTableRow = document.getElementById('emptyContacts');
        if (table.lastElementChild.childElementCount == 0) {
            emptyContactsTableRow.classList.remove('hidden');
        }
    }

    function resolvePagination() {
        var hasNext = document.getElementById("hasNext").value;
        var hasPrevious = document.getElementById("hasPrevious").value;
        if (hasNext == "true") {
            document.getElementById("nextPage").classList.remove("disabled");
        }
        if (hasPrevious == "true") {
            document.getElementById("previousPage").classList.remove("disabled");
        }
    }

    function updateSearchAttributes() {
        var searchAttributes = document.getElementById("contactSearchAttributes").children;
        if (sessionStorage.getItem("isSearch") === null) {
            for (var i = 0; i < searchAttributes.length; i++) {
                sessionStorage.setItem(searchAttributes[i].name, searchAttributes[i].value);
            }
        } else {
            for (i = 0; i < searchAttributes.length; i++) {
                searchAttributes[i].value = sessionStorage.getItem(searchAttributes[i].name);
            }
        }
    }

    document.getElementById("nextPage").onclick = function() {
        updateCheckboxes();
        document.getElementById("isLowerIds").value = false;
        var form = document.getElementById("contactsForm");
        form.setAttribute("action", this.getAttribute("data-url"));
        form.setAttribute("method", "post");
        var startContactIdForNextPage = document.getElementById("startContactIdForNextPage").value;
        document.getElementById("startContactIdForPage").value = startContactIdForNextPage;
        form.submit();
        return false;
    };

    document.getElementById("previousPage").onclick = function() {
        updateCheckboxes();
        document.getElementById("isLowerIds").value = true;
        var form = document.getElementById("contactsForm");
        form.setAttribute("action", this.getAttribute("data-url"));
        form.setAttribute("method", "post");
        var startContactIdForPreviousPage = document.getElementById("startContactIdForPreviousPage").value;
        document.getElementById("startContactIdForPage").value = startContactIdForPreviousPage;
        form.submit();
        return false;
    };

    function updateCheckboxes() {
        var checkedContacts = sessionStorage.getItem("checkedContacts");
        if (checkedContacts !== null) {
            var contactsId = JSON.parse(checkedContacts);
            var checkboxesOfPage = document.getElementsByName("contact");
            for (var i = 0; i < checkboxesOfPage.length; i++) {
                for (var j = 0; j < contactsId.length; j++) {
                    if (checkboxesOfPage[i].value == contactsId[j]) {
                        checkboxesOfPage[i].checked = true;
                    }
                }
            }
        }
    }

    document.getElementById("sendEmailButton").onclick = function() {
        var form = document.getElementById("contactsForm");
        form.setAttribute("action", this.getAttribute("href"));
        form.setAttribute("method", "post");
        var checkedContacts = sessionStorage.getItem("checkedContacts");
        if (checkedContacts !== null) {
            var contactsId = JSON.parse(checkedContacts).join(",");
            document.getElementById("emailContactsId").value = contactsId;
        }
        form.submit();
        return false;
    };

    function createCheckboxListeners() {
        var contacts = document.getElementsByName("contact");
        for (var i = 0; i < contacts.length; i++) {
            contacts[i].onchange = function() {
                if (this.checked) {
                    if (sessionStorage.getItem("checkedContacts") === null) {
                        var checkedContacts = [];
                        checkedContacts.push(this.value);
                        sessionStorage.setItem("checkedContacts", JSON.stringify(checkedContacts));
                    } else {
                        checkedContacts = JSON.parse(sessionStorage.getItem("checkedContacts"));
                        checkedContacts.push(this.value);
                        sessionStorage.setItem("checkedContacts", JSON.stringify(checkedContacts));
                    }
                } else {
                    checkedContacts = JSON.parse(sessionStorage.getItem("checkedContacts"));
                    var index = checkedContacts.indexOf(this.value);
                    checkedContacts.splice(index, 1);
                    sessionStorage.setItem("checkedContacts", JSON.stringify(checkedContacts));
                }
            }
        }
    }

    document.getElementById("deleteContactsButton").onclick = function() {
        var checkedContacts = sessionStorage.getItem("checkedContacts");
        if (checkedContacts !== null) {
            var checkDeleting = confirm("Действительно удалить выбранные номера?");
            if (checkDeleting) {
                var form = document.getElementById("contactsForm");
                form.setAttribute("action", this.getAttribute("href"));
                form.setAttribute("method", "post");
                var contactsId = JSON.parse(checkedContacts).join(",");
                document.getElementById("deletingContactsId").value = contactsId;
                form.submit();
            }
        }
        else
            alert("Пожалуйста, выберите контакты для удаления!");
        return false;
    };

    document.getElementById("allContactList").onclick = function() {
        var searchAttributes = document.getElementById("contactSearchAttributes").children;
        for (var i = 0; i < searchAttributes.length; i++) {
            searchAttributes[i].value = "";
        }
        sessionStorage.clear();
        document.getElementById("isSearch").value = false;
        return true;
    };

    var showContactButtons = document.getElementsByName("showContact");
    var editContactButtons = document.getElementsByName("editContact");

    for (var i = 0; i < showContactButtons.length; i++) {
        showContactButtons[i].onclick = function() {
            location.href = this.getAttribute("data-url");
        };
        editContactButtons[i].onclick = function() {
            location.href = this.getAttribute("data-url");
        };
    }
});