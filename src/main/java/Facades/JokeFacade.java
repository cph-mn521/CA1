/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Entities.Joke;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Martin
 */
public class JokeFacade {

    private static JokeFacade instance;
    private static EntityManagerFactory emf;
    private static long count;

    public static JokeFacade Get(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new JokeFacade();
        }
        count = getCount();
        return instance;
    }

    /**
     *
     * @return
     */
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

///////////////////////////////////////////////////////////////////////////////
///////////////////////////  UPDATE METHODS ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////      
    public void update(Joke J) {
        EntityManager em = emf.createEntityManager();
        try {
            Joke DJ = em.find(Joke.class, J.getId());
            em.getTransaction().begin();
            DJ.setScore(J.getScore());
            DJ.setVotes(J.getVotes());
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public double vote(Joke j, int Score) {
        j.setScore(j.getScore() + Score);
        j.setVotes(j.getVotes() + 1);
        return j.getScore() / j.getVotes();
    }
///////////////////////////////////////////////////////////////////////////////
///////////////////////////  GET METHODS //////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////    

    /**
     *
     * @return
     */
    public List<Joke> getAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Joke.getAll").getResultList();
    }

    public Joke getById(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Joke.class, id);
    }

    public Joke getRandom() {
        EntityManager em = emf.createEntityManager();
        Random r = new Random();
        long id = (long) r.nextInt((int) count - 1) + 1;
        return em.find(Joke.class, id);
    }

    private static long getCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long count = (long) em.createQuery("SELECT COUNT(m) FROM Joke m").getSingleResult();
            return count;
        } finally {
            em.close();
        }
    }

    public long count() {
        return count;
    }
///////////////////////////////////////////////////////////////////////////////
///////////////////////////  CREATE METHODS ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////     

    public Joke createJoke(Joke J) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(J);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        count++;
        return J;
    }

    public void populate() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.persist(new Joke("knock kncok; whos there? boo; boo who?; Stop crying...", "MWUN", 12, 4));
            em.persist(new Joke("Hvorfor gik kyllingen over vejen?", "MWUN", 30, 10));
            em.persist(new Joke("Niels er dum, haha, det er jo sjovt.", "MWUN", 5, 1));
            em.persist(new Joke("reeee", "MWUN", 100, 1));
            em.getTransaction().commit();
        } finally {
            em.close();
            count = getCount();
        }
    }
}
