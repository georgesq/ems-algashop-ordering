package com.algaworks.algashop.ordering.presentation;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algashop.ordering.domain.model.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.domain.model.DomainException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerEmailIsInUseException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, 
            HttpStatusCode status, 
            WebRequest request) {
        
        var problemDetail = ProblemDetail.forStatus(status.value());

        problemDetail.setTitle("Invalid fields");
        problemDetail.setDetail("One or more fields are invalid");
        problemDetail.setType(URI.create("/errors/invalid-fields"));

        Map<Object, Object> fieldErrors = ex.getBindingResult().getAllErrors().stream().collect(

            Collectors.toMap(
                objectError -> ((FieldError) objectError).getField(), 
                objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
            )

        );

        problemDetail.setProperty("fields", fieldErrors);

        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(DomainEntityNotFoundException.class)
    public ProblemDetail handleDomainEntityNotFoundProblemDetail(DomainEntityNotFoundException e) {

        var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Not found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/not-found"));

        return problemDetail;

    }

    @ExceptionHandler({UnprocessableEntityException.class, DomainException.class})
    public ProblemDetail handleUnprocessableEntityProblemDetail(Exception e) {

        var problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        problemDetail.setTitle("Unprocessable entity");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/unprocessable-entity"));

        return problemDetail;

    }

    @ExceptionHandler(CustomerEmailIsInUseException.class)
    public ProblemDetail handleCustomerEmailIsInUseProblemDetail(CustomerEmailIsInUseException e) {

        var problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problemDetail.setTitle("conflict");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/conflict"));

        return problemDetail;

    }
    
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleInternalServerErrorProblemDetail(Exception e) {

        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problemDetail.setTitle("internal server error");
        problemDetail.setDetail("An unexpected internal error occurred.");
        problemDetail.setType(URI.create("/errors/internal-error"));

        return problemDetail;

    }

    @ExceptionHandler(GatewayTimeoutException.class)
    public ProblemDetail handleGatewayTimeoutErrorProblemDetail(GatewayTimeoutException e) {

        log.error(PAGE_NOT_FOUND_LOG_CATEGORY, e);

        var problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);

        problemDetail.setTitle("gateway timeout error");
        problemDetail.setDetail("An gateway timeout error occurred.");
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;

    }

    @ExceptionHandler(BadGatewayException.class)
    public ProblemDetail handleBadGatewayTimeoutErrorProblemDetail(BadGatewayException e) {

        log.error("Bad Gateway Error: ", e);

        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);

        problemDetail.setTitle("bad gateway error");
        problemDetail.setDetail("An bad gateway error occurred.");
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;

    }

}
