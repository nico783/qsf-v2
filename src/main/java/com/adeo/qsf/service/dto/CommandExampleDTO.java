package com.adeo.qsf.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.adeo.qsf.domain.CommandExample} entity.
 */
public class CommandExampleDTO implements Serializable {

    private Long id;

    private String description;


    private Long receiptExampleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getReceiptExampleId() {
        return receiptExampleId;
    }

    public void setReceiptExampleId(Long receiptExampleId) {
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

        CommandExampleDTO commandExampleDTO = (CommandExampleDTO) o;
        if (commandExampleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commandExampleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommandExampleDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", receiptExample=" + getReceiptExampleId() +
            "}";
    }
}
