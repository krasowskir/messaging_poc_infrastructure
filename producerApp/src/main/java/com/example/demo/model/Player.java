package com.example.demo.model;

//@Entity
public class Player {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Integer id;
    private String firstName;
    private String lastName;
    private int telephoneNumber;

    //@Embedded
    private Address address;

    public Player() {
    }

    public Player(String firstName, String lastName, int telephoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
