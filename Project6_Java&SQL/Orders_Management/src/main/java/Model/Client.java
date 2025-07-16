package Model;

public class Client {
    int ClientID;
    String LastName;
    String FirstName;
    String Email;
    String PhoneNumber;
    String Street;
    String Number;
    String City;
    String PostalCode;
    String Country;

    public Client(int clientID, String lastName, String firstName, String email, String phoneNumber, String street, String number, String city, String postalCode, String country) {
        ClientID = clientID;
        LastName = lastName;
        FirstName = firstName;
        Email = email;
        PhoneNumber = phoneNumber;
        Street = street;
        Number = number;
        City = city;
        PostalCode = postalCode;
        Country = country;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    @Override
    public String toString() {
        return "Client{" +
                "ClientID=" + ClientID +
                ", LastName='" + LastName + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", Email='" + Email + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Street='" + Street + '\'' +
                ", Number='" + Number + '\'' +
                ", City='" + City + '\'' +
                ", PostalCode='" + PostalCode + '\'' +
                ", Country='" + Country + '\'' +
                '}';
    }
}
