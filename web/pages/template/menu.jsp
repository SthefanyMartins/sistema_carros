<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:ui="http://java.sun.com/jsf/facelets" 
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich" >
    <f:subview id="menu">
        <h:form id="formmenu">
            <h:panelGrid columns="1" cellpadding="0" cellspacing="0" border="0" style="width:100%;horizontal-align:left;text-align:left;" >
                <rich:toolBar id="toolBar">
                    <rich:menuItem submitMode="ajax" value="Home" action="home" onclick="Richfaces.showModalPanel('modalMensagemProcessando')"  />
                    <rich:menuItem submitMode="ajax" value="Carro" action="carro" onclick="Richfaces.showModalPanel('modalMensagemProcessando')"  />
                </rich:toolBar>
            </h:panelGrid>
        </h:form>
    </f:subview>
</jsp:root>
