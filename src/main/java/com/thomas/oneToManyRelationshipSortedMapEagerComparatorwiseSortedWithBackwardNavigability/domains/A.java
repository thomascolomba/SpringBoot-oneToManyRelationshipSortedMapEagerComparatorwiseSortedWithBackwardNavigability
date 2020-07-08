package com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.domains;

import javax.persistence.*;

import org.hibernate.annotations.SortComparator;

import com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.comparator.AKeyComparator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Entity
@Table(name = "A")
@NoArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of = {"id"})
public class A implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String a;

    @OneToMany(mappedBy = "a", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @SortComparator(AKeyComparator.class)
    @MapKey(name="b")
    private SortedMap<String, B> myMap = new TreeMap<String, B>();

    public A(String a) {
        this.a = a;
    }

    @Override
    public String toString() {
        String toReturn = "A{" +
                "id=" + id +
                ", a='" + a + "' myMap : ";
        for(Map.Entry<String, B> entry : myMap.entrySet()) {
        	toReturn += entry.getKey()+" -> "+entry.getValue().toString();
        }
        toReturn += '}';
        return toReturn;
    }
}