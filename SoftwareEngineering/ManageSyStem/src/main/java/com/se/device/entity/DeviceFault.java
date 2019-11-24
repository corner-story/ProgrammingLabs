package com.se.device.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_fault")
@EntityListeners(AuditingEntityListener.class)
public class DeviceFault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "device_id")
    private Integer device_id;

    @Column(name = "device_name")
    private String device_name;

    @Column(name = "fault_state", length = 600)
    private String fault_state;

    @Temporal(TemporalType.DATE)
    @Column(name = "begin_time")
    private Date begin_time;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_time")
    private Date end_time;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "result", length = 600)
    private String result;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "create_time")
    private Date create_time;

    @Column(name = "backup")
    private String backup;

    @Column(name = "authorize_id")
    private Integer authorize_id;

    @Column(name = "authorize_name")
    private String authorize_name;

    @Column(name = "authorize_result")
    private String authorize_result = "等待审核";



    public DeviceFault() {
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

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getFault_state() {
        return fault_state;
    }

    public void setFault_state(String fault_state) {
        this.fault_state = fault_state;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public Integer getAuthorize_id() {
        return authorize_id;
    }

    public void setAuthorize_id(Integer authorize_id) {
        this.authorize_id = authorize_id;
    }

    public String getAuthorize_name() {
        return authorize_name;
    }

    public void setAuthorize_name(String authorize_name) {
        this.authorize_name = authorize_name;
    }

    public String getAuthorize_result() {
        return authorize_result;
    }

    public void setAuthorize_result(String authorize_result) {
        this.authorize_result = authorize_result;
    }

    @Override
    public String toString() {
        return "DeviceFault{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", device_name='" + device_name + '\'' +
                ", fault_state='" + fault_state + '\'' +
                ", begin_time=" + begin_time +
                ", end_time=" + end_time +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", result='" + result + '\'' +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                ", authorize_id=" + authorize_id +
                ", authorize_name='" + authorize_name + '\'' +
                ", authorize_result='" + authorize_result + '\'' +
                '}';
    }
}
