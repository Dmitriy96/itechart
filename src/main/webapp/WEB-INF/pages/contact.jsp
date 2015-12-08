<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Информация о контакте</title>

    <c:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    <c:url value="/resources/css/custom.css" var="customCss"/>
    <link href="${customCss}" rel="stylesheet"/>
</head>
<body>

    <div class="container text-font">

        <div class="row page-header">
            <div class="col-md-9">
                <h1><c:out value="${contact.surname}"> <c:out value="${contact.name}"> <c:out value="${contact.patronymic}"></h1>
            </div>
            <div class="col-md-3 control-buttons-alignment">
                <button type="button" class="btn btn-primary" title="Назад">
                    <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Назад
                </button>
                <button type="button" class="btn btn-warning" title="Редактировать контакт">
                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Редактировать
                </button>
            </div>
        </div>

        <div class="row">
            <div class="col-md-3">
                <img id="image"
                class="img-responsive clickable"
                src='${pageContext.request.contextPath}/resources/images/${contact.idContact}.jpg'
                alt=""
                onerror='${pageContext.request.contextPath}/resources/images/default_avatar.png'>
            </div>
            <div class="col-md-4">
                <ul class="group-text-block">
                    <li class="text-block">
                        <h3>Дата рождения</h3>
                        <p><c:out value="${contact.birthday}"/> </p>
                    </li>
                    <li class="text-block">
                        <h3>Пол</h3>
                        <p><c:out value="${contact.gender}"/></p>
                    </li>
                    <li class="text-block">
                        <h3>Гражданство</h3>
                        <p><c:out value="${contact.citizenship}"/></p>
                    </li>
                    <li class="text-block">
                        <h3>Семейное положение</h3>
                        <p><c:out value="${contact.maritalStatus}"/></p>
                    </li>
                </ul>
            </div>
            <div class="col-md-4">
                <ul class="group-text-block">
                    <li class="text-block">
                        <h3>Web Site</h3>
                        <p><c:out value="${contact.website}"/></p>
                    </li>
                    <li class="text-block">
                        <h3>Email</h3>
                        <p><c:out value="${contact.email}"/></p>
                    </li>
                    <li class="text-block">
                        <h3>Место работы</h3>
                        <p><c:out value="${contact.company}"/></p>
                    </li>
                </ul>
            </div>
        </div>

        <hr>

        <div class="row">
            <h2>Адрес</h2>
            <ul>
                <li><c:out value="${contact.address.country}"/>, г. <c:out value="${contact.address.city}"/>, ул. <c:out value="${contact.address.street}"/>, д. <c:out value="${contact.address.houseNumber}"/>, кв. <c:out value="${contact.address.apartmentNumber}"/>, индекс <c:out value="${contact.address.zipCode}"/></li>
            </ul>
        </div>

        <hr>
        <br>
        <br>

        <h2>Контактные телефоны</h2>

        <div class="row">
            <table class="table table-hover">
                <c:forEach items="${contact.phoneList}" var="phone">
                    <tr>
                        <td>
                            <div class="checkbox cell-alignment">
                                <label>
                                    <input type="checkbox" value="${phone.idPhone}">
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="cell-text-alignment"><a href="">+<c:out value="${phone.countryCode}"/><c:out value="${phone.operatorCode}"/><c:out value="${phone.phoneNumber}"/></a></div>
                        </td>
                        <td>
                            <div class="cell-text-alignment"><c:out value="${phone.phoneType}"/></div>
                        </td>
                        <td>
                            <div class="cell-text-alignment"><c:out value="${phone.comment}"/></div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <br>
        <br>

        <h2>Список присоединений</h2>

        <div class="row">
            <table class="table table-hover">
                <c:forEach items="${contact.attachmentList}" var="attachment">
                    <tr>
                        <td>
                            <div class="checkbox cell-alignment">
                                <label>
                                    <input type="checkbox" value="${attachment.idAttachment}">
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="cell-text-alignment"><a href=""><c:out value="${attachment.fileName}"/></a></div>
                        </td>
                        <td>
                            <div class="cell-text-alignment"><c:out value="${attachment.uploadDate}"/></div>
                        </td>
                        <td>
                            <div class="cell-text-alignment"><c:out value="${attachment.comment}"/></div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <br/><br/>

        <div class="row">
            <div class="col-md-3 col-md-offset-3 text-center">
                <button type="button" class="btn btn-warning btn-lg button-size" title="Редактировать контакт">
                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Редактировать
                </button>
            </div>
            <div class="col-md-1 text-center">
                <button type="button" class="btn btn-primary btn-lg button-size" title="Назад">
                    <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Назад
                </button>
            </div>
        </div>

    </div>

    <div class="page-bottom"></div>

</body>
</html>
