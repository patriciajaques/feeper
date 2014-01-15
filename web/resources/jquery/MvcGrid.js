function goToPage(gridName, pageIndex) {
    $("#frm_" + gridName).find("#currentPage").val(pageIndex);
    $("#frm_" + gridName).find("#gridAction").val("CurrentPageChanged");
    submitForm(gridName);
}

function onPageSizeChange(gridName) {
    $("#frm_" + gridName).find("#gridAction").val("PageSizeChanged");
    submitForm(gridName);
}

function sort(gridName, sortField, sortDirection) {
    $("#frm_" + gridName).find("#gridAction").val("Sorted");
    $("#frm_" + gridName).find("#sortField").val(sortField);
    $("#frm_" + gridName).find("#sortDirection").val(sortDirection);
    submitForm(gridName);
}

function submitForm(gridName) {
    var form = $("#frm_" + gridName);
    form.submit();
}

$(function () {
    $(".grid-linha").live("mouseover", function () {
        $(this).css("background-color", "#f3f3f3");
    }).live("mouseleave", function () {
        $(this).css("background-color", "");
    });

    $(".grid-mvc .ui-widget-header > th").addClass("ui-widget-header");
});