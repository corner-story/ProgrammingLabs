package com.se.device.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_fault")
public class DeviceFault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "device_id")
    int device_id;

    @Column(name = "fault_state", length = 600)
    String fault_state;

    @Column(name = "begin_time")
    Date begin_time;

    @Column(name = "end_time")
    Date end_time;

    @Column(name = "leader_id")
    int leader_id;

    @Column(name = "result", length = 600)
    String result;


    @Column(name = "create_time")
    Date create_time;

    @Column(name = "backup")
    String backup;

    public DeviceFault() {
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

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
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

    @Override
    public String toString() {
        return "DeviceFault{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", fault_state='" + fault_state + '\'' +
                ", begin_time=" + begin_time +
                ", end_time=" + end_time +
                ", leader_id=" + leader_id +
                ", result='" + result + '\'' +
                ", create_time=" + create_time +
                ", backup='" + backup + '\'' +
                '}';
    }
}
