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
                <h1>Иванов Иван Иванович</h1>
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
                <img class="img-responsive" src="../../resources/images/default_avatar.png" alt="">
            </div>
            <div class="col-md-4">
                <ul class="group-text-block">
                    <li class="text-block">
                        <h3>Дата рождения</h3>
                        <p>01 января 1980</p>
                    </li>
                    <li class="text-block">
                        <h3>Пол</h3>
                        <p>мужской</p>
                    </li>
                    <li class="text-block">
                        <h3>Гражданство</h3>
                        <p>РБ</p>
                    </li>
                    <li class="text-block">
                        <h3>Семейное положение</h3>
                        <p>холост</p>
                    </li>
                </ul>
            </div>
            <div class="col-md-4">
                <ul class="group-text-block">
                    <li class="text-block">
                        <h3>Web Site</h3>
                        <p>www.website.my</p>
                    </li>
                    <li class="text-block">
                        <h3>Email</h3>
                        <p>myemail@mail.my</p>
                    </li>
                    <li class="text-block">
                        <h3>Место работы</h3>
                        <p>iTechArt</p>
                    </li>
                </ul>
            </div>
        </div>

        <hr>

        <div class="row">
            <h2>Адрес</h2>
            <ul>
                <li>Беларусь, г. Минск, ул. Независимости, д. 1, кв. 1, индекс 351001</li>
            </ul>
            <ul>
                <li>Беларусь, г. Минск, ул. Независимости, д. 1, кв. 1, индекс 351001</li>
            </ul>
        </div>

        <hr>
        <br>
        <br>

        <h2>Контактные телефоны</h2>

        <div class="row">
            <table class="table table-hover">
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="1" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">+375293333333</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">Мобильный</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">Не звонить после 10</div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="1" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">1234567</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">Домашний</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="1" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">8765432</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">Домашний</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">не звонить после 10</div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="1" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">+375297654321</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">Мобильный</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"></div>
                    </td>
                </tr>
            </table>
        </div>

        <br>
        <br>

        <h2>Список присоединений</h2>

        <div class="row">
            <table class="table table-hover">
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="1" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">FileName</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">01.01.1980</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">Время учёбы</div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="2" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">FileName2</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">01.01.1980</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="3" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">FileName3</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">01.01.1980</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">Время работы</div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="checkbox cell-alignment">
                            <label>
                                <input type="checkbox" value="4" aria-label="...">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"><a href="">FileName4</a></div>
                    </td>
                    <td>
                        <div class="cell-text-alignment">01.01.1980</div>
                    </td>
                    <td>
                        <div class="cell-text-alignment"></div>
                    </td>
                </tr>
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
