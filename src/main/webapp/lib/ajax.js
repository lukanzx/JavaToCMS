// 需要添加 jQuery 依赖
// <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
// <script src="../../lib/ajax.js"></script>

function ajaxPost(__url, __formData, __functionName, __functionNameError = null) {
    $.ajax({
        url: __url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(__formData),
        success: __functionName,
        error: function (xhr, status, error) {
            console.error('AJAX request failed:', status, error);
        }
    });
}

