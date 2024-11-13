package webCalendarSpring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webCalendarSpring.business.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e.id FROM Event e ORDER BY e.id ASC")
    List<Long> findAllIdsSorted();
}
