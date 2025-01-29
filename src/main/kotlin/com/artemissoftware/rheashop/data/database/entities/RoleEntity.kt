package com.artemissoftware.rheashop.data.database.entities

import jakarta.persistence.*


@Entity
class RoleEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var name: String = ""

    constructor(name: String) : this(){
        this.name = name
    }

    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<UserEntity> = HashSet()

}