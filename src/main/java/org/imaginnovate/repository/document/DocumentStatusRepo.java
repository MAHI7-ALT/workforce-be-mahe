
package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.entity.document.DocumentStatus;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentStatusRepo  implements PanacheRepositoryBase<DocumentStatus , Integer >{

    public List<DocumentStatus> getAllDocumentStatus() {
        return getEntityManager().createQuery("select new org.imaginnovate.entity.document.DocumentStatus(id,name) from DocumentStatus", DocumentStatus.class).getResultList();
    }

    public DocumentStatus findById(DocumentStatus documentStatus) {
        return getEntityManager().find(DocumentStatus.class, documentStatus);
    }

    public void merge(DocumentStatus existingDocumentStatus) {
        getEntityManager().merge(existingDocumentStatus);
    }

    public DocumentStatus findById(Byte id) {
        return id == null ? null : getEntityManager().find(DocumentStatus.class, id);
    }

}
