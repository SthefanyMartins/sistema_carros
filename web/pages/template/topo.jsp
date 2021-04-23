<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:ui="http://java.sun.com/jsf/facelets"
          xmlns:a4j="http://richfaces.org/a4j"
          xmlns:rich="http://richfaces.org/rich">
    <f:subview id="topo">
        <table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td colspan="2" height="45">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" height="45" >
                        <tr>
                            <td  width="134" style="background:url(#{facesContext.externalContext.request.contextPath}/imagens/topo1.gif) no-repeat;">
                                
                            </td>
                            <td background="#{facesContext.externalContext.request.contextPath}/imagens/topo2.gif" style="vertical-align:bottom;padding-bottom:0px;" >
                                <div align="left">
                                    <rich:spacer width="5"/>
                                    <h:outputText value="Sistema Carros" style="color:white;font-face:verdana;font-size:2em;font-weight:bold;" />
                                </div>
                            </td>
                            
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </f:subview>

</jsp:root>