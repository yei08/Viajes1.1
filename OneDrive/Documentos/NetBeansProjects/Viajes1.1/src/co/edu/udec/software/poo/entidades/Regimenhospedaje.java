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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "regimenhospedaje", catalog = "mydb", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"TipoPension"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regimenhospedaje.findAll", query = "SELECT r FROM Regimenhospedaje r"),
    @NamedQuery(name = "Regimenhospedaje.findByTipoPension", query = "SELECT r FROM Regimenhospedaje r WHERE r.tipoPension = :tipoPension")})
public class Regimenhospedaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TipoPension", nullable = false, length = 30)
    private String tipoPension;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regimenHospedajeTipoPension")
    private List<Cliente> clienteList;
    @JoinColumns({
        @JoinColumn(name = "Hoteles_idHoteles", referencedColumnName = "idHoteles", nullable = false),
        @JoinColumn(name = "Hoteles_PlazaDisponible_FechaDeRefencia", referencedColumnName = "PlazaDisponible_FechaDeRefencia", nullable = false)})
    @ManyToOne(optional = false)
    private Hotel hotel;

    public Regimenhospedaje() {
    }

    public Regimenhospedaje(String tipoPension) {
        this.tipoPension = tipoPension;
    }

    public String getTipoPension() {
        return tipoPension;
    }

    public void setTipoPension(String tipoPension) {
        this.tipoPension = tipoPension;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoPension != null ? tipoPension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regimenhospedaje)) {
            return false;
        }
        Regimenhospedaje other = (Regimenhospedaje) object;
        if ((this.tipoPension == null && other.tipoPension != null) || (this.tipoPension != null && !this.tipoPension.equals(other.tipoPension))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.software.poo.entidades.Regimenhospedaje[ tipoPension=" + tipoPension + " ]";
    }
    
}
