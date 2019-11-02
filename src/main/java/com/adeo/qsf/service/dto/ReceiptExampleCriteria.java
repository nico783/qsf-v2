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
 * Criteria class for the {@link com.adeo.qsf.domain.ReceiptExample} entity. This class is used
 * in {@link com.adeo.qsf.web.rest.ReceiptExampleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /receipt-examples?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReceiptExampleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter state;

    public ReceiptExampleCriteria(){
    }

    public ReceiptExampleCriteria(ReceiptExampleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.state = other.state == null ? null : other.state.copy();
    }

    @Override
    public ReceiptExampleCriteria copy() {
        return new ReceiptExampleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReceiptExampleCriteria that = (ReceiptExampleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        state
        );
    }

    @Override
    public String toString() {
        return "ReceiptExampleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
            "}";
    }

}
