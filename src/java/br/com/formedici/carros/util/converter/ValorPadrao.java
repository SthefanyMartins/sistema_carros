/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.util.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author ricardo camargo
 */
public class ValorPadrao implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return arg2;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

        if (arg2 instanceof Date) {//data
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            if (formata.format(arg2).equals("01/01/0001")) {
                return "Outros";
            } else {
                return formata.format(arg2);
            }
        } else if (arg2 instanceof Boolean) {//boolean
            Boolean valor = (Boolean) arg2;
            if (valor) {
                return "Sim";
            } else {
                return "Não";
            }
        } else {//outros
            return arg2.toString();
        }
    }
}
