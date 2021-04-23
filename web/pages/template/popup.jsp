<jsp:root version="2.0"
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:ui="http://java.sun.com/jsf/facelets"
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich"
          xmlns:jp="http://www.jenia.org/jsf/popup">
    <f:subview id="popup">

        <style>
            .rolagemModal {
                height:410px;
                overflow:auto;
            }

        </style>

        <!-- PopUp Generica -->
        <rich:modalPanel id="popupPesquisa" height="500" width="700" onshow="document.getElementById('popup:formPopup:dadoPesquisa').focus();" >

            <center>
                <f:facet name="header">
                    <h:outputText value="Pesquisa" />
                </f:facet>

                <f:facet name="controls">
                    <h:graphicImage value="#{pageContext.request.contextPath}/imagens/btnclose.png"
                                    style="cursor:pointer" onclick="Richfaces.hideModalPanel('popupPesquisa');Richfaces.hideModalPanel('modalMensagemProcessando')" />
                </f:facet>
                <a4j:form id="formPopup">
                    <rich:panel bodyClass="rolagemModal" id="painelPrincipalPopup">
                        <h:panelGroup rendered="#{popupWebBean.pesquisaOn}">

                            <h:selectOneMenu id="tipoDadoPesquisa" value="#{popupWebBean.campoPesquisaSelecionado}">
                                <f:selectItems value="#{popupWebBean.possiveisCamposDePequisa}"/>
                            </h:selectOneMenu>
                            <h:selectOneMenu value="#{popupWebBean.tipoLike}"  >
                                <f:selectItem itemLabel="Igual a " itemValue="0"/>
                                <f:selectItem itemLabel="Começando por" itemValue="1"/>
                                <f:selectItem itemLabel="Contenha" itemValue="2"/>
                            </h:selectOneMenu>
                            <h:inputText id="dadoPesquisa" value="#{popupWebBean.dadoPesquisado}" size="40"/>
                            <br/>
                            <a4j:commandButton image="/imagens/btn_consultar.gif" actionListener="#{popupWebBean.consultar}"
                                               reRender="tipoDadoPesquisa,dadoPesquisa,dataTablePopup"
                                               onclick="javascript:Richfaces.showModalPanel('modalMensagemProcessando')"
                                               oncomplete="javascript:Richfaces.hideModalPanel('modalMensagemProcessando')"/>
                        </h:panelGroup>

                        <a4j:outputPanel ajaxRendered="true">
                            <a4j:region>

                                <rich:dataTable id="dataTablePopup"
                                                border="1"
                                                headerClass="linha_titulo"
                                                rows="10"
                                                width="640"
                                                reRender="sc1"
                                                value="#{popupWebBean.lista}"
                                                binding="#{popupWebBean.dataTable}"
                                                onRowMouseOver="this.className='linha_sobre'"
                                                onRowMouseOut="this.className='linha_normal'" >
                                    <f:facet name="header">
                                        <h:panelGroup>
                                            <rich:datascroller align="left"
                                                               ajaxSingle="false"
                                                               maxPages="10"
                                                               for="dataTablePopup"
                                                               page="#{popupWebBean.pagina}"
                                                               reRender="dataTablePopup"
                                                               id="sc1" />
                                        </h:panelGroup>
                                    </f:facet>
                                </rich:dataTable>
                            </a4j:region>
                        </a4j:outputPanel>
                    </rich:panel>
                </a4j:form>
            </center>

        </rich:modalPanel>


    </f:subview>
</jsp:root>