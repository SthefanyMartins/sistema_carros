/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.view;

import br.com.formedici.carros.controller.bean.CarroBean;
import br.com.formedici.carros.model.Carro;
import br.com.formedici.carros.util.Util;
import br.com.formedici.carros.util.JSFHelper;
import br.com.formedici.carros.util.PadraoWebBean;
import java.util.Date;
import java.util.LinkedHashMap;
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
    private String pesqFabricante;
    private String pesqCor;
    private Integer pesqAno;

    public CarroWebBean() {
        this.bean = new CarroBean();
    }

    public String consultar() {
        setListaCarros(new ListDataModel(getBean().consultar(getPesqModelo(), getPesqFabricante(),
                       getPesqCor(), getPesqAno())));
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
        if(validarParaSalvar()){
            getBean().salvar(getCarro());
            consultar();
            return "carro";
        }
        return null;
    }

    public Boolean validarParaSalvar(){
        Boolean valida = true;
        String testaModelo = getCarro().getModelo().trim();
        String testaFabricante = getCarro().getFabricante().trim();
        String testaCor = getCarro().getCor().trim();
        if(Util.isNullOrEmpty(testaModelo)){
            JSFHelper.addErrorMessage("Campo Modelo é obrigatório!");
            getCarro().setModelo("");
            valida = false;
        }
        if(Util.isNullOrEmpty(testaFabricante)){
            JSFHelper.addErrorMessage("Campo Fabricante é obrigatório!");
            getCarro().setFabricante("");
            valida = false;
        }
        if(Util.isNullOrEmpty(testaCor)){
            JSFHelper.addErrorMessage("Campo Cor é obrigatório!");
            getCarro().setCor("");
            valida = false;
        }
        if(getCarro().getAno().compareTo(new Date()) > 0){
            JSFHelper.addErrorMessage("Digite uma data menor que a atual!");
            valida = false;
        }
        return valida;
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

    public void limparConsulta(){
        setPesqModelo("");
        setPesqFabricante("");
        setPesqCor("");
        setPesqAno(null);
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

    public String getPesqFabricante() {
        return pesqFabricante;
    }

    public void setPesqFabricante(String pesqFabricante) {
        this.pesqFabricante = pesqFabricante;
    }

    public Integer getPesqAno() {
        return pesqAno;
    }

    public void setPesqAno(Integer pesqAno) {
        this.pesqAno = pesqAno;
    }

    public String getPesqCor() {
        return pesqCor;
    }

    public void setPesqCor(String pesqCor) {
        this.pesqCor = pesqCor;
    }
}
