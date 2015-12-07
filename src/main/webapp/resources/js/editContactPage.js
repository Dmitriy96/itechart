document.addEventListener('DOMContentLoaded', function(){

    setContactDataToInputs();
    convertDateToReadableForm();

    function setContactDataToInputs() {             // TODO set contact data to inputs for change

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
    }
});