package br.com.formedici.carros.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 *
 * @author Sthefany
 */
@Embeddable
public class UsuarioTelefonePK implements Serializable{
    @Column(name="itemtelefone", nullable=false)
    private Integer itemtelefone;

    @Column(name="codusuario")
    private Integer codusuario;

    public UsuarioTelefonePK(){
        
    }

    public Integer getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Integer codusuario) {
        this.codusuario = codusuario;
    }

    public Integer getItemtelefone() {
        return itemtelefone;
    }

    public void setItemtelefone(Integer itemtelefone) {
        this.itemtelefone = itemtelefone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioTelefonePK other = (UsuarioTelefonePK) obj;
        if (this.itemtelefone != other.itemtelefone && (this.itemtelefone == null || !this.itemtelefone.equals(other.itemtelefone))) {
            return false;
        }
        if (this.codusuario != other.codusuario && (this.codusuario == null || !this.codusuario.equals(other.codusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.itemtelefone != null ? this.itemtelefone.hashCode() : 0);
        hash = 83 * hash + (this.codusuario != null ? this.codusuario.hashCode() : 0);
        return hash;
    }
}
