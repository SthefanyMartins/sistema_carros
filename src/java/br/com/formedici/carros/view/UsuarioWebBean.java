package br.com.formedici.carros.view;

import br.com.formedici.carros.controller.bean.UsuarioBean;
import br.com.formedici.carros.model.Usuario;
import br.com.formedici.carros.util.PadraoWebBean;
import br.com.formedici.carros.util.Util;
import br.com.formedici.carros.util.JSFHelper;
import java.util.LinkedHashMap;
import javax.faces.model.ListDataModel;
/**
 *
 * @author Sthefany
 */
public class UsuarioWebBean extends PadraoWebBean{
    private UsuarioBean bean;
    private Usuario usuario;
    private ListDataModel listaUsuarios;
    private Boolean edicao = false;
    private String confirmaSenha;
    private String pesqLogin;
    private Boolean requiredPassword = true;
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
        setRequiredPassword(true);
        setEdicao(false);

        return "form";
    }

    public String editar() {
        setUsuario((Usuario) getListaUsuarios().getRowData());
        setArmazenarSenha(getUsuario().getSenha());
        setEdicao(true);
        setRequiredPassword(false);
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
        if (!getEdicao()) {//quando não estiver editando
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
        }else{//quando estiver editando
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

    public void limparConsulta(){
        setPesqLogin("");
    }
    
    //getters e setters
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

    public Boolean getRequiredPassword() {
        return requiredPassword;
    }

    public void setRequiredPassword(Boolean requiredPassword) {
        this.requiredPassword = requiredPassword;
    }

    public String getArmazenarSenha() {
        return armazenarSenha;
    }

    public void setArmazenarSenha(String armazenarSenha) {
        this.armazenarSenha = armazenarSenha;
    }

}
