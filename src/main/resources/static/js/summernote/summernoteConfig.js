<!-- SummerNote 설정 -->
$(document).ready(function () {
    $('#summernote').summernote({
        height: 320,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
        callbacks : {
            onImageUpload : function(files, editor, welEditable) {
                for (let i = 0; i < files.length; i++) {
                    sendFile(files[i], this);
                }
            }
        }
    });
});

function sendFile(file, el) {
    // csrf
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    var form_data = new FormData();
    form_data.append('file', file);
    $.ajax({
        data : form_data,
        type : 'post',
        url : '/image',
        cache : false,
        contentType : false,
        enctype : 'multipart/form-data',
        processData : false,
        beforeSend: function (xhr) { // csrf
            xhr.setRequestHeader(header, token)
        },
        success : function(url) {
            $(el).summernote('insertImage', url, function($image) {
                $image.css('width', "50%");
            });
        }
    });
}


