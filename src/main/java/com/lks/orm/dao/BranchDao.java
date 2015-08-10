package com.lks.orm.dao;

import com.lks.orm.entities.Branch;
import com.lks.orm.entities.Comments;

import java.util.List;

public interface BranchDao {

	String createBranch(String branchName, String branchAddress);
    List<Branch> retrieveAllBranches();
    Branch retrieveBranch(String branchName);
    boolean editBranch(Branch branch);
    boolean deleteExistingBranch(String branchName);
}