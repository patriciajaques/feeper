function Validador(args) {
    this.erros = [];
    this.submitErros = [];
    this.submitSuccess = [];
    this.msg = args.msg !== undefined ? args.msg : {};
    this.divErro = args.divErro;
    this.form = args.form;
    this.seletor = args.seletor;
    this.metodos = args.metodos !== undefined ? args.metodos : [];
    var _self = this;
    var _blurRegistrado = false;

    this.form.submit(function () {
        var retorno = _self.validaForm();
        if (!retorno) {
            for (i = 0; i < _self.submitErros.length; i++)
                _self.submitErros[i]();
        }
        else {
            for (i = 0; i < _self.submitSuccess.length; i++)
                _self.submitSuccess[i]();
        }
        return retorno;
    });

    _self.onError = function (args) {
        _self.erros.push(args.fn);
    }

    _self.onSubmitError = function (args) {
        _self.submitErros.push(args.fn);
    }

    _self.onSubmitSuccess = function (args) {
        _self.submitSuccess.push(args.fn);
    }

    _self.novoMetodo = function (args) {
        _self.metodos.push(args.fn);
        if (args.evento != null && args.evento !== undefined) {
            args.seletor.bind(args.evento, function (e) {
                _self.verificaCamposMetodo(args.fn);
            });
        }
        else {
            args.seletor.blur(function () {
                _self.verificaCamposMetodo(args.fn);
            });
        }
    }

    _self.validaForm = function () {
        if (!_blurRegistrado)
            $("." + _self.seletor, _self.form).blur(function () {
                _blurRegistrado = true;
                _self.verificaCampos();
            });

        if (_self.verificaCampos()) {
            $("input[type=submit]", _self.form).attr("disabled", "disabled");
            $("input[type=button]", _self.form).attr("disabled", "disabled");
            return true;
        }

        return false;
    }

    _self.verificaCampos = function () {
        var erros = "";

        if (_self.metodos !== undefined && _self.metodos.length > 0)
            for (i = 0; i < _self.metodos.length; i++) {
                if (erros == "")
                    erros = _self.metodos[i]();
                else
                    break;
            }

        if (erros == "") erros = _self.isEmail();
        if (erros == "") erros = _self.isDecimal();
        if (erros == "") erros = _self.caracterRepetido();
        if (erros == "") erros = _self.obrigatorio();

        if (erros != "") {
            _self.divErro.html(erros).slideDown("fast");
            for (i = 0; i < _self.erros.length; i++)
                _self.erros[i]();
            return false;
        }
        else {
            _self.divErro.slideUp("fast").html("");
            return true;
        }
    }

    _self.verificaCamposMetodo = function (fn) {
        var erros = "";

        if (fn !== undefined)
            erros += fn();

        if (erros != "") {
            _self.divErro.html(erros).slideDown("fast");
            for (i = 0; i < _self.erros.length; i++)
                _self.erros[i]();
            return false;
        }
        else {
            if ($(".error", _self.form).length == 0)
                _self.divErro.slideUp("fast");
            else {
                _self.verificaCampos();
            }
            return true;
        }
    }

    _self.obrigatorio = function () {
        var mensagem = (_self.msg.obrigatorio !== undefined ? _self.msg.obrigatorio : "Campos obrigatórios:") + "<br><ul>";
        var campos = "";
        var validatedRadios = [];
        $(".obrigatorio", _self.form).each(function () {
            var validation = "";
            var type = $(this).attr("type");
            if (type !== undefined && type.toLowerCase() == "radio") {
                var radioGroup = $(this).attr("name");

                if (validatedRadios.indexOf(radioGroup) != -1) return true;

                var checkedRadio = $(".obrigatorio[type='" + type + "'][name='" + radioGroup + "']:checked", _self.form);
                if (checkedRadio.length == 0) {
                    $("#dv" + radioGroup).addClass("error");
                    campos += "<li>" + $(this).attr("title") + "</li>";
                }
                else {
                    $("#dv" + radioGroup).removeClass("error");
                }
                validatedRadios.push(radioGroup);
            }
            else if (type !== undefined && type.toLowerCase() == "checkbox") {
                var checkName = $(this).attr("name");
                var checkedCheckbox = $(".obrigatorio[type='" + type + "']:checked", _self.form);
                if (checkedCheckbox.length == 0) {
                    $("#dv" + checkName).addClass("error");
                    campos += "<li>" + $(this).attr("title") + "</li>";
                }
                else {
                    $("#dv" + checkName).removeClass("error");
                }
            }
            else if (this.type !== undefined && this.type.toLowerCase() == "select-multiple") {
                if (this.selectedIndex == -1 || this.options[this.selectedIndex].value == "") {
                    $(this).addClass("error");
                    campos += "<li>" + $(this).attr("title") + "</li>";
                }
                else {
                    $(this).removeClass("error");
                }
            }
            else {
                validation = $(this).val().replace(/^\s+|\s+$/g, "");
                if (validation == "") {
                    $(this).addClass("error");
                    campos += "<li>" + $(this).attr("title") + "</li>";
                }
                else {
                    $(this).removeClass("error");
                }
            }
        });
        if (campos != "") return mensagem + campos + "</ul>";
        return "";
    }

    _self.isDecimal = function () {
        var mensagem = (_self.msg.isDecimal !== undefined ? _self.msg.isDecimal : "Campos com valores decimais inválidos:") + "<br><ul>";
        var campos = "";
        $(".decimal", _self.form).each(function () {
            if ($(this).val() != "") {
                value = $(this).val().replace(/^\s+|\s+$/g, "");
                value = value.replace(/\./gi, "");
                if (!(/^[-+]?[0-9]+(\,[0-9]+)?$/.test(value))) {
                    $(this).addClass("error");
                    campos += "<li>" + $(this).attr("title") + "</li>";
                }
            }
            else {
                $(this).removeClass("error");
            }
        });
        if (campos != "") return mensagem + campos + "</ul>";
        return "";
    }

    _self.caracterRepetido = function () {
        var mensagem = (_self.msg.caracterRepetido !== undefined ? _self.msg.caracterRepetido : "Campos com caracteres repetidos:") + "<br><ul>";
        var campos = "";
        $(".caracter-repetido", _self.form).each(function () {
            if ($(this).val() != "") {
                var str = $(this).val().toString().toUpperCase();
                if (str.length >= 3) {
                    for (var i = str.toString().length - 1; i >= 2; i--) {
                        if ((str.toLocaleString().charAt(i) == str.toLocaleString().charAt(i - 1)) && (str.toLocaleString().charAt(i) == str.toLocaleString().charAt(i - 2))) {
                            $(this).addClass("error");
                            campos += "<li>" + $(this).attr("title") + "</li>";
                            break;
                        }
                    }
                }
            }
            else {
                $(this).removeClass("error");
            }
        });
        if (campos != "") return mensagem + campos + "</ul>";
        return "";
    }

    _self.isEmail = function () {
        var mensagem = (_self.msg.isEmail !== undefined ? _self.msg.isEmail : "Campos com E-mail inválidos:") + "<br><ul>";
        var campos = "";
        $(".email", _self.form).each(function () {
            if ($(this).val() != "") {
                value = $(this).val().replace(/^\s+|\s+$/g, "");

                var patt = /^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
                if (!patt.test(value)) {
                    $(this).addClass("error");
                    campos += "<li>" + $(this).attr("title") + "</li>";
                }
            }
            else {
                $(this).removeClass("error");
            }
        });
        if (campos != "") return mensagem + campos + "</ul>";
        return "";
    }
}