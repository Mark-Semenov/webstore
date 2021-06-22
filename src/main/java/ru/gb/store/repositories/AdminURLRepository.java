package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.store.entities.AdminURL;

@Repository
public interface AdminURLRepository extends JpaRepository<AdminURL, Long> {

}
