package com.avenuecode.udemy.account.manager.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String seniority;
    private String technology;
    private String hours;
    private String goal;
    private Date created;
    private Boolean scheduled;

    public Request(final String email, final String seniority, final String technology, final String hours, final String goal) {
        this.email = email;
        this.seniority = seniority;
        this.technology = technology;
        this.hours = hours;
        this.goal = goal;
        this.created = new Date();
        this.scheduled = false;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", seniority='" + seniority + '\'' +
                ", technology='" + technology + '\'' +
                ", hours='" + hours + '\'' +
                ", goal='" + goal + '\'' +
                ", created=" + created +
                ", scheduled=" + scheduled +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Request request = (Request) o;

        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
