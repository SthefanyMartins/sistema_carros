/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.util;

import br.com.formedici.carros.util.converter.ValorPadrao;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.richfaces.component.html.HtmlColumn;
import org.richfaces.component.html.HtmlComponentControl;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author caio
 */
public class PopupManagerWebBean extends PadraoWebBean {

    public static String MANAGED_BEAN_NAME = "popupWebBean";
    public static String CAMPO_TIPO_TRANSIENT = "@Transient->";
    private Object objetoDePesquisa;
    private PadraoBean bean = null;
    private ListDataModel lista;
    private String tituloDaPesquisa = super.getPesquisaTitulo();
    private HtmlDataTable dataTable = null;
    //private HtmlDatascroller dataScroller = null;
    private Boolean colunasMontadas = false;
    private String campoPesquisaSelecionado;
    private String dadoPesquisado = new String();
    //
    private String camposSQL; //CAMPOS SELECIONADO
    private String tabelasSQL; // TABELAS DO FROM
    private String condicaoSQL; //Condições do WHERE
    private String atributoSQL; //Campos para o Where conforme a seleção
    private Integer campoRetorno = -1;
    /*
     * 0 - Igual
     * 1 - Começando por
     * 2 - Contenha
     */
    private Integer tipoLike = 1;

    public SelectItem[] getPossiveisCamposDePequisa() {
        if ((this.getPesquisaCabecalhosVetor() != null) && (this.getPesquisaCamposVetor() != null)) {
            String[] strCabecalhos = this.getPesquisaCabecalhosVetor();
            String[] strCampos = this.getPesquisaCamposVetor();
            SelectItem[] selects = new SelectItem[strCabecalhos.length];
            //if (strCabecalhos.length == strCampos.length) {
                for (int i = 0; i < strCabecalhos.length; i++) {
                    String strCab = strCabecalhos[i].trim();
                    String strCmp = strCampos[i].trim();
                    selects[i] = new SelectItem(strCmp, strCab);
                }
            //}
            return selects;
        } else {
            return new SelectItem[0];
        }
    }

    public void bClose(ActionEvent e) {
        this.limpaParametros();
    }

    public void limpaParametros() {
        this.setLOCALIZACAO_BEANS_CONTROLE(null);
        this.setLOCALIZACAO_POJOS(null);
        this.setPesquisaNomeObjetoPrincipal(null);
        this.setPesquisaTitulo(null);
        this.setPesquisaInterfaceBean(null);
        this.setPesquisaCampos("");
        this.setPesquisaCabecalhos("");
        this.setPesquisaCampoDeRetorno(null);
        this.setColunasMontadas(false);
        this.setPesquisaOn(true);
        this.setPesquisaBeanEFuncaoDeRetorno(null);
        this.setObjetoSelecionado(null);
        this.setLista(new ListDataModel());
        this.setCamposSQL(null);
        this.setTabelasSQL(null);
        this.setCondicaoSQL(null);
        this.setCampoRetorno(-1);
        this.setCampoPesquisaSelecionado("");
        this.setDadoPesquisado("");
    }

    public void montarDataTable() {
        this.getDataTable().getChildren().clear();
        String[] strCabecalhos = this.getPesquisaCabecalhosVetor();
        String[] strCampos = this.getPesquisaCamposVetor();
        //if (strCabecalhos.length == strCampos.length) {
            for (int i = 0; i < strCabecalhos.length; i++) {
                String strCabecalho = strCabecalhos[i];
                String strCampo = strCampos[i];
                if (getCamposSQL() != null) {
                    if (!strCampo.contains(CAMPO_TIPO_TRANSIENT)) {
                        this.getDataTable().getChildren().add(criaColuna(strCabecalho, this.getDataTable().getVar(), i));
                    } else {
                        if (!Util.isNullOrEmpty(this.getFrase())) {
                            this.setCamposSQL(this.getCamposSQL().replace("," + strCampo, ""));
                        }
                        this.getDataTable().getChildren().add(criaColuna(strCabecalho, this.getDataTable().getVar(), strCampo.replace(CAMPO_TIPO_TRANSIENT, "")));
                    }
                } else {
                    if (strCampo.contains(CAMPO_TIPO_TRANSIENT)) {
                        this.getDataTable().getChildren().add(criaColuna(strCabecalho, this.getDataTable().getVar(), strCampo.replace(CAMPO_TIPO_TRANSIENT, "")));
                    } else {
                        this.getDataTable().getChildren().add(criaColuna(strCabecalho, this.getDataTable().getVar(), strCampo));
                    }
                }
            }
        //}
    }

    public void consultar(ActionEvent e) {
        this.montarDataTable();

        this.setColunasMontadas(true);
        //}
        if (this.getCamposSQL() != null) {

            setLista(new ListDataModel(getBean().getDAO().consultaQuery(getFrase())));
        } else {
            Integer modoPesquisa = 2;
            if (getTipoLike() == 0) {
                modoPesquisa = 0;
            } else if (getTipoLike() == 1) {
                modoPesquisa = 4;
            } else if (getTipoLike() == 2) {
                modoPesquisa = 2;
            }
            
                PadraoBean beanAux = this.getBean();
                beanAux.setModoPesquisa(modoPesquisa);
                setLista(new ListDataModel(beanAux.consultaPeloExemplo(this.montaObjetoDeExemplo())));
        }
    }

    @Override
    public PadraoBean getBean() {
        //
        if (this.getPesquisaInterfaceBean() == null) {
            if (this.getPesquisaNomeObjetoPrincipal() != null) {
                this.setPesquisaInterfaceBean(this.getLOCALIZACAO_BEANS_CONTROLE() + this.getPesquisaNomeObjetoPrincipal() + PadraoWebBean.PESQUISA_BEAN_SUFIXO);
            } else {
                //Um bean padrao para realizar consultas.
                //Coloquei o beneficiario pois o padraoBean nao permite instanciar
                //xdsthis.setPesquisaInterfaceBean(BeneficiarioBean.class.getName());
            }
        }
        //
        this.bean = (PadraoBean) this.getBean(this.getPesquisaInterfaceBean());
        return bean;
    }

    private Object montaObjetoDeExemplo() {
        try {
            Class objDePesquisa = Class.forName(this.getLOCALIZACAO_POJOS() + this.getPesquisaNomeObjetoPrincipal());
            Object objDePesquisaNew = Class.forName(this.getLOCALIZACAO_POJOS() + this.getPesquisaNomeObjetoPrincipal()).newInstance();
            String nome = this.getCampoPesquisaSelecionado();
            if (nome != null) {
                String novoNome = this.getCampoPesquisaSelecionado().substring(1);
                Field[] campos = Class.forName(this.getLOCALIZACAO_POJOS() + this.getPesquisaNomeObjetoPrincipal()).getDeclaredFields();
                novoNome = this.getCampoPesquisaSelecionado().substring(0, 1).toUpperCase() + novoNome;
                Class tipoDoObjeto = null;
                for (Field field : campos) {
                    if (field.getName().equals(nome)) {
                        tipoDoObjeto = field.getType();
                    }
                }
                Method mSet = objDePesquisa.getMethod("set" + novoNome, tipoDoObjeto);
                if (Integer.class.equals(tipoDoObjeto)) {
                    if (!Util.isNullOrEmpty(this.getDadoPesquisado())) {
                        mSet.invoke(objDePesquisaNew, Integer.valueOf(this.getDadoPesquisado()));
                    }
                } else if (String.class.equals(tipoDoObjeto)) {
                    mSet.invoke(objDePesquisaNew, this.getDadoPesquisado());
                }
            }
            return objDePesquisaNew;
        } catch (InstantiationException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Object getCampoDeRetorno() {
        try {
            Class objDePesquisa = Class.forName(this.getLOCALIZACAO_POJOS() + this.getPesquisaNomeObjetoPrincipal());
            Object objDePesquisaNew = getObjetoSelecionado();
            String nome = this.getPesquisaCampoDeRetorno();
            Object objetoRetorno = null;
            if (nome != null) {
                String novoNome = this.getPesquisaCampoDeRetorno().substring(1);
                Field[] campos = Class.forName(this.getLOCALIZACAO_POJOS() + this.getPesquisaNomeObjetoPrincipal()).getDeclaredFields();
                novoNome = this.getPesquisaCampoDeRetorno().substring(0, 1).toUpperCase() + novoNome;

                Method mGet = objDePesquisa.getMethod("get" + novoNome);
                objetoRetorno = mGet.invoke(objDePesquisaNew);
            }
            return objetoRetorno;

        } catch (IllegalAccessException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PopupManagerWebBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object getObjetoDePesquisa() {
        return this.getDefaultSessionBean().getPesquisaObjetoSelecionado();
    }

    public void setObjetoDePesquisa(Object o) {
        this.objetoDePesquisa = o;
    }

    @Override
    public void setBean(PadraoBean bean) {
        this.bean = bean;
    }

    public ListDataModel getLista() {
        return lista;
    }

    public void setLista(ListDataModel lista) {
        this.lista = lista;
    }

    public String getTituloDaPesquisa() {
        if (tituloDaPesquisa == null) {
            tituloDaPesquisa = this.getPesquisaNomeObjetoPrincipal();
        }
        return tituloDaPesquisa;
    }

    public HtmlDataTable getDataTable() {
        String variavel = "item";
        if (this.dataTable == null) {
            this.dataTable = new HtmlDataTable();
            this.dataTable.setVar(variavel);
            this.dataTable.setId("dataTablePopup");
        }
        return this.dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    private HtmlColumn criaColuna(String header, String variavel, String campo) {
        String nomeDaPopup = "popupPesquisa";
        HtmlColumn coluna = new HtmlColumn();
        UIOutput titulo = new HtmlOutputLabel();
        HtmlComponentControl componentControl = new HtmlComponentControl();
        HtmlCommandLink linkInterno = new HtmlCommandLink();
        HtmlAjaxCommandLink valor = new HtmlAjaxCommandLink();
        valor.setId("headerColunaAjax" + header + "CommandLink");
        valor.setReRender(this.getPesquisaCamposReRender());
        linkInterno.setId("headerColuna" + header + "CommandLink");
        titulo.setId("headerColuna" + header);
        titulo.setValue(header);
        coluna.setId("idColuna" + header);
        coluna.setHeader(titulo);
        MethodExpression vbBSelecionado = null;
        ValueExpression vbCampo = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(
                FacesContext.getCurrentInstance().getELContext(), "#{" + variavel + "." + campo + "}", String.class);
        if (this.getPesquisaBeanEFuncaoDeRetorno() != null) {
            vbBSelecionado = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + this.getPesquisaBeanEFuncaoDeRetorno() + "}", String.class, new Class[0]);
        } else {
            vbBSelecionado = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + MANAGED_BEAN_NAME + ".bSelecionarClick}", String.class, new Class[0]);
        }
        valor.setValueExpression("value", vbCampo);
        valor.setActionExpression(vbBSelecionado);
        valor.setId("valorColuna" + header);
        valor.setImmediate(true);
        valor.setOnclick("Richfaces.hideModalPanel('" + nomeDaPopup + "');Richfaces.showModalPanel('modalMensagemProcessando')");
        valor.setOncomplete("Richfaces.hideModalPanel('modalMensagemProcessando')");
        //componentControl.setId("CC" + header);
        //componentControl.setFor(nomeDaPopup);
        //componentControl.setEvent("onclick");
        //componentControl.setOperation("close");
        //valor.getChildren().add(componentControl);
        //closePopupFrame.getChildren().add(linkInterno);
        //coluna.getChildren().add(closePopupFrame);
        coluna.getChildren().add(valor);
        //coluna.setFilterBy(this.getDataTable().getVar() + "." + campo);
        //coluna.setFilterEvent("onkeyup");
        coluna.setSortBy("#{" + variavel + "." + campo + "}");

        return coluna;
    }

    private HtmlColumn criaColuna(String header, String variavel, Integer ordem) {
        String nomeDaPopup = "popupPesquisa";
        HtmlColumn coluna = new HtmlColumn();
        UIOutput titulo = new HtmlOutputLabel();
        HtmlCommandLink linkInterno = new HtmlCommandLink();
        HtmlAjaxCommandLink valor = new HtmlAjaxCommandLink();
        HtmlOutputText outputText = new HtmlOutputText();
        String headerNome = header.replace(" ", "_");
        valor.setId("headerColunaAjax" + headerNome + "CommandLink");
        valor.setReRender(this.getPesquisaCamposReRender());
        linkInterno.setId("headerColuna" + headerNome + "CommandLink");
        titulo.setId("headerColuna" + headerNome);
        titulo.setValue(header);
        coluna.setId("idColuna" + headerNome);
        coluna.setHeader(titulo);
        MethodExpression vbBSelecionado = null;
        ValueExpression vbCampo = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(
                FacesContext.getCurrentInstance().getELContext(), "#{" + variavel + "[" + ordem + "]}", Object.class);

        if (this.getPesquisaBeanEFuncaoDeRetorno() != null) {
            vbBSelecionado = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + this.getPesquisaBeanEFuncaoDeRetorno() + "}", String.class, new Class[0]);
        } else {
            vbBSelecionado = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + MANAGED_BEAN_NAME + ".bSelecionarClick}", String.class, new Class[0]);
        }
        outputText.setValueExpression("value", vbCampo);
        outputText.setConverter(new ValorPadrao());

        valor.setActionExpression(vbBSelecionado);
        valor.setId("valorColuna" + headerNome);
        valor.setImmediate(true);
        valor.setOnclick("Richfaces.hideModalPanel('" + nomeDaPopup + "');Richfaces.showModalPanel('modalMensagemProcessando')");
        valor.setOncomplete("Richfaces.hideModalPanel('modalMensagemProcessando')");
        valor.getChildren().add(outputText);

        coluna.getChildren().add(valor);

        return coluna;
    }

    public void setTituloDaPesquisa(String tituloDaPesquisa) {
        this.tituloDaPesquisa = tituloDaPesquisa;
    }

    public Object getObjetoSelecionado() {
        if (this.getDefaultSessionBean().getPesquisaObjetoSelecionado() == null) {
            this.setObjetoSelecionado(getObjeto());
        }
        return (Object) this.getDefaultSessionBean().getPesquisaObjetoSelecionado();
    }

    public void setObjetoSelecionado(Object objetoSelecionado) {
        this.getDefaultSessionBean().setPesquisaObjetoSelecionado(objetoSelecionado);
    }

    
    public Boolean getColunasMontadas() {
        return colunasMontadas;
    }

    public void setColunasMontadas(Boolean colunasMontadas) {
        this.colunasMontadas = colunasMontadas;
    }

    @Override
    public Boolean getPesquisaOn() {
        return this.getDefaultSessionBean().getPesquisaOn();
    }

    @Override
    public void setPesquisaOn(Boolean pesquisaOn) {
        this.getDefaultSessionBean().setPesquisaOn(pesquisaOn);
    }

    public String getCampoPesquisaSelecionado() {
        return campoPesquisaSelecionado;
    }

    public void setCampoPesquisaSelecionado(String campoPesquisaSelecionado) {
        this.campoPesquisaSelecionado = campoPesquisaSelecionado;
    }

    public String getDadoPesquisado() {
        return dadoPesquisado;
    }

    public void setDadoPesquisado(String dadoPesquisado) {
        this.dadoPesquisado = dadoPesquisado;
    }

    public String getAtributoSQL() {
        return atributoSQL;
    }

    public void setAtributoSQL(String atributoSQL) {
        this.atributoSQL = atributoSQL;
    }

    public String getCamposSQL() {
        return camposSQL;
    }

    public void setCamposSQL(String camposSQL) {
        setPesquisaCampos(camposSQL);
        this.camposSQL = camposSQL;
    }

    public String getCondicaoSQL() {
        return condicaoSQL;
    }

    public void setCondicaoSQL(String condicaoSQL) {
        this.condicaoSQL = condicaoSQL;
    }

    public String getTabelasSQL() {
        return tabelasSQL;
    }

    public void setTabelasSQL(String tabelasSQL) {
        this.tabelasSQL = tabelasSQL;
    }

    public String getFrase() {
        StringBuilder frase = new StringBuilder("SELECT ");
        frase.append(getCamposSQL());
        frase.append(" FROM ");
        frase.append(getTabelasSQL());
        if ((!Util.isNullOrEmpty(getCondicaoSQL()))
                || (!Util.isNullOrEmpty(getDadoPesquisado()))) {
            frase.append(" WHERE ");
        }

        Boolean incluiAnd = false;
        if (!Util.isNullOrEmpty(getCondicaoSQL())) {
            incluiAnd = true;
            frase.append(getCondicaoSQL());
        }

        if (!Util.isNullOrEmpty(getDadoPesquisado())) {
            frase.append(incluiAnd ? " AND " : "")
                    .append(" UPPER(CONCAT(")
                    .append(getCampoPesquisaSelecionado())
                    .append(",'')) LIKE '")
                    .append(getTipoLike() == 2 ? "%" : "")
                    .append(getDadoPesquisado().toUpperCase())
                    .append(getTipoLike() > 0 ? "%" : "")
                    .append("'");
        }
        frase.append(" ORDER BY ");
        frase.append(getCampoPesquisaSelecionado());
        return frase.toString();
    }

    public Object getObjeto() {
        if ((getCampoRetorno() != null) && (getCampoRetorno() >= 0)) {
            Object[] objRet = (Object[]) getLista().getRowData();
            return objRet[getCampoRetorno()];
        } else {
            return getLista().getRowData();
        }
    }

    public Integer getCampoRetorno() {
        return campoRetorno;
    }

    public void setCampoRetorno(Integer campoRetorno) {
        this.campoRetorno = campoRetorno;
    }

    public Integer getTipoLike() {
        return tipoLike;
    }

    public void setTipoLike(Integer tipoLike) {
        this.tipoLike = tipoLike;
    }
}
