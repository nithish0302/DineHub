package com.dinehub.paymentservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

    @Id
    private  long paymentId;
}
