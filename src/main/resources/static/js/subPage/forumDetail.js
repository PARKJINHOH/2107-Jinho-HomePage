$("#delete").click(function() {
    // csrf
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    const deleteData =JSON.stringify({boardSeq:$("#board_seq").val()});

    $.ajax({
        data: deleteData,
        type : 'post',
        url : '/forum/delete',
        contentType: 'application/json',
        beforeSend: function (xhr) { // csrf
            xhr.setRequestHeader(header, token)
        },
        success : function() {
            alert("삭제 성공!");
            location.href = "/forum";
        },
        error : function() {
            alert("사용자를 확인해 주세요.");
        }
    });
});