/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
ajustaNumero = (function(e) {
    while (e.value.length < 7) {
        e.value = '0' + e.value;
    }
});