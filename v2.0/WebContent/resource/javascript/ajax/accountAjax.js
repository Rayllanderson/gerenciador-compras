$(".alert").hide();
$(".alertE").hide();


$('#change-pass').on('click', function () {
    let old = $('#old').val();
    let new1 = $('#new1').val();
    let new2 = $('#new2').val();
    let modal = $('#exampleModal')

    if (old && new1 && new2) {
        $.ajax({
            method: "POST",
            url: "my-account?action=change-password",
            data: {
                old: old,
                new1: new1,
                new2: new2
            }
        }).done(function () {
            alertBoostrap("Sua senha foi alterada com sucesso", 'alert alert-success', "Sucesso")
            $('#exampleModal').modal('hide')
            $.get("my-account?action=view")
            modal.find('#old').val('')
            modal.find('#new1').val('')
            modal.find('#new2').val('')
        }).fail(function (xhr) {
            alertBoostrapError(xhr.responseText, 'alert alert-danger', "Erro")
        });
    } else {
        alertBoostrapError("Um ou mais campos estão nulos", 'alert alert-danger', "Erro")
    }
});


if (error) {
    alertBoostrap(error, 'alert alert-danger')
    error = '';
}

if (success) {
    alertBoostrap(success, 'alert alert-success')
    success = '';
}

function alertBoostrap(msg, classe) {
    $(".alert").show();
    document.getElementById('alertMsg').innerHTML = msg;
    document.getElementById("success-alert").className = classe;
    $("#success-alert").fadeTo(2700, 500).slideUp(500, function () {
        $("#success-alert").slideUp(500);
    });
}

function alertBoostrapError(msg, classe) {
    $("#alertE").show();
    document.getElementById('alertMsgE').innerHTML = msg;
    document.getElementById("alertE").className = classe;
    $("#alertE").fadeTo(2700, 500).slideUp(500, function () {
        $("#alertE").slideUp(500);
    });
}

function passwordEye(element) {
    $(element + ' a').on('click', function (event) {
        event.preventDefault();
        if ($(element + ' input').attr("type") == "text") {
            $(element + ' input').attr('type', 'password');
            $(element + ' i').addClass("fa-eye-slash");
            $(element + ' i').removeClass("fa-eye");
        } else if ($(element + ' input').attr("type") == "password") {
            $(element + ' input').attr('type', 'text');
            $(element + ' i').removeClass("fa-eye-slash");
            $(element + ' i').addClass("fa-eye");
        }
    });
};

passwordEye('#show_hide_password')
passwordEye('#show_hide_password2')
passwordEye('#show_hide_password3')


