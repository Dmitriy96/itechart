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

        <form method="post" action="${pageContext.request.contextPath}/pages/email/send" role="form">

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="recipients">Кому</label>
                        <input type="text" class="form-control" id="recipients" value="${emails}" name="recipients" placeholder="Кому">
                    </div>
                    <div class="form-group">
                        <label for="subject">Тема</label>
                        <input type="text" class="form-control" id="subject" name="subject" placeholder="Тема">
                    </div>
                    <div class="form-group">
                        <label for="template">Шаблон</label><br/>
                        <select id="template">
                            <option value="NONE">--- Выбор ---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="text">Текст:</label>
                        <textarea class="form-control" rows="12" id="text" name="text"></textarea>
                    </div>
                </div>
            </div>

            <br/><br/><br/>

            <div class="row">
                <div class="col-md-3 col-md-offset-3 text-center">
                    <button type="submit" class="btn btn-success btn-lg button-size">Отправить</button>
                </div>
                <div class="col-md-1 text-center">
                    <button type="submit" class="btn btn-info btn-lg button-size">Назад</button>
                </div>
            </div>

        </form>

    </div>

    <div class="page-bottom"></div>

</body>
</html>
