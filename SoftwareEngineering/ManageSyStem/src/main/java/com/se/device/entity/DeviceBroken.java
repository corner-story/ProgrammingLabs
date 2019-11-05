package com.se.device.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_broken")
public class DeviceBroken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "device_id")
    int device_id;

    @Column(name = "authorize_id")
    int authorize_id;

    @Column(name = "user_id")
    int user_id;

    @Column(name = "handle_way", length = 600)
    String handle_way;

    @Column(name = "create_time")
    Date create_time;

    @Column(name = "backup")
    String backup;

    public DeviceBroken() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getAuthorize_id() {
        return authorize_id;
    }

    public void setAuthorize_id(int authorize_id) {
        this.authorize_id = authorize_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
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

    @Override
    public String toString() {
        return "DeviceBroken{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", authorize_id=" + authorize_id +
                ", user_id=" + user_id +
                ", handle_way='" + handle_way + '\'' +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                '}';
    }
}
