package urlshortener.usecase.exception

class NotFoundException(override val message: String) : RuntimeException() {
}