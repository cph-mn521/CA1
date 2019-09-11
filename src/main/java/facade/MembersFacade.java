/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import Entities.Members;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nille
 */
public class MembersFacade {

    private static MembersFacade instance;
    private static EntityManagerFactory emf;

    private MembersFacade() {

    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MembersFacade getMembersFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MembersFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Members> getAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Members.getAll").getResultList();
    }
}
