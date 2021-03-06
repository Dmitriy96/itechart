<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Редактирование контакта</title>

    <c:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    <c:url value="/resources/css/custom.css" var="customCss"/>
    <link href="${customCss}" rel="stylesheet"/>
    <c:url value="/resources/css/popup.css" var="popupCss"/>
    <link href="${popupCss}" rel="stylesheet"/>
</head>
<body>

    <div class="container text-font">

        <h1 class="page-header">Редактирование контакта</h1>

        <form id="contactForm" method="post" action="${pageContext.request.contextPath}/pages/editContact/${contact.idContact}" enctype="multipart/form-data" role="form">

            <div class="row">
                <div class="col-md-3">
                    <img id="image"
                         name="userImage"
                         class="img-responsive clickable"
                         src="${pageContext.request.contextPath}/resources/images/${contact.idContact}.jpg"
                         alt=""
                         onerror='this.src="${pageContext.request.contextPath}/resources/images/default_avatar.png"'>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input type="text" class="form-control" id="name" name="name" value="${contact.name}" placeholder="Имя" maxlength="150" required/>
                    </div>
                    <div class="form-group">
                        <label for="surname">Фамилия</label>
                        <input type="text" class="form-control" id="surname" name="surname" value="${contact.surname}" placeholder="Фамилия" maxlength="150" required/>
                    </div>
                    <div class="form-group">
                        <label for="patronymic">Отчество</label>
                        <input type="text" class="form-control" id="patronymic" name="patronymic" value="${contact.patronymic}" placeholder="Отчество" maxlength="150"/>
                    </div>
                    <div class="form-group">
                        <label for="birthday">Дата рождения</label>
                        <input type="text" class="form-control" id="birthday" name="birthday" value="${contact.birthday}" placeholder="01.01.1980" pattern="\d{2}\.\d{2}\.\d{4}" required/>
                    </div>
                    <div class="form-group">
                        <input type="text" class="hidden" id="contactGender" value="${contact.gender}"/>
                        <label for="gender">Пол</label><br/>
                        <select id="gender" name="gender">
                            <option value="NONE">--- Выбор ---</option>
                            <option value="MALE">Мужской</option>
                            <option value="FEMALE">Женский</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label for="citizenship">Гражданство</label>
                        <input type="text" class="form-control" id="citizenship" name="citizenship" value="${contact.citizenship}" placeholder="Гражданство" maxlength="150"/>
                    </div>
                    <div class="form-group">
                        <label for="website">Веб сайт</label>
                        <input type="text" class="form-control" id="website" name="website" value="${contact.website}" placeholder="Веб сайт" maxlength="150"/>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${contact.email}" placeholder="Email" maxlength="150" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required/>
                    </div>
                    <div class="form-group">
                        <label for="company">Место работы</label>
                        <input type="text" class="form-control" id="company" name="company" value="${contact.company}" placeholder="Место работы" maxlength="150" required/>
                    </div>
                    <div class="form-group">
                        <input type="text" class="hidden" id="contactMaritalStatus" value="${contact.maritalStatus}"/>
                        <label for="marital">Семейное положение</label>
                        <select id="marital" name="marital">
                            <option value="NONE">--- Выбор ---</option>
                            <option value="SINGLE">Холост/Не замужем</option>
                            <option value="MARRIED">Женат/Замужем</option>
                            <option value="DIVORCED">Разведён/Разведена</option>
                            <option value="WIDOWED">Вдовец/Вдова</option>
                        </select>
                    </div>
                </div>
            </div>

            <hr>

            <div class="row">
                <h2>Адрес</h2>
            </div>

            <div class="row">
                <div class="col-xs-6">
                    <div class="form-group">
                        <input type="text" class="hidden" id="chosenCountry" value="${contact.address.country}"/>
                        <label for="country">Страна</label>
                        <select id="country" name="country" class="form-control">
                            <option value="NONE" selected>--- Выбор ---</option>
                            <c:forEach items="${countries}" var="country">
                                <option value="${country}">${country}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="city">Город</label>
                        <input type="text" class="form-control" id="city" name="city" value="${contact.address.city}" placeholder="Город" maxlength="150" required/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="street">Улица</label>
                        <input type="text" class="form-control" id="street" name="street" value="${contact.address.street}" placeholder="Улица" maxlength="150" required/>
                    </div>
                    <div class="form-group">
                        <label for="zipCode">Индекс</label>
                        <input type="text" class="form-control" id="zipCode" name="zipCode" value="${contact.address.zipCode}" placeholder="Индекс" maxlength="10"/>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="houseNumber">Номер дома</label>
                        <input type="text" class="form-control" id="houseNumber" name="houseNumber" value="${contact.address.houseNumber}" placeholder="Номер дома" maxlength="150" required/>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="apartmentNumber">Квартира</label>
                        <input type="text" class="form-control" id="apartmentNumber" name="apartmentNumber" value="${contact.address.apartmentNumber}" placeholder="Квартира" maxlength="45">
                    </div>
                </div>
            </div>

            <hr>
            <br>
            <br>

            <div class="row">
                <div class="col-md-8">
                    <h2>Контактные телефоны</h2>
                </div>
                <div class="col-md-4 control-buttons-alignment">
                    <button id="addPhoneButton" type="button" class="btn btn-info" title="Добавить телефон">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                    </button>
                    <button id="editPhoneButton" type="button" class="btn btn-warning" title="Редактировать телефон">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Редактировать
                    </button>
                    <button id="deletePhoneButton" type="button" class="btn btn-danger" title="Удалить телефон">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Удалить
                    </button>
                </div>
            </div>

            <div class="row">
                <table class="table table-hover" datatype="phone">
                    <tbody>
                        <c:forEach items="${contact.phoneList}" var="phone" varStatus="loop">
                            <tr>
                                <td>
                                    <div class="checkbox cell-alignment">
                                        <label>
                                            <input type="checkbox" value="${loop.index}">
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="cell-text-alignment"><c:if test="${phone.countryCode != 0 && phone.operatorCode != 0}">+${phone.countryCode}${phone.operatorCode}</c:if>${phone.phoneNumber}</div>
                                </td>
                                <td>
                                    <div class="cell-text-alignment">${phone.phoneType}</div>
                                </td>
                                <td>
                                    <div class="cell-text-alignment">${phone.comment}</div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="emptyPhonesTableRow" class="well well-lg text-center hidden">
                    Таблица не содержит телефонов.
                </div>
            </div>

            <br>
            <br>

            <div class="row">
                <div class="col-md-8">
                    <h2>Список файлов</h2>
                </div>
                <div class="col-md-4 control-buttons-alignment">
                    <button id="addAttachmentButton" type="button" class="btn btn-info" title="Добавить">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                    </button>
                    <button id="editAttachmentButton" type="button" class="btn btn-warning" title="Редактировать">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Редактировать
                    </button>
                    <button id="deleteAttachmentButton" type="button" class="btn btn-danger" title="Удалить">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Удалить
                    </button>
                </div>
            </div>

            <div class="row">
                <table class="table table-hover" datatype="attachment">
                    <tbody>
                        <c:forEach items="${contact.attachmentList}" var="attachment" varStatus="loop">
                            <tr>
                                <td>
                                    <div class="checkbox cell-alignment">
                                        <label>
                                            <input type="checkbox" value="${loop.index}">
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="cell-text-alignment">${attachment.fileName}</div>
                                </td>
                                <td>
                                    <div class="cell-text-alignment">${attachment.uploadDate}</div>
                                </td>
                                <td>
                                    <div class="cell-text-alignment">${attachment.comment}</div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="emptyAttachmentsTableRow" class="well well-lg text-center hidden">
                    Таблица не содержит файлов.
                </div>
            </div>

            <br/><br/><br/>

            <div class="row">
                <div class="col-md-3 col-md-offset-3 text-center">
                    <button type="submit" id="saveButton" data-url="${pageContext.request.contextPath}/pages/editContact/${contact.idContact}" class="btn btn-success btn-lg button-size">Сохранить</button>
                </div>
                <div class="col-md-1 text-center">
                    <button type="submit" id="cancelButton" data-url="${pageContext.request.contextPath}/pages/contacts" class="btn btn-info btn-lg button-size">Отмена</button>
                </div>
            </div>

            <!--Popup image form-->
            <div id="image-popup-form-container">
                <div id="image-popup-form">
                    <div id="image-popup-message"></div>
                    <input type="text" id="popupImageText" readonly/>
                    <input type='file' accept="image/jpeg" id="imageInput"/>
                    <input type="button" value="Найти" id="findImageButton"/>
                    <img id="popup-image"
                         class="img-responsive img-centralize"
                         src="${pageContext.request.contextPath}/resources/images/${contact.idContact}.jpg"
                         alt=""
                         onerror='this.src="${pageContext.request.contextPath}/resources/images/default_avatar.png"'>
                    <input type="button" id="imageOk" value="Сохранить">
                    <input type="button" id="imageCancel" value="Отменить">
                </div>
            </div>

            <!--Popup phones form-->
            <div id="phone-popup-form-container">
                <div id="phone-popup-form" class="popup-input-block">
                    <h3 id="phone-popup-message"></h3>
                    <div id="phoneError" class="alert alert-danger hidden" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        <span id="phoneErrorText"></span>
                    </div>
                    <div class="form-group">
                        <label for="countryCode">Код страны</label>
                        <input type="text" class="form-control" id="countryCode" placeholder="+375" maxlength="10" pattern="\+\d{1,3}"/>
                    </div>
                    <div class="form-group">
                        <label for="operatorCode">Код оператора</label>
                        <input type="text" class="form-control" id="operatorCode" placeholder="Код оператора" maxlength="10" pattern="\d+"/>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Телефонный номер</label>
                        <input type="text" class="form-control" id="phoneNumber" placeholder="Телефонный номер" maxlength="10" pattern="\d+"/>
                    </div>
                    <div class="form-group">
                        <label for="phoneType">Тип телефона</label><br/>
                        <select id="phoneType">
                            <option value="MOBILE" selected>Мобильный</option>
                            <option value="LANDLINE">Домашний</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="phoneComment">Комментарий</label>
                        <textarea rows="6" cols="45" class="form-control" id="phoneComment" maxlength="5000"></textarea>
                    </div>
                    <input type="button" id="phoneOk" value="Сохранить">
                    <input type="button" id="phoneCancel" value="Отменить">
                </div>
            </div>

            <!--Popup attachments form-->
            <div id="attachment-popup-form-container">
                <div id="attachment-popup-form" class="popup-input-block">
                    <h3 id="attachment-popup-message"></h3><br/>
                    <div id="attachmentError" class="alert alert-danger hidden" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        <span id="attachmentErrorText"></span>
                    </div>
                    <div class="form-group">
                        <label for="fileName">Имя файла</label>
                        <input type="text" class="form-control" id="fileName" placeholder="Имя файла" maxlength="100"/>
                    </div>
                    <div id="attachmentPopupFileChooserBlock" class="form-group">
                        <label for="popupAttachmentText">Выбор файла</label><br/>
                        <input type="text" id="popupAttachmentText" readonly/>
                        <input type='file' id="attachmentInput"/>
                        <input type="button" value="Найти" id="findAttachmentButton"/>
                    </div>
                    <div class="form-group">
                        <label for="attachmentComment">Комментарий</label>
                        <textarea rows="6" cols="45" class="form-control" id="attachmentComment" maxlength="5000"></textarea>
                    </div>
                    <input type="button" id="attachmentOk" value="Сохранить">
                    <input type="button" id="attachmentCancel" value="Отменить">
                </div>
            </div>

            <!--Phone and Attachment table row template-->
            <table id="utilityTable" class="hidden">
                <tr id="tableRow">
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"></div>
                    </td>
                </tr>
            </table>

            <!--Blocks of hidden inputs, which contain data from tables-->
            <div id="hiddenPhonesInputList" class="hidden">
                <c:forEach items="${contact.phoneList}" var="phone" varStatus="loop">
                    <input type="text" name="countryCode${loop.index}" value="${phone.countryCode}" data-change="false" data-delete="false"/>
                    <input type="text" name="operatorCode${loop.index}" value="${phone.operatorCode}" data-change="false" data-delete="false"/>
                    <input type="text" name="phoneNumber${loop.index}" value="${phone.phoneNumber}" data-change="false" data-delete="false"/>
                    <input type="text" name="phoneComment${loop.index}" value="${phone.comment}" data-change="false" data-delete="false"/>
                    <input type="text" name="phoneType${loop.index}" value="${phone.phoneType}" data-change="false" data-delete="false"/>
                </c:forEach>
            </div>
            <div id="hiddenAttachmentsInputList" class="hidden">
                <c:forEach items="${contact.attachmentList}" var="attachment" varStatus="loop">
                    <input type="text" name="file${loop.index}" value="${attachment.fileName}" data-change="false" data-delete="false"/>
                    <input type="text" name="fileName${loop.index}" value="${attachment.fileName}" data-change="false" data-delete="false"/>
                    <input type="text" id="attachingDate${loop.index}" name="attachingDate${loop.index}" value="${attachment.uploadDate}" data-change="false" data-delete="false"/>
                    <input type="text" name="attachmentComment${loop.index}" value="${attachment.comment}" data-change="false" data-delete="false"/>
                </c:forEach>
            </div>
            <div name="primaryContactIds" class="hidden">
                <c:forEach items="${contact.phoneList}" var="phone" varStatus="loop">
                    <input type="text" name="idPhone${loop.index}" value="${phone.idPhone}"/>
                </c:forEach>
                <input type="text" id="phonesInitialCount" name="phonesInitialCount" value="${fn:length(contact.phoneList)}"/>
                <c:forEach items="${contact.attachmentList}" var="attachment" varStatus="loop">
                    <input type="text" name="idAttachment${loop.index}" value="${attachment.idAttachment}"/>
                </c:forEach>
                <input type="text" id="attachmentsInitialCount" name="attachmentsInitialCount" value="${fn:length(contact.attachmentList)}"/>
                <input type="text" name="idContact" value="${contact.idContact}">
            </div>

        </form>

    </div>

    <div class="page-bottom"></div>

    <script src="${pageContext.request.contextPath}/resources/js/imagePopup.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/editContact/editContactPhonePopup.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/editContact/editContactAttachmentPopup.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/editContact/editContactPage.js"></script>

</body>
</html>
