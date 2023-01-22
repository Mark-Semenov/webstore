package ru.gb.mark.webstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.mark.webstore.entity.AdminURL;

@Repository
public interface AdminURLRepository extends JpaRepository<AdminURL, Long> {

}
