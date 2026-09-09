package com.kafe.koffee.entities;

import com.kafe.koffee.enums.PaymentMethodType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentMethod {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodType type;
    
    /*
    * CARD         -> Visa
    * UPI          -> Google Pay
    * WALLET       -> Paytm
    * NET_BANKING  -> HDFC
    * COD          -> Cash on Delivery */
    
    private String displayName;


    private String provider;
    
    /** **** **** **** 1234
    * user@upi */
    private String maskedDetails;
    
    /*
     * Token received from your payment gateway.
     */
    private String gatewayToken;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean defaultMethod = false;

    
    


}
