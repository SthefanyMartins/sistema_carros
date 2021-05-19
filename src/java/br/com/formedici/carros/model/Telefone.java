package br.com.formedici.carros.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Sthefany
 */

@Entity
@NamedQueries({
    @NamedQuery(name="Telefone.findTelefoneAll",      query="SELECT t FROM Telefone t ORDER BY t.codtelefone"),
    @NamedQuery(name="Telefone.findTelefoneById",     query="SELECT t FROM Telefone t WHERE t.codtelefone= :id"),
    @NamedQuery(name="Telefone.findTelefoneByUser",     query="SELECT t FROM Telefone t WHERE t.usuario= :user"),
})
public class Telefone extends PadraoModel implements Serializable{
    @Id
    @Column(name="codtelefone", nullable=false)
    private Integer codtelefone;

    @Column(name="numero", length=12, nullable=false)
    private String numero;

    /**
     * 1 - Celular
     * 2 - Telefone
     */
    @Column(name="tipo", nullable=false)
    private Integer tipo;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    public Telefone(){

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

    public Integer getCodtelefone() {
        return codtelefone;
    }

    public void setCodtelefone(Integer codtelefone) {
        this.codtelefone = codtelefone;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Telefone other = (Telefone) obj;
        if (this.codtelefone != other.codtelefone && (this.codtelefone == null || !this.codtelefone.equals(other.codtelefone))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.codtelefone != null ? this.codtelefone.hashCode() : 0);
        return hash;
    }
}
