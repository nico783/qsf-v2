package com.adeo.qsf.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.adeo.qsf.domain.CommandExample} entity. This class is used
 * in {@link com.adeo.qsf.web.rest.CommandExampleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /command-examples?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommandExampleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LongFilter receiptExampleId;

    public CommandExampleCriteria(){
    }

    public CommandExampleCriteria(CommandExampleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.receiptExampleId = other.receiptExampleId == null ? null : other.receiptExampleId.copy();
    }

    @Override
    public CommandExampleCriteria copy() {
        return new CommandExampleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getReceiptExampleId() {
        return receiptExampleId;
    }

    public void setReceiptExampleId(LongFilter receiptExampleId) {
        this.receiptExampleId = receiptExampleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommandExampleCriteria that = (CommandExampleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(receiptExampleId, that.receiptExampleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        receiptExampleId
        );
    }

    @Override
    public String toString() {
        return "CommandExampleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (receiptExampleId != null ? "receiptExampleId=" + receiptExampleId + ", " : "") +
            "}";
    }

}
