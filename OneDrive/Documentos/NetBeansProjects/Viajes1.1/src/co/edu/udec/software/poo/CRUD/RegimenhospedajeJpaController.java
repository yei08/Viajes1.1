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
import co.edu.udec.software.poo.entidades.Hotel;
import co.edu.udec.software.poo.entidades.Cliente;
import co.edu.udec.software.poo.entidades.Regimenhospedaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class RegimenhospedajeJpaController implements Serializable {

    public RegimenhospedajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regimenhospedaje regimenhospedaje) throws PreexistingEntityException, Exception {
        if (regimenhospedaje.getClienteList() == null) {
            regimenhospedaje.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hotel hotel = regimenhospedaje.getHotel();
            if (hotel != null) {
                hotel = em.getReference(hotel.getClass(), hotel.getHotelPK());
                regimenhospedaje.setHotel(hotel);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : regimenhospedaje.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdClientes());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            regimenhospedaje.setClienteList(attachedClienteList);
            em.persist(regimenhospedaje);
            if (hotel != null) {
                hotel.getRegimenhospedajeList().add(regimenhospedaje);
                hotel = em.merge(hotel);
            }
            for (Cliente clienteListCliente : regimenhospedaje.getClienteList()) {
                Regimenhospedaje oldRegimenHospedajeTipoPensionOfClienteListCliente = clienteListCliente.getRegimenHospedajeTipoPension();
                clienteListCliente.setRegimenHospedajeTipoPension(regimenhospedaje);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldRegimenHospedajeTipoPensionOfClienteListCliente != null) {
                    oldRegimenHospedajeTipoPensionOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldRegimenHospedajeTipoPensionOfClienteListCliente = em.merge(oldRegimenHospedajeTipoPensionOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegimenhospedaje(regimenhospedaje.getTipoPension()) != null) {
                throw new PreexistingEntityException("Regimenhospedaje " + regimenhospedaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regimenhospedaje regimenhospedaje) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regimenhospedaje persistentRegimenhospedaje = em.find(Regimenhospedaje.class, regimenhospedaje.getTipoPension());
            Hotel hotelOld = persistentRegimenhospedaje.getHotel();
            Hotel hotelNew = regimenhospedaje.getHotel();
            List<Cliente> clienteListOld = persistentRegimenhospedaje.getClienteList();
            List<Cliente> clienteListNew = regimenhospedaje.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its regimenHospedajeTipoPension field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (hotelNew != null) {
                hotelNew = em.getReference(hotelNew.getClass(), hotelNew.getHotelPK());
                regimenhospedaje.setHotel(hotelNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdClientes());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            regimenhospedaje.setClienteList(clienteListNew);
            regimenhospedaje = em.merge(regimenhospedaje);
            if (hotelOld != null && !hotelOld.equals(hotelNew)) {
                hotelOld.getRegimenhospedajeList().remove(regimenhospedaje);
                hotelOld = em.merge(hotelOld);
            }
            if (hotelNew != null && !hotelNew.equals(hotelOld)) {
                hotelNew.getRegimenhospedajeList().add(regimenhospedaje);
                hotelNew = em.merge(hotelNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Regimenhospedaje oldRegimenHospedajeTipoPensionOfClienteListNewCliente = clienteListNewCliente.getRegimenHospedajeTipoPension();
                    clienteListNewCliente.setRegimenHospedajeTipoPension(regimenhospedaje);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldRegimenHospedajeTipoPensionOfClienteListNewCliente != null && !oldRegimenHospedajeTipoPensionOfClienteListNewCliente.equals(regimenhospedaje)) {
                        oldRegimenHospedajeTipoPensionOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldRegimenHospedajeTipoPensionOfClienteListNewCliente = em.merge(oldRegimenHospedajeTipoPensionOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = regimenhospedaje.getTipoPension();
                if (findRegimenhospedaje(id) == null) {
                    throw new NonexistentEntityException("The regimenhospedaje with id " + id + " no longer exists.");
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
            Regimenhospedaje regimenhospedaje;
            try {
                regimenhospedaje = em.getReference(Regimenhospedaje.class, id);
                regimenhospedaje.getTipoPension();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regimenhospedaje with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = regimenhospedaje.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Regimenhospedaje (" + regimenhospedaje + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable regimenHospedajeTipoPension field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Hotel hotel = regimenhospedaje.getHotel();
            if (hotel != null) {
                hotel.getRegimenhospedajeList().remove(regimenhospedaje);
                hotel = em.merge(hotel);
            }
            em.remove(regimenhospedaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regimenhospedaje> findRegimenhospedajeEntities() {
        return findRegimenhospedajeEntities(true, -1, -1);
    }

    public List<Regimenhospedaje> findRegimenhospedajeEntities(int maxResults, int firstResult) {
        return findRegimenhospedajeEntities(false, maxResults, firstResult);
    }

    private List<Regimenhospedaje> findRegimenhospedajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regimenhospedaje.class));
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

    public Regimenhospedaje findRegimenhospedaje(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regimenhospedaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegimenhospedajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regimenhospedaje> rt = cq.from(Regimenhospedaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
