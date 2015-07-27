package com.lks.stateMachine;

import com.lks.core.DocumentUtils;
import com.lks.core.model.DocumentDO;
import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ARState extends AbstractState {

    @Override
    public Document archive(Document document) {
        //copy everything to DocumentArchive entity
        DocumentArchive documentArchive = DocumentUtils.archiveDocument(document);
        //save the record in db
        documentUploadDao.archiveDocument(documentArchive);
        //delete the document record
        documentUploadDao.deleteDocument(document);

        //move the file from file server to archiver

        return null;
    }
}
