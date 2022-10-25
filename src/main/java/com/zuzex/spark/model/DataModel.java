package com.zuzex.spark.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "data")
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonnode", typeClass = JsonNodeStringType.class)
public class DataModel implements Serializable {

    @Id
    private UUID id;

    @Type(type = "jsonnode")
    private JsonNode data;
}
