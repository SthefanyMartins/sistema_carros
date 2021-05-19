package br.com.formedici.carros.view;

import br.com.formedici.carros.controller.bean.UsuarioBean;
import br.com.formedici.carros.model.Telefone;
import br.com.formedici.carros.model.Usuario;
import br.com.formedici.carros.util.PadraoWebBean;
import br.com.formedici.carros.util.Util;
import br.com.formedici.carros.util.JSFHelper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.faces.model.ListDataModel;
/**
 *
 * @author Sthefany
 */
public class UsuarioWebBean extends PadraoWebBean{
    private UsuarioBean bean;
    private Usuario usuario;
    private Telefone telefone = new Telefone();
    private ListDataModel listaUsuarios;
    private ListDataModel listaTelefones;
    private Boolean edicao = false;
    private Boolean statusTelefoneEdicao = false;
    private String confirmaSenha;
    private String pesqLogin;
    private String armazenarSenha;
    private List<Telefone> listaTelefonesDeletados = new ArrayList<Telefone>();

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
        return "form";
    }

    public String editar() {
        setUsuario((Usuario) getListaUsuarios().getRowData());
        setArmazenarSenha(getUsuario().getSenha());
        setListaTelefones(new ListDataModel(getUsuario().getTelefones()));
        setEdicao(true);
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
            getBean().deletarTelefones(getListaTelefonesDeletados());
            getBean().salvar(getUsuario());
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
            LinkedHashMap<String, Object> parametros = new LinkedHashMap<String, Object>();
            parametros.put("login", getUsuario().getLogin());
            Boolean retornaExiste = getBean().existeParametros("Usuario.findUsuarioByLogin", parametros);
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
        mensagemExclusao = getBean().excluir(getUsuario());

        if (! mensagemExclusao.equals("")) {
            JSFHelper.addErrorMessage(mensagemExclusao);
            return null;
        }
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
            getTelefone().setCodtelefone(definirCodigoTelefone());
        }else{
            getUsuario().getTelefones().remove(getTelefone());
        }
        getTelefone().setUsuario(getUsuario());
        getUsuario().getTelefones().add(getTelefone());
        setListaTelefones(new ListDataModel(getUsuario().getTelefones()));
        setTelefone(new Telefone());
        getTelefone().setTipo(0);
        getTelefone().setNumero("");
        setStatusTelefoneEdicao(false);
        return null;
    }

    public Integer definirCodigoTelefone(){
        Integer cod = getBean().proximoCodigo(Telefone.class, "codtelefone");
        Integer codFinal = cod;
        List<Telefone> telefones = getUsuario().getTelefones();
        for(Telefone t : telefones){
            if(t.getCodtelefone() >= cod){
                codFinal = t.getCodtelefone() + 1;
            }
        }
        return codFinal;
    }

    public String editarTelefone(){
        setTelefone((Telefone) getListaTelefones().getRowData());
        setStatusTelefoneEdicao(true);
        return null;
    }

    public String setarTelefoneExcluir(){
        setTelefone((Telefone) getListaTelefones().getRowData());
        return null;
    }

    public String excluirTelefone(){
        Integer cod = getBean().proximoCodigo(Telefone.class, "codtelefone");
        if(getTelefone().getCodtelefone() < cod){
            getListaTelefonesDeletados().add(getTelefone());
        }
        getUsuario().getTelefones().remove(getTelefone());
        setListaTelefones(new ListDataModel(getUsuario().getTelefones()));
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

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
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

    public List<Telefone> getListaTelefonesDeletados() {
        return listaTelefonesDeletados;
    }

    public void setListaTelefonesDeletados(List<Telefone> listaTelefonesDeletados) {
        this.listaTelefonesDeletados = listaTelefonesDeletados;
    }
}
