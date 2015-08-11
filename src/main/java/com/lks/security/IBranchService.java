package com.lks.security;

import com.lks.core.model.BranchDO;
import com.lks.core.model.UserModelDO;

import java.util.List;

/**
 * Created by lokkur on 8/10/2015.
 */
public interface IBranchService {

    String createNewBranch(BranchDO branchDO);
    boolean editBranch(BranchDO branchDO);
    boolean deleteBranch(BranchDO branchDO);
    List<BranchDO> getAllBranches();
}
