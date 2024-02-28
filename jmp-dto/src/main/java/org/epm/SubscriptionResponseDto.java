package org.epm;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class SubscriptionResponseDto extends RepresentationModel<SubscriptionResponseDto> {
    private Long id;
    private Long userId;
    private LocalDate startDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public SubscriptionResponseDto(Long id) {
        this.id = id;
    }

    public SubscriptionResponseDto(Link initialLink, Long id) {
        super(initialLink);
        this.id = id;
    }

    public SubscriptionResponseDto(Iterable<Link> initialLinks, Long id) {
        super(initialLinks);
        this.id = id;
    }

    public SubscriptionResponseDto() {
        super();
    }

}
