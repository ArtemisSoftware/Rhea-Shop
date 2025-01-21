package com.artemissoftware.rheashop.controller

import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.service.CartItemService
import com.artemissoftware.rheashop.service.CartService
import com.artemissoftware.rheashop.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND

@RestController
@RequestMapping("\${api.prefix}/cartItems")
class CartItemController(
    private val cartItemService: CartItemService,
    private val cartService: CartService,
    private val userService: UserService
) {

    @PostMapping("/item/add")
    fun addItemToCart(
        @RequestParam productId: Long,
        @RequestParam quantity: Int
    ): ResponseEntity<ApiResponse> {
        try {
//            val user = userService.getAuthenticatedUser()
//            val cart = cartService.initializeNewCart(user)
//            cartItemService.addItemToCart(cart.id, productId, quantity)

            return ResponseEntity
                .ok(ApiResponse("Add Item Success"))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body<ApiResponse>(ApiResponse("Error adding item to card", e.message))
        } //catch (e: JwtException) {
//            return ResponseEntity.status(UNAUTHORIZED).body<ApiResponse>(ApiResponse(e.getMessage(), null))
//        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    fun removeItemFromCart(@PathVariable cartId: Long, @PathVariable itemId: Long): ResponseEntity<ApiResponse> {
        try {
            cartItemService.removeItemFromCart(cartId, itemId)
            return ResponseEntity
                .ok(ApiResponse("Remove Item Success", null))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error removing item with id $itemId", e.message))
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    fun updateItemQuantity(
        @PathVariable cartId: Long,
        @PathVariable itemId: Long,
        @RequestParam quantity: Int
    ): ResponseEntity<ApiResponse> {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity)
            return ResponseEntity
                .ok(ApiResponse("Update Item Success"))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error updating item with id $itemId", e.message))
        }
    }
}