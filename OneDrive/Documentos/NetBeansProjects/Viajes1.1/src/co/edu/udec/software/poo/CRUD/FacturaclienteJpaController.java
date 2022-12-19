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
import co.edu.udec.software.poo.entidades.Cliente;
import co.edu.udec.software.poo.entidades.Facturacliente;
import co.edu.udec.software.poo.entidades.Hotel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class FacturaclienteJpaController implements Serializable {

    public FacturaclienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facturacliente facturacliente) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (facturacliente.getClienteList() == null) {
            facturacliente.setClienteList(new ArrayList<Cliente>());
        }
        List<String> illegalOrphanMessages = null;
        Cliente clienteOrphanCheck = facturacliente.getCliente();
        if (clienteOrphanCheck != null) {
            Facturacliente oldFacturaClienteidFacturaClienteOfCliente = clienteOrphanCheck.getFacturaClienteidFacturaCliente();
            if (oldFacturaClienteidFacturaClienteOfCliente != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cliente " + clienteOrphanCheck + " already has an item of type Facturacliente whose cliente column cannot be null. Please make another selection for the cliente field.");
            }
        }
        Hotel hotelOrphanCheck = facturacliente.getHotel();
        if (hotelOrphanCheck != null) {
            Facturacliente oldFacturaclienteOfHotel = hotelOrphanCheck.getFacturacliente();
            if (oldFacturaclienteOfHotel != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Hotel " + hotelOrphanCheck + " already has an item of type Facturacliente whose hotel column cannot be null. Please make another selection for the hotel field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = facturacliente.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdClientes());
                facturacliente.setCliente(cliente);
            }
            Hotel hotel = facturacliente.getHotel();
            if (hotel != null) {
                hotel = em.getReference(hotel.getClass(), hotel.getHotelPK());
                facturacliente.setHotel(hotel);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : facturacliente.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdClientes());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            facturacliente.setClienteList(attachedClienteList);
            em.persist(facturacliente);
            if (cliente != null) {
                cliente.setFacturaClienteidFacturaCliente(facturacliente);
                cliente = em.merge(cliente);
            }
            if (hotel != null) {
                hotel.setFacturacliente(facturacliente);
                hotel = em.merge(hotel);
            }
            for (Cliente clienteListCliente : facturacliente.getClienteList()) {
                Facturacliente oldFacturaClienteidFacturaClienteOfClienteListCliente = clienteListCliente.getFacturaClienteidFacturaCliente();
                clienteListCliente.setFacturaClienteidFacturaCliente(facturacliente);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldFacturaClienteidFacturaClienteOfClienteListCliente != null) {
                    oldFacturaClienteidFacturaClienteOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldFacturaClienteidFacturaClienteOfClienteListCliente = em.merge(oldFacturaClienteidFacturaClienteOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturacliente(facturacliente.getIdFacturaCliente()) != null) {
                throw new PreexistingEntityException("Facturacliente " + facturacliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facturacliente facturacliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturacliente persistentFacturacliente = em.find(Facturacliente.class, facturacliente.getIdFacturaCliente());
            Cliente clienteOld = persistentFacturacliente.getCliente();
            Cliente clienteNew = facturacliente.getCliente();
            Hotel hotelOld = persistentFacturacliente.getHotel();
            Hotel hotelNew = facturacliente.getHotel();
            List<Cliente> clienteListOld = persistentFacturacliente.getClienteList();
            List<Cliente> clienteListNew = facturacliente.getClienteList();
            List<String> illegalOrphanMessages = null;
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cliente " + clienteOld + " since its facturaClienteidFacturaCliente field is not nullable.");
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                Facturacliente oldFacturaClienteidFacturaClienteOfCliente = clienteNew.getFacturaClienteidFacturaCliente();
                if (oldFacturaClienteidFacturaClienteOfCliente != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cliente " + clienteNew + " already has an item of type Facturacliente whose cliente column cannot be null. Please make another selection for the cliente field.");
                }
            }
            if (hotelNew != null && !hotelNew.equals(hotelOld)) {
                Facturacliente oldFacturaclienteOfHotel = hotelNew.getFacturacliente();
                if (oldFacturaclienteOfHotel != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Hotel " + hotelNew + " already has an item of type Facturacliente whose hotel column cannot be null. Please make another selection for the hotel field.");
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its facturaClienteidFacturaCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdClientes());
                facturacliente.setCliente(clienteNew);
            }
            if (hotelNew != null) {
                hotelNew = em.getReference(hotelNew.getClass(), hotelNew.getHotelPK());
                facturacliente.setHotel(hotelNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdClientes());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            facturacliente.setClienteList(clienteListNew);
            facturacliente = em.merge(facturacliente);
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.setFacturaClienteidFacturaCliente(facturacliente);
                clienteNew = em.merge(clienteNew);
            }
            if (hotelOld != null && !hotelOld.equals(hotelNew)) {
                hotelOld.setFacturacliente(null);
                hotelOld = em.merge(hotelOld);
            }
            if (hotelNew != null && !hotelNew.equals(hotelOld)) {
                hotelNew.setFacturacliente(facturacliente);
                hotelNew = em.merge(hotelNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Facturacliente oldFacturaClienteidFacturaClienteOfClienteListNewCliente = clienteListNewCliente.getFacturaClienteidFacturaCliente();
                    clienteListNewCliente.setFacturaClienteidFacturaCliente(facturacliente);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldFacturaClienteidFacturaClienteOfClienteListNewCliente != null && !oldFacturaClienteidFacturaClienteOfClienteListNewCliente.equals(facturacliente)) {
                        oldFacturaClienteidFacturaClienteOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldFacturaClienteidFacturaClienteOfClienteListNewCliente = em.merge(oldFacturaClienteidFacturaClienteOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturacliente.getIdFacturaCliente();
                if (findFacturacliente(id) == null) {
                    throw new NonexistentEntityException("The facturacliente with id " + id + " no longer exists.");
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
            Facturacliente facturacliente;
            try {
                facturacliente = em.getReference(Facturacliente.class, id);
                facturacliente.getIdFacturaCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturacliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Cliente clienteOrphanCheck = facturacliente.getCliente();
            if (clienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facturacliente (" + facturacliente + ") cannot be destroyed since the Cliente " + clienteOrphanCheck + " in its cliente field has a non-nullable facturaClienteidFacturaCliente field.");
            }
            List<Cliente> clienteListOrphanCheck = facturacliente.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facturacliente (" + facturacliente + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable facturaClienteidFacturaCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Hotel hotel = facturacliente.getHotel();
            if (hotel != null) {
                hotel.setFacturacliente(null);
                hotel = em.merge(hotel);
            }
            em.remove(facturacliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facturacliente> findFacturaclienteEntities() {
        return findFacturaclienteEntities(true, -1, -1);
    }

    public List<Facturacliente> findFacturaclienteEntities(int maxResults, int firstResult) {
        return findFacturaclienteEntities(false, maxResults, firstResult);
    }

    private List<Facturacliente> findFacturaclienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturacliente.class));
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

    public Facturacliente findFacturacliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturacliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaclienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturacliente> rt = cq.from(Facturacliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
