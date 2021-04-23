/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.jpautils;


import br.com.formedici.carros.model.PadraoModel;
import br.com.formedici.carros.util.Util;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author caio
 */
public class JPAUtil {

    private Class CLASSEPADRAO = PadraoModel.class;
    private String NOMEOBJPRINCIPAL = " objPrincipal ";
    private String SELECT = "SELECT ";
    private String FROM = " FROM ";
    private String WHERE = " WHERE ";
    private String AND = " AND ";
    private String JOIN = " JOIN ";
    private String UPPER = " UPPER ";
    private String hqlFormado = "";
    private String subHQLFormado = "";

    public String getHQLByExample(Object objeto) {
        String where = percorreClasse(objeto, ModoDePesquisa.MODO_SEM_LIKE,NOMEOBJPRINCIPAL.trim(),null);
        if (!Util.isNullOrEmpty(where)){
            where = WHERE+where;
        }
        this.setHqlFormado(SELECT + NOMEOBJPRINCIPAL + FROM + objeto.getClass().getSimpleName() + NOMEOBJPRINCIPAL+where);
        return this.getHqlFormado();
    }

    public String getHQLByExample(Object objeto, Integer modoDePequisa,Integer modoSens) {
        String where = percorreClasse(objeto, modoDePequisa,NOMEOBJPRINCIPAL.trim() , modoSens);
        if (!Util.isNullOrEmpty(where)){
            where = WHERE+where;
        }
        this.setHqlFormado(SELECT + NOMEOBJPRINCIPAL + FROM + objeto.getClass().getSimpleName() + NOMEOBJPRINCIPAL+where);
        return this.getHqlFormado();
    }
    
    public String getHQLByExample(Object objeto, Integer modoDePequisa) {
        String where = percorreClasse(objeto, modoDePequisa,NOMEOBJPRINCIPAL.trim() , null);
        if (!Util.isNullOrEmpty(where)){
            where = WHERE+where;
        }
        this.setHqlFormado(SELECT + NOMEOBJPRINCIPAL + FROM + objeto.getClass().getSimpleName() + NOMEOBJPRINCIPAL+where);
        return this.getHqlFormado();
    }

    public String percorreClasse(Object objeto, Integer modoPesquisa , String caminhoCampo , Integer modoSens) {
        String fimDaFraseFormada = "";
        //Boolean retorno = false;
        Boolean temWhere = false;
        Field[] campos = objeto.getClass().getDeclaredFields();
        for (Field field : campos) {
            if (field.getModifiers() != 9) {
                if (pegaValorDoCampo(field, objeto) != null) {
                    String campoPreparado = "";
                    if (temWhere) {
                        campoPreparado += " " + AND + " ";
                    }
                    temWhere = true;
                    Boolean colchetes = false;
                    if (Util.isNullOrEmpty(caminhoCampo)){
                        colchetes = true;
                    }else if (NOMEOBJPRINCIPAL.trim().equals(caminhoCampo)){
                        colchetes = true;
                    }
                    String retornoStrPreparada = this.preparaString(field, objeto, modoPesquisa , colchetes,caminhoCampo,modoSens);
                    if (!Util.isNullOrEmpty(retornoStrPreparada)){
                        campoPreparado += retornoStrPreparada;
                        fimDaFraseFormada += campoPreparado;
                    }
                }
            }
        }
        /*if (temWhere) {
            fimDaFraseFormada = " " + WHERE + " " + fimDaFraseFormada;
        }*/
        //this.setHqlFormado(this.getHqlFormado() + fimDaFraseFormada);
        return fimDaFraseFormada;
    }

    private Object pegaValorDoCampo(Field field, Object objeto) {
        String novoNome = field.getName().substring(1);
        novoNome = field.getName().substring(0, 1).toUpperCase() + novoNome;
        try {
            Method metodo = objeto.getClass().getMethod("get" + novoNome, new Class[0]);
            Object objRet = (Object) metodo.invoke(objeto, new Object[0]);
            if (!field.getType().equals(String.class)) {
                return objRet;
            } else {
                if (Util.isNullOrEmpty((String) objRet)) {
                    return null;
                } else {
                    return objRet;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String preparaString(Field field, Object objeto, Integer modoPesquisa,Boolean colchetes,String caminhoCampo , Integer modoSens) {
        String uppercase = "";
        if (modoPesquisa == null) {
            modoPesquisa = ModoDePesquisa.MODO_SEM_LIKE;
        }
        if (modoSens == null){
            modoSens = ModoDePesquisa.MODO_CASESENSITIVE;
        }
        String colchetecsd = "";
        String colchetecse = "";
        if (modoSens == ModoDePesquisa.MODO_CASEINSENSITIVE){
            colchetecsd = ")";
            colchetecse = "(";
            uppercase = this.UPPER;
        }else{
            colchetecsd = "";
            colchetecse = "";
            uppercase = "";
        }
        
        String colcheted = "";
        String colchetee = "";
        if (colchetes){
            colcheted = ")";
            colchetee = "(";
        }
        if (!Util.isNullOrEmpty(caminhoCampo)){
            caminhoCampo = caminhoCampo+".";
        }
        String campoPreparado = "";
        String nome = field.getName();
        if (field.getType().equals(String.class)) {
            String valorDaPesquisa = ((String) pegaValorDoCampo(field, objeto));
            if (modoSens == ModoDePesquisa.MODO_CASEINSENSITIVE){
                valorDaPesquisa = ((String) pegaValorDoCampo(field, objeto)).toUpperCase();
            }
            String tipoPesquisa = "";
            String inicialLike = "";
            String finalLike = "";
            if (ModoDePesquisa.MODO_SEM_LIKE.equals(modoPesquisa)) {
                tipoPesquisa = " = ";
            } else if (ModoDePesquisa.MODO_COM_LIKE.equals(modoPesquisa)) {
                tipoPesquisa = " like ";
            } else if (ModoDePesquisa.MODO_FULL_LIKE.equals(modoPesquisa)) {
                tipoPesquisa = " like ";
                inicialLike = "%";
                finalLike = "%";
            } else if (ModoDePesquisa.MODO_INICIAL_LIKE.equals(modoPesquisa)) {
                tipoPesquisa = " like ";
                inicialLike = "%";
                finalLike = "";
            } else if (ModoDePesquisa.MODO_FINAL_LIKE.equals(modoPesquisa)) {
                tipoPesquisa = " like ";
                inicialLike = "";
                finalLike = "%";
            }
            
            campoPreparado = colchetee + uppercase + colchetecse + caminhoCampo + nome + " " + colchetecsd + tipoPesquisa + " " + " '" + inicialLike + valorDaPesquisa + finalLike + "' "+colcheted;
        } else if (field.getType().equals(Integer.class)) {
            campoPreparado = colchetee + caminhoCampo + nome + " = " + String.valueOf((Integer) pegaValorDoCampo(field, objeto)) + colcheted;
        } else if (field.getType().equals(Boolean.class)) {
            campoPreparado = colchetee + caminhoCampo + nome + " = " + String.valueOf((Boolean) pegaValorDoCampo(field, objeto)) + colcheted;
        } else if (field.getType().equals(Date.class)) {
            SimpleDateFormat sdf = new SimpleDateFormat();
            String data = sdf.format((Date) pegaValorDoCampo(field, objeto));
            
            campoPreparado = colchetee + caminhoCampo + nome + " = " + data + colcheted;
        }else if (pegaValorDoCampo(field, objeto).getClass().getSuperclass().equals(PadraoModel.class)) {
            String newJoin = preparaJoin(field,pegaValorDoCampo(field, objeto),modoPesquisa, nome,modoSens);
        }
        return campoPreparado;
    }
    
    private String preparaJoin(Field field, Object objeto , Integer modoPesquisa , String nomeCampoDoJoin , Integer modoSens){
        String campo = nomeCampoDoJoin.trim();
        String prefixo = "";
        if (!Util.isNullOrEmpty(campo)){
            prefixo = ".";
        }
        //String strDoJoin = campo+"."+percorreClasse(objeto, modoPesquisa,campo);
        String strDoJoin = percorreClasse(objeto, modoPesquisa,campo,modoSens);
        
        return strDoJoin;
    }
    
    public String getHqlFormado() {
        return hqlFormado;
    }

    public void setHqlFormado(String hqlFormado) {
        this.hqlFormado = hqlFormado;
    }

    public String getSubHQLFormado() {
        return subHQLFormado;
    }

    public void setSubHQLFormado(String subHQLFormado) {
        this.subHQLFormado = subHQLFormado;
    }
}
