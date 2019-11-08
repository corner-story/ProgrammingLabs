package com.se.device.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device")
@EntityListeners(AuditingEntityListener.class)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "type")
    private String type;

    @Column(name = "inside_number")
    private Integer inside_number;

    @Column(name = "belong_dp_id")
    private Integer belong_dp_id;

    @Column(name = "belong_dp_name")
    private String belong_dp_name;

    @Column(name = "location")
    private String location;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "create_time")
    private java.util.Date create_time;

    @Column(name = "backup")
    private String backup;

    public Device() {
    }

    public String getBelong_dp_name() {
        return belong_dp_name;
    }

    public void setBelong_dp_name(String belong_dp_name) {
        this.belong_dp_name = belong_dp_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getInside_number() {
        return inside_number;
    }

    public void setInside_number(Integer inside_number) {
        this.inside_number = inside_number;
    }

    public Integer getBelong_dp_id() {
        return belong_dp_id;
    }

    public void setBelong_dp_id(Integer belong_dp_id) {
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
                ", belong_dp_name='" + belong_dp_name + '\'' +
                ", location='" + location + '\'' +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                '}';
    }
}
