/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.controller.bean;

import br.com.formedici.carros.model.Telefone;
import br.com.formedici.carros.model.Usuario;
import br.com.formedici.carros.util.PadraoBean;
import br.com.formedici.carros.util.PadraoDAO;
import br.com.formedici.carros.util.Util;
import java.util.LinkedHashMap;
import java.util.List;
/**
 *
 * @author gabri
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

    public List<Telefone> consultarTelefones(Usuario usuario){
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<String, Object>();
        parametros.put("user", usuario);
        return getDAO().retornaList("Telefone.findTelefoneByUser", parametros);
    }

    public void deletarTelefones(List<Telefone> telefonesDeletados){
        for(Telefone t : telefonesDeletados){
            Telefone tel = (Telefone) findOneByExample(t);
            tel.setUsuario(null);
            excluir(tel);
        }
    }
}
