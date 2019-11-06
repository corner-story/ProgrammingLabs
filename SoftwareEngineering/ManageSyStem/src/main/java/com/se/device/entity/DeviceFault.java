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

    @Column(name = "fault_state", length = 600)
    private String fault_state;

    @Temporal(TemporalType.DATE)
    @Column(name = "begin_time")
    private Date begin_time;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_time")
    private Date end_time;

    @Column(name = "leader_id")
    private Integer leader_id;

    @Column(name = "result", length = 600)
    private String result;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "create_time")
    private Date create_time;

    @Column(name = "backup")
    private String backup;

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

    public Integer getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(Integer leader_id) {
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
