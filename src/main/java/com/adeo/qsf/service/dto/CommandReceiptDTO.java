package com.adeo.qsf.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class CommandReceiptDTO implements Serializable {

    private final Long commandId;
    private final String commandDescription;
    private final Long receiptId;
    private final String receiptState;

    public CommandReceiptDTO(Long commandId, String commandDescription, Long receiptId, String receiptState) {
        this.commandId = commandId;
        this.commandDescription = commandDescription;
        this.receiptId = receiptId;
        this.receiptState = receiptState;
    }

    public Long getCommandId() {
        return commandId;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public String getReceiptState() {
        return receiptState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandReceiptDTO that = (CommandReceiptDTO) o;
        return commandId.equals(that.commandId) &&
            receiptId.equals(that.receiptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandId, receiptId);
    }

    @Override
    public String toString() {
        return "CommandReceiptDTO{" +
            "commandId=" + commandId +
            ", commandDescription='" + commandDescription + '\'' +
            ", receiptId=" + receiptId +
            ", receiptState='" + receiptState + '\'' +
            '}';
    }
}
