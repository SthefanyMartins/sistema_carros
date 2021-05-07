/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author gabri
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Usuario.findUsuarioAll",      query="SELECT u FROM Usuario u ORDER BY u.codusuario"),
    @NamedQuery(name="Usuario.findUsuarioById",     query="SELECT u FROM Usuario u WHERE u.codusuario = :id"),
    @NamedQuery(name="Usuario.findUsuarioByLogin",     query="SELECT u FROM Usuario u WHERE u.login = :login"),
})
public class Usuario extends PadraoModel implements Serializable{

    @Id
    @Column(name="codusuario", nullable=false)
    private Integer codusuario;

    @Column(name="login", length=50, nullable=false)
    private String login;

    @Column(name="senha", length=50, nullable=false)
    private String senha;

    public Usuario(){

    }

    public Integer getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Integer codusuario) {
        this.codusuario = codusuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.codusuario != other.codusuario && (this.codusuario == null || !this.codusuario.equals(other.codusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.codusuario != null ? this.codusuario.hashCode() : 0);
        return hash;
    }

}
