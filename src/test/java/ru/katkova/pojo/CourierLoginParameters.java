package ru.katkova.pojo;

public class CourierLoginParameters
{
    private String login;
    private String password;

    public CourierLoginParameters(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public CourierLoginParameters()
    {
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
