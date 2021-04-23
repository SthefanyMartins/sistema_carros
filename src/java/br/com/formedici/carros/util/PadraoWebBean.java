/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.util;

import br.com.formedici.carros.controller.bean.DefaultSessionBean;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author victor
 *
 * Alteracao : 19/03/2009 , Por : Caio Rodrigo Paulucci Mudanca do util para uma
 * versao atualizada com funcoes novas.
 * ---------------------------------------------------------------
 */
public class PadraoWebBean {
    ///Para paginação

    private Integer codigoTela = null;
    private Integer qtdeRegistros = new Integer(25);
    private Integer pagina = new Integer(1);
    public static String NOME_SISTEMA = "Sistema Carros";
    //para popup dinâmico
    private String LOCALIZACAO_BEANS_CONTROLE = null;
    private String LOCALIZACAO_POJOS = null;
    public static String PESQUISA_BEAN_SUFIXO = "Bean";
    // utilizado para WebBeans que não necessitam de um Bean próprio
    private PadraoBean bean;
    //variáveis para carregar imagem

    protected Locale getDefaultLocale() {
        return new Locale("pt", "BR");
    }

    public DefaultSessionBean getDefaultSessionBean() {
        return (DefaultSessionBean) JSFHelper.getManagedBean("DefaultSessionBean");
    }

    public PopupManagerWebBean getPopupManagerWebBean() {
        return (PopupManagerWebBean) JSFHelper.getManagedBean("popupWebBean");
    }

    public Object getWebBean(String beanName) {
        return JSFHelper.getManagedBean(beanName);
    }

    public Integer getQtdeRegistros() {
        return qtdeRegistros;
    }

    public void setQtdeRegistros(Integer qtdeRegistros) {
        this.qtdeRegistros = qtdeRegistros;
    }

    public Integer getPagina() {
        if (pagina <= 0) {
            setPagina(1);
        }
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    protected String getPesquisaNomeObjetoPrincipal() {
        return this.getDefaultSessionBean().getPesquisaNomeObjetoPrincipal();
    }

    protected String getPesquisaTitulo() {
        return this.getDefaultSessionBean().getPesquisaTitulo();
    }

    protected String getPesquisaInterfaceBean() {
        return this.getDefaultSessionBean().getPesquisaInterfaceBean();
    }

    protected void setPesquisaNomeObjetoPrincipal(String nome) {
        this.getDefaultSessionBean().setPesquisaNomeObjetoPrincipal(nome);
    }

    protected void setPesquisaTitulo(String titulo) {
        this.getDefaultSessionBean().setPesquisaTitulo(titulo);
    }

    protected void setPesquisaInterfaceBean(String bean) {
        this.getDefaultSessionBean().setPesquisaInterfaceBean(bean);
    }

    protected String getPesquisaCampos() {
        return this.getDefaultSessionBean().getPesquisaCampos();
    }

    public String[] getPesquisaCamposVetor() {
        if (this.getDefaultSessionBean().getPesquisaCampos() == null) {
            return null;
        }
        return this.getDefaultSessionBean().getPesquisaCampos().split(",");
    }

    public void setPesquisaCampos(String nome) {
        this.getDefaultSessionBean().setPesquisaCampos(nome);
    }

    public void setPesquisaBeanEFuncaoDeRetorno(String nome) {
        this.getDefaultSessionBean().setPesquisaBeanEFuncaoDeRetorno(nome);
    }

    protected String getPesquisaBeanEFuncaoDeRetorno() {
        return this.getDefaultSessionBean().getPesquisaBeanEFuncaoDeRetorno();
    }

    protected String getPesquisaCabecalhos() {
        return this.getDefaultSessionBean().getPesquisaCabecalhos();
    }

    protected String[] getPesquisaCabecalhosVetor() {
        if (this.getDefaultSessionBean().getPesquisaCabecalhos() == null) {
            return null;
        }
        return this.getDefaultSessionBean().getPesquisaCabecalhos().split(",");
    }

    public void setPesquisaCabecalhos(String nome) {
        this.getDefaultSessionBean().setPesquisaCabecalhos(nome);
    }

    public String getPesquisaCamposReRender() {
        return this.getDefaultSessionBean().getPesquisaAjaxCamposReRender();
    }

    public void setPesquisaCamposReRender(String campo) {
        this.getDefaultSessionBean().setPesquisaAjaxCamposReRender(campo);
    }

    public String getPesquisaCampoDeRetorno() {
        return this.getDefaultSessionBean().getPesquisaCampoDeRetorno();
    }

    public void setPesquisaCampoDeRetorno(String campo) {
        this.getDefaultSessionBean().setPesquisaCampoDeRetorno(campo);
    }

    protected ValueBinding createValueBinding(String valueExpression) {
        return this.getContext().getApplication().createValueBinding(valueExpression);
    }

    protected ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
        return this.getContext().getApplication().getExpressionFactory().createValueExpression(
                this.getContext().getELContext(), valueExpression, valueType);
    }

    protected MethodExpression createActionExpression(String actionExpression, Class<?> returnType) {
        return this.getContext().getApplication().getExpressionFactory().createMethodExpression(
                this.getContext().getELContext(), actionExpression, returnType, new Class[0]);
    }

    public Object getObjetoDePesquisaSelecionado() {
        return this.getPopupManagerWebBean().getObjetoSelecionado();
    }

    public Boolean getPesquisaOn() {
        return this.getDefaultSessionBean().getPesquisaOn();
    }

    public void setPesquisaOn(Boolean pesquisaOn) {
        this.getDefaultSessionBean().setPesquisaOn(pesquisaOn);

    }

    public FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public String getLOCALIZACAO_BEANS_CONTROLE() {
        if (this.LOCALIZACAO_BEANS_CONTROLE == null) {
            this.LOCALIZACAO_BEANS_CONTROLE = "br.com.formedici.carros.controller.bean.";
        }
        return LOCALIZACAO_BEANS_CONTROLE;
    }

    public void setLOCALIZACAO_BEANS_CONTROLE(String LOCALIZACAO_BEANS_CONTROLE) {
        this.LOCALIZACAO_BEANS_CONTROLE = LOCALIZACAO_BEANS_CONTROLE;
    }

    public String getLOCALIZACAO_POJOS() {
        if (this.LOCALIZACAO_POJOS == null) {
            this.LOCALIZACAO_POJOS = "br.com.formedici.carros.model.";
        }
        return LOCALIZACAO_POJOS;
    }

    public void setLOCALIZACAO_POJOS(String LOCALIZACAO_POJOS) {
        this.LOCALIZACAO_POJOS = LOCALIZACAO_POJOS;
    }

    public Object getBean(String classe) {
        Class classeC = null;
        try {
            classeC = Class.forName(classe);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PadraoWebBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.getBean(classeC);
    }

    public Object getBean(Class classe) {
        Object objRetorno = null;
        try {
            objRetorno = classe.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(PadraoWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PadraoWebBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
         * Object objRetorno = null; try { objRetorno =
         * this.getContexto().lookup(classe.getName()); } catch (NamingException
         * ex) { Logger.getLogger(PadraoMB.class.getName()).log(Level.SEVERE,
         * null, ex); }
         */
        return objRetorno;
    }

    protected void limpaParametrosDePesquisa() {
        this.getPopupManagerWebBean().limpaParametros();
    }

    protected void setPesquisaFrase(String frase) {
        this.getDefaultSessionBean().setPesquisaFrase(frase);
    }

    protected String getPesquisaFrase() {
        return this.getDefaultSessionBean().getPesquisaFrase();
    }

    public String getIp() {
        HttpServletRequest request = (HttpServletRequest) this.getContext().getExternalContext().getRequest();
        String ip = request.getRemoteAddr();
        return ip;
    }

    public PadraoBean getBean() {
        if (bean == null) {
            bean = new PadraoBean() {

                @Override
                public PadraoDAO getDAO() {
                    if (DAO == null) {
                        DAO = new PadraoDAO();
                    }
                    return DAO;
                }
            };
        }
        return bean;
    }

    public void setBean(PadraoBean bean) {
        this.bean = bean;
    }

    public Integer getCodigoTela() {
        return codigoTela;
    }

    public Boolean getLoginBeneficiario() {
        return this.getDefaultSessionBean().getLoginBeneficiarioControle();
    }

    public void setLoginBeneficiario(Boolean loginBeneficiario) {
        this.getDefaultSessionBean().setLoginBeneficiarioControle(loginBeneficiario);
    }

    @SuppressWarnings("static-access")
    public void preparaRelatorioPdf(String caminho, JRBeanCollectionDataSource ds, Map parametros, String nomeDoArquivo) throws IOException {
        byte[] bytes = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            //caminho = JasperCompileManager.compileReportToFile(caminho);
            JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(new File(caminho));

            bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, ds);

        } catch (JRException e) {
            e.printStackTrace();
        }

        if (bytes != null && bytes.length > 0) {
            ExternalContext context = JSFHelper.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) context.getResponse();
            //response.setHeader("Content-Disposition", "attachment;filename=\"" + nomeDoArquivo + "\"");
            response.setContentLength((int) bytes.length); // O tamanho do arquivo
            //response.setContentType("application/x-download"); // e obviamente o tipo
            response.setContentType("application/pdf"); // e obviamente o tipo

            ServletOutputStream out = response.getOutputStream();
            InputStream in = new ByteArrayInputStream(bytes);
            byte[] buf = new byte[bytes.length];
            int count;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            in.close();
            out.flush();
            out.close();
            facesContext.responseComplete();
        }
    }

    public String getAjuda() {
        Object retorno = JSFHelper.getSessionAttribute("arquivohelp");
        if (retorno != null) {
            return (String) retorno;
        } else {
            return null;
        }
    }

    // Usar apenas se souber pra que serve
    public void forceNewView() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }
}