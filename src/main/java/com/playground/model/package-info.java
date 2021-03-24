@TypeDefs({
        /*
         * the following line defines org.jadira.usertype.dateandtime.joda.PersistentDateTime to be the default type being
         * used when an entity field is of type DateTime.
         */
        @TypeDef(name = "PersistentDateTime", typeClass = PersistentDateTime.class, defaultForType = DateTime.class),
        @TypeDef(name = "pg-uuid-binary", defaultForType = UUID.class, typeClass = UUIDBinaryType.class),
        @TypeDef(name = "pgsql-enum", typeClass = PostgreSQLEnumType.class)
})
package com.playground.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.UUIDBinaryType;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.DateTime;

import java.util.UUID;
