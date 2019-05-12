package de.dis2011.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Contract {
    private int id = -1;
    private Person person;
    private Tenancy tenancy;
    private Purchase purchase;
    private int personid = 1;
    private int duration;
    private int additional_cost;
    private int appartment;
    private int installment;
    private int rate;
    private int house;

    private String first_name;
    private String last_name;
    private String address;
    private String place;
    private String startdate;
    private String contractdate;

    public int getId() {return id;}
    public int getPersonid(){return personid;}
    private Person getPerson() {return person;}
    private Purchase getPurchase() {return purchase;}
    private Tenancy getTenancy(){return  tenancy;}
    private int getDuration() {return duration;}
    private int getAdditionalCost() {return additional_cost;}
    private int getAppartment() {return appartment;}
    private int getInstallment() {return installment;}
    private int getRate() {return rate;}
    private int getHouse() {return house;}
    private String getFirst_name() {return first_name;}
    private String getLast_name() {return last_name;}
    private String getAddress() {return address;}
    private String getPlace() {return place;}
    private String getStartdate() {return startdate;}
    private String getContractdate() {return contractdate;}

    public void setId(int id) {this.id = id; }
    public void setPersonid(int personid){this.personid=id;}
    public void setPerson(Person person) {
        this.person = person;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setAdditionalCost(int additional_cost) {
        this.additional_cost = additional_cost;
    }
    public void setAppartment(int appartment) {
        this.appartment = appartment;
    }
    public void setInstallment(int installment) {
        this.installment = installment;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public void setHouse(int house) {
        this.house = house;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public void setContractdate(String contract) {
        this.contractdate = contract;
    }
    public void setPurchase(Purchase purchase){ this.purchase = purchase;}
    public void setTenancy(Tenancy tenancy){this.tenancy = tenancy;}

    //hibernate
    private static final SessionFactory sessionFactory;
    static { sessionFactory = new Configuration().configure().buildSessionFactory(); }

    public Contract saveContract() {
        int new_id = 1;

        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            //if id is initial, create new object in page
            if (getId() == -1) {
                //get all persons and find the highest id, increment it by 1. (i dont want to rebuild the entire table with an identity column right now...)
                List<Contract> contract_list = session.createQuery("SELECT a FROM Contract a", Contract.class).getResultList();

                if (contract_list.size() != 0) {
                    for (Contract c : contract_list) {
                        new_id += 1;
                    }
                } else {
                    new_id = 1;
                }

                //set id on transient object in memory
                setId(new_id);

                //save transient object into db to make it persistent
                session.save(this);
            }
            //commit transaction
            session.getTransaction().commit();
            session.close();
            return this;

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        //commit transaction
        session.getTransaction().commit();
        session.close();
        return null;
    }


    //hibernate
    public void showContracts() {
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Contract> contract_list = session.createQuery("SELECT a FROM Contract a", Contract.class).getResultList();

        if (contract_list.size() != 0) {
            for (Contract c : contract_list) {
                System.out.println("Number:" + c.getId() + ", Date " + c.getContractdate() + ", Ort: " + c.getPlace() + ", Person: " + c.getPerson().getId());
            }
        } else {
            System.out.println("Es wurde keine Verträge gefunden");
        }

        System.out.println();
        //commit transaction
        session.getTransaction().commit();
        session.close();
    }
}


