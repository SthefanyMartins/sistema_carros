<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="2.0"
          xmlns:f="http://java.sun.com/jsf/core"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:ui="http://java.sun.com/jsf/facelets"
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich">
    <ui:composition template="pages/template/template.jsp">
        <ui:define name="corpo" >
            <a4j:form >
                <h:outputText value="Bem vindo ao sistema!" />
            </a4j:form>
        </ui:define>
    </ui:composition>
</jsp:root>
