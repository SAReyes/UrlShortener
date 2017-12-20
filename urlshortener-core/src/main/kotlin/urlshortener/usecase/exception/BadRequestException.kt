package urlshortener.usecase.exception

class BadRequestException(override val message: String) : RuntimeException()
