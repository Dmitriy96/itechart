document.addEventListener('DOMContentLoaded', function(){

    var phoneDataInputIDs = ['countryCode', 'operatorCode', 'phoneNumber', 'phoneComment', 'phoneType'];
    var attachmentDataInputIDs = ['file', 'fileName', 'attachingDate', 'attachmentComment'];


    function renamePhoneHiddenInputs() {
        var hiddenPhonesInputList = document.getElementById('hiddenPhonesInputList');
        for (var i = 0; i < hiddenPhonesInputList.childElementCount / phoneDataInputIDs.length; i++) {
            for (var j = 0; j < phoneDataInputIDs.length; j++) {
                hiddenPhonesInputList.children[i * phoneDataInputIDs.length + j].name = phoneDataInputIDs[j] + i;
            }
        }
    }

    function renameAttachmentHiddenInputs() {
        var hiddenAttachmentsInputList = document.getElementById("hiddenAttachmentsInputList");
        for (i = 0; i < hiddenAttachmentsInputList.childElementCount / attachmentDataInputIDs.length; i++) {
            for (j = 0; j < attachmentDataInputIDs.length; j++) {
                hiddenAttachmentsInputList.children[i * attachmentDataInputIDs.length + j].name = attachmentDataInputIDs[j] + i;
            }
        }
    }

    document.getElementById("saveContactButton").onclick = function() {
        renamePhoneHiddenInputs();
        renameAttachmentHiddenInputs();
    };
});