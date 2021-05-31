/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author Henrique
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Carro.findCarroAll",      query="SELECT c FROM Carro c ORDER BY c.codcarro"),
    @NamedQuery(name="Carro.findCarroById",     query="SELECT c FROM Carro c WHERE c.codcarro= :id"),
})
public class Carro extends PadraoModel implements Serializable {

    @Id
    @Column(name="codcarro", nullable=false)
    private Integer codcarro;
    
    @Column(name="modelo", length=50, nullable=false)
    private String modelo;

    @Column(name="fabricante", length=50, nullable=false)
    private String fabricante;

    @Column(name="cor", length=50, nullable=false)
    private String cor;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="ano", nullable=false)
    private Date ano;

    @ManyToMany(mappedBy = "carros")
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    
    public Carro() {
        
    }

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

    public Integer getCodcarro() {
        return codcarro;
    }

    public void setCodcarro(Integer codcarro) {
        this.codcarro = codcarro;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carro other = (Carro) obj;
        if (this.codcarro != other.codcarro && (this.codcarro == null || !this.codcarro.equals(other.codcarro))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.codcarro != null ? this.codcarro.hashCode() : 0);
        return hash;
    }
}
