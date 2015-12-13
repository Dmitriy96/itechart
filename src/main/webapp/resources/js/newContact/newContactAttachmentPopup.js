document.addEventListener('DOMContentLoaded', function(){

    var container = document.getElementById('attachment-popup-form-container');
    var table = document.querySelector('table[datatype="attachment"]');
    emptyTableCheck(table);

    function showCover() {
        var coverDiv = document.createElement('div');
        coverDiv.id = 'cover-div';
        document.body.appendChild(coverDiv);
    }

    function hideCover() {
        document.body.removeChild(document.getElementById('cover-div'));
    }

    function emptyTableCheck(table) {
        if (table.lastElementChild.childElementCount == 0) {
            document.getElementById('emptyAttachmentsTableRow').classList.remove('hidden');
            return true;
        } else {
            document.getElementById('emptyAttachmentsTableRow').classList.add('hidden');
        }
        return false;
    }

    function closest(element, tagName) {
        while (element) {
            if (element.tagName == tagName.toUpperCase()) return element;
            element = element.parentNode;
        }
        return null;
    }

    function validateData() {
        var val = document.getElementById('fileName').value;
        if (!val) {
            alert("File name must be filled out");
            return false;
        }
        if (document.getElementById("attachmentInput").files.length == 0) {
            alert("Please, browse a file.");
            return false;
        }
        return true;
    }

    function complete() {
        document.getElementById('fileName').value = "";
        document.getElementById('attachmentComment').value = "";
        document.getElementById('popupAttachmentText').value = "";
        document.getElementById('attachmentPopupFileChooserBlock').classList.remove('hidden');
        hideCover();
        container.style.display = 'none';
        document.body.style.overflow = "";
        emptyTableCheck(table);
    }

    function showAddAttachmentPopup() {
        showCover();
        document.getElementById('attachment-popup-message').innerHTML = "Добавление нового файла";
        document.body.style.overflow = "hidden";

        function addNewRow() {
            var tableRow = document.getElementById('tableRow');
            var tbody = table.firstElementChild;
            var newRow = tableRow.cloneNode(true);
            newRow.removeAttribute('id');
            var checkBox = newRow.querySelector('[type="checkbox"]');
            checkBox.value = tbody.childElementCount;
            tbody.appendChild(newRow);

            var attachmentDataInputIDs = ['fileName', 'attachingDate', 'attachmentComment'];
            var attachmentData = [];
            var date = new Date();
            attachmentData[0] = document.getElementById('fileName').value;
            attachmentData[1] = date.getTime();
            attachmentData[2] = document.getElementById('attachmentComment').value;

            var hiddenAttachmentsInputList = document.getElementById('hiddenAttachmentsInputList');
            var fileInput = document.getElementById('attachmentInput');
            var newFileInput = fileInput.cloneNode(true);
            fileInput.removeAttribute('id');
            fileInput.parentNode.appendChild(newFileInput);
            fileInput.name = "file" + checkBox.value;
            hiddenAttachmentsInputList.appendChild(fileInput);

            var rowData = newRow.getElementsByClassName('cell-text-alignment');
            for (var i = 0; i < attachmentDataInputIDs.length; i++) {
                rowData[i].innerHTML = attachmentData[i];
                var input = document.createElement('input');
                input.name = attachmentDataInputIDs[i] + checkBox.value;
                input.type = "text";
                input.value = attachmentData[i];
                input.setAttribute("data-number", checkBox.value);
                hiddenAttachmentsInputList.appendChild(input);
            }
            var options = {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            };
            rowData[1].innerHTML = date.toLocaleDateString('ru', options);
        }

        document.getElementById('attachmentOk').onclick = function() {
            var isCorrect = validateData();
            if (!isCorrect) return false;
            addNewRow();
            complete();
        };

        document.getElementById('attachmentCancel').onclick = function() {
            complete();
        };

        container.style.display = 'block';
    }

    function showEditAttachmentPopup(attachmentTableRow) {
        showCover();
        var container = document.getElementById('attachment-popup-form-container');
        var checkBox = attachmentTableRow.querySelector('[type="checkbox"]');
        document.getElementById('attachmentPopupFileChooserBlock').className = 'hidden';
        var hiddenAttachmentsInputList = document.getElementById('hiddenAttachmentsInputList');
        var attachmentDataInputIDs = ['fileName', 'attachmentComment'];
        document.getElementById('attachment-popup-message').innerHTML = "Изменение информации о файле";
        document.body.style.overflow = "hidden";
        setDataForEdition();

        function setDataForEdition() {
            for (var i = 0; i < attachmentDataInputIDs.length; i++) {
                var inputID = attachmentDataInputIDs[i];
                document.getElementById(inputID).value =
                    hiddenAttachmentsInputList.querySelector('[name="' + inputID + checkBox.value + '"]').value;
            }
        }

        function change() {
            var attachmentData = [];
            for (var i = 0; i < attachmentDataInputIDs.length; i++) {
                attachmentData[i] = document.getElementById(attachmentDataInputIDs[i]).value;
            }
            var rowData = attachmentTableRow.getElementsByClassName('cell-text-alignment');
            rowData[0].innerHTML = attachmentData[0];
            rowData[2].innerHTML = attachmentData[1];
            for (i = 0; i < attachmentDataInputIDs.length; i++) {
                var hiddenInput = hiddenAttachmentsInputList.querySelector('[name="' + attachmentDataInputIDs[i] + checkBox.value + '"]');
                hiddenInput.value = attachmentData[i];
            }
        }

        document.getElementById('attachmentOk').onclick = function() {
            var fileName = document.getElementById('fileName').value;
            if (!fileName) return false;
            change();
            complete();
        };

        document.getElementById('attachmentCancel').onclick = function() {
            complete();
        };

        container.style.display = 'block';
    }

    function deleteAttachment(chosenCheckBox) {
        var hiddenAttachmentsInputList = document.getElementById('hiddenAttachmentsInputList');
        var attachmentDataInputIDs = ['file', 'fileName', 'attachingDate', 'attachmentComment'];
        function removeHiddenInputs(value) {
            for (var i = 0; i < attachmentDataInputIDs.length; i++) {
                var hiddenInput = hiddenAttachmentsInputList.querySelector('[name="' + attachmentDataInputIDs[i] + value + '"]');
                hiddenInput.parentNode.removeChild(hiddenInput);
            }
        }
        for (var i = 0; i < chosenCheckBox.length; i++) {
            removeHiddenInputs(chosenCheckBox[i].value);
            var row = closest(chosenCheckBox[i], 'tr');
            row.parentNode.removeChild(row);
        }
    }


    document.getElementById('addAttachmentButton').onclick = function() {
        showAddAttachmentPopup();
    };
    document.getElementById('editAttachmentButton').onclick = function() {
        if (emptyTableCheck(table)) return;
        var checkBox = table.querySelector(':checked');
        if (checkBox) {
            var phoneTableRow = closest(checkBox, 'tr');
            showEditAttachmentPopup(phoneTableRow);
        }
        else
            alert("Please, choose a phone number to change!");              // TODO change alert to pretty message
    };
    document.getElementById('deleteAttachmentButton').onclick = function() {
        var chosenCheckBox = table.querySelectorAll('input:checked');
        if (chosenCheckBox && chosenCheckBox.length > 0) {
            var checkDeleting = confirm("Действительно удалить выбранные файлы?");
            if (checkDeleting)
                deleteAttachment(chosenCheckBox);
        }
        else
            alert("Please, choose a phone number to change!");              // TODO change alert to pretty message
        emptyTableCheck(table);
    };
    document.getElementById('findAttachmentButton').onclick = function() {
        var attachmentInput = document.getElementById("attachmentInput");
        attachmentInput.onchange = function() {  // should be inside for save it working after 'attachmentInput' element replacement
            var popupText = document.getElementById("popupAttachmentText");
            popupText.value = this.value;
        };
        attachmentInput.click();
    };
});
