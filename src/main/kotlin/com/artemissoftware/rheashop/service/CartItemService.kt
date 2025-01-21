package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.database.entities.CartItemEntity
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.repository.CartItemRepository
import com.artemissoftware.rheashop.repository.CartRepository
import com.artemissoftware.rheashop.repository.ProductRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CartItemService(
    private val cartItemRepository: CartItemRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {

    fun addItemToCart(cartId: Long, productId: Long, quantity: Int) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
        val cart = cartRepository.findById(cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found") }

        val product = productRepository.findById(productId)
            .orElseThrow { ResourceNotFoundException("Product not found") }

        val cartItem = cart
            .items
            .stream()
            .filter { item -> item.product?.id == productId }
            .findFirst().orElse(CartItemEntity())

        if (cartItem.id == 0L) {
            cartItem.cart = cart
            cartItem.product = product
            cartItem.quantity = quantity
            cartItem.unitPrice = product.price
        } else {
            cartItem.quantity = (cartItem.quantity + quantity)
        }
        cartItem.setTotalPrice()
        cart.addItem(cartItem)
        cartItemRepository.save(cartItem)
        cartRepository.save(cart)
    }

    fun removeItemFromCart(cartId: Long, productId: Long) {
        val cart = cartRepository.findById(cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found") }

        val itemToRemove = getCartItem(cartId, productId)
        cart.removeItem(itemToRemove)
        cartRepository.save(cart)
    }

    fun updateItemQuantity(cartId: Long, productId: Long, quantity: Int) {
        val cart = cartRepository.findById(cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found") }

        cart
            .items
            .stream()
            .filter { item -> item.product?.id == productId }
            .findFirst()
            .ifPresent { item ->
                item.quantity = quantity
                item.unitPrice = item.product?.price ?: BigDecimal.ZERO
                item.setTotalPrice()
            }

        val totalAmount = cart
            .items
            .stream()
            .map{ it.totalPrice }
            .reduce(BigDecimal.ZERO, BigDecimal::add)

        cart.totalAmount = totalAmount
        cartRepository.save(cart)
    }

    fun getCartItem(cartId: Long, productId: Long): CartItemEntity {
        val cart = cartRepository.findById(cartId)
            .orElseThrow { ResourceNotFoundException("Cart not found") }

        return cart
            .items
            .stream()
            .filter { item -> item.product?.id == productId }
            .findFirst()
            .orElseThrow { ResourceNotFoundException("Item not found") }
    }
}