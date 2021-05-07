<?xml version="1.0" encoding="UTF-8"?>
<!--
    Document   : listausuario
    Created on : 04/05/2021, 13:53:52
    Author     : Sthefany
-->

<jsp:root version="2.0"
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:ui="http://java.sun.com/jsf/facelets"
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich">
    <ui:composition template="/pages/template/template.jsp">
        <ui:define name="corpo">
            <h:form>
                <a4j:keepAlive beanName="usuarioWebBean" />

                <rich:panel header="Consulta de Usuários" style="width:650px">

                    <h:panelGrid columns="2" columnClasses="alinha_direita,alinha_esquerda">
                        <h:outputText value="Login"/>
                        <h:inputText value="#{usuarioWebBean.pesqLogin}" size="30" maxlength="50"/>
                    </h:panelGrid>

                    <br/>

                    <h:commandButton id="botaoConsultar" value="Consultar" action="#{usuarioWebBean.consultar}" image="/imagens/btn_consultar.gif" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="show" event="onmouseup" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="hide" event="oncomplete" />
                    <rich:spacer width="3"/>
                    <h:commandButton id="botaoLimpar" value="Limpar consulta" action="#{usuarioWebBean.limparConsulta}" image="/imagens/btn_limpar_consulta.gif" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="show" event="onmouseup" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="hide" event="oncomplete" />
                    <rich:spacer width="3"/>
                    <h:commandButton id="botaoIncluir" value="Inserir" action="#{usuarioWebBean.inserir}" image="/imagens/btn_inserir.gif" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoIncluir" operation="show" event="onmouseup" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoIncluir" operation="hide" event="oncomplete" />

                    <br/>

                    <h:panelGrid columns="1" id="painelGrid">
                        <h:panelGroup>
                            <h:outputText value="Visualizar "/>
                            <h:inputText id="qtdeVisualizarRegistros" value="#{usuarioWebBean.qtdeRegistros}" style="width:40px">

                            </h:inputText>
                            <h:outputText value=" registros "/>
                        </h:panelGroup>

                    </h:panelGrid>
                    <br/>

                    <a4j:queue requestDelay="100" />
                    <rich:dataTable
                        id="tabela"
                        rows="#{usuarioWebBean.qtdeRegistros}"
                        border="2"
                        headerClass="linha_titulo"
                        rendered="#{! empty usuarioWebBean.listaUsuarios}"
                        value="#{usuarioWebBean.listaUsuarios}"
                        var="usuario"
                        reRender="ds1"
                        onRowMouseOver="this.className='linha_sobre'"
                        onRowMouseOut="this.className='linha_normal'"
                        width="420">

                        <f:facet name="header">
                            <h:panelGroup>
                                <rich:datascroller align="center"
                                                   ajaxSingle="false"
                                                   maxPages="10"
                                                   for="tabela"
                                                   page="#{usuarioWebBean.pagina}"
                                                   rendered="#{! empty usuarioWebBean.listaUsuarios}"
                                                   reRender="tabela"
                                                   id="ds1" />
                                <h:outputText value="  #{usuarioWebBean.listaUsuarios.rowCount} registros" rendered="#{! empty usuarioWebBean.listaUsuarios}"/>
                            </h:panelGroup>
                        </f:facet>

                        <rich:column sortBy="#{usuario.codusuario}" >
                            <f:facet name="header">
                                <h:outputText value="Código" />
                            </f:facet>
                            <h:outputText value="#{usuario.codusuario}" />
                        </rich:column>

                        <rich:column sortBy="#{usuario.login}" filterBy="#{usuario.login}" filterEvent="onkeyup">
                            <f:facet name="header">
                                <h:outputText value="Login" />
                            </f:facet>
                            <h:outputText value="#{usuario.login}"  />
                        </rich:column>

                        <rich:column style="text-align:center">
                            <f:facet name="header" >
                                <h:outputText value="Editar" />
                            </f:facet>
                            <h:commandButton action="#{usuarioWebBean.editar}" image="/imagens/btn_editar.gif" />
                        </rich:column>

                        <rich:column style="text-align:center">
                            <f:facet name="header">
                                <h:outputText value="Excluir" />
                            </f:facet>
                            <a4j:commandButton action="#{usuarioWebBean.setarUsuarioExcluir}" image="/imagens/btn_excluir_mini.gif"
                                               oncomplete="Richfaces.showModalPanel('modalConfirmacaoExclusao')"
                                               reRender="modalConfirmacaoExclusao" />
                        </rich:column>

                    </rich:dataTable>
                </rich:panel>
            </h:form>

            <rich:modalPanel id="modalConfirmacaoExclusao" width="300" autosized="true">
                <f:facet name="header">
                    <h:outputText value="ATENÇÃO" />
                </f:facet>
                <f:facet name="controls">
                    <h:graphicImage value="/imagens/btnclose.png" style="cursor:pointer" onclick="Richfaces.hideModalPanel('modalConfirmacaoExclusao')" />
                </f:facet>

                <h:form>
                    <a4j:keepAlive beanName="usuarioWebBean" />
                    <h:graphicImage value="/imagens/warning_gde.gif"/>
                    <h:outputText value="  Confirma a Exclusão do Usuario" style="font-size: 10pt;"/>
                    <strong>
                        <h:outputText value="#{usuarioWebBean.usuario.login}" style="font-size: 10pt;"/>
                    </strong>

                    <br/><br/>
                    <div align="center">
                        <h:panelGrid columns="2">
                            <h:commandButton
                                id="btn_confirmar"
                                action="#{usuarioWebBean.excluir}"
                                onclick="Richfaces.showModalPanel('modalMensagemProcessando')"
                                image="/imagens/btn_confirmar.gif" />

                            <h:graphicImage value="/imagens/btn_cancelar.gif" style="cursor:pointer" onclick="Richfaces.hideModalPanel('modalConfirmacaoExclusao')" />
                        </h:panelGrid>
                    </div>
                </h:form>
            </rich:modalPanel>


        </ui:define>
    </ui:composition>
</jsp:root>