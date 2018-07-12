function fNumero(tecla) {
    if (tecla.charCode) {
        num_tecla = tecla.charCode;
        if (!(num_tecla >= 48 && num_tecla <= 57)) { return false; }
    } else {
        num_tecla = tecla.keyCode;
        if (!(num_tecla >= 48 && num_tecla <= 57) && //Números
                                       !(num_tecla >= 35 && num_tecla <= 40) && //Direcionais + END (35) + HOME (36)
                                       !(num_tecla == 8) && !(num_tecla == 9) && !(num_tecla == 13) // Voltar (8) + Tab (9) + Enter (13)
                                       ) { return false; }
    }
}

function fTexto(e) {
    var keynum = window.event ? e.keyCode : e.which;
    var keychar
    var numcheck
    keychar = String.fromCharCode(keynum)
    numcheck = /\d/
    return !numcheck.test(keychar)
}

function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    if (getCookie(c_name) != "") {
        var exdatedel = new Date();
        exdatedel.setDate(exdatedel.getDate() - 10);
        document.cookie = c_name + "=" + escape(value) + ";expires=" + exdatedel.toUTCString();
    }
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toUTCString());
}

function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}

function CarregaDropLookup(idDrop, url, selecionado, atributoPesquisa, fraseCarregando, fraseErroCarregar) {
    $("#" + idDrop).html('<option>' + fraseCarregando + '</option>');

    $.ajax({
        url: url,
        cache: false,
        success: function(data) {
            if (data == "e") {
                $("#" + idDrop).html('<option>' + fraseErroCarregar + '</option>');
                return;
            }
            var options = "";
            var j = data.split("#;#");

            for (var i = 0; i < j.length; i++) {
                options += '<option value="' + j[i].split('#@#')[0] + '" ' + selecionado + '>' + j[i].split('#@#')[1] + '</option>';
            }
            $("#" + idDrop).html(options);
            if (selecionado != "" && selecionado != undefined)
                $("#" + idDrop).find("option[" + atributoPesquisa + "='" + selecionado + "']").attr('selected', 'selected');
        }
    });
}

function ValidaEmail(email) {
    var er = new RegExp(/^[A-Za-z0-9_\-\.]+@[A-Za-z0-9_\-\.]{2,}\.[A-Za-z0-9]{2,}(\.[A-Za-z0-9])?/);
    if (er.test(email)) { return true; }
    return false;
}

function ValidaData(valor, cultura) {
    var formato = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;
    if (!formato.test(valor)) return false;
    var mes = cultura == "US" ? valor.split("/")[0] : valor.split("/")[1];
    var dia = cultura == "US" ? valor.split("/")[1] : valor.split("/")[0];
    var ano = valor.split("/")[2];
    var data = new Date(ano, mes - 1, dia);

    if ((data.getMonth() + 1 != mes) || (data.getDate() != dia) || (data.getFullYear() != ano))
        return false;

    return true;
}

function ValidarDataMaiorIgualQue(dataTeste, dataBase, formato) {
    var arrDataTeste = dataTeste.split("/");
    var arrDataBase = dataBase.split("/");

    var data1 = new Date(arrDataTeste[2], formato == "US" ? arrDataTeste[0] : arrDataTeste[1], formato == "US" ? arrDataTeste[1] : arrDataTeste[0]);
    var data2 = new Date(arrDataBase[2], formato == "US" ? arrDataBase[0] : arrDataBase[1], formato == "US" ? arrDataBase[1] : arrDataBase[0]);
    var one_day = 1000 * 60 * 60 * 24;

    var diff = Math.ceil((data1.getTime() - data2.getTime()) / (one_day));
    return (diff >= 0);
}


function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g, "");
}
function ltrim(stringToTrim) {
    return stringToTrim.replace(/^\s+/, "");
}
function rtrim(stringToTrim) {
    return stringToTrim.replace(/\s+$/, "");
}

function HabilitaHelp() {
    $("input[help]").live('focus', function() {
        $($(this).attr("help")).slideDown("fast");
    }).live('blur', function() {
        $($(this).attr("help")).slideUp("fast");
    });
}

function popup(url, w, h) {
    var width = w;
    var height = h;
    var left = (screen.width - width) / 2;
    var top = (screen.height - height) / 2;
    var params = 'width=' + width + ', height=' + height;
    params += ', top=' + top + ', left=' + left;
    params += ', directories=no';
    params += ', location=no';
    params += ', menubar=no';
    params += ', resizable=yes';
    params += ', scrollbars=yes';
    params += ', status=no';
    params += ', toolbar=no';
    newwin = window.open(url, 'windowname5', params);
    if (window.focus) { newwin.focus() }
    return false;
}

window.SessionTimeout = (function () {
    var _timeLeft, _popupTimer, _countDownTimer, _urlTimeout, _divMsg, _sessionTimeout;

    var stopTimers = function () {
        window.clearTimeout(_popupTimer);
        window.clearTimeout(_countDownTimer);
    };

    var updateCountDown = function () {
        var min = Math.floor(_timeLeft / 60);
        var sec = _timeLeft % 60;
        if (sec < 10)
            sec = "0" + sec;
        $(".CountDownHolder").html(min + ":" + sec);
        if (_timeLeft > 0) {
            _timeLeft--;
            _countDownTimer = window.setTimeout(updateCountDown, 1000);
        } else {
            CloseSession();
        }
    };

    var showPopup = function () {
        $.fancybox({
            'autoSize': true,
            'autoResize': true,
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'modal': true,
            'content': _divMsg
        });
        _timeLeft = 60;
        updateCountDown();
    };

    var schedulePopup = function (delay, urlTimeout, msg) {
        _urlTimeout = urlTimeout;
        _divMsg = msg;
        _sessionTimeout = delay;
        stopTimers();
        _popupTimer = window.setTimeout(showPopup, delay * 60000);
    };

    var sendKeepAlive = function () {
        $.fancybox.close();
        $.ajax({
            url: _urlTimeout,
            cache: false
        });
        stopTimers();
        SessionTimeout.schedulePopup(_sessionTimeout, _urlTimeout, _divMsg);
    };

    return {
        schedulePopup: schedulePopup,
        sendKeepAlive: sendKeepAlive
    };

})();

if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (searchElement, fromIndex) {
        var i,
        pivot = (fromIndex) ? fromIndex : 0,
        length;

        if (!this) {
            throw new TypeError();
        }

        length = this.length;

        if (length === 0 || pivot >= length) {
            return -1;
        }

        if (pivot < 0) {
            pivot = length - Math.abs(pivot);
        }

        for (i = pivot; i < length; i++) {
            if (this[i] === searchElement) {
                return i;
            }
        }
        return -1;
    };
}