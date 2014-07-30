/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
popup = (function(pagina) {
    window.open(pagina, '_blank', 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=850,height=700');
});

relatorio = (function() {
    window.setTimeout("abrePopup(pagina)", 1000);
});

function setFocus(id)
{
    document.getElementById(id).focus();
}
