package de.dis2011.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Apartment {

    private int fk_estate_id;
    private int floor;
    private int rent;
    private int rooms;
    private int kitchen;
    private int balcony;

    //hibernate
    private static final SessionFactory sessionFactory;
    static { sessionFactory = new Configuration().configure().buildSessionFactory(); }

    public int getFk_estate_id() {
        return fk_estate_id;
    }

    public void setFk_estate_id(int fk_estate_id) {
        this.fk_estate_id = fk_estate_id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public double getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getKitchen() {
        return kitchen;
    }

    public void setKitchen(int kitchen) {
        this.kitchen = kitchen;
    }

    public int getBalcony() {
        return balcony;
    }

    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }

    public void saveHib() {
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        try {
            session.save(this);

            //commit transaction
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        session.close();


    }
    public static Apartment getByID(int id) {

        return null;
    }
}
