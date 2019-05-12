package de.dis2011.data;

import de.dis2011.FormUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Purchase {

    private int id = -1;
    private int houseId;
    private int installment;
    private House house;
    private int rate;
    private Person fk_person;
    private Contract fk_contract;

    private String first_name;
    private String last_name;
    private String address;
    private String place;
    private String contractdate;


    private int getId() {
        return id;
    }
    private int getHouseId(){return houseId;}
    private int getInstallment(){return installment;}
    private int getRate(){return  rate;}

    private String getPlace() {
        return place;
    }
    private String getContractdate() {
        return contractdate;
    }

    public Person getFk_person() {
        return fk_person;
    }
    public Contract getFk_contract(){return fk_contract;}
    public House getHouse() {
        return house;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setHouseId(int houseId){this.houseId = houseId;}

    public void setPlace(String place) {
        this.place = place;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public void setContractdate(String contract) {
        this.contractdate = contract;
    }

    public void setFk_person(Person fk_person) {
        this.fk_person = fk_person;
    }
    public void setFk_contract(Contract fk_contract){this.fk_contract = fk_contract;}
    public void setHouse(House house) {this.house = house;}

    //hibernate
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void savePurchase() {

        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //TODO
        //                House hid = getFk_appartment();
        //                setAppartmentId(hid.getId());

        try {
            Purchase check_house = (Purchase) session.get(Purchase.class, getHouseId());
            if (check_house == null) {
                System.out.println("Haus mit der ID " + getHouseId() + " ist verfügbar.");
                Contract contract = new Contract();

                contract.setPerson(getFk_person());
                contract.setPurchase(this);
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
                System.out.println("Haus mit der ID " + getHouseId() + " hat bereits einen Vertrag.");
            }


        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

    }
}