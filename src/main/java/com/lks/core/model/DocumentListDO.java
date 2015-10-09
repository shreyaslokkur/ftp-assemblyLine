package com.lks.core.model;

import java.util.List;

/**
 * Created by lokkur on 10/9/2015.
 */
public class DocumentListDO extends AbstractDO {
    private List<DocumentDO> documentList;
    private Long totalCount;

    public List<DocumentDO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentDO> documentList) {
        this.documentList = documentList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
