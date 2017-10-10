package gallery

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserProfileController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserProfile.list(params), model:[userProfileCount: UserProfile.count()]
    }

    def show(UserProfile userProfile) {
        respond userProfile
    }

    @Transactional
    def save(UserProfile userProfile) {
        if (userProfile == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (userProfile.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userProfile.errors, view:'create'
            return
        }

        userProfile.save flush:true

        respond userProfile, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(UserProfile userProfile) {
        if (userProfile == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (userProfile.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userProfile.errors, view:'edit'
            return
        }

        userProfile.save flush:true

        respond userProfile, [status: OK, view:"show"]
    }

    @Transactional
    def delete(UserProfile userProfile) {

        if (userProfile == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        userProfile.delete flush:true

        render status: NO_CONTENT
    }
}
