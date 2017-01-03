package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 5/21/16.
 */

@Entity
@Table(name="tb_params")
public class Parameter {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name = "h1", length = 100)
    private String h1;

    @Column(name = "h2", length = 100)
    private String h2;

    @Column(name = "h3", length = 100)
    private String h3;

    @Column(name = "h4", length = 100)
    private String h4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public String getH4() {
        return h4;
    }

    public void setH4(String h4) {
        this.h4 = h4;
    }
}
