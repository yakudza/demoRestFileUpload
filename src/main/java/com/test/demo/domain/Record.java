package com.test.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "record")
@ToString(of = {"id", "primaryKey", "name", "description", "updated"})
@EqualsAndHashCode(of = {"id", "primaryKey"})
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String primaryKey;
    @Size(min=2, max=30)
    private String name;
    @Size(min=10, max=200)
    private String description;
    private Timestamp updated;

    public Record(){}

    public Record(String[] dataarray) {
        primaryKey = dataarray[0];
        name = dataarray[1];
        description = dataarray[2];
        updated = new Timestamp(Long.valueOf(dataarray[3])*1000);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
