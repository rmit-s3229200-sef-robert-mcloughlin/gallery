package gallery

import grails.gorm.DetachedCriteria

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import gallery.Authentication

@Transactional(readOnly = true)
class UserController {
    Authentication auth = new Authentication()

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userCount: User.count()]
    }

    def show(User user) {
        respond user
    }

    def login(String username, String password){
        DetachedCriteria criteria = new DetachedCriteria(User).build {
            eq 'username', username
        }

        User u = criteria.get()

        println("Got User: ")
        println(u)

        if(u != null){
            if(u.password.equals(password)){
                println("Password Matches")
                String token = auth.login(u)
                render(token)
            } else {
                println("Password failed")
                render (status: 401, text: "Authentication failed")
                return "Authentication Failed, incorrect username or password"
            }
        } else {
            println("User is null")
            return "Failed"
        }

    }

    @Transactional
    def save(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        user.save flush:true

        respond user, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }

        user.save flush:true

        respond user, [status: OK, view:"show"]
    }

    @Transactional
    def delete(User user) {

        if (user == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        user.delete flush:true

        render status: NO_CONTENT
    }
}
