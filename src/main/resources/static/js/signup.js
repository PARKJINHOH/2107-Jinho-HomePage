function email_check(email) {
    /*
    * 이메일 정규식
    * */
    const regex = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return (email != '' && email != 'undefined' && regex.test(email));

}

$(function () {
    $("input[name='email']").blur(function () {

        let email = $(this).val();

        // 값을 입력안한경우는 아예 체크를 하지 않는다.
        if (email == '' || email == 'undefined')
            return;

        // 이메일 유효성 검사
        if (!email_check(email)) {
            alert('잘못된 형식의 이메일 주소입니다.');
            $('#email').focus();
            return false;
        }
    });
});

