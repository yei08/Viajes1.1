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
import co.edu.udec.software.poo.entidades.Plazadisponible;
import co.edu.udec.software.poo.entidades.Facturacliente;
import co.edu.udec.software.poo.entidades.Hotel;
import co.edu.udec.software.poo.entidades.HotelPK;
import co.edu.udec.software.poo.entidades.Regimenhospedaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JEIFER ALCALA
 */
public class HotelJpaController implements Serializable {

    public HotelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hotel hotel) throws PreexistingEntityException, Exception {
        if (hotel.getHotelPK() == null) {
            hotel.setHotelPK(new HotelPK());
        }
        if (hotel.getRegimenhospedajeList() == null) {
            hotel.setRegimenhospedajeList(new ArrayList<Regimenhospedaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazadisponible plazaDisponibleFechaDeRefencia1 = hotel.getPlazaDisponibleFechaDeRefencia1();
            if (plazaDisponibleFechaDeRefencia1 != null) {
                plazaDisponibleFechaDeRefencia1 = em.getReference(plazaDisponibleFechaDeRefencia1.getClass(), plazaDisponibleFechaDeRefencia1.getFechaDeRefencia());
                hotel.setPlazaDisponibleFechaDeRefencia1(plazaDisponibleFechaDeRefencia1);
            }
            Facturacliente facturacliente = hotel.getFacturacliente();
            if (facturacliente != null) {
                facturacliente = em.getReference(facturacliente.getClass(), facturacliente.getIdFacturaCliente());
                hotel.setFacturacliente(facturacliente);
            }
            List<Regimenhospedaje> attachedRegimenhospedajeList = new ArrayList<Regimenhospedaje>();
            for (Regimenhospedaje regimenhospedajeListRegimenhospedajeToAttach : hotel.getRegimenhospedajeList()) {
                regimenhospedajeListRegimenhospedajeToAttach = em.getReference(regimenhospedajeListRegimenhospedajeToAttach.getClass(), regimenhospedajeListRegimenhospedajeToAttach.getTipoPension());
                attachedRegimenhospedajeList.add(regimenhospedajeListRegimenhospedajeToAttach);
            }
            hotel.setRegimenhospedajeList(attachedRegimenhospedajeList);
            em.persist(hotel);
            if (plazaDisponibleFechaDeRefencia1 != null) {
                plazaDisponibleFechaDeRefencia1.getHotelList().add(hotel);
                plazaDisponibleFechaDeRefencia1 = em.merge(plazaDisponibleFechaDeRefencia1);
            }
            if (facturacliente != null) {
                Hotel oldHotelOfFacturacliente = facturacliente.getHotel();
                if (oldHotelOfFacturacliente != null) {
                    oldHotelOfFacturacliente.setFacturacliente(null);
                    oldHotelOfFacturacliente = em.merge(oldHotelOfFacturacliente);
                }
                facturacliente.setHotel(hotel);
                facturacliente = em.merge(facturacliente);
            }
            for (Regimenhospedaje regimenhospedajeListRegimenhospedaje : hotel.getRegimenhospedajeList()) {
                Hotel oldHotelOfRegimenhospedajeListRegimenhospedaje = regimenhospedajeListRegimenhospedaje.getHotel();
                regimenhospedajeListRegimenhospedaje.setHotel(hotel);
                regimenhospedajeListRegimenhospedaje = em.merge(regimenhospedajeListRegimenhospedaje);
                if (oldHotelOfRegimenhospedajeListRegimenhospedaje != null) {
                    oldHotelOfRegimenhospedajeListRegimenhospedaje.getRegimenhospedajeList().remove(regimenhospedajeListRegimenhospedaje);
                    oldHotelOfRegimenhospedajeListRegimenhospedaje = em.merge(oldHotelOfRegimenhospedajeListRegimenhospedaje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHotel(hotel.getHotelPK()) != null) {
                throw new PreexistingEntityException("Hotel " + hotel + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hotel hotel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hotel persistentHotel = em.find(Hotel.class, hotel.getHotelPK());
            Plazadisponible plazaDisponibleFechaDeRefencia1Old = persistentHotel.getPlazaDisponibleFechaDeRefencia1();
            Plazadisponible plazaDisponibleFechaDeRefencia1New = hotel.getPlazaDisponibleFechaDeRefencia1();
            Facturacliente facturaclienteOld = persistentHotel.getFacturacliente();
            Facturacliente facturaclienteNew = hotel.getFacturacliente();
            List<Regimenhospedaje> regimenhospedajeListOld = persistentHotel.getRegimenhospedajeList();
            List<Regimenhospedaje> regimenhospedajeListNew = hotel.getRegimenhospedajeList();
            List<String> illegalOrphanMessages = null;
            if (facturaclienteOld != null && !facturaclienteOld.equals(facturaclienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Facturacliente " + facturaclienteOld + " since its hotel field is not nullable.");
            }
            for (Regimenhospedaje regimenhospedajeListOldRegimenhospedaje : regimenhospedajeListOld) {
                if (!regimenhospedajeListNew.contains(regimenhospedajeListOldRegimenhospedaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Regimenhospedaje " + regimenhospedajeListOldRegimenhospedaje + " since its hotel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (plazaDisponibleFechaDeRefencia1New != null) {
                plazaDisponibleFechaDeRefencia1New = em.getReference(plazaDisponibleFechaDeRefencia1New.getClass(), plazaDisponibleFechaDeRefencia1New.getFechaDeRefencia());
                hotel.setPlazaDisponibleFechaDeRefencia1(plazaDisponibleFechaDeRefencia1New);
            }
            if (facturaclienteNew != null) {
                facturaclienteNew = em.getReference(facturaclienteNew.getClass(), facturaclienteNew.getIdFacturaCliente());
                hotel.setFacturacliente(facturaclienteNew);
            }
            List<Regimenhospedaje> attachedRegimenhospedajeListNew = new ArrayList<Regimenhospedaje>();
            for (Regimenhospedaje regimenhospedajeListNewRegimenhospedajeToAttach : regimenhospedajeListNew) {
                regimenhospedajeListNewRegimenhospedajeToAttach = em.getReference(regimenhospedajeListNewRegimenhospedajeToAttach.getClass(), regimenhospedajeListNewRegimenhospedajeToAttach.getTipoPension());
                attachedRegimenhospedajeListNew.add(regimenhospedajeListNewRegimenhospedajeToAttach);
            }
            regimenhospedajeListNew = attachedRegimenhospedajeListNew;
            hotel.setRegimenhospedajeList(regimenhospedajeListNew);
            hotel = em.merge(hotel);
            if (plazaDisponibleFechaDeRefencia1Old != null && !plazaDisponibleFechaDeRefencia1Old.equals(plazaDisponibleFechaDeRefencia1New)) {
                plazaDisponibleFechaDeRefencia1Old.getHotelList().remove(hotel);
                plazaDisponibleFechaDeRefencia1Old = em.merge(plazaDisponibleFechaDeRefencia1Old);
            }
            if (plazaDisponibleFechaDeRefencia1New != null && !plazaDisponibleFechaDeRefencia1New.equals(plazaDisponibleFechaDeRefencia1Old)) {
                plazaDisponibleFechaDeRefencia1New.getHotelList().add(hotel);
                plazaDisponibleFechaDeRefencia1New = em.merge(plazaDisponibleFechaDeRefencia1New);
            }
            if (facturaclienteNew != null && !facturaclienteNew.equals(facturaclienteOld)) {
                Hotel oldHotelOfFacturacliente = facturaclienteNew.getHotel();
                if (oldHotelOfFacturacliente != null) {
                    oldHotelOfFacturacliente.setFacturacliente(null);
                    oldHotelOfFacturacliente = em.merge(oldHotelOfFacturacliente);
                }
                facturaclienteNew.setHotel(hotel);
                facturaclienteNew = em.merge(facturaclienteNew);
            }
            for (Regimenhospedaje regimenhospedajeListNewRegimenhospedaje : regimenhospedajeListNew) {
                if (!regimenhospedajeListOld.contains(regimenhospedajeListNewRegimenhospedaje)) {
                    Hotel oldHotelOfRegimenhospedajeListNewRegimenhospedaje = regimenhospedajeListNewRegimenhospedaje.getHotel();
                    regimenhospedajeListNewRegimenhospedaje.setHotel(hotel);
                    regimenhospedajeListNewRegimenhospedaje = em.merge(regimenhospedajeListNewRegimenhospedaje);
                    if (oldHotelOfRegimenhospedajeListNewRegimenhospedaje != null && !oldHotelOfRegimenhospedajeListNewRegimenhospedaje.equals(hotel)) {
                        oldHotelOfRegimenhospedajeListNewRegimenhospedaje.getRegimenhospedajeList().remove(regimenhospedajeListNewRegimenhospedaje);
                        oldHotelOfRegimenhospedajeListNewRegimenhospedaje = em.merge(oldHotelOfRegimenhospedajeListNewRegimenhospedaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                HotelPK id = hotel.getHotelPK();
                if (findHotel(id) == null) {
                    throw new NonexistentEntityException("The hotel with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HotelPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hotel hotel;
            try {
                hotel = em.getReference(Hotel.class, id);
                hotel.getHotelPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hotel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Facturacliente facturaclienteOrphanCheck = hotel.getFacturacliente();
            if (facturaclienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hotel (" + hotel + ") cannot be destroyed since the Facturacliente " + facturaclienteOrphanCheck + " in its facturacliente field has a non-nullable hotel field.");
            }
            List<Regimenhospedaje> regimenhospedajeListOrphanCheck = hotel.getRegimenhospedajeList();
            for (Regimenhospedaje regimenhospedajeListOrphanCheckRegimenhospedaje : regimenhospedajeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hotel (" + hotel + ") cannot be destroyed since the Regimenhospedaje " + regimenhospedajeListOrphanCheckRegimenhospedaje + " in its regimenhospedajeList field has a non-nullable hotel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Plazadisponible plazaDisponibleFechaDeRefencia1 = hotel.getPlazaDisponibleFechaDeRefencia1();
            if (plazaDisponibleFechaDeRefencia1 != null) {
                plazaDisponibleFechaDeRefencia1.getHotelList().remove(hotel);
                plazaDisponibleFechaDeRefencia1 = em.merge(plazaDisponibleFechaDeRefencia1);
            }
            em.remove(hotel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hotel> findHotelEntities() {
        return findHotelEntities(true, -1, -1);
    }

    public List<Hotel> findHotelEntities(int maxResults, int firstResult) {
        return findHotelEntities(false, maxResults, firstResult);
    }

    private List<Hotel> findHotelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hotel.class));
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

    public Hotel findHotel(HotelPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hotel.class, id);
        } finally {
            em.close();
        }
    }

    public int getHotelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hotel> rt = cq.from(Hotel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
