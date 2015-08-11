package com.lks.security;

import com.lks.core.FALException;
import com.lks.core.model.BranchDO;
import com.lks.orm.dao.BranchDao;
import com.lks.orm.entities.Branch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lokkur on 8/10/2015.
 */
public class BranchService implements IBranchService {

    @Resource(name = "branchDao")
    BranchDao branchDao;

    @Override
    public String createNewBranch(BranchDO branchDO) {
        //check if the user exists already
        if(branchDao.retrieveBranch(branchDO.getBranchCode()) == null)
            return branchDao.createBranch(branchDO.getBranchCode(),branchDO.getBranchName(), branchDO.getZone(), branchDO.getRegion());
        else
            throw new FALException("Branch already Exists");
    }

    @Override
    public boolean editBranch(BranchDO branchDO) {
        Branch branch = branchDao.retrieveBranch(branchDO.getBranchCode());
        branch.setBranchName(branchDO.getBranchName());
        return branchDao.editBranch(branch);
    }

    @Override
    public boolean deleteBranch(BranchDO branchDO) {
        if(branchDao.retrieveBranch(branchDO.getBranchCode()) != null){
            return branchDao.deleteExistingBranch(branchDO.getBranchCode());
        }else
            throw new FALException("Branch does not exist: "+  branchDO.getBranchName());

    }

    @Override
    public List<BranchDO> getAllBranches() {
        List<Branch> branchList = branchDao.retrieveAllBranches();
        List<BranchDO> branchDOList = new ArrayList<>();
        BranchDO branchDO = null;
        for(Branch branch : branchList){
            branchDO = new BranchDO();
            branchDO.setBranchCode(branch.getBranchCode());
            branchDO.setBranchName(branch.getBranchName());
            branchDO.setZone( branch.getZone());
            branchDO.setRegion(branch.getRegion());
            branchDOList.add(branchDO);
        }
        return branchDOList;
    }
}
