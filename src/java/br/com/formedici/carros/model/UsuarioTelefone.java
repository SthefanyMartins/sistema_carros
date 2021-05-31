package br.com.formedici.carros.model;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name="usuario_telefone")
public class UsuarioTelefone extends PadraoModel implements Serializable{

    @EmbeddedId
    private UsuarioTelefonePK id;

    @Column(name="numero", length=12, nullable=false)
    private String numero;

    /**
     * 1 - Celular
     * 2 - Telefone
     */
    @Column(name="tipo", nullable=false)
    private Integer tipo;

    @ManyToOne
    @JoinColumn(name = "codusuario", referencedColumnName="codusuario", insertable=false, updatable=false)
    private Usuario codusuario;

    public UsuarioTelefone(){

    }

    public String getTipoDescricao(){
        String valor = null;
        if(getTipo() == 1){
            valor = "Celular";
        }else{
            valor = "Telefone";
        }
        return valor;
    }

    public Usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Usuario codusuario) {
        this.codusuario = codusuario;
    }

    public UsuarioTelefonePK getId() {
        return id;
    }

    public void setId(UsuarioTelefonePK id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioTelefone other = (UsuarioTelefone) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
