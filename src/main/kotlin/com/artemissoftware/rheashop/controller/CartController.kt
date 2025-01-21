package com.artemissoftware.rheashop.controller

import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.service.CartService
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("\${api.prefix}/carts")
class CartController(
    private val cartService: CartService
) {

    @GetMapping("/{cartId}/my-cart")
    fun getCart(@PathVariable cartId: Long): ResponseEntity<ApiResponse> {
        try {
            val cart = cartService.getCart(cartId)
            return ResponseEntity
                .ok(ApiResponse("Success", cart))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Unable to get cart with id: $cartId",e.message))
        }
    }

    @DeleteMapping("/{cartId}/clear")
    fun clearCart(@PathVariable cartId: Long): ResponseEntity<ApiResponse> {
        try {
            cartService.clearCart(cartId)
            return ResponseEntity
                .ok(ApiResponse("Clear Cart Success!"))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Unable to clear cart with id: $cartId", e.message))
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    fun getTotalAmount(@PathVariable cartId: Long): ResponseEntity<ApiResponse> {
        try {
            val totalPrice: BigDecimal = cartService.getTotalPrice(cartId)
            return ResponseEntity
                .ok(ApiResponse("Total Price", totalPrice))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Unable to get total amount from cart with id: $cartId", e.message))
        }
    }
}