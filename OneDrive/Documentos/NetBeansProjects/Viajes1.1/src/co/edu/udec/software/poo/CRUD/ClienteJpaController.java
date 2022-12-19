/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.software.poo.CRUD;

import co.edu.udec.software.poo.CRUD.exceptions.IllegalOrphanException;
import co.edu.udec.software.poo.CRUD.exceptions.NonexistentEntityException;
import co.edu.udec.software.poo.entidades.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.udec.software.poo.entidades.Facturacliente;
import co.edu.udec.software.poo.entidades.Regimenhospedaje;
import co.edu.udec.software.poo.entidades.Sucursale;
import co.edu.udec.software.poo.entidades.Vuelo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturacliente facturaClienteidFacturaCliente = cliente.getFacturaClienteidFacturaCliente();
            if (facturaClienteidFacturaCliente != null) {
                facturaClienteidFacturaCliente = em.getReference(facturaClienteidFacturaCliente.getClass(), facturaClienteidFacturaCliente.getIdFacturaCliente());
                cliente.setFacturaClienteidFacturaCliente(facturaClienteidFacturaCliente);
            }
            Regimenhospedaje regimenHospedajeTipoPension = cliente.getRegimenHospedajeTipoPension();
            if (regimenHospedajeTipoPension != null) {
                regimenHospedajeTipoPension = em.getReference(regimenHospedajeTipoPension.getClass(), regimenHospedajeTipoPension.getTipoPension());
                cliente.setRegimenHospedajeTipoPension(regimenHospedajeTipoPension);
            }
            Sucursale sucursalesidSucursal = cliente.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal = em.getReference(sucursalesidSucursal.getClass(), sucursalesidSucursal.getIdSucursal());
                cliente.setSucursalesidSucursal(sucursalesidSucursal);
            }
            Vuelo vuelosNroVuelo = cliente.getVuelosNroVuelo();
            if (vuelosNroVuelo != null) {
                vuelosNroVuelo = em.getReference(vuelosNroVuelo.getClass(), vuelosNroVuelo.getNroVuelo());
                cliente.setVuelosNroVuelo(vuelosNroVuelo);
            }
            Facturacliente facturacliente = cliente.getFacturacliente();
            if (facturacliente != null) {
                facturacliente = em.getReference(facturacliente.getClass(), facturacliente.getIdFacturaCliente());
                cliente.setFacturacliente(facturacliente);
            }
            em.persist(cliente);
            if (facturaClienteidFacturaCliente != null) {
                facturaClienteidFacturaCliente.getClienteList().add(cliente);
                facturaClienteidFacturaCliente = em.merge(facturaClienteidFacturaCliente);
            }
            if (regimenHospedajeTipoPension != null) {
                regimenHospedajeTipoPension.getClienteList().add(cliente);
                regimenHospedajeTipoPension = em.merge(regimenHospedajeTipoPension);
            }
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getClienteList().add(cliente);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            if (vuelosNroVuelo != null) {
                vuelosNroVuelo.getClienteList().add(cliente);
                vuelosNroVuelo = em.merge(vuelosNroVuelo);
            }
            if (facturacliente != null) {
                Cliente oldClienteOfFacturacliente = facturacliente.getCliente();
                if (oldClienteOfFacturacliente != null) {
                    oldClienteOfFacturacliente.setFacturacliente(null);
                    oldClienteOfFacturacliente = em.merge(oldClienteOfFacturacliente);
                }
                facturacliente.setCliente(cliente);
                facturacliente = em.merge(facturacliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdClientes());
            Facturacliente facturaClienteidFacturaClienteOld = persistentCliente.getFacturaClienteidFacturaCliente();
            Facturacliente facturaClienteidFacturaClienteNew = cliente.getFacturaClienteidFacturaCliente();
            Regimenhospedaje regimenHospedajeTipoPensionOld = persistentCliente.getRegimenHospedajeTipoPension();
            Regimenhospedaje regimenHospedajeTipoPensionNew = cliente.getRegimenHospedajeTipoPension();
            Sucursale sucursalesidSucursalOld = persistentCliente.getSucursalesidSucursal();
            Sucursale sucursalesidSucursalNew = cliente.getSucursalesidSucursal();
            Vuelo vuelosNroVueloOld = persistentCliente.getVuelosNroVuelo();
            Vuelo vuelosNroVueloNew = cliente.getVuelosNroVuelo();
            Facturacliente facturaclienteOld = persistentCliente.getFacturacliente();
            Facturacliente facturaclienteNew = cliente.getFacturacliente();
            List<String> illegalOrphanMessages = null;
            if (facturaclienteOld != null && !facturaclienteOld.equals(facturaclienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Facturacliente " + facturaclienteOld + " since its cliente field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (facturaClienteidFacturaClienteNew != null) {
                facturaClienteidFacturaClienteNew = em.getReference(facturaClienteidFacturaClienteNew.getClass(), facturaClienteidFacturaClienteNew.getIdFacturaCliente());
                cliente.setFacturaClienteidFacturaCliente(facturaClienteidFacturaClienteNew);
            }
            if (regimenHospedajeTipoPensionNew != null) {
                regimenHospedajeTipoPensionNew = em.getReference(regimenHospedajeTipoPensionNew.getClass(), regimenHospedajeTipoPensionNew.getTipoPension());
                cliente.setRegimenHospedajeTipoPension(regimenHospedajeTipoPensionNew);
            }
            if (sucursalesidSucursalNew != null) {
                sucursalesidSucursalNew = em.getReference(sucursalesidSucursalNew.getClass(), sucursalesidSucursalNew.getIdSucursal());
                cliente.setSucursalesidSucursal(sucursalesidSucursalNew);
            }
            if (vuelosNroVueloNew != null) {
                vuelosNroVueloNew = em.getReference(vuelosNroVueloNew.getClass(), vuelosNroVueloNew.getNroVuelo());
                cliente.setVuelosNroVuelo(vuelosNroVueloNew);
            }
            if (facturaclienteNew != null) {
                facturaclienteNew = em.getReference(facturaclienteNew.getClass(), facturaclienteNew.getIdFacturaCliente());
                cliente.setFacturacliente(facturaclienteNew);
            }
            cliente = em.merge(cliente);
            if (facturaClienteidFacturaClienteOld != null && !facturaClienteidFacturaClienteOld.equals(facturaClienteidFacturaClienteNew)) {
                facturaClienteidFacturaClienteOld.getClienteList().remove(cliente);
                facturaClienteidFacturaClienteOld = em.merge(facturaClienteidFacturaClienteOld);
            }
            if (facturaClienteidFacturaClienteNew != null && !facturaClienteidFacturaClienteNew.equals(facturaClienteidFacturaClienteOld)) {
                facturaClienteidFacturaClienteNew.getClienteList().add(cliente);
                facturaClienteidFacturaClienteNew = em.merge(facturaClienteidFacturaClienteNew);
            }
            if (regimenHospedajeTipoPensionOld != null && !regimenHospedajeTipoPensionOld.equals(regimenHospedajeTipoPensionNew)) {
                regimenHospedajeTipoPensionOld.getClienteList().remove(cliente);
                regimenHospedajeTipoPensionOld = em.merge(regimenHospedajeTipoPensionOld);
            }
            if (regimenHospedajeTipoPensionNew != null && !regimenHospedajeTipoPensionNew.equals(regimenHospedajeTipoPensionOld)) {
                regimenHospedajeTipoPensionNew.getClienteList().add(cliente);
                regimenHospedajeTipoPensionNew = em.merge(regimenHospedajeTipoPensionNew);
            }
            if (sucursalesidSucursalOld != null && !sucursalesidSucursalOld.equals(sucursalesidSucursalNew)) {
                sucursalesidSucursalOld.getClienteList().remove(cliente);
                sucursalesidSucursalOld = em.merge(sucursalesidSucursalOld);
            }
            if (sucursalesidSucursalNew != null && !sucursalesidSucursalNew.equals(sucursalesidSucursalOld)) {
                sucursalesidSucursalNew.getClienteList().add(cliente);
                sucursalesidSucursalNew = em.merge(sucursalesidSucursalNew);
            }
            if (vuelosNroVueloOld != null && !vuelosNroVueloOld.equals(vuelosNroVueloNew)) {
                vuelosNroVueloOld.getClienteList().remove(cliente);
                vuelosNroVueloOld = em.merge(vuelosNroVueloOld);
            }
            if (vuelosNroVueloNew != null && !vuelosNroVueloNew.equals(vuelosNroVueloOld)) {
                vuelosNroVueloNew.getClienteList().add(cliente);
                vuelosNroVueloNew = em.merge(vuelosNroVueloNew);
            }
            if (facturaclienteNew != null && !facturaclienteNew.equals(facturaclienteOld)) {
                Cliente oldClienteOfFacturacliente = facturaclienteNew.getCliente();
                if (oldClienteOfFacturacliente != null) {
                    oldClienteOfFacturacliente.setFacturacliente(null);
                    oldClienteOfFacturacliente = em.merge(oldClienteOfFacturacliente);
                }
                facturaclienteNew.setCliente(cliente);
                facturaclienteNew = em.merge(facturaclienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdClientes();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdClientes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Facturacliente facturaclienteOrphanCheck = cliente.getFacturacliente();
            if (facturaclienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Facturacliente " + facturaclienteOrphanCheck + " in its facturacliente field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Facturacliente facturaClienteidFacturaCliente = cliente.getFacturaClienteidFacturaCliente();
            if (facturaClienteidFacturaCliente != null) {
                facturaClienteidFacturaCliente.getClienteList().remove(cliente);
                facturaClienteidFacturaCliente = em.merge(facturaClienteidFacturaCliente);
            }
            Regimenhospedaje regimenHospedajeTipoPension = cliente.getRegimenHospedajeTipoPension();
            if (regimenHospedajeTipoPension != null) {
                regimenHospedajeTipoPension.getClienteList().remove(cliente);
                regimenHospedajeTipoPension = em.merge(regimenHospedajeTipoPension);
            }
            Sucursale sucursalesidSucursal = cliente.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getClienteList().remove(cliente);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            Vuelo vuelosNroVuelo = cliente.getVuelosNroVuelo();
            if (vuelosNroVuelo != null) {
                vuelosNroVuelo.getClienteList().remove(cliente);
                vuelosNroVuelo = em.merge(vuelosNroVuelo);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
