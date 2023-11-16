package ru.katkova.pojo;

public class CourierLoginResponse
{
    private Long id;

    public CourierLoginResponse(Long id)
    {
        this.id = id;
    }

    public CourierLoginResponse()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
