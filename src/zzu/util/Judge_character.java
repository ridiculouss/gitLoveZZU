package zzu.util;

public class Judge_character {

	// 检查是否是数字并且为11位
	public boolean character(String str) {

		if (str != null && str.matches("[0-9]+") && str.length() == 11) {

			return true;
		} else {
			return false;
		}

	}

	// 判断第一个参数，是否在第二个数组参数中
	public static boolean JudgeMember(String userId, String[] existeduid) {
		
		for (String s : existeduid) {// 判断是否已存在
			if (userId.equals(s)) {
				
				return true;//存在则返回true
			}
		}

		return false;
	}

	public static void main(String[] args) {
		Judge_character j = new Judge_character();
		boolean b = j.character("12345678901");
		System.out.println(b);

		System.out.println((int) ((Math.random() * 9 + 1) * 100000000)); // 随机生成9位数
	}
}
