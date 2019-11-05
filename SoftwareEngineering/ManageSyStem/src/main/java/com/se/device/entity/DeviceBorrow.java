package com.se.device.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_borrow")
public class DeviceBorrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "device_id")
    int device_id;

    @Column(name = "dp_id")
    int dp_id;

    @Column(name = "user_id")
    int user_id;

    @Column(name = "do_what", length = 600)
    String do_what;

    @Column(name = "back_time")
    Date back_time;

    @Column(name = "return_time")
    Date return_time;

    @Column(name = "create_time")
    Date create_time;

    @Column(name = "backup")
    String backup;

    public DeviceBorrow() {
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

    public int getDp_id() {
        return dp_id;
    }

    public void setDp_id(int dp_id) {
        this.dp_id = dp_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDo_what() {
        return do_what;
    }

    public void setDo_what(String do_what) {
        this.do_what = do_what;
    }

    public Date getBack_time() {
        return back_time;
    }

    public void setBack_time(Date back_time) {
        this.back_time = back_time;
    }

    public Date getReturn_time() {
        return return_time;
    }

    public void setReturn_time(Date return_time) {
        this.return_time = return_time;
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
        return "DeviceBorrow{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", dp_id=" + dp_id +
                ", user_id=" + user_id +
                ", do_what='" + do_what + '\'' +
                ", back_time=" + back_time +
                ", return_time=" + return_time +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                '}';
    }
}
