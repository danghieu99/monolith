package com.danghieu99.monolith.order.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.order.constant.ECancelStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cancel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Cancel extends BaseEntity {

    @Column(nullable = false)
    private String userAccountUUID;

    @Column(nullable = false)
    private String orderUUID;

    @Column(nullable = false)
    private String shopUUID;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    @Builder.Default
    private ECancelStatus status = ECancelStatus.CANCEL_PENDING;
}