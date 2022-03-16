package ru.kpfu.itis.sokolov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.sokolov.model.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
