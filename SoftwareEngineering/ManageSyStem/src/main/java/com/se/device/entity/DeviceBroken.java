package com.se.device.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_broken")
@EntityListeners(AuditingEntityListener.class)
public class DeviceBroken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "device_id")
    private Integer device_id;

    @Column(name = "device_name")
    private String device_name;

    @Column(name = "authorize_id")
    private Integer authorize_id;

    @Column(name = "authorize_name")
    private String authorize_name;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "handle_way", length = 600)
    private String handle_way;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "create_time")
    private Date create_time;

    @Column(name = "backup")
    private String backup;

    public DeviceBroken() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getAuthorize_id() {
        return authorize_id;
    }

    public void setAuthorize_id(Integer authorize_id) {
        this.authorize_id = authorize_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getHandle_way() {
        return handle_way;
    }

    public void setHandle_way(String handle_way) {
        this.handle_way = handle_way;
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

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getAuthorize_name() {
        return authorize_name;
    }

    public void setAuthorize_name(String authorize_name) {
        this.authorize_name = authorize_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "DeviceBroken{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", device_name='" + device_name + '\'' +
                ", authorize_id=" + authorize_id +
                ", authorize_name='" + authorize_name + '\'' +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", handle_way='" + handle_way + '\'' +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                '}';
    }
}
