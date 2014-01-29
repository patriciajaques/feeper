function goToPage(pageIndex) {
    $("#frm-paged").find("#currentPage").val(pageIndex);
    $("#frm-paged").find("#gridAction").val("CurrentPageChanged");
    submitForm();
}

function sort(sortField, sortDirection) {
    $("#frm-paged").find("#gridAction").val("Sorted");
    $("#frm-paged").find("#sortField").val(sortField);
    $("#frm-paged").find("#sortDirection").val(sortDirection);
    submitForm();
}

function setSearch() {
    $("#frm-paged").find("#gridAction").val("Searched");
}

function submitForm() {
    var form = $("#frm-paged");
    form.submit();
}