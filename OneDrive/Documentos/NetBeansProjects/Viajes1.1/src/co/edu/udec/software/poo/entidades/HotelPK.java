/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.software.poo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JEIFER ALCALA
 */
@Embeddable
public class HotelPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idHoteles", nullable = false)
    private int idHoteles;
    @Basic(optional = false)
    @Column(name = "PlazaDisponible_FechaDeRefencia", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date plazaDisponibleFechaDeRefencia;

    public HotelPK() {
    }

    public HotelPK(int idHoteles, Date plazaDisponibleFechaDeRefencia) {
        this.idHoteles = idHoteles;
        this.plazaDisponibleFechaDeRefencia = plazaDisponibleFechaDeRefencia;
    }

    public int getIdHoteles() {
        return idHoteles;
    }

    public void setIdHoteles(int idHoteles) {
        this.idHoteles = idHoteles;
    }

    public Date getPlazaDisponibleFechaDeRefencia() {
        return plazaDisponibleFechaDeRefencia;
    }

    public void setPlazaDisponibleFechaDeRefencia(Date plazaDisponibleFechaDeRefencia) {
        this.plazaDisponibleFechaDeRefencia = plazaDisponibleFechaDeRefencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idHoteles;
        hash += (plazaDisponibleFechaDeRefencia != null ? plazaDisponibleFechaDeRefencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HotelPK)) {
            return false;
        }
        HotelPK other = (HotelPK) object;
        if (this.idHoteles != other.idHoteles) {
            return false;
        }
        if ((this.plazaDisponibleFechaDeRefencia == null && other.plazaDisponibleFechaDeRefencia != null) || (this.plazaDisponibleFechaDeRefencia != null && !this.plazaDisponibleFechaDeRefencia.equals(other.plazaDisponibleFechaDeRefencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.software.poo.entidades.HotelPK[ idHoteles=" + idHoteles + ", plazaDisponibleFechaDeRefencia=" + plazaDisponibleFechaDeRefencia + " ]";
    }
    
}
