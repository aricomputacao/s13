/**
 * Ocultar o login em navegadores não suportados
 * Necessária a biblioteca do jQuery
 */

/**
 * Cria a função
 * @returns {undefined}
 */
testaNavegador = (function() {
    //if (jQuery.browser.mozilla) {
//
//} else
    if (jQuery.browser.msie) {
        $('#pnlogin').hide();
        $('#msg').show();
//} else if (jQuery.browser.safari) {
//
//} else if (jQuery.browser.opera) {
//
//} else if (jQuery.browser.chrome) {

    } else {
        $('#msg').hide();
    }
});

/**
 * Chama a função
 */
testaNavegador();