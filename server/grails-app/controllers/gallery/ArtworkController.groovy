package gallery

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ArtworkController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Artwork.list(params), model:[artworkCount: Artwork.count()]
    }

    def show(Artwork artwork) {
        respond artwork
    }

    @Transactional
    def save(Artwork artwork) {
        if (artwork == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (artwork.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond artwork.errors, view:'create'
            return
        }

        artwork.save flush:true

        respond artwork, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Artwork artwork) {
        if (artwork == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (artwork.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond artwork.errors, view:'edit'
            return
        }

        artwork.save flush:true

        respond artwork, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Artwork artwork) {

        if (artwork == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        artwork.delete flush:true

        render status: NO_CONTENT
    }
}
