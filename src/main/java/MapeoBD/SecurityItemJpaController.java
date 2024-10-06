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
import MapeoBD.SecurityItem;

public class SecurityItemJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public SecurityItemJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_Proyecto2_jar_1.0-SNAPSHOTPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Create (Insert)
    public void create(SecurityItem securityItem) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(securityItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Read (Find by ID)
    public SecurityItem findSecurityItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SecurityItem.class, id);
        } finally {
            em.close();
        }
    }

    // Read (Find All)
    public List<SecurityItem> findSecurityItemEntities() {
        return findSecurityItemEntities(true, -1, -1);
    }

    public List<SecurityItem> findSecurityItemEntities(int maxResults, int firstResult) {
        return findSecurityItemEntities(false, maxResults, firstResult);
    }

    private List<SecurityItem> findSecurityItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SecurityItem.class));
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

    // Update
    public void edit(SecurityItem securityItem) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            securityItem = em.merge(securityItem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSecurityItem(securityItem.getSecurityItemId()) == null) {
                throw new EntityNotFoundException("The securityItem with id " + securityItem.getSecurityItemId() + " no longer exists.");
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
            SecurityItem securityItem;
            try {
                securityItem = em.getReference(SecurityItem.class, id);
                securityItem.getSecurityItemId();
            } catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("The securityItem with id " + id + " no longer exists.");
            }
            em.remove(securityItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Count (total number of SecurityItem entities)
    public int getSecurityItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SecurityItem> rt = cq.from(SecurityItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
