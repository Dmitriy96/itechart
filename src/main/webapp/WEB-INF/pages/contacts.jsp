<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <form id="contactsForm" method="post" action="${pageContext.request.contextPath}/pages/contacts" role="form">
            <div class="row">

                <div class="col-md-2 affix">
                    <ul class="group-list">
                        <li class="list-item">
                            <a href="${pageContext.request.contextPath}/pages/newContact"><span class="glyphicon glyphicon-plus"></span> Создать контакт</a>
                        </li>
                        <li class="list-item">
                            <a href="${pageContext.request.contextPath}/pages/search"><span class="glyphicon glyphicon-search"></span> Поиск контактов</a>
                        </li>
                        <li class="list-item">
                            <a href="${pageContext.request.contextPath}/pages/contacts" id="deleteContactsButton"><span class="glyphicon glyphicon-search"></span> Удалить контакты</a>
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
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${contacts}" var="contact">
                                    <tr>
                                        <td>
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="contact" value="${contact.idContact}">
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="cell-text-alignment"><a href="">${contact.name} ${contact.surname}</a></div>
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
                                        <td>
                                            <div class="cell-alignment">
                                                <button type="button" class="btn btn-info" title="Посмотреть">
                                                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                                </button>
                                                <button type="button" class="btn btn-warning" title="Редактировать">
                                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                                </button>
                                            </div>
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
                    <ul class="pagination">
                        <li>
                            <a href="#">&laquo;</a>
                        </li>
                        <li class="active">
                            <a href="#">1</a>
                        </li>
                        <li>
                            <a href="#">2</a>
                        </li>
                        <li>
                            <a href="#">3</a>
                        </li>
                        <li>
                            <a href="#">&raquo;</a>
                        </li>
                    </ul>
                </div>
            </div>
        </form>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/contactsPage.js"></script>

</body>
</html>