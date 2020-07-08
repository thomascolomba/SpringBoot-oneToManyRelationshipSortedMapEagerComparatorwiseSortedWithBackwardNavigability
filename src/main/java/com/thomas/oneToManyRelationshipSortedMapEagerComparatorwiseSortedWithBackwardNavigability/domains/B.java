package com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.domains;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "B")
@NoArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of = {"id"})
public class B implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String b;

    @ManyToOne
    @JoinColumn(name = "a_id", nullable = true)
    private A a;

    public B(String b, A a) {
        this.b = b;
        this.a = a;
    }

    @Override
    public String toString() {
        return "B{" +
                "id=" + id +
                ", b=" + b +
//                ", a.a=" + a.getA() +
                '}';
    }
}