package com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.domains.A;
import com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.domains.B;
import com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.repositories.ARepository;
import com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.repositories.BRepository;

@SpringBootApplication
@Transactional
public class AccessingDataJpaApplication {

	private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJpaApplication.class);
	}

	@Bean
	public CommandLineRunner demo(ARepository aRepository, BRepository bRepository) {
		return (args) -> {
			log.info("===== Persisting As and Bs");
			persistData(aRepository, bRepository);
			readData(aRepository, bRepository);
			log.info("===== Modifying some As and Bs");
			modifyData(aRepository, bRepository);
			readData(aRepository, bRepository);
			log.info("===== Deleting some As and Bs");
			deleteData(aRepository, bRepository);
			readData(aRepository, bRepository);
		};
	}
	
	private void readData(ARepository aRepository, BRepository bRepository) {
		Iterable<A> As = aRepository.findAll();
		log.info("===== As");
		for(A a : As) {
			log.info(a.toString());
		}
		
		Iterable<B> Bs = bRepository.findAll();
		log.info("===== Bs");
		for(B b : Bs) {
			log.info(b.toString());
		}
	}
	
	private void persistData(ARepository aRepository, BRepository bRepository) {
		//we build A without nested Bs, we set A to each B
		A a1 = new A("a1");
		A a2 = new A("a2");
		B b1 = new B("b1", a1);
		B b2 = new B("b2", a1);
		B b3 = new B("b3", a2);
		B b4 = new B("b4", a2);
		aRepository.save(a1);
		aRepository.save(a2);
		bRepository.save(b1);
		bRepository.save(b2);
		bRepository.save(b3);
		bRepository.save(b4);
		
		//we can build an A without Bs
		A a3 = new A("a3");
		aRepository.save(a3);
	}

	private void modifyData(ARepository aRepository, BRepository bRepository) {
		//we change change the value of a1's b1 to b5 and a2's b3 in b6
		A a1 = aRepository.findByA("a1").get(0);
		for(Map.Entry<String, B> entry : a1.getMyMap().entrySet()) {
			if(entry.getKey().equals("b1")) {
				entry.getValue().setB("b5");
			}
		}
		aRepository.save(a1);
		B b3 = bRepository.findByB("b3").get(0);
		b3.setB("b6");
		bRepository.save(b3);
	}
	
	private void deleteData(ARepository aRepository, BRepository bRepository) {
		//we delete a1 and b4
		A a1 = aRepository.findByA("a1").get(0);
		aRepository.delete(a1);
		
		A a2 = aRepository.findByA("a2").get(0);
		a2.getMyMap().remove("b4");
		aRepository.save(a2);
	}
}
