package webCalendarSpring.presentation;

import java.time.LocalDate;

public class EventPostResponse {
    private String message;
    private String event;
    private LocalDate date;

    private void setDate(LocalDate date) {
        this.date = date;
    }

    private void setEvent(String event) {
        this.event = event;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getEvent() {
        return event;
    }

    public LocalDate getDate() {
        return date;
    }

    private EventPostResponse() {}

    public static class Builder {
        private final EventPostResponse response;

        public Builder() {
            response = new EventPostResponse();
        }

        public Builder message(String message) {
            response.setMessage(message);
            return this;
        }

        public Builder event(String event) {
            response.setEvent(event);
            return this;
        }

        public Builder date(LocalDate date) {
            response.setDate(date);
            return this;
        }

        public EventPostResponse build() {
            return response;
        }
    }
}
