package MapeoBD;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MapeoBD.Stock;

/**
 * JPA Controller for Stock entity
 * Handles CRUD operations for the Stock table in the database.
 */
public class StockJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public StockJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_Proyecto2_jar_1.0-SNAPSHOTPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Create (Insert)
    public void create(Stock stock) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(stock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Read (Find by ID)
    public Stock findStock(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stock.class, id);
        } finally {
            em.close();
        }
    }

    // Read (Find all)
    public List<Stock> findStockEntities() {
        return findStockEntities(true, -1, -1);
    }

    public List<Stock> findStockEntities(int maxResults, int firstResult) {
        return findStockEntities(false, maxResults, firstResult);
    }

    private List<Stock> findStockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stock.class));
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

    // Update (Edit)
    public void edit(Stock stock) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            stock = em.merge(stock);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStock(stock.getStockId()) == null) {
                throw new EntityNotFoundException("The stock with id " + stock.getStockId() + " no longer exists.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Delete
    public void destroy(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock stock;
            try {
                stock = em.getReference(Stock.class, id);
                stock.getStockId();
            } catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("The stock with id " + id + " no longer exists.");
            }
            em.remove(stock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Count (Total number of Stock entities)
    public int getStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stock> rt = cq.from(Stock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
