package com.mpolivaha.lambda.jb.repository

import com.mpolivaha.lambda.jb.domain.JobDefinition
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional

@ApplicationScoped
class JobRepository @Inject constructor(
    @PersistenceContext val entityManager: EntityManager
) {

    @Transactional
    fun save(jobDefinition: JobDefinition) {
        entityManager.persist(jobDefinition)
    }
}