package de.dis2011.data;

public class House {

    private int fk_estate_id;
    private int floors;
    private int price;
    private int garden;

    public int getFk_estate_id() {
        return fk_estate_id;
    }

    public void setFk_estate_id(int fk_estate_id) {
        this.fk_estate_id = fk_estate_id;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGarden() {
        return garden;
    }

    public void setGarden(int garden) {
        this.garden = garden;
    }


        public static House getByID(int id) {

            return null;

}}
