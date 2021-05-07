/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.controller.bean;

import br.com.formedici.carros.model.Usuario;
import br.com.formedici.carros.util.PadraoBean;
import br.com.formedici.carros.util.PadraoDAO;
import br.com.formedici.carros.util.Util;
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
}
