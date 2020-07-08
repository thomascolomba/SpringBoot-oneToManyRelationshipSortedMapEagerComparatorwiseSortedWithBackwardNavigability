package com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.domains.B;


public interface BRepository extends CrudRepository<B, Long> {
	List<B> findByB(String b);
}