package eu.ubi.techradar.repository;

import eu.ubi.techradar.entity.Item;
import eu.ubi.techradar.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {}
