package model;
import dao.AccountDAO;

public class LoginLogic {
	private boolean flag;
	public LoginLogic() {
		// TODO 自動生成されたコンストラクター・スタブ
		flag = true;
	}
	public Account execute(User user) {
		AccountDAO dao = new AccountDAO();
		Account account = dao.findByLogin(user);
		return account;
	}

	
	public boolean getFlag() {
		return this.flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
