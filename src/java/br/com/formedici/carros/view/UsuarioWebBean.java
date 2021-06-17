package br.com.formedici.carros.view;

import br.com.formedici.carros.controller.bean.UsuarioBean;
import br.com.formedici.carros.model.Carro;
import br.com.formedici.carros.model.Usuario;
import br.com.formedici.carros.model.UsuarioTelefone;
import br.com.formedici.carros.util.PadraoWebBean;
import br.com.formedici.carros.util.Util;
import br.com.formedici.carros.util.JSFHelper;
import br.com.formedici.carros.util.PopupManagerWebBean;
import javax.faces.model.ListDataModel;
/**
 *
 * @author Sthefany
 */
public class UsuarioWebBean extends PadraoWebBean{
    private UsuarioBean bean;
    private Usuario usuario;
    private UsuarioTelefone telefone = new UsuarioTelefone();
    private Carro carro = new Carro();
    private Integer idcarro;
    private ListDataModel listaUsuarios;
    private ListDataModel listaTelefones;
    private ListDataModel listaCarros;
    private Boolean edicao = false;
    private Boolean statusTelefoneEdicao = false;
    private String confirmaSenha;
    private String pesqLogin;
    private String armazenarSenha;

    public UsuarioWebBean(){
        this.bean = new UsuarioBean();
    }

    public String consultar() {
        setListaUsuarios(new ListDataModel(getBean().consultar(getPesqLogin())));
        return "usuario";
    }

    public String inserir() {
        setUsuario(new Usuario());
        getUsuario().setCodusuario(getBean().proximoCodigo(Usuario.class, "codusuario"));
        setEdicao(false);
        setListaTelefones(new ListDataModel());
        setListaCarros(new ListDataModel());
        getTelefone().setNumero("");
        setCarro(new Carro());
        return "form";
    }

    public String editar() {
        setUsuario((Usuario) getListaUsuarios().getRowData());
        setUsuario((Usuario) getBean().getObjeto(Usuario.class, getUsuario().getCodusuario()));
        setArmazenarSenha(getUsuario().getSenha());
        setListaTelefones(new ListDataModel(getUsuario().getTelefones()));
        setListaCarros(new ListDataModel(getUsuario().getCarros()));
        getTelefone().setNumero("");
        setEdicao(true);
        setCarro(new Carro());
        return "form";
    }

    public String salvarMais() {
        if (this.salvar() == null) {
            return null;
        }
        JSFHelper.addInfoMessage("Usuario gravado com sucesso!");
        return this.inserir();
    }

    public String salvar() {
        if(validarParaSalvar()){
            getBean().salvar(getUsuario(), getEdicao());
            consultar();
            return "usuario";
        }
        return null;
    }

    public Boolean validarParaSalvar(){
        Boolean valida = true;
        String testaLogin = getUsuario().getLogin().trim();
        String testaSenha = getUsuario().getSenha().trim();
        if (!getEdicao()) {
            if (getUsuario().getCodusuario() == null) {
                JSFHelper.addErrorMessage("Código do Usuário está em branco!");
                valida = false;
            }
            if (getBean().existeIdSimples(Usuario.class, getUsuario().getCodusuario())) {
                JSFHelper.addErrorMessage("Código do usuário já cadastrado!");
                valida = false;
            }
            Boolean retornaExiste = getBean().existeUsuario(getUsuario().getLogin());
            if(retornaExiste){
               JSFHelper.addErrorMessage("Esse login já existe!");
               getUsuario().setLogin("");
               valida = false;
            }
            if(Util.isNullOrEmpty(testaSenha)){
                JSFHelper.addErrorMessage("Campo Senha é obrigatório!");
                getUsuario().setSenha("");
                valida = false;
            }
            if(Util.isNullOrEmpty(getConfirmaSenha().trim())){
                JSFHelper.addErrorMessage("Campo Confirmar a senha é obrigatório!");
                getUsuario().setSenha("");
                valida = false;
            }
        }else{
            if(Util.isNullOrEmpty(testaSenha) && Util.isNullOrEmpty(getConfirmaSenha().trim())){
                getUsuario().setSenha(getArmazenarSenha());
            }
        }
        if(Util.isNullOrEmpty(testaLogin)){
            JSFHelper.addErrorMessage("Campo Login é obrigatório!");
            getUsuario().setLogin("");
            valida = false;
        }
        if(!getUsuario().getSenha().equals(getConfirmaSenha()) && !Util.isNullOrEmpty(testaSenha) && !Util.isNullOrEmpty(getConfirmaSenha().trim())){
            JSFHelper.addErrorMessage("Senhas diferentes!");
            getUsuario().setSenha("");
            setConfirmaSenha("");
            valida = false;
        }
        return valida;
    }

    public String setarUsuarioExcluir(){
        setUsuario((Usuario) getListaUsuarios().getRowData());
        return null;
    }

    public String excluir() {
        String mensagemExclusao = "";
        getBean().excluirLista(getUsuario().getTelefones());
        mensagemExclusao = getBean().excluir(getUsuario());
        if (! mensagemExclusao.equals("")) {
            JSFHelper.addErrorMessage(mensagemExclusao);
            return null;
        }
        getUsuario().setLogin("");
        getUsuario().setSenha("");
        getUsuario().setCodusuario(0);
        consultar();
        return null;
    }

    public String limparConsulta(){
        setPesqLogin("");
        return null;
    }

    public String adicionarTelefone() {
        if (getTelefone().getTipo() != 1 && getTelefone().getTipo() != 2) {
            JSFHelper.addErrorMessage("Selecione um tipo de telefone!");
            return null;
        } else if (Util.isNullOrEmpty(getTelefone().getNumero())) {
            JSFHelper.addErrorMessage("Digite um número de telefone válido!");
            return null;
        } else if (!getStatusTelefoneEdicao()) {
            getTelefone().setCodusuario(getUsuario());
            getTelefone().setId(getUsuario().novoItemTelefone());
        }else{
            getUsuario().getTelefones().remove(getTelefone());
        }
        getUsuario().getTelefones().add(getTelefone());
        setListaTelefones(new ListDataModel(getUsuario().getTelefones()));
        setTelefone(new UsuarioTelefone());
        setStatusTelefoneEdicao(false);
        return null;
         
    }

    public String editarTelefone(){
        setTelefone((UsuarioTelefone) getListaTelefones().getRowData());
        setStatusTelefoneEdicao(true);
        return null;

    }

    public String setarTelefoneExcluir(){
        setTelefone((UsuarioTelefone) getListaTelefones().getRowData());
        return null;
    }

    public String excluirTelefone(){
        getUsuario().getTelefones().remove(getTelefone());
        setListaTelefones(new ListDataModel(getUsuario().getTelefones()));
        setTelefone(new UsuarioTelefone());
        return null;
    }

    public String setarCarroExcluir(){
        setCarro((Carro) getListaCarros().getRowData());
        return null;
    }

    public String excluirCarro(){
        getUsuario().getCarros().remove(getCarro());
        setListaCarros(new ListDataModel(getUsuario().getCarros()));
        return null;
    }

    //popups pesquisa de carro
    public String changeCarro() {
        if (getCarro().getCodcarro() != null) {
            Carro catCarroAux = (Carro) getBean().getObjeto(Carro.class, getCarro().getCodcarro());
            if (catCarroAux != null) {
                setCarro(catCarroAux);
            } else {
                setCarro(new Carro());
            }
        } else {
            setCarro(new Carro());
        }
        return null;
    }

    public String pesquisaCarro() {
        super.limpaParametrosDePesquisa();

        PopupManagerWebBean popupManagerWB = (PopupManagerWebBean) JSFHelper.getManagedBean("popupWebBean");
        popupManagerWB.setCamposSQL("c.codcarro, c.modelo, c.fabricante, c.cor");
        popupManagerWB.setTabelasSQL("Carro c");
        popupManagerWB.setCampoRetorno(0);
        
        super.setPesquisaBeanEFuncaoDeRetorno("usuarioWebBean.popupRetornoCarro");
        super.setPesquisaCabecalhos("Código,Modelo,Fabricante,Cor");
        super.setPesquisaCamposReRender("codcarro,modeloCarro,fabricanteCarro,corCarro,anoCarro");

        return null;
    }

    public String popupRetornoCarro() {
        Carro objeto = (Carro) this.getBean().getObjeto(Carro.class, super.getObjetoDePesquisaSelecionado());
        this.setCarro(objeto);
        super.limpaParametrosDePesquisa();

        return null;
    }

    public String inserirCarro() {
        Boolean existeCarro = this.getBean().existeIdSimples(Carro.class, getCarro().getCodcarro());
        if (!existeCarro) {
            JSFHelper.addErrorMessage("Digite um Carro cadastrado");
        } else if(getUsuario().existeCarro(getCarro().getCodcarro())){
            JSFHelper.addErrorMessage("Carro já incluso!");
        } else {
            getUsuario().getCarros().add(getCarro());
            setListaCarros(new ListDataModel(getUsuario().getCarros()));
        }
        setCarro(new Carro());
        super.limpaParametrosDePesquisa();
        return null;
    }

    @Override
    public UsuarioBean getBean(){
        return bean;
    }

    public void setBean(UsuarioBean bean) {
        this.bean = bean;
    }

    public Boolean getEdicao() {
        return edicao;
    }

    public void setEdicao(Boolean edicao) {
        this.edicao = edicao;
    }

    public ListDataModel getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ListDataModel listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public String getPesqLogin() {
        return pesqLogin;
    }

    public void setPesqLogin(String pesqLogin) {
        this.pesqLogin = pesqLogin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getArmazenarSenha() {
        return armazenarSenha;
    }

    public void setArmazenarSenha(String armazenarSenha) {
        this.armazenarSenha = armazenarSenha;
    }

    public UsuarioTelefone getTelefone() {
        return telefone;
    }

    public void setTelefone(UsuarioTelefone telefone) {
        this.telefone = telefone;
    }

    public Boolean getStatusTelefoneEdicao() {
        return statusTelefoneEdicao;
    }

    public void setStatusTelefoneEdicao(Boolean statusTelefoneEdicao) {
        this.statusTelefoneEdicao = statusTelefoneEdicao;
    }

    public ListDataModel getListaTelefones() {
        return listaTelefones;
    }

    public void setListaTelefones(ListDataModel listaTelefones) {
        this.listaTelefones = listaTelefones;
    }

    public ListDataModel getListaCarros() {
        return listaCarros;
    }

    public void setListaCarros(ListDataModel listaCarros) {
        this.listaCarros = listaCarros;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Integer getIdcarro() {
        return idcarro;
    }

    public void setIdcarro(Integer idcarro) {
        this.idcarro = idcarro;
    }
}
