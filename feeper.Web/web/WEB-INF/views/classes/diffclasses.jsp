<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.modal>
    <jsp:attribute name="title"><fmt:message key="title.classe"/></jsp:attribute>
    <jsp:attribute name="header">
        <link href="<c:url value='/resources/jsdifflib/diffview.css'/>" rel="stylesheet" type="text/css"/>
        <script src="<c:url value='/resources/jsdifflib/difflib.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jsdifflib/diffview.js'/>" type="text/javascript"></script>
    </jsp:attribute>     
    <jsp:body>  
        <input id="codigoAluno" type="hidden" value="<c:out value="${textoClasseAluno}"/>">
        <input id="codigoColega" type="hidden" value="<c:out value="${textoClasseColega}"/>">

        <div id="diffDiv" style="width:100%;"></div>  

        <script type="text/javascript">
            $(function () {

                var alunoText = difflib.stringAsLines($("#codigoAluno").val());
                var colegaText = difflib.stringAsLines($("#codigoColega").val());

                // create a SequenceMatcher instance that diffs the two sets of lines
                var sm = new difflib.SequenceMatcher(alunoText, colegaText);

                // get the opcodes from the SequenceMatcher instance
                // opcodes is a list of 3-tuples describing what changes should be made to the base text
                // in order to yield the new text
                var opcodes = sm.get_opcodes();
                // build the diff view and add it to the current DOM
                $("#diffDiv").append(diffview.buildView({
                    baseTextLines: alunoText,
                    newTextLines: colegaText,
                    opcodes: opcodes,
                    // set the display titles for each resource
                    baseTextName: "${nomeAluno}",
                    newTextName: "${nomeColega}",
                    viewType: 1
                }));
            });
        </script>
    </jsp:body>
</t:master.modal>
