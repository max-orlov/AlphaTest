function getItems() {
    $('#box_id, #item_color').focus(function () {
        $('#answer').empty();
    });

    var box = $('#box_id').val();
    if (box === "") {
        box = undefined;
    }
    var color = $('#item_color').val();

    $.ajax({
        url: "main",
        type: "GET",
        data:{"box_id" : box, "item_color" : color},
        success: [function (fromserver) {
            $('#answer').append(JSON.stringify(fromserver));
            $('#box_id').val("");
            $('#item_color').val("");
        }]
    });
}

$('#getInfo').click(function () {
    $('#answer').empty();
    getItems()
});


