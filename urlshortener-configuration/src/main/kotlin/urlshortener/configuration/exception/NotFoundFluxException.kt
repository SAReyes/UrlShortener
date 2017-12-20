package urlshortener.configuration.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import urlshortener.usecase.exception.NotFoundException

class NotFoundFluxException(notFound: NotFoundException) :
        ResponseStatusException(HttpStatus.NOT_FOUND, notFound.message, notFound)
