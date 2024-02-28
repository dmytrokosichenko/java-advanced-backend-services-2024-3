package org.epm.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.epm.SubscriptionRequestDto;
import org.epm.SubscriptionResponseDto;
import org.epm.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {


    private final SubscriptionService subscriptionService;

    @Autowired
    public ServiceController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }


    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> createSubscription(@RequestBody SubscriptionRequestDto request) {
        SubscriptionResponseDto response = subscriptionService.createSubscription(request);
        addHateoasLinks(response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<SubscriptionResponseDto> updateSubscription(@Parameter(description = "Subscription ID", required = true, example = "1") @PathVariable("id") Long id, @RequestBody SubscriptionRequestDto request) {
        SubscriptionResponseDto response = subscriptionService.updateSubscription(id, request);
        addHateoasLinks(response);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<SubscriptionResponseDto> getSubscription(@Parameter(description = "Subscription ID", required = true, example = "1") @PathVariable("id") Long id) {
        SubscriptionResponseDto response = subscriptionService.getSubscription(id);
        addHateoasLinks(response);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<CollectionModel<SubscriptionResponseDto>> getAllSubscription() {
        List<SubscriptionResponseDto> responses = subscriptionService
                .getAllSubscription()
                .stream()
                .peek(this::addHateoasLinks)
                .toList();
        Link links = linkTo(methodOn(ServiceController.class).getAllSubscription()).withSelfRel();
        CollectionModel<SubscriptionResponseDto> collectionModel = CollectionModel.of(responses, links);
        return ResponseEntity.ok(collectionModel);
    }

    public ResponseEntity<Void> deleteSubscription(@Parameter(description = "Subscription ID", required = true, example = "1") @PathVariable("id") Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    private void addHateoasLinks(SubscriptionResponseDto response) {
        Long id = response.getId();
        response.add(linkTo(methodOn(ServiceController.class).getSubscription(id)).withSelfRel());
        response.add(linkTo(methodOn(ServiceController.class).getAllSubscription()).withRel("getAllSubscription"));
        response.add(linkTo(methodOn(ServiceController.class).updateSubscription(id, null)).withRel("updateSubscription"));
        response.add(linkTo(methodOn(ServiceController.class).deleteSubscription(id)).withRel("deleteSubscription"));
    }
}
