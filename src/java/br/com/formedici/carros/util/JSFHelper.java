/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.util;

import java.util.Map;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpServletRequest;

/**
 *  
 * @author victor
 */
public class JSFHelper {

    public static ExternalContext getExternalContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getExternalContext();
    }

    public static String getRequestParameter(String parameterName) {
        Map paramMap = getExternalContext().getRequestParameterMap();
        return (String) paramMap.get(parameterName);
    }

    public static Object getRequestAttribute(String attributeName) {
        Map attrMap = getExternalContext().getRequestMap();
        return attrMap.get(attributeName);
    }

    public static Object getSessionAttribute(String attributeName) {
        Map attrMap = getExternalContext().getSessionMap();
        return attrMap.get(attributeName);
    }

    public static void setSessionAttribute(String attributeName, Object attribute) {
        Map attrMap = getExternalContext().getSessionMap();
        attrMap.put(attributeName, attribute);
    }

    public static Object getApplicationAttribute(String attributeName) {
        Map attrMap = getExternalContext().getApplicationMap();
        return attrMap.get(attributeName);
    }

    public static void setApplicationAttribute(String attributeName, Object attribute) {
        Map attrMap = getExternalContext().getApplicationMap();
        attrMap.put(attributeName, attribute);
    }

    public static void addErrorMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static void addWarnMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static void addInfoMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static Object getManagedBean(String beanName) {
        Object o = getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
        return o;
    }

    public static void setManagedBean(String beanName, Object bean) {
        getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), bean);
    }

    private static Application getApplication() {
        ApplicationFactory appFactory =
                (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);

        return appFactory.getApplication();
    }

    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }

    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }

    public static String getIpAddress(){
        HttpServletRequest request = (HttpServletRequest) JSFHelper.getExternalContext().getRequest();
        return request.getRemoteAddr();
    }
}
