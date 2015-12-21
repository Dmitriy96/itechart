<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Список контактов</title>

    <c:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    <c:url value="/resources/css/custom.css" var="customCss"/>
    <link href="${customCss}" rel="stylesheet"/>
</head>

<body>

    <div class="container-fluid">

        <h2 class="text-center">Список контактов</h2>

        <hr>

        <form id="contactsForm" role="form">
            <div class="row">

                <div class="col-md-2 affix">
                    <ul class="group-list">
                        <li class="list-item">
                            <a id="allContactList" href="${pageContext.request.contextPath}/pages/contacts"><span class="glyphicon glyphicon-list-alt"></span> Список контактов</a>
                        </li>
                        <li class="list-item">
                            <a href="${pageContext.request.contextPath}/pages/newContact"><span class="glyphicon glyphicon-plus"></span> Создать контакт</a>
                        </li>
                        <li class="list-item">
                            <a href="${pageContext.request.contextPath}/pages/search"><span class="glyphicon glyphicon-search"></span> Поиск контактов</a>
                        </li>
                        <li class="list-item">
                            <a href="${pageContext.request.contextPath}/pages/contacts" id="deleteContactsButton"><span class="glyphicon glyphicon-trash"></span> Удалить контакты</a>
                        </li>
                        <li class="list-item">
                            <a href="${pageContext.request.contextPath}/pages/email" id="sendEmailButton"><span class="glyphicon glyphicon-envelope"></span> Отправить email</a>
                        </li>
                    </ul>
                </div>

                <div class="col-md-10 col-md-offset-2 left-vertical-divider">
                    <div class="table-responsive">
                        <table id="contactsTable" class="table table-hover">
                            <thead>
                            <tr>
                                <th></th>
                                <th><div class="cell-text-alignment">Имя</div></th>
                                <th><div class="cell-text-alignment">Дата рождения</div></th>
                                <th><div class="cell-text-alignment">Адрес</div></th>
                                <th><div class="cell-text-alignment">Место работы</div></th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${contacts}" var="contact" varStatus="loop">
                                    <tr>
                                        <td>
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="contact" value="${contact.idContact}">
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="cell-text-alignment"><a href="${pageContext.request.contextPath}/pages/editContact/${contact.idContact}">${contact.name} ${contact.surname}</a></div>
                                        </td>
                                        <td>
                                            <div class="cell-text-alignment">${contact.birthday}</div>
                                        </td>
                                        <td>
                                            <div class="cell-text-alignment">${contact.address.city}
                                                <c:if test="${contact.address.street != null}">, ул. ${contact.address.street}</c:if>
                                                <c:if test="${contact.address.houseNumber != null}">, д. ${contact.address.houseNumber}</c:if>
                                                <c:if test="${contact.address.apartmentNumber != null}">, кв. ${contact.address.apartmentNumber}</c:if>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="cell-text-alignment">${contact.company}</div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div id="emptyContacts" class="well well-lg text-center hidden">
                            Таблица не содержит контактов.
                        </div>
                    </div>
                </div>
            </div>

            <!-- Pagination -->
            <div class="row text-center">
                <div class="col-lg-12">
                    <ul class="pager">
                        <li id="previousPage" data-url="${pageContext.request.contextPath}/pages/contacts" class="disabled"><a href="">Предыдущая</a></li>
                        <li id="nextPage" data-url="${pageContext.request.contextPath}/pages/contacts" class="disabled"><a href="">Следующая</a></li>
                    </ul>
                </div>
            </div>

            <div id="contactSearchAttributes" class="hidden">
                <input type="text" id="isSearch" name="isSearch" value="${isSearch}"/>
                <input type="text" name="name" value="${searchAttributes.name}"/>
                <input type="text" name="surname" value="${searchAttributes.surname}"/>
                <input type="text" name="patronymic" value="${searchAttributes.patronymic}"/>
                <input type="text" name="citizenship" value="${searchAttributes.citizenship}"/>
                <input type="text" name="birthdayDateFrom" value="${searchAttributes.birthdayDateFrom}"/>
                <input type="text" name="birthdayDateTo" value="${searchAttributes.birthdayDateTo}"/>
                <input type="text" name="gender" value="${searchAttributes.gender}"/>
                <input type="text" name="maritalStatus" value="${searchAttributes.maritalStatus}"/>
                <input type="text" name="country" value="${searchAttributes.address.country}"/>
                <input type="text" name="city" value="${searchAttributes.address.city}"/>
                <input type="text" name="street" value="${searchAttributes.address.street}"/>
                <input type="text" name="houseNumber" value="${searchAttributes.address.houseNumber}"/>
                <input type="text" name="apartmentNumber" value="${searchAttributes.address.apartmentNumber}"/>
                <input type="text" name="zipCode" value="${searchAttributes.address.zipCode}"/>
            </div>

            <div name="paginationValues" class="hidden">
                <input type="text" id="startContactIdForPreviousPage" name="startContactIdForPreviousPage" value="${contacts[0].idContact}"/>
                <input type="text" id="isLowerIds" name="isLowerIds" value=""/>
                <c:forEach items="${contacts}" var="contact" varStatus="loop">
                    <c:if test="${loop.last}">
                        <input type="text" id="startContactIdForNextPage" name="startContactIdForNextPage" value="${contact.idContact}"/>
                    </c:if>
                </c:forEach>
                <input type="text" id="startContactIdForPage" name="startContactIdForPage" value=""/>
            </div>

            <input type="text" id="deletingContactsId" name="deletingContactsId" class="hidden" value=""/>
            <input type="text" id="emailContactsId" name="emailContactsId" class="hidden" value=""/>

        </form>

        <div class="hidden">
            <input type="text" id="hasNext" value="${hasNext}"/>
            <input type="text" id="hasPrevious" value="${hasPrevious}"/>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/resources/js/contactsPage.js"></script>

</body>
</html>