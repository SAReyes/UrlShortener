package urlshortener.dataprovider.database.click

import org.springframework.data.repository.CrudRepository

interface ClickRepository: CrudRepository<ClickEntity, Long>
