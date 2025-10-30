package com.algaworks.algashop.ordering.presentation;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.customer.query.CustomerFilter;
import com.algaworks.algashop.ordering.application.customer.query.CustomerOutput;
import com.algaworks.algashop.ordering.application.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.application.customer.query.CustomerSummaryOutput;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerManagementApplicationService applicationService;
    private final CustomerQueryService queryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOutput create(@Valid @RequestBody CustomerInput input) {

        var customerId = this.applicationService.create(input);

        return this.queryService.findById(customerId);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageModel<CustomerSummaryOutput> findAll(CustomerFilter customerFilter) {

        return PageModel.of(this.queryService.filter(customerFilter));

    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOutput findById(@PathVariable UUID customerId, HttpServletResponse response) {

        UriComponentsBuilder builder = MvcUriComponentsBuilder.fromMethodCall(
                    MvcUriComponentsBuilder.on(CustomerController.class)
                        .findById(customerId, response));;

        response.addHeader("Location", builder.build().toString());

        return this.queryService.findById(customerId);

    }

}