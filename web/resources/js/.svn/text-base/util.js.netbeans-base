function FixHeader(grid) {
    var header = $("th", grid).first().parent();
    var y_Header = header.position().top;
    var w_Header = header.width();
    var h_Header = header.height();
    var html_header = "";
    $("th", grid).each(function () {
        var css = $(this).attr("class");
        html_header += "<th style=\"width:" + $(this).width() + "px; height:" + $(this).height() + "px\" class=\"" + css + "\">" + $(this).html() + "</th>";
    });

    $(window).scroll(function () {
        if ($(window).scrollTop() > y_Header && $("#header_fixed").length == 0)
            header.after("<tr id=\"header_fixed\" class=\"fixado\" style=\"width:" + w_Header + "px; height:" + h_Header + "px\">" + html_header + "</tr>");
        else if ($(window).scrollTop() <= y_Header && $("#header_fixed").length > 0)
            $("#header_fixed").remove();
    });
}

function fixIt(e, marginTop) {
	if (marginTop === undefined)
		marginTop = 0;

	var y = $(e).position().top;
	var w = $(e).width();
	var aux = $(e).clone().addClass("fix-it").removeAttr("id").css({ "top": marginTop, "width": w });
	
	$(window).scroll(function () {
		if ($(window).scrollTop() > y - marginTop && $(".fix-it").length == 0)
			$(e).after(aux);
		else if ($(window).scrollTop() <= y - marginTop && $(".fix-it").length > 0)
			$(".fix-it").remove();
	});
}