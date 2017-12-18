package urlshortener.configuration.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import urlshortener.usecase.exception.BadRequestException

class BadRequestFluxException(badRequest: BadRequestException) :
        ResponseStatusException(HttpStatus.BAD_REQUEST, badRequest.message, badRequest)
