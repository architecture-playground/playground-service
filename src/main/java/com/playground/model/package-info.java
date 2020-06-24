@TypeDefs({
        @TypeDef(name = "pg-uuid-binary", defaultForType = UUID.class, typeClass = UUIDBinaryType.class),
        @TypeDef(name = "pgsql-enum", typeClass = PostgreSQLEnumType.class)
})
package com.playground.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.UUIDBinaryType;

import java.util.UUID;