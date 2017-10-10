package gallery

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReviewController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Review.list(params), model:[reviewCount: Review.count()]
    }

    def show(Review review) {
        respond review
    }

    @Transactional
    def save(Review review) {
        String token = request.getHeader('token')

        if(token == null){ //Fail if no token provided
            transactionStatus.setRollbackOnly()
            render(status: 401, text: "Requires login to insert new review")
            return            
        }
    
        if (review == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (review.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond review.errors, view:'create'
            return
        }

        review.save flush:true

        respond review, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Review review) {
        if (review == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (review.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond review.errors, view:'edit'
            return
        }

        review.save flush:true

        respond review, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Review review) {

        if (review == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        review.delete flush:true

        render status: NO_CONTENT
    }
}
