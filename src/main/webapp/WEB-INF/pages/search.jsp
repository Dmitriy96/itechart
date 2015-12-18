<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Поиск контакта</title>

    <c:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    <c:url value="/resources/css/custom.css" var="customCss"/>
    <link href="${customCss}" rel="stylesheet"/>
</head>
<body>

    <div class="container text-font">
    
        <h1 class="page-header">Поиск контакта</h1>

        <form id="searchForm" method="post" action="${pageContext.request.contextPath}/pages/search" role="form">

            <div id="emptyFieldsError" class="alert alert-danger hidden" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                At least one fild should be filled.
            </div>
            <c:if test="${dateInvalidFormat != null}">
                <div class="alert alert-danger hidden" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Error:</span>
                    Invalid date format.
                </div>
            </c:if>

            <div class="row">
                <div class="col-md-8">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Имя" maxlength="150">
                    </div>
                    <div class="form-group">
                        <label for="surname">Фамилия</label>
                        <input type="text" class="form-control" id="surname" name="surname" placeholder="Фамилия" maxlength="150">
                    </div>
                    <div class="form-group">
                        <label for="patronymic">Отчество</label>
                        <input type="text" class="form-control" id="patronymic" name="patronymic" placeholder="Отчество" maxlength="150">
                    </div>
                    <div class="form-group">
                        <label for="citizenship">Гражданство</label>
                        <input type="text" class="form-control" id="citizenship" name="citizenship" placeholder="Гражданство" maxlength="150">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="lowerBirthday">Дата рождения (от)</label>
                        <input type="text" class="form-control" id="lowerBirthday" name="lowerBirthday" placeholder="01.01.1980" pattern="\d{2}\.\d{2}\.\d{4}"/>
                    </div>
                    <div class="form-group">
                        <label for="gender">Пол</label><br/>
                        <select id="gender" name="gender">
                            <option value="NONE">--- Выбор ---</option>
                            <option value="MALE">Мужской</option>
                            <option value="FEMALE">Женский</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="upperBirthday">Дата рождения (до)</label>
                        <input type="text" class="form-control" id="upperBirthday" name="upperBirthday" placeholder="01.01.1980" pattern="\d{2}\.\d{2}\.\d{4}"/>
                    </div>
                    <div class="form-group">
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


            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="country">Страна</label>
                        <select id="country" name="country" class="form-control">
                            <option value="NONE" selected>--- Выбор ---</option>
                            <c:forEach items="${countries}" var="country">
                                <option value="${country}">${country}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="street">Улица</label>
                        <input type="text" class="form-control" id="street" name="street" placeholder="Улица" maxlength="150">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="city">Город</label>
                        <input type="text" class="form-control" id="city" name="city" placeholder="Город" maxlength="150">
                    </div>
                    <div class="form-group">
                        <label for="houseNumber">Номер дома</label>
                        <input type="text" class="form-control" id="houseNumber" name="houseNumber" placeholder="Номер дома" maxlength="150">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="zipCode">Индекс</label>
                        <input type="text" class="form-control" id="zipCode" name="zipCode" placeholder="Индекс" maxlength="10">
                    </div>
                    <div class="form-group">
                        <label for="apartmentNumber">Квартира</label>
                        <input type="text" class="form-control" id="apartmentNumber" name="apartmentNumber" placeholder="Квартира" maxlength="45">
                    </div>
                </div>
            </div>

            <br/><br/><br/>

            <div class="row">
                <div class="col-md-3 col-md-offset-3 text-center">
                    <button type="submit" id="searchButton" class="btn btn-success btn-lg button-size">Поиск</button>
                </div>
                <div class="col-md-1 text-center">
                    <button type="submit" id="cancelButton" class="btn btn-info btn-lg button-size">Назад</button>
                </div>
            </div>

        </form>

    </div>

    <div class="page-bottom"></div>

    <script src="${pageContext.request.contextPath}/resources/js/search/searchPage.js"></script>

</body>
</html>
