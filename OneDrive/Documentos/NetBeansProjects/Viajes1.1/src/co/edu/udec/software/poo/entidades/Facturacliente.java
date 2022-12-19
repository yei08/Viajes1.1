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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JEIFER ALCALA
 */
@Entity
@Table(name = "facturacliente", catalog = "mydb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facturacliente.findAll", query = "SELECT f FROM Facturacliente f"),
    @NamedQuery(name = "Facturacliente.findByIdFacturaCliente", query = "SELECT f FROM Facturacliente f WHERE f.idFacturaCliente = :idFacturaCliente"),
    @NamedQuery(name = "Facturacliente.findByDescuento", query = "SELECT f FROM Facturacliente f WHERE f.descuento = :descuento"),
    @NamedQuery(name = "Facturacliente.findByCostoHospedajePension", query = "SELECT f FROM Facturacliente f WHERE f.costoHospedajePension = :costoHospedajePension"),
    @NamedQuery(name = "Facturacliente.findByCostoPension", query = "SELECT f FROM Facturacliente f WHERE f.costoPension = :costoPension"),
    @NamedQuery(name = "Facturacliente.findByImpuesto", query = "SELECT f FROM Facturacliente f WHERE f.impuesto = :impuesto")})
public class Facturacliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idFacturaCliente", nullable = false)
    private Integer idFacturaCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Descuento", precision = 12, scale = 0)
    private Float descuento;
    @Column(name = "CostoHospedajePension", precision = 22, scale = 0)
    private Double costoHospedajePension;
    @Column(name = "CostoPension", precision = 22, scale = 0)
    private Double costoPension;
    @Column(name = "Impuesto", precision = 12, scale = 0)
    private Float impuesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaClienteidFacturaCliente")
    private List<Cliente> clienteList;
    @JoinColumn(name = "idFacturaCliente", referencedColumnName = "idClientes", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "idFacturaCliente", referencedColumnName = "idHoteles", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Hotel hotel;

    public Facturacliente() {
    }

    public Facturacliente(Integer idFacturaCliente) {
        this.idFacturaCliente = idFacturaCliente;
    }

    public Integer getIdFacturaCliente() {
        return idFacturaCliente;
    }

    public void setIdFacturaCliente(Integer idFacturaCliente) {
        this.idFacturaCliente = idFacturaCliente;
    }

    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public Double getCostoHospedajePension() {
        return costoHospedajePension;
    }

    public void setCostoHospedajePension(Double costoHospedajePension) {
        this.costoHospedajePension = costoHospedajePension;
    }

    public Double getCostoPension() {
        return costoPension;
    }

    public void setCostoPension(Double costoPension) {
        this.costoPension = costoPension;
    }

    public Float getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Float impuesto) {
        this.impuesto = impuesto;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        hash += (idFacturaCliente != null ? idFacturaCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturacliente)) {
            return false;
        }
        Facturacliente other = (Facturacliente) object;
        if ((this.idFacturaCliente == null && other.idFacturaCliente != null) || (this.idFacturaCliente != null && !this.idFacturaCliente.equals(other.idFacturaCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udec.software.poo.entidades.Facturacliente[ idFacturaCliente=" + idFacturaCliente + " ]";
    }
    
}
