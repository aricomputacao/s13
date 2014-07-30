/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// Alterada para receber as teclas do teclado numerico e o tab
soNumero = (function(e, c) {
    var tecla = (window.event) ? event.keyCode : e.which;
//    console.log(tecla);
    if ((tecla > 47 && tecla < 58) || tecla > 95 && tecla < 106) {
        return true;
    } else {
        // 8 enter 9 tab 0?
        if (tecla == 8 || tecla == 0 || tecla == 9) {
            return true;
        }
        else {
            return false;
        }
    }
});

ajustaNumero = (function(e) {
    while (e.value.length < 7) {
        e.value = '0' + e.value;
    }
});


//soNumero = (function(e, c) {
//    var tecla = (window.event) ? event.keyCode : e.which;
//    if (tecla > 47 && tecla < 58) {
//        return true;
//    } else {
//        if (tecla == 8 || tecla == 0) {
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//
//});
