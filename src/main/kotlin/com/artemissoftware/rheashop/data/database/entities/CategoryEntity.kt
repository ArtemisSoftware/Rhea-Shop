package com.artemissoftware.rheashop.data.database.entities
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

class CategoryEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var name: String = ""

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    var products: List<ProductEntity> = emptyList()

    constructor(name: String): this(){
        this.name = name
    }
}