package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.entity.document.DocumentType;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentTypeRepo implements PanacheRepositoryBase<DocumentType , Byte >{


    public List<DocumentType> findAlllDocumentTypes() {
        return getEntityManager().createQuery("select  new org.imaginnovate.entity.document.DocumentType(id, name) from DocumentType d", DocumentType.class).getResultList();
    }

    public DocumentType findById(Byte id) {
        return getEntityManager().find(DocumentType.class, id);
    }

    public DocumentType findById() {
        return getEntityManager().createQuery("select  new org.imaginnovate.Entity.document.DocumentType(id) from DocumentType d", DocumentType.class).getSingleResult();
    }

    public DocumentType findById(String documentTypeId) {
        return getEntityManager().find(DocumentType.class, documentTypeId);
    }

    public DocumentType findById(DocumentType documentTypeId) {
        return getEntityManager().find(DocumentType.class, documentTypeId.getId());
    }

    public DocumentType findByName(String name) {
        return getEntityManager().find(DocumentType.class, name);
    }


}
