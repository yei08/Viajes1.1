/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.software.poo.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "plazadisponible", catalog = "mydb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plazadisponible.findAll", query = "SELECT p FROM Plazadisponible p"),
    @NamedQuery(name = "Plazadisponible.findByFechaDeRefencia", query = "SELECT p FROM Plazadisponible p WHERE p.fechaDeRefencia = :fechaDeRefencia"),
    @NamedQuery(name = "Plazadisponible.findByPlazaTotal", query = "SELECT p FROM Plazadisponible p WHERE p.plazaTotal = :plazaTotal"),
    @NamedQuery(name = "Plazadisponible.findByPlazaIndividual", query = "SELECT p FROM Plazadisponible p WHERE p.plazaIndividual = :plazaIndividual"),
    @NamedQuery(name = "Plazadisponible.findByPlazaDisponiblecol", query = "SELECT p FROM Plazadisponible p WHERE p.plazaDisponiblecol = :plazaDisponiblecol"),
    @NamedQuery(name = "Plazadisponible.findByPlazaMatrimonial", query = "SELECT p FROM Plazadisponible p WHERE p.plazaMatrimonial = :plazaMatrimonial"),
    @NamedQuery(name = "Plazadisponible.findByPlazaCuadruple", query = "SELECT p FROM Plazadisponible p WHERE p.plazaCuadruple = :plazaCuadruple")})
public class Plazadisponible implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FechaDeRefencia", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeRefencia;
    @Column(name = "PlazaTotal")
    private Integer plazaTotal;
    @Column(name = "PlazaIndividual")
    private Integer plazaIndividual;
    @Column(name = "PlazaDisponiblecol")
    private Integer plazaDisponiblecol;
    @Column(name = "PlazaMatrimonial")
    private Integer plazaMatrimonial;
    @Column(name = "PlazaCuadruple")
    private Integer plazaCuadruple;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plazaDisponibleFechaDeRefencia1")
    private List<Hotel> hotelList;

    public Plazadisponible() {
    }

    public Plazadisponible(Date fechaDeRefencia) {
        this.fechaDeRefencia = fechaDeRefencia;
    }

    public Date getFechaDeRefencia() {
        return fechaDeRefencia;
    }

    public void setFechaDeRefencia(Date fechaDeRefencia) {
        this.fechaDeRefencia = fechaDeRefencia;
    }

    public Integer getPlazaTotal() {
        return plazaTotal;
    }

    public void setPlazaTotal(Integer plazaTotal) {
        this.plazaTotal = plazaTotal;
    }

    public Integer getPlazaIndividual() {
        return plazaIndividual;
    }

    public void setPlazaIndividual(Integer plazaIndividual) {
        this.plazaIndividual = plazaIndividual;
    }

    public Integer getPlazaDisponiblecol() {
        return plazaDisponiblecol;
    }

    public void setPlazaDisponiblecol(Integer plazaDisponiblecol) {
        this.plazaDisponiblecol = plazaDisponiblecol;
    }

    public Integer getPlazaMatrimonial() {
        return plazaMatrimonial;
    }

    public void setPlazaMatrimonial(Integer plazaMatrimonial) {
        this.plazaMatrimonial = plazaMatrimonial;
    }

    public Integer getPlazaCuadruple() {
        return plazaCuadruple;
    }

    public void setPlazaCuadruple(Integer plazaCuadruple) {
        this.plazaCuadruple = plazaCuadruple;
    }

    @XmlTransient
    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechaDeRefencia != null ? fechaDeRefencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plazadisponible)) {
            return false;
        }
        Plazadisponible other = (Plazadisponible) object;
        if ((this.fechaDeRefencia == null && other.fechaDeRefencia != null) || (this.fechaDeRefencia != null && !this.fechaDeRefencia.equals(other.fechaDeRefencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.software.poo.entidades.Plazadisponible[ fechaDeRefencia=" + fechaDeRefencia + " ]";
    }
    
}
