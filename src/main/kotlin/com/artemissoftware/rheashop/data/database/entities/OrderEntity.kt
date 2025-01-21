package com.artemissoftware.rheashop.data.database.entities

import com.artemissoftware.rheashop.data.models.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate


@Entity
class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var orderDate: LocalDate = LocalDate.now()
    var totalAmount: BigDecimal = BigDecimal.ZERO

    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: Set<OrderItemEntity> = HashSet<OrderItemEntity>()

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
}