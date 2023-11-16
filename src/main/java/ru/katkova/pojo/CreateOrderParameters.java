package ru.katkova.pojo;

import java.util.List;

public class CreateOrderParameters
{
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Long rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public CreateOrderParameters(String firstName, String lastName, String address, String metroStation, String phone, Long rentTime, String deliveryDate, String comment, List<String> color)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public CreateOrderParameters(String firstName, String lastName, String address, String metroStation, String phone, Long rentTime, String deliveryDate, String comment)
    {
        this(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, null);
    }

    public CreateOrderParameters()
    {
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getMetroStation()
    {
        return metroStation;
    }

    public void setMetroStation(String metroStation)
    {
        this.metroStation = metroStation;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Long getRentTime()
    {
        return rentTime;
    }

    public void setRentTime(Long rentTime)
    {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate()
    {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate)
    {
        this.deliveryDate = deliveryDate;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public List<String> getColor()
    {
        return color;
    }

    public void setColor(List<String> color)
    {
        this.color = color;
    }
}
