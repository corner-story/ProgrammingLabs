package com.se.device.entity;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;


    @Column(name = "role_id")
    int role_id;

    @Column(name = "dp_id")
    int dp_id;

    @Column(name = "create_time")
    Date create_time;

    @Column(name = "backup")
    String backup;

    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getDp_id() {
        return dp_id;
    }

    public void setDp_id(int dp_id) {
        this.dp_id = dp_id;
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
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role_id=" + role_id +
                ", dp_id=" + dp_id +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                '}';
    }


}
