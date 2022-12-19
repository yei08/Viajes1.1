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
import co.edu.udec.software.poo.entidades.Vuelo;
import java.util.ArrayList;
import java.util.List;
import co.edu.udec.software.poo.entidades.Cliente;
import co.edu.udec.software.poo.entidades.Sucursale;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class SucursaleJpaController implements Serializable {

    public SucursaleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursale sucursale) throws PreexistingEntityException, Exception {
        if (sucursale.getVueloList() == null) {
            sucursale.setVueloList(new ArrayList<Vuelo>());
        }
        if (sucursale.getClienteList() == null) {
            sucursale.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vuelo> attachedVueloList = new ArrayList<Vuelo>();
            for (Vuelo vueloListVueloToAttach : sucursale.getVueloList()) {
                vueloListVueloToAttach = em.getReference(vueloListVueloToAttach.getClass(), vueloListVueloToAttach.getNroVuelo());
                attachedVueloList.add(vueloListVueloToAttach);
            }
            sucursale.setVueloList(attachedVueloList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : sucursale.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdClientes());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            sucursale.setClienteList(attachedClienteList);
            em.persist(sucursale);
            for (Vuelo vueloListVuelo : sucursale.getVueloList()) {
                Sucursale oldSucursalesidSucursalOfVueloListVuelo = vueloListVuelo.getSucursalesidSucursal();
                vueloListVuelo.setSucursalesidSucursal(sucursale);
                vueloListVuelo = em.merge(vueloListVuelo);
                if (oldSucursalesidSucursalOfVueloListVuelo != null) {
                    oldSucursalesidSucursalOfVueloListVuelo.getVueloList().remove(vueloListVuelo);
                    oldSucursalesidSucursalOfVueloListVuelo = em.merge(oldSucursalesidSucursalOfVueloListVuelo);
                }
            }
            for (Cliente clienteListCliente : sucursale.getClienteList()) {
                Sucursale oldSucursalesidSucursalOfClienteListCliente = clienteListCliente.getSucursalesidSucursal();
                clienteListCliente.setSucursalesidSucursal(sucursale);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldSucursalesidSucursalOfClienteListCliente != null) {
                    oldSucursalesidSucursalOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldSucursalesidSucursalOfClienteListCliente = em.merge(oldSucursalesidSucursalOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSucursale(sucursale.getIdSucursal()) != null) {
                throw new PreexistingEntityException("Sucursale " + sucursale + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursale sucursale) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursale persistentSucursale = em.find(Sucursale.class, sucursale.getIdSucursal());
            List<Vuelo> vueloListOld = persistentSucursale.getVueloList();
            List<Vuelo> vueloListNew = sucursale.getVueloList();
            List<Cliente> clienteListOld = persistentSucursale.getClienteList();
            List<Cliente> clienteListNew = sucursale.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Vuelo vueloListOldVuelo : vueloListOld) {
                if (!vueloListNew.contains(vueloListOldVuelo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vuelo " + vueloListOldVuelo + " since its sucursalesidSucursal field is not nullable.");
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its sucursalesidSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Vuelo> attachedVueloListNew = new ArrayList<Vuelo>();
            for (Vuelo vueloListNewVueloToAttach : vueloListNew) {
                vueloListNewVueloToAttach = em.getReference(vueloListNewVueloToAttach.getClass(), vueloListNewVueloToAttach.getNroVuelo());
                attachedVueloListNew.add(vueloListNewVueloToAttach);
            }
            vueloListNew = attachedVueloListNew;
            sucursale.setVueloList(vueloListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdClientes());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            sucursale.setClienteList(clienteListNew);
            sucursale = em.merge(sucursale);
            for (Vuelo vueloListNewVuelo : vueloListNew) {
                if (!vueloListOld.contains(vueloListNewVuelo)) {
                    Sucursale oldSucursalesidSucursalOfVueloListNewVuelo = vueloListNewVuelo.getSucursalesidSucursal();
                    vueloListNewVuelo.setSucursalesidSucursal(sucursale);
                    vueloListNewVuelo = em.merge(vueloListNewVuelo);
                    if (oldSucursalesidSucursalOfVueloListNewVuelo != null && !oldSucursalesidSucursalOfVueloListNewVuelo.equals(sucursale)) {
                        oldSucursalesidSucursalOfVueloListNewVuelo.getVueloList().remove(vueloListNewVuelo);
                        oldSucursalesidSucursalOfVueloListNewVuelo = em.merge(oldSucursalesidSucursalOfVueloListNewVuelo);
                    }
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Sucursale oldSucursalesidSucursalOfClienteListNewCliente = clienteListNewCliente.getSucursalesidSucursal();
                    clienteListNewCliente.setSucursalesidSucursal(sucursale);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldSucursalesidSucursalOfClienteListNewCliente != null && !oldSucursalesidSucursalOfClienteListNewCliente.equals(sucursale)) {
                        oldSucursalesidSucursalOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldSucursalesidSucursalOfClienteListNewCliente = em.merge(oldSucursalesidSucursalOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sucursale.getIdSucursal();
                if (findSucursale(id) == null) {
                    throw new NonexistentEntityException("The sucursale with id " + id + " no longer exists.");
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
            Sucursale sucursale;
            try {
                sucursale = em.getReference(Sucursale.class, id);
                sucursale.getIdSucursal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursale with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Vuelo> vueloListOrphanCheck = sucursale.getVueloList();
            for (Vuelo vueloListOrphanCheckVuelo : vueloListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursale (" + sucursale + ") cannot be destroyed since the Vuelo " + vueloListOrphanCheckVuelo + " in its vueloList field has a non-nullable sucursalesidSucursal field.");
            }
            List<Cliente> clienteListOrphanCheck = sucursale.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursale (" + sucursale + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable sucursalesidSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sucursale);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursale> findSucursaleEntities() {
        return findSucursaleEntities(true, -1, -1);
    }

    public List<Sucursale> findSucursaleEntities(int maxResults, int firstResult) {
        return findSucursaleEntities(false, maxResults, firstResult);
    }

    private List<Sucursale> findSucursaleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursale.class));
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

    public Sucursale findSucursale(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursale.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursaleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursale> rt = cq.from(Sucursale.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
