<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:ui="http://java.sun.com/jsf/facelets" 
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich" >
    <style type="text/css">
        .rich-messages-marker img {padding-right:7px;}
    </style>
    <h:form>

        <f:subview id="messages" >
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
        </f:subview>
    </h:form>

</jsp:root>