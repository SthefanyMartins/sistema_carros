/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.controller.bean;

import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author caio
 */
public class DefaultSessionBean {

    private String pesquisaNomeObjetoPrincipal;
    private String pesquisaTitulo;
    private String pesquisaInterfaceBean;
    private String pesquisaCampos;
    private String pesquisaCabecalhos;
    private String pesquisaCampoDeRetorno;
    private String pesquisaAjaxCamposReRender;
    private Object pesquisaObjetoSelecionado;
    private String pesquisaBeanEFuncaoDeRetorno;
    private String pesquisaFrase;
    private Boolean pesquisaOn = true;
    
    private Boolean loginBeneficiarioControle;
    
    public String getPesquisaNomeObjetoPrincipal() {
        return pesquisaNomeObjetoPrincipal;
    }

    public void setPesquisaNomeObjetoPrincipal(String pesquisaNomeObjetoPrincipal) {
        this.pesquisaNomeObjetoPrincipal = pesquisaNomeObjetoPrincipal;
    }
    
    public String getPesquisaTitulo() {
        return pesquisaTitulo;
    }

    public void setPesquisaTitulo(String pesquisaTitulo) {
        this.pesquisaTitulo = pesquisaTitulo;
    }

    public String getPesquisaInterfaceBean() {
        return pesquisaInterfaceBean;
    }

    public void setPesquisaInterfaceBean(String pesquisaInterfaceBean) {
        this.pesquisaInterfaceBean = pesquisaInterfaceBean;
    }

    public String getPesquisaCampos() {
        return pesquisaCampos;
    }

    public void setPesquisaCampos(String pesquisaCampos) {
        this.pesquisaCampos = pesquisaCampos;
    }

    public String getPesquisaCabecalhos() {
        return pesquisaCabecalhos;
    }

    public void setPesquisaCabecalhos(String pesquisaCabecalhos) {
        this.pesquisaCabecalhos = pesquisaCabecalhos;
    }

    public String getPesquisaCampoDeRetorno() {
        return pesquisaCampoDeRetorno;
    }

    public void setPesquisaCampoDeRetorno(String pesquisaCampoDeRetorno) {
        this.pesquisaCampoDeRetorno = pesquisaCampoDeRetorno;
    }

    public Object getPesquisaObjetoSelecionado() {
        return pesquisaObjetoSelecionado;
    }

    public void setPesquisaObjetoSelecionado(Object pesquisaObjetoSelecionado) {
        this.pesquisaObjetoSelecionado = pesquisaObjetoSelecionado;
    }

    public String getPesquisaAjaxCamposReRender() {
        return pesquisaAjaxCamposReRender;
    }

    public void setPesquisaAjaxCamposReRender(String pesquisaAjaxCamposReRender) {
        this.pesquisaAjaxCamposReRender = pesquisaAjaxCamposReRender;
    }

    public Boolean getPesquisaOn() {
        return pesquisaOn;
    }

    public void setPesquisaOn(Boolean pesquisaOn) {
        this.pesquisaOn = pesquisaOn;
    }

    public String getPesquisaBeanEFuncaoDeRetorno() {
        return pesquisaBeanEFuncaoDeRetorno;
    }

    public void setPesquisaBeanEFuncaoDeRetorno(String pesquisaBeanEFuncaoDeRetorno) {
        this.pesquisaBeanEFuncaoDeRetorno = pesquisaBeanEFuncaoDeRetorno;
    }

    public String getPesquisaFrase() {
        return pesquisaFrase;
    }

    public void setPesquisaFrase(String pesquisaFrase) {
        this.pesquisaFrase = pesquisaFrase;
    }

    public Boolean getLoginBeneficiarioControle() {
        return loginBeneficiarioControle;
    }

    public void setLoginBeneficiarioControle(Boolean loginBeneficiarioControle) {
        this.loginBeneficiarioControle = loginBeneficiarioControle;
    }

    public boolean getExisteMensagemError() {
        Iterator<FacesMessage> i = FacesContext.getCurrentInstance().getMessages();
        while (i.hasNext()) {
            FacesMessage msg = i.next();
            if (msg.getSeverity().equals(FacesMessage.SEVERITY_ERROR)) {
                return true;
            }
        }
        return false;
    }

    public boolean getExisteMensagemInfo() {
        Iterator<FacesMessage> i = FacesContext.getCurrentInstance().getMessages();
        while (i.hasNext()) {
            FacesMessage msg = i.next();
            if (msg.getSeverity().equals(FacesMessage.SEVERITY_INFO)) {
                return true;
            }
        }
        return false;
    }

    public boolean getExisteMensagemWarn() {
        Iterator<FacesMessage> i = FacesContext.getCurrentInstance().getMessages();
        while (i.hasNext()) {
            FacesMessage msg = i.next();
            if (msg.getSeverity().equals(FacesMessage.SEVERITY_WARN)) {
                return true;
            }
        }
        return false;
    }
}
