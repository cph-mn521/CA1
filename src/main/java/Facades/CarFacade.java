/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Entities.Car;
import dto.CarDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author jonab
 */
public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CarFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CarFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void addCar(Car car) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List getAllCars() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery("Select c from Car c", Car.class);
            List<CarDTO> cars = new ArrayList();
            for (int i = 0; i < query.getResultList().size(); i++) {
                cars.add(new CarDTO(query.getResultList().get(i)));
            }
            return cars;
        } finally {
            em.close();
        }
    }

    

    public static void main(String[] args) {
        CarFacade cf = getFacade(Persistence.createEntityManagerFactory("pu"));
        cf.addCar(new Car(2002, "Ford", "Mondeo", 250000, "Lars", "BX29038"));
    }
}
