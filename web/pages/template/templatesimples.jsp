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
                        <td colspan="2">
                            <ui:insert name="topo">
                                <ui:include src="topo.jsp"/>
                            </ui:insert>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top" height="100%" width="100%">
                            <table border="0" align="center" cellpadding="0" cellspacing="0" height="100%" width="100%">
                                <tr>
                                    <td valign="top" align="center">
                                        <style type="text/css">
                                            .rich-messages-marker img {padding-right:7px;}
                                            .rich-message-label {color:red;}
                                            .top {vertical-align:top;}
                                        </style>
                                        <br/>

                                        <a4j:outputPanel ajaxRendered="true">
                                            <a4j:outputPanel rendered="#{DefaultSessionBean.existeMensagemError}" >

                                                <div class="bordaError">
                                                    <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
                                                    <div class="conteudo">
                                                        <rich:messages layout="table" errorClass="mensagemError" level="ERROR" >
                                                            <f:facet name="errorMarker">
                                                                <h:graphicImage value="/imagens/error.gif" />
                                                            </f:facet>
                                                        </rich:messages>
                                                    </div>
                                                    <b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
                                                </div>
                                            </a4j:outputPanel>
                                        </a4j:outputPanel>

                                        <a4j:outputPanel ajaxRendered="true">
                                            <a4j:outputPanel rendered="#{DefaultSessionBean.existeMensagemInfo}" >
                                                <div class="bordaInfo">
                                                    <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
                                                    <div class="conteudo">
                                                        <rich:messages layout="table" infoClass="mensagemInfo" level="INFO" >
                                                            <f:facet name="infoMarker" >
                                                                <h:graphicImage value="/imagens/informacao.gif" />
                                                            </f:facet>
                                                        </rich:messages>
                                                    </div>
                                                    <b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
                                                </div>
                                            </a4j:outputPanel>
                                        </a4j:outputPanel>

                                        <a4j:outputPanel ajaxRendered="true">
                                            <a4j:outputPanel rendered="#{DefaultSessionBean.existeMensagemWarn}" >
                                                <div class="bordaWarn">
                                                    <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
                                                    <div class="conteudo">
                                                        <rich:messages layout="table" warnClass="mensagemWarn" level="WARN" >
                                                            <f:facet name="warnMarker" >
                                                                <h:graphicImage value="/imagens/warn.gif" />
                                                            </f:facet>
                                                        </rich:messages>
                                                    </div>
                                                    <b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
                                                </div>
                                            </a4j:outputPanel>
                                        </a4j:outputPanel>

                                        <br/>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="100%" valign="top" align="center">
                                        <ui:insert name="corpo" style="border:1px dotted #333;"/>
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





<ui:insert name="popup">
    <ui:include src="popup.jsp"/>
</ui:insert>

</f:view>

</body>
</html>

</jsp:root>