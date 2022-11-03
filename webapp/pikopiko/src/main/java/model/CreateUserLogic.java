package model;
import dao.AccountDAO;

public class CreateUserLogic{
	private boolean flag;
	public CreateUserLogic() {
		// TODO 自動生成されたコンストラクター・スタブ
		flag = true;
	}
	public boolean execute(User user) {
		AccountDAO dao = new AccountDAO();
		Account account = dao.createUser(user);
		if (account == null) {
			flag = false;
			return flag;
		}else {
			flag = true;
			return flag;
		}
	}
}