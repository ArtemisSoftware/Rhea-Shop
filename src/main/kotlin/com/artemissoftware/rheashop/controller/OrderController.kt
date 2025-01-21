package com.artemissoftware.rheashop.controller

import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.service.OrderService
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.prefix}/orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping("/order")
    fun createOrder(@RequestParam userId: Long): ResponseEntity<ApiResponse> {
        try {
            val order = orderService.placeOrder(userId)
            //-----------------------cartService.clearCart(cart.id)

            return ResponseEntity
                .ok(ApiResponse("Item Order Success!", order))
        } catch (e: Exception) {
            return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ApiResponse("Error occurred creating order for user $userId!", e.message))
        }
    }

    @GetMapping("/{orderId}/order")
    fun getOrderById(@PathVariable orderId: Long): ResponseEntity<ApiResponse> {
        try {
            val order = orderService.getOrder(orderId)
            return ResponseEntity
                .ok(ApiResponse("Item Order Success!", order))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Oops!", e.message))
        }
    }

    @GetMapping("/user/{userId}/order")
    fun getUserOrders(@PathVariable userId: Long): ResponseEntity<ApiResponse> {
        try {
            val orders = orderService.getUserOrders(userId)
            return ResponseEntity
                .ok(ApiResponse("Item Order Success!", orders))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Oops!", e.message))
        }
    }
}