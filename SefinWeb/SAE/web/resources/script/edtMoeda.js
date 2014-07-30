/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
mascaraInputs = (function() {
    $('.edtMoeda').maskMoney({symbol: 'R$ ', thousands: '.', decimal: ',', symbolStay: true, allowZero: true});
});

mascaraQuantidadeInteira = (function() {
    $('.edtQtdInt').maskMoney({decimal: ',', symbol: '', thousands: '.', allowZero: false, precision: 4});
});

$(document).ready(function() {
    mascaraQuantidadeInteira();
    mascaraInputs();
});

