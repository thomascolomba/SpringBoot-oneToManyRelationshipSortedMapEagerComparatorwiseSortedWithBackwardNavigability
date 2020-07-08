CRUD operations on a OneToMany relationship with a java SortedSet type with Eager fetch, backward navigability on that relationship.<br/>
The ordering of the list's elements is determined by a java method (a Comparator class).<br/>
An instance of A has a SortedMap of B.<br/>
An instance of B has a reference to the A instance it is related to.<br/>
A <><--0..*-----1-- B<br/>
<br/>
compile & execute :<br/>
mvn spring-boot:run<br/>
compile into fat jar then execute :<br/>
mvn clean package<br/>
java -jar target/oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability-0.0.1-SNAPSHOT.jar<br/>
<br/>
To Compile from within Eclipse or any other IDE, you need to install Lombok : https://projectlombok.org/setup/overview<br/>
<br/>
<br/>

--A.java (entity that holds a collection of B entities)<br/>
<b>@OneToMany(mappedBy = "a", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)<br/>
@SortComparator(AKeyComparator.class)<br/>
@MapKey(name="b")<br/>
private SortedMap&lt;String, B&gt; bSet = new TreeMap&lt;String, B&gt;();</b><br/>

--B.java (entity related to a A entity)<br/>
<b>@ManyToOne<br/>
@JoinColumn(name = "a_id", nullable = false)<br/>
private A a;</b><br/>
a is a reference to the A instance that holds the B instance. It allows backward navigability (we can write b.getA() to retrieve the A instance holding this B instance).<br/>

--AKeyComparator.java (implements Comparator&lt;String&gt;)<br/>
return keyOfA_1.compareTo(keyOfA_2);<br/>

--AccessingDataJpaApplication.java (main class)<br/>
log.info("===== Persisting As and Bs");<br/>
<b>persistData</b>(aRepository, bRepository);<br/>
readData(aRepository, bRepository);<br/>
log.info("===== Modifying some As and Bs");<br/>
<b>modifyData</b>(aRepository, bRepository);<br/>
readData(aRepository, bRepository);<br/>
log.info("===== Deleting some As and Bs");<br/>
<b>deleteData</b>(aRepository, bRepository);<br/>
readData(aRepository, bRepository);<br/>
...<br/>
<b>persistData(){</b><br/>
&nbsp;&nbsp;//we build A without nested Bs, we set A to each B<br/>
&nbsp;&nbsp;A a1 = new A("a1");<br/>
&nbsp;&nbsp;A a2 = new A("a2");<br/>
&nbsp;&nbsp;B b1 = new B("b1", a1);<br/>
&nbsp;&nbsp;B b2 = new B("b2", a1);<br/>
&nbsp;&nbsp;B b3 = new B("b3", a2);<br/>
&nbsp;&nbsp;B b4 = new B("b4", a2);<br/>
&nbsp;&nbsp;aRepository.save(a1);<br/>
&nbsp;&nbsp;aRepository.save(a2);<br/>
&nbsp;&nbsp;bRepository.save(b1);<br/>
&nbsp;&nbsp;bRepository.save(b2);<br/>
&nbsp;&nbsp;bRepository.save(b3);<br/>
&nbsp;&nbsp;bRepository.save(b4);<br/>
}<br/>
<b>modifyData(){</b><br/>
&nbsp;&nbsp;//we change change the value of a1's b1 to b5 and a2's b3 in b6<br/>
&nbsp;&nbsp;A a1 = aRepository.findByA("a1").get(0);<br/>
&nbsp;&nbsp;for(Map.Entry<String, B> entry : a1.getMyMap().entrySet()) {<br/>
&nbsp;&nbsp;&nbsp;&nbsp;if(entry.getKey().equals("b1")) {<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;entry.getValue().setB("b5");<br/>
&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
&nbsp;&nbsp;}<br/>
&nbsp;&nbsp;aRepository.save(a1);<br/>
&nbsp;&nbsp;B b3 = bRepository.findByB("b3").get(0);<br/>
&nbsp;&nbsp;b3.setB("b6");<br/>
&nbsp;&nbsp;bRepository.save(b3);<br/>
}<br/>
<b>deleteData(){</b><br/>
&nbsp;&nbsp;//we delete a1 and b4<br/>
&nbsp;&nbsp;A a1 = aRepository.findByA("a1").get(0);<br/>
&nbsp;&nbsp;aRepository.delete(a1);<br/>
&nbsp;&nbsp;A a2 = aRepository.findByA("a2").get(0);<br/>
&nbsp;&nbsp;a2.getMyMap().remove("b4");<br/>
&nbsp;&nbsp;aRepository.save(a2);<br/>
}<br/>