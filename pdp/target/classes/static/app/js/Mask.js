$(document).ready(function () {
    $('.decimal').mask("#.##0,00", {reverse: true});
    
    //máscara telefone 8 ou 9 dígitos
    var behavior = function (val) {
        return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
    },
    options = {
        onKeyPress: function (val, e, field, options) {
            field.mask(behavior.apply({}, arguments), options);
        }
    };

    $('.telefone').mask(behavior, options);
    //fim máscara telefone 8 ou 9 dígitos
});