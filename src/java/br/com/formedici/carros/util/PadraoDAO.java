package br.com.formedici.carros.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * @author victor
 */
public class PadraoDAO {

    //Ambiente de Produção
    private EntityManager entityManager = null;
    public EntityManager getEntityManager() {
        if ((entityManager == null) || (!entityManager.isOpen())) {
            entityManager = JPAHelper.createEntityManager();
        }
        return entityManager;
    }

    //Ambiente para testes locais~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    private static EntityManagerFactory fabrica;
//    private static EntityManager entityManager = null;
//
//    public static EntityManager getEntityManager() {
//
//        if ((fabrica == null) || (!fabrica.isOpen())) {
//            fabrica = Persistence.createEntityManagerFactory("loggi2");
//        }
//
//        if ((entityManager == null) || (!entityManager.isOpen())) {
//            entityManager = fabrica.createEntityManager();
//        }
//        return entityManager;
//     }
    //FIM ambiente testes locais------------------------------------------------

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public void salvar(Object objeto) {
        getEntityManager().merge(objeto);
    }

    public void excluir(Object objeto) {
        getEntityManager().remove(getEntityManager().merge(objeto));
    }

    public List listaTodos(Class classe) {
        Query query = getEntityManager().createNamedQuery(classe.getSimpleName() + ".find" + classe.getSimpleName() + "All");
        query.setHint("toplink.refresh", "true");
        return query.getResultList();
    }

    public Boolean existeIdSimples(Class classe, Object id) {
        if (id == null) {
            return false;
        }
        Query query = getEntityManager().createNamedQuery(classe.getSimpleName() + ".find" + classe.getSimpleName() + "ById");
        query.setHint("toplink.refresh", "true");
        query.setParameter("id", id);
        List resultado = query.getResultList();

        if ((resultado == null) || (resultado.isEmpty())) {
            resultado = null;
            return false;
        } else {
            resultado = null;
            return true;
        }
    }

    public Boolean existeIdSimplesCount(Class classe, Object id) {
        if (id == null) {
            return false;
        }
        Query query = getEntityManager().createNamedQuery(classe.getSimpleName() + ".count" + classe.getSimpleName());
        query.setHint("toplink.refresh", "true");
        query.setParameter("id", id);
        Object resultado = query.getSingleResult();

        if (resultado == null) {
            return false;
        } else {
            return (((Long) resultado) > 0);
        }
    }

    public Boolean existeParametros(String namedQuery, LinkedHashMap<String, Object> parametros) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        query.setHint("toplink.refresh", "true");

        int mapsize = parametros.size();

        Iterator keyValuePares = parametros.entrySet().iterator();

        for (int i = 0; i < mapsize; i++) {
            Map.Entry linha = (Map.Entry) keyValuePares.next();
            String key = (String) linha.getKey();
            Object valor = linha.getValue();
            query.setParameter(key, valor);
        }

        List resultado = query.getResultList();

        if ((resultado == null) || (resultado.isEmpty())) {
            return false;
        } else {
            return true;
        }
    }

    public String retornaNome(Class classe, Object id) {
        if (id == null) {
            return null;
        }
        Query query = getEntityManager().createNamedQuery(classe.getSimpleName() + ".find" + classe.getSimpleName() + "NomeById");
        query.setHint("toplink.refresh", "true");
        query.setParameter("id", id);
        List resultado = query.getResultList();

        if ((resultado == null) || (resultado.isEmpty())) {
            return null;
        } else {
            return resultado.iterator().next().toString();
        }
    }

    public Object retornaObjeto(Class classe, Object id) {
        if (id == null) {
            return null;
        }
        Query query = criaNamedQuery(classe.getSimpleName() + ".find" + classe.getSimpleName() + "ById");
        query.setParameter("id", id);
        query.setHint("toplink.refresh", "true");
        List resultado = query.getResultList();

        if ((resultado == null) || (resultado.isEmpty())) {
            return null;
        } else {
            return resultado.iterator().next();
        }
    }

    public Object retornaObjeto(String namedQuery, LinkedHashMap<String, Object> parametros) {
        List resultado = retornaList(namedQuery, parametros);

        if ((resultado == null) || (resultado.isEmpty())) {
            return null;
        } else {
            return resultado.iterator().next();
        }
    }

    public Object retornaObjeto(String frase) {
        Query query = this.criaQuery(frase);
        List resultado = query.getResultList();

        if ((resultado == null) || (resultado.isEmpty())) {
            return null;
        } else {
            return resultado.iterator().next();
        }
    }

    public Object retornaObjetoPorNome(Class classe, Object nome) {
        Query query = getEntityManager().createNamedQuery(classe.getSimpleName() + ".find" + classe.getSimpleName() + "ByNome");
        query.setHint("toplink.refresh", "true");
        query.setParameter("nome", nome);
        List resultado = query.getResultList();

        if ((resultado == null) || (resultado.isEmpty())) {
            return null;
        } else {
            return resultado.iterator().next();
        }
    }

    public List retornaList(String namedQuery, LinkedHashMap<String, Object> parametros) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        query.setHint("toplink.refresh", "true");

        int mapsize = parametros.size();

        Iterator keyValuePares = parametros.entrySet().iterator();

        for (int i = 0; i < mapsize; i++) {
            Map.Entry linha = (Map.Entry) keyValuePares.next();
            String key = (String) linha.getKey();
            Object valor = linha.getValue();
            query.setParameter(key, valor);
        }

        List resultado = query.getResultList();

        if ((resultado == null) || (resultado.isEmpty())) {
            return null;
        } else {
            return resultado;
        }
    }

    public List consultaNativa(String sql) {
        //Query query = getEntityManager().createNativeQuery(sql);
        Query query = criaQueryNativa(sql);
        query.setHint("toplink.refresh", "true");
        return query.getResultList();
    }

    public List consultaLinhaNativa(String sql) {
        Query query = getEntityManager().createNativeQuery(sql);
        query.setHint("toplink.refresh", "true");
        List retorno = query.getResultList();
        if ((retorno != null) && (! retorno.isEmpty())) {
            return (List) retorno.iterator().next();
        } else {
            return null;
        }
    }

    public List consultaQuery(String frase) {
        Query query = getEntityManager().createQuery(frase);
        query.setHint("toplink.refresh", "true");
        return query.getResultList();
    }

    public void executaQuery(String frase) {
        Query query = getEntityManager().createQuery(frase);
        query.executeUpdate();
    }

    public void executaQueryNativa(String frase) {
        Query query = getEntityManager().createNativeQuery(frase);
        query.executeUpdate();
    }

    public Query criaQuery(String frase) {
        Query query = getEntityManager().createQuery(frase);
        query.setHint("toplink.refresh", "true");
        return query;
    }

    public Query criaQueryNativa(String frase) {
        Query query = getEntityManager().createNativeQuery(frase);
        query.setHint("toplink.refresh", "true");
        return query;
    }

    public Query criaNamedQuery(String frase) {
        Query query = getEntityManager().createNamedQuery(frase);
        query.setHint("toplink.refresh", "true");
        return query;
    }

    public Long getCountFraseNativo(String frase) {
        List linha = consultaLinhaNativa(frase);
        return (Long) linha.get(0);
    }

    public Long getCountFrase(String frase) {
        Query query = criaQuery(frase);
        return (Long) query.getSingleResult();
    }

    public Double getSomaFraseNativo(String frase) {
        List linha = consultaLinhaNativa(frase);
        return (Double) linha.get(0);
    }
}