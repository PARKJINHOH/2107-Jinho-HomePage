/**
 * 이메일
 */
$(function () {
    $("input[name='email']").blur(function () {
        const regex = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
        let email = $(this).val();

        // 값을 입력안한경우는 아예 체크를 하지 않는다.
        if (email === '' || email === 'undefined')
            return false;

        // 이메일 유효성 검사
        if (!(email !== '' && email !== 'undefined' && regex.test(email))) {
            alert('잘못된 형식의 이메일 주소입니다.');
            setTimeout(function () {
                $("#id").focus();
            }, 10)
            return false;
        }
    });
});

/**
 * 비밀번호 정규식
 */
$(function () {
    $("input[name='password']").blur(function () {
        let pw = $("#pw").val();
        let eng = pw.search(/[a-z]/ig);
        let spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

        if (pw === '' || pw === 'undefined')
            return false;

        if (pw.length < 8 || pw.length > 16) {
            alert("8 ~ 16자리 이내로 입력해주세요.");
            pwTimeOut();
            return false;
        } else if (pw.search(/\s/) !== -1) {
            alert("비밀번호는 공백 없이 입력해주세요.");
            pwTimeOut();
            return false;
        } else if (eng < 0 || spe < 0) {
            alert("영문, 특수문자를 혼합하여 입력해주세요.");
            pwTimeOut();
            return false;
        } else {
            return true;
        }
    });
});

function pwTimeOut() {
    setTimeout(function () {
        $("#pw").focus();
    }, 10)
}

/**
 * 비밀번호 확인
 */
$(function () {

    $("#alert-danger").css('visibility', 'hidden');
    $("#pw, #pw2").keyup(function () {
        let pw1 = $("#pw").val();
        let pw2 = $("#pw2").val();

        if (pw1 === '' || pw1 === 'undefined' || pw2 === '' || pw2 === 'undefined' )
            return false;

        if (pw1 !== "" || pw2 !== "") {
            if (pw1 === pw2) {
                $("#alert-danger").css('visibility', 'hidden');
            } else {
                $("#alert-danger").css('visibility', 'visible');
            }
        }
    });
});

/**
 * 닉네임
 */
$(function () {
    $("input[name='nickname']").blur(function () {

        let nickname = $("#nickname").val();

        if (nickname === '' || nickname === 'undefined')
            return false;

        if (nickname.length < 2 || nickname.length > 6) {
            alert("2 ~ 6자리 이내로 입력해주세요.");
        } else if (nickname.search(/\s/) !== -1) {
            alert("공백 없이 입력해주세요.");
            return false;
        } else {
            return true;
        }
    });
});
