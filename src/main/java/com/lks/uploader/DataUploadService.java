package com.lks.uploader;

import com.lks.core.enums.DocOperations;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.orm.dao.DataUploadDao;
import com.lks.stateMachine.IState;
import com.lks.stateMachine.NRState;
import com.lks.stateMachine.StateMachineFactory;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */

public class DataUploadService implements IDataUploadService {

    public static final Logger logger = Logger.getLogger(DataUploadService.class.getName());


    @Resource(name = "dataUploadDao")
    DataUploadDao dataUploadDao;

    @Resource(name = "stateMachineFactory")
    StateMachineFactory stateMachineFactory;

    public int createNewDocument(FileReceivedForUploadDO fileReceivedForUploadDO){

        logger.info("Entered the creation of new document method in data upload service");
        try{
            IState currentState = new NRState();
            return currentState.create(fileReceivedForUploadDO.getFileName(), fileReceivedForUploadDO.getFileLocation(), fileReceivedForUploadDO.getCreatedBy(), fileReceivedForUploadDO.getBranchName(), fileReceivedForUploadDO.getPlaceOfMeeting(), fileReceivedForUploadDO.getBookletNo(), fileReceivedForUploadDO.getApplicationNo(), fileReceivedForUploadDO.getNumOfCustomers());

        }catch(Exception e){
            logger.severe("Encountered exception in the method create New document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }
        return -1;

    }

    public synchronized String performOperationOnDocument(FileOperationDO fileOperationDO){

        DocOperations docOperations = fileOperationDO.getDocOperations();
        IState currentState = stateMachineFactory.getCurrentState(fileOperationDO.getDocumentId());
        if(DocOperations.HOLD.equals(docOperations)){
            currentState.hold(fileOperationDO.getDocumentId(), fileOperationDO.getComment(), fileOperationDO.getUserId());
        }else if(DocOperations.LOCK.equals(docOperations)){
            currentState.lock(fileOperationDO.getDocumentId(), fileOperationDO.getUserId());
        }else if(DocOperations.APPROVE.equals(docOperations)){
            currentState.approve(fileOperationDO.getDocumentId(), fileOperationDO.getUserId());
        }else if(DocOperations.REJECT.equals(docOperations)){
            currentState.reject(fileOperationDO.getDocumentId(), fileOperationDO.getComment(), fileOperationDO.getAssignedTo(), fileOperationDO.getUserId());
        }else if(DocOperations.COMPLETE.equals(docOperations)){
            currentState.complete(fileOperationDO.getDocumentId(), fileOperationDO.getUserId());
        }else if(DocOperations.RESOLVE.equals(docOperations)){
            currentState.resolve(fileOperationDO.getDocumentId(), fileOperationDO.getComment(), fileOperationDO.getAssignedTo(), fileOperationDO.getUserId());
        }


        return null;

    }


}
