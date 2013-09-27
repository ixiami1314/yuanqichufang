package com.yuanqichufang.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-22
 * Time: 下午8:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class PropertyCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String code;

    @Column
    private boolean useable;

    @OneToOne
    @JoinColumns ({
            @JoinColumn (name = "passportId", referencedColumnName = "id"),
            @JoinColumn (name = "passportName", referencedColumnName = "passport")
    })
    private Passport passport;

    @Column
    private Date usedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isUseable() {
        return useable;
    }

    public void setUseable(boolean useable) {
        this.useable = useable;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
