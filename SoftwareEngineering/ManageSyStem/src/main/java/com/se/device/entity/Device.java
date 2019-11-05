package com.se.device.entity;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "model")
    String model;

    @Column(name = "type")
    String type;

    @Column(name = "inside_number")
    int inside_number;

    @Column(name = "belong_dp_id")
    int belong_dp_id;

    @Column(name = "location")
    String location;

    @Column(name = "create_time")
    Date create_time;

    @Column(name = "backup")
    String backup;

    public Device() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInside_number() {
        return inside_number;
    }

    public void setInside_number(int inside_number) {
        this.inside_number = inside_number;
    }

    public int getBelong_dp_id() {
        return belong_dp_id;
    }

    public void setBelong_dp_id(int belong_dp_id) {
        this.belong_dp_id = belong_dp_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", inside_number=" + inside_number +
                ", belong_dp_id=" + belong_dp_id +
                ", location='" + location + '\'' +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                '}';
    }
}
