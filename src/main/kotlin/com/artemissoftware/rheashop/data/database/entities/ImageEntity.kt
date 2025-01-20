package com.artemissoftware.rheashop.data.database.entities

import jakarta.persistence.*
import java.sql.Blob

@Entity
class ImageEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var fileName: String = ""
    var fileType: String = ""

    @Lob
    var image: Blob? = null
    var downloadUrl: String = ""

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null
}