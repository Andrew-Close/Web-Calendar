package webCalendarSpring.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webCalendarSpring.persistence.EventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EventService {
    private EventRepository repository;

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public void saveEvent(Event event) {
        repository.save(event);
    }

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public Event getEventById(int id) {
        return repository.findById(id).get();
    }

    public List<Event> getTodaysEvents() {
        return repository.findAll()
                .stream()
                .filter(event -> Objects.equals(event.getDate(), LocalDate.now()))
                .toList();
    }

    public List<Event> getEventsWithinTimeframe(LocalDate startTime, LocalDate endTime) {
        if (endTime == null) {
            return repository.findAll()
                    .stream()
                    .filter(event -> event.getDate().isAfter(startTime))
                    .toList();
        } else if (startTime == null) {
            return repository.findAll()
                    .stream()
                    .filter(event -> event.getDate().isBefore(endTime))
                    .toList();
        } else {
            return repository.findAll()
                    .stream()
                    .filter(event -> event.getDate().isAfter(startTime) && event.getDate().isBefore(endTime))
                    .toList();
        }
    }

    public Event deleteEvent(int id) {
        Event event = repository.findById(id).get();
        repository.deleteById(id);
        return event;
    }

    public boolean isEmpty() {
        return repository.findAll().isEmpty();
    }

    public boolean idExists(int id) {
        return repository.existsById(id);
    }
}
