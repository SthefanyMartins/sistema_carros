package br.com.formedici.carros.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Sthefany
 */
@Embeddable
public class UsuarioCarroPK implements Serializable {
    
    @Column(name="codusuario")
    private Integer codusuario;

    @Column(name="codcarro")
    private Integer codcarro;

    public UsuarioCarroPK() {

    }

    public Integer getCodcarro() {
        return codcarro;
    }

    public void setCodcarro(Integer codcarro) {
        this.codcarro = codcarro;
    }

    public Integer getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Integer codusuario) {
        this.codusuario = codusuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioCarroPK other = (UsuarioCarroPK) obj;
        if (this.codusuario != other.codusuario && (this.codusuario == null || !this.codusuario.equals(other.codusuario))) {
            return false;
        }
        if (this.codcarro != other.codcarro && (this.codcarro == null || !this.codcarro.equals(other.codcarro))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.codusuario != null ? this.codusuario.hashCode() : 0);
        hash = 23 * hash + (this.codcarro != null ? this.codcarro.hashCode() : 0);
        return hash;
    }

    
}
