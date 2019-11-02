package com.adeo.qsf.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public class CommandReceiptCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter commandId;
    private StringFilter commandDescription;
    private LongFilter receiptId;
    private StringFilter receiptState;

    public CommandReceiptCriteria() {
    }

    public CommandReceiptCriteria(CommandReceiptCriteria other) {
        this.commandId = other.commandId == null ? null : other.commandId.copy();
        this.commandDescription = other.commandDescription == null ? null : other.commandDescription.copy();
        this.receiptId = other.receiptId == null ? null : other.receiptId.copy();
        this.receiptState = other.receiptState == null ? null : other.receiptState .copy();
    }

    @Override
    public Criteria copy() {
        return new CommandReceiptCriteria(this);
    }

    public LongFilter getCommandId() {
        return commandId;
    }

    public void setCommandId(LongFilter commandId) {
        this.commandId = commandId;
    }

    public StringFilter getCommandDescription() {
        return commandDescription;
    }

    public void setCommandDescription(StringFilter commandDescription) {
        this.commandDescription = commandDescription;
    }

    public LongFilter getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(LongFilter receiptId) {
        this.receiptId = receiptId;
    }

    public StringFilter getReceiptState() {
        return receiptState;
    }

    public void setReceiptState(StringFilter receiptState) {
        this.receiptState = receiptState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandReceiptCriteria that = (CommandReceiptCriteria) o;
        return Objects.equals(commandId, that.commandId) &&
            Objects.equals(commandDescription, that.commandDescription) &&
            Objects.equals(receiptId, that.receiptId) &&
            Objects.equals(receiptState, that.receiptState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandId, commandDescription, receiptId, receiptState);
    }

    @Override
    public String toString() {
        return "CommandReceiptCriteria{" +
            "commandId=" + commandId +
            ", commandDescription=" + commandDescription +
            ", receiptId=" + receiptId +
            ", receiptState=" + receiptState +
            '}';
    }
}
