package model;

import java.util.ArrayList;

import dao.AccountDAO;

public class ConstLogic {
	public int getTitleCount(Account account) {
		AccountDAO dao = new AccountDAO();
		return dao.getRecordCount(account);
	}

	public ArrayList<String> getTitle(Account account) {
		AccountDAO dao = new AccountDAO();		
		return dao.getRecordTitle(account);
	}

}
