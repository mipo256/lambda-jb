package com.mpolivaha.lambda.jb.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class ProcessLaunchContext (

    @Column(name = "vm_options")
    open val vmOptions: List<String>,

    @Column(name = "env_variables")
    open val envVariables: List<String>,
)