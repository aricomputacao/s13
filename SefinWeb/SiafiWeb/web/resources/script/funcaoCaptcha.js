/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var i;
atualizaCapcha = (function() {
    $.ajax({
        url: "/SiafiWeb/GerarCaptcha",
        type: 'POST',
        global: false
    }).success(function(res) {
//        var ret = JSON.parse(res);
        criarCaptacha('txt', res);
        i = res;
//        criarCaptacha('canvas2', ret.texto);

    });

});
$(document).ready(function() {
    atualizaCapcha();
});


criarCaptacha = (function(id, texto) {
    var canvas = document.getElementById(id);
    if (canvas != null) {
        var context = canvas.getContext("2d");
        context.fillStyle = "rgb(240, 240, 240)";
        context.fillRect(0, 0, canvas.width, canvas.height);
        context.fillStyle = "rgb(250, 50,50)";
        context.font = "24px Walkway";
        context.fillText(texto, 10, 25);
    }
});

verificaCaptcha = (function() {
    
    
    if ($("#j_username").val() == "") {
        mensagem("Informe o login.",0);
        $("#j_username").focus();
        return;
    }

    if ($("#captcha").val() == "") {
        mensagem("Informe o código de verificação.",3);
        $("#txt").focus();
        return;
    }

    if (new String($("#captcha").val()).toUpperCase() == i) {
        document.frm.submit();
       
    }else{
           

    }
});

mensagem = (function(mensagem, tipo) {
    switch (tipo) {
        case 0:
            $(".login").html("<div class='alert alert-danger alerta'></span><button type='button' class='close' data-dismiss='alert'></button><span class='glyphicon glyphicon-remove-sign'></span>   " + mensagem + "</div>");
//            setTimeout(ocultaMensagem, 3000);
            break;
        case 1:
            $(".alerta").html("<div class='alert alert-warning alerta'><button type='button' class='close' data-dismiss='alert'>×</button><span class='glyphicon glyphicon-warning-sign'></span>  " + mensagem + "</div>");
//            setTimeout(ocultaMensagem, 3000);
            break;
        case 2:
            $(".alerta").html("<div class='alert alert-success alerta'><button type='button' class='close' data-dismiss='alert'>×</button><span class='glyphicon glyphicon-exclamation-sign'></span>  " + mensagem + "</div>");
//            setTimeout(ocultaMensagem, 3000);
            break;
        default :
            $(".alerta").html("<div class='alert alert-info alerta'><button type='button' class='close' data-dismiss='alert'>×</button><span class='glyphicon glyphicon-info-sign'></span>  " + mensagem + "</div>");
//            setTimeout(ocultaMensagem, 3000);
    }
});

