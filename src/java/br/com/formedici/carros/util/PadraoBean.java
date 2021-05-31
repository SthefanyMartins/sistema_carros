/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.util;

import br.com.formedici.carros.jpautils.JPAUtil;
import br.com.formedici.carros.jpautils.ModoDePesquisa;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author victor
 */
public abstract class PadraoBean {

    private Integer modoPesquisa = 2;
    protected PadraoDAO DAO;

    public EntityManager getEntityManager() {
        return getDAO().getEntityManager();
    }

    public PadraoDAO getDAO() {
        if (this.DAO == null) {
            this.DAO = new PadraoDAO();
        }
        return this.DAO;
    }

    public void setDAO(PadraoDAO DAO) {
        this.DAO = DAO;
    }

    public String salvar(Object objeto) {
        String retorno = "";
        try {
            if (! this.getEntityManager().getTransaction().isActive()) {
                this.getEntityManager().getTransaction().begin();
            }
            this.getDAO().salvar(objeto);
            this.getEntityManager().flush();
            this.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante o processo.");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
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

    public String excluir(Object objeto) {
        String retorno = "";
        try {
            this.getEntityManager().getTransaction().begin();
            this.getDAO().excluir(objeto);
            this.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante o processo.");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            retorno = "Erro ao excluir registro. Registro está sendo utilizado em outro cadastro.";
            if (this.getEntityManager().getTransaction().isActive()) {
                this.getEntityManager().getTransaction().rollback();
            }
        }
        this.getEntityManager().close();
        return retorno;
    }

    public String excluirLista(List lista) {
        String retorno = "";
        try {
            this.getEntityManager().getTransaction().begin();
            for (Object object : lista) {
                this.getDAO().excluir(object);
            }
            this.getEntityManager().getTransaction().commit();
            //this.getEntityManager().flush();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante o processo.");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            retorno = "Erro ao excluir registro. Registro está sendo utilizado em outro cadastro.";
            if (this.getEntityManager().getTransaction().isActive()) {
                this.getEntityManager().getTransaction().rollback();
            }
        }
        this.getEntityManager().close();
        return retorno;
    }

    public List getLista(Class classe) {
        List lista = getDAO().consultaQuery("Select obj from " + classe.getSimpleName() + " as obj");
        this.getEntityManager().close();
        return lista;
    }

    public List listaTodos(Class classe) {
        List lista = this.getDAO().listaTodos(classe);
        this.getEntityManager().close();
        return lista;
    }

    public Boolean existeIdSimples(Class classe, Object id) {
        if (id == null) {
            return false;
        }
        Boolean resultado = this.getDAO().existeIdSimples(classe, id);
        this.getEntityManager().close();
        return resultado;
    }

    public Boolean existeParametros(String namedQuery, LinkedHashMap<String, Object> parametros) {
        Boolean resultado = this.getDAO().existeParametros(namedQuery, parametros);
        this.getEntityManager().close();
        return resultado;
    }

    public String getNome(Class classe, Object id) {
        String resultado = this.getDAO().retornaNome(classe, id);
        this.getEntityManager().close();
        return resultado;
    }

    public String salvarLista(List lista) {
        String retorno = "";
        try {
            this.getEntityManager().getTransaction().begin();
            for (Object objeto : lista) {
                this.getDAO().salvar(objeto);
            }
            this.getEntityManager().flush();
            this.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante o processo.");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            retorno = "Ocorreu um erro ao salvar o registro. Erro: " + e.toString();
            if (this.getEntityManager().getTransaction().isActive()) {
                this.getEntityManager().getTransaction().rollback();
            }
        }
        this.getEntityManager().close();
        this.getEntityManager().clear();
        return retorno;
    }

    public Object getObjeto(Class classe, Object id) {
        if (id == null) {
            return null;
        }
        Object resultado = this.getDAO().retornaObjeto(classe, id);
        this.getEntityManager().close();
        return resultado;
    }

    public Object getObjeto(String namedQuery, LinkedHashMap<String, Object> parametros) {
        Object resultado = this.getDAO().retornaObjeto(namedQuery, parametros);
        this.getEntityManager().close();
        return resultado;
    }

    public List getList(String namedQuery, LinkedHashMap<String, Object> parametros) {
        List resultado = this.getDAO().retornaList(namedQuery, parametros);
        this.getEntityManager().close();
        return resultado;
    }

    public List getLista(String query) {
        List resultado = this.getDAO().consultaQuery(query);
        this.getEntityManager().close();
        return resultado;
    }

    public List getListaNativa(String query) {
        List resultado = this.getDAO().consultaNativa(query);
        this.getEntityManager().close();
        return resultado;
    }

    public List findByExample(Object obj) {
        JPAUtil util = new JPAUtil();
        List objRet = null;
        try {
            objRet = getDAO().consultaQuery(util.getHQLByExample(obj, getModoPesquisa(), ModoDePesquisa.MODO_CASEINSENSITIVE));
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            objRet = null;
        }
        return objRet;
    }

    public Object findOneByExample(Object obj) {
        JPAUtil util = new JPAUtil();
        Object objRet = null;
        try {
            objRet = getDAO().retornaObjeto(util.getHQLByExample(obj));
        } catch (Exception e) {
            objRet = null;
        }
        return objRet;
    }

    public List consultaPeloExemplo(Object objeto) {
        return this.findByExample(objeto);
    }

    public Integer getModoPesquisa() {
        return modoPesquisa;
    }

    public void setModoPesquisa(Integer modoPesquisa) {
        this.modoPesquisa = modoPesquisa;
    }

    public Integer proximoCodigo(Class classe, String codigo) {
        String sFrase = "SELECT MAX(obj." + codigo + ") FROM " + classe.getSimpleName() + " obj";
        Object retorno = getDAO().retornaObjeto(sFrase);
        if (retorno == null) {
            return 1;
        } else {
            return ((Integer) retorno) + 1;
        }
    }
}
