package com.sokolov.model;

import javax.persistence.*;

@Entity (name = "requests")
@Table(name = "requests")
public class WeatherRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id")
    private WeatherJournal weatherJournal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String city;

    public WeatherRequest(WeatherJournal weatherJournal, User user, String city) {
        this.weatherJournal = weatherJournal;
        this.user = user;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WeatherJournal getWeatherJournal() {
        return weatherJournal;
    }

    public void setWeatherJournal(WeatherJournal weatherJournal) {
        this.weatherJournal = weatherJournal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
