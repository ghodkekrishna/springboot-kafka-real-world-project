package net.codefusionhub.springboot.repository;

import net.codefusionhub.springboot.entity.Wikimedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikimediaDataRepository extends JpaRepository<Wikimedia, Long> {
}
