package com.ox.company.crm.repository;

import com.ox.company.crm.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c LEFT JOIN FETCH c.tasks WHERE c.id = :id")
    Contact findContactById(@Param("id") Long id);

    @Query("SELECT c FROM Contact c LEFT JOIN FETCH c.tasks LEFT JOIN FETCH c.client")
    List<Contact> findAllContacts();
}
