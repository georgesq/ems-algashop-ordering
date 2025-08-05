package br.com.transformers.ems.algashop.ordering.domain.model.entity;

import java.time.OffsetDateTime;
import java.util.Objects;

import br.com.transformers.ems.algashop.ordering.domain.model.exception.CustomerArchivedException;
import br.com.transformers.ems.algashop.ordering.domain.model.exception.RegisteredAtException;
import br.com.transformers.ems.algashop.ordering.domain.model.validator.NotNullNonEmptyValidator;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Address;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.BirthDate;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Document;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Email;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.FullName;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.LoyaltPoints;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.Phone;
import br.com.transformers.ems.algashop.ordering.domain.model.valueobject.id.CustomerId;
import lombok.Builder;

public class Customer implements AggregateRoot<CustomerId> {

    private CustomerId id;

    private FullName fullName;

    private BirthDate birthDate;

    private Email email;

    private Phone phone;

    private Document document;

    private Boolean promotionNotificaficationsAllowed;

    private Boolean archived = false;

    private OffsetDateTime registeredAt;

    private OffsetDateTime archivedAt;

    private LoyaltPoints loyaltyPoints;

    private Address address;

    private static final NotNullNonEmptyValidator nnnev = NotNullNonEmptyValidator.getInstance();

    @Builder(builderClassName = "NewSimpleCustomerBuild", builderMethodName = "draft")
    private static Customer createNewInstance(FullName fullName, BirthDate birthDate, Email email, Phone phone,
            Document document, Boolean promotionNotificaficationsAllowed,
            Address address) {

        return new Customer(
                new CustomerId(),
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificaficationsAllowed,
                false,
                OffsetDateTime.now(),
                null,
                new LoyaltPoints(),
                address
        );

    }

    @Builder(builderClassName = "NewFullCustomerBuild", builderMethodName = "existing")
    private Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone, Document document,
                    Boolean promotionNotificaficationsAllowed, Boolean archived, OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt, LoyaltPoints loyaltyPoints, Address address) {

        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificaficationsAllowed(promotionNotificaficationsAllowed);
        this.setRegisteredAt(registeredAt);
        this.setAddress(address);
        this.setLoyaltyPoints(new LoyaltPoints());
        this.setLoyaltyPoints(loyaltyPoints);
        this.setArchived(archived);
        this.setArchivedAt(archivedAt);
    }

    public void addLoyaltyPoints(Integer value) {
        this.verifyIsChangeble();

        this.loyaltyPoints = this.loyaltyPoints.add(value);
    }

    public void archive() {
        verifyIsChangeble();

        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setAddress(this.address.toBuilder()
                .number(null)
                .complement(null)
                .build());
    }

    public void enablePromotionNotificafications() {
        verifyIsChangeble();

        this.setPromotionNotificaficationsAllowed(true);
    }

    public void disablePromotionNotificafications() {
        verifyIsChangeble();

        this.setPromotionNotificaficationsAllowed(false);
    }

    public void changeName(FullName value) {
        verifyIsChangeble();

        this.fullName = value;
    }

    public void changeEmail(Email value) {
        verifyIsChangeble();

        this.email = value;
    }

    public void changePhone(Phone value) {
        verifyIsChangeble();

        this.phone = value;
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
        return document;
    }

    public Boolean isPromotionNotificaficationsAllowed() {
        return promotionNotificaficationsAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public Integer loyaltyPoints() {
        return loyaltyPoints.value();
    }

    public Address address() {
        return this.address;
    }

    public void changeAddress(Address value) {
        verifyIsChangeble();

        this.address = value;
    }

    private void setPromotionNotificaficationsAllowed(Boolean value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("PromotionNotificaficationsAllowed is invalid");
        }

        this.promotionNotificaficationsAllowed = value;
    }

    private void setRegisteredAt(OffsetDateTime value) {
        if (!nnnev.isValid(value, null)) {
            throw new RegisteredAtException();
        }

        this.registeredAt = value;
    }

    private void setArchivedAt(OffsetDateTime value) {
        this.archivedAt = value;
    }

    private void setId(CustomerId value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("Id is invalid");
        }

        this.id = value;
    }

    private void setFullName(FullName value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("FullName is invalid");
        }

        this.fullName = value;
    }

    private void setBirthDate(BirthDate value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("BirthDate is invalid");
        }

        this.birthDate = value;
    }

    private void setEmail(Email value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("Email is invalid");
        }

        this.email = value;
    }

    private void setPhone(Phone value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("Phone is invalid");
        }

        this.phone = value;
    }

    private void setDocument(Document value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("Document is invalid");
        }

        this.document = value;
    }

    private void setArchived(Boolean value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("Archived is invalid");
        }

        this.archived = value;
    }

    private void setLoyaltyPoints(LoyaltPoints value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("LoyaltyPoints is invalid");
        }
        this.loyaltyPoints = value;
    }

    private void setAddress(Address value) {
        if (!nnnev.isValid(value, null)) {
            throw new IllegalArgumentException("Address is invalid");
        }

        this.address = value;
    }

    private void verifyIsChangeble() {
        if (this.archived) {
            throw new CustomerArchivedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
