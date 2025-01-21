package com.artemissoftware.rheashop.data.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.NaturalId


@Entity
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var firstName: String = ""
    var lastName: String = ""

    @NaturalId
    var email: String = ""
    var password: String = ""

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var cart: CartEntity? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orders: List<OrderEntity> = emptyList()

//    @ManyToMany(
//        fetch = FetchType.EAGER,
//        cascade = [CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH]
//    )
//    @JoinTable(
//        name = "user_roles",
//        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
//        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
//    )
//    private val roles: Collection<Role> = HashSet<Role>()

}