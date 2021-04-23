/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.controller.bean;

import br.com.formedici.carros.util.PadraoBean;
import br.com.formedici.carros.util.PadraoDAO;

/**
 *
 * @author Cleriston
 */
public class DadosPopupBean extends PadraoBean{

    @Override
    public PadraoDAO getDAO() {
        if (super.DAO == null) {
            super.DAO = new PadraoDAO();
            super.DAO.setEntityManager(this.getEntityManager());
        }
        return super.DAO;
    }
    

}
