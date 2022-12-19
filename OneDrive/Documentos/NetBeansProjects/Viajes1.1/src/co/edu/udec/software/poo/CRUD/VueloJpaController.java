/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udec.software.poo.CRUD;

import co.edu.udec.software.poo.CRUD.exceptions.IllegalOrphanException;
import co.edu.udec.software.poo.CRUD.exceptions.NonexistentEntityException;
import co.edu.udec.software.poo.CRUD.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.udec.software.poo.entidades.Sucursale;
import co.edu.udec.software.poo.entidades.Cliente;
import co.edu.udec.software.poo.entidades.Vuelo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class VueloJpaController implements Serializable {

    public VueloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vuelo vuelo) throws PreexistingEntityException, Exception {
        if (vuelo.getClienteList() == null) {
            vuelo.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursale sucursalesidSucursal = vuelo.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal = em.getReference(sucursalesidSucursal.getClass(), sucursalesidSucursal.getIdSucursal());
                vuelo.setSucursalesidSucursal(sucursalesidSucursal);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : vuelo.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdClientes());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            vuelo.setClienteList(attachedClienteList);
            em.persist(vuelo);
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getVueloList().add(vuelo);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            for (Cliente clienteListCliente : vuelo.getClienteList()) {
                Vuelo oldVuelosNroVueloOfClienteListCliente = clienteListCliente.getVuelosNroVuelo();
                clienteListCliente.setVuelosNroVuelo(vuelo);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldVuelosNroVueloOfClienteListCliente != null) {
                    oldVuelosNroVueloOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldVuelosNroVueloOfClienteListCliente = em.merge(oldVuelosNroVueloOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVuelo(vuelo.getNroVuelo()) != null) {
                throw new PreexistingEntityException("Vuelo " + vuelo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vuelo vuelo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelo persistentVuelo = em.find(Vuelo.class, vuelo.getNroVuelo());
            Sucursale sucursalesidSucursalOld = persistentVuelo.getSucursalesidSucursal();
            Sucursale sucursalesidSucursalNew = vuelo.getSucursalesidSucursal();
            List<Cliente> clienteListOld = persistentVuelo.getClienteList();
            List<Cliente> clienteListNew = vuelo.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its vuelosNroVuelo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sucursalesidSucursalNew != null) {
                sucursalesidSucursalNew = em.getReference(sucursalesidSucursalNew.getClass(), sucursalesidSucursalNew.getIdSucursal());
                vuelo.setSucursalesidSucursal(sucursalesidSucursalNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdClientes());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            vuelo.setClienteList(clienteListNew);
            vuelo = em.merge(vuelo);
            if (sucursalesidSucursalOld != null && !sucursalesidSucursalOld.equals(sucursalesidSucursalNew)) {
                sucursalesidSucursalOld.getVueloList().remove(vuelo);
                sucursalesidSucursalOld = em.merge(sucursalesidSucursalOld);
            }
            if (sucursalesidSucursalNew != null && !sucursalesidSucursalNew.equals(sucursalesidSucursalOld)) {
                sucursalesidSucursalNew.getVueloList().add(vuelo);
                sucursalesidSucursalNew = em.merge(sucursalesidSucursalNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Vuelo oldVuelosNroVueloOfClienteListNewCliente = clienteListNewCliente.getVuelosNroVuelo();
                    clienteListNewCliente.setVuelosNroVuelo(vuelo);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldVuelosNroVueloOfClienteListNewCliente != null && !oldVuelosNroVueloOfClienteListNewCliente.equals(vuelo)) {
                        oldVuelosNroVueloOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldVuelosNroVueloOfClienteListNewCliente = em.merge(oldVuelosNroVueloOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vuelo.getNroVuelo();
                if (findVuelo(id) == null) {
                    throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelo vuelo;
            try {
                vuelo = em.getReference(Vuelo.class, id);
                vuelo.getNroVuelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = vuelo.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable vuelosNroVuelo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sucursale sucursalesidSucursal = vuelo.getSucursalesidSucursal();
            if (sucursalesidSucursal != null) {
                sucursalesidSucursal.getVueloList().remove(vuelo);
                sucursalesidSucursal = em.merge(sucursalesidSucursal);
            }
            em.remove(vuelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vuelo> findVueloEntities() {
        return findVueloEntities(true, -1, -1);
    }

    public List<Vuelo> findVueloEntities(int maxResults, int firstResult) {
        return findVueloEntities(false, maxResults, firstResult);
    }

    private List<Vuelo> findVueloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vuelo.class));
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

    public Vuelo findVuelo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vuelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVueloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vuelo> rt = cq.from(Vuelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
