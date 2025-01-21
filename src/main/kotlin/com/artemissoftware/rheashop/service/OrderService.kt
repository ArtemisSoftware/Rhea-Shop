package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.database.entities.CartEntity
import com.artemissoftware.rheashop.data.database.entities.OrderEntity
import com.artemissoftware.rheashop.data.database.entities.OrderItemEntity
import com.artemissoftware.rheashop.data.database.entities.ProductEntity
import com.artemissoftware.rheashop.data.mapper.toDto
import com.artemissoftware.rheashop.data.models.OrderStatus
import com.artemissoftware.rheashop.data.network.dto.OrderDto
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.repository.CartItemRepository
import com.artemissoftware.rheashop.repository.CartRepository
import com.artemissoftware.rheashop.repository.OrderRepository
import com.artemissoftware.rheashop.repository.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.function.Function
import java.util.function.Supplier


@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) {

    @Transactional
    fun placeOrder(userId: Long): OrderEntity {

        cartRepository.findByUserId(userId)?.let { cart ->
            val order = createOrder(cart)
            val orderItemList = createOrderItems(order, cart)
            order.items = HashSet(orderItemList)
            order.totalAmount = calculateTotalAmount(orderItemList)

            val savedOrder = orderRepository.save(order)
            //-----------------------cartService.clearCart(cart.id)
            return savedOrder
        } ?: run {
            throw ResourceNotFoundException("Cart not found")
        }
    }

    private fun createOrder(cart: CartEntity): OrderEntity {
        val order = OrderEntity()
        order.user = cart.user
        order.status = OrderStatus.PENDING
        return order
    }

    private fun createOrderItems(order: OrderEntity, cart: CartEntity): List<OrderItemEntity> {
        return cart
            .items
            .stream()
            .map { cartItem ->
                val product = cartItem.product
                product?.let {
                    it.inventory = (it.inventory - cartItem.quantity)
                    productRepository.save(it)
                    OrderItemEntity(
                        order = order,
                        product = it,
                        quantity = cartItem.quantity,
                        price = cartItem.unitPrice
                    )
                }
            }
            .toList()
    }

    private fun calculateTotalAmount(orderItemList: List<OrderItemEntity>): BigDecimal {
        return orderItemList
            .stream()
            .map { item ->
                item.price
                    .multiply(BigDecimal(item.quantity))
            }
            .reduce(BigDecimal.ZERO, BigDecimal::add)
    }

    fun getOrder(orderId: Long): OrderDto {
        return orderRepository.findById(orderId)
            .map { it.toDto() }
            .orElseThrow { ResourceNotFoundException("Order not found") }
    }

    fun getUserOrders(userId: Long): List<OrderDto> {
        val orders = orderRepository.findByUserId(userId)
        return orders
            .stream()
            .map { it.toDto() }
            .toList()
    }
}