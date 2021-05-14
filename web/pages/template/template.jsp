<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:ui="http://java.sun.com/jsf/facelets"
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich">
    <html>

        <head>
        <a4j:loadStyle src="/css/estilosAplicacao.css" />
        <title> Sistema Carros </title>
    </head>
    <body >
    <f:view contentType="text/html">
        <!-- Define a máscara genérica aplicada pela classe de estilo, nos calendários -->
        <a4j:loadScript src="/scripts/jquery.maskedinput-1.1.4.js"/>
        <a4j:loadScript src="/scripts/funcoes_profsyst.js"/>
        <rich:jQuery id="mskData" selector=".txtData" timing="onload" query="mask('99/99/9999',{placeholder:' '})" />
        <rich:jQuery id="mskDataHora" selector=".txtDataHora" timing="onload" query="mask('99/99/9999 99:99',{placeholder:' '})" />
        <rich:jQuery id="mskDataMesAnoAux" selector=".txtDataMesAno" timing="onload" query="mask('99/9999',{placeholder:' '})" />
        <rich:jQuery id="mskHora" selector=".txtHora" timing="onload" query="mask('99:99',{placeholder:' '})" />
        <rich:jQuery id="mskCEP" selector=".txtCEP" timing="onload" query="mask('99999-999',{placeholder:'-'})" />

        <table border="0" align="center" cellpadding="0" cellspacing="0" height="100%" width="100%">
            <tr>
                <td >
                    <ui:insert name="topo">
                        <ui:include src="topo.jsp"/>
                    </ui:insert>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <ui:insert name="menu">
                        <ui:include src="menu.jsp" />
                    </ui:insert>
                </td>
            </tr>
            <tr>
                <td valign="top" height="100%" width="100%">
                    <table border="0" align="center" cellpadding="0" cellspacing="0" height="100%" width="100%">
                        <tr>
                            <td valign="top" align="center">
                                <ui:insert name="messages">
                                    <ui:include src="messages.jsp"/>
                                </ui:insert>
                            </td>
                        </tr>
                        <tr>
                            <td height="100%" valign="top" align="center">
                                <ui:insert name="corpo" style="border:1px dotted #333;"/>
                                <br/>
                                <ui:insert name="messages">
                                    <ui:include src="messages.jsp"/>
                                </ui:insert>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <ui:insert name="rodape">
                        <ui:include src="rodape.jsp"/>
                    </ui:insert>
                </td>
            </tr>
        </table>

<rich:modalPanel id="modalMensagemProcessando" width="200" height="120">
    <f:facet name="header">
        <h:outputText value="Processando"/>
    </f:facet>
    <div align="center">
        <h:graphicImage value="/imagens/aguardando.gif"/>
        <br/>
        <h:outputText value="Sua operação está sendo realizada. Por favor aguarde"/>
    </div>
</rich:modalPanel>


<rich:modalPanel id="modalHelp" width="700" height="450">
    <f:facet name="header">
        <h:panelGroup>
            <h:outputText value=" HELP " >
            </h:outputText>
        </h:panelGroup>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
            <h:graphicImage value="/imagens/btnclose.png" style="cursor:pointer" id="closemodalHelp" />
            <rich:componentControl for="modalHelp" attachTo="closemodalHelp" operation="hide" event="onclick"/>
        </h:panelGroup>
    </f:facet>
    <br/>
     
     <br/><br/>
     <div style="text-align:center; width: 100%;" >
         <h:graphicImage value="/imagens/btn_fechar.gif" style="cursor:pointer" id="btn_fechar_help" />
         <rich:componentControl for="modalHelp" attachTo="btn_fechar_help" operation="hide" event="onclick"/>
     </div>


</rich:modalPanel>


<ui:insert name="popup">
    <ui:include src="popup.jsp"/>
</ui:insert>

</f:view>

</body>
</html>

</jsp:root>