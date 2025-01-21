package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.database.entities.CartEntity
import com.artemissoftware.rheashop.data.database.entities.UserEntity
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.repository.CartItemRepository
import com.artemissoftware.rheashop.repository.CartRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository
) {

    fun getCart(id: Long): CartEntity {
        val cart = cartRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Cart not found") }

        val totalAmount = cart.totalAmount
        cart.totalAmount = totalAmount
        return cartRepository.save(cart)
    }

    @Transactional
    fun clearCart(id: Long) {
        val cart = getCart(id)
        cartItemRepository.deleteAllByCartId(id)
        cart.clearCart()
        cartRepository.deleteById(id)
    }

    fun getTotalPrice(id: Long): BigDecimal {
        val cart = getCart(id)
        return cart.totalAmount
    }

    fun initializeNewCart(user: UserEntity): CartEntity {

        val userCart = cartRepository.findByUserId(user.id)

        return if(userCart == null){
            val cart = CartEntity()
            cart.user = user
            cartRepository.save(cart)
        }
        else userCart
    }

    fun getCartByUserId(userId: Long): CartEntity? {
        return cartRepository.findByUserId(userId)
    }
}