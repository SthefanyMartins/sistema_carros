/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author VICTOR
 */
public class TipoServico implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return arg2;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if (arg2 instanceof Integer) {
            int valor = (Integer) arg2;
            if (valor == 1) {
                return "Brasíndice (Materiais)";
            } else if (valor == 2) {
                return "Brasíndice (Med./Soluções)";
            } else if (valor == 3) {
                return "Simpro";
            } else if (valor == 4) {
                return "Próprio";
            } else if (valor == 5) {
                return "Taxas";
            } else if (valor == 6) {
                return "Procedimentos";
            } else if (valor == 7) {
                return "Despesas";
            } else if (valor == 8) {
                return "TNUMM";
            }
        }
        return "";
    }
}