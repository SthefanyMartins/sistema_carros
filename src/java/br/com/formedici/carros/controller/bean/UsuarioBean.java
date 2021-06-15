package br.com.formedici.carros.controller.bean;

import br.com.formedici.carros.model.Usuario;
import br.com.formedici.carros.model.UsuarioTelefone;
import br.com.formedici.carros.util.PadraoBean;
import br.com.formedici.carros.util.PadraoDAO;
import br.com.formedici.carros.util.Util;
import java.util.List;
/**
 *
 * @author Sthefany
 */
public class UsuarioBean extends PadraoBean{
    @Override
    public PadraoDAO getDAO() {
        if (super.DAO == null) {
            super.DAO = new PadraoDAO();
            super.DAO.setEntityManager(super.getEntityManager());
        }
        return super.DAO;
    }

    public List<Usuario> consultar(String login) {
        String consulta = "SELECT u FROM Usuario u";
        if (!Util.isNullOrEmpty(login)) {
            consulta += Util.colocaWhereOuAnd(consulta) + "u.login LIKE '%" + login + "%'";
        }
        consulta += " ORDER BY u.codusuario";
        return getDAO().consultaQuery(consulta);
    }

    public String salvar(Usuario usuarioPrincipal, Boolean edicao){
        String retorno = "";
        try{
            if(edicao){
                Usuario usuarioBanco = (Usuario) super.getObjeto(Usuario.class, usuarioPrincipal.getCodusuario());
                this.getEntityManager().getTransaction().begin();
                for(UsuarioTelefone catTelefoneBanco : usuarioBanco.getTelefones()){
                    if(!usuarioPrincipal.getTelefones().contains(catTelefoneBanco)){
                        this.getDAO().excluir(catTelefoneBanco);
                    }
                }
            }
            if (!this.getEntityManager().getTransaction().isActive()) {
                this.getEntityManager().getTransaction().begin();
            }
            this.getDAO().salvar(usuarioPrincipal);
            this.getEntityManager().flush();
            this.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante o processo.");
            e.printStackTrace();
            retorno = "Ocorreu um erro ao salvar o registro. Erro: " + e.toString();
            if (this.getEntityManager().getTransaction().isActive()) {
                this.getEntityManager().getTransaction().rollback();
            }
        }
        this.getEntityManager().close();
        this.getEntityManager().clear();
        return retorno;
    }
}
