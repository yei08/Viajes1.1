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
import co.edu.udec.software.poo.entidades.Plazadisponible;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class PlazadisponibleJpaController implements Serializable {

    public PlazadisponibleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plazadisponible plazadisponible) throws PreexistingEntityException, Exception {
        if (plazadisponible.getHotelList() == null) {
            plazadisponible.setHotelList(new ArrayList<Hotel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Hotel> attachedHotelList = new ArrayList<Hotel>();
            for (Hotel hotelListHotelToAttach : plazadisponible.getHotelList()) {
                hotelListHotelToAttach = em.getReference(hotelListHotelToAttach.getClass(), hotelListHotelToAttach.getHotelPK());
                attachedHotelList.add(hotelListHotelToAttach);
            }
            plazadisponible.setHotelList(attachedHotelList);
            em.persist(plazadisponible);
            for (Hotel hotelListHotel : plazadisponible.getHotelList()) {
                Plazadisponible oldPlazaDisponibleFechaDeRefencia1OfHotelListHotel = hotelListHotel.getPlazaDisponibleFechaDeRefencia1();
                hotelListHotel.setPlazaDisponibleFechaDeRefencia1(plazadisponible);
                hotelListHotel = em.merge(hotelListHotel);
                if (oldPlazaDisponibleFechaDeRefencia1OfHotelListHotel != null) {
                    oldPlazaDisponibleFechaDeRefencia1OfHotelListHotel.getHotelList().remove(hotelListHotel);
                    oldPlazaDisponibleFechaDeRefencia1OfHotelListHotel = em.merge(oldPlazaDisponibleFechaDeRefencia1OfHotelListHotel);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlazadisponible(plazadisponible.getFechaDeRefencia()) != null) {
                throw new PreexistingEntityException("Plazadisponible " + plazadisponible + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plazadisponible plazadisponible) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazadisponible persistentPlazadisponible = em.find(Plazadisponible.class, plazadisponible.getFechaDeRefencia());
            List<Hotel> hotelListOld = persistentPlazadisponible.getHotelList();
            List<Hotel> hotelListNew = plazadisponible.getHotelList();
            List<String> illegalOrphanMessages = null;
            for (Hotel hotelListOldHotel : hotelListOld) {
                if (!hotelListNew.contains(hotelListOldHotel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hotel " + hotelListOldHotel + " since its plazaDisponibleFechaDeRefencia1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Hotel> attachedHotelListNew = new ArrayList<Hotel>();
            for (Hotel hotelListNewHotelToAttach : hotelListNew) {
                hotelListNewHotelToAttach = em.getReference(hotelListNewHotelToAttach.getClass(), hotelListNewHotelToAttach.getHotelPK());
                attachedHotelListNew.add(hotelListNewHotelToAttach);
            }
            hotelListNew = attachedHotelListNew;
            plazadisponible.setHotelList(hotelListNew);
            plazadisponible = em.merge(plazadisponible);
            for (Hotel hotelListNewHotel : hotelListNew) {
                if (!hotelListOld.contains(hotelListNewHotel)) {
                    Plazadisponible oldPlazaDisponibleFechaDeRefencia1OfHotelListNewHotel = hotelListNewHotel.getPlazaDisponibleFechaDeRefencia1();
                    hotelListNewHotel.setPlazaDisponibleFechaDeRefencia1(plazadisponible);
                    hotelListNewHotel = em.merge(hotelListNewHotel);
                    if (oldPlazaDisponibleFechaDeRefencia1OfHotelListNewHotel != null && !oldPlazaDisponibleFechaDeRefencia1OfHotelListNewHotel.equals(plazadisponible)) {
                        oldPlazaDisponibleFechaDeRefencia1OfHotelListNewHotel.getHotelList().remove(hotelListNewHotel);
                        oldPlazaDisponibleFechaDeRefencia1OfHotelListNewHotel = em.merge(oldPlazaDisponibleFechaDeRefencia1OfHotelListNewHotel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Date id = plazadisponible.getFechaDeRefencia();
                if (findPlazadisponible(id) == null) {
                    throw new NonexistentEntityException("The plazadisponible with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Date id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazadisponible plazadisponible;
            try {
                plazadisponible = em.getReference(Plazadisponible.class, id);
                plazadisponible.getFechaDeRefencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plazadisponible with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Hotel> hotelListOrphanCheck = plazadisponible.getHotelList();
            for (Hotel hotelListOrphanCheckHotel : hotelListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Plazadisponible (" + plazadisponible + ") cannot be destroyed since the Hotel " + hotelListOrphanCheckHotel + " in its hotelList field has a non-nullable plazaDisponibleFechaDeRefencia1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(plazadisponible);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plazadisponible> findPlazadisponibleEntities() {
        return findPlazadisponibleEntities(true, -1, -1);
    }

    public List<Plazadisponible> findPlazadisponibleEntities(int maxResults, int firstResult) {
        return findPlazadisponibleEntities(false, maxResults, firstResult);
    }

    private List<Plazadisponible> findPlazadisponibleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plazadisponible.class));
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

    public Plazadisponible findPlazadisponible(Date id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plazadisponible.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlazadisponibleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plazadisponible> rt = cq.from(Plazadisponible.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
