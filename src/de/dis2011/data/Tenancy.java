package de.dis2011.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Tenancy {


    private int id = -1;
    private int appartmentId;
    private int personId;
    private int duration;
    private int additional_cost;
    private String startdate;
    private String contractdate;
    private String place;
    private Apartment appartment;
    private Person fk_person;
    private Contract fk_contract;

    private int getId(){return id;}
    private int getAppartmentId(){return appartmentId;}
    private int getPersonId(){return personId;}

    private int getDuration(){return duration;}
    private int getAdditional_cost(){return additional_cost;}

    private String getContractdate() {
        return contractdate;
    }
    private String getStartdate(){return startdate;}
    private String getPlace(){return place;}

    public Person getFk_person() {
        return fk_person;
    }
    public Contract getFk_contract(){return fk_contract;}
    public Apartment getAppartment(){ return appartment;}


    public void setId(int id) {
        this.id = id;
    }

    public void setPersonId(int personId){this.personId=personId;}

    public void setAppartmentId(int appartmentId) {
        this.appartmentId = appartmentId;
    }

    public  void setDuration(int duration){this.duration = duration;}

    public void setStartdate(String startdate){this.startdate = startdate;}

    public void setPlace(String place){this.place = place;}

    public void setContractdate(String contract) {
        this.contractdate = contract;
    }

    public void setAdditional_cost(int additional_cost){this.additional_cost = additional_cost;}

    public void setFk_person(Person fk_person) {
        this.fk_person = fk_person;
    }

    public void setFk_contract(Contract fk_contract){this.fk_contract = fk_contract;}

    public void setAppartment(Apartment appartment) { this.appartment = appartment; }

    //hibernate
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void saveTenancy() {

        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        try {
            Tenancy check_appartment = (Tenancy) session.get(Tenancy.class, getAppartmentId());
            if (check_appartment == null) {
                System.out.println("Appartment mit der ID " + getAppartmentId() + " ist verfügbar.");
                Contract contract = new Contract();

                contract.setPersonid(getPersonId());
                contract.setTenancy(this);
                contract.setPlace(getPlace());
                contract.setContractdate(getContractdate());

                Contract cid = contract.saveContract();
                setFk_contract(cid);

                setId(getFk_contract().getId());

                if (getFk_contract() != null) {
                    //save transient object into db to make it persistent
                    session.save(this);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    System.out.println("Es ist ein Fehler aufgetreten mit der Erstellung des Vertrages.");
                }


            } else {
                System.out.println("Appartment mit der ID " + getAppartment() + " hat bereits einen Vertrag.");
            }


        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }


    }
}

