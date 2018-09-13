package pl.training.cloud.payments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {

    @GeneratedValue
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTimestamp;
    @Column(unique = true)
    private String transactionId;
    @OneToOne(cascade = CascadeType.PERSIST)
    private PaymentInfo paymentInfo;

}
