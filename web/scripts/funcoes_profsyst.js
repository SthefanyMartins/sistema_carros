/**
 * Validação de um único campo
 */
function validaCampo(nomeCampo, campo) {
    if (document.getElementById(campo).value == '') {
        alert(nomeCampo + ' é obrigatório.');
        return false;
    } else {
        return true;
    }
}

function pegaTeclaEvento(evento) {
    if (evento.keyCode == 0) {
        return evento.charCode;
    } else {
        return evento.keyCode;
    }
}

function teclaSeta(tecla) {
    if (tecla == 37)
        return true;
    if (tecla == 38)
        return true;
    if (tecla == 39)
        return true;
    if (tecla == 40)
        return true;
    return false;
}

function limitarCampo(tamanhoMaximo, obj, evento){
    if ((evento.keyCode == 46) && (evento.charCode == 0)) return true; // Colocado devido a diferenças de navegadores
    if ((evento.keyCode == 8) && (evento.charCode == 0)) return true; // Colocado devido a diferenças de navegadores
    var tecla = pegaTeclaEvento(evento);
    if (obj.value.length >= tamanhoMaximo) {
        if (!teclaSeta(tecla)) {
            return false;
        }
    }
    return true;
}

function limitarDecimais(obj, casas, evento){
    if ((evento.keyCode == 46) && (evento.charCode == 0)) return true; // Colocado devido a diferenças de navegadores
    if ((evento.keyCode == 8) && (evento.charCode == 0)) return true; // Colocado devido a diferenças de navegadores
    var tecla = pegaTeclaEvento(evento);
    var valorCampo = obj.value;
    if (!teclaSeta(tecla)) {
        if (valorCampo.indexOf(',') >= 0)
            valorCampo = valorCampo.replace(',', '.');
        if (valorCampo.indexOf('.') >= 0) {
            if (posicaoCursor(obj) <= valorCampo.indexOf('.')) {
                return true;
            }
            var divisao = valorCampo.split('.');
            if (divisao[1].length >= casas) {
                return false;
            }
        }
    }

    if ((String.fromCharCode(tecla) == '.') || (String.fromCharCode(tecla) == ',')) {
        if ((valorCampo.indexOf(',') >= 0) || (valorCampo.indexOf('.') >= 0))
            return false;
    }
    return true;
}

function posicaoCursor(el) {
    if ('selectionStart' in el) {
        return el.selectionStart;
    } else if (document.selection) {
        el.focus();

        var r = document.selection.createRange();
        if (r == null) {
            return 0;
        }

        var re = el.createTextRange(),
        rc = re.duplicate();
        re.moveToBookmark(r.getBookmark());
        rc.setEndPoint('EndToStart', re);

        var add_newlines = 0;
        for (var i=0; i<rc.text.length; i++) {
            if (rc.text.substr(i, 2) == '\r\n') {
                add_newlines += 2;
                i++;
            }
        }
        //We need to substract the no. of lines
        return rc.text.length - add_newlines;
    } else {
        return 0;
    }
}

//funcoes de ajuste de foco pela tecla ETNER
function ajstaFoco(e){
    var keyCode = e.which? e.which : event.keyCode;
                   
    if(keyCode == 9 || keyCode == 13){
        var nextSib = this.nextSibling;
        while(nextSib){
            if(nextSib.tagName == 'INPUT' 
                && (nextSib.getAttribute('type').toLowerCase() == 'text' 
                    || nextSib.getAttribute('type').toLowerCase() == 'checkbox'
                    || nextSib.getAttribute('type').toLowerCase() == 'radio')
                && nextSib.name.toLowerCase()!=e.currentTarget.name.toLowerCase()){
                nextSib.focus();
                return;
            }
            nextSib = nextSib.nextSibling;
        }
    }
}

function adicionaEvento(elemento, evento, funcao, bool){
    bool = (bool == null)? false : bool;
    if(elemento.addEventListener)
        elemento.addEventListener(evento, funcao, bool);
    else
        elemento.attachEvent('on' + evento, funcao);
}
