package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.store.entities.AdminPanelBlock;

@Repository
public interface AdminPanelBlockRepository extends JpaRepository<AdminPanelBlock, Long> {

}
