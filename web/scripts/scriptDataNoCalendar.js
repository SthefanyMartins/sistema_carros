/**
* Mascara Dinâmica
*/
function mask(isNum, event, field, mask, maxLength) {

    var keyCode;
    if (event.srcElement)
        keyCode = event.keyCode;
    else if (event.target)
        keyCode = event.which;
       
    var maskStack = new Array();
       
    var isDynMask = false;
    if (mask.indexOf('[') != -1)
        isDynMask = true;
               
    var length = mask.length;
   
    for (var i = 0; i < length; i++)
        maskStack.push(mask.charAt(i));
       
    var value = field.value;
    var i = value.length;
   
    if (keyCode == 0 || keyCode == 8)
        return true;

    //código adaptado para aceitar X (maiúsculo) ou x (minúsculo), além de números
    if (isNum && (keyCode < 48 || keyCode > 57))
        return false;
   
    if (!isDynMask && i < length ) {
       
        if (maskStack.toString().indexOf(String.fromCharCode(keyCode)) != -1 && keyCode != 8) {
            return false;
        } else {
            if (keyCode != 8) {
                if (maskStack[i] != '#') {
                    var old = field.value;
                    field.value = old + maskStack[i];
                }           
            }
           
            if (autoTab(field, keyCode, length)) {
                if (!document.layers) {
                    return true;
                } else if (keyCode != 8) {
                    field.value += String.fromCharCode(keyCode);
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }               
        }
       
    } else if (isDynMask && i < length) {
                           
        var maskChars = "";
        for (var j = 0; j < maskStack.length; j++)
            if (maskStack[j] != '#' && maskStack[j] != '[' && maskStack[j] != ']')
                maskChars += maskStack[j];

        var tempValue = "";
        for (var j = 0; j < value.length; j++) {
            if (maskChars.indexOf(value.charAt(j)) == -1)
                tempValue += value.charAt(j);
        }
       
        value = tempValue + String.fromCharCode(keyCode);
                       
        if (maskChars.indexOf(String.fromCharCode(keyCode)) != -1) {
            return false;
        } else {
       
            var staticMask = mask.substring(mask.indexOf(']') + 1);
            var dynMask = mask.substring(mask.indexOf('[') + 1, mask.indexOf(']'));
       
            var realMask = new Array;
       
            if (mask.indexOf('[') == 0) {
                var countStaticMask = staticMask.length - 1;
                var countDynMask = dynMask.length - 1;
                for (var j = value.length - 1; j >= 0; j--) {
                    if (countStaticMask >= 0) {
                        realMask.push(staticMask.charAt(countStaticMask));
                        countStaticMask--;
                    }
                    if (countStaticMask < 0) {
                        if (countDynMask >= 0) {
                            if (dynMask.charAt(countDynMask) != '#') {
                                realMask.push(dynMask.charAt(countDynMask));
                                countDynMask--;
                            }
                        }
                        if (countDynMask == -1) {
                            countDynMask = dynMask.length - 1;
                        }
                        realMask.push(dynMask.charAt(countDynMask));
                        countDynMask--;
                    }
                }
            }
           
            var result = "";
               
            var countValue = 0;
            while (realMask.length > 0) {
                var c = realMask.pop();   
                if (c == '#') {
                    result += value.charAt(countValue);
                    countValue++;   
                } else {
                    result += c;
                }
            }
           
            field.value = result;
       
            if (maxLength != undefined &&  value.length == maxLength) {
               
                var form = field.form;
                for (var i = 0; i < form.elements.length; i++) {
                    if (form.elements[i] == field) {
                        field.blur();
                        //if alterado para quando a máscara for utilizada no último campo, não dê mensagem de erro quando tentar colocar o foco no "Salvar"
                        //if (form.elements[i + 1] != null)                                         
                        if ((form.elements[i + 1] != null) && (form.elements[i + 1].name != "METHOD"))
                            form.elements[i + 1].focus();
                        break;
                    }
                }
            }
           
            return false;
        }
    } else {
        return false;
    }
}

/**
 * se muda ou não de campo
 *
 */
function autoTab(field, keyCode, length) {
    var i = field.value.length;
       
    if (i == length - 1) {
   
        field.value += String.fromCharCode(keyCode);
   
        var form = field.form;
        for (var i = 0; i < form.elements.length; i++) {
            if (form.elements[i] == field) {
                field.blur();                                         
                //if alterado para quando a máscara for utilizada no último campo, não dê mensagem de erro quando tentar colocar o foco no "Salvar"
                //if (form.elements[i + 1] != null)
                if ((form.elements[i + 1] != null) && (form.elements[i + 1].name != "METHOD"))
                    form.elements[i + 1].focus();
                break;
            }
        }
       
        return false;
    } else {
        return true;
    }   
}

/**
 * Marca para Data sem horas.
 */
function mascaraData(campo) {
    return "##/##/####"; // 00/00/0000
}