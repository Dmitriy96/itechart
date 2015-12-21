document.addEventListener('DOMContentLoaded', function(){

    var phoneDataInputIDs = ['countryCode', 'operatorCode', 'phoneNumber', 'phoneComment', 'phoneType'];
    var attachmentDataInputIDs = ['file', 'fileName', 'attachingDate', 'attachmentComment'];
    setChosenGender();
    setChosenMaritalStatus();
    setChosenCountry();
    convertDateToReadableForm();
    setHiddenDatesToMilliseconds();


    function setChosenGender() {
        var contactGender = document.getElementById('contactGender').value;
        var gender = document.getElementById('gender');
        for (var i = 0; i < gender.options.length; i++) {
            if (gender.options[i].value == contactGender)
                gender.options[i].selected = true;
        }
    }

    function setChosenMaritalStatus() {
        var contactMaritalStatus = document.getElementById('contactMaritalStatus').value;
        var marital = document.getElementById('marital');
        for (var i = 0; i < marital.options.length; i++) {
            if (marital.options[i].value == contactMaritalStatus)
                marital.options[i].selected = true;
        }
    }

    function setChosenCountry() {
        var chosenCountry = document.getElementById('chosenCountry').value;
        var country = document.getElementById('country');
        for (var i = 0; i < country.options.length; i++) {
            if (country.options[i].value == chosenCountry)
                country.options[i].selected = true;
        }
    }

    function convertDateToReadableForm() {
        var table = document.querySelector('table[datatype="attachment"]');
        var tableRows = table.getElementsByTagName('tr');
        for (var i = 0; i < tableRows.length; i++) {
            var rowData = tableRows[i].getElementsByClassName('cell-text-alignment');
            var dateInMilliseconds = rowData[1].innerHTML;
            var date = new Date(dateInMilliseconds);
            var options = {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            };
            rowData[1].innerHTML = date.toLocaleDateString('ru', options);
        }
        var birthday = document.getElementById("birthday");
        birthday.value = birthday.value.split("-").reverse().join(".");
    }

    function setHiddenDatesToMilliseconds() {
        var hiddenAttachmentsInputList = document.getElementById("hiddenAttachmentsInputList");
        var currentDateElementNumber = 0;
        for (var i = 0; i < hiddenAttachmentsInputList.childElementCount; i++) {
            var hiddenInput = hiddenAttachmentsInputList.children[i];
            if (hiddenInput.name == ("attachingDate" + currentDateElementNumber)) {
                var id = "attachingDate" + currentDateElementNumber;
                currentDateElementNumber++;
                var splittedDate = document.getElementById(id).value.split("-");
                document.getElementById(id).value = (new Date(splittedDate[0], splittedDate[1], splittedDate[2])).getTime();
            }
        }
    }

    function renamePhoneHiddenInputs() {
        var hiddenPhonesInputList = document.getElementById('hiddenPhonesInputList');
        var phonesInitialCount = document.getElementById("phonesInitialCount").value;
        var i;
        for (i = 0; i < phonesInitialCount; i++) {
            for (var j = 0; j < phoneDataInputIDs.length; j++) {
                var hiddenInput = hiddenPhonesInputList.children[i * phoneDataInputIDs.length + j];
                if (hiddenInput.getAttribute("data-delete") == "true") {
                    hiddenInput.name = "delete" + phoneDataInputIDs[j] + i;
                    continue;
                }
                if (hiddenInput.getAttribute("data-change") == "true")
                    hiddenInput.name = "update" + phoneDataInputIDs[j] + i;
            }
        }
        for (; i < hiddenPhonesInputList.childElementCount / phoneDataInputIDs.length; i++) {
            for (j = 0; j < phoneDataInputIDs.length; j++) {
                hiddenInput = hiddenPhonesInputList.children[i * phoneDataInputIDs.length + j];
                hiddenInput.name = "new" + phoneDataInputIDs[j] + (i - phonesInitialCount);
            }
        }
    }

    function renameAttachmentHiddenInputs() {
        var hiddenAttachmentsInputList = document.getElementById('hiddenAttachmentsInputList');
        var attachmentsInitialCount = document.getElementById("attachmentsInitialCount").value;
        var i;
        for (i = 0; i < attachmentsInitialCount; i++) {
            for (var j = 0; j < attachmentDataInputIDs.length; j++) {
                var hiddenInput = hiddenAttachmentsInputList.children[i * attachmentDataInputIDs.length + j];
                if (hiddenInput.getAttribute("data-delete") == "true") {
                    hiddenInput.name = "delete" + attachmentDataInputIDs[j] + i;
                    continue;
                }
                if (hiddenInput.getAttribute("data-change") == "true")
                    hiddenInput.name = "update" + attachmentDataInputIDs[j] + i;
            }
        }
        for (; i < hiddenAttachmentsInputList.childElementCount / attachmentDataInputIDs.length; i++) {
            for (j = 0; j < attachmentDataInputIDs.length; j++) {
                hiddenInput = hiddenAttachmentsInputList.children[i * attachmentDataInputIDs.length + j];
                hiddenInput.name = "new" + attachmentDataInputIDs[j] + (i - attachmentsInitialCount);
            }
        }
    }

    document.getElementById("saveButton").onclick = function() {
        renamePhoneHiddenInputs();
        renameAttachmentHiddenInputs();
        var form = document.getElementById("contactForm");
        form.setAttribute("action", this.getAttribute("data-url"));
        form.setAttribute("method", "post");
        form.submit();
    };
    document.getElementById("cancelButton").onclick = function() {
        location.href = this.getAttribute("data-url");
    };
});