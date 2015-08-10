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
        if(branchDao.retrieveBranch(branchDO.getBranchName()) == null)
            return branchDao.createBranch(branchDO.getBranchName(), branchDO.getBranchAddress());
        else
            throw new FALException("Branch already Exists");
    }

    @Override
    public boolean editBranchAddress(BranchDO branchDO) {
        Branch branch = branchDao.retrieveBranch(branchDO.getBranchName());
        branch.setBranchName(branchDO.getBranchName());
        return branchDao.editBranch(branch);
    }

    @Override
    public boolean deleteBranch(BranchDO branchDO) {
        if(branchDao.retrieveBranch(branchDO.getBranchName()) != null){
            return branchDao.deleteExistingBranch(branchDO.getBranchName());
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
            branchDO.setBranchName(branch.getBranchName());
            branchDO.setBranchAddress( branch.getBranchAddress());
            branchDOList.add(branchDO);
        }
        return branchDOList;
    }
}
