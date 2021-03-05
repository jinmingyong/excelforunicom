function connect() {
    $.ajax({
        type: 'get',
        url: '../codeGenerator/getTables',
        success: function (result) {
            if(result.code==200){
                $('#tableDiv').html("");
                alert("连接成功");
                var str = '<br>';
                var list = result.data;
                for (var i = 0; i < list.length; i++)
                    str += (list[i]+':<input type="checkbox" name="table" value="' + list[i] + '">&nbsp;&nbsp;&nbsp;&nbsp;');
                $('#tableDiv').append(str);
            }
        },
        error: function (error) {
            alert(JSON.stringify(error));
        }
    })
}

function generate() {
    var myArray=new Array();
    for (var i = 0; i < document.getElementsByName('table').length; i++) {
        if (document.getElementsByName('table')[i].checked) {
            myArray.push(document.getElementsByName('table')[i].value);
        }
    }
    if (myArray.length == 0) {
        alert("您没有选择任何数据");
    } else {
        $.ajax({
            type: 'post',
            url: '../codeGenerator/codeGenerators',
            data: JSON.stringify(myArray),
            contentType : "application/json" ,
            success: function (result) {
               alert(result);
            },
            error: function (error) {
                alert(JSON.stringify(error));
            }
        })
    }
}