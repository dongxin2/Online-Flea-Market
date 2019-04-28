var Model = (function () {
    console.log("acticated!");
    var map = {
        "register": {url: "./login.php?q=register", type: "post"},
        "login": {url: "./login.php?q=login", type: "post"}
    };
    var handler = {};//对象
    for (var name in map) {//name =>register,login,得到后面url
        handler[name] = (function (obj) {//obj就是map[name] || {}的自调用
            var reqtype = obj['type'] || 'post';
            var url = obj['url'] || '';
            var fun = function (param, success, error) {//ajax传递数据
                $.ajax({
                    type: reqtype,
                    url: url,
                    dataType: "json",
                    data: param,
                    success: function (data) {
                        success(data);
                        console.log(param);
                    },
                    error: error
                });
                console.log()
            };
            return fun;
        })(map[name] || {});//name 就是前端传过来的值
    }
    return handler;
})();