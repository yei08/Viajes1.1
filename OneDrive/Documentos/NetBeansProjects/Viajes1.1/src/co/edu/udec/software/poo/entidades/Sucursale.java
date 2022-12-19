/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.software.poo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "sucursales", catalog = "mydb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursale.findAll", query = "SELECT s FROM Sucursale s"),
    @NamedQuery(name = "Sucursale.findByIdSucursal", query = "SELECT s FROM Sucursale s WHERE s.idSucursal = :idSucursal"),
    @NamedQuery(name = "Sucursale.findByDireccion", query = "SELECT s FROM Sucursale s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sucursale.findByNroTelefonico", query = "SELECT s FROM Sucursale s WHERE s.nroTelefonico = :nroTelefonico")})
public class Sucursale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idSucursal", nullable = false)
    private Integer idSucursal;
    @Column(name = "Direccion", length = 45)
    private String direccion;
    @Column(name = "NroTelefonico", length = 45)
    private String nroTelefonico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalesidSucursal")
    private List<Vuelo> vueloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalesidSucursal")
    private List<Cliente> clienteList;

    public Sucursale() {
    }

    public Sucursale(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNroTelefonico() {
        return nroTelefonico;
    }

    public void setNroTelefonico(String nroTelefonico) {
        this.nroTelefonico = nroTelefonico;
    }

    @XmlTransient
    public List<Vuelo> getVueloList() {
        return vueloList;
    }

    public void setVueloList(List<Vuelo> vueloList) {
        this.vueloList = vueloList;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursale)) {
            return false;
        }
        Sucursale other = (Sucursale) object;
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.software.poo.entidades.Sucursale[ idSucursal=" + idSucursal + " ]";
    }
    
}
