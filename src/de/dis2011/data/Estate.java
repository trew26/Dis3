package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.dis2011.data.Makler;

public class Estate {

    private int id = -1;
    private int zip;
    private int number;
    private int floor;
    private int rent;
    private int rooms;
    private int balcony;
    private int kitchen;
    private int floors;
    private int price;
    private int garden;

    private String city;
    private String street;
    private String area;
    private Makler fk_agent;

    public int getId() {
        return id;
    }
//estate
    public int getzip() {
        return zip;
    }

    public int getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getArea() {
        return area;
    }

    public Makler getFk_agent() {
        return fk_agent;
    }
//apartment
    public int getFloor() {
        return floor;
    }

    public int getRent() {
        return rent;
    }

    public int getRooms() {
        return rooms;
    }

    public int getBalcony() {
        return balcony;
    }

    public int getKitchen() {
        return kitchen;
    }
//house
    public int getFloors() {
        return floors;
    }

    public int getPrice() { return price; }

    public int getGarden() { return garden; }
//house
    public void setFloors(int floors) {
        this.floors = floors;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setGarden(int garden) {
        this.garden = garden;
    }
//apartment
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }

    public void setKitchen(int kitchen) {
        this.kitchen = kitchen;
    }
//estate
    public void setId(int id) {
        this.id = id;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setFk_agent(Makler fk_agent) {
        this.fk_agent = fk_agent;
    }

    //hibernate
    private static final SessionFactory sessionFactory;
    static { sessionFactory = new Configuration().configure().buildSessionFactory(); }

    public Estate testHibEstateGet(int id) {
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // the querry
        Estate estate = (Estate) session.get(Estate.class, id);

        //commit transaction
        session.getTransaction().commit();
        session.close();

        return estate;
    }

    public static void showAllEstatesHib() {
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Estate> estate_list = session.createQuery("SELECT a FROM Estate a", Estate.class).getResultList();

        if (estate_list.size() != 0) {
            for (Estate e : estate_list) {
                System.out.println("ID:" + e.getId() + ", City: " + e.getCity() + ", Makler ID: " + e.getFk_agent().getId() + ", Makler Login: " + e.getFk_agent().getLogin());
            }
        } else {
            System.out.println("Es wurde keine Estates gefunden");
        }

        System.out.println();
        //commit transaction
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteEstateHib(int id) {
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            //Delete a persistent object
            Estate delete_estate = session.get(Estate.class, id);
            if (delete_estate != null) {
                session.delete(delete_estate);
                System.out.println("Estate mit ID " + id + " wurde gelöscht.");
            } else {
                System.out.println("Es konnte keine Estate zur ID " + id + " gefunden werden.");
            }

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

    public void saveHib() {
        //fields
        int new_id = 1;
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            //if id is initial, create new object in page
            if (getId() == -1) {
                //get all estates and find the highest id, increment it by 1. (i dont want to rebuild the entire table with an identity column right now...)
                List<Estate> estate_list = session.createQuery("SELECT a FROM Estate a", Estate.class).getResultList();

                if (estate_list.size() != 0) {
                    for (Estate e : estate_list) {
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
            // if id is not initial, update object in page
            else {
                Estate load_estate = session.get(Estate.class, id);
                if (load_estate != null) {
                    //the new values are saved on the transient object, set them on the object loaded from db and save
                    load_estate.setId(id);
                    load_estate.setCity(getCity());
                    load_estate.setZip(getzip());
                    load_estate.setStreet(getStreet());
                    load_estate.setNumber(getNumber());
                    load_estate.setArea(getArea());
                    load_estate.setFk_agent(getFk_agent());

                    session.save(load_estate);
                }
            }
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

    public void saveApartmentHib() {
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //create transient apartment object
        Apartment apartment = new Apartment();
        apartment.setFk_estate_id(getId());
        apartment.setBalcony(getBalcony());
        apartment.setFloor(getFloor());
        apartment.setKitchen(getKitchen());
        apartment.setRent(getRent());
        apartment.setRooms(getRooms());

        //save the apartment
        //apartment.saveHib();


        try {
            session.save(apartment);

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

    public void saveHouseHib() {
        //get the session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //create transient apartment object
        House house = new House();
        house.setFk_estate_id(getId());
        house.setFloors(getFloors());
        house.setGarden(getGarden());
        house.setPrice(getPrice());

        try {
            session.save(house);

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

    //db2
    public void save() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (getId() == -1) {
                // Achtung, hier wird noch ein Parameter mitgegeben,
                // damit spC$ter generierte IDs zurC<ckgeliefert werden!
                String insertSQL = "INSERT INTO estate(id, zip, number, city, street, area, fk_agent_login) VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement insert_pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // get the estate id's to find the next highest that is available
                String get_id_SQL = "SELECT id from estate";
                PreparedStatement id_pstmt = con.prepareStatement(get_id_SQL);
                ResultSet id_rs = id_pstmt.executeQuery();

                if (id_rs.next()){
                    while (id_rs.next()) {
                        setId(id_rs.getInt("id"));
                    }
                    setId(getId()+1);
                } else {
                    // keine estate in Tabelle
                    setId(0);
                }

                // Setze Anfrageparameter und fC<hre Anfrage aus
                insert_pstmt.setInt(1, getId());
                insert_pstmt.setInt(2, getzip());
                insert_pstmt.setInt(3, getNumber());

                insert_pstmt.setString(4, getCity());
                insert_pstmt.setString(5, getStreet());
                insert_pstmt.setString(6, getArea());
                insert_pstmt.setString(7, getFk_agent().getLogin());

                insert_pstmt.executeUpdate();
                insert_pstmt.close();
            }
            else {
                // wenn ID nicht initial ist wird die Safe methode an einem Objekt aufgerufen, d.h. es wird ein Update durchgeführt
                // prüfe ob makler Estate bearbeiten darf
                String testSQL = "SELECT * FROM estate WHERE id = ?";
                PreparedStatement test_pstmt = con.prepareStatement(testSQL);

                test_pstmt.setInt(1, getId());

                ResultSet test_rs = test_pstmt.executeQuery();
                if(test_rs.next()) {
                    if(!(test_rs.getString("fk_agent_login").equals(getFk_agent()))) {
                        System.out.println("Makler " + getFk_agent() + " ist nicht berechtigt die Estate mit ID " + getId() +
                                " zu bearbeiten, da diese von Makler " + test_rs.getString("fk_agent_login") + " verwaltet wird.");
                        test_pstmt.close();
                        test_rs.close();
                    }
                    else {
                        // wenn makler bearbeiten darf
                        String updateSQL = "Update estate SET zip = ?, number = ?, city = ?, street = ?, area = ?, fk_agent_login = ? WHERE id = ?";
                        PreparedStatement update_pstmt = con.prepareStatement(updateSQL);

                        update_pstmt.setInt(1, getzip());
                        update_pstmt.setInt(2, getNumber());
                        update_pstmt.setString(3, getCity());
                        update_pstmt.setString(4, getStreet());
                        update_pstmt.setString(5, getArea());
                        update_pstmt.setString(6, getFk_agent().getLogin());
                        update_pstmt.setInt(7, getId());

                        update_pstmt.executeUpdate();
                        update_pstmt.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveApartment(){
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {   String insertSQL = "INSERT INTO apartment(fk_estate_id, floor, rent, rooms, kitchen, balcony) VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement insert_pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Setze Anfrageparameter und fC<hre Anfrage aus
                insert_pstmt.setInt(1, getId());
                insert_pstmt.setInt(2, getFloor());
                insert_pstmt.setInt(3, getRent());
                insert_pstmt.setInt(4, getRooms());
                insert_pstmt.setInt(5, getKitchen());
                insert_pstmt.setInt(6, getBalcony());

                insert_pstmt.executeUpdate();
                insert_pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveHouse(){
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            String insertSQL = "INSERT INTO house(fk_estate_id, floors, price, garden) VALUES (?, ?, ?, ?)";

            PreparedStatement insert_pstmt = con.prepareStatement(insertSQL,
                    Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            insert_pstmt.setInt(1, getId());
            insert_pstmt.setInt(2, getFloors());
            insert_pstmt.setInt(3, getPrice());
            insert_pstmt.setInt(4, getGarden());

            insert_pstmt.executeUpdate();
            insert_pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateApartment(String markler_login){
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();
        try{
            // wenn ID nicht initial ist wird die Safe methode an einem Objekt aufgerufen, d.h. es wird ein Update durchgeführt
            // prüfe ob makler Estate bearbeiten darf
            String testSQL = "SELECT * FROM estate WHERE id = ? AND fk_agent_login = ?";
            PreparedStatement test_pstmt = con.prepareStatement(testSQL);

            test_pstmt.setInt(1, getId());
            test_pstmt.setString(2, markler_login);

            ResultSet test_rs = test_pstmt.executeQuery();
            if(test_rs.next()) {
                // wenn makler bearbeiten darf
                String updateSQL = "Update apartment SET floor = ?, rent = ?, rooms = ?, kitchen = ?, balcony = ? WHERE fk_estate_id = ?";
                PreparedStatement update_pstmt = con.prepareStatement(updateSQL);

                update_pstmt.setInt(1, getFloor());
                update_pstmt.setInt(2, getRent());
                update_pstmt.setInt(3, getRooms());
                update_pstmt.setInt(4, getKitchen());
                update_pstmt.setInt(5, getBalcony());
                update_pstmt.setInt(6, getId());

                update_pstmt.executeUpdate();
                update_pstmt.close();
            }
        } catch (SQLException e) {
        }
    }

    public void updateHouse(String markler_login){
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();
        try{
            // wenn ID nicht initial ist wird die Safe methode an einem Objekt aufgerufen, d.h. es wird ein Update durchgeführt
            // prüfe ob makler Estate bearbeiten darf
            String testSQL = "SELECT * FROM estate WHERE id = ? AND fk_agent_login = ?";
            PreparedStatement test_pstmt = con.prepareStatement(testSQL);

            test_pstmt.setInt(1, getId());
            test_pstmt.setString(2, markler_login);

            ResultSet test_rs = test_pstmt.executeQuery();
            if(test_rs.next()) {
                // wenn makler bearbeiten darf
                String updateSQL = "Update house SET floors = ?, price = ?, garden = ? WHERE fk_estate_id = ?";
                PreparedStatement update_pstmt = con.prepareStatement(updateSQL);

                update_pstmt.setInt(1, getFloors());
                update_pstmt.setInt(2, getPrice());
                update_pstmt.setInt(3, getGarden());
                update_pstmt.setInt(4, getId());

                update_pstmt.executeUpdate();
                update_pstmt.close();
            }
        } catch (SQLException e) {
        }
    }

    public static void showEstates(String makler_login) {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();
        try {
            // Teste ob Estate mit id existiert
            String test_SQL = "SELECT * FROM estate WHERE fk_agent_login = ?";
            PreparedStatement test_pstmt = con.prepareStatement(test_SQL);
            test_pstmt.setString(1, makler_login);
            ResultSet rs = test_pstmt.executeQuery();
            while(rs.next()) {
                System.out.println("ID: " + rs.getString("id") +
                        ", Street: " + rs.getString("street"));
            }

            test_pstmt.close();
            rs.close();
            System.out.println();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEstate(int id, String makler_login) {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // Teste ob Estate mit id existiert
            String test_SQL = "SELECT * FROM estate WHERE id = ? AND fk_agent_login = ?";
            PreparedStatement test_pstmt = con.prepareStatement(test_SQL);
            test_pstmt.setInt(1, id);
            test_pstmt.setString(2, makler_login);
            ResultSet test_rs = test_pstmt.executeQuery();
            if (!test_rs.next()){
                System.out.println("Keine Berechtigung für Estate mit der ID " + id + " vorhanden.");
                test_pstmt.close();
                test_rs.close();
                return;
            }
            else {
                try {
                    String apart_SQL = "SELECT * FROM apartment WHERE fk_estate_id = ?";
                    PreparedStatement apart_pstmt = con.prepareStatement(apart_SQL);
                    apart_pstmt.setInt(1, id);
                    ResultSet apart_rs = apart_pstmt.executeQuery();
                    if(!apart_rs.next()){
                        //lösche den Eintrag aus House
                        String HdeleteSQL = "DELETE FROM house WHERE FK_ESTATE_ID = ?";
                        PreparedStatement Adelete_pstmt = con.prepareStatement(HdeleteSQL);
                        Adelete_pstmt.setInt(1, id);

                        // Führe Querry aus
                        Adelete_pstmt.executeUpdate();
                        Adelete_pstmt.close();
                    }
                    else{
                        //lösche den Eintrag aus Apartment
                        String AdeleteSQL = "DELETE FROM apartment WHERE FK_ESTATE_ID = ?";
                        PreparedStatement Hdelete_pstmt = con.prepareStatement(AdeleteSQL);
                        Hdelete_pstmt.setInt(1, id);

                        // Führe Querry aus
                        Hdelete_pstmt.executeUpdate();
                        Hdelete_pstmt.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                // Tatsächlies löschen
                // Erzeuge Querry
                String selectSQL = "DELETE FROM estate WHERE id = ?";
                PreparedStatement pstmt = con.prepareStatement(selectSQL);
                pstmt.setInt(1, id);

                // Führe Querry aus
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("Estate mit ID " + id + " wurde gelöscht.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
