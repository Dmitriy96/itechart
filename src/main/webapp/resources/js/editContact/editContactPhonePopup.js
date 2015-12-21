document.addEventListener('DOMContentLoaded', function(){

    var container = document.getElementById('phone-popup-form-container');
    var table = document.querySelector('table[datatype="phone"]');
    var phoneDataInputIDs = ['countryCode', 'operatorCode', 'phoneNumber', 'phoneComment', 'phoneType'];
    var phonesInitialCount = document.getElementById("phonesInitialCount").value;
    var error = document.getElementById("phoneError");
    emptyTableCheck(table);
    convertPhoneTypesToReadableForm();

    function showCover() {
        var coverDiv = document.createElement('div');
        coverDiv.id = 'cover-div';
        document.body.appendChild(coverDiv);
    }

    function hideCover() {
        document.body.removeChild(document.getElementById('cover-div'));
    }

    var counter = (function () {
        var counter = -1;
        return function (value) {
            if (value && !isNaN(value))
                return counter += value;
            else
                return counter;
        }
    })();
    counter(phonesInitialCount);

    function emptyTableCheck(table) {
        if (table.lastElementChild.childElementCount == 0) {
            document.getElementById('emptyPhonesTableRow').classList.remove('hidden');
            return true;
        } else {
            document.getElementById('emptyPhonesTableRow').classList.add('hidden');
        }
        return false;
    }

    function convertPhoneTypesToReadableForm() {
        if (emptyTableCheck(table)) return;
        function getSelectedTextByValue(value) {
            var phoneType = document.getElementById("phoneType");
            var text = phoneType.querySelector('option[value="' + value + '"]').text;
            return text;
        }

        for (var i = 0; i < table.rows.length; i++) {
            var phoneTableRow = table.rows[i];
            var rowData = phoneTableRow.getElementsByClassName('cell-text-alignment');
            rowData[1].innerHTML = getSelectedTextByValue(rowData[1].innerHTML);
        }
    }

    function closest(element, tagName) {
        console.log(element, tagName);
        while (element) {
            if (element.tagName == tagName.toUpperCase()) return element;
            element = element.parentNode;
            console.log(element, tagName);
        }
        return null;
    }

    function validateData() {
        var errorText = document.getElementById("phoneErrorText");
        var countryCode = document.getElementById('countryCode').value;
        if (countryCode) {
            if (countryCode.match(/^\+\d+$/) == null) {
                errorText.innerHTML = "Incorrect countryCode";
                error.classList.remove("hidden");
                return false;
            }
            operatorCode = document.getElementById('operatorCode').value;
            if (!operatorCode || isNaN(operatorCode)) {
                errorText.innerHTML = "Incorrect operatorCode";
                error.classList.remove("hidden");
                return false;
            }
        }
        var operatorCode = document.getElementById('operatorCode').value;
        if (operatorCode) {
            if (isNaN(operatorCode)) {
                errorText.innerHTML = "Incorrect operatorCode";
                error.classList.remove("hidden");
                return false;
            }
            if (!countryCode || countryCode.match(/^\+\d+$/) == null) {
                errorText.innerHTML = "Incorrect countryCode";
                error.classList.remove("hidden");
                return false;
            }
        }
        var phoneNumber = document.getElementById('phoneNumber').value;
        if (!phoneNumber || isNaN(phoneNumber)) {
            errorText.innerHTML = "Incorrect phoneNumber";
            error.classList.remove("hidden");
            return false;
        }
        return true;
    }

    function complete() {
        document.getElementById('countryCode').value = "";
        document.getElementById('operatorCode').value = "";
        document.getElementById('phoneNumber').value = "";
        document.getElementById('phoneComment').value = "";
        hideCover();
        container.style.display = 'none';
        document.body.style.overflow = "";
        error.classList.add("hidden");
        emptyTableCheck(table);
    }

    function showAddPhonePopup() {
        showCover();
        document.getElementById('phone-popup-message').innerHTML = "Добавление номера телефона";
        document.body.style.overflow = "hidden";

        function addNewRow() {
            var tableRow = document.getElementById('tableRow');
            var tbody = table.firstElementChild;
            var newRow = tableRow.cloneNode(true);
            newRow.removeAttribute('id');
            var checkBox = newRow.querySelector('[type="checkbox"]');
            checkBox.value = counter(1);
            tbody.appendChild(newRow);

            var phoneData = [];
            for (var i = 0; i < phoneDataInputIDs.length; i++) {
                phoneData[i] = document.getElementById(phoneDataInputIDs[i]).value;
            }
            var phoneSelect = document.getElementById("phoneType");

            var rowData = newRow.getElementsByClassName('cell-text-alignment');
            rowData[0].innerHTML = phoneData[0] + "" + phoneData[1] + "" + phoneData[2];
            rowData[1].innerHTML = phoneSelect.options[phoneSelect.selectedIndex].innerHTML;
            rowData[2].innerHTML = phoneData[3];
            var hiddenPhonesInputList = document.getElementById('hiddenPhonesInputList');
            for (i = 0; i < phoneData.length; i++) {
                var input = document.createElement('input');
                input.name = phoneDataInputIDs[i] + counter();
                input.type = "text";
                input.value = phoneData[i];
                input.setAttribute("data-number", counter());
                hiddenPhonesInputList.appendChild(input);
            }
            var countryCode = hiddenPhonesInputList.querySelector('[name="countryCode' + counter() + '"]');
            countryCode.value = countryCode.value.substr(1);
        }

        document.getElementById('phoneOk').onclick = function() {
            var isCorrect = validateData();
            if (!isCorrect) return false;
            addNewRow();
            complete();
        };

        document.getElementById('phoneCancel').onclick = function() {
            complete();
        };

        container.style.display = 'block';
    }

    function showEditPhonePopup(phoneTableRow) {
        showCover();
        var container = document.getElementById('phone-popup-form-container');
        var checkBox = phoneTableRow.querySelector('[type="checkbox"]');
        var hiddenPhonesInputList = document.getElementById('hiddenPhonesInputList');
        document.getElementById('phone-popup-message').innerHTML = "Изменение номера телефона";
        document.body.style.overflow = "hidden";
        setDataForEdition();

        function setDataForEdition() {
            for (var i = 0; i < phoneDataInputIDs.length; i++) {
                var inputID = phoneDataInputIDs[i];
                document.getElementById(inputID).value =
                    hiddenPhonesInputList.querySelector('[name="' + inputID + checkBox.value + '"]').value;
            }
            var countryCode = document.getElementById("countryCode");
            countryCode.value = "+" + countryCode.value;
        }

        function change() {
            var phoneData = [];
            for (var i = 0; i < phoneDataInputIDs.length; i++) {
                phoneData[i] = document.getElementById(phoneDataInputIDs[i]).value
            }
            var phoneType = document.getElementById('phoneType');

            function getSelectedTextByValue(value) {
                var text = phoneType.querySelector('option[value="' + value + '"]').text;
                return text;
            }

            var rowData = phoneTableRow.getElementsByClassName('cell-text-alignment');
            rowData[0].innerHTML = phoneData[0] + "" + phoneData[1] + "" + phoneData[2];
            rowData[1].innerHTML = getSelectedTextByValue(phoneData[4]);
            rowData[2].innerHTML = phoneData[3];

            for (i = 0; i < phoneDataInputIDs.length; i++) {
                var hiddenInput = hiddenPhonesInputList.querySelector('[name="' + phoneDataInputIDs[i] + checkBox.value + '"]');
                if (checkBox.value <= phonesInitialCount)
                    hiddenInput.setAttribute("data-change", "true");
                hiddenInput.value = phoneData[i];
            }
            var countryCode = hiddenPhonesInputList.querySelector('[name="countryCode' + checkBox.value + '"]');
            countryCode.value = countryCode.value.substr(1);
        }

        document.getElementById('phoneOk').onclick = function() {
            var isCorrect = validateData();
            if (!isCorrect) return false;
            change();
            complete();
        };

        document.getElementById('phoneCancel').onclick = function() {
            complete();
        };

        container.style.display = 'block';
    }

    function deletePhones(chosenCheckBoxes) {
        var hiddenPhonesInputList = document.getElementById('hiddenPhonesInputList');
        function setDataDeleteAttribute(value) {
            for (var i = 0; i < phoneDataInputIDs.length; i++) {
                var hiddenInput = hiddenPhonesInputList.querySelector('[name="' + phoneDataInputIDs[i] + value + '"]');
                hiddenInput.setAttribute("data-delete", "true");
            }
        }
        function removeHiddenInputs(value) {
            for (var i = 0; i < phoneDataInputIDs.length; i++) {
                var hiddenInput = hiddenPhonesInputList.querySelector('[name="' + phoneDataInputIDs[i] + value + '"]');
                hiddenInput.parentNode.removeChild(hiddenInput);
            }
        }
        for (var i = 0; i < chosenCheckBoxes.length; i++) {
            if (chosenCheckBoxes[i].value <= phonesInitialCount)
                setDataDeleteAttribute(chosenCheckBoxes[i].value);
            else
                removeHiddenInputs(chosenCheckBoxes[i].value);
            var row = closest(chosenCheckBoxes[i], 'tr');
            row.parentNode.removeChild(row);
        }
    }


    document.getElementById('addPhoneButton').onclick = function() {
        showAddPhonePopup();
    };
    document.getElementById('editPhoneButton').onclick = function() {
        if (emptyTableCheck(table)) return;
        var checkBox = table.querySelector(':checked');
        if (checkBox) {
            var phoneTableRow = closest(checkBox, 'tr');
            console.log(phoneTableRow);
            showEditPhonePopup(phoneTableRow);
        }
        else
            alert("Please, choose a phone number to change!");              // TODO change alert to pretty message
    };
    document.getElementById('deletePhoneButton').onclick = function() {
        var chosenCheckBoxes = table.querySelectorAll('input:checked');
        if (chosenCheckBoxes && chosenCheckBoxes.length > 0) {
            var checkDeleting = confirm("Действительно удалить выбранные номера?");
            if (checkDeleting)
                deletePhones(chosenCheckBoxes);
        }
        else
            alert("Please, choose a phone number to delete!");              // TODO change alert to pretty message
        emptyTableCheck(table);
    };
});
