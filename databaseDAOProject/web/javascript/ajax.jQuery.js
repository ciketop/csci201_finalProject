$(document).ready(function () {
    $("form[name=sortForm] input[name^=fpDelSort], form[name=sortForm] input[name^=assignmentSort]")
        .change(function () {
            let sortForm = $("form[name=sortForm]");
            // console.log(sortForm.attr('action'));
            // console.log(sortForm.serialize());

            $.ajax({
                url: sortForm.attr('action'),
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'text',
                data: sortForm.serialize(),
                success: function (data, textStatus, jQxhr) {
                    // console.log(Boolean(data.trim()));
                    if (data) {
                        console.log("received");
                        $("div.mainTableContainer").html(data);
                        // $("table#mainTable").replaceWith(data);
                    }
                },
                error: function (jqXhr, textStatus, errorThrown) {
                    console.log(errorThrown);
                }
            });
        });

    $("form[name=sortForm]").submit(function () {
        let sortForm = $("form[name=sortForm]");
        // console.log(sortForm.attr('action'));

        let res = $.post({
            url: sortForm.attr('action'),
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'text',
            data: sortForm.serialize(),
            success: function (data, textStatus, jQxhr) {
                // $('#response pre').html( data );
                // console.log(data);
                console.log(Boolean(data.trim()));
                if (data) {
                    console.log("received");
                }
            },
            error: function (jqXhr, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    });
});