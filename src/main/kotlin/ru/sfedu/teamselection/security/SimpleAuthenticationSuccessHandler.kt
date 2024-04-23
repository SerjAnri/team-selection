package ru.sfedu.teamselection.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import ru.sfedu.teamselection.domain.Users
import ru.sfedu.teamselection.enums.Roles
import ru.sfedu.teamselection.repository.UserRepository
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class SimpleAuthenticationSuccessHandler(
    private val userRepository: UserRepository
): AuthenticationSuccessHandler {

    private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        var redirectUrl = ""
        val user = authentication.principal as DefaultOidcUser
        val email = user.email
        val userInDb = userRepository.findByEmail(email)
        if (userInDb == null) {
            userRepository.save(Users(fio = user.fullName, email = email, role = Roles.USER))
            redirectUrl += "/registration"
            redirectStrategy.sendRedirect(request, response, redirectUrl)
            return
        }
        if (userInDb.role == Roles.ADMINISTRATOR || userInDb.role == Roles.SUPER_ADMINISTRATOR) {
            redirectUrl = "/admin_panel"
            redirectStrategy.sendRedirect(request, response, redirectUrl)
            return
        }
        if (userInDb.role == Roles.USER) {
            redirectUrl = "/user_profile"
            redirectStrategy.sendRedirect(request, response, redirectUrl)
            return
        }

    }
}