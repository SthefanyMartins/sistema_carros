<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : listacarro
    Created on : 20/04/2021, 16:07:31
    Author     : Henrique
-->

<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:ui="http://java.sun.com/jsf/facelets" 
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich">
    <ui:composition template="/pages/template/template.jsp">
        <ui:define name="corpo" >
            <h:form>
                <a4j:keepAlive beanName="carroWebBean" />

                <rich:panel header="Consulta de Carros" style="width:800px">

                    <h:panelGrid columns="2" columnClasses="alinha_direita,alinha_esquerda">
                        <h:outputText value="Modelo"/>
                        <h:inputText value="#{carroWebBean.pesqModelo}" size="30" maxlength="50"/>

                        <h:outputText value="Fabricante"/>
                        <h:inputText value="#{carroWebBean.pesqFabricante}" size="30" maxlength="50"/>

                        <h:outputText value="Cor"/>
                        <h:inputText value="#{carroWebBean.pesqCor}" size="30" maxlength="50"/>

                        <h:outputText value="Ano"/>
                        <h:inputText value="#{carroWebBean.pesqAno}" size="4" maxlength="4" converterMessage="O campo ano é numérico! Digite um ano válido!"/>
                    </h:panelGrid>

                    <br/>

                    <h:commandButton id="botaoConsultar" value="Consultar" action="#{carroWebBean.consultar}" image="/imagens/btn_consultar.gif" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="show" event="onmouseup" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="hide" event="oncomplete" />
                    <rich:spacer width="3"/>
                    <h:commandButton id="botaoLimpar" value="Limpar consulta" action="#{carroWebBean.limparConsulta}" image="/imagens/btn_limpar_consulta.gif" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="show" event="onmouseup" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoConsultar" operation="hide" event="oncomplete" />
                    <rich:spacer width="3"/>
                    <h:commandButton id="botaoIncluir" value="Inserir" action="#{carroWebBean.inserir}" image="/imagens/btn_inserir.gif" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoIncluir" operation="show" event="onmouseup" />
                    <rich:componentControl for="modalMensagemProcessando" attachTo="botaoIncluir" operation="hide" event="oncomplete" />

                    <br/>

                    <h:panelGrid columns="1" id="painelGrid">                            
                        <h:panelGroup>
                            <h:outputText value="Visualizar "/>
                            <h:inputText id="qtdeVisualizarRegistros" value="#{carroWebBean.qtdeRegistros}" style="width:40px">

                            </h:inputText>
                            <h:outputText value=" registros "/>
                        </h:panelGroup>        

                    </h:panelGrid>
                    <br/>

                    <a4j:queue requestDelay="100" />
                    <rich:dataTable 
                        id="tabela"
                        rows="#{carroWebBean.qtdeRegistros}"
                        border="2" 
                        headerClass="linha_titulo" 
                        rendered="#{! empty carroWebBean.listaCarros}"
                        value="#{carroWebBean.listaCarros}"
                        var="carro"
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
                                                   page="#{carroWebBean.pagina}"
                                                   rendered="#{! empty carroWebBean.listaCarros}"
                                                   reRender="tabela"
                                                   id="ds1" />
                                <h:outputText value="  #{carroWebBean.listaCarros.rowCount} registros" rendered="#{! empty carroWebBean.listaCarros}"/>
                            </h:panelGroup>
                        </f:facet>

                        <rich:column sortBy="#{carro.codcarro}" >
                            <f:facet name="header">
                                <h:outputText value="Código" />
                            </f:facet>
                            <h:outputText value="#{carro.codcarro}" />
                        </rich:column>

                        <rich:column sortBy="#{carro.modelo}" filterBy="#{carro.modelo}" filterEvent="onkeyup">
                            <f:facet name="header">
                                <h:outputText value="Modelo" />
                            </f:facet>
                            <h:outputText value="#{carro.modelo}"  />
                        </rich:column>

                        <rich:column sortBy="#{carro.fabricante}" filterBy="#{carro.fabricante}" filterEvent="onkeyup">
                            <f:facet name="header">
                                <h:outputText value="Fabricante" />
                            </f:facet>
                            <h:outputText value="#{carro.fabricante}"/>
                        </rich:column>

                        <rich:column sortBy="#{carro.cor}" filterBy="#{carro.cor}" filterEvent="onkeyup" width="100%">
                            <f:facet name="header">
                                <h:outputText value="Cor" />
                            </f:facet>
                            <h:outputText value="#{carro.cor}"/>
                        </rich:column>

                        <rich:column sortBy="#{carro.ano}" >
                            <f:facet name="header">
                                <h:outputText value="Ano" />
                            </f:facet>
                            <h:outputText value="#{carro.ano}" converter="ValorData"/>
                        </rich:column>

                        <rich:column style="text-align:center">
                            <f:facet name="header" >
                                <h:outputText value="Editar" />
                            </f:facet>
                            <h:commandButton action="#{carroWebBean.editar}" image="/imagens/btn_editar.gif" />
                        </rich:column>

                        <rich:column style="text-align:center">
                            <f:facet name="header">
                                <h:outputText value="Excluir" />
                            </f:facet>
                            <a4j:commandButton action="#{carroWebBean.setarCarroExcluir}" image="/imagens/btn_excluir_mini.gif"
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
                    <a4j:keepAlive beanName="carroWebBean" />
                    <h:graphicImage value="/imagens/warning_gde.gif"/>
                    <h:outputText value="  Confirma a Exclusão do Carro" style="font-size: 10pt;"/>
                    <strong>
                        <h:outputText value="#{carroWebBean.carro.modelo}" style="font-size: 10pt;"/>
                    </strong>

                    <br/><br/>
                    <div align="center">
                        <h:panelGrid columns="2">
                            <h:commandButton
                                id="btn_confirmar"
                                action="#{carroWebBean.excluir}"
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
