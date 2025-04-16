package com.epam.training.login.domain;

import java.util.Objects;

public class Address {

    private String country;
    private String city;

    private String addressLine;
    private String zipCode;

    private String name;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(addressLine, address.addressLine) && Objects.equals(zipCode, address.zipCode) && Objects.equals(name, address.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, addressLine, zipCode, name);
    }

    @Override
    public String toString() {
        return name + "\n"
                + zipCode + "\n"
                + city + "\n"
                + addressLine + "\n"
                + country;
    }
}
