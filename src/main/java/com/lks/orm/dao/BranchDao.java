package com.lks.orm.dao;

import com.lks.orm.entities.Branch;
import com.lks.orm.entities.Comments;

import java.util.List;

public interface BranchDao {

	String createBranch(int branchCode, String branchName, String zone, String region);
    List<Branch> retrieveAllBranches();
    Branch retrieveBranch(int branchCode);
    boolean editBranch(Branch branch);
    boolean deleteExistingBranch(int branchCode);
}