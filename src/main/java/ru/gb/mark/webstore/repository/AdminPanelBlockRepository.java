package ru.gb.mark.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.mark.webstore.entity.AdminPanelBlock;

@Repository
public interface AdminPanelBlockRepository extends JpaRepository<AdminPanelBlock, Long> {

}
