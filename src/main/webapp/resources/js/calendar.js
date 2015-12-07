/*calendar funcs*/

    /*объявим массив ссылок на все календари объявляемые на странице */
    var calendarsObjs = [];
    register_new_calendar('birthday', 'ru');

    /*эта функция служит для регистрации нового календаря, idobj - id контейна DIV.calendarHolder,
     а lang - язык (в данном примере ru - для русского, иначе английский), чтобы можно было показать
     наименования месяцев на понятном пользователю языке*/
    function register_new_calendar(idobj, lang) {
        var obj = document.getElementById(idobj);
        a = new CalendarObj(obj, lang);
        calendarsObjs.push(a);
        return a;
    }

    /*ещё одно общая функция, позволяющая найти экземпляр класса по одному из DOM элементов -
     указанному INPUT или DIV.calendarHolder, параметр index - указывает вернуть сам объект или только его
     индекс в массиве calendarsObjs */
    function find_CalendarObj_class(Obj, index) {
        for (var i = calendarsObjs.length - 1; i >= 0; i--) {
            if (calendarsObjs[i].htmlContainer == Obj || calendarsObjs[i].inputObj == Obj) return index ? i : calendarsObjs[i];
        }
        return null;
    }

    /* сама функция класса календарика, входные параметры такие же
     как и у функции-регистратора - register_new_calendar */
    function CalendarObj(container, lang) {
        this.htmlContainer = container;
        this.widget = null; //указатель на контейнер DIV.widgetCalendar
        this.inputObj = null; //указатель на элемент формы INPUT, где хранится текстовое значение выбранной даты
        this.lang = lang; //язык, на котором отображаются названия месяцев года

        /*функция - обработчик некого события, когда мы хотим прятать календарик. Не используется
         в данном случае, но если вы найдете ей применение - то можете прицепить на какое либо событие
         в качестве обработчика */
        this.hideCalendar = function (ev) {
            targ = (ev.target) ? ev.target : ev.target = ev.srcElement;
            obj = find_CalendarObj_class(targ.parentNode, false);
            if (obj != null)
                obj.widget.style.display = 'none';
            else
                alert('Calendar object error');
        };

        /*функция - обработчик события onfocus нашего текстового поля. Показывает календарик.
         Ниже мы прицепим данную функцию как обработчик. Входным параметром является объект
         Event. */
        this.showCalendar = function (ev) {
            targ = (ev.target) ? ev.target : ev.target = ev.srcElement;
            obj = find_CalendarObj_class(targ.parentNode, false);
            if (obj != null)
                obj.doWidget(0, 0);
            else
                alert('Calendar object error');
        };
        /* Функция возвращает объект Date, который соответствует значению введенному в текстовое поле.
         Если формат даты не удаётся распознать - возвращает текущую дату.*/
        this.getDate = function () {
            var dtStr = this.inputObj.value;
            var expr = /([\d]{1,2}).([\d]{1,2}).([\d]{4})/;
            var res = expr.exec(dtStr);
            var D = new Date();
            if (res != null) {
                var D = new Date(res[3], res[2] - 1, res[1]);
                if (D.toString() == 'Invalid Date') D = new Date();
            }
            return D;
        };
        /* Функция устанавливает строковое значение в текстовом поле, используя для этого
         входной параметр DT типа Date */
        this.setDate = function (DT) {
            var mn = DT.getMonth() + 1;
            var da = DT.getDate();
            this.inputObj.value = (da < 10 ? '0' : '') + da + '.' + (mn < 10 ? '0' : '') + mn + '.' + DT.getFullYear();
        };
        /* Тоже устанавливает дату в текстовом поле, но входным параметром является число милисекунд,
         а также функция прячет календарик - используем эту функцию при выборе значения в календаре */
        this.setNHide = function (DTint) {
            var DT = new Date(DTint);
            this.setDate(DT);
            this.widget.style.display = 'none';
        };
        /* функция создаёт html код календарика и показывает его. На входе два параметра, которые предназначены для
         смещения текущего значения месяца - mn, и года - ye. Задаются как дельта, т.е. +1, -1 или 0, если смещения нет */
        this.doWidget = function (mn, ye) { /* mn = +-1 */
            var dt = this.getDate();
            var currentDate = new Date(dt.getFullYear() + ye, dt.getMonth() + mn, dt.getDate());
            var index = find_CalendarObj_class(this.htmlContainer, true);
            var ws = "";
            switch (this.lang) {
                case 'ru':
                    var mntArray = ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'];
                    break;
                default:
                    var mntArray = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
                    break;
            }
            /* HTML код календарика, помимо календаря, содержит у нас две прокрутки - по месяцам и по годам.
             Зная индекс экземпляра класса в общем массиве - мы будет вызывать нужную построения календарика
             на-прямую. */
            ws = '<div onclick="calendarsObjs[' + index + '].widget.style.display = \'none\'" class="close">x</div>';
            ws += '<div class="clear"> </div>';
            ws += '<div style="text-align: center;">';
            ws += '  <a class="monthSlide" href="#" onclick="calendarsObjs[' + index + '].doWidget(-1, 0);"><</a>';
            ws += '  <div class="monthTab" onclick="calendarsObjs[' + index + '].doWidget(0, 0);">' + mntArray[currentDate.getMonth()] + '</div>';
            ws += '  <a class="monthSlide" href="#" onclick="calendarsObjs[' + index + '].doWidget(1, 0);">></a>';
            ws += '</div>';
            ws += '<div class="clear"> </div>';
            ws += '<div style="text-align: center;">';
            ws += '  <a class="monthSlide" href="#" onclick="calendarsObjs[' + index + '].doWidget(0, -1);"><</a>';
            ws += '  <div class="monthTab" onclick="calendarsObjs[' + index + '].doWidget(0, 0);">' + currentDate.getFullYear() + '</div>';
            ws += '<a class="monthSlide" href="#" onclick="calendarsObjs[' + index + '].doWidget(0, 1);">></a>';
            ws += '</div>'
            ws += '<div class="clear"> </div>';
            ws += '<div class="month">';

            dtm = [currentDate.getMonth(), currentDate.getFullYear()];

            dateF = new Date(dtm[1], dtm[0], 1);
            dateL = new Date(dtm[1], dtm[0] + 1, 1);
            /*сместимся от первого числа текущего месяца к ближайшему понедельнику.
             Смещаемся назад по времени, т.к. не хотим потерять неделю текущего месяца. */
            wd = dateF.getDay() - 1;
            switch (wd) {
                case -1:
                    dateCRS = new Date(dtm[1], dtm[0], -5);
                    break;
                default:
                    dateCRS = new Date(dtm[1], dtm[0], 1 - wd);
            }
            /*Собираем месяц в отдельном аккумуляторе - строке (для оптимизации, можно было сразу и в ws добавлять) */
            var out = '<div class="clear"> </div>';
            var nowDate = new Date();
            var nowDateStr = nowDate.getFullYear() + '/' + (nowDate.getMonth() + 1) + '/' + nowDate.getDate();
            /* цикл по неделям */
            while (dateCRS.getTime() <= dateL.getTime()) {
                k = 7;
                /* цикл по дням недели */
                while (k-- > 0) {
                    /* стилями зададим раскраску отдельных дней - текущий день, а также день из другого месяца*/
                    addStyle = nowDateStr == dateCRS.getFullYear() + '/' + (dateCRS.getMonth() + 1) + '/' + dateCRS.getDate() ? 'curr' : '';
                    if (dateCRS < dateF || dateCRS >= dateL) addStyle += ' nonM';
                    /* при выборе для вызовем ф-цию установки даты */
                    out += '<div class="dayH"><a href="#" onclick="calendarsObjs[' + index + '].setNHide('
                        + dateCRS.valueOf() + ')"' + ' class="day ' + addStyle + '">' + dateCRS.getDate() + '</a></div>';
                    dateCRS.setDate(dateCRS.getDate() + 1);
                }
                out += '<div class="clear"> </div>';
            }
            //HTML календаря собран, объединим все в одной переменной
            ws += out + '</div>';
            /*устанавливаем дату, показываем календарик */
            this.setDate(currentDate);
            this.widget.innerHTML = ws;
            this.widget.style.display = 'block';
        };

        /* ищем составные части нашего DOM объекта */
        for (var i = container.childNodes.length - 1; i >= 0; i--) {
            var t = container.childNodes[i];
            if (t.className == 'widgetCalendar' && t.tagName == 'DIV') {
                t.style.width = this.htmlContainer.style.width;
                this.widget = t;
            }
            else if (t.tagName == 'INPUT' && t.type == 'text') {
                this.inputObj = t;
                //когда обнаружен элемент INPUT - приаттачим к нему событие onfocus, как и собирались
                if (t.addEventListener) t.addEventListener('focus', this.showCalendar, false);
                else t.attachEvent("onfocus", this.showCalendar);
            }
        }
        /* тут бы логично предупреждение показывать, если inputObj или widget не найден.
         Но, сейчас это делать не охота :) Если хотите, напишите сами. */
    }
