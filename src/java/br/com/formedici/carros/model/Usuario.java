package br.com.formedici.carros.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Sthefany
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

    @OneToMany(mappedBy = "codusuario", cascade =  CascadeType.ALL, fetch=FetchType.EAGER)
    private List<UsuarioTelefone> telefones = new ArrayList<UsuarioTelefone>();

    @ManyToMany
    @JoinTable(name = "usuario_carro",
            joinColumns = {@JoinColumn(name = "codusuario")},
            inverseJoinColumns = {@JoinColumn(name = "codcarro")})
    private List<Carro> carros = new ArrayList<Carro>();

    public Usuario(){

    }

    public UsuarioTelefonePK novoItemTelefone(){
        Integer cod = 0;
        for(UsuarioTelefone tel: getTelefones()){
            if(tel.getId().getItemtelefone() > cod){
                cod = tel.getId().getItemtelefone();
            }
        }
        UsuarioTelefonePK pk = new UsuarioTelefonePK();
        pk.setItemtelefone(cod + 1);
        pk.setCodusuario(getCodusuario());
        return pk;
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

    public List<UsuarioTelefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<UsuarioTelefone> telefones) {
        this.telefones = telefones;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
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
