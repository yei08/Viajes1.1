/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.software.poo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "hoteles", catalog = "mydb", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idHoteles"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hotel.findAll", query = "SELECT h FROM Hotel h"),
    @NamedQuery(name = "Hotel.findByIdHoteles", query = "SELECT h FROM Hotel h WHERE h.hotelPK.idHoteles = :idHoteles"),
    @NamedQuery(name = "Hotel.findByNombre", query = "SELECT h FROM Hotel h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "Hotel.findByDireccion", query = "SELECT h FROM Hotel h WHERE h.direccion = :direccion"),
    @NamedQuery(name = "Hotel.findByNroTelefonico", query = "SELECT h FROM Hotel h WHERE h.nroTelefonico = :nroTelefonico"),
    @NamedQuery(name = "Hotel.findByPlazaDisponibleFechaDeRefencia", query = "SELECT h FROM Hotel h WHERE h.hotelPK.plazaDisponibleFechaDeRefencia = :plazaDisponibleFechaDeRefencia")})
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HotelPK hotelPK;
    @Column(name = "Nombre", length = 45)
    private String nombre;
    @Column(name = "Direccion", length = 45)
    private String direccion;
    @Column(name = "NroTelefonico", length = 45)
    private String nroTelefonico;
    @JoinColumn(name = "PlazaDisponible_FechaDeRefencia1", referencedColumnName = "FechaDeRefencia", nullable = false)
    @ManyToOne(optional = false)
    private Plazadisponible plazaDisponibleFechaDeRefencia1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "hotel")
    private Facturacliente facturacliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Regimenhospedaje> regimenhospedajeList;

    public Hotel() {
    }

    public Hotel(HotelPK hotelPK) {
        this.hotelPK = hotelPK;
    }

    public Hotel(int idHoteles, Date plazaDisponibleFechaDeRefencia) {
        this.hotelPK = new HotelPK(idHoteles, plazaDisponibleFechaDeRefencia);
    }

    public HotelPK getHotelPK() {
        return hotelPK;
    }

    public void setHotelPK(HotelPK hotelPK) {
        this.hotelPK = hotelPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Plazadisponible getPlazaDisponibleFechaDeRefencia1() {
        return plazaDisponibleFechaDeRefencia1;
    }

    public void setPlazaDisponibleFechaDeRefencia1(Plazadisponible plazaDisponibleFechaDeRefencia1) {
        this.plazaDisponibleFechaDeRefencia1 = plazaDisponibleFechaDeRefencia1;
    }

    public Facturacliente getFacturacliente() {
        return facturacliente;
    }

    public void setFacturacliente(Facturacliente facturacliente) {
        this.facturacliente = facturacliente;
    }

    @XmlTransient
    public List<Regimenhospedaje> getRegimenhospedajeList() {
        return regimenhospedajeList;
    }

    public void setRegimenhospedajeList(List<Regimenhospedaje> regimenhospedajeList) {
        this.regimenhospedajeList = regimenhospedajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelPK != null ? hotelPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.hotelPK == null && other.hotelPK != null) || (this.hotelPK != null && !this.hotelPK.equals(other.hotelPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.software.poo.entidades.Hotel[ hotelPK=" + hotelPK + " ]";
    }
    
}
