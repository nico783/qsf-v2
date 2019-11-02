package com.adeo.qsf.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.adeo.qsf.domain.ReceiptExample} entity.
 */
public class ReceiptExampleDTO implements Serializable {

    private Long id;

    private String state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

        ReceiptExampleDTO receiptExampleDTO = (ReceiptExampleDTO) o;
        if (receiptExampleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), receiptExampleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReceiptExampleDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            "}";
    }
}
