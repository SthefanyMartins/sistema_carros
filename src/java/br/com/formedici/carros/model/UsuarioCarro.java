package br.com.formedici.carros.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sthefany
 */
@Entity
@Table(name="usuario_carro")

public class UsuarioCarro extends PadraoModel implements Serializable{
    @EmbeddedId
    private UsuarioCarroPK id;

    @ManyToOne
    @JoinColumn(name="codusuario",referencedColumnName="codusuario", nullable=false, insertable=false, updatable=false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="codcarro", referencedColumnName="codcarro", nullable=false, insertable=false, updatable=false)
    private Carro carro;

    public UsuarioCarro() {
        
    }

    public Carro getCodcarro() {
        return carro;
    }

    public void setCodcarro(Carro codcarro) {
        this.carro = codcarro;
    }

    public Usuario getCodusuario() {
        return usuario;
    }

    public void setCodusuario(Usuario codusuario) {
        this.usuario = codusuario;
    }

    public UsuarioCarroPK getId() {
        return id;
    }

    public void setId(UsuarioCarroPK id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioCarro other = (UsuarioCarro) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

}
