package gallery

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ShoppingCartController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ShoppingCart.list(params), model:[shoppingCartCount: ShoppingCart.count()]
    }

    def show(ShoppingCart shoppingCart) {
        respond shoppingCart
    }

    @Transactional
    def save(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (shoppingCart.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond shoppingCart.errors, view:'create'
            return
        }

        shoppingCart.save flush:true

        respond shoppingCart, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (shoppingCart.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond shoppingCart.errors, view:'edit'
            return
        }

        shoppingCart.save flush:true

        respond shoppingCart, [status: OK, view:"show"]
    }

    @Transactional
    def delete(ShoppingCart shoppingCart) {

        if (shoppingCart == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        shoppingCart.delete flush:true

        render status: NO_CONTENT
    }
}
