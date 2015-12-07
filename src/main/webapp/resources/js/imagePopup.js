document.addEventListener('DOMContentLoaded', function(){

    function showCover() {
        var coverDiv = document.createElement('div');
        coverDiv.id = 'cover-div';
        document.body.appendChild(coverDiv);
    }

    function hideCover() {
        document.body.removeChild(document.getElementById('cover-div'));
    }

    function showPopup() {
        showCover();
        var container = document.getElementById('image-popup-form-container');
        var primaryImagePath = document.getElementById('image').src;
        document.getElementById('image-popup-message').innerHTML = "Выберите картинку:";
        document.body.style.overflow = "hidden";

        function complete() {
            document.getElementById("popupImageText").value = "";
            hideCover();
            container.style.display = 'none';
            document.body.style.overflow = ""
        }

        document.getElementById('imageOk').onclick = function() {
            complete();
        };

        document.getElementById('imageCancel').onclick = function() {
            document.getElementById('image').src = primaryImagePath;
            document.getElementById('popup-image').src = primaryImagePath;
            complete();
        };

        container.style.display = 'block';
    }

    document.getElementById('image').onclick = function() {
        showPopup();
    };
    document.getElementById('findImageButton').onclick = function() {
        var imageInput = document.getElementById("imageInput");
        imageInput.click();
    };
    document.getElementById('imageInput').onchange = function() {
        var imageInput = document.getElementById("imageInput");
        var popupText = document.getElementById("popupImageText");
        popupText.value = imageInput.value;

        var newImagePath = URL.createObjectURL(event.target.files[0]);
        var popupImage = document.getElementById('popup-image');
        popupImage.src = newImagePath;
        var image = document.getElementById('image');
        image.src = newImagePath;
    };



    /*document.getElementById('findImageButton').onclick = function() {
        var imageInput = document.getElementById("imageInput");
        imageInput.click();
    };
    document.getElementById('imageInput').onchange = function() {

        var xhr = new XMLHttpRequest();
        var newImagePath = URL.createObjectURL(event.target.files[0]);
        xhr.open("GET", newImagePath, false);
        //xhr.responseType = "blob";
        //xhr.setRequestHeader('Content-Type', 'image/jpeg');
        xhr.onload = response;
        xhr.send();
        function response(e) {
            var urlCreator = window.URL || window.webkitURL;
            var binaryData = [];
            binaryData.push(this.response);
            var blob = new Blob(binaryData, {type: "image/jpeg"});
            var imageUrl = urlCreator.createObjectURL(blob);
            var popupImage = document.getElementById('popup-image');
            popupImage.src = imageUrl;
            var image = document.getElementById('image');
            image.src = imageUrl;
        }
    };*/
});
