function showError(span, msg, input) {
    if (msg) {
        $(span).css('color', 'red').html(msg); // colorear span en rojo
        if (input) {
            $(input).css('border-color', 'red').focus(); // si hay input, que reclame foco
        }
    } else {
        $(span).css('color', '').html(''); // quitar color rojo
        if (input) {
            $(input).css('border-color', ''); // si hay input, quitar borde rojo
        }
    }
}

function showMsg(span, msg, input) {
    if (msg) {
        $(span).css('color', 'green').html(msg);
    } else {
        $(span).css('color', '').html('');
    }
    if (input) {
        $(input).css('border-color', '');
    }
}

//function isNumeric(valStr){
//    for(var i=0; i<valStr.length; i++){
//        if(valStr[i]<'0' || valStr[i]>'9'){
//            return false;
//        }
//    }
//    return true;
//}

function isNumeric(valStr) {
    return /^\+?(0|[1-9]\d*)$/.test(valStr)
}

function parseMoney(moneyStr){
    return parseInt((""+moneyStr).replace('$','').replace('.','').replace(',','.'));
}

function isEmail(emailStr){
    return /^(.+)@(.+)\.(.+)$/.test(emailStr);
}

function isPhoneNumber(phoneNumberStr){
    return /^[0-9]{7,12}$/.test(phoneNumberStr);
}