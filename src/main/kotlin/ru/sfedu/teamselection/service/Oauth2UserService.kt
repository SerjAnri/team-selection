package ru.sfedu.teamselection.service

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class Oauth2UserService(
): DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        return super.loadUser(userRequest) as DefaultOidcUser
    }
}