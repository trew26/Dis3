package de.dis2011.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Person {

    private int id = -1;

    private String first_name;
    private String last_name;
    private String address;

    public int getId() {return id;}
    public String getFirst_name(){return first_name;}
    public String getLast_name(){return last_name;}
    public String getAddress(){return  address;}


    public void setId(int id) {this.id = id; }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    //hibernate
    private static final SessionFactory sessionFactory;
    static { sessionFactory = new Configuration().configure().buildSessionFactory(); }

    public void savePerson() {
        int new_id = 1;

        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            //if id is initial, create new object in page
            if (getId() == -1) {
                //get all persons and find the highest id, increment it by 1. (i dont want to rebuild the entire table with an identity column right now...)
                List<Person> person_list = session.createQuery("SELECT a FROM Person a", Person.class).getResultList();

                if (person_list.size() != 0) {
                    for (Person p : person_list) {
                        new_id += 1;
                    }
                } else {
                    new_id = 1;
                }

                //set id on transient object in memory
                setId(new_id);

                //save transient object into db to make it persistent
                session.save(this);
                session.getTransaction().commit();
            }
            // if id is not initial, update object in page
            else {
                Person load_person = session.get(Person.class, id);
                if (load_person != null) {
                    //the new values are saved on the transient object, set them on the object loaded from db and save
                    load_person.setId(id);
                    load_person.setFirst_name(first_name);
                    load_person.setLast_name(last_name);
                    load_person.setAddress(address);

                    session.save(load_person);
                    session.getTransaction().commit();
                    session.close();
                }
            }


        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public static Person getByID(int id) {
        try {
            //get the session
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = (Person) session.get(Person.class, id);

            //commit transaction
            session.getTransaction().commit();
            session.close();
            return person;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
