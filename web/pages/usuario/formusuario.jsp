<?xml version="1.0" encoding="UTF-8"?>
<!--
    Document   : formusuario
    Created on : 04/05/2021, 13:53:31
    Author     : Sthefany
-->

<jsp:root version="2.0"
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:ui="http://java.sun.com/jsf/facelets"
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich" >
    <ui:composition template="/pages/template/template.jsp">
        <ui:define name="corpo" >
            <h:form>
                <a4j:keepAlive beanName="usuarioWebBean" />

                <rich:panel header="Cadastro de Usuario" style="width:600px;">
                    <rich:tabPanel switchType="client">
                        <rich:tab label="Cadastro" id="tabCadastro">
                            <h:panelGrid columns="2">
                                <h:outputText value="Código" />
                                <h:inputText id="codusuario" value="#{usuarioWebBean.usuario.codusuario}" size="10" maxlength="10" rendered="#{! usuarioWebBean.edicao}" converterMessage="O campo Código deve ser um inteiro." />
                                <h:inputText id="codusuario_desabilitado" value="#{usuarioWebBean.usuario.codusuario}" size="10" rendered="#{usuarioWebBean.edicao}" disabled="true" />

                                <h:outputText value="Login" />
                                <h:inputText id="login" value="#{usuarioWebBean.usuario.login}" size="50" maxlength="50"/>

                                <h:outputText value="Senha" />
                                <h:inputSecret id="senha" value="#{usuarioWebBean.usuario.senha}"  size="50" maxlength="50"/>

                                <h:outputText value="Confirmar a senha" />
                                <h:inputSecret id="confirmarSenha" value="#{usuarioWebBean.confirmaSenha}" size="50" maxlength="50"/>
                            </h:panelGrid>
                         </rich:tab>
                         <rich:tab label="Telefones" id="tabTelefones">
                             <h:panelGrid columns="2" id="pg_telefones" columnClasses="alinha_direita,alinha_esquerda" >
                                <h:outputText value="Tipo" />
                                <h:selectOneMenu value="#{usuarioWebBean.telefone.tipo}">
                                    <f:selectItem itemValue="0" itemLabel="Selecione um tipo"></f:selectItem>
                                    <f:selectItem itemValue="1" itemLabel="Celular"></f:selectItem>
                                    <f:selectItem itemValue="2" itemLabel="Telefone"></f:selectItem>
                                </h:selectOneMenu>
                                <h:outputText value="Número" />
                                <h:inputText id="numero" value="#{usuarioWebBean.telefone.numero}" size="13" maxlength="13"/>
                            </h:panelGrid>
                             <br/>
                             <a4j:commandButton action="#{usuarioWebBean.adicionarTelefone}" image="/imagens/btn_adicionar.gif" reRender="tabela_telefones, pg_telefones"/>
                             <br/>
                             <a4j:queue requestDelay="100" />
                            <rich:dataTable
                                id="tabela_telefones"
                                rows="#{usuarioWebBean.qtdeRegistros}"
                                border="2"
                                headerClass="linha_titulo"
                                rendered="#{! empty usuarioWebBean.listaTelefones}"
                                value="#{usuarioWebBean.listaTelefones}"
                                var="telefone"
                                reRender="ds1"
                                onRowMouseOver="this.className='linha_sobre'"
                                onRowMouseOut="this.className='linha_normal'"
                                width="420">

                                <f:facet name="header">
                                    <h:panelGroup>
                                        <rich:datascroller align="center"
                                                           ajaxSingle="false"
                                                           maxPages="10"
                                                           for="tabela_telefones"
                                                           page="#{usuarioWebBean.pagina}"
                                                           rendered="#{! empty usuarioWebBean.listaTelefones}"
                                                           reRender="tabela_telefones"
                                                           id="ds1" />
                                        <h:outputText value="  #{usuarioWebBean.listaTelefones.rowCount} registros" rendered="#{! empty usuarioWebBean.listaTelefones}"/>
                                    </h:panelGroup>
                                </f:facet>

                                <rich:column sortBy="#{telefone.codtelefone}" >
                                    <f:facet name="header">
                                        <h:outputText value="Código" />
                                    </f:facet>
                                    <h:outputText value="#{telefone.codtelefone}"/>
                                </rich:column>

                                <rich:column sortBy="#{telefone.numero}">
                                    <f:facet name="header">
                                        <h:outputText value="Número de telefone" />
                                    </f:facet>
                                    <h:outputText value="#{telefone.numero}"  />
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Tipo de telefone" />
                                    </f:facet>
                                    <h:outputText value="#{telefone.tipoDescricao}"/>
                                </rich:column>

                                <rich:column style="text-align:center">
                                    <f:facet name="header" >
                                        <h:outputText value="Editar" />
                                    </f:facet>
                                    <h:commandButton action="#{usuarioWebBean.editarTelefone}" image="/imagens/btn_editar.gif" />
                                </rich:column>

                                <rich:column style="text-align:center">
                                    <f:facet name="header">
                                        <h:outputText value="Excluir" />
                                    </f:facet>
                                    <a4j:commandButton action="#{usuarioWebBean.setarTelefoneExcluir}" image="/imagens/btn_excluir_mini.gif"
                                                       oncomplete="Richfaces.showModalPanel('modalConfirmacaoExclusaoTelefone')"
                                                       reRender="modalConfirmacaoExclusaoTelefone" />
                                </rich:column>

                            </rich:dataTable>
                        </rich:tab>
                            <rich:tab label="Carros" id="tabCarros"></rich:tab>
                    </rich:tabPanel>
                    <br/>
                    <h:panelGroup>
                        <h:commandButton value="Salvar" action="#{usuarioWebBean.salvarMais}" image="/imagens/btn_salvar_mais.gif" />
                        <rich:spacer width="3"/>
                        <h:commandButton value="Salvar" action="#{usuarioWebBean.salvar}" image="/imagens/btn_salvar.gif" />
                        <rich:spacer width="3"/>
                        <h:commandButton value="Voltar" action="#{usuarioWebBean.consultar}" image="/imagens/btn_voltar.gif" immediate="true" />
                    </h:panelGroup>
                </rich:panel>
            </h:form>

            <rich:modalPanel id="modalConfirmacaoExclusaoTelefone" width="300" autosized="true">
                <f:facet name="header">
                    <h:outputText value="ATENÇÃO" />
                </f:facet>
                <f:facet name="controls">
                    <h:graphicImage value="/imagens/btnclose.png" style="cursor:pointer" onclick="Richfaces.hideModalPanel('modalConfirmacaoExclusao')" />
                </f:facet>

                <h:form>
                    <a4j:keepAlive beanName="usuarioWebBean" />
                    <h:graphicImage value="/imagens/warning_gde.gif"/>
                    <h:outputText value="  Confirma a Exclusão do Telefone " style="font-size: 10pt;"/>
                    <strong>
                        <h:outputText value="#{usuarioWebBean.telefone.numero}" style="font-size: 10pt;"/>
                    </strong>

                    <br/><br/>
                    <div align="center">
                        <h:panelGrid columns="2">
                            <h:commandButton
                                id="btn_confirmar"
                                action="#{usuarioWebBean.excluirTelefone}"
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
