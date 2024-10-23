package org.imaginnovate.entity.document;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;    

@Entity
@Table(name = "dc_document_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentType  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Byte id;

    @Column(name = "name", unique = true, nullable = false, length = 15)
    private String name;
    
}
