<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : formcarro
    Created on : 20/04/2021, 16:11:16
    Author     : Henrique
-->

<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:ui="http://java.sun.com/jsf/facelets"
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich" >
    <ui:composition template="/pages/template/template.jsp">
        <ui:define name="corpo">
            <h:form>
                <a4j:keepAlive beanName="carroWebBean" />

                <rich:panel header="Cadastro de Carro" style="width:600px;">
                    <h:panelGrid columns="2" columnClasses="alinha_direita,alinha_esquerda">
                        <h:outputText value="Código" />
                        <h:inputText id="codcarro" value="#{carroWebBean.carro.codcarro}" size="10" maxlength="10" rendered="#{! carroWebBean.edicao}" required="true" requiredMessage="Campo Código é obrigatório !" converterMessage="O campo Código deve ser um inteiro." />
                        <h:inputText id="codcarro_desabilitado" value="#{carroWebBean.carro.codcarro}" size="10" rendered="#{carroWebBean.edicao}" disabled="true" />

                        <h:outputText value="Modelo" />
                        <h:inputText id="modelo" value="#{carroWebBean.carro.modelo}" size="50" maxlength="50" required="true" requiredMessage="Campo Modelo é obrigatório." />

                        <h:outputText value="Fabricante" />
                        <h:inputText id="fabricante" value="#{carroWebBean.carro.fabricante}" size="50" maxlength="50" required="true" requiredMessage="Campo Fabricante é obrigatório." />

                        <h:outputText value="Cor" />
                        <h:inputText id="cor" value="#{carroWebBean.carro.cor}" size="50" maxlength="50" required="true" requiredMessage="Campo Cor é obrigatório." />

                        <h:outputText value="Ano"/>
                        <rich:calendar id="ano"
                                       inputClass="TextField txtData"
                                       enableManualInput="true"
                                       converterMessage="Data Inválida"
                                       popup="true"
                                       showApplyButton="false"
                                       datePattern="dd/MM/yyyy"
                                       ajaxSingle="true"
                                       cellWidth="10px" cellHeight="10px"
                                       style="width:200px"
                                       value="#{carroWebBean.carro.ano}"
                                       required="true"
                                       requiredMessage="Digite o ano"
                                       locale="pt_BR"
                                       inputSize="10"
                                       showWeeksBar="false">
                        </rich:calendar>
                    </h:panelGrid>
                    <br/>
                    <h:panelGroup>
                        <h:commandButton value="Salvar" action="#{carroWebBean.salvarMais}" image="/imagens/btn_salvar_mais.gif" />
                        <rich:spacer width="3"/>
                        <h:commandButton value="Salvar" action="#{carroWebBean.salvar}" image="/imagens/btn_salvar.gif" />
                        <rich:spacer width="3"/>
                        <h:commandButton value="Voltar" action="#{carroWebBean.consultar}" image="/imagens/btn_voltar.gif" immediate="true" />
                    </h:panelGroup>
                </rich:panel>
            </h:form>
        </ui:define>
    </ui:composition>
</jsp:root>
