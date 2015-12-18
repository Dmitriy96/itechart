<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Отправить email</title>

    <c:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    <c:url value="/resources/css/custom.css" var="customCss"/>
    <link href="${customCss}" rel="stylesheet"/>
</head>
<body>

    <div class="container text-font">

        <h1 class="page-header">Отправить email</h1>

        <form id="emailForm" method="post" action="${pageContext.request.contextPath}/pages/email/send" role="form">

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="recipients">Кому</label>
                        <input type="text" class="form-control" id="recipients" value="${recipients}" name="recipients" placeholder="Кому" pattern="([a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3},?)+" required>
                        <input type="text" id="emailContactsId" name="emailContactsId" value="${emailContactsId}" class="hidden"/>
                    </div>
                    <div class="form-group">
                        <label for="subject">Тема</label>
                        <input type="text" class="form-control" id="subject" name="subject" value="${subject}" placeholder="Тема" maxlength="150" required/>
                    </div>
                    <div class="form-group">
                        <label for="template">Шаблон</label><br/>
                        <select id="template" name="template" data-url="${pageContext.request.contextPath}/pages/email">
                            <option value="NONE" selected>--- Выбор ---</option>
                            <c:forEach items="${templates}" var="template">
                                <option value="${template}">${template}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="defaultInputBlock" class="form-group">
                        <label for="text">Текст:</label>
                        <textarea class="form-control" rows="12" id="text" name="textareaContent" maxlength="5000"></textarea>
                    </div>
                    <div id="templateBlock">
                        <div id="chosenTemplate" class="hidden">
                            ${chosenTemplate}
                        </div>
                        <div id="templateHiddenInputs" class="hidden">
                            <input type="text" id="chosenValue" value="${chosenValue}"/>
                        </div>
                    </div>
                </div>
            </div>

            <br/><br/><br/>

            <div class="row">
                <div class="col-md-3 col-md-offset-3 text-center">
                    <button id="sendButton" type="submit" data-url="${pageContext.request.contextPath}/pages/email/send" class="btn btn-success btn-lg button-size">Отправить</button>
                </div>
                <div class="col-md-1 text-center">
                    <button id="backButton" type="submit" class="btn btn-info btn-lg button-size">Назад</button>
                </div>
            </div>

        </form>

    </div>

    <div class="page-bottom"></div>

    <script src="${pageContext.request.contextPath}/resources/js/email/emailPage.js"></script>

</body>
</html>
