/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.controller.bean;

import br.com.formedici.carros.model.Carro;
import br.com.formedici.carros.util.PadraoBean;
import br.com.formedici.carros.util.PadraoDAO;
import br.com.formedici.carros.util.Util;
import java.util.List;

/**
 *
 * @author Henrique
 */
public class CarroBean extends PadraoBean {

    @Override
    public PadraoDAO getDAO() {
        if (super.DAO == null) {
            super.DAO = new PadraoDAO();
            super.DAO.setEntityManager(super.getEntityManager());
        }
        return super.DAO;
    }

    public List<Carro> consultar(String modelo) {
        String consulta = "SELECT c FROM Carro c";
        if (!Util.isNullOrEmpty(modelo)) {
            consulta += Util.colocaWhereOuAnd(consulta) + "c.modelo LIKE '%" + modelo + "%'";
        }
        consulta += " ORDER BY c.codcarro";
        return getDAO().consultaQuery(consulta);
    }
}