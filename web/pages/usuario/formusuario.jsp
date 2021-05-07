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
                    <h:panelGrid columns="2" columnClasses="alinha_direita,alinha_esquerda">
                        <h:outputText value="Código" />
                        <h:inputText id="codusuario" value="#{usuarioWebBean.usuario.codusuario}" size="10" maxlength="10" rendered="#{! usuarioWebBean.edicao}" required="true" requiredMessage="Campo Código é obrigatório !" converterMessage="O campo Código deve ser um inteiro." />
                        <h:inputText id="codusuario_desabilitado" value="#{usuarioWebBean.usuario.codusuario}" size="10" rendered="#{usuarioWebBean.edicao}" disabled="true" />

                        <h:outputText value="Login" />
                        <h:inputText id="login" value="#{usuarioWebBean.usuario.login}" size="50" maxlength="50" required="true" requiredMessage="Campo Login é obrigatório." />

                        <h:outputText value="Senha" />
                        <h:inputSecret id="senha" value="#{usuarioWebBean.usuario.senha}"  size="50" maxlength="50" required="#{usuarioWebBean.requiredPassword}" requiredMessage="Campo Senha é obrigatório."/>

                        <h:outputText value="Confirmar a senha" />
                        <h:inputSecret id="confirmarSenha" value="#{usuarioWebBean.confirmaSenha}" size="50" maxlength="50" required="#{usuarioWebBean.requiredPassword}" requiredMessage="Campo Confirmar a senha é obrigatório."/>
                    </h:panelGrid>
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
        </ui:define>
    </ui:composition>
</jsp:root>
