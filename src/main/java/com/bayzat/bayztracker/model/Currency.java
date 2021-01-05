package com.bayzat.bayztracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "\"currency\"",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "symbol"})})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Currency extends BaseEntity  {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "symbol")
    @NotBlank
    private String symbol;

    @Column(name = "currentPrice")
    private Double currentPrice;

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updateAt=" + updatedAt +
                '}';
    }
}