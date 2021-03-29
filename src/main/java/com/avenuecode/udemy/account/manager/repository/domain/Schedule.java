package com.avenuecode.udemy.account.manager.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    private Date start;
    private Date end;
    private String scheduledFor;
    private String seniority;
    private String technology;
    private String hours;
    private String goal;
    private Boolean active;

    public Schedule(final Account account, final Date end, final String scheduledFor,
                    final String seniority, final String technology, final String hours, final String goal) {
        this.account = account;
        //Schedule will start counting from its creating date
        this.start = new Date();
        this.end = end;
        this.scheduledFor = scheduledFor;
        this.seniority = seniority;
        this.technology = technology;
        this.hours = hours;
        this.goal = goal;
        this.active = true;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", scheduledFor='" + scheduledFor + '\'' +
                ", seniority='" + seniority + '\'' +
                ", technology='" + technology + '\'' +
                ", hours='" + hours + '\'' +
                ", goal='" + goal + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Schedule schedule = (Schedule) o;

        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

