@TypeDef(name = "pg-uuid-binary", defaultForType = UUID.class, typeClass = UUIDBinaryType.class)
package com.playground.model;

import org.hibernate.annotations.TypeDef;
import org.hibernate.type.UUIDBinaryType;

import java.util.UUID;