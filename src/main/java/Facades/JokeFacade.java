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

///////////////////////////////////////////////////////////////////////////////
///////////////////////////  UPDATE METHODS ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////      
    
    
    
///////////////////////////////////////////////////////////////////////////////
///////////////////////////  GET METHODS //////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////    
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Joke> getAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Joke.getAll").getResultList();
    }

    public Joke getB7Id(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Joke.class, id);
    }

    public Joke getRandom(long id) {
        EntityManager em = emf.createEntityManager();
        Random r = new Random();
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

    public long count(){
        return count;
    }
///////////////////////////////////////////////////////////////////////////////
///////////////////////////  CREATE METHODS ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////     

    public Joke createMovie(Joke J) {
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
        count ++;
        return J;
    }
    
    public void populate(){
                EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.persist(new Joke("knock kncok; whos there? boo; boo who?; Stop crying...","MWUN",0,0));
            em.persist(new Joke("Hvorfor gik kyllingen over vejen?","MWUN",0,0));
            em.persist(new Joke("Niels er dum, haha, det er jo sjovt.","MWUN",0,0));
            em.persist(new Joke("reeee","MWUN",0,0));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        Joke J = new Joke("knock kncok; whos there? boo; boo who?; Stop crying...","MWUN",0,0);
    }
}
