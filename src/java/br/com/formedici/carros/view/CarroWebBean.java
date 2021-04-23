/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.view;

import br.com.formedici.carros.controller.bean.CarroBean;
import br.com.formedici.carros.model.Carro;
import br.com.formedici.carros.util.JSFHelper;
import br.com.formedici.carros.util.PadraoWebBean;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Henrique
 */
public class CarroWebBean extends PadraoWebBean {

    private CarroBean bean;
    private Carro carro;
    private ListDataModel listaCarros;
    private Boolean edicao = false;
    private String pesqModelo;

    public CarroWebBean() {
        this.bean = new CarroBean();
    }

    public String consultar() {
        setListaCarros(new ListDataModel(getBean().consultar(getPesqModelo())));
        return "carro";
    }

    public String inserir() {
        setCarro(new Carro());
        getCarro().setCodcarro(getBean().proximoCodigo(Carro.class, "codcarro"));
        setEdicao(false);

        return "form";
    }

    public String editar() {
        setCarro((Carro) getListaCarros().getRowData());
        setEdicao(true);

        return "form";
    }

    public String salvarMais() {
        if (this.salvar() == null) {
            return null;
        }
        JSFHelper.addInfoMessage("Carro gravado com sucesso!");
        return this.inserir();
    }

    public String salvar() {
        if (!getEdicao()) {
            if (getCarro().getCodcarro() == null) {
                JSFHelper.addErrorMessage("Código do Carro está em branco!");
                return null;
            }
            if (getBean().existeIdSimples(Carro.class, getCarro().getCodcarro())) {
                JSFHelper.addErrorMessage("Código do Carro já cadastrado!");
                return null;
            }
        }

        getBean().salvar(getCarro());
        consultar();
        return "carro";
    }

    public String setarCarroExcluir(){
        setCarro((Carro) getListaCarros().getRowData());
        return null;
    }

    public String excluir() {
        String mensagemExclusao = "";
        mensagemExclusao = getBean().excluir(getCarro());

        if (! mensagemExclusao.equals("")) {
            JSFHelper.addErrorMessage(mensagemExclusao);
            return null;
        }
        consultar();
        return null;
    }

    @Override
    public CarroBean getBean() {
        return bean;
    }

    public void setBean(CarroBean bean) {
        this.bean = bean;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public ListDataModel getListaCarros() {
        return listaCarros;
    }

    public void setListaCarros(ListDataModel listaCarros) {
        this.listaCarros = listaCarros;
    }

    public Boolean getEdicao() {
        return edicao;
    }

    public void setEdicao(Boolean edicao) {
        this.edicao = edicao;
    }

    public String getPesqModelo() {
        return pesqModelo;
    }

    public void setPesqModelo(String pesqModelo) {
        this.pesqModelo = pesqModelo;
    }
}
