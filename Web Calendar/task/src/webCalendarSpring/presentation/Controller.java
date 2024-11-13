package webCalendarSpring.presentation;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import webCalendarSpring.business.Event;
import webCalendarSpring.business.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private EventService service;

    @Autowired
    public Controller(EventService service) {
        this.service = service;
    }

    @GetMapping("/event/today")
    public ResponseEntity<List<Event>> getTodaysEvent() {
        return ResponseEntity
                .ok()
                .body(service.getTodaysEvents());
    }

    @GetMapping("/event")
    public ResponseEntity<List<?>> getEvents(
            @RequestParam(name = "start_time", required = false) LocalDate start_time,
            @RequestParam(name = "end_time", required = false) LocalDate end_time) {
        if (start_time == null && end_time == null) {
            return getAllEvents();
        }
        return getEventsWithinTimeframe(start_time, end_time);
    }

    private ResponseEntity<List<?>> getAllEvents() {
        if (service.isEmpty()) {
            return ResponseEntity.status(204).body(List.of());
        } else {
            return ResponseEntity
                    .ok()
                    .body(service.getAllEvents());
        }
    }

    private ResponseEntity<List<?>> getEventsWithinTimeframe(LocalDate start_time, LocalDate end_time) {
        List<Event> eventList = service.getEventsWithinTimeframe(start_time, end_time);
        return eventList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(eventList);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") int id) {
        if (!service.idExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "The event doesn't exist!"));
        }
        return ResponseEntity.ok().body(service.getEventById(id));
    }

    @PostMapping("/event")
    public ResponseEntity<EventPostResponse> postEvent(@RequestBody @Valid Event event, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.saveEvent(event);
        EventPostResponse response = new EventPostResponse.Builder()
                .message("The event has been added!")
                .event(event.getEvent())
                .date(event.getDate())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") int id) {
        if (!service.idExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "The event doesn't exist!"));
        }
        return ResponseEntity.ok().body(service.deleteEvent(id));
    }
}
