/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Transient;

/**
 *
 * @author caio
 */
public class PadraoModel implements Serializable {
    @Transient
    private Boolean selecionado;
    
    protected String dateTimeToString(Date cal) {
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm");
        ret = sdf.format(cal);
        ret += "H" + sdfh.format(cal);
        return ret;
    }

    protected String timeToString(Date cal) {
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        ret = sdf.format(cal);
        return ret;
    }

    protected String dateToString(Date cal) {
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ret = sdf.format(cal);
        return ret;
    }

    protected Date stringToDate(String str){
        try {
            Date data = null;
            SimpleDateFormat sdf = new SimpleDateFormat();
            data = sdf.parse(str);
            return data;
        } catch (ParseException ex) {
            return null;
        }
    }
    
    protected String calendarTimeToString(Calendar cal) {
        String ret = "";
        Date dia = cal.getTime();
        ret = this.dateTimeToString(dia);
        return ret;
    }

    protected String calendarToString(Calendar cal) {
        String ret = "";
        Date dia = cal.getTime();
        return this.dateToString(dia);
    }

    public Boolean getSelecionado() {
        if (selecionado==null){
            setSelecionado(false);
        }
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }
}
